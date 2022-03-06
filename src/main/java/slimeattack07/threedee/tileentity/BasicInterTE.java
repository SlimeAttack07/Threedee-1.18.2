package slimeattack07.threedee.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class BasicInterTE extends BlockEntity {
	public boolean stackmode;
	public String last_recipe = "";

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
	
	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.put(Threedee.MOD_ID, NBTHelper.toNBT(this));
	}
	
	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		CompoundTag initvalues = compound.getCompound(Threedee.MOD_ID);

		if(initvalues != null) {
			stackmode = initvalues.getBoolean("stackmode");
			last_recipe = initvalues.getString("lastrecipe");
		}
	}
}