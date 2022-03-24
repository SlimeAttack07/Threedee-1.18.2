package slimeattack07.threedee.datagen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.init.TDBlocks;

// Credit goes to McJty's data gen tutorials
public class DataLootTables extends LootTableProvider{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final Map<Block, LootTable.Builder> loot_tables = new HashMap<>();
    private final DataGenerator generator;

	public DataLootTables(DataGenerator gen) {
		super(gen);
		generator = gen;
	}
	
	private void addTables() {
		loot_tables.put(TDBlocks.MORTAR_AND_PESTLE_ANDESITE.get(), createSimpleTable(TDBlocks.MORTAR_AND_PESTLE_ANDESITE.getId().getPath(), 
				TDBlocks.MORTAR_AND_PESTLE_ANDESITE.get()));
	}

	private LootTable.Builder createSimpleTable(String name, Block block) {
        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block));
        return LootTable.lootTable().withPool(builder);
    }
	
	@Override
    public void run(HashCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : loot_tables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootContextParamSets.BLOCK).build());
        }
        writeTables(cache, tables);
    }

    private void writeTables(HashCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                DataProvider.save(GSON, cache, LootTables.serialize(lootTable), path);
            } catch (IOException e) {
                Threedee.LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }
    
    @Override
    public String getName() {
    	return Threedee.MOD_ID + " loot tables";
    }
}
