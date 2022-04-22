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
import slimeattack07.threedee.tileentity.ArtefactAnalyzerTE;
import slimeattack07.threedee.util.TdBasicMethods;
import slimeattack07.threedee.util.helpers.ModShapes;

public class ArtefactAnalyzer extends InteractBlock {

	public ArtefactAnalyzer() {
		super(ModShapes.W16_H16, -1);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return TDTileEntityTypes.ARTEFACT_ANALYZER.get().create(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return level.isClientSide() ? null : (l, s, pos, tile) -> ((ArtefactAnalyzerTE) tile).tick();
	}
	
	@Override
	public void inform(Player player, BlockEntity tile) {
		ArtefactAnalyzerTE te = (ArtefactAnalyzerTE) tile;
		
		if(!te.running) {
			if(te.hasOutput()) {
				te.retreiveOutput();
				return;
			}
			
			TdBasicMethods.messagePlayer(player, "message.threedee.machine_empty");
			return;
		}
		
		int time_remaining = te.ticks_to_craft - te.current_time;
		int seconds = time_remaining / 20;
		int minutes = seconds / 60;
		seconds -= 60 * minutes;
		
		String message = TdBasicMethods.getTranslation("message.threedee.artefact_analyzer.analyze_time");
		message = message.replace("MARKER1", minutes + "");
		message = message.replace("MARKER2", seconds + "");
		
		TdBasicMethods.messagePlayerCustom(player, message);
	}
	
	@Override
	public void validateAndCraft(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		ArtefactAnalyzerTE te = (ArtefactAnalyzerTE) tile;
		
		if (te.running) {
			TdBasicMethods.messagePlayer(player, "message.threedee.machine_full");
			
			return;
		}
		
		te.addInput(main, player);
	}

	@Override
	public boolean checkTileEnt(BlockEntity tile) {
		return tile instanceof ArtefactAnalyzerTE;
	}

	@Override
	public void toggleMode(BlockEntity tile, Player player) {
	}
	
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
			BlockEntity tile = level.getBlockEntity(pos);
			
			if (checkTileEnt(tile)) {
				ArtefactAnalyzerTE te = (ArtefactAnalyzerTE) tile;
				
				if(te.hasOutput())
					te.retreiveOutput();
				
				level.updateNeighbourForOutputSignal(pos, this);
				level.removeBlockEntity(pos);	
			}
		}
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
		BlockEntity tile = level.getBlockEntity(pos);
		if(checkTileEnt(tile)) {
			ArtefactAnalyzerTE te = (ArtefactAnalyzerTE) tile;
			
			if(!te.running || te.ticks_to_craft == 0)
				return 0;
			
			return (int) Math.ceil(15 - (15D / (double) te.ticks_to_craft) * te.current_time);
		}
		
		return 0;
	}
}