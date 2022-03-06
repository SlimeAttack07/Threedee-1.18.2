package slimeattack07.threedee.tileentity;

import java.util.List;
import java.util.Random;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.util.TdBasicMethods;
import slimeattack07.threedee.util.helpers.NBTHelper;

public class HeadFabricatorTE extends BlockEntity{
	boolean initialized = false;
	public boolean stackmode;
	public String last_recipe;
	public String loot_table;
	public int catalyst_amount;
	public int time_per_shot;
	public int current_time;
	
	private final int INC_TIME_STEP = 5;
	private final int MAX_TIME = 20 + INC_TIME_STEP;

	public HeadFabricatorTE(BlockPos pos, BlockState state) {
		super(TDTileEntityTypes.TD_HEADFABRICATOR.get(), pos, state);
	}
	
	public boolean getMode() {
		return stackmode;
	}
	
	public int getTPS() {
		return time_per_shot;
	}
	
	public void nextTimeStep() {
		time_per_shot = (time_per_shot + INC_TIME_STEP) % MAX_TIME;
	}
	
	public void setCatalystAmount(int amount) {
		if (amount >= 0)
			catalyst_amount = amount;
	}
	
	public void addCatalyst(int amount) {
		if (catalyst_amount + amount >= 0)
			catalyst_amount += amount;
		else
			catalyst_amount = 0;
	}
	
	public void decCatalyst() {
		if (catalyst_amount > 0)
			catalyst_amount--;
	}
	
	public void setLastRecipe(String recipe) {
		last_recipe = recipe;
	}
	
	public boolean toggleMode() {
		stackmode = !stackmode;
		return stackmode;
	}
	
	public void setLootTable(String loottable) {
		loot_table = loottable;
	}
	
	private void init() {
		initialized = true;
		stackmode = false;
		time_per_shot = 0;
		current_time = 0;
		last_recipe = "";
		loot_table = "";
	}
	
	public Vec3 genRandMotion(Random rand) {
		double x = ThreadLocalRandom.current().nextDouble(0, 0.2D);
		double y = ThreadLocalRandom.current().nextDouble(0, 0.2D);
		double neg_x = rand.nextInt(2);
		double neg_y = rand.nextInt(2);
		
		if(neg_x == 0)
			x = 0D - x;
		
		if(neg_y == 0)
			y = 0D - y;
		
		return new Vec3(x, 1D, y);
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
			this.catalyst_amount = initvalues.getInt("catalyst_amount");
			this.time_per_shot = initvalues.getInt("timepershot");
			this.current_time = initvalues.getInt("currenttime");
			this.last_recipe = initvalues.getString("lastrecipe");
			this.loot_table = initvalues.getString("loottable");
			initialized = true;
		}
		else	
			init();
	}
	
	public void tick() {
		if(!initialized)
			init();
		
		if (catalyst_amount <= 0)
			return;
		
		if (current_time >= time_per_shot) {
			
			current_time = 0;
			catalyst_amount--;
			
			Random rand = new Random();
			List<ItemStack> outputs = TdBasicMethods.genFromLootTable(level, loot_table);
			
			for(ItemStack output: outputs)
				TdBasicMethods.spawn(output, level, worldPosition, 0.5D, 1.6D, 0.5D, genRandMotion(rand));
			
			TdBasicMethods.playSound(level, worldPosition, SoundEvents.GENERIC_EXPLODE);
		} else 
			current_time++;
		
		setChanged();
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
	}
}