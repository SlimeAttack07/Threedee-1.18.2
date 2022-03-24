package slimeattack07.threedee.datagen;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.AncientModelBlocks;
import slimeattack07.threedee.init.CommonModelBlocks;
import slimeattack07.threedee.init.EpicModelBlocks;
import slimeattack07.threedee.init.LegendaryModelBlocks;
import slimeattack07.threedee.init.RareModelBlocks;
import slimeattack07.threedee.init.TDItems;
import slimeattack07.threedee.init.UncommonModelBlocks;
import slimeattack07.threedee.recipes.HeadAssemblerRecipe;

public class DataRecipes extends RecipeProvider{
	public DataRecipes(DataGenerator gen) {
		super(gen);
	}
	
	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		registerAll(CommonModelBlocks.COMMON, consumer, "common", TDItems.CATALYST_COMMON.get(), 2);
		registerAll(UncommonModelBlocks.UNCOMMON, consumer, "common", TDItems.CATALYST_UNCOMMON.get(), 4);
		registerAll(RareModelBlocks.RARE, consumer, "common", TDItems.CATALYST_RARE.get(), 6);
		registerAll(EpicModelBlocks.EPIC, consumer, "common", TDItems.CATALYST_EPIC.get(), 8);
		registerAll(LegendaryModelBlocks.LEGENDARY, consumer, "common", TDItems.CATALYST_LEGENDARY.get(), 10);
		registerAll(AncientModelBlocks.ANCIENT, consumer, "common", TDItems.CATALYST_ANCIENT.get(), 12);
	}
    
	private void registerAll(DeferredRegister<Block> reg, Consumer<FinishedRecipe> consumer, String rarity, Item catalyst, int amount) {
		reg.getEntries().stream()
		.map(RegistryObject::get).forEach(block -> {
			consumer.accept(new ModelAssembler(block.getRegistryName(), rarity, catalyst, amount));
		});
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
