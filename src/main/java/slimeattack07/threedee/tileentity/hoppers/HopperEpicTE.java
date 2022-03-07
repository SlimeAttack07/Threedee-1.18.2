package slimeattack07.threedee.tileentity.hoppers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.init.TDTileEntityTypes;

public class HopperEpicTE extends HopperCommonTE{

	public HopperEpicTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.EPIC_HOPPER.get(), pos, state);
	}

	@Override
	protected DropRarity getFilter() {
		return DropRarity.EPIC;
	}
}
