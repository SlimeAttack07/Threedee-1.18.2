package slimeattack07.threedee.objects.blocks;

import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.objects.items.TokenCard;
import slimeattack07.threedee.recipes.ItemExchangerRecipe;
import slimeattack07.threedee.tileentity.ItemExchangerTE;
import slimeattack07.threedee.util.TdBasicMethods;

public class ItemExchanger extends InteractBlock {

	public ItemExchanger() {
		super(Properties.of(Material.STONE).strength(0.4f, 2.0f).sound(SoundType.STONE).
				requiresCorrectToolForDrops());
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return TDTileEntityTypes.TD_ITEMEXCHANGER.get().create(pos, state);
	}
	
	@Override
	public void inform(Player player, BlockEntity tile) {
		ItemExchangerTE te = (ItemExchangerTE) tile;
		
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
					balance = " " + TdBasicMethods.getTranslation("misc.threedee.exchangers.corrupt");
					
				TdBasicMethods.messagePlayerBack(player, "message.threedee.exchangers.balance", balance);
				return;
			}
		}
			
		TdBasicMethods.messagePlayer(player, "message.threedee.exchangers.no_card");
		return;
	}
	
	@Override
	public int valAndCalc(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		ItemExchangerTE te = (ItemExchangerTE) tile;
		
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
		
		ItemExchangerRecipe recipe = getRecipe(player, main, level, te.last_recipe);
		
		if(recipe == null) {
			TdBasicMethods.messagePlayer(player, "message.threedee.item_exchanger.invalid");
			return 0;
		}
		
		te.setLastRecipe(recipe.getId().toString());
		
		
		if(recipe.getCount() > main.getCount()) {
			String message = TdBasicMethods.getTranslation("message.threedee.item_exchanger.more");
			message = message.replace("MARKER1", recipe.getCount() + "");
			TdBasicMethods.messagePlayerCustom(player, message);
			return 0;
		}
		
		if(te.mininumBalance(recipe.getTokens())) {
			te.updateCard(-1 * recipe.getTokens());
			
			if(recipe.consume()) {
				TdBasicMethods.reduceStack(main, recipe.getCount());
			}
			
			TdBasicMethods.addOrSpawn(player, recipe.getResultItem(), level, pos);
			
			String message = TdBasicMethods.getTranslation("message.threedee.item_exchanger.exchange");
			message = message.replace("MARKER1", recipe.getTokens() + "");
			TdBasicMethods.messagePlayerCustom(player, message);
			
			return 1;
		}
		else {
			String message = TdBasicMethods.getTranslation("message.threedee.item_exchanger.no_bal");
			message = message.replace("MARKER1", recipe.getTokens() - te.getBalance() + "");
			TdBasicMethods.messagePlayerCustom(player, message);
			
			return 0;
		}
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
		return tile instanceof ItemExchangerTE;
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
				ItemExchangerTE te = (ItemExchangerTE) tile;
				
				if(te.hasCard())
					TdBasicMethods.spawn(te.extractCard(), level, pos, 0.5, 1, 0.5);
				
				level.removeBlockEntity(pos);
			}
		}
	}
	

	@Nullable
	private ItemExchangerRecipe getRecipe(Player player, ItemStack stack, Level level, String last_recipe) {
		if (stack == null) {
			return null;
		}
		
		if(last_recipe != null && !last_recipe.equals("")) {
			ItemExchangerRecipe recipe = (ItemExchangerRecipe) TdBasicMethods.getRecipe(last_recipe, level);
			
			if(recipe != null && recipe.matches(TdBasicMethods.getWrapper(stack), level)) 
				return recipe;
		}

		Set<Recipe<?>> recipes = TdBasicMethods.findRecipesByType(ItemExchangerRecipe.Type.INSTANCE, level);
		
		for (Recipe<?> Recipe : recipes) {
			ItemExchangerRecipe recipe = (ItemExchangerRecipe) Recipe;
			
			if (recipe.matches(TdBasicMethods.getWrapper(stack), level)) {
				return recipe;
			}
		}

		return null;
	}
}