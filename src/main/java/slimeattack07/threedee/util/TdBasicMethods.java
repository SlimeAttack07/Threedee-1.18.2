package slimeattack07.threedee.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class TdBasicMethods {

	public static void reduceStack(ItemStack item, int amount) {
		item.setCount(item.getCount() - amount);
	}

	public static void destroyStack(ItemStack item) {
		item.setCount(0);
	}

	public static boolean tryAdding(Player player, ItemStack item_in) {
		if(item_in.isEmpty())
			return false;
		
		return player.getInventory().add(item_in.copy());
	}
	
	public static void spawn(ItemStack item_in, Level level, BlockPos pos, double xoff, double yoff, double zoff, Vec3 motion) {
		if(item_in.isEmpty())
			return;
		
		ItemEntity ent = new ItemEntity(level, pos.getX() + xoff, pos.getY() + yoff, pos.getZ() + zoff, item_in.copy());
		ent.setDeltaMovement(motion);
		level.addFreshEntity(ent);
	}
	
	public static void spawn(ItemStack item_in, Level level, BlockPos pos, double xoff, double yoff, double zoff) {
		if(item_in.isEmpty())
			return;
	
		ItemEntity ent = new ItemEntity(level, pos.getX() + xoff, pos.getY() + yoff, pos.getZ() + zoff, item_in.copy());
		level.addFreshEntity(ent);
	}

	public static void addOrSpawn(Player player, ItemStack item_in, Level level, BlockPos pos, double xoff, double yoff, double zoff, Vec3 motion) {
		if (!tryAdding(player, item_in)) {
			spawn(item_in, level, pos, xoff, yoff, zoff, motion);
		}
	}

	public static void addOrSpawn(Player player, ItemStack item_in, Level level, BlockPos pos, double xoff, double yoff, double zoff) {
		if (!tryAdding(player, item_in)) {
			spawn(item_in, level, pos, xoff, yoff, zoff);
		}
	}

	public static void addOrSpawn(Player player, ItemStack item_in, Level level, BlockPos pos, Vec3 motion) {
		addOrSpawn(player, item_in, level, pos, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, motion);
	}

	public static void addOrSpawn(Player player, ItemStack item_in, Level level, BlockPos pos) {
		addOrSpawn(player, item_in, level, pos, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
	}

	public static void messagePlayerBack(Player player, String translation, String back) {
		player.sendMessage(
				new TextComponent(new TranslatableComponent(translation).getString() + back), Util.NIL_UUID);
	}
	
	public static void messagePlayerFront(Player player, String front, String translation) {
		player.sendMessage(
				new TextComponent(front + new TranslatableComponent(translation).getString()), Util.NIL_UUID);
	}

	public static void messagePlayer(@Nullable Player player, String translation) {
		if(player != null)
			player.sendMessage(new TextComponent(new TranslatableComponent(translation).getString()), Util.NIL_UUID);
	}
	
	public static void messagePlayerCustom(@Nullable Player player, String message) {
		if(player != null)
			player.sendMessage(new TextComponent(message), Util.NIL_UUID);
	}
	
	public static String getTranslation(String translation) {
		return new TranslatableComponent(translation).getString();
	}
	
	public static TextComponent createColoredText(String translation, ChatFormatting color, boolean newline) {
		String text = new TranslatableComponent(translation).getString();
		
		if(newline)
			text += System.lineSeparator();
		
		return new TextComponent(color + text);
	}
	
	public static TextComponent createColoredText(String translation, ChatFormatting color) {
		return createColoredText(translation, color, false);
	}
	
	public static TextComponent createBlueText(String translation) {
		return createColoredText(translation, ChatFormatting.BLUE);
	}
	
	public static TextComponent createGrayText(String translation) {
		return createColoredText(translation, ChatFormatting.GRAY);
	}
	
	public static TextComponent createGrayText(String translation, String back) {
		String text = getTranslation(translation);
		
		return createColoredText(text + back, ChatFormatting.GRAY);
	}
	
	public static TextComponent createRedText(String translation) {
		return createColoredText(translation, ChatFormatting.RED);
	}
	
	public static TextComponent createGreenText(String translation) {
		return createColoredText(translation, ChatFormatting.GREEN);
	}
	
	public static TextComponent createDGreenText(String translation) {
		return createColoredText(translation, ChatFormatting.DARK_GREEN);
	}

	public static void playSound(Level level, BlockPos pos, SoundEvent sound) {
		level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), sound, SoundSource.MASTER, 1.0f, 1.0f);
	}
	
	public static boolean safeContains(String string, String sub) {
		String[] split = string.split(":");
		// Note that this is safe because registry names always contain exactly 1 ':', meaning that split always is of length 2.
		
		return split[1].contains(sub);
	}
	
	public static String safeReplace(String string, String old_sub, String new_sub) {
		String[] split = string.split(":");
		// Note that this is safe because registry names always contain exactly 1 ':', meaning that split always is of length 2.
		
		return split[0] + ":" + split[1].replace(old_sub, new_sub);
	}
	
	public static Set<Recipe<?>> findRecipesByType(RecipeType<?> typeIn, Level level) {
		return level != null ? level.getRecipeManager().getRecipes().stream()
				.filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
	}
	
	public static Set<Recipe<?>> findRecipesByType(RecipeType<?> typeIn, RecipeManager recipe_manager) {
		return recipe_manager.getRecipes().stream()
				.filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet());
	}
	
	public static Recipe<?> getRecipe(String id, Level level) {
		ResourceLocation recipe_id = new ResourceLocation(id);
		
		Optional<? extends Recipe<?>> recipe = level.getRecipeManager().byKey(recipe_id);
		
		if (recipe.isPresent()) {
			return recipe.get();
		}
		
		return null;
	}
	
	public static ItemStackHandler getHandler(ItemStack stack) {
		ItemStackHandler handler = new ItemStackHandler();
		handler.insertItem(0, stack, false);
		return handler;
	}

	public static RecipeWrapper getWrapper(ItemStack stack) {
		return new RecipeWrapper(TdBasicMethods.getHandler(stack));
	}
	
	public static ItemStack getMatchingStack(Ingredient ingredient, ItemStack in) {
		for (ItemStack stack : ingredient.getItems()) {
			if(stack.getItem() == in.getItem())
				return stack;
		}
		
		return null;
	}
	
	public static List<ItemStack> genFromLootTable(Level level, String loot_table) {
		if(loot_table == null)
			return new ArrayList<>();
		
		LootTable table = level.getServer().getLootTables().get(new ResourceLocation(loot_table));
		LootContext.Builder lootcontext$builder = new LootContext.Builder(level.getServer().overworld());
		LootContextParamSet.Builder lps$builder = new LootContextParamSet.Builder();
		LootContext context = lootcontext$builder.create(lps$builder.build());
		
		return table.getRandomItems(context);
	}
}