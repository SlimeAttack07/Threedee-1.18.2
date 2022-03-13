package slimeattack07.threedee.plugins.JEI;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDBlocks;
import slimeattack07.threedee.init.TDItems;
import slimeattack07.threedee.recipes.ArtefactAnalyzerRecipe;
import slimeattack07.threedee.recipes.HandsawRecipe;
import slimeattack07.threedee.recipes.HeadAssemblerRecipe;
import slimeattack07.threedee.recipes.HeadFabricatorRecipe;
import slimeattack07.threedee.recipes.HeadRecyclerRecipe;
import slimeattack07.threedee.recipes.ItemExchangerRecipe;
import slimeattack07.threedee.recipes.MortarPestleRecipe;
import slimeattack07.threedee.recipes.TinyCauldronRecipe;
import slimeattack07.threedee.tileentity.HeadRecyclerTE;
import slimeattack07.threedee.util.TdBasicMethods;

@JeiPlugin
public class TdJEIPlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(Threedee.MOD_ID, "jeiplugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IGuiHelper guihelper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(new MortarPestleCategory(guihelper), new TinyCauldronCategory(guihelper),
				new HandsawCategory(guihelper), new HeadRecyclerCategory(guihelper), new HeadAssemblerCategory(guihelper),
				new ArtefactAnalyzerCategory(guihelper), new HeadFabricatorCategory(guihelper), new ItemExchangerCategory(guihelper));
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		Minecraft mc = Minecraft.getInstance();
		ClientLevel level = mc.level;
		RecipeManager manager = level.getRecipeManager();
		
		// Adding the recipe's to their categories
		registration.addRecipes(TdBasicMethods.findRecipesByType(MortarPestleRecipe.Type.INSTANCE, manager), 
				new ResourceLocation(Threedee.MOD_ID, "mortar_and_pestle"));
		registration.addRecipes(TdBasicMethods.findRecipesByType(TinyCauldronRecipe.Type.INSTANCE, manager), 
				new ResourceLocation(Threedee.MOD_ID, "tiny_cauldron"));
		registration.addRecipes(TdBasicMethods.findRecipesByType(HandsawRecipe.Type.INSTANCE, manager), 
				new ResourceLocation(Threedee.MOD_ID, "handsaw"));
		registration.addRecipes(TdBasicMethods.findRecipesByType(HeadRecyclerRecipe.Type.INSTANCE, manager), 
				new ResourceLocation(Threedee.MOD_ID, "head_recycler"));
		registration.addRecipes(TdBasicMethods.findRecipesByType(HeadAssemblerRecipe.Type.INSTANCE, manager), 
				new ResourceLocation(Threedee.MOD_ID, "head_assembler"));
		registration.addRecipes(TdBasicMethods.findRecipesByType(HeadFabricatorRecipe.Type.INSTANCE, manager), 
				new ResourceLocation(Threedee.MOD_ID, "head_fabricator"));
		registration.addRecipes(TdBasicMethods.findRecipesByType(ItemExchangerRecipe.Type.INSTANCE, manager), 
				new ResourceLocation(Threedee.MOD_ID, "item_exchanger"));
		registration.addRecipes(TdBasicMethods.findRecipesByType(ArtefactAnalyzerRecipe.Type.INSTANCE, manager), 
				new ResourceLocation(Threedee.MOD_ID, "artefact_analyzer"));
		
		// Preparation work for adding item descriptions
		String head_rec_limit = TdBasicMethods.getTranslation("instruction.threedee.head_recycler.limit").
				replace("MARKER1", HeadRecyclerTE.BUFFER_SIZE + "");
		
		// Adding item descriptions
		registration.addIngredientInfo(new ItemStack(TDBlocks.HEAD_RECYCLER.get()), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("description.threedee.head_recycler"),
				TdBasicMethods.createBlueText("instruction.threedee.head_recycler.rightclick"),
				TdBasicMethods.createBlueText("instruction.threedee.head_recycler.order"),
				TdBasicMethods.createBlueText("instruction.threedee.head_recycler.rightclick_empty"),
				new TextComponent(ChatFormatting.BLUE + head_rec_limit),
				TdBasicMethods.createBlueText("instruction.threedee.general.leftclick"),
				TdBasicMethods.createRedText("instruction.threedee.head_recycler.broken"));
		
		registration.addIngredientInfo(new ItemStack(TDBlocks.HEAD_ASSEMBLER.get()), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("description.threedee.head_assembler"),
				TdBasicMethods.createBlueText("instruction.threedee.head_assembler.rightclick_paste"),
				TdBasicMethods.createBlueText("instruction.threedee.head_assembler.rightclick_empty"),
				TdBasicMethods.createBlueText("instruction.threedee.head_assembler.head_top"),
				TdBasicMethods.createBlueText("instruction.threedee.head_assembler.rightclick_catalyst"),
				TdBasicMethods.createBlueText("instruction.threedee.general.leftclick"),
				TdBasicMethods.createRedText("instruction.threedee.head_assembler.broken"));
		
		registration.addIngredientInfo(new ItemStack(TDBlocks.HEAD_FABRICATOR.get()), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("description.threedee.head_fabricator"),
				TdBasicMethods.createBlueText("instruction.threedee.head_fabricator.rightclick_catalyst"),
				TdBasicMethods.createBlueText("instruction.threedee.head_fabricator.rightclick_empty"),
				TdBasicMethods.createBlueText("instruction.threedee.head_fabricator.limit"),
				TdBasicMethods.createBlueText("instruction.threedee.general.leftclick"),
				TdBasicMethods.createBlueText("instruction.threedee.head_fabricator.leftclick_shift"),
				TdBasicMethods.createRedText("instruction.threedee.head_fabricator.broken"));
		
		registration.addIngredientInfo(new ItemStack(TDBlocks.ARTEFACT_ANALYZER.get()), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("description.threedee.artefact_analyzer"),
				TdBasicMethods.createBlueText("instruction.threedee.artefact_analyzer.rightclick"),
				TdBasicMethods.createBlueText("instruction.threedee.artefact_analyzer.limit"),
				TdBasicMethods.createRedText("instruction.threedee.artefact_analyzer.broken"));
		
		registration.addIngredientInfo(new ItemStack(TDBlocks.ARTEFACT_EXCHANGER.get()), VanillaTypes.ITEM,
				TdBasicMethods.createBlueText("description.threedee.artefact_exchanger"),
				TdBasicMethods.createBlueText("instruction.threedee.exchangers.rightclick_empty"),
				TdBasicMethods.createBlueText("instruction.threedee.exchangers.shift_rightclick_empty"),
				TdBasicMethods.createBlueText("instruction.threedee.exchangers.rightclick_card"),
				TdBasicMethods.createBlueText("instruction.threedee.artefact_exchanger.rightclick_artefact"),
				TdBasicMethods.createRedText("instruction.threedee.exchangers.broken"));
		
		registration.addIngredientInfo(new ItemStack(TDBlocks.ITEM_EXCHANGER.get()), VanillaTypes.ITEM,
				TdBasicMethods.createBlueText("description.threedee.item_exchanger"),
				TdBasicMethods.createBlueText("instruction.threedee.exchangers.rightclick_empty"),
				TdBasicMethods.createBlueText("instruction.threedee.exchangers.shift_rightclick_empty"),
				TdBasicMethods.createBlueText("instruction.threedee.exchangers.rightclick_card"),
				TdBasicMethods.createBlueText("instruction.threedee.item_exchanger.rightclick_item"),
				TdBasicMethods.createBlueText("instruction.threedee.item_exchanger.tokens"),
				TdBasicMethods.createBlueText("instruction.threedee.item_exchanger.consume"),
				TdBasicMethods.createRedText("instruction.threedee.exchangers.broken"));
		
		registration.addIngredientInfo(new ItemStack(TDBlocks.NEGOTIATOR.get()), VanillaTypes.ITEM,
				TdBasicMethods.createBlueText("description.threedee.negotiator"),
				TdBasicMethods.createBlueText("instruction.threedee.negotiator.rightclick"),
				TdBasicMethods.createBlueText("instruction.threedee.negotiator.limit"),
				TdBasicMethods.createRedText("instruction.threedee.negotiator.broken"));
		
		registration.addIngredientInfo(TDBlocks.getMortarPestles(), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("description.threedee.mortar_and_pestle"));
		
		registration.addIngredientInfo(TDBlocks.getTinyCauldrons(), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("description.threedee.tiny_cauldron"));
		
		registration.addIngredientInfo(TDBlocks.getHandsaws(), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("description.threedee.handsaw"));
		
		registration.addIngredientInfo(new ItemStack(TDItems.CATALYZED_BONE_MEAL.get()), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("description.threedee.cat_bone_meal"),
				TdBasicMethods.createRedText("description.threedee.cat_bone_meal_issues"));
		
		registration.addIngredientInfo(new ItemStack(TDItems.CATALYST_CORRUPTED.get()), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("jei.threedee.descriptions.catalyst_corrupted"));
		
		registration.addIngredientInfo(TDItems.getAncientRocks(), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("jei.threedee.descriptions.ancient_rock"));
		
		registration.addIngredientInfo(TDItems.getCatalysts(), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("jei.threedee.descriptions.catalyst"));
		
		registration.addIngredientInfo(TDBlocks.getCommonHeads(), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("jei.threedee.descriptions.heads_fab"));
		
		registration.addIngredientInfo(TDBlocks.getUncommonHeads(), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("jei.threedee.descriptions.heads_fab"));
		
		registration.addIngredientInfo(TDBlocks.getRareHeads(), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("jei.threedee.descriptions.heads_fab"));
		
		registration.addIngredientInfo(TDBlocks.getEpicHeads(), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("jei.threedee.descriptions.heads_fab"));
		
		registration.addIngredientInfo(TDBlocks.getLegendaryHeads(), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("jei.threedee.descriptions.heads_fab"));
		
		registration.addIngredientInfo(TDBlocks.getAncientHeads(), VanillaTypes.ITEM, 
				TdBasicMethods.createBlueText("jei.threedee.descriptions.artefact_an"));
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.MORTAR_AND_PESTLE_ANDESITE.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "mortar_and_pestle"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.MORTAR_AND_PESTLE_DIORITE.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "mortar_and_pestle"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.MORTAR_AND_PESTLE_GRANITE.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "mortar_and_pestle"));
		
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.TINY_CAULDRON_ACACIA.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "tiny_cauldron"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.TINY_CAULDRON_BIRCH.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "tiny_cauldron"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.TINY_CAULDRON_DARK_OAK.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "tiny_cauldron"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.TINY_CAULDRON_JUNGLE.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "tiny_cauldron"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.TINY_CAULDRON_OAK.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "tiny_cauldron"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.TINY_CAULDRON_SPRUCE.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "tiny_cauldron"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.TINY_CAULDRON_DARK_OAK.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "tiny_cauldron"));
		
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.HANDSAW_ACACIA.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "handsaw"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.HANDSAW_BIRCH.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "handsaw"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.HANDSAW_DARK_OAK.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "handsaw"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.HANDSAW_JUNGLE.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "handsaw"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.HANDSAW_OAK.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "handsaw"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.HANDSAW_SPRUCE.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "handsaw"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.HANDSAW_DARK_OAK.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "handsaw"));
		
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.HEAD_RECYCLER.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "head_recycler"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.HEAD_ASSEMBLER.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "head_assembler"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.HEAD_FABRICATOR.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "head_fabricator"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.ARTEFACT_ANALYZER.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "artefact_analyzer"));
		registration.addRecipeCatalyst(new ItemStack(TDBlocks.ITEM_EXCHANGER.get().asItem()), new ResourceLocation(Threedee.MOD_ID, "item_exchanger"));
	}
}
