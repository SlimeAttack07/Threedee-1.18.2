package slimeattack07.threedee.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.util.TdBasicMethods;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class HeadRecyclerTE extends BlockEntity{
	boolean initialized = false;
	public boolean stackmode;
	public int current_time;
	public List<ItemStack> outputs;
	public List<ItemStack> cor_outputs;
	public List<Float> rec_times;
	public List<Integer> amounts;
	public String last_recipe;
	public static final int BUFFER_SIZE = 5;
	
	public HeadRecyclerTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.TD_HEADRECYCLER.get(), pos, state);
	}
	
	public boolean getMode() {
		return stackmode;
	}
	
	public boolean updateItemstackInLists(ItemStack output, ItemStack cor_output, float ticks_needed, int amount) {
		if(amount == 0)
			return false;
		
		if(!initialized)
			init();
		
		int index = outputs.indexOf(output);
		
		if(amount > 0) {
			if(index < 0) {
				if(outputs.size() >= BUFFER_SIZE)
					return false;
				
				outputs.add(output);
				cor_outputs.add(cor_output);
				rec_times.add(ticks_needed);
				amounts.add(amount);
			}
			else
				amounts.set(index, amounts.get(index) + amount);
		}
		else {
			if(index < 0)
				return false;
			
			if(amounts.get(index) <= -1 * amount) {
				outputs.remove(index);
				cor_outputs.remove(index);
				rec_times.remove(index);
				amounts.remove(index);
			}
			else
				amounts.set(index, amounts.get(index) + amount); // use + instead of -, since we know amount is negative
		}
		
		return true;
	}
	
	public boolean toggleMode() {
		stackmode = !stackmode;
		return stackmode;
	}
	
	public void setLastRecipe(String recipe) {
		last_recipe = recipe;
	}
	
	public void init() {
		initialized = true;
		stackmode = false;
		current_time = 0;
		last_recipe = "";
		outputs = new ArrayList<>();
		cor_outputs = new ArrayList<>();
		rec_times = new ArrayList<>();
		amounts = new ArrayList<>();
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
			this.stackmode = initvalues.getBoolean("stackmode");
			this.current_time = initvalues.getInt("currenttime");
			this.last_recipe = initvalues.getString("lastrecipe");
			CompoundTag output = initvalues.getCompound("outputs");
			CompoundTag cor_output = initvalues.getCompound("cor_outputs");
			CompoundTag rec_time = initvalues.getCompound("rec_times");
			CompoundTag amount = initvalues.getCompound("amounts");
			
			for(int i = 0; i < BUFFER_SIZE; i++) {
				CompoundTag nbt = output.getCompound("" + i);
				
				if(nbt == null)
					break;
				
				if(!initialized)
					init();
				
				outputs.add((ItemStack) NBTHelper.fromNBT(nbt));
				cor_outputs.add((ItemStack) NBTHelper.fromNBT(cor_output.getCompound("" + i)));
				rec_times.add(rec_time.getFloat("" + i));
				amounts.add(amount.getInt("" + i));
			}
			
			initialized = true;
		}
		else	
			init();
	}
	
	public void tick() {		
		if(!initialized || rec_times.size() == 0) 
			return;
		
		if(current_time >= rec_times.get(0)) {
			current_time = 0;
			ItemStack output = outputs.get(0);
			updateItemstackInLists(output, ItemStack.EMPTY, 0, -1);
			TdBasicMethods.spawn(output, level, worldPosition, 0.5D, 1.6D, 0.5D);
			TdBasicMethods.playSound(level, worldPosition, SoundEvents.CONDUIT_ACTIVATE);
		} else
			current_time++;
		
		iAmDirty();
	}
	
	public void iAmDirty() {
		setChanged();
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
	}
}