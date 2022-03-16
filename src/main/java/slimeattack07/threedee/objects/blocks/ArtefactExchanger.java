package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.objects.items.TokenCard;
import slimeattack07.threedee.tileentity.ArtefactExchangerTE;
import slimeattack07.threedee.util.TdBasicMethods;

public class ArtefactExchanger extends InteractBlock {

	public ArtefactExchanger() {
		super(13, -1);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return TDTileEntityTypes.ARTEFACT_EXCHANGER.get().create(pos, state);
	}
	
	@Override
	public void inform(Player player, BlockEntity tile) {
		ArtefactExchangerTE te = (ArtefactExchangerTE) tile;
		
		if(te.hasCard()) {
			if(player.isCrouching()) {
				TdBasicMethods.addOrSpawn(player, te.extractCard(), tile.getLevel(), tile.getBlockPos());
				return;
			}
			else {
				int bal = TokenCard.getBalance(te.card);
				String balance = " " + bal;
				
				if(bal == -1)
					balance = " " + TdBasicMethods.getTranslation("misc.threedee.infinite");
				
				if (bal < -1)
					balance = " " + TdBasicMethods.getTranslation("misc.threedee.exchangers_corrupt");
					
				TdBasicMethods.messagePlayerBack(player, "message.threedee.exchangers.balance", balance);
				return;
			}
		}
			
		TdBasicMethods.messagePlayer(player, "message.threedee.exchangers.no_card");
		return;
	}
	
	@Override
	public void validateAndCraft(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		ArtefactExchangerTE te = (ArtefactExchangerTE) tile;
		
		if (main.getItem() instanceof TokenCard) {
			if(te.hasCard()) {
				TdBasicMethods.messagePlayer(player, "message.threedee.exchangers.has_card");
				
				return;
			}
			else {
				te.setCard(main.copy());
				TdBasicMethods.reduceStack(main, 1);
				
				return;
			}
		}
		
		te.addInput(main, player);
	}

	@Override
	public boolean checkTileEnt(BlockEntity tile) {
		return tile instanceof ArtefactExchangerTE;
	}

	@Override
	public void toggleMode(BlockEntity tile, Player player) {
	}
	
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
			BlockEntity tile = level.getBlockEntity(pos);
			
			if (checkTileEnt(tile)) {
				ArtefactExchangerTE te = (ArtefactExchangerTE) tile;
				
				if(te.hasCard())
					TdBasicMethods.spawn(te.extractCard(), level, pos, 0.5, 1, 0.5);
				
				level.removeBlockEntity(pos);
			}
		}
	}
}