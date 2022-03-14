package slimeattack07.threedee.tileentity;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
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
import slimeattack07.threedee.recipes.ArtefactAnalyzerRecipe;
import slimeattack07.threedee.util.TdBasicMethods;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class ArtefactAnalyzerTE extends BlockEntity{
	public boolean running;
	public boolean item_can_be_sold = true;
	public String last_recipe = "";
	public String loot_table = "";
	public int ticks_to_craft;
	public int current_time;
	
	private final ItemStackHandler itemhandler = createHandler();
	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemhandler);

	public ArtefactAnalyzerTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.ARTEFACT_ANALYZER.get(), pos, state);
	}
	
	public ItemStackHandler getHandler() {
		return itemhandler;
	}
	
	public void setRunning(boolean is_running) {
		running = is_running;
	}
	
	public void setTicksToCraft(int amount) {
		if (amount >= 0)
			ticks_to_craft = amount;
		else
			ticks_to_craft = 0;
	}
	
	public void setLastRecipe(String recipe) {
		last_recipe = recipe;
	}
	
	public void setLootTable(String loottable) {
		loot_table = loottable;
	}
	
	public boolean hasOutput() {
		return !itemhandler.getStackInSlot(1).isEmpty();
	}
	
	public boolean canInsertItem(ItemStack input, boolean simulate) {
		ArtefactAnalyzerRecipe recipe = getRecipe(input);
		
		if(running || recipe == null)
			return false;

		if(!simulate) {
			setLastRecipe(recipe.getId().toString());
			setLootTable(recipe.getLootTable());
			setTicksToCraft(recipe.ticksToCraft());
			setChanged();
		}
		
		return true;
	}
	
	public boolean addedInput(ItemStack stack, Player player) {
		ArtefactAnalyzerRecipe recipe = getRecipe(stack);
		
		if(recipe == null)
			return false;
		
		setLastRecipe(recipe.getId().toString());
		setLootTable(recipe.getLootTable());
		setRunning(true);
		setTicksToCraft(recipe.ticksToCraft());
		int amount_in = TdBasicMethods.getMatchingStack(recipe.getInput(), stack).getCount();
		
		TdBasicMethods.reduceStack(stack, amount_in);
		
		String message = TdBasicMethods.getTranslation("message.threedee.artefact_analyzer.start");
		int seconds = recipe.ticksToCraft() / 20;
		int minutes = seconds / 60;
		seconds -= 60 * minutes;
		message = message.replace("MARKER1", minutes + "");
		message = message.replace("MARKER2", seconds + "");
		
		TdBasicMethods.messagePlayerCustom(player, message);
		setChanged();
		
		return true;
	}
	
	public void retreiveOutput() {
		if(running)
			return;
		
		TdBasicMethods.spawn(itemhandler.extractItem(1, 1, false), level, worldPosition, 0, 1, 0);
		TdBasicMethods.playSound(level, worldPosition, SoundEvents.PLAYER_LEVELUP);
	}
	
	@Nullable
	private ArtefactAnalyzerRecipe getRecipe(ItemStack stack) {
		if (stack == null) {
			return null;
		}
		
		if(last_recipe != null && !last_recipe.equals("")) {
			ArtefactAnalyzerRecipe recipe = (ArtefactAnalyzerRecipe) TdBasicMethods.getRecipe(last_recipe, level);
			
			if(recipe != null && recipe.matches(TdBasicMethods.getWrapper(stack), level)) 
				return recipe;
		}

		Set<Recipe<?>> recipes = TdBasicMethods.findRecipesByType(ArtefactAnalyzerRecipe.Type.INSTANCE, level);
		
		for (Recipe<?> Recipe : recipes) {
			ArtefactAnalyzerRecipe recipe = (ArtefactAnalyzerRecipe) Recipe;
			
			if (recipe.matches(TdBasicMethods.getWrapper(stack), level)) {
				return recipe;
			}
		}

		return null;
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
				
				if(isItemValid(slot, stack) && canInsertItem(stack, simulate)) {
					ItemStack result = super.insertItem(slot, stack, simulate);
					setRunning(!(simulate || result.equals(stack, false)));
					
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

			        ItemStack existing = this.stacks.get(slot);

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
			                this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
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
			running = tag.getBoolean("running");
			item_can_be_sold = tag.getBoolean("item_can_be_sold");
			current_time = tag.getInt("currenttime");
			ticks_to_craft = tag.getInt("tickstocraft");
			last_recipe = tag.getString("lastrecipe");
			loot_table = tag.getString("loottable");
			
			if(tag.contains("inventory"))
				itemhandler.deserializeNBT(tag.getCompound("inventory"));
		}
	}
	
	public void tick() {
		if (level.isClientSide() || !running)
			return;
		
		if (current_time >= ticks_to_craft) {
			
			current_time = 0;
			
			List<ItemStack> outputs = TdBasicMethods.genFromLootTable(level, loot_table);
			ItemStack output = ItemStack.EMPTY;
			
			if(outputs.size() > 0)
				output = outputs.get(0); // only one output allowed
			
			running = false;
			itemhandler.extractItem(-1, 1, false);
			itemhandler.insertItem(-1, output, false);
			TdBasicMethods.playSound(level, worldPosition, SoundEvents.BEACON_DEACTIVATE);
			
		} else {
			current_time++;
			
			if(current_time % 100 == 0 && current_time < ticks_to_craft - 100)
				TdBasicMethods.playSound(level, worldPosition, SoundEvents.BEACON_AMBIENT);
		}
		
		setChanged();
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
	}
}