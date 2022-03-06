package slimeattack07.threedee.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.objects.items.AncientRock;
import slimeattack07.threedee.objects.items.CatSeedItem;
import slimeattack07.threedee.objects.items.CatalystItem;
import slimeattack07.threedee.objects.items.CatalyzationDowngrade;
import slimeattack07.threedee.objects.items.CatalyzationUpgrade;
import slimeattack07.threedee.objects.items.CatalyzedBoneMeal;
import slimeattack07.threedee.objects.items.TokenCard;

public class TDItems {

	public static final DeferredRegister<Item> TD_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Threedee.MOD_ID);
	
	public static final RegistryObject<Item> WOOD_PULP = TD_ITEMS.register("wood_pulp", () -> basicItem());
	public static final RegistryObject<Item> WET_WOOD_PULP = TD_ITEMS.register("wet_wood_pulp", () -> basicItem());
	
	public static final RegistryObject<Item> COAL_POWDER = TD_ITEMS.register("coal_powder", () -> basicItem());
	
	public static final RegistryObject<Item> DYE_PASTE = TD_ITEMS.register("dye_paste", () -> basicItem());
	
	public static final RegistryObject<Item> GREEN_DYE = TD_ITEMS.register("green_dye", () -> basicItem());
	
	public static final RegistryObject<Item> CATALYST_COMMON = TD_ITEMS.register("catalyst_common", () ->  new CatalystItem(DropRarity.COMMON));
	public static final RegistryObject<Item> CATALYST_UNCOMMON = TD_ITEMS.register("catalyst_uncommon", () ->  new CatalystItem(DropRarity.UNCOMMON));
	public static final RegistryObject<Item> CATALYST_RARE = TD_ITEMS.register("catalyst_rare", () ->  new CatalystItem(DropRarity.RARE));
	public static final RegistryObject<Item> CATALYST_EPIC = TD_ITEMS.register("catalyst_epic", () ->  new CatalystItem(DropRarity.EPIC));
	public static final RegistryObject<Item> CATALYST_LEGENDARY = TD_ITEMS.register("catalyst_legendary", () ->  new CatalystItem(DropRarity.LEGENDARY));
	public static final RegistryObject<Item> CATALYST_ANCIENT = TD_ITEMS.register("catalyst_ancient", () ->  new CatalystItem(DropRarity.ANCIENT));
	
	public static final RegistryObject<Item> CATALYST_SHARD_COMMON = TD_ITEMS.register("catalyst_shard_common", () ->  new CatalystItem(DropRarity.COMMON));
	public static final RegistryObject<Item> CATALYST_SHARD_UNCOMMON = TD_ITEMS.register("catalyst_shard_uncommon", () ->  new CatalystItem(DropRarity.UNCOMMON));
	public static final RegistryObject<Item> CATALYST_SHARD_RARE = TD_ITEMS.register("catalyst_shard_rare", () ->  new CatalystItem(DropRarity.RARE));
	public static final RegistryObject<Item> CATALYST_SHARD_EPIC = TD_ITEMS.register("catalyst_shard_epic", () ->  new CatalystItem(DropRarity.EPIC));
	public static final RegistryObject<Item> CATALYST_SHARD_LEGENDARY = TD_ITEMS.register("catalyst_shard_legendary", () ->  new CatalystItem(DropRarity.LEGENDARY));
	public static final RegistryObject<Item> CATALYST_SHARD_ANCIENT = TD_ITEMS.register("catalyst_shard_ancient", () ->  new CatalystItem(DropRarity.ANCIENT));
	
	public static final RegistryObject<Item> CATALYST_CORRUPTED = TD_ITEMS.register("catalyst_corrupted", () ->  basicItem());
	
	public static final RegistryObject<Item> CATALYST_SEED_COMMON = TD_ITEMS.register("catalyst_seeds_common", () -> new CatSeedItem(TDBlocks.CATALYST_COMMON_CROP.get(), DropRarity.COMMON));
	public static final RegistryObject<Item> CATALYST_SEED_UNCOMMON = TD_ITEMS.register("catalyst_seeds_uncommon", () -> new CatSeedItem(TDBlocks.CATALYST_UNCOMMON_CROP.get(), DropRarity.UNCOMMON));
	public static final RegistryObject<Item> CATALYST_SEED_RARE = TD_ITEMS.register("catalyst_seeds_rare", () -> new CatSeedItem(TDBlocks.CATALYST_RARE_CROP.get(), DropRarity.RARE));
	public static final RegistryObject<Item> CATALYST_SEED_EPIC = TD_ITEMS.register("catalyst_seeds_epic", () -> new CatSeedItem(TDBlocks.CATALYST_EPIC_CROP.get(), DropRarity.EPIC));
	public static final RegistryObject<Item> CATALYST_SEED_LEGENDARY = TD_ITEMS.register("catalyst_seeds_legendary", () -> new CatSeedItem(TDBlocks.CATALYST_LEGENDARY_CROP.get(), DropRarity.LEGENDARY));
	public static final RegistryObject<Item> CATALYST_SEED_ANCIENT = TD_ITEMS.register("catalyst_seeds_ancient", () -> new CatSeedItem(TDBlocks.CATALYST_ANCIENT_CROP.get(), DropRarity.ANCIENT));
	
	public static final RegistryObject<Item> CATALYZED_BONE_MEAL = TD_ITEMS.register("catalyzed_bone_meal", () -> new CatalyzedBoneMeal());
	
	public static final RegistryObject<Item> CATALYZATION_UPGRADE = TD_ITEMS.register("catalyst_upgrade", () -> new CatalyzationUpgrade());
	public static final RegistryObject<Item> CATALYZATION_DOWNGRADE = TD_ITEMS.register("catalyst_downgrade", () -> new CatalyzationDowngrade());
	
	public static final RegistryObject<Item> ANCIENT_ROCK_0 = TD_ITEMS.register("ancient_rock_0", () -> new AncientRock());
	public static final RegistryObject<Item> ANCIENT_ROCK_1 = TD_ITEMS.register("ancient_rock_1", () -> new AncientRock());
	public static final RegistryObject<Item> ANCIENT_ROCK_2 = TD_ITEMS.register("ancient_rock_2", () -> new AncientRock());
	public static final RegistryObject<Item> ANCIENT_ROCK_3 = TD_ITEMS.register("ancient_rock_3", () -> new AncientRock());
	public static final RegistryObject<Item> ANCIENT_ROCK_4 = TD_ITEMS.register("ancient_rock_4", () -> new AncientRock());
	
	public static final RegistryObject<Item> TOKEN_CARD = TD_ITEMS.register("token_card", () -> new TokenCard(false));
	public static final RegistryObject<Item> TOKEN_CARD_CREATIVE = TD_ITEMS.register("token_card_creative", () -> new TokenCard(true));
	
	public static Item basicItem() {
		return new Item(new Item.Properties().tab(Threedee.TD_ITEMS));
	}
	
	public static List<ItemStack> getAncientRocks(){
		List<ItemStack> items = new ArrayList<>();
		
		items.add(new ItemStack(ANCIENT_ROCK_0.get()));
		items.add(new ItemStack(ANCIENT_ROCK_1.get()));
		items.add(new ItemStack(ANCIENT_ROCK_2.get()));
		items.add(new ItemStack(ANCIENT_ROCK_3.get()));
		items.add(new ItemStack(ANCIENT_ROCK_4.get()));
		
		return items;
	}
	
	public static List<ItemStack> getCatalysts(){
		List<ItemStack> items = new ArrayList<>();
		
		items.add(new ItemStack(CATALYST_COMMON.get()));
		items.add(new ItemStack(CATALYST_UNCOMMON.get()));
		items.add(new ItemStack(CATALYST_RARE.get()));
		items.add(new ItemStack(CATALYST_EPIC.get()));
		items.add(new ItemStack(CATALYST_LEGENDARY.get()));
		items.add(new ItemStack(CATALYST_ANCIENT.get()));
		
		return items;
	}
}