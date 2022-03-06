package slimeattack07.threedee.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.objects.items.TokenCard;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class ItemExchangerTE extends BlockEntity {
	boolean initialized = false;
	public ItemStack card;
	public String last_recipe;

	public ItemExchangerTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.TD_ITEMEXCHANGER.get(), pos, state);
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
	
	private void init() {
		initialized = true;
		card = ItemStack.EMPTY;
		last_recipe = "";
	}	
	
	public boolean hasCard() {
		if(card == null)
			card = ItemStack.EMPTY;
		
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
		compound.put("initvalues", NBTHelper.toNBT(this));
		
	}
	
	@Override
	public void load( CompoundTag compound) {
		super.load(compound);
		CompoundTag initvalues = compound.getCompound("initvalues");
		
		if(initvalues != null) {
			this.card = (ItemStack) NBTHelper.fromNBT(initvalues.getCompound("card"));
			this.last_recipe = initvalues.getString("last_recipe");
			initialized = true;
		}
		else	
			init();
	}
}