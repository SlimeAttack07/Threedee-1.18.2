package slimeattack07.threedee.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.AncientModelBlocks;
import slimeattack07.threedee.init.CommonModelBlocks;
import slimeattack07.threedee.init.EpicModelBlocks;
import slimeattack07.threedee.init.LegendaryModelBlocks;
import slimeattack07.threedee.init.RareModelBlocks;
import slimeattack07.threedee.init.UncommonModelBlocks;

public class DataItemModels extends ItemModelProvider{

	public DataItemModels(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Threedee.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerModels() {
		registerAll(CommonModelBlocks.COMMON);
		registerAll(UncommonModelBlocks.UNCOMMON);
		registerAll(RareModelBlocks.RARE);
		registerAll(EpicModelBlocks.EPIC);
		registerAll(LegendaryModelBlocks.LEGENDARY);
		registerAll(AncientModelBlocks.ANCIENT);
	}
	
	private void registerAll(DeferredRegister<Block> reg) {
		reg.getEntries().stream()
		.map(RegistryObject::get).forEach(block -> {
			withExistingParent(block.getRegistryName().getPath(), Threedee.MOD_ID + ":block/" + block.getRegistryName().getPath());
		});
	}

	@Override
	public String getName() {
		return Threedee.MOD_ID + "item models";
	}
}
