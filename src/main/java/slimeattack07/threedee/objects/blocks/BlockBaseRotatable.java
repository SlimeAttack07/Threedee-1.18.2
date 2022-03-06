package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;

public class BlockBaseRotatable extends BlockBase {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public BlockBaseRotatable(Material mat, float hard, float resist, SoundType sound) {
		super(mat, hard, resist, sound);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	public BlockBaseRotatable(Material mat, SoundType sound) {
		super(mat, sound);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	public BlockBaseRotatable(Block block) {
		super(block);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}