package slimeattack07.threedee.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.objects.blocks.Model;
import slimeattack07.threedee.util.helpers.ModShapes;

public class UncommonModelBlocks {
	public static final DeferredRegister<Block> UNCOMMON = DeferredRegister.create(ForgeRegistries.BLOCKS, Threedee.MOD_ID);
	
	public static final RegistryObject<Block> BOTTLE_WHITE_WATER = UNCOMMON.register("bottle_white_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_ORANGE_WATER = UNCOMMON.register("bottle_orange_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_MAGENTA_WATER = UNCOMMON.register("bottle_magenta_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_LIGHT_BLUE_WATER = UNCOMMON.register("bottle_light_blue_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_YELLOW_WATER = UNCOMMON.register("bottle_yellow_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_LIME_WATER = UNCOMMON.register("bottle_lime_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_PINK_WATER = UNCOMMON.register("bottle_pink_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_GRAY_WATER = UNCOMMON.register("bottle_gray_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_LIGHT_GRAY_WATER = UNCOMMON.register("bottle_light_gray_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_CYAN_WATER = UNCOMMON.register("bottle_cyan_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_PURPLE_WATER = UNCOMMON.register("bottle_purple_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_BLUE_WATER = UNCOMMON.register("bottle_blue_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_BROWN_WATER = UNCOMMON.register("bottle_brown_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_GREEN_WATER = UNCOMMON.register("bottle_green_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_RED_WATER = UNCOMMON.register("bottle_red_water", () -> model(true, ModShapes.W8_H12, 0));
	public static final RegistryObject<Block> BOTTLE_BLACK_WATER = UNCOMMON.register("bottle_black_water", () -> model(true, ModShapes.W8_H12, 0));	
	
//	private static Model model(VoxelShape shape, int shape_type) {
//		return new Model(DropRarity.UNCOMMON, false, shape, shape_type);
//	}
	
	private static Model model(boolean translucent, VoxelShape shape, int shape_type) {
		return new Model(DropRarity.UNCOMMON, translucent, shape, shape_type);
	}
}
