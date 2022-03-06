package slimeattack07.threedee.objects.items;

import java.util.List;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import slimeattack07.threedee.util.TdBasicMethods;

public class BasicInterItem extends BlockItem {
	private String description;

	public BasicInterItem(Block blockIn, Properties prop, String descript) {
		super(blockIn, prop);
		description = descript;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {

		if (Screen.hasShiftDown()) {
			tooltip.add(TdBasicMethods.createBlueText(description));
		} else
			tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_shift"));

		if (Screen.hasControlDown()) {
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.general.rightclick"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.general.leftclick"));
		} else
			tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_control"));

		super.appendHoverText(stack, level, tooltip, flagIn);
	}
}