package slimeattack07.threedee.objects.items;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import slimeattack07.threedee.tileentity.HeadRecyclerTE;
import slimeattack07.threedee.util.TdBasicMethods;

public class HeadRecyclerItem extends BlockItem {

	public HeadRecyclerItem(Block blockIn, Properties prop) {
		super(blockIn, prop);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {

		if (Screen.hasShiftDown()) 
			tooltip.add(TdBasicMethods.createBlueText("description.threedee.head_recycler"));
		else
			tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_shift"));

		if (Screen.hasControlDown()) {
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.head_recycler.rightclick"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.head_recycler.order"));
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.head_recycler.rightclick_empty"));
			
			String text = TdBasicMethods.getTranslation("instruction.threedee.head_recycler.limit");
			text = text.replace("MARKER1", HeadRecyclerTE.BUFFER_SIZE + "");
			tooltip.add(new TextComponent(ChatFormatting.BLUE + text));
			
			tooltip.add(TdBasicMethods.createBlueText("instruction.threedee.general.leftclick"));
			tooltip.add(TdBasicMethods.createRedText("instruction.threedee.head_recycler.broken"));
		} else {

			tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_control"));
		}

		super.appendHoverText(stack, level, tooltip, flagIn);
	}
}