package slimeattack07.threedee.util.helpers;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

public class PropertiesHelper {
	
	public static Properties create(int type, boolean tool) {
		Material mat = Material.BAMBOO;
		float hardness = 0.4f;
		float resistance = 2.0f;
		SoundType sound = SoundType.BAMBOO;
		
		switch(type) {
		case -1: // Stone
			mat = Material.STONE;
			sound = SoundType.STONE;
			break;
		case 0: // Glass
			sound = SoundType.GLASS;
			break;
		case 1: // Bamboo
			break;
		case 2: // Wood
			sound = SoundType.WOOD;
			break;
		case 3: // Wool
			sound = SoundType.WOOL;
			break;
		case 4: // Metal
			sound = SoundType.METAL;
			break;
		case 5: // Organic
			sound = SoundType.GRASS;
		default:
			break;
		}
		
		Properties prop = Properties.of(mat).strength(hardness, resistance).sound(sound);
		
		return tool ? prop.requiresCorrectToolForDrops() : prop;
	}
}
