package slimeattack07.threedee.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.recipes.ArtefactAnalyzerRecipe;
import slimeattack07.threedee.recipes.HandsawRecipe;
import slimeattack07.threedee.recipes.ModelAssemblerRecipe;
import slimeattack07.threedee.recipes.ModelFabricatorRecipe;
import slimeattack07.threedee.recipes.ModelRecyclerRecipe;
import slimeattack07.threedee.recipes.ItemExchangerRecipe;
import slimeattack07.threedee.recipes.MortarPestleRecipe;
import slimeattack07.threedee.recipes.TinyCauldronRecipe;

public class TDRecipeSerializer {
	 public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
	            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Threedee.MOD_ID);
	 
	 public static final RegistryObject<RecipeSerializer<MortarPestleRecipe>> MORTAR_PESTLE =
	            SERIALIZERS.register("mortar_and_pestle", () -> MortarPestleRecipe.Serializer.INSTANCE);
	 public static final RegistryObject<RecipeSerializer<TinyCauldronRecipe>> TINY_CAULDRON =
	            SERIALIZERS.register("tiny_cauldron", () -> TinyCauldronRecipe.Serializer.INSTANCE);
	 public static final RegistryObject<RecipeSerializer<HandsawRecipe>> HANDSAW =
	            SERIALIZERS.register("handsaw", () -> HandsawRecipe.Serializer.INSTANCE);
	 public static final RegistryObject<RecipeSerializer<ModelAssemblerRecipe>> MODEL_ASSEMBLER =
	            SERIALIZERS.register("model_assembler", () -> ModelAssemblerRecipe.Serializer.INSTANCE);
	 public static final RegistryObject<RecipeSerializer<ModelFabricatorRecipe>> MODEL_FABRICATOR =
	            SERIALIZERS.register("model_fabricator", () -> ModelFabricatorRecipe.Serializer.INSTANCE);
	 public static final RegistryObject<RecipeSerializer<ModelRecyclerRecipe>> MODEL_RECYCLER =
	            SERIALIZERS.register("model_recycler", () -> ModelRecyclerRecipe.Serializer.INSTANCE);
	 public static final RegistryObject<RecipeSerializer<ArtefactAnalyzerRecipe>> ARTEFACT_ANALYZER =
	            SERIALIZERS.register("artefact_analyzer", () -> ArtefactAnalyzerRecipe.Serializer.INSTANCE);
	 public static final RegistryObject<RecipeSerializer<ItemExchangerRecipe>> ITEM_EXCHANGER =
	            SERIALIZERS.register("item_exchanger", () -> ItemExchangerRecipe.Serializer.INSTANCE);
}