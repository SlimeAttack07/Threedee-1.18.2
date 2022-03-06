package slimeattack07.threedee.recipes;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

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

public class MortarPestleRecipe implements Recipe<RecipeWrapper>{
	private final ResourceLocation ID;
	private final Ingredient INPUT;
	private final ItemStack OUTPUT;
	
	public static final ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(Threedee.MOD_ID, "mortar_and_pestle");

	public MortarPestleRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
		ID = id;
		INPUT = input;
		OUTPUT = output;
	}
	
	public Ingredient getInput() {
		return INPUT;
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
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(null, INPUT);
	}
	
	@Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    
    public static class Type implements RecipeType<MortarPestleRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "mortar_and_pestle";
    }
    
    public static class Serializer implements RecipeSerializer<MortarPestleRecipe>{
    	 public static final Serializer INSTANCE = new Serializer();
         public static final ResourceLocation ID = new ResourceLocation(Threedee.MOD_ID, "mortar_and_pestle");

    	@Override
    	public MortarPestleRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
    		Ingredient input = Ingredient.fromJson(json.getAsJsonObject("input"));
    		ItemStack output = CraftingHelper.getItemStack(json.getAsJsonObject("output"), true);
    	
    		return new MortarPestleRecipe(recipeId, input, output);
    	}

    	@Override
    	public MortarPestleRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
    		ItemStack output = buffer.readItem();
    		Ingredient input = Ingredient.fromNetwork(buffer);
    		
    		return new MortarPestleRecipe(recipeId, input, output);
    	}

    	@Override
    	public void toNetwork(FriendlyByteBuf buffer, MortarPestleRecipe recipe) {
    		Ingredient input = recipe.getIngredients().get(0);
    		
    		input.toNetwork(buffer);
    		buffer.writeItemStack(recipe.getResultItem(), false);
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