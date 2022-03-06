package slimeattack07.threedee.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.objects.items.TokenCard;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class ArtefactExchangerTE extends BlockEntity {
	boolean initialized = false;
	public ItemStack card;

	public ArtefactExchangerTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.TD_ARTEFACTEXCHANGER.get(), pos, state);
	}
	
	public ItemStack getCard() {
		return card;
	}
	
	public void setCard(ItemStack stack) {
		if(stack.getItem() instanceof TokenCard) {
			card = stack;
		}
	}
	
	public void emptyMachine() {
		card = ItemStack.EMPTY;
	}
	
	private void init() {
		initialized = true;
		card = ItemStack.EMPTY;
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
			initialized = true;
		}
		else	
			init();
	}
}