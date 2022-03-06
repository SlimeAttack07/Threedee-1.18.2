package slimeattack07.threedee.recipes;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import slimeattack07.threedee.Threedee;

public class ItemExchangerRecipe implements Recipe<RecipeWrapper>{
	private final ResourceLocation ID;
	private Ingredient INPUT;
	private final int COUNT;
	private final ItemStack OUTPUT;
	private final int TOKENS;
	private final boolean CONSUME;
	
	public static final ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(Threedee.MOD_ID, "item_exchanger");
	
	public ItemExchangerRecipe(ResourceLocation id, Ingredient input, int count, ItemStack output, int tokens, boolean consume) {
		ItemStack real_input = input.getItems()[0];
		real_input.setCount(count);
		
		ID = id;
		INPUT = Ingredient.of(real_input);
		COUNT = count;
		OUTPUT = output;
		TOKENS = tokens;
		CONSUME = consume;
	}
	
	public int getTokens() {
		return TOKENS;
	}
	
	public boolean consume() {
		return CONSUME;
	}
	
	public int getCount() {
		return COUNT;
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
    
    public static class Type implements RecipeType<ItemExchangerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "item_exchanger";
    }
    
    public static class Serializer implements RecipeSerializer<ItemExchangerRecipe>{
    	 public static final Serializer INSTANCE = new Serializer();
         public static final ResourceLocation ID = new ResourceLocation(Threedee.MOD_ID,"item_exchanger");

    	@Override
    	public ItemExchangerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
    		Ingredient input = Ingredient.fromJson(json.getAsJsonObject("input"));
    		ItemStack output = input.getItems()[0];
    		
    		if(json.has("output"))
    			output = CraftingHelper.getItemStack(json.getAsJsonObject("output"), true);
    		
    		int count = json.has("amount_in") ? json.get("amount_in").getAsInt() : 1;
    		Item item =  input.getItems()[0].getItem();
    		
    		if(count < 1 || count > item.getMaxDamage(new ItemStack(item)))
    			count = 1;
    		
    		int tokens = json.has("tokens") ? json.get("tokens").getAsInt() : 1;
    		boolean consume = json.has("consume") && json.get("consume").getAsBoolean();
    		
    		return new ItemExchangerRecipe(recipeId, input, count, output, tokens, consume);
    	}

    	@Override
    	public ItemExchangerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
    		Ingredient input = Ingredient.fromNetwork(buffer);
    		ItemStack output = buffer.readItem();
    		int count = buffer.readInt();
    		int tokens = buffer.readInt();
    		boolean consume = buffer.readBoolean();
    		
    		return new ItemExchangerRecipe(recipeId, input, count, output, tokens, consume);
    	}

    	@Override
    	public void toNetwork(FriendlyByteBuf buffer, ItemExchangerRecipe recipe) {
    		Ingredient input = recipe.getIngredients().get(0);
    		
    		input.toNetwork(buffer);
    		buffer.writeItemStack(recipe.getResultItem(), false);
    		buffer.writeInt(recipe.getCount());
    		buffer.writeInt(recipe.getTokens());
    		buffer.writeBoolean(recipe.consume());
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