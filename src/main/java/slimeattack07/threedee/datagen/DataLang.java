package slimeattack07.threedee.datagen;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.AncientModelBlocks;
import slimeattack07.threedee.init.CommonModelBlocks;
import slimeattack07.threedee.init.EpicModelBlocks;
import slimeattack07.threedee.init.LegendaryModelBlocks;
import slimeattack07.threedee.init.RareModelBlocks;
import slimeattack07.threedee.init.UncommonModelBlocks;

public class DataLang extends LanguageProvider{

	public DataLang(DataGenerator gen, String locale) {
		super(gen, Threedee.MOD_ID, locale);
	}

	@Override
	protected void addTranslations() {
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
			add(block, genName(block.getRegistryName().getPath()));
		});
	}

	@Override
	public String getName() {
		return Threedee.MOD_ID + " lang";
	}
	
	private String genName(String regname) {
		String result = "";
		String[] s = regname.split("_");
		
		for(int i = 0; i < s.length - 1; i++)
			result += StringUtils.capitalize(s[i]) + " ";
		
		if(s.length > 0)
			result += StringUtils.capitalize(s[s.length - 1]);
		
		return result;
	}
}
