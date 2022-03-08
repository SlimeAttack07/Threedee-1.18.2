package slimeattack07.threedee.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class HeadBlockTE extends BlockEntity {
	public boolean can_be_sold = true;
	public boolean prices_known;
	public int min;
	public int max;

	public HeadBlockTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.HEAD_BLOCK.get(), pos, state);
	}
	
	public void setData(boolean item_can_be_sold, boolean item_prices_known, int min_value, int max_value) {
		can_be_sold = item_can_be_sold;
		prices_known = item_prices_known;
		min = min_value;
		max = max_value;
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
			this.can_be_sold = tag.getBoolean("can_be_sold");
			this.prices_known = tag.getBoolean("prices_known");
			this.min = tag.getInt("min");
			this.max = tag.getInt("max");
		}
	}
}