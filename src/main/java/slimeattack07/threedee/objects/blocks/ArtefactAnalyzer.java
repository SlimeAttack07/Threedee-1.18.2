package slimeattack07.threedee.objects.blocks;

import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.recipes.ArtefactAnalyzerRecipe;
import slimeattack07.threedee.tileentity.ArtefactAnalyzerTE;
import slimeattack07.threedee.util.TdBasicMethods;

public class ArtefactAnalyzer extends InteractBlock {

	public ArtefactAnalyzer() {
		super(11, -1);
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
				retreiveOutput(te, te.getLevel());
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
	public int valAndCalc(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		ArtefactAnalyzerTE te = (ArtefactAnalyzerTE) tile;
		ArtefactAnalyzerRecipe recipe = getRecipe(player, main, level, te.last_recipe);
		
		if (te.running) {
			TdBasicMethods.messagePlayer(player, "message.threedee.machine_full");
			return 0;
		}
		
		if(te.hasOutput())
			retreiveOutput(te, level);
		
		if(recipe == null)
			return 0;
		
		te.setLastRecipe(recipe.getId().toString());
		te.setLootTable(recipe.getLootTable());
		te.setRunning(true);
		te.setTicksToCraft(recipe.ticksToCraft());
		int amount_in = TdBasicMethods.getMatchingStack(recipe.getInput(), main).getCount();
		
		TdBasicMethods.reduceStack(main, amount_in);
		
		String message = TdBasicMethods.getTranslation("message.threedee.artefact_analyzer.start");
		int seconds = recipe.ticksToCraft() / 20;
		int minutes = seconds / 60;
		seconds -= 60 * minutes;
		message = message.replace("MARKER1", minutes + "");
		message = message.replace("MARKER2", seconds + "");
		
		TdBasicMethods.messagePlayerCustom(player, message);
		
		return 1;
	}

	@Override
	public void playEffects(Level level, BlockPos pos) {
		TdBasicMethods.playSound(level, pos, SoundEvents.BEACON_ACTIVATE);
	}

	@Override
	public void craft(int amount, BlockEntity tile, Player player, BlockPos pos, Level level) {
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
					retreiveOutput(te, level);
				
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
	
	@Nullable
	private ArtefactAnalyzerRecipe getRecipe(Player player, ItemStack stack, Level level, String last_recipe) {
		if (stack == null) {
			return null;
		}
		
		if(last_recipe != null && !last_recipe.equals("")) {
			ArtefactAnalyzerRecipe recipe = (ArtefactAnalyzerRecipe) TdBasicMethods.getRecipe(last_recipe, level);
			
			if(recipe != null && recipe.matches(TdBasicMethods.getWrapper(stack), level)) 
				return recipe;
		}

		Set<Recipe<?>> recipes = TdBasicMethods.findRecipesByType(ArtefactAnalyzerRecipe.Type.INSTANCE, level);
		
		for (Recipe<?> Recipe : recipes) {
			ArtefactAnalyzerRecipe recipe = (ArtefactAnalyzerRecipe) Recipe;
			
			if (recipe.matches(TdBasicMethods.getWrapper(stack), level)) {
				return recipe;
			}
		}

		return null;
	}
	
	public void retreiveOutput(ArtefactAnalyzerTE te, Level level) {
		if(te.running)
			return;
		
		ItemStack output = te.getResult();
		te.emptyMachine();
		TdBasicMethods.spawn(output, level, te.getBlockPos(), 0, 1, 0);
		TdBasicMethods.playSound(level, te.getBlockPos(), SoundEvents.PLAYER_LEVELUP);
	}
}