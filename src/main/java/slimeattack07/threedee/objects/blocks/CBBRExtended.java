package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class CBBRExtended extends CustomBlockBaseRotatable {
	private int type;
	
	public CBBRExtended(int typ) {
		super(Block.Properties.of(Material.BAMBOO).strength(0.4f, 2.0f).sound(SoundType.WOOL));
		type = typ;
	}

	@Override
	public int getType() {
		return type;
	}
	
	@Override
	public Direction getDirection(BlockState state) {
		return state.getValue(FACING);
	}
	
	@Override
	public boolean isBasedOnHead() {
		return true;
	}
}