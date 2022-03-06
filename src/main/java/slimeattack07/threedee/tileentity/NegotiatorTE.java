package slimeattack07.threedee.tileentity;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.util.TdBasicMethods;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class NegotiatorTE extends BlockEntity{
	public int current_time;
	public boolean running;
	public ItemStack input = ItemStack.EMPTY;
	public static final int NEG_TIME = 3600;

	public NegotiatorTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.TD_NEGOTIATOR.get(), pos, state);
	}
	
	public void activate(ItemStack stack) {
		input = stack;
		running = true;
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
			current_time = tag.getInt("currenttime");
			running = tag.getBoolean("running");			
			input = (ItemStack) NBTHelper.fromNBT(tag.getCompound("input"));
		}
	}
	
	public ItemStack getOutput() {
		if(!running)
			return input;
		
		return ItemStack.EMPTY;
	}
	
	public void clearInput() {
		input = ItemStack.EMPTY;
	}
	
	public void tick() {	
		if(level.isClientSide() || !running || input.equals(ItemStack.EMPTY)) 
			return;
		
		if(current_time % 360 == 0 && current_time < NEG_TIME - 360) {
			TdBasicMethods.playSound(level, worldPosition, SoundEvents.VILLAGER_NO);
		}
		
		if(current_time >= NEG_TIME) {
			current_time = 0;		
			running = false;
			CompoundTag nbt = input.getTag();
			CompoundTag threedee = nbt.getCompound(Threedee.MOD_ID);
			
			ThreadLocalRandom rand = ThreadLocalRandom.current();
			int price = rand.nextInt(threedee.getInt("min"), threedee.getInt("max") + 1);
			
			threedee.remove("prices_known");
			threedee.putBoolean("prices_known", true);
			threedee.remove("price");
			threedee.putInt("price", price);
			
			TdBasicMethods.playSound(level, worldPosition, SoundEvents.VILLAGER_CELEBRATE);
		} else
			current_time++;
		
		setChanged();
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
	}
}