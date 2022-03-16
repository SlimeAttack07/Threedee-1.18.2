package slimeattack07.threedee.objects.blocks;

import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import slimeattack07.threedee.recipes.HandsawRecipe;
import slimeattack07.threedee.tileentity.BasicInterTE;
import slimeattack07.threedee.util.TdBasicMethods;

public class Handsaw extends BasicIntBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	public Handsaw() {
		super(4, -1);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public void playEffects(Level level, BlockPos pos) {
		TdBasicMethods.playSound(level, pos, SoundEvents.WOOD_BREAK);
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
	protected int getAmountPossible(BasicInterTE te, ItemStack main, Level level) {
		HandsawRecipe recipe = getRecipe(main, level, te.last_recipe);
		
		if(recipe != null) {
			if (ItemStack.isSame(recipe.getResultItem(), ItemStack.EMPTY)) 
				return 0;
			
			int amount_in = TdBasicMethods.getMatchingStack(recipe.getInput(), main).getCount();
			te.setLastRecipe(recipe.getId().toString());
			return amount_in;
		}
		
		return 0;
	}
	
	@Nullable
	private HandsawRecipe getRecipe(ItemStack stack, Level level, String last_recipe) {
		if (stack == null) {
			return null;
		}
		
		if(last_recipe != null && !last_recipe.equals("")) {
			HandsawRecipe recipe = (HandsawRecipe) TdBasicMethods.getRecipe(last_recipe, level);
			
			if(recipe != null && recipe.matches(TdBasicMethods.getWrapper(stack), level)) 
				return recipe;
		}

		Set<Recipe<?>> recipes = TdBasicMethods.findRecipesByType(HandsawRecipe.Type.INSTANCE, level);
		
		for (Recipe<?> Recipe : recipes) {
			HandsawRecipe recipe = (HandsawRecipe) Recipe;
			
			if (recipe.matches(TdBasicMethods.getWrapper(stack), level)) {
				return recipe;
			}
		}

		return null;
	}
	
	@Nullable
	@Override
	protected ItemStack getResultItem(String last_recipe, Level level) {
		HandsawRecipe recipe = (HandsawRecipe) TdBasicMethods.getRecipe(last_recipe, level);
		
		if(recipe == null) 
			return null;
		
		return recipe.getResultItem();
	}
}