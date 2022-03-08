package slimeattack07.threedee.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.objects.items.TokenCard;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class ItemExchangerTE extends BlockEntity {
	public ItemStack card = ItemStack.EMPTY;
	public String last_recipe = "";

	public ItemExchangerTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.ITEM_EXCHANGER.get(), pos, state);
	}
	
	public ItemStack getCard() {
		return card;
	}
	
	public void setCard(ItemStack stack) {
		if(stack.getItem() instanceof TokenCard) {
			card = stack;
		}
	}
	
	public boolean mininumBalance(int minimum) {
		int balance = TokenCard.getBalance(card);
		return hasCard() && (balance == -1 || balance >= minimum);
	}
	
	public int getBalance() {
		if (hasCard())
			return TokenCard.getBalance(card);
		else
			return 0;
	}
	
	public void emptyMachine() {
		card = ItemStack.EMPTY;
	}
	
	public boolean hasCard() {
		return card.getItem() instanceof TokenCard;
	}
	
	public ItemStack extractCard() {
		if(hasCard()) {
			ItemStack output = card;
			emptyMachine();
			return output;
		}
		
		return ItemStack.EMPTY;
	}
	
	public void updateCard(int amount) {
		if(hasCard()) 
			TokenCard.updateBalance(card, amount);
	}
	
	public String getLastRecipe() {
		return last_recipe;
	}
	
	public void setLastRecipe(String recipe) {
		last_recipe = recipe;
	}
	
	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.put(Threedee.MOD_ID, NBTHelper.toNBT(this));
	}
	
	@Override
	public void load( CompoundTag compound) {
		super.load(compound);
		CompoundTag tag = compound.getCompound(Threedee.MOD_ID);
		
		if(tag != null) {
			card = (ItemStack) NBTHelper.fromNBT(tag.getCompound("card"));
			last_recipe = tag.getString("last_recipe");
		}
	}
}