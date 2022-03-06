package slimeattack07.threedee.objects.items;

import java.util.List;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.objects.blocks.CustomBlockBaseRotatable;
import slimeattack07.threedee.objects.blocks.Head;
import slimeattack07.threedee.util.TdBasicMethods;

public class CatalyzationUpgrade extends Item {

	public CatalyzationUpgrade() {
		super(new Item.Properties().tab(Threedee.TD_ITEMS));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			tooltip.add(TdBasicMethods.createGrayText("description.threedee.cat_upgrade"));
			tooltip.add(TdBasicMethods.createRedText("description.threedee.cat_upgrade_limited"));
		}
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
		
		if (!(block instanceof Head))
			return InteractionResult.SUCCESS;
		
		String name = block.getRegistryName().toString();
		String modelname = TdBasicMethods.safeReplace(name, "head", "model");
		
		Block new_block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modelname));
		
		if (new_block == null || !(new_block instanceof CustomBlockBaseRotatable) || !((CustomBlockBaseRotatable) new_block).isBasedOnHead()) {
			TdBasicMethods.messagePlayer(context.getPlayer(), "message.threedee.no_upgrade");
			return InteractionResult.SUCCESS;
		}
		
		TdBasicMethods.reduceStack(item, 1);
		level.setBlockAndUpdate(pos, new_block.defaultBlockState());
		
		return InteractionResult.SUCCESS;
	}
}