package slimeattack07.threedee.datagen;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDBlocks;
import slimeattack07.threedee.init.TDItems;
import slimeattack07.threedee.recipes.HeadAssemblerRecipe;

public class DataRecipes extends RecipeProvider{
	public DataRecipes(DataGenerator gen) {
		super(gen);
	}
	
	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		consumer.accept(new ModelAssembler(TDBlocks.MORTAR_AND_PESTLE_ANDESITE.getId(), "common", TDItems.CATALYST_COMMON.get(), 2));
	}
    
    @Override
    public String getName() {
    	return Threedee.MOD_ID + " recipes";
    }
    
    public static class ModelAssembler implements FinishedRecipe{
    	private final ResourceLocation ID;
    	private final String FOLDER;
    	private final Item INPUT;
    	private final int DYE;
    	
    	public ModelAssembler(ResourceLocation id, String folder, Item input, int dye) {
    		ID = id;
    		FOLDER = folder + "/";
    		INPUT = input;
    		DYE = dye;
    	}
    	
		@Override
		public void serializeRecipeData(JsonObject json) {
			JsonObject input = new JsonObject();
			input.addProperty("item", INPUT.getRegistryName().toString());
			
			json.add("input", input);
			json.addProperty("amount", DYE);
			json.addProperty("block_on_top", ID.toString());
		}

		@Override
		public ResourceLocation getId() {
			return new ResourceLocation(Threedee.MOD_ID, "head_assembler/" + FOLDER + ID.getPath());
		}

		@Override
		public RecipeSerializer<?> getType() {
			return HeadAssemblerRecipe.Serializer.INSTANCE;
		}

		@Override
		public JsonObject serializeAdvancement() {
			return null;
		}

		@Override
		public ResourceLocation getAdvancementId() {
			return null;
		}
    	
    }
}
