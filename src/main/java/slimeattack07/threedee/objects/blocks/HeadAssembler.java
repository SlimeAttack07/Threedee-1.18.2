package slimeattack07.threedee.objects.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import slimeattack07.threedee.init.TDItems;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.recipes.HeadAssemblerRecipe;
import slimeattack07.threedee.tileentity.HeadAssemblerTE;
import slimeattack07.threedee.util.TdBasicMethods;

public class HeadAssembler extends InteractBlock {

	public HeadAssembler() {
		super(6, -1);
	}
	
//TODO: Check
//	@Override
//	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
//		return !adjacentBlockState.getBlock().equals(Blocks.AIR);
//	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return TDTileEntityTypes.HEAD_ASSEMBLER.get().create(pos, state);
	}
	
	@Override
	public void inform(Player player, BlockEntity tile) {
		HeadAssemblerTE te = (HeadAssemblerTE) tile;
		String message = TdBasicMethods.getTranslation("message.threedee.dye_amount");
		message = message.replace("MARKER1", te.getDyeAmount() + "");
		TdBasicMethods.messagePlayerCustom(player, message);
	}

	@Override
	public void validateAndCraft(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		HeadAssemblerTE te = (HeadAssemblerTE) tile;
		boolean stackmode = te.getMode();
		HeadAssemblerRecipe recipe = getRecipe(player, main, level, te.last_recipe, level.getBlockState(pos.offset(0, 1, 0)).getBlock());
		
		if(recipe == null)
			return;
		
		int amount_in = TdBasicMethods.getMatchingStack(recipe.getInput(), main).getCount();
		int times_possible = main.getCount() / amount_in;
		te.setLastRecipe(recipe.getId().toString());
		
		if (recipe.isDyeFiller()) {
			if (stackmode) {
				te.incDyeAmount(recipe.getAmount() * times_possible);
				TdBasicMethods.reduceStack(main, amount_in * times_possible);
			}
			else {
				te.incDyeAmount(recipe.getAmount());
				TdBasicMethods.reduceStack(main, amount_in);
			}
			
			return;
		}
		
		if(recipe.getAmount() > te.getDyeAmount()) {
			TdBasicMethods.messagePlayerBack(player, "message.threedee.no_paste", " (" + te.dye_amount + "/" + recipe.getAmount() + ")");
			return;
		}
		
		if(stackmode) {
			int amount = Math.min(te.getDyeAmount() / recipe.getAmount(), main.getCount() / amount_in);
			int used_paste = amount*recipe.getAmount();
			
			TdBasicMethods.reduceStack(main, amount * amount_in);
			te.decDyeAmount(used_paste);
			
			for(int i = 0; i < amount; i++)
				TdBasicMethods.addOrSpawn(player, recipe.getResultItem(), level, pos);
			
			inform(player, tile);
			TdBasicMethods.playSound(level, pos, SoundEvents.BEACON_POWER_SELECT);
			
			return;
		}
		
		TdBasicMethods.reduceStack(main, amount_in);
		te.decDyeAmount(recipe.getAmount());
		TdBasicMethods.addOrSpawn(player, recipe.getResultItem(), level, pos);
		inform(player, tile);
		TdBasicMethods.playSound(level, pos, SoundEvents.BEACON_POWER_SELECT);
	}

	@Override
	public boolean checkTileEnt(BlockEntity tile) {
		return tile instanceof HeadAssemblerTE;
	}

	@Override
	public void toggleMode(BlockEntity tile, Player player) {
		HeadAssemblerTE te = (HeadAssemblerTE) tile;
		if(te.toggleMode())
			TdBasicMethods.messagePlayer(player, "message.threedee.stackmode_true");
		else
			TdBasicMethods.messagePlayer(player, "message.threedee.stackmode_false");
	}
	
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
			BlockEntity tile = level.getBlockEntity(pos);
			
			if (checkTileEnt(tile)) {	
				HeadAssemblerTE te = (HeadAssemblerTE) tile;
				if (te.dye_amount == 0)
					return;
				
				Item item = TDItems.DYE_PASTE.get();
				TdBasicMethods.spawn(new ItemStack(item, te.dye_amount), level, pos, 0.5D, 0.5D, 0.5D);		
			
				level.removeBlockEntity(pos);
			}
			
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
	
	@Nullable
	private HeadAssemblerRecipe getRecipe(Player player, ItemStack stack, Level level, String last_recipe, Block block_on_top) {
		if (stack == null) {
			return null;
		}
		
		HeadAssemblerRecipe recipe = null;
		boolean missing_catalyst = false;
		List<ItemStack> itemstacks = new ArrayList<>();
		
		if(last_recipe != null && !last_recipe.equals("")) {
			recipe = (HeadAssemblerRecipe) TdBasicMethods.getRecipe(last_recipe, level);
			
			if(recipe != null) {
				boolean top_block_matched = true;
				if(!recipe.isDyeFiller())
					top_block_matched = block_on_top.getRegistryName().equals(recipe.getBlockOnTop().getRegistryName());
				
				if(top_block_matched)
					if(recipe.matches(TdBasicMethods.getWrapper(stack), level)) 
						return recipe;
					else {
						if(!recipe.isDyeFiller()){
							missing_catalyst = true;
							Collections.addAll(itemstacks, recipe.getInput().getItems());
						}
						recipe = null;
					}
					
				else
					recipe = null;
			}
		}
		
		if(recipe == null) {
			Set<Recipe<?>> recipes = TdBasicMethods.findRecipesByType(HeadAssemblerRecipe.Type.INSTANCE, level);
			
			for (Recipe<?> Recipe : recipes) {
				HeadAssemblerRecipe hrecipe = (HeadAssemblerRecipe) Recipe;
				boolean top_block_matched = true;
				
				if(!hrecipe.isDyeFiller())
					top_block_matched = block_on_top.getRegistryName().equals(hrecipe.getBlockOnTop().getRegistryName());
				
				if (top_block_matched) {
					if(hrecipe.matches(TdBasicMethods.getWrapper(stack), level)) {
						recipe = hrecipe;
						missing_catalyst = false;
						break;
					}
					else if(!hrecipe.isDyeFiller()){
						missing_catalyst = true;
						ItemStack[] stacks = hrecipe.getInput().getItems();
						
						for(ItemStack istack : stacks) {
							if(!itemstacks.contains(istack))
								itemstacks.add(istack);
						}
					}
				}
			}
		}
		
		if(missing_catalyst)
			notifyMissingCatalyst(player, itemstacks);
		
		return recipe;
	}
	
	public void notifyMissingCatalyst(Player player, List<ItemStack> itemstacks) {
		TdBasicMethods.messagePlayer(player, "message.threedee.no_catalyst");
		
		for(ItemStack itemstack : itemstacks) {
			String id = " (" + itemstack.getItem().getRegistryName().toString() + ")";
			String name = itemstack.getDisplayName().getString();
			TdBasicMethods.messagePlayerCustom(player, itemstack.getCount() + "x " + name + id);
		}
	}
}