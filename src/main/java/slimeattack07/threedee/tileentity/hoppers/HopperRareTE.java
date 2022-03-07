package slimeattack07.threedee.tileentity.hoppers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.init.TDTileEntityTypes;

public class HopperRareTE extends HopperCommonTE{

	public HopperRareTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.RARE_HOPPER.get(), pos, state);
	}

	@Override
	protected DropRarity getFilter() {
		return DropRarity.RARE;
	}
}
