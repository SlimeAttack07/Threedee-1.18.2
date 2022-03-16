package slimeattack07.threedee.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.objects.items.TokenCard;
import slimeattack07.threedee.util.TdBasicMethods;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class ArtefactExchangerTE extends BlockEntity {
	public ItemStack card = ItemStack.EMPTY;

	private final ItemStackHandler itemhandler = createHandler();
	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemhandler);
	
	public ArtefactExchangerTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.ARTEFACT_EXCHANGER.get(), pos, state);
	}
	
	public ItemStackHandler getHandler() {
		return itemhandler;
	}
	
	public ItemStack getCard() {
		return card;
	}
	
	public void setCard(ItemStack stack) {
		if(stack.getItem() instanceof TokenCard) {
			card = stack;
		}
	}
	
	public void emptyMachine() {
		card = ItemStack.EMPTY;
	}
	
	public boolean hasCard() {
		return card.getItem() instanceof TokenCard;
	}
	
	public ItemStack extractCard() {
		if(hasCard()) {
			ItemStack output = card;
			emptyMachine();
			return output;
		}
		
		return ItemStack.EMPTY;
	}
	
	public void addInput(ItemStack input, Player player) {		
		if(itemhandler.isItemValid(0, input) && canInsertItem(input, player, false)) {
			itemhandler.insertItem(-1, input.copy(), false);
			TdBasicMethods.reduceStack(input, 1);
			itemhandler.extractItem(0, 1, false);
		}
	}
	
	private boolean canInsertItem(ItemStack input, @Nullable Player player, boolean simulate) {
		CompoundTag nbt = input.getTag();
		
		if(nbt != null && nbt.contains(Threedee.MOD_ID)) {
			CompoundTag threedee = nbt.getCompound(Threedee.MOD_ID);
			boolean can_be_sold = threedee.getBoolean("can_be_sold");
			boolean prices_known = threedee.getBoolean("prices_known");
			
			if(can_be_sold) {
				if(prices_known) {
					if(hasCard()) {
						if(!simulate) {
							int balance = threedee.getInt("price");
							updateCard(balance);
							
							String message = TdBasicMethods.getTranslation("message.threedee.artefact_exchanger.exchange");
							message = message.replace("MARKER1", balance + "");
							
							TdBasicMethods.messagePlayerCustom(player, message);
							TdBasicMethods.playSound(level, worldPosition, SoundEvents.TOTEM_USE);
						}
						
						return true;
					}
					else
						TdBasicMethods.messagePlayer(player, "message.threedee.exchangers.no_card");
				}
				else
					TdBasicMethods.messagePlayer(player, "message.threedee.artefact_exchanger.no_price");
			}
			else
				TdBasicMethods.messagePlayer(player, "message.threedee.artefact_exchanger.invalid");
		}
		else
			TdBasicMethods.messagePlayer(player, "message.threedee.artefact_exchanger.invalid");
		
		return false;
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? handler.cast() : super.getCapability(cap, side);
	}
	
	private ItemStackHandler createHandler(){
		return new ItemStackHandler(1){
			@Override
			protected void onContentsChanged(int slot){
				setChanged();
			}

			// Constantly try to extract whatever is in the given slot to keep it empty. Removes the need to make this block tick.
			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack){
				extractItem(slot, 1, false);
				
				return true;
			}

			// Validate here again in case other mods somehow skip isItemValid(int, ItemStack)
			@Nonnull
			@Override
			public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
				if(slot == -1)
					return super.insertItem(0, stack, simulate);
				
				ItemStack result = isItemValid(slot, stack) && canInsertItem(stack, null, simulate) ? super.insertItem(slot, stack, simulate) : stack; 
				
				if(!result.equals(stack, false))
					extractItem(slot, 1, false);
					
				return result;
			}
			
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}
		};
	}
	
	@Override
	public void setRemoved() {
		super.setRemoved();
		handler.invalidate();
	}
	
	public void updateCard(int amount) {
		if(hasCard()) 
			TokenCard.updateBalance(card, amount);
	}
	
	// Note that we don't save or load the inventory on purpose! It just exists to enable hopper interaction..
	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.put(Threedee.MOD_ID, NBTHelper.toNBT(this));
	}
	
	@Override
	public void load( CompoundTag compound) {
		super.load(compound);
		CompoundTag tag = compound.getCompound(Threedee.MOD_ID);
		
		if(tag != null) 
			card = (ItemStack) NBTHelper.fromNBT(tag.getCompound("card"));
	}
}