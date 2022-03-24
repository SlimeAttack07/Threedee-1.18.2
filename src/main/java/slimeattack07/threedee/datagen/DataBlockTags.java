package slimeattack07.threedee.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
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

public class DataBlockTags extends BlockTagsProvider {

	public DataBlockTags(DataGenerator gen,  ExistingFileHelper exFileHelper) {
		super(gen, Threedee.MOD_ID, exFileHelper);
	}

	@Override
	protected void addTags() {
		registerAll(CommonModelBlocks.COMMON, "common");
		registerAll(UncommonModelBlocks.UNCOMMON, "uncommon");
		registerAll(RareModelBlocks.RARE, "rare");
		registerAll(EpicModelBlocks.EPIC, "epic");
		registerAll(LegendaryModelBlocks.LEGENDARY, "legendary");
		registerAll(AncientModelBlocks.ANCIENT, "ancient");	
	}
	
	private void registerAll(DeferredRegister<Block> reg, String rarity) {
		TagKey<Block> tagkey = BlockTags.create(new ResourceLocation(Threedee.MOD_ID, rarity + "_models"));
		
		reg.getEntries().stream()
		.map(RegistryObject::get).forEach(block -> {
			tag(tagkey).add(block);
		});
	}
	
	@Override
	public String getName() {
		return Threedee.MOD_ID + " block tags";
	}
}
