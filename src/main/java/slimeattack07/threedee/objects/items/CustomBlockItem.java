package slimeattack07.threedee.objects.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CustomBlockItem extends BlockItem {

	public CustomBlockItem(Block blockIn, CreativeModeTab tab) {
		super(blockIn, new Item.Properties().tab(tab));
		setRegistryName(blockIn.getRegistryName());
	}
}