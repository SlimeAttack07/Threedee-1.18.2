package slimeattack07.threedee.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class BasicInterTE extends BlockEntity {
	public boolean stackmode;
	boolean initialized = false;
	public String last_recipe;

	public BasicInterTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.TD_BASICINTER.get(), pos, state);
	}
	
	public boolean getMode() {
		return stackmode;
	}
	
	public String getLastRecipe() {
		return last_recipe;
	}
	
	public void setLastRecipe(String input) {
		last_recipe = input;
	}
	
	public boolean toggleMode() {
		stackmode = !stackmode;
		return stackmode;
	}
	
	private void init() {
		initialized = true;
		stackmode = false;
		last_recipe = "";
	}
	
	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.put("initvalues", NBTHelper.toNBT(this));
	}
	
	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		CompoundTag initvalues = compound.getCompound("initvalues");

		if(initvalues != null) {
			this.stackmode = initvalues.getBoolean("stackmode");
			this.last_recipe = initvalues.getString("lastrecipe");
			
			initialized = true;
		}
		else
			init();
	}
}