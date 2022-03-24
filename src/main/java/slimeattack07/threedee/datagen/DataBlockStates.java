package slimeattack07.threedee.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDBlocks;

public class DataBlockStates extends BlockStateProvider{

	public DataBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Threedee.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		horizontalBlock(TDBlocks.MORTAR_AND_PESTLE_ANDESITE.get(), models().getExistingFile(TDBlocks.MORTAR_AND_PESTLE_ANDESITE.getId()));
	}

}
