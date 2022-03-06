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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.tileentity.HopperRarityTE;

public class HopperRarity extends HopperBlock implements EntityBlock{
	private DropRarity filter;

	public HopperRarity(DropRarity rarity) {
		super(Block.Properties.of(Material.METAL, MaterialColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 4.8F).sound(SoundType.METAL).noOcclusion());
		filter = rarity;
	}
	
	public DropRarity getFilter() {
		return filter;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return TDTileEntityTypes.TD_HOPPERRARITY.get().create(pos, state);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity BlockEntity = level.getBlockEntity(pos);
			if (BlockEntity instanceof HopperRarityTE) {
				((HopperRarityTE) BlockEntity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		} else {
			BlockEntity BlockEntity = level.getBlockEntity(pos);
			if (BlockEntity instanceof HopperRarityTE) {
				player.openMenu((HopperRarityTE) BlockEntity);
				player.awardStat(Stats.INSPECT_HOPPER);
			}

			return InteractionResult.CONSUME;
		}
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof HopperRarityTE) {
				Containers.dropContents(level, pos, (HopperRarityTE) be);
				level.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
	      BlockEntity blockentity = level.getBlockEntity(pos);
	      if (blockentity instanceof HopperRarityTE) {
	    	  HopperRarityTE.entityInside(level, pos, state, entity, (HopperRarityTE)blockentity);
	      }

	   }
}
