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

public class BlockBaseRotatable extends Block {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	private BlockBaseRotatable(Properties prop) {
		super(prop);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	public static BlockBaseRotatable create(Material mat, float hard, float resist, SoundType sound, boolean tool) {
		Properties prop = Block.Properties.of(mat).strength(hard, resist).sound(sound);
		
		return tool ? new BlockBaseRotatable(prop.requiresCorrectToolForDrops()) : new BlockBaseRotatable(prop);
	}
	
	public static BlockBaseRotatable create(Material mat, SoundType sound, boolean tool) {
		Properties prop = Block.Properties.of(mat).strength(0.6f, 3.0f).sound(sound);
		
		return tool ? new BlockBaseRotatable(prop.requiresCorrectToolForDrops()) : new BlockBaseRotatable(prop);
	}
	
	public static BlockBaseRotatable create(Block block, boolean tool) {
		Properties prop = Block.Properties.copy(block);
		
		return tool ? new BlockBaseRotatable(prop.requiresCorrectToolForDrops()) : new BlockBaseRotatable(prop);
	}
}