package slimeattack07.threedee.util.helpers;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

public class PropertiesHelper {
	
	public static Properties create(int type, boolean tool) {
		Material mat = Material.STONE;
		float hardness = 0.4f;
		float resistance = 2.0f;
		SoundType sound = SoundType.STONE;
		
		switch(type) {
		case -1: // Stone
			break;
		case 0: // Glass Bottle
			mat = Material.GLASS;
			sound = SoundType.GLASS;
			break;
		case 1: // Bamboo
			mat = Material.BAMBOO;
			sound = SoundType.WOOL;
		default:
			break;
		}
		
		Properties prop = Properties.of(mat).strength(hardness, resistance).sound(sound);
		
		return tool ? prop.requiresCorrectToolForDrops() : prop;
	}
}
