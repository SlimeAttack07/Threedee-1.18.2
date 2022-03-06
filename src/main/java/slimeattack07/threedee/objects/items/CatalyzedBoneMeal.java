package slimeattack07.threedee.objects.items;

import java.util.List;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.util.TdBasicMethods;

public class CatalyzedBoneMeal extends Item {
	public CatalyzedBoneMeal() {
		super(new Item.Properties().tab(Threedee.TD_ITEMS));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
		if (Screen.hasShiftDown())
			tooltip.add(TdBasicMethods.createGrayText("description.threedee.cat_bone_meal"));
		else
			tooltip.add(TdBasicMethods.createGrayText("instruction.threedee.general.hold_shift"));
		
		super.appendHoverText(stack, level, tooltip, flagIn);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {		
		ItemStack item = context.getItemInHand();
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		Block block = state.getBlock();

		if(level.isClientSide())
			return InteractionResult.SUCCESS;
		
		if (block instanceof CropBlock) {
			CropBlock crop = (CropBlock) block;
			int max_stage = crop.getMaxAge();
			level.setBlockAndUpdate(pos, crop.getStateForAge(max_stage));
			TdBasicMethods.reduceStack(item, 1);
			
			return InteractionResult.SUCCESS;
		}
		else
			return super.useOn(context);
	}
}