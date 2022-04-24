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
import slimeattack07.threedee.recipes.ModelRecyclerRecipe;
import slimeattack07.threedee.tileentity.ModelRecyclerTE;
import slimeattack07.threedee.util.TdBasicMethods;
import slimeattack07.threedee.util.helpers.ModShapes;

public class ModelRecycler extends InteractBlock {

	public ModelRecycler() {
		super(ModShapes.W16_H16, -1);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return TDTileEntityTypes.MODEL_RECYCLER.get().create(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return level.isClientSide() ? null : (l, s, pos, tile) -> ((ModelRecyclerTE) tile).tick();
	}
	
	@Override
	public void inform(Player player, BlockEntity tile) {
		ModelRecyclerTE te = (ModelRecyclerTE) tile;
		if (te.outputs == null || te.outputs.size() == 0) {
			TdBasicMethods.messagePlayer(player, "message.threedee.machine_empty");
			return;
		}
		
		TdBasicMethods.messagePlayer(player, "message.threedee.remaining_outputs");
		double total_rec_time = 0;
		
		for(int i = 0; i < te.outputs.size(); i++) {
			ItemStack itemstack = te.outputs.get(i);
			
			if(itemstack.equals(ItemStack.EMPTY))
				continue;
			
			int times = te.amounts.get(i);
			float rec_time = te.rec_times.get(i);
			total_rec_time += rec_time * times;
			
			String id = " (" + itemstack.getItem().getRegistryName().toString() + ")";
			String name = itemstack.getDisplayName().getString();
			String front = times + "x (" + itemstack.getCount() + "x " + name + id + ")";
			
			if(itemstack.getCount() == 1)
				front = times + "x " + name + id;
			
			String back = TdBasicMethods.getTranslation("message.threedee.recyle_time");
			
			TdBasicMethods.messagePlayerCustom(player, front + " (" + times * rec_time / 20.0 + " " + back + ")");
		}
		
		TdBasicMethods.messagePlayerFront(player, total_rec_time / 20.0 + " ", "message.threedee.total_recyle_time");
		
	}
	
	@Override
	public void validateAndCraft(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		ModelRecyclerTE te = (ModelRecyclerTE) tile;
		boolean stackmode = te.getMode();
		ModelRecyclerRecipe recipe = getRecipe(main, level, te.last_recipe);
		
		if(recipe == null)
			return;
		
		int amount_in = TdBasicMethods.getMatchingStack(recipe.getInput(), main).getCount();
		int times = 1;
		
		if(stackmode)
			times = main.getCount() / amount_in;
		
		te.setLastRecipe(recipe.getId().toString());
		
		if(te.updateItemstackInLists(recipe.getResultItem(), recipe.getCorruptedOutput(), recipe.ticksNeeded(), times)) {
			TdBasicMethods.reduceStack(main, amount_in * times);
			return;
		}
		
		TdBasicMethods.messagePlayer(player, "message.threedee.machine_full");
	}

	@Override
	public boolean checkTileEnt(BlockEntity tile) {
		return tile instanceof ModelRecyclerTE;
	}

	@Override
	public void toggleMode(BlockEntity tile, Player player) {
		ModelRecyclerTE te = (ModelRecyclerTE) tile;
		
		boolean mode = te.toggleMode();
		String value = String.valueOf(mode);
		
		TdBasicMethods.messagePlayer(player, "message.threedee.stackmode_" + value);
	}
	
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
			BlockEntity tile = level.getBlockEntity(pos);
			
			if (checkTileEnt(tile)) {
				ModelRecyclerTE te = (ModelRecyclerTE) tile;
				if(te.outputs != null) {
					for(int i = 0; i < te.cor_outputs.size(); i++) {
						ItemStack itemstack = te.cor_outputs.get(i);
						itemstack.setCount(itemstack.getCount() * te.amounts.get(i));
						TdBasicMethods.spawn(itemstack, level, pos, 0.5D, 0.5D, 0.5D);
					}
				}
				level.removeBlockEntity(pos);
			}
		}
	}
	
	@Nullable
	private ModelRecyclerRecipe getRecipe(ItemStack stack, Level level, String last_recipe) {
		if (stack == null) {
			return null;
		}
		
		if(last_recipe != null && !last_recipe.equals("")) {
			ModelRecyclerRecipe recipe = (ModelRecyclerRecipe) TdBasicMethods.getRecipe(last_recipe, level);
			
			if(recipe != null && recipe.matches(TdBasicMethods.getWrapper(stack), level)) 
				return recipe;
			
		}

		Set<Recipe<?>> recipes = TdBasicMethods.findRecipesByType(ModelRecyclerRecipe.Type.INSTANCE, level);
		
		for (Recipe<?> Recipe : recipes) {
			ModelRecyclerRecipe recipe = (ModelRecyclerRecipe) Recipe;
			
			if (recipe.matches(TdBasicMethods.getWrapper(stack), level)) {
				return recipe;
			}
		}

		return null;
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
		BlockEntity tile = level.getBlockEntity(pos);
		if(checkTileEnt(tile)) {
			ModelRecyclerTE te = (ModelRecyclerTE) tile;
			int total = 0;
			
			for(int i = 0; i < te.amounts.size(); i++) {
				if(te.amounts.get(i) > 0) {
					total += te.amounts.get(i) * te.rec_times.get(i);
				}
			}
			
			return (int) Math.ceil(15 - (15D / (double) total) * te.current_time);
		}
		
		return 0;
	}
}