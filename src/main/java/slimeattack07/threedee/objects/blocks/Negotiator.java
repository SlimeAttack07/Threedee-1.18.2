package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.tileentity.NegotiatorTE;
import slimeattack07.threedee.util.TdBasicMethods;

public class Negotiator extends InteractBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public Negotiator() {
		super(12, -1);
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

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return TDTileEntityTypes.NEGOTIATOR.get().create(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return level.isClientSide() ? null : (l, s, pos, tile) -> ((NegotiatorTE) tile).tick();
	}
	
	@Override
	public void inform(Player player, BlockEntity tile) {
		NegotiatorTE te = (NegotiatorTE) tile;
		boolean output = false;
		
		if(te.hasOutput()) {
			te.retreiveOutput(player);
			output = true;
		}
		
		if (!te.hasInput()) {
			if(!output)
				TdBasicMethods.messagePlayer(player, "message.threedee.machine_empty");
			
			return;
		}		
		else {
			int time_remaining = NegotiatorTE.NEG_TIME - te.current_time;
			int seconds = time_remaining / 20;
			int minutes = seconds / 60;
			seconds -= 60 * minutes;
			
			String message = TdBasicMethods.getTranslation("message.threedee.negotiator.time");
			message = message.replace("MARKER1", minutes + "");
			message = message.replace("MARKER2", seconds + "");
			
			TdBasicMethods.messagePlayerCustom(player, message);
		}
	}
	
	@Override
	public int validateAndCraft(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		NegotiatorTE te = (NegotiatorTE) tile;
	
		if(te.running) {
			TdBasicMethods.messagePlayer(player, "message.threedee.machine_full");
			return 0;
		}
		
		return te.canInsertItem(main, player) ? 1 : 0;
	}

	@Override
	public void playEffects(Level level, BlockPos pos) {
		TdBasicMethods.playSound(level, pos, SoundEvents.VILLAGER_YES);
	}

	@Override
	public boolean checkTileEnt(BlockEntity tile) {
		return tile instanceof NegotiatorTE;
	}

	@Override
	public void toggleMode(BlockEntity tile, Player player) {
	}
	
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
			BlockEntity tile = level.getBlockEntity(pos);
			
			if (checkTileEnt(tile)) {
				NegotiatorTE te = (NegotiatorTE) tile;
				te.dropItems();
				
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
			NegotiatorTE te = (NegotiatorTE) tile;
			
			if(!te.running || NegotiatorTE.NEG_TIME == 0)
				return 0;
			
			return (int) Math.ceil(15 - (15D / (double) NegotiatorTE.NEG_TIME) * te.current_time);
		}
		
		return 0;
	}
}