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
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import slimeattack07.threedee.Threedee;

public class HeadRecyclerRecipe implements Recipe<RecipeWrapper>{
	private final ItemStack CORRUPTED_OUTPUT;
	private final ItemStack OUTPUT;
	private final ResourceLocation ID;
	private final Ingredient INPUT;
	private final float TICKS;
	
	public static final ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(Threedee.MOD_ID, "head_recycler");
	
	public HeadRecyclerRecipe(ResourceLocation id, Ingredient input, ItemStack output, ItemStack cor_output, float ticks) {
		ID = id;
		INPUT = input;
		OUTPUT = output;
		CORRUPTED_OUTPUT = cor_output;
		TICKS = ticks;
	}
	
	public ItemStack getCorruptedOutput() {
		return CORRUPTED_OUTPUT;
	}
	
	public float ticksNeeded() {
		return TICKS;
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
		return OUTPUT;
	}
	
	@Override
	public boolean canCraftInDimensions(int w, int h) {
		return false;
	}
	
	@Override
	public ItemStack getResultItem() {
		return OUTPUT;
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
    
    public static class Type implements RecipeType<HeadRecyclerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "head_recycler";
    }
    
    public static class Serializer implements RecipeSerializer<HeadRecyclerRecipe>{
    	 public static final Serializer INSTANCE = new Serializer();
         public static final ResourceLocation ID = new ResourceLocation(Threedee.MOD_ID,"head_recycler");

    	@Override
    	public HeadRecyclerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
    		try {
	    		Ingredient input = Ingredient.fromJson(json.getAsJsonObject("input"));
	    		ItemStack output = CraftingHelper.getItemStack(json.getAsJsonObject("output"), true);
	    		ItemStack cor_output = CraftingHelper.getItemStack(json.getAsJsonObject("corrupted_output"), true);
	    		float ticks_needed = json.get("ticks_needed").getAsFloat();
	    		if (ticks_needed < 0)
	    			ticks_needed = 0;
	    		
	    		return new HeadRecyclerRecipe(recipeId, input, output, cor_output, ticks_needed);
    		} catch (ClassCastException | IllegalStateException | JsonSyntaxException e) {
				Threedee.LOGGER.error("Can't process malformed recipe! Recipe id is " + recipeId + ". Error: " + e.getMessage());
				return null;
			}
    	}

    	@Override
    	public HeadRecyclerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
    		Ingredient input = Ingredient.fromNetwork(buffer);
    		ItemStack output = buffer.readItem();
    		ItemStack cor_output = buffer.readItem();
    		float ticks_needed = buffer.readFloat();
    		if (ticks_needed < 0)
    			ticks_needed = 0;
    		
    		return new HeadRecyclerRecipe(recipeId, input, output, cor_output, ticks_needed);
    	}

    	@Override
    	public void toNetwork(FriendlyByteBuf buffer, HeadRecyclerRecipe recipe) {
    		Ingredient input = recipe.getIngredients().get(0);
    		
    		input.toNetwork(buffer);
    		buffer.writeItemStack(recipe.getResultItem(), false);
    		buffer.writeItemStack(recipe.getCorruptedOutput(), false);
    		buffer.writeFloat(recipe.ticksNeeded());
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