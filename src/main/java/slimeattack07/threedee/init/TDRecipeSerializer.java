package slimeattack07.threedee.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.recipes.ArtefactAnalyzerRecipe;
import slimeattack07.threedee.recipes.HandsawRecipe;
import slimeattack07.threedee.recipes.HeadAssemblerRecipe;
import slimeattack07.threedee.recipes.HeadFabricatorRecipe;
import slimeattack07.threedee.recipes.HeadRecyclerRecipe;
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
	 public static final RegistryObject<RecipeSerializer<HeadAssemblerRecipe>> HEAD_ASSEMBLER =
	            SERIALIZERS.register("head_assembler", () -> HeadAssemblerRecipe.Serializer.INSTANCE);
	 public static final RegistryObject<RecipeSerializer<HeadFabricatorRecipe>> HEAD_FABRICATOR =
	            SERIALIZERS.register("head_fabricator", () -> HeadFabricatorRecipe.Serializer.INSTANCE);
	 public static final RegistryObject<RecipeSerializer<HeadRecyclerRecipe>> HEAD_RECYCLER =
	            SERIALIZERS.register("head_recycler", () -> HeadRecyclerRecipe.Serializer.INSTANCE);
	 public static final RegistryObject<RecipeSerializer<ArtefactAnalyzerRecipe>> ARTEFACT_ANALYZER =
	            SERIALIZERS.register("artefact_analyzer", () -> ArtefactAnalyzerRecipe.Serializer.INSTANCE);
	 public static final RegistryObject<RecipeSerializer<ItemExchangerRecipe>> ITEM_EXCHANGER =
	            SERIALIZERS.register("item_exchanger", () -> ItemExchangerRecipe.Serializer.INSTANCE);
}