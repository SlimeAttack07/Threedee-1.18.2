package slimeattack07.threedee.objects.items;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import slimeattack07.threedee.util.TdBasicMethods;

public class HeadBasedModelItem extends BlockItem{

	public HeadBasedModelItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
		
	}
	
	public Item headFromModel(ItemStack stack) {
		String name = stack.getItem().getRegistryName().toString();
		String head = TdBasicMethods.safeReplace(name, "model", "head");
		
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(head));
		if(item == null)
			item = Items.AIR;
		
		return item;
	}

	@Override
	public Component getName(ItemStack stack) {
		Item item = headFromModel(stack);
		
		return item.getName(new ItemStack(item, 1));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
		Item item = headFromModel(stack);
		
		item.appendHoverText(stack, level, tooltip, flagIn);
	}
}