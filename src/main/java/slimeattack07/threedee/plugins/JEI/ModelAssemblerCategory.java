package slimeattack07.threedee.plugins.JEI;

import java.util.Arrays;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.config.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDBlocks;
import slimeattack07.threedee.recipes.ModelAssemblerRecipe;
import slimeattack07.threedee.util.TdBasicMethods;

public class ModelAssemblerCategory implements IRecipeCategory<ModelAssemblerRecipe>{
	private final IDrawable icon;
	private final IDrawable background;

	public ModelAssemblerCategory(IGuiHelper guihelper) {
		icon = guihelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(TDBlocks.MODEL_ASSEMBLER.get()));
		background = guihelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 0, 168, 125, 18);
	}
	
	@Override
	public ResourceLocation getUid() {
		return new ResourceLocation(Threedee.MOD_ID, "model_assembler");
	}

	@Override
	public Class<? extends ModelAssemblerRecipe> getRecipeClass() {
		return ModelAssemblerRecipe.class;
	}

	@Override
	public Component getTitle() {
		return new TranslatableComponent("jei.threedee.titles.model_assembler");
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
	public void setRecipe(IRecipeLayoutBuilder builder, ModelAssemblerRecipe recipe, IFocusGroup focuses) {
		if(!recipe.isDyeFiller()) {
			builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addItemStacks(Arrays.asList(recipe.getInput().getItems()));
			builder.addSlot(RecipeIngredientRole.INPUT, 50, 1).addItemStack(new ItemStack(recipe.getBlockOnTop()));
			builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 1).addItemStack(recipe.getResultItem());
		}
	}
	
	@Override
	public void draw(ModelAssemblerRecipe recipe, IRecipeSlotsView view, PoseStack stack, double mouseX, double mouseY) {
		String text;
		
		if(recipe.isDyeFiller())
			text = TdBasicMethods.getTranslation("jei.threedee.draw.dye_paste_added") + " " + recipe.getAmount();
		else
			text = TdBasicMethods.getTranslation("jei.threedee.draw.dye_paste_required") + " " + recipe.getAmount();
		
		drawPaintCost(Minecraft.getInstance(), stack, text, 0xFF80FF20);
	}
	
	private void drawPaintCost(Minecraft minecraft, PoseStack posestack, String text, int mainColor) {
		int shadowColor = 0xFF000000 | (mainColor & 0xFCFCFC) >> 2;
		int width = minecraft.font.width(text);
		int x = background.getWidth() - 2 - width + 1;
		int y = 28;

		minecraft.font.draw(posestack, text, x + 1, y, shadowColor);
		minecraft.font.draw(posestack, text, x, y + 1, shadowColor);
		minecraft.font.draw(posestack, text, x + 1, y + 1, shadowColor);
		minecraft.font.draw(posestack, text, x, y, mainColor);
	}
}
