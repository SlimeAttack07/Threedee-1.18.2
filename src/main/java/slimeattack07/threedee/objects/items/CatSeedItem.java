package slimeattack07.threedee.objects.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.Threedee;

public class CatSeedItem extends BlockItem {
	private ChatFormatting color;
	
	@Override
	public Component getName(ItemStack stack) {
		return new TextComponent(color + super.getName(stack).getString());
	}

	public CatSeedItem(Block blockIn, DropRarity rar) {
		super(blockIn, new Item.Properties().tab(Threedee.TD_ITEMS));
		color = CatalystItem.findRarity(rar);
	}

}
