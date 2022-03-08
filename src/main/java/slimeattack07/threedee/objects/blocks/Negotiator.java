package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import slimeattack07.threedee.Threedee;
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
		if (te.input == null || te.input.equals(ItemStack.EMPTY)) {
			TdBasicMethods.messagePlayer(player, "message.threedee.machine_empty");
			return;
		}
		
		if(!te.running) {
			TdBasicMethods.addOrSpawn(player, te.input, tile.getLevel(), tile.getBlockPos());
			te.clearInput();
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
	public int valAndCalc(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		NegotiatorTE te = (NegotiatorTE) tile;
	
		if(te.running) {
			TdBasicMethods.messagePlayer(player, "message.threedee.machine_full");
			return 0;
		}
		
		boolean retrieved_output = false;
		
		if(te.input != null && !te.getOutput().equals(ItemStack.EMPTY)) {
			TdBasicMethods.addOrSpawn(player, te.input, level, pos);
			te.clearInput();
			retrieved_output = true;
		}
		
		CompoundTag nbt = main.getTag();
		if(nbt != null && nbt.contains(Threedee.MOD_ID)) {
			CompoundTag threedee = nbt.getCompound(Threedee.MOD_ID);
			boolean can_be_sold = threedee.getBoolean("can_be_sold");
			boolean prices_known = threedee.getBoolean("prices_known");
			
			if(can_be_sold && !prices_known) {
				ItemStack stack = new ItemStack(main.getItem(), 1);
				stack.setTag(nbt);
				te.activate(stack.copy());
				TdBasicMethods.reduceStack(main, 1);
				
				int seconds = NegotiatorTE.NEG_TIME / 20;
				int minutes = seconds / 60;
				seconds -= 60 * minutes;
				
				String message = TdBasicMethods.getTranslation("message.threedee.negotiator.start");
				message = message.replace("MARKER1", minutes + "");
				message = message.replace("MARKER2", seconds + "");
				
				TdBasicMethods.messagePlayerCustom(player, message);
				return 1;
			}
			
			
			if(!retrieved_output) {
				if(prices_known)
					TdBasicMethods.messagePlayer(player, "message.threedee.negotiator.already_known");
				else
					TdBasicMethods.messagePlayer(player, "message.threedee.negotiator.invalid");
			}
				
			
			return 0;
		}
		
		if(!retrieved_output)
			TdBasicMethods.messagePlayer(player, "message.threedee.negotiator.invalid");
		
		return 0;
	}

	@Override
	public void playEffects(Level level, BlockPos pos) {
		TdBasicMethods.playSound(level, pos, SoundEvents.VILLAGER_YES);
	}

	@Override
	public void craft(int amount, BlockEntity tile, Player player, BlockPos pos, Level level) {
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
				
				if(te.input != null && !te.input.equals(ItemStack.EMPTY)) 
					TdBasicMethods.spawn(te.input, level, pos, 0.5D, 0.5D, 0.5D);
				
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