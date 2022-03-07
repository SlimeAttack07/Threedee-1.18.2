package slimeattack07.threedee.util;

import javax.annotation.Nonnull;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.InvWrapper;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.objects.items.HeadBlockItem;
import slimeattack07.threedee.tileentity.hoppers.HopperCommonTE;

/**
 * 
 * @author Forge, with small modifications by SlimeAttack07 to filter certain items.
 *
 */
public class HopperRarityItemHandler extends InvWrapper{
    private final HopperCommonTE hopper;
    private final DropRarity filter;

    public HopperRarityItemHandler(HopperCommonTE hopper, DropRarity filter){
        super(hopper);
        this.hopper = hopper;
        this.filter = filter;
    }

    private boolean canFilter(ItemStack stack) {
    	if(stack.getItem() instanceof HeadBlockItem) {
    		HeadBlockItem item = (HeadBlockItem) stack.getItem();
    		
    		return item.getRarity().equals(filter);
    	}
    	
    	return false;
    }
    
    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    	if(!canFilter(stack))
    		return stack;
    	
        if (simulate)
            return super.insertItem(slot, stack, simulate);
        else {
            boolean wasEmpty = getInv().isEmpty();

            int originalStackSize = stack.getCount();
            stack = super.insertItem(slot, stack, simulate);

            if (wasEmpty && originalStackSize > stack.getCount()) {
                if (!hopper.isOnCustomCooldown())
                    hopper.setCooldown(8);
            }

            return stack;
        }
    }
}