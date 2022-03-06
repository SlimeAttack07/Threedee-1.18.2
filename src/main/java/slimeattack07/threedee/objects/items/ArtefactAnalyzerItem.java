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

public class ArtefactAnalyzerItem extends BlockItem {

	public ArtefactAnalyzerItem(Block blockIn, Properties prop) {
		super(blockIn, prop);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {

		if (Screen.hasShiftDown()) {
			tooltip.add(TdBasicMethods.createBlueText("description.threedee.artefact_analyzer"));
		} else
			tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_shift"));

		if (Screen.hasControlDown()) {
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.artefact_analyzer.rightclick"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.artefact_analyzer.limit"));
			tooltip.add(TdBasicMethods.createRedText("instruction.threedee.artefact_analyzer.broken"));
		} else {
			tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_control"));
		}

		super.appendHoverText(stack, level, tooltip, flagIn);
	}
}