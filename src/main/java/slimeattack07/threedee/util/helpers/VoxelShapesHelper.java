package slimeattack07.threedee.util.helpers;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelShapesHelper {
	
	public static VoxelShape getVoxelFromType(int type, Direction dir) {
//		switch(type) {
//		
//		case 0: return bottleVoxel; // 0 = bottle
//		case 1: return headVoxel; // 1 = head
//		case 2: return getMortarPestleVoxel(dir); // 2 = mortar and pestle
//		case 3: return getTinyCauldronVoxel(dir); // 3 = tiny cauldron
//		case 4: return getHandsawVoxel(dir); // 4 = handsaw
//		case 5: return headFabVoxel; // 5 = head fabricator
//		case 6: return headAsAndRec; // 6 = head assembler or head recycler
//		case 7: return muffinVoxel; // 7 = muffin (c: 0)
//		case 8: return getPresentVoxel(dir); // 8 = present (c: 2, 3)
//		case 9: return getTomatoVoxel(dir); // 9 = tomato (c: 4)
//		case 10: return getIngotVoxel(dir); // 10 = ingot (c: 6, 7, 8, 9, 10. u: 2, 6, 7, 8, 19. r: 1, 2, 3, 4, 5, 6, 12. e: 1, 2, 3, 4. l: 1, 2, 3, 24)
//		case 11: return artefactAn; // 11 = artefact analyzer
//		case 12: return getNegotiatorVoxel(dir); // 12 = negotiator
//		case 13: return artefactex; // 13 = artefact exchanger
//		
//		default: return VoxelShapes.fullCube();
//		}
		
		return Block.box(0.1, 0, 0.1, 15.9, 15, 15.9);
	}
}