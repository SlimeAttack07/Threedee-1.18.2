package slimeattack07.threedee.objects.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.Threedee;

public class CatalystItem extends Item{
	private DropRarity rarity;
	private ChatFormatting color;

	public CatalystItem(DropRarity rar) {
		super(new Item.Properties().tab(Threedee.TD_ITEMS));
		rarity = rar;
		color = findRarity(rarity);
	}
	
	@Override
	public Component getName(ItemStack stack) {
		return new TextComponent(color + super.getName(stack).getString());
	}
	
	public DropRarity getRarity() {
		return rarity;
	}
	
	public ChatFormatting getColor() {
		return color;
	}
	
	public static ChatFormatting findRarity(DropRarity rarity) {
		switch (rarity) {
		case COMMON: return ChatFormatting.WHITE;
		case UNCOMMON: return ChatFormatting.GREEN;
		case RARE: return ChatFormatting.AQUA;
		case EPIC: return ChatFormatting.DARK_PURPLE;
		case LEGENDARY: return ChatFormatting.GOLD;
		default: return ChatFormatting.RED;
		}
	}
}