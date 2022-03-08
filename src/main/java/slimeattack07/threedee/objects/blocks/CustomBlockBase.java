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
import slimeattack07.threedee.util.helpers.VoxelShapesHelper;

public abstract class CustomBlockBase extends Block {
	private final int SHAPE_TYPE;

	public CustomBlockBase(int shape_type, int prop_type) {
		super(PropertiesHelper.create(prop_type, false));
		SHAPE_TYPE = shape_type;
	}
	
	public CustomBlockBase(int shape_type, int prop_type, boolean tool) {
		super(PropertiesHelper.create(prop_type, tool));
		SHAPE_TYPE = shape_type;
	}
	
	public Direction getDirection(BlockState state) {
		return Direction.NORTH;
	}
	
	public boolean isBasedOnHead() {
		return false;
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
		return getVoxelShape(state);
	}
	
	public VoxelShape getVoxelShape(BlockState state) {
		return VoxelShapesHelper.getVoxelFromType(SHAPE_TYPE, getDirection(state));
	}
}