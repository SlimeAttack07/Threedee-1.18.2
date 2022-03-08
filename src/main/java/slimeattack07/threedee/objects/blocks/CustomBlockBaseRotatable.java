package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class CustomBlockBaseRotatable extends CustomBlockBase {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public CustomBlockBaseRotatable(int shape_type, int prop_type) {
		super(shape_type, prop_type);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}
	
	public CustomBlockBaseRotatable(int shape_type, int prop_type, boolean tool) {
		super(shape_type, prop_type, tool);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public Direction getDirection(BlockState state) {
		return state.getValue(FACING);
	}
}