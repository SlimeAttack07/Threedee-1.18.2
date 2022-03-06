package slimeattack07.threedee.tileentity;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.util.TdBasicMethods;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class ArtefactAnalyzerTE extends BlockEntity{
	public boolean running;
	public boolean item_can_be_sold = true;
	public String last_recipe = "";
	public String loot_table = "";
	public int ticks_to_craft;
	public int current_time;
	public ItemStack result = ItemStack.EMPTY;

	public ArtefactAnalyzerTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.TD_ARTEFACTANALYZER.get(), pos, state);
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
	
	public ItemStack getResult() {
		return result;
	}
	
	public void emptyMachine() {
		result = ItemStack.EMPTY;
	}
	
	public boolean hasOutput() {
		return !result.equals(ItemStack.EMPTY);
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
			result = (ItemStack) NBTHelper.fromNBT(tag.getCompound("result"));
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
			
			result = output;
			
			TdBasicMethods.playSound(level, worldPosition, SoundEvents.BEACON_DEACTIVATE);
			running = false;
		} else {
			current_time++;
			
			if(current_time % 100 == 0 && current_time < ticks_to_craft - 100)
				TdBasicMethods.playSound(level, worldPosition, SoundEvents.BEACON_AMBIENT);
		}
		
		setChanged();
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
	}
}