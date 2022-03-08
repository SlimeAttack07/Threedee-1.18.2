package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.tileentity.HeadBlockTE;

public class Head extends CustomBlockBaseRotatable implements EntityBlock{
	
	private DropRarity rarity;
	
	public Head(DropRarity rar) {
		super(1, 1);
		rarity = rar;
	}
	
	public Head(int type) {
		super(1, 1);
		
		switch (type) {
		case 0: rarity = DropRarity.COMMON; break;
		case 1: rarity = DropRarity.UNCOMMON; break;
		case 2: rarity = DropRarity.RARE; break;
		case 3: rarity = DropRarity.EPIC; break;
		case 4: rarity = DropRarity.LEGENDARY; break;
		default: rarity = DropRarity.ANCIENT;
		}
	}
	
	public DropRarity getRarity() {
		return rarity;
	}
	
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		BlockEntity tile = level.getBlockEntity(pos);
		
		if(tile instanceof HeadBlockTE && rarity.equals(DropRarity.ANCIENT)) {
			if(stack.hasTag()) {
				CompoundTag tag = stack.getTag();
				
				if(tag.contains(Threedee.MOD_ID)) {
					
					HeadBlockTE te = (HeadBlockTE) tile;
					
					CompoundTag nbt = tag.getCompound(Threedee.MOD_ID);
					boolean can_be_sold = nbt.getBoolean("can_be_sold");
					boolean prices_known = nbt.getBoolean("prices_known");
					int min = nbt.getInt("min");
					int max = nbt.getInt("max");
					te.setData(can_be_sold, prices_known, min, max);
				}
			}
			
		}
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return TDTileEntityTypes.HEAD_BLOCK.get().create(pos, state);
	}
}