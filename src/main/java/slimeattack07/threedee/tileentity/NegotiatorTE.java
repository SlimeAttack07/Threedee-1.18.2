package slimeattack07.threedee.tileentity;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.util.TdBasicMethods;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class NegotiatorTE extends BlockEntity{
	public int current_time;
	public boolean running;
	public static final int NEG_TIME = 3600;
	
	private final ItemStackHandler itemhandler = createHandler();
	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemhandler);

	public NegotiatorTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.NEGOTIATOR.get(), pos, state);
	}
	
	public ItemStackHandler getHandler() {
		return itemhandler;
	}
	
	public boolean hasOutput() {
		return !itemhandler.getStackInSlot(1).isEmpty();
	}
	
	public boolean hasInput() {
		return !itemhandler.getStackInSlot(0).isEmpty();
	}
	
	public void dropItems() {
		TdBasicMethods.spawn(itemhandler.extractItem(-1, 1, false), level, worldPosition, 0, 1, 0);
		TdBasicMethods.spawn(itemhandler.extractItem(1, 1, false), level, worldPosition, 0, 1, 0);
	}
	
	public void retreiveOutput(Player player) {
		if(running)
			return;
		
		TdBasicMethods.addOrSpawn(player, itemhandler.extractItem(1, 1, false), level, worldPosition, 0, 1, 0);
		TdBasicMethods.playSound(level, worldPosition, SoundEvents.VILLAGER_CELEBRATE);
	}
	
	public void addInput(ItemStack input, Player player) {
		if(canInsertItem(input, player, false)) {
			
			ItemStack result = itemhandler.insertItem(-2, input.copy(), false);
			
			if(!(result.equals(input, false))) {
				TdBasicMethods.reduceStack(input, 1);
				running = true;
			}
		}
	}
	
	private boolean canInsertItem(ItemStack input, @Nullable Player player, boolean simulate) {
		if(running)
			return false;
		
		boolean retrieved_output = false;
		
		if(player != null && hasOutput()) {
			retreiveOutput(player);
			retrieved_output = true;
			TdBasicMethods.playSound(level, worldPosition, SoundEvents.ITEM_PICKUP);
		}
		
		CompoundTag nbt = input.getTag();
		
		if(nbt != null && nbt.contains(Threedee.MOD_ID)) {
			CompoundTag threedee = nbt.getCompound(Threedee.MOD_ID);
			boolean can_be_sold = threedee.getBoolean("can_be_sold");
			boolean prices_known = threedee.getBoolean("prices_known");
			
			if(can_be_sold && !prices_known) {				
				if(!simulate) {					
					int seconds = NegotiatorTE.NEG_TIME / 20;
					int minutes = seconds / 60;
					seconds -= 60 * minutes;
					
					String message = TdBasicMethods.getTranslation("message.threedee.negotiator.start");
					message = message.replace("MARKER1", minutes + "");
					message = message.replace("MARKER2", seconds + "");
					
					TdBasicMethods.messagePlayerCustom(player, message);
					TdBasicMethods.playSound(level, worldPosition, SoundEvents.VILLAGER_YES);
				}
				
				return true;
			}
			
			if(!retrieved_output) {
				if(prices_known)
					TdBasicMethods.messagePlayer(player, "message.threedee.negotiator.already_known");
				else
					TdBasicMethods.messagePlayer(player, "message.threedee.negotiator.invalid");
			}
		}
		else if(!retrieved_output)
			TdBasicMethods.messagePlayer(player, "message.threedee.negotiator.invalid");
		
		return false;
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? handler.cast() : super.getCapability(cap, side);
	}
	
	private ItemStackHandler createHandler(){
		return new ItemStackHandler(2){
			@Override
			protected void onContentsChanged(int slot){
				setChanged();
			}

			// Only allow items in a slot when it's empty
			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack){				
				return slot != 1 && getStackInSlot(1).isEmpty();
			}

			// Validate here again in case other mods somehow skip isItemValid(int, ItemStack)
			@Nonnull
			@Override
			public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
				if(slot == -1)
					return forceInsertItem(1, stack, simulate);
				
				if(slot == -2)
					return forceInsertItem(0, stack, simulate);
				
				if(isItemValid(slot, stack) && canInsertItem(stack, null, simulate)) {
					ItemStack result = super.insertItem(slot, stack, simulate);
					running = !(simulate || result.equals(stack, false));
					
					return result;
				}
				
				return stack;
			}
			
			// Only allow slot 1 to be extracted from.
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				if(slot == 1)
					return super.extractItem(slot, amount, simulate);
				
				if(slot == -1)
					return super.extractItem(0, amount, simulate);
				
				return ItemStack.EMPTY;
			}
			
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}
			
			// Basically equal to super.insertItem except it doens't call isItemValid
			private ItemStack forceInsertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
				  if (stack.isEmpty())
			            return ItemStack.EMPTY;
			            
			        validateSlotIndex(slot);

			        ItemStack existing = stacks.get(slot);

			        int limit = getStackLimit(slot, stack);

			        if (!existing.isEmpty()) {
			            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
			                return stack;

			            limit -= existing.getCount();
			        }

			        if (limit <= 0)
			            return stack;

			        boolean reachedLimit = stack.getCount() > limit;

			        if (!simulate) {
			            if (existing.isEmpty()) {
			                stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
			            }
			            else {
			                existing.grow(reachedLimit ? limit : stack.getCount());
			            }
			            
			            onContentsChanged(slot);
			        }

			        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
			}
		};
	}
	
	@Override
	public void setRemoved() {
		super.setRemoved();
		handler.invalidate();
	}
	
	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.put(Threedee.MOD_ID, NBTHelper.toNBT(this));
	}
	
	@Override
	public void load( CompoundTag compound) {
		super.load(compound);
		CompoundTag tag = compound.getCompound(Threedee.MOD_ID);
		
		if(tag != null) {
			current_time = tag.getInt("currenttime");
			running = tag.getBoolean("running");

			if(tag.contains("inventory"))
				itemhandler.deserializeNBT(tag.getCompound("inventory"));
		}
	}
	
	public void tick() {	
		if(level.isClientSide() || !running || itemhandler.getStackInSlot(0).isEmpty()) 
			return;
		
		if(current_time % 360 == 0 && current_time < NEG_TIME - 360 && current_time > 0) {
			TdBasicMethods.playSound(level, worldPosition, SoundEvents.VILLAGER_NO);
		}
		
		if(current_time >= NEG_TIME) {
			current_time = 0;		
			running = false;
			ItemStack stack = itemhandler.getStackInSlot(0).copy();
			CompoundTag nbt = stack.getTag();
			CompoundTag threedee = nbt.getCompound(Threedee.MOD_ID);
			
			ThreadLocalRandom rand = ThreadLocalRandom.current();
			int price = rand.nextInt(threedee.getInt("min"), threedee.getInt("max") + 1);
			
			threedee.remove("prices_known");
			threedee.putBoolean("prices_known", true);
			threedee.remove("price");
			threedee.putInt("price", price);
			
			itemhandler.insertItem(-1, stack, false);
			itemhandler.extractItem(-1, 1, false);
			TdBasicMethods.playSound(level, worldPosition, SoundEvents.VILLAGER_CELEBRATE);
		} else
			current_time++;
		
		setChanged();
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
	}
}