package slimeattack07.threedee.plugins.JEI;

import java.util.Arrays;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.config.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDBlocks;
import slimeattack07.threedee.recipes.ModelFabricatorRecipe;
import slimeattack07.threedee.util.TdBasicMethods;

public class ModelFabricatorCategory implements IRecipeCategory<ModelFabricatorRecipe>{
	private final IDrawable icon;
	private final IDrawable background;

	public ModelFabricatorCategory(IGuiHelper guihelper) {
		icon = guihelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(TDBlocks.MODEL_FABRICATOR.get()));
		background = guihelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 0, 220, 82, 34);
	}
	
	@Override
	public ResourceLocation getUid() {
		return new ResourceLocation(Threedee.MOD_ID, "model_fabricator");
	}

	@Override
	public Class<? extends ModelFabricatorRecipe> getRecipeClass() {
		return ModelFabricatorRecipe.class;
	}

	@Override
	public Component getTitle() {
		return new TranslatableComponent("jei.threedee.titles.model_fabricator");
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, ModelFabricatorRecipe recipe, IFocusGroup focuses) {
		ItemStack output = new ItemStack(Items.PAPER);
		String text = TdBasicMethods.getTranslation("jei.threedee.loot_table") + " " + recipe.getLootTable();
		output.setHoverName(new TextComponent(text));
		
		builder.addSlot(RecipeIngredientRole.INPUT, 1, 9).addItemStacks(Arrays.asList(recipe.getInput().getItems()));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 9).addItemStack(output);
	}
}
