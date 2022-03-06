package slimeattack07.threedee.objects.blocks;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import slimeattack07.threedee.tileentity.BasicInterTE;
import slimeattack07.threedee.util.TdBasicMethods;

public abstract class BasicIntBlock extends InteractBlock {
	
	public BasicIntBlock(Properties prop) {
		super(prop);
	}
	
	@Override
	public int valAndCalc(Player player, ItemStack main, ItemStack off, BlockEntity tile, Level level, BlockPos pos) {
		BasicInterTE te = (BasicInterTE) tile;
		boolean stackmode = te.getMode();

		int amount_in = getAmountPossible(te, player, main, level);
		
		if(amount_in < 1)
			return 0;
		
		if(stackmode) {	
			int times_possible = main.getCount() / amount_in;
			TdBasicMethods.reduceStack(main, amount_in * times_possible);
			
			return times_possible;
		}
		
		TdBasicMethods.reduceStack(main, amount_in);
		return 1;
	}
	
	protected abstract int getAmountPossible(BasicInterTE te, Player player, ItemStack main, Level level);
	@Nullable protected abstract ItemStack getResultItem(String last_recipe, Level level);
	
	@Override
	public void craft(int amount, BlockEntity tile, Player player, BlockPos pos, Level level) {
		BasicInterTE te = (BasicInterTE) tile;
		ItemStack output = getResultItem(te.getLastRecipe(), level);
		
		if(output == null)
			return;
		
		while (amount > 0) {
			TdBasicMethods.addOrSpawn(player, output, level, pos);
			amount--;
		}
	}
	
	@Override
	public boolean checkTileEnt(BlockEntity tile) {
		return tile instanceof BasicInterTE;
	}
	
	@Override
	public void toggleMode(BlockEntity tile, Player player) {
		BasicInterTE te = (BasicInterTE) tile;
		if(te.toggleMode())
			TdBasicMethods.messagePlayer(player, "message.threedee.stackmode_true");
		else
			TdBasicMethods.messagePlayer(player, "message.threedee.stackmode_false");
	}
}