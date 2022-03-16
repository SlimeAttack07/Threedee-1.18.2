package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.tileentity.HeadFabricatorTE;
import slimeattack07.threedee.util.TdBasicMethods;

public class HeadFabricator extends InteractBlock {

	public HeadFabricator() {
		super(5, -1);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return TDTileEntityTypes.HEAD_FABRICATOR.get().create(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return level.isClientSide() ? null : (l, s, pos, tile) -> ((HeadFabricatorTE) tile).tick();
	}
	
	@Override
	public void inform(Player player, BlockEntity tile) {
		HeadFabricatorTE te = (HeadFabricatorTE) tile;
		TdBasicMethods.messagePlayerBack(player, "message.threedee.remaining_catalyst", " " + te.getCatalystAmount());
	}
	
	@Override
	public void validateAndCraft(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		HeadFabricatorTE te = (HeadFabricatorTE) tile;		
		boolean message = !te.addedInput(main) && te.getCatalystAmount() > 0;
		
		if (message)
			TdBasicMethods.messagePlayerBack(player, "message.threedee.still_firing", " " + te.getCatalystAmount() + "x (" + te.last_recipe + ")");
	}


	@Override
	public boolean checkTileEnt(BlockEntity tile) {
		return tile instanceof HeadFabricatorTE;
	}

	@Override
	public void toggleMode(BlockEntity tile, Player player) {
		HeadFabricatorTE te = (HeadFabricatorTE) tile;
		
		if (player.isCrouching()) {
			te.nextTimeStep();
			double speed = te.getTPS() /20D;
			TdBasicMethods.messagePlayerBack(player, "message.threedee.headfab_speed", " " + speed + "s");
			return;
		}
		
		boolean mode = te.toggleMode();
		String value = String.valueOf(mode);
		
		TdBasicMethods.messagePlayer(player, "message.threedee.stackmode_" + value);
	}
	
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
			BlockEntity tile = level.getBlockEntity(pos);
			
			if (tile instanceof HeadFabricatorTE) {
				HeadFabricatorTE te = (HeadFabricatorTE) tile;
				te.dropInventory();
			
				level.removeBlockEntity(pos);
			}
		}
	}
	
	
}