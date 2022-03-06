package slimeattack07.threedee.objects.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class GlassBottle extends CustomBlockBaseRotatable {
	
	public GlassBottle() {
		super(Block.Properties.of(Material.GLASS).strength(0.4f, 2.0f).sound(SoundType.GLASS));
	}

	@Override
	public int getType() {
		return 0;
	}
}