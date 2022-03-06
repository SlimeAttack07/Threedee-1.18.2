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

public class HeadAssemblerItem extends BlockItem {

	public HeadAssemblerItem(Block blockIn, Properties prop) {
		super(blockIn, prop);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {

		if (Screen.hasShiftDown()) {
			tooltip.add(TdBasicMethods.createBlueText("description.threedee.head_assembler"));
		} else
			tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_shift"));

		if (Screen.hasControlDown()) {
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.head_assembler.rightclick_paste"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.head_assembler.rightclick_empty"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.head_assembler.head_top"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.head_assembler.rightclick_catalyst"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.general.leftclick"));
			tooltip.add(TdBasicMethods.createRedText("instruction.threedee.head_assembler.broken"));
		} else {
			tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_control"));
		}

		super.appendHoverText(stack, level, tooltip, flagIn);
	}
}