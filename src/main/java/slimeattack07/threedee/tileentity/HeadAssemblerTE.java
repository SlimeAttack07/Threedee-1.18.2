package slimeattack07.threedee.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class HeadAssemblerTE extends BlockEntity {
	public boolean stackmode;
	public int dye_amount;
	public String last_recipe = "";

	public HeadAssemblerTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.HEAD_ASSEMBLER.get(), pos, state);
	}
	
	public boolean getMode() {
		return stackmode;
	}
	
	public int getDyeAmount() {
		return dye_amount;
	}
	
	public void setDyeAmount(int amount) {
		dye_amount = amount;
	}
	
	public void decDyeAmount(int amount) {
		int new_amount = dye_amount - amount;
		if (new_amount > 0)
			dye_amount = new_amount;
		else
			dye_amount = 0;
	}
	
	public void incDyeAmount(int amount) {
		dye_amount += amount;
	}
	
	public boolean toggleMode() {
		stackmode = !stackmode;
		return stackmode;
	}
	
	public String getLastRecipe() {
		return last_recipe;
	}
	
	public void setLastRecipe(String recipe) {
		last_recipe = recipe;
	}
	
	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.put(Threedee.MOD_ID, NBTHelper.toNBT(this));
		
	}
	
	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		CompoundTag tag = compound.getCompound(Threedee.MOD_ID);
		if(tag != null) {
			dye_amount = tag.getInt("dye");
			stackmode = tag.getBoolean("stackmode");
			last_recipe = tag.getString("lastrecipe");
		}
	}
}