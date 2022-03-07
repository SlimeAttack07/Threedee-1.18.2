package slimeattack07.threedee.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.tileentity.hoppers.HopperAncientTE;
import slimeattack07.threedee.tileentity.hoppers.HopperCommonTE;
import slimeattack07.threedee.tileentity.hoppers.HopperEpicTE;
import slimeattack07.threedee.tileentity.hoppers.HopperLegendaryTE;
import slimeattack07.threedee.tileentity.hoppers.HopperRareTE;
import slimeattack07.threedee.tileentity.hoppers.HopperUncommonTE;

public class HopperRarity extends HopperBlock implements EntityBlock{
	private DropRarity filter;

	public HopperRarity(DropRarity rarity) {
		super(Block.Properties.of(Material.METAL, MaterialColor.STONE).requiresCorrectToolForDrops().
				strength(3.0F, 4.8F).sound(SoundType.METAL).noOcclusion());
		filter = rarity;
	}
	
	public DropRarity getFilter() {
		return filter;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		switch(filter) {
		case COMMON:
			return TDTileEntityTypes.COMMON_HOPPER.get().create(pos, state);
		case UNCOMMON:
			return TDTileEntityTypes.UNCOMMON_HOPPER.get().create(pos, state);
		case RARE:
			return TDTileEntityTypes.RARE_HOPPER.get().create(pos, state);
		case EPIC:
			return TDTileEntityTypes.EPIC_HOPPER.get().create(pos, state);
		case LEGENDARY:
			return TDTileEntityTypes.LEGENDARY_HOPPER.get().create(pos, state);
		case ANCIENT:
			return TDTileEntityTypes.ANCIENT_HOPPER.get().create(pos, state);
		default:
			return TDTileEntityTypes.COMMON_HOPPER.get().create(pos, state);
		}
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		if(level.isClientSide())
			return null;
		
		switch(filter) {
		case COMMON:
			return createTickerHelper(type, TDTileEntityTypes.COMMON_HOPPER.get(), HopperCommonTE::pushItemsTick);
		case UNCOMMON:
			return createTickerHelper(type, TDTileEntityTypes.UNCOMMON_HOPPER.get(), HopperUncommonTE::pushItemsTick);
		case RARE:
			return createTickerHelper(type, TDTileEntityTypes.RARE_HOPPER.get(), HopperRareTE::pushItemsTick);
		case EPIC:
			return createTickerHelper(type, TDTileEntityTypes.EPIC_HOPPER.get(), HopperEpicTE::pushItemsTick);
		case LEGENDARY:
			return createTickerHelper(type, TDTileEntityTypes.LEGENDARY_HOPPER.get(), HopperLegendaryTE::pushItemsTick);
		case ANCIENT:
			return createTickerHelper(type, TDTileEntityTypes.ANCIENT_HOPPER.get(), HopperAncientTE::pushItemsTick);
		default:
			return null;
		
		}
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity BlockEntity = level.getBlockEntity(pos);
			if (BlockEntity instanceof HopperCommonTE) {
				((HopperCommonTE) BlockEntity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		} else {
			BlockEntity BlockEntity = level.getBlockEntity(pos);
			if (BlockEntity instanceof HopperCommonTE) {
				player.openMenu((HopperCommonTE) BlockEntity);
				player.awardStat(Stats.INSPECT_HOPPER);
			}

			return InteractionResult.CONSUME;
		}
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof HopperCommonTE) {
				Containers.dropContents(level, pos, (HopperCommonTE) be);
				level.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
	      BlockEntity blockentity = level.getBlockEntity(pos);
	      if (blockentity instanceof HopperCommonTE) {
	    	  HopperCommonTE.entityInside(level, pos, state, entity, (HopperCommonTE)blockentity);
	      }

	   }
}
