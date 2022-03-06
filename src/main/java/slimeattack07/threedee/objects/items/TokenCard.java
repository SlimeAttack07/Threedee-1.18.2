package slimeattack07.threedee.objects.items;

import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.util.TdBasicMethods;

public class TokenCard extends Item {
	private final boolean is_creative;

	public TokenCard(boolean make_creative) {
		super(new Item.Properties().tab(Threedee.TD_ITEMS));
		is_creative = make_creative;
	}
	
	public boolean isCreative() {
		return is_creative;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
		if(is_creative)
			tooltip.add(TdBasicMethods.createGrayText("misc.threedee.creative_card"));
		else {
			boolean needs_tag = true;
			CompoundTag nbt = stack.getOrCreateTag();
			int balance = 0;

			if(nbt.contains(Threedee.MOD_ID)) {
				CompoundTag data = nbt.getCompound(Threedee.MOD_ID);
				
				if(data.contains("card_balance")) {
					needs_tag = false;
					balance = data.getInt("card_balance");
				}
			}
			
			if(needs_tag) {
				CompoundTag added_nbt = stack.getOrCreateTagElement(Threedee.MOD_ID);
				added_nbt.putInt("card_balance", 0);
				nbt.put(Threedee.MOD_ID, added_nbt);
				stack.setTag(nbt);
			}
			
			tooltip.add(TdBasicMethods.createGrayText("misc.threedee.normal_card", " " + balance));
		}
		
		super.appendHoverText(stack, level, tooltip, flagIn);
	}
	
	public static void updateBalance(ItemStack stack, int amount) {
		if(!(stack.getItem() instanceof TokenCard) || ((TokenCard) stack.getItem()).isCreative())
			return;
		
		CompoundTag nbt = stack.getOrCreateTag();

		if(nbt.contains(Threedee.MOD_ID)) {
			CompoundTag data = nbt.getCompound(Threedee.MOD_ID);
			
			if(data.contains("card_balance")) {
				int balance = data.getInt("card_balance");
				data.remove("card_balance");
				data.putInt("card_balance", Math.max(0, balance + amount));
				return;
			}
		}
		
		CompoundTag added_nbt = stack.getOrCreateTagElement(Threedee.MOD_ID);
		added_nbt.putInt("card_balance", amount);
		nbt.put(Threedee.MOD_ID, added_nbt);
		stack.setTag(nbt);
	}
	
	public static int getBalance(ItemStack stack) {
		if(!(stack.getItem() instanceof TokenCard))
			return -2;
		
		if(((TokenCard) stack.getItem()).isCreative())
			return -1;
		
		CompoundTag nbt = stack.getOrCreateTag();

		if(nbt.contains(Threedee.MOD_ID)) {
			CompoundTag data = nbt.getCompound(Threedee.MOD_ID);
			
			if(data.contains("card_balance")) {
				return data.getInt("card_balance");
			}
		}
		
		CompoundTag added_nbt = stack.getOrCreateTagElement(Threedee.MOD_ID);
		nbt.put(Threedee.MOD_ID, added_nbt);
		stack.setTag(nbt);
		
		return 0;
	}
}
