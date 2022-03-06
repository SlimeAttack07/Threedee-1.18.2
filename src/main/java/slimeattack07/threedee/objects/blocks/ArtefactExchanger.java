package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.objects.items.TokenCard;
import slimeattack07.threedee.tileentity.ArtefactExchangerTE;
import slimeattack07.threedee.util.TdBasicMethods;

public class ArtefactExchanger extends InteractBlock {

	public ArtefactExchanger() {
		super(Properties.of(Material.STONE).strength(0.4f, 2.0f).sound(SoundType.STONE).
				requiresCorrectToolForDrops());
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return TDTileEntityTypes.TD_ARTEFACTEXCHANGER.get().create(pos, state);
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
	public int valAndCalc(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		ArtefactExchangerTE te = (ArtefactExchangerTE) tile;
		
		if (main.getItem() instanceof TokenCard) {
			if(te.hasCard()) {
				TdBasicMethods.messagePlayer(player, "message.threedee.exchangers.has_card");
				return 0;
			}
			else {
				te.setCard(main.copy());
				TdBasicMethods.reduceStack(main, 1);
				return 0;
			}
		}
		
		CompoundTag nbt = main.getTag();
		
		if(nbt != null && nbt.contains(Threedee.MOD_ID)) {
			CompoundTag threedee = nbt.getCompound(Threedee.MOD_ID);
			boolean can_be_sold = threedee.getBoolean("can_be_sold");
			boolean prices_known = threedee.getBoolean("prices_known");
			
			if(can_be_sold) {
				if(prices_known) {
					if(te.hasCard()) {
						int balance = threedee.getInt("price");
						te.updateCard(balance);
						TdBasicMethods.reduceStack(main, 1);
						
						String message = TdBasicMethods.getTranslation("message.threedee.artefact_exchanger.exchange");
						message = message.replace("MARKER1", balance + "");
						
						TdBasicMethods.messagePlayerCustom(player, message);
						return 1;
					}
					else {
						TdBasicMethods.messagePlayer(player, "message.threedee.exchangers.no_card");
						return 0;
					}
				}
				else {
					TdBasicMethods.messagePlayer(player, "message.threedee.artefact_exchanger.no_price");
					return 0;
				}
			}
			
			TdBasicMethods.messagePlayer(player, "message.threedee.artefact_exchanger.invalid");
			return 0;
		}
		
		TdBasicMethods.messagePlayer(player, "message.threedee.artefact_exchanger.invalid");
		return 0;
	}

	@Override
	public void playEffects(Level level, BlockPos pos) {
		TdBasicMethods.playSound(level, pos, SoundEvents.TOTEM_USE);
	}

	@Override
	public void craft(int amount, BlockEntity tile, Player player, BlockPos pos, Level level) {
	}

	@Override
	public boolean checkTileEnt(BlockEntity tile) {
		return tile instanceof ArtefactExchangerTE;
	}

	@Override
	public void toggleMode(BlockEntity tile, Player player) {
	}
	
	@Override
	public int getType() {
		return 13;
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