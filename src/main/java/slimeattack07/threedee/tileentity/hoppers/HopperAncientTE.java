package slimeattack07.threedee.tileentity.hoppers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.init.TDTileEntityTypes;

public class HopperAncientTE extends HopperCommonTE{

	public HopperAncientTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.ANCIENT_HOPPER.get(), pos, state);
	}

	@Override
	protected DropRarity getFilter() {
		return DropRarity.ANCIENT;
	}
}
