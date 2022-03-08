package slimeattack07.threedee.objects.blocks;

import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.recipes.HeadFabricatorRecipe;
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
		TdBasicMethods.messagePlayerBack(player, "message.threedee.remaining_catalyst", " " + te.catalyst_amount);
	}
	
	@Override
	public int valAndCalc(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		HeadFabricatorTE te = (HeadFabricatorTE) tile;
		boolean stackmode = te.getMode();
		HeadFabricatorRecipe recipe = getRecipe(player, main, level, te.last_recipe);
		
		if (te.catalyst_amount > 0 && !recipe.getId().toString().equals(te.last_recipe)) {
			TdBasicMethods.messagePlayerBack(player, "message.threedee.still_firing", " " + te.catalyst_amount + "x (" + te.last_recipe + ")");
			return 0;
		}
		
		if(recipe == null)
			return 0;
		
		te.setLastRecipe(recipe.getId().toString());
		te.setLootTable(recipe.getLootTable());
		int amount_in = TdBasicMethods.getMatchingStack(recipe.getInput(), main).getCount();
		int times = 1;
		
		if (stackmode)
			times = main.getCount() / amount_in;
		
		TdBasicMethods.reduceStack(main, amount_in * times);
		te.addCatalyst(times);
		
		return times;
	}

	@Override
	public void playEffects(Level level, BlockPos pos) {
	}

	@Override
	public void craft(int amount, BlockEntity tile, Player player, BlockPos pos, Level level) {
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
				
				if(te.catalyst_amount == 0)
					return;
				
				HeadFabricatorRecipe recipe = (HeadFabricatorRecipe) TdBasicMethods.getRecipe(te.last_recipe, level);
				ItemStack stack = recipe.getInput().getItems()[0]; // safe since this block only accepts single item type recipes
				stack.setCount(stack.getCount() * te.catalyst_amount);
				
				TdBasicMethods.spawn(stack, level, pos, 0.5D, 0.5D, 0.5D);
			
				level.removeBlockEntity(pos);
			}
		}
	}
	
	@Nullable
	private HeadFabricatorRecipe getRecipe(Player player, ItemStack stack, Level level, String last_recipe) {
		if (stack == null) {
			return null;
		}
		
		if(last_recipe != null && !last_recipe.equals("")) {
			HeadFabricatorRecipe recipe = (HeadFabricatorRecipe) TdBasicMethods.getRecipe(last_recipe, level);
			
			if(recipe != null && recipe.matches(TdBasicMethods.getWrapper(stack), level)) 
				return recipe;
		}

		Set<Recipe<?>> recipes = TdBasicMethods.findRecipesByType(HeadFabricatorRecipe.Type.INSTANCE, level);
		
		for (Recipe<?> Recipe : recipes) {
			HeadFabricatorRecipe recipe = (HeadFabricatorRecipe) Recipe;
			
			if (recipe.matches(TdBasicMethods.getWrapper(stack), level)) {
				return recipe;
			}
		}

		return null;
	}
}