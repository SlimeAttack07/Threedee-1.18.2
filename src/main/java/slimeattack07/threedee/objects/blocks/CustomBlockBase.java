package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import slimeattack07.threedee.util.helpers.PropertiesHelper;

public abstract class CustomBlockBase extends Block {
	private final VoxelShape SHAPE;

	public CustomBlockBase(VoxelShape shape, int prop_type) {
		super(PropertiesHelper.create(prop_type, false));
		SHAPE = shape;
	}
	
	public boolean isTranslucent() {
		return false;
	}
	
	public CustomBlockBase(VoxelShape shape, int prop_type, boolean tool) {
		super(PropertiesHelper.create(prop_type, tool));
		SHAPE = shape;
	}
	
	public Direction getDirection(BlockState state) {
		return Direction.NORTH;
	}

	@Override
	public boolean isValidSpawn(BlockState state, BlockGetter world, BlockPos pos, Type type,
			EntityType<?> entityType) {
		return false;
	}
	
	@Override
	public boolean isPossibleToRespawnInThis() {
		return true;
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter getter, BlockPos pos) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}