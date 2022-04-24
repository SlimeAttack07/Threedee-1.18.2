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
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import slimeattack07.threedee.Threedee;

public class ModelAssemblerRecipe implements Recipe<RecipeWrapper>{
	private final boolean IS_DYE_FILLER;
	private final int AMOUNT;
	private final Block BLOCK_ON_TOP;
	private final ResourceLocation ID;
	private final Ingredient INPUT;
	
	public static final ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(Threedee.MOD_ID, "model_assembler");
	
	public ModelAssemblerRecipe(ResourceLocation id, Ingredient input, Block block_on_top, boolean is_dye_filler, int amount) {
		ID = id;
		INPUT = input;
		BLOCK_ON_TOP = block_on_top;
		IS_DYE_FILLER = is_dye_filler;
		AMOUNT = amount;
	}
	
	public boolean isDyeFiller() {
		return IS_DYE_FILLER;
	}
	
	public int getAmount() {
		return AMOUNT;
	}
	
	public Block getBlockOnTop() {
		return BLOCK_ON_TOP;
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
		return new ItemStack(BLOCK_ON_TOP);
	}
	
	@Override
	public boolean canCraftInDimensions(int w, int h) {
		return false;
	}
	
	@Override
	public ItemStack getResultItem() {
		return new ItemStack(BLOCK_ON_TOP);
	}
	
	@Override
	public ResourceLocation getId() {
		return ID;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		if(!isDyeFiller())
			return NonNullList.of(null, INPUT, Ingredient.of(BLOCK_ON_TOP));
		else
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
    
    public static class Type implements RecipeType<ModelAssemblerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "model_assembler";
    }
    
    public static class Serializer implements RecipeSerializer<ModelAssemblerRecipe>{
    	public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Threedee.MOD_ID,"model_assembler");

    	@Override
    	public ModelAssemblerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
    		try {
	    		Ingredient input = Ingredient.fromJson(json.getAsJsonObject("input"));
	    		boolean is_dye_filler = json.has("is_dye_filler") && json.get("is_dye_filler").getAsBoolean();
	    		String output = "minecraft:air";
	    		int amount = json.get("amount").getAsInt();
	    		
	    		
	    		if(!is_dye_filler)
	    			output = json.get("block_on_top").getAsString();	
	    		
	    		Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(output));
	    		
	    		return new ModelAssemblerRecipe(recipeId, input, block, is_dye_filler, amount);
    		} catch (ClassCastException | IllegalStateException | JsonSyntaxException e) {
				Threedee.LOGGER.error("Can't process malformed recipe! Recipe id is " + recipeId + ". Error: " + e.getMessage());
				return null;
			}
    	}

    	@Override
    	public ModelAssemblerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
    		Ingredient input = Ingredient.fromNetwork(buffer);
    		boolean is_dye_filler = buffer.readBoolean();
    		String output = "minecraft:air";
    		int fill_amount = buffer.readInt();
    		
    		if(!is_dye_filler)
    			output = buffer.readUtf();

    		Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(output));
    		
    		return new ModelAssemblerRecipe(recipeId, input, block, is_dye_filler, fill_amount);
    	}

    	@Override
    	public void toNetwork(FriendlyByteBuf buffer, ModelAssemblerRecipe recipe) {
    		Ingredient input = recipe.getIngredients().get(0);
    		
    		input.toNetwork(buffer);
    		boolean is_dye_filler = recipe.isDyeFiller();
    		buffer.writeBoolean(is_dye_filler);
    		buffer.writeInt(recipe.getAmount());
    		
    		if(!is_dye_filler)
    			buffer.writeUtf(recipe.getBlockOnTop().getRegistryName().toString());
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