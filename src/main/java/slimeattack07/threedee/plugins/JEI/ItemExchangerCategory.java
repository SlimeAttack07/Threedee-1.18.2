package slimeattack07.threedee.plugins.JEI;

import java.util.Arrays;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
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
import slimeattack07.threedee.recipes.ItemExchangerRecipe;
import slimeattack07.threedee.util.TdBasicMethods;

public class ItemExchangerCategory implements IRecipeCategory<ItemExchangerRecipe>{
	private final IDrawable icon;
	private final IDrawable background;

	public ItemExchangerCategory(IGuiHelper guihelper) {
		icon = guihelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(TDBlocks.ITEM_EXCHANGER.get()));
		background = guihelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 0, 220, 82, 34);
	}
	
	@Override
	public ResourceLocation getUid() {
		return new ResourceLocation(Threedee.MOD_ID, "item_exchanger");
	}

	@Override
	public Class<? extends ItemExchangerRecipe> getRecipeClass() {
		return ItemExchangerRecipe.class;
	}

	@Override
	public Component getTitle() {
		return new TranslatableComponent("jei.threedee.titles.item_exchanger");
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
	public void setRecipe(IRecipeLayoutBuilder builder, ItemExchangerRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 1, 9).addItemStacks(Arrays.asList(recipe.getInput().getItems()));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 9).addItemStack(recipe.getResultItem());
	}
	
	@Override
	public void draw(ItemExchangerRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
		String text1 = TdBasicMethods.getTranslation("jei.threedee.tokens_required") + " " + recipe.getTokens();
		String text2 = TdBasicMethods.getTranslation("jei.threedee.consumed") + " " + recipe.consume();
		
		drawTokenCost(Minecraft.getInstance(), stack, text1, text2, 0xFF80FF20);
	}
	
	private void drawTokenCost(Minecraft minecraft, PoseStack stack, String text1, String text2, int mainColor) {
		int shadowColor = 0xFF000000 | (mainColor & 0xFCFCFC) >> 2;
		int width1 = minecraft.font.width(text1);
		int x1 = background.getWidth() - 2 - width1;
		int y1 = 27;
		int width2 = minecraft.font.width(text2);
		int x2 = background.getWidth() - 2 - width2;
		int y2 = 0;

		minecraft.font.draw(stack, text1, x1 + 1, y1, shadowColor);
		minecraft.font.draw(stack, text1, x1, y1 + 1, shadowColor);
		minecraft.font.draw(stack, text1, x1 + 1, y1 + 1, shadowColor);
		minecraft.font.draw(stack, text1, x1, y1, mainColor);
		
		minecraft.font.draw(stack, text2, x2 + 1, y2, shadowColor);
		minecraft.font.draw(stack, text2, x2, y2 + 1, shadowColor);
		minecraft.font.draw(stack, text2, x2 + 1, y2 + 1, shadowColor);
		minecraft.font.draw(stack, text2, x2, y2, mainColor);
	}
}
