package slimeattack07.threedee.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDBlocks;

public class DataItemTags extends ItemTagsProvider {

	public DataItemTags(DataGenerator gen, BlockTagsProvider blocktags, ExistingFileHelper exFileHelper) {
		super(gen, blocktags, Threedee.MOD_ID, exFileHelper);
	}

	@Override
	protected void addTags() {
		TagKey<Item> common = ItemTags.create(new ResourceLocation(Threedee.MOD_ID, "common_models"));
		tag(common).add(TDBlocks.MORTAR_AND_PESTLE_ANDESITE.get().asItem());
	}

	@Override
	public String getName() {
		return Threedee.MOD_ID + " item tags";
	}
}
