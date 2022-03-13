package slimeattack07.threedee.tileentity;

import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.recipes.HeadFabricatorRecipe;
import slimeattack07.threedee.util.TdBasicMethods;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class HeadFabricatorTE extends BlockEntity{
	public boolean stackmode;
	public String last_recipe = "";
	public String loot_table = "";
	public int time_per_shot;
	public int current_time;
	
	private final int INC_TIME_STEP = 5;
	private final int MAX_TIME = 20 + INC_TIME_STEP;
	
	private final ItemStackHandler itemhandler = createHandler();
	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemhandler);

	public HeadFabricatorTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.HEAD_FABRICATOR.get(), pos, state);
	}
	
	public ItemStackHandler getHandler() {
		return itemhandler;
	}
	
	public boolean getMode() {
		return stackmode;
	}
	
	public int getTPS() {
		return time_per_shot;
	}
	
	public void nextTimeStep() {
		time_per_shot = (time_per_shot + INC_TIME_STEP) % MAX_TIME;
	}
	
	public int getCatalystAmount() {
		return itemhandler.getStackInSlot(0).getCount();
	}
	
	public void setLastRecipe(String recipe) {
		last_recipe = recipe;
		setChanged();
	}
	
	public boolean toggleMode() {
		stackmode = !stackmode;
		return stackmode;
	}
	
	public void setLootTable(String loottable) {
		loot_table = loottable;
		setChanged();
	}
	
	public Vec3 genRandMotion(Random rand) {
		double x = ThreadLocalRandom.current().nextDouble(0, 0.2D);
		double y = ThreadLocalRandom.current().nextDouble(0, 0.2D);
		double neg_x = rand.nextInt(2);
		double neg_y = rand.nextInt(2);
		
		if(neg_x == 0)
			x = 0D - x;
		
		if(neg_y == 0)
			y = 0D - y;
		
		return new Vec3(x, 1D, y);
	}
	
	public void decreaseCatalyst() {
		itemhandler.extractItem(-1, 1, false);
	}
	
	public boolean addedCatalyst(ItemStack input) {
		HeadFabricatorRecipe recipe = getRecipe(input);
		
		// Recipe not null and either no stored catalyst or current recipe matches last recipe.
		boolean can_craft = recipe != null && (!(getCatalystAmount() > 0) || recipe.getId().toString().equals(last_recipe));
		
		if(!can_craft)
			return false;
		
		setLastRecipe(recipe.getId().toString());
		setLootTable(recipe.getLootTable());
		int amount_in = TdBasicMethods.getMatchingStack(recipe.getInput(), input).getCount();
		int times = 1;
		
		if (stackmode)
			times = input.getCount() / amount_in;
		
		ItemStack copy = input.copy();
		TdBasicMethods.reduceStack(input, amount_in * times);
		copy.setCount(times);
		itemhandler.insertItem(0, copy, false);
		
		return true;
	}
	
	public boolean canInsertItem(ItemStack input, boolean simulate) {
		HeadFabricatorRecipe recipe = getRecipe(input);
		
		// Recipe not null and either no stored catalyst or current recipe matches last recipe.
		boolean can_craft = recipe != null && (!(getCatalystAmount() > 0) || recipe.getId().toString().equals(last_recipe));
		
		if(!can_craft)
			return false;
		
		if(simulate) {
			setLastRecipe(recipe.getId().toString());
			setLootTable(recipe.getLootTable());
		}
		
		return true;
	}
	
	public void dropInventory() {
		TdBasicMethods.spawn(itemhandler.getStackInSlot(0), level, worldPosition, 0.5D, 0.5D, 0.5D);
	}
	
	@Nullable
	private HeadFabricatorRecipe getRecipe(ItemStack stack) {
		if (stack == null) {
			return null;
		}
		
		if(last_recipe != null && !last_recipe.equals("")) {
			HeadFabricatorRecipe recipe = (HeadFabricatorRecipe) TdBasicMethods.getRecipe(last_recipe, level);
			
			if(recipe != null && recipe.matches(TdBasicMethods.getWrapper(stack), level)) 
				return recipe;
		}

		Set<Recipe<?>> recipes = TdBasicMethods.findRecipesByType(HeadFabricatorRecipe.Type.INSTANCE, level);
		
		for (Recipe<?> Recipe : recipes) {
			HeadFabricatorRecipe recipe = (HeadFabricatorRecipe) Recipe;
			
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
		return new ItemStackHandler(1){
			@Override
			protected void onContentsChanged(int slot){
				setChanged();
			}

			// Only allow items that are already present.
			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack){
				ItemStack slotstack = getStackInSlot(slot);
				
				return stack.getItem().equals(slotstack.getItem()) || slotstack.isEmpty();
			}

			// Validate here again in case other mods somehow skip isItemValid(int, ItemStack)
			@Nonnull
			@Override
			public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
				return isItemValid(slot, stack) && canInsertItem(stack, simulate) ? super.insertItem(slot, stack, simulate) : stack;
			}
			
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				return slot < 0 ? super.extractItem(0, amount, simulate) : ItemStack.EMPTY;
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
			stackmode = tag.getBoolean("stackmode");
			time_per_shot = tag.getInt("timepershot");
			current_time = tag.getInt("currenttime");
			last_recipe = tag.getString("lastrecipe");
			loot_table = tag.getString("loottable");
			
			if(tag.contains("inventory"))
				itemhandler.deserializeNBT(tag.getCompound("inventory"));
		}
	}
	
	public void tick() {		
		if (level.isClientSide() || getCatalystAmount() <= 0)
			return;
		
		if (current_time >= time_per_shot) {
			current_time = 0;
			decreaseCatalyst();
			
			Random rand = new Random();
			List<ItemStack> outputs = TdBasicMethods.genFromLootTable(level, loot_table);
			
			for(ItemStack output: outputs)
				TdBasicMethods.spawn(output, level, worldPosition, 0.5D, 1.6D, 0.5D, genRandMotion(rand));
			
			TdBasicMethods.playSound(level, worldPosition, SoundEvents.GENERIC_EXPLODE);
		} else 
			current_time++;
		
		setChanged();
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
	}
}