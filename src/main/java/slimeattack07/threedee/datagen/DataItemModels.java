package slimeattack07.threedee.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDBlocks;

public class DataItemModels extends ItemModelProvider{

	public DataItemModels(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Threedee.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerModels() {
		withExistingParent(TDBlocks.MORTAR_AND_PESTLE_ANDESITE.getId().getPath(), Threedee.MOD_ID + ":block/" + TDBlocks.MORTAR_AND_PESTLE_ANDESITE.getId().getPath());
	}

	@Override
	public String getName() {
		return Threedee.MOD_ID + "item models";
	}
}
