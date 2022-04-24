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

public class EpicModelBlocks {
	public static final DeferredRegister<Block> EPIC = DeferredRegister.create(ForgeRegistries.BLOCKS, Threedee.MOD_ID);
	
	public static final RegistryObject<Block> BOTTLE_LAVA = EPIC.register("bottle_lava", () -> model(true, ModShapes.W8_H12, 0));	
	
//	private static Model model(VoxelShape shape, int shape_type) {
//		return new Model(DropRarity.EPIC, false, shape, shape_type);
//	}
	
	private static Model model(boolean translucent, VoxelShape shape, int shape_type) {
		return new Model(DropRarity.COMMON, translucent, shape, shape_type);
	}
}
