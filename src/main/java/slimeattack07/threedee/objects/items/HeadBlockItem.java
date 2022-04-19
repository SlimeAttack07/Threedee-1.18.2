package slimeattack07.threedee.objects.items;

import java.util.Iterator;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.objects.blocks.Head;
import slimeattack07.threedee.util.TdBasicMethods;

public class HeadBlockItem extends BlockItem{
	private DropRarity rarity;
	private ChatFormatting color;

	public HeadBlockItem(Block blockIn, Properties prop) {
		super(blockIn, prop);
		Head base = (Head) blockIn;
		rarity = base.getRarity(); 
		
		switch (rarity) {
		case COMMON: color = ChatFormatting.WHITE; break;
		case UNCOMMON: color = ChatFormatting.GREEN; break;
		case RARE: color = ChatFormatting.AQUA; break;
		case EPIC: color = ChatFormatting.DARK_PURPLE; break;
		case LEGENDARY: color = ChatFormatting.GOLD; break;
		case ANCIENT: color = ChatFormatting.RED; break;
		default: color = ChatFormatting.BLACK;
		}
	}
	
	public DropRarity getRarity() {
		return rarity;
	}
	
	public int getHeadNumber() {
		String regname = this.getRegistryName().toString();
		regname = regname.split(":")[1];
		regname = regname.replace("head_" + rarity.toString().toLowerCase() + "_", "");
		
		try {
			int number = Integer.parseInt(regname);
			return number;
		} catch (NumberFormatException e) {
			Threedee.LOGGER.info("Error parsing: " + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public Component getName(ItemStack stack) {
		return new TextComponent(color + super.getName(stack).getString());
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
		String type = rarity.toString().toLowerCase();
		String rar =  new TranslatableComponent("rarity.threedee." + type).getString();
		tooltip.add(new TextComponent(color + rar));
		
		if (rarity == DropRarity.ANCIENT) {
			if(Screen.hasShiftDown()) {
				String head = this.getRegistryName().toString().replace(":", ".");
				tooltip.add(TdBasicMethods.createGrayText("lore." + head + ".description"));
				tooltip.add(TdBasicMethods.createGrayText("lore." + head + ".history"));
				
				if(stack.hasTag()) {
					CompoundTag nbt = stack.getTag();
					if(nbt.contains(Threedee.MOD_ID)) {
						CompoundTag data = (CompoundTag) nbt.get(Threedee.MOD_ID);
						boolean can_be_sold = data.getBoolean("can_be_sold");
						boolean price_known = data.getBoolean("prices_known");
						
						if(can_be_sold) {
							if(price_known) {				
								tooltip.add(TdBasicMethods.createGrayText("lore." + head + ".buyer"));
								tooltip.add(new TextComponent("")); // separation for readability
								
								String range = TdBasicMethods.getTranslation("misc.threedee.artefact_price");
								range = range.replace("MARKER1", data.getInt("min") + "");
								range = range.replace("MARKER2", data.getInt("max") + "");
								tooltip.add(TdBasicMethods.createRedText(range));
								
								String price = TdBasicMethods.getTranslation("misc.threedee.artefact_offer");
								price = price.replace("MARKER1", data.getInt("price") + "");
								tooltip.add(TdBasicMethods.createRedText(price));
							}
							else {
								tooltip.add(new TextComponent(""));
								tooltip.add(TdBasicMethods.createGrayText("misc.threedee.artefact_saleable"));
							}
						}
						else {
							tooltip.add(new TextComponent(""));
							tooltip.add(TdBasicMethods.createGrayText("misc.threedee.artefact_decorative"));
						}
					}
				}
				else {
					tooltip.add(new TextComponent(""));
					tooltip.add(TdBasicMethods.createGrayText("misc.threedee.artefact_decorative"));
				}
			}
			else
				tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_shift"));
		}
		if(!stack.getItem().getRegistryName().toString().contains("model")) {
			Iterator<TagKey<Item>> it = stack.getTags().iterator();
			
			boolean has_sets = false;
			while(it.hasNext()) {
				ResourceLocation loc = it.next().location();
	
				if(loc.toString().contains(Threedee.MOD_ID + ":" + "models/")) {
					if(!has_sets) {
						has_sets = true;
						tooltip.add(TdBasicMethods.createDGreenText("misc.threedee.has_sets"));
					}
					tooltip.add(TdBasicMethods.createGreenText("tags." + loc.toString().replace(":", ".").replace("/", ".")));
				}
			}
			
			if(!has_sets)
				tooltip.add(TdBasicMethods.createDGreenText("misc.threedee.has_no_sets"));
		}
		
		super.appendHoverText(stack, level, tooltip, flagIn);
	}
}