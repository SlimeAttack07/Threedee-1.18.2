package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import slimeattack07.threedee.init.TDTileEntityTypes;

public abstract class InteractBlock extends CustomBlockBase implements EntityBlock {
	
	public InteractBlock (int shape_type, int prop_type) {
		super(shape_type, prop_type, true);
	}
	
	public abstract void validateAndCraft(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos);
	
	public abstract boolean checkTileEnt(BlockEntity tile);
	
	public abstract void toggleMode(BlockEntity tile, Player player);
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return TDTileEntityTypes.BASIC_INTERACT.get().create(pos, state);
	}
	
	public void inform(Player player, BlockEntity tile) {
		return;
	}
	
	public double xOffset() {
		return 0.5D;
	}
	
	public double yOffset() {
		return 1D;
	}
	
	public double zOffset() {
		return 0.5D;
	}
	
	// Player right-click on block
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn,
			BlockHitResult hit) {
		
		if(level.isClientSide())
			return InteractionResult.SUCCESS;
		
		ItemStack main = player.getMainHandItem();
		ItemStack off = player.getOffhandItem();
		
		BlockEntity tile = level.getBlockEntity(pos);
		if (!checkTileEnt(tile))
			return InteractionResult.SUCCESS;
		
		if (main.isEmpty()) {
			inform(player, tile);
			
			return InteractionResult.SUCCESS;
		}
		
		validateAndCraft(player, main, off, tile, level, pos);

		return InteractionResult.SUCCESS;
	}
	
	@Override
	public void attack(BlockState state, Level level, BlockPos pos, Player player) {
		
		if(level.isClientSide())
			return;
		if(!player.getMainHandItem().isEmpty())
			return;
		
		BlockEntity tile = level.getBlockEntity(pos);
		if (!checkTileEnt(tile))
			return;
		
		toggleMode(tile, player);
	}
	
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
			BlockEntity tile = level.getBlockEntity(pos);
			
			if (checkTileEnt(tile)) {
				level.removeBlockEntity(pos);
			}
		}
	}
}