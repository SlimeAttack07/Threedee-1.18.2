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

public class ModelFabricatorItem extends BlockItem {

	public ModelFabricatorItem(Block blockIn, Properties prop) {
		super(blockIn, prop);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {

		if (Screen.hasShiftDown()) {
			tooltip.add(TdBasicMethods.createBlueText("description.threedee.model_fabricator"));
		} else
			tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_shift"));

		if (Screen.hasControlDown()) {
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.model_fabricator.rightclick_catalyst"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.model_fabricator.rightclick_empty"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.model_fabricator.limit"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.general.leftclick"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.model_fabricator.leftclick_shift"));
			tooltip.add(TdBasicMethods.createRedText("instruction.threedee.model_fabricator.broken"));
		} else {

			tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_control"));
		}

		super.appendHoverText(stack, level, tooltip, flagIn);
	}
}