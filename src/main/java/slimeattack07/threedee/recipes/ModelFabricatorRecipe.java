package slimeattack07.threedee.recipes;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import slimeattack07.threedee.Threedee;

public class ModelFabricatorRecipe implements Recipe<RecipeWrapper>{
	private final String LOOT_TABLE;
	private final ResourceLocation ID;
	private final Ingredient INPUT;
	
	public static final ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(Threedee.MOD_ID, "model_fabricator");
	
	public ModelFabricatorRecipe(ResourceLocation id, Ingredient input, String loot_table) {
		ID = id;
		INPUT = input;
		LOOT_TABLE = loot_table;
	}
	
	public String getLootTable() {
		return LOOT_TABLE;
	}
	
	public Ingredient getInput() {
		return INPUT;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(null, INPUT);
	}
	
	@Override
	public boolean matches(RecipeWrapper wrapper, Level level) {
		return INPUT.test(wrapper.getItem(0));
	}
	
	@Override
	public ItemStack assemble(RecipeWrapper wrapper) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canCraftInDimensions(int w, int h) {
		return false;
	}
	
	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}
	
	@Override
	public ResourceLocation getId() {
		return ID;
	}
	
	@Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    
    public static class Type implements RecipeType<ModelFabricatorRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "model_fabricator";
    }
    
    public static class Serializer implements RecipeSerializer<ModelFabricatorRecipe>{
    	public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Threedee.MOD_ID,"model_fabricator");
        
    	@Override
    	public ModelFabricatorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
    		try {
	    		Ingredient input = Ingredient.fromJson(json.getAsJsonObject("input"));
	    		String loot_table = json.get("loot_table").getAsString();
	    		
	    		return new ModelFabricatorRecipe(recipeId, input, loot_table);
    		} catch (ClassCastException | IllegalStateException | JsonSyntaxException e) {
				Threedee.LOGGER.error("Can't process malformed recipe! Recipe id is " + recipeId + ". Error: " + e.getMessage());
				return null;
			}
    	}

    	@Override
    	public ModelFabricatorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
    		Ingredient input = Ingredient.fromNetwork(buffer);
    		String loot_table = buffer.readUtf();
    		
    		return new ModelFabricatorRecipe(recipeId, input, loot_table);
    	}

    	@Override
    	public void toNetwork(FriendlyByteBuf buffer, ModelFabricatorRecipe recipe) {
    		Ingredient input = recipe.getIngredients().get(0);
    		
    		input.toNetwork(buffer);
    		buffer.writeUtf(recipe.getLootTable());
    	}
    	
    	@Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}