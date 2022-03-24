package slimeattack07.threedee.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDBlocks;

public class DataBlockTags extends BlockTagsProvider {

	public DataBlockTags(DataGenerator gen,  ExistingFileHelper exFileHelper) {
		super(gen, Threedee.MOD_ID, exFileHelper);
	}

	@Override
	protected void addTags() {
		TagKey<Block> common = BlockTags.create(new ResourceLocation(Threedee.MOD_ID, "common_models"));
		tag(common).add(TDBlocks.MORTAR_AND_PESTLE_ANDESITE.get());
	}

	@Override
	public String getName() {
		return Threedee.MOD_ID + " block tags";
	}
}
