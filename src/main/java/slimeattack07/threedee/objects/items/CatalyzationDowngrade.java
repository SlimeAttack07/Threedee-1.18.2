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
import slimeattack07.threedee.objects.blocks.CustomBlockBase;
import slimeattack07.threedee.objects.blocks.Head;
import slimeattack07.threedee.util.TdBasicMethods;

public class CatalyzationDowngrade extends Item {

	public CatalyzationDowngrade() {
		super(new Item.Properties().tab(Threedee.TD_ITEMS));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) 
			tooltip.add(TdBasicMethods.createGrayText("description.threedee.cat_downgrade"));
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
		
		if (!(block instanceof CustomBlockBase))
			return InteractionResult.SUCCESS;
		
		CustomBlockBase cbb = (CustomBlockBase) block;
		
		if (!cbb.isBasedOnHead()) {
			TdBasicMethods.messagePlayer(context.getPlayer(), "message.threedee.no_downgrade");
			return InteractionResult.SUCCESS;
		}
		
		String name = block.getRegistryName().toString();
		String headname = TdBasicMethods.safeReplace(name, "model", "head");

		Block new_block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(headname));
		
		if (new_block == null || !(new_block instanceof Head))
			return InteractionResult.SUCCESS;

		TdBasicMethods.reduceStack(item, 1);
		level.setBlockAndUpdate(pos, new_block.defaultBlockState());
		
		return InteractionResult.SUCCESS;
	}
}