package slimeattack07.threedee.objects.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class BlockBase extends Block {

	public BlockBase(Material mat, float hard, float resist, SoundType sound) {
		super(Block.Properties.of(mat).strength(hard, resist).sound(sound));
	}
	
	public BlockBase(Material mat, SoundType sound) {
		super(Block.Properties.of(mat).strength(0.6f, 3.0f).sound(sound));
	}
	
	public BlockBase(Block block) {
		super (Block.Properties.copy(block));
	}
}