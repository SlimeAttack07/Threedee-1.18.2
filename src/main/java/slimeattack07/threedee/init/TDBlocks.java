package slimeattack07.threedee.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.objects.blocks.ArtefactAnalyzer;
import slimeattack07.threedee.objects.blocks.ArtefactExchanger;
import slimeattack07.threedee.objects.blocks.BlockBase;
import slimeattack07.threedee.objects.blocks.BlockBaseRotatable;
import slimeattack07.threedee.objects.blocks.CBBRExtended;
import slimeattack07.threedee.objects.blocks.CatalystCrop;
import slimeattack07.threedee.objects.blocks.GlassBottle;
import slimeattack07.threedee.objects.blocks.Handsaw;
import slimeattack07.threedee.objects.blocks.Head;
import slimeattack07.threedee.objects.blocks.HeadAssembler;
import slimeattack07.threedee.objects.blocks.HeadFabricator;
import slimeattack07.threedee.objects.blocks.HeadRecycler;
import slimeattack07.threedee.objects.blocks.HopperRarity;
import slimeattack07.threedee.objects.blocks.IngotModel;
import slimeattack07.threedee.objects.blocks.ItemExchanger;
import slimeattack07.threedee.objects.blocks.MortarPestle;
import slimeattack07.threedee.objects.blocks.Negotiator;
import slimeattack07.threedee.objects.blocks.TinyCauldron;

public class TDBlocks {

	public static final DeferredRegister<Block> TD_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Threedee.MOD_ID);
	
	// Blocks
	public static final RegistryObject<Block> PULP_BLOCK = TD_BLOCKS.register("pulp_block", () -> new BlockBase(Material.SAND, SoundType.SAND));
	public static final RegistryObject<Block> WET_PULP_BLOCK = TD_BLOCKS.register("wet_pulp_block", () -> new BlockBase(Material.CLAY, SoundType.WET_GRASS));
	public static final RegistryObject<Block> HARD_PULP_BLOCK = TD_BLOCKS.register("hard_pulp_block", () -> new BlockBase(Material.WOOD, SoundType.WOOD));
	
	public static final RegistryObject<Block> COAL_POWDER_BLOCK = TD_BLOCKS.register("coal_powder_block", () -> new BlockBase(Material.SAND, SoundType.SAND));
	public static final RegistryObject<Block> ROUGH_ASPHALT = TD_BLOCKS.register("rough_asphalt", () -> new BlockBase(Material.STONE, SoundType.STONE));
	public static final RegistryObject<Block> SMOOTH_ASPHALT = TD_BLOCKS.register("smooth_asphalt", () -> new BlockBase(Material.STONE, SoundType.STONE));
	
	public static final RegistryObject<Block> OAK_CRATE = TD_BLOCKS.register("oak_crate", () -> new BlockBase(Material.WOOD, SoundType.WOOD));
	public static final RegistryObject<Block> SPRUCE_CRATE = TD_BLOCKS.register("spruce_crate", () -> new BlockBase(Material.WOOD, SoundType.WOOD));
	public static final RegistryObject<Block> BIRCH_CRATE = TD_BLOCKS.register("birch_crate", () -> new BlockBase(Material.WOOD, SoundType.WOOD));
	public static final RegistryObject<Block> JUNGLE_CRATE = TD_BLOCKS.register("jungle_crate", () -> new BlockBase(Material.WOOD, SoundType.WOOD));
	public static final RegistryObject<Block> ACACIA_CRATE = TD_BLOCKS.register("acacia_crate", () -> new BlockBase(Material.WOOD, SoundType.WOOD));
	public static final RegistryObject<Block> DARK_OAK_CRATE = TD_BLOCKS.register("dark_oak_crate", () -> new BlockBase(Material.WOOD, SoundType.WOOD));
	
	public static final RegistryObject<Block> DECORATIVE_FURNACE = TD_BLOCKS.register("decorative_furnace", () -> new BlockBaseRotatable(Blocks.COBBLESTONE));
	public static final RegistryObject<Block> DECORATIVE_BLAST_FURNACE = TD_BLOCKS.register("decorative_blast_furnace", () -> new BlockBaseRotatable(Blocks.COBBLESTONE));
	public static final RegistryObject<Block> DECORATIVE_SMOKER = TD_BLOCKS.register("decorative_smoker", () -> new BlockBaseRotatable(Blocks.COBBLESTONE));
	public static final RegistryObject<Block> DECORATIVE_LIT_FURNACE = TD_BLOCKS.register("decorative_lit_furnace", () -> new BlockBaseRotatable(Blocks.COBBLESTONE));
	public static final RegistryObject<Block> DECORATIVE_LIT_BLAST_FURNACE = TD_BLOCKS.register("decorative_lit_blast_furnace", () -> new BlockBaseRotatable(Blocks.COBBLESTONE));
	public static final RegistryObject<Block> DECORATIVE_LIT_SMOKER = TD_BLOCKS.register("decorative_lit_smoker", () -> new BlockBaseRotatable(Blocks.COBBLESTONE));
	
	public static final RegistryObject<Block> ANCIENT_STONE = TD_BLOCKS.register("ancient_stone", () -> new BlockBase(Material.STONE, SoundType.STONE));
	
	// crops
	public static final RegistryObject<Block> CATALYST_COMMON_CROP = TD_BLOCKS.register("catalyst_common_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_COMMON));
	public static final RegistryObject<Block> CATALYST_UNCOMMON_CROP = TD_BLOCKS.register("catalyst_uncommon_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_UNCOMMON));
	public static final RegistryObject<Block> CATALYST_RARE_CROP = TD_BLOCKS.register("catalyst_rare_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_RARE));
	public static final RegistryObject<Block> CATALYST_EPIC_CROP = TD_BLOCKS.register("catalyst_epic_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_EPIC));
	public static final RegistryObject<Block> CATALYST_LEGENDARY_CROP = TD_BLOCKS.register("catalyst_legendary_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_LEGENDARY));
	public static final RegistryObject<Block> CATALYST_ANCIENT_CROP = TD_BLOCKS.register("catalyst_ancient_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_ANCIENT));

	// Normal Models
	public static final RegistryObject<Block> EMPTY_BOTTLE = TD_BLOCKS.register("empty_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> EMPTY_WATER_BOTTLE = TD_BLOCKS.register("water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> WHITE_WATER_BOTTLE = TD_BLOCKS.register("white_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> ORANGE_WATER_BOTTLE = TD_BLOCKS.register("orange_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> MAGENTA_WATER_BOTTLE = TD_BLOCKS.register("magenta_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> LIGHT_BLUE_WATER_BOTTLE = TD_BLOCKS.register("light_blue_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> YELLOW_WATER_BOTTLE = TD_BLOCKS.register("yellow_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> LIME_WATER_BOTTLE = TD_BLOCKS.register("lime_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> PINK_WATER_BOTTLE = TD_BLOCKS.register("pink_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> GRAY_WATER_BOTTLE = TD_BLOCKS.register("gray_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> LIGHT_GRAY_WATER_BOTTLE = TD_BLOCKS.register("light_gray_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> CYAN_WATER_BOTTLE = TD_BLOCKS.register("cyan_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> PURPLE_WATER_BOTTLE = TD_BLOCKS.register("purple_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> BLUE_WATER_BOTTLE = TD_BLOCKS.register("blue_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> BROWN_WATER_BOTTLE = TD_BLOCKS.register("brown_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> GREEN_WATER_BOTTLE = TD_BLOCKS.register("green_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> RED_WATER_BOTTLE = TD_BLOCKS.register("red_water_bottle", () -> new GlassBottle());
	public static final RegistryObject<Block> BLACK_WATER_BOTTLE = TD_BLOCKS.register("black_water_bottle", () -> new GlassBottle());	
	public static final RegistryObject<Block> LAVA_BOTTLE = TD_BLOCKS.register("lava_bottle", () -> new GlassBottle());
	
	// Head-Based Models
	public static final RegistryObject<Block> MODEL_COMMON_0 = TD_BLOCKS.register("model_common_0", () -> new CBBRExtended(7));
	public static final RegistryObject<Block> MODEL_COMMON_2 = TD_BLOCKS.register("model_common_2", () -> new CBBRExtended(8));
	public static final RegistryObject<Block> MODEL_COMMON_3 = TD_BLOCKS.register("model_common_3", () -> new CBBRExtended(8));
	public static final RegistryObject<Block> MODEL_COMMON_4 = TD_BLOCKS.register("model_common_4", () -> new CBBRExtended(9));
	public static final RegistryObject<Block> MODEL_COMMON_6 = TD_BLOCKS.register("model_common_6", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_COMMON_7 = TD_BLOCKS.register("model_common_7", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_COMMON_8 = TD_BLOCKS.register("model_common_8", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_COMMON_9 = TD_BLOCKS.register("model_common_9", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_COMMON_10 = TD_BLOCKS.register("model_common_10", () -> new IngotModel());
	
	public static final RegistryObject<Block> MODEL_UNCOMMON_2 = TD_BLOCKS.register("model_uncommon_2", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_UNCOMMON_6 = TD_BLOCKS.register("model_uncommon_6", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_UNCOMMON_7 = TD_BLOCKS.register("model_uncommon_7", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_UNCOMMON_8 = TD_BLOCKS.register("model_uncommon_8", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_UNCOMMON_19 = TD_BLOCKS.register("model_uncommon_19", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_UNCOMMON_31 = TD_BLOCKS.register("model_uncommon_31", () -> new IngotModel());
	
	public static final RegistryObject<Block> MODEL_RARE_1 = TD_BLOCKS.register("model_rare_1", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_RARE_2 = TD_BLOCKS.register("model_rare_2", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_RARE_3 = TD_BLOCKS.register("model_rare_3", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_RARE_4 = TD_BLOCKS.register("model_rare_4", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_RARE_5 = TD_BLOCKS.register("model_rare_5", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_RARE_6 = TD_BLOCKS.register("model_rare_6", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_RARE_12 = TD_BLOCKS.register("model_rare_12", () -> new IngotModel());
	
	public static final RegistryObject<Block> MODEL_EPIC_1 = TD_BLOCKS.register("model_epic_1", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_EPIC_2 = TD_BLOCKS.register("model_epic_2", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_EPIC_3 = TD_BLOCKS.register("model_epic_3", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_EPIC_4 = TD_BLOCKS.register("model_epic_4", () -> new IngotModel());
	
	public static final RegistryObject<Block> MODEL_LEGENDARY_1 = TD_BLOCKS.register("model_legendary_1", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_LEGENDARY_2 = TD_BLOCKS.register("model_legendary_2", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_LEGENDARY_3 = TD_BLOCKS.register("model_legendary_3", () -> new IngotModel());
	public static final RegistryObject<Block> MODEL_LEGENDARY_24 = TD_BLOCKS.register("model_legendary_24", () -> new IngotModel());
	
	// Functional Blocks / Models
	public static final RegistryObject<Block> MORTAR_AND_PESTLE_DIORITE = TD_BLOCKS.register("mortar_and_pestle_diorite", () -> new MortarPestle());
	public static final RegistryObject<Block> MORTAR_AND_PESTLE_ANDESITE = TD_BLOCKS.register("mortar_and_pestle_andesite", () -> new MortarPestle());
	public static final RegistryObject<Block> MORTAR_AND_PESTLE_GRANITE = TD_BLOCKS.register("mortar_and_pestle_granite", () -> new MortarPestle());
	
	public static final RegistryObject<Block> TINY_CAULDRON_OAK = TD_BLOCKS.register("tiny_cauldron_oak", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_BIRCH = TD_BLOCKS.register("tiny_cauldron_birch", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_SPRUCE = TD_BLOCKS.register("tiny_cauldron_spruce", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_JUNGLE = TD_BLOCKS.register("tiny_cauldron_jungle", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_ACACIA = TD_BLOCKS.register("tiny_cauldron_acacia", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_DARK_OAK = TD_BLOCKS.register("tiny_cauldron_dark_oak", () -> new TinyCauldron());
	
	public static final RegistryObject<Block> HEAD_FABRICATOR = TD_BLOCKS.register("head_fabricator", () -> new HeadFabricator());
	public static final RegistryObject<Block> HEAD_ASSEMBLER = TD_BLOCKS.register("head_assembler", () -> new HeadAssembler());
	public static final RegistryObject<Block> HEAD_RECYCLER = TD_BLOCKS.register("head_recycler", () -> new HeadRecycler());
	public static final RegistryObject<Block> ARTEFACT_ANALYZER = TD_BLOCKS.register("artefact_analyzer", () -> new ArtefactAnalyzer());
	public static final RegistryObject<Block> ARTEFACT_EXCHANGER = TD_BLOCKS.register("artefact_exchanger", () -> new ArtefactExchanger());
	public static final RegistryObject<Block> ITEM_EXCHANGER = TD_BLOCKS.register("item_exchanger", () -> new ItemExchanger());
	public static final RegistryObject<Block> NEGOTIATOR = TD_BLOCKS.register("negotiator", () -> new Negotiator());
	
	public static final RegistryObject<Block> HANDSAW_OAK = TD_BLOCKS.register("handsaw_oak", () -> new Handsaw());
	public static final RegistryObject<Block> HANDSAW_BIRCH = TD_BLOCKS.register("handsaw_birch", () -> new Handsaw());
	public static final RegistryObject<Block> HANDSAW_SPRUCE = TD_BLOCKS.register("handsaw_spruce", () -> new Handsaw());
	public static final RegistryObject<Block> HANDSAW_JUNGLE = TD_BLOCKS.register("handsaw_jungle", () -> new Handsaw());
	public static final RegistryObject<Block> HANDSAW_ACACIA = TD_BLOCKS.register("handsaw_acacia", () -> new Handsaw());
	public static final RegistryObject<Block> HANDSAW_DARK_OAK = TD_BLOCKS.register("handsaw_dark_oak", () -> new Handsaw());
	
	public static final RegistryObject<Block> HOPPER_COMMON = TD_BLOCKS.register("hopper_common", () -> new HopperRarity(DropRarity.COMMON));
	public static final RegistryObject<Block> HOPPER_UNCOMMON = TD_BLOCKS.register("hopper_uncommon", () -> new HopperRarity(DropRarity.UNCOMMON));
	public static final RegistryObject<Block> HOPPER_RARE = TD_BLOCKS.register("hopper_rare", () -> new HopperRarity(DropRarity.RARE));
	public static final RegistryObject<Block> HOPPER_EPIC = TD_BLOCKS.register("hopper_epic", () -> new HopperRarity(DropRarity.EPIC));
	public static final RegistryObject<Block> HOPPER_LEGENDARY = TD_BLOCKS.register("hopper_legendary", () -> new HopperRarity(DropRarity.LEGENDARY));
	public static final RegistryObject<Block> HOPPER_ANCIENT = TD_BLOCKS.register("hopper_ancient", () -> new HopperRarity(DropRarity.ANCIENT));
	
	// Heads
	public static final List<RegistryObject<Block>> COMMON_HEADS = new ArrayList<>();
	public static final List<RegistryObject<Block>> UNCOMMON_HEADS = new ArrayList<>();
	public static final List<RegistryObject<Block>> RARE_HEADS = new ArrayList<>();
	public static final List<RegistryObject<Block>> EPIC_HEADS = new ArrayList<>();
	public static final List<RegistryObject<Block>> LEGENDARY_HEADS = new ArrayList<>();
	public static final List<RegistryObject<Block>> ANCIENT_HEADS = new ArrayList<>();
	
	public static void registerHeads() {
		for(int i = 0; i < Threedee.COMMON_HEADS; i++) {
			final RegistryObject<Block> HEAD = TD_BLOCKS.register("head_common_" + i, () -> new Head(0));
			COMMON_HEADS.add(HEAD);
		}
		
		for(int i = 0; i < Threedee.UNCOMMON_HEADS; i++) {
			final RegistryObject<Block> HEAD = TD_BLOCKS.register("head_uncommon_" + i, () -> new Head(1));
			UNCOMMON_HEADS.add(HEAD);
		}
		
		for(int i = 0; i < Threedee.RARE_HEADS; i++) {
			final RegistryObject<Block> HEAD = TD_BLOCKS.register("head_rare_" + i, () -> new Head(2));
			RARE_HEADS.add(HEAD);
		}
		
		for(int i = 0; i < Threedee.EPIC_HEADS; i++) {
			final RegistryObject<Block> HEAD = TD_BLOCKS.register("head_epic_" + i, () -> new Head(3));
			EPIC_HEADS.add(HEAD);
		}
		
		for(int i = 0; i < Threedee.LEGENDARY_HEADS; i++) {
			final RegistryObject<Block> HEAD = TD_BLOCKS.register("head_legendary_" + i, () -> new Head(4));
			LEGENDARY_HEADS.add(HEAD);
		}
		
		for(int i = 0; i < Threedee.ANCIENT_HEADS; i++) {
			final RegistryObject<Block> HEAD = TD_BLOCKS.register("head_ancient_" + i, () -> new Head(5));
			ANCIENT_HEADS.add(HEAD);
		}
	}
	
	public static List<ItemStack> getMortarPestles(){
		List<ItemStack> blocks = new ArrayList<>();
		
		blocks.add(new ItemStack(MORTAR_AND_PESTLE_ANDESITE.get()));
		blocks.add(new ItemStack(MORTAR_AND_PESTLE_DIORITE.get()));
		blocks.add(new ItemStack(MORTAR_AND_PESTLE_GRANITE.get()));
		
		return blocks;
	}
	
	public static List<ItemStack> getTinyCauldrons(){
		List<ItemStack> blocks = new ArrayList<>();
		
		blocks.add(new ItemStack(TINY_CAULDRON_ACACIA.get()));
		blocks.add(new ItemStack(TINY_CAULDRON_BIRCH.get()));
		blocks.add(new ItemStack(TINY_CAULDRON_DARK_OAK.get()));
		blocks.add(new ItemStack(TINY_CAULDRON_JUNGLE.get()));
		blocks.add(new ItemStack(TINY_CAULDRON_OAK.get()));
		blocks.add(new ItemStack(TINY_CAULDRON_SPRUCE.get()));
		
		return blocks;
	}
	
	public static List<ItemStack> getHandsaws(){
		List<ItemStack> blocks = new ArrayList<>();
		
		blocks.add(new ItemStack(HANDSAW_ACACIA.get()));
		blocks.add(new ItemStack(HANDSAW_BIRCH.get()));
		blocks.add(new ItemStack(HANDSAW_DARK_OAK.get()));
		blocks.add(new ItemStack(HANDSAW_JUNGLE.get()));
		blocks.add(new ItemStack(HANDSAW_OAK.get()));
		blocks.add(new ItemStack(HANDSAW_SPRUCE.get()));
		
		return blocks;
	}
	
	public static List<ItemStack> getCommonHeads(){
		List<ItemStack> heads = new ArrayList<>();
		
		COMMON_HEADS.stream().forEach(block -> heads.add(new ItemStack(block.get())));
		
		return heads;
	}
	
	public static List<ItemStack> getUncommonHeads(){
		List<ItemStack> heads = new ArrayList<>();
		
		UNCOMMON_HEADS.stream().forEach(block -> heads.add(new ItemStack(block.get())));
		
		return heads;
	}
	
	public static List<ItemStack> getRareHeads(){
		List<ItemStack> heads = new ArrayList<>();
		
		RARE_HEADS.stream().forEach(block -> heads.add(new ItemStack(block.get())));
		
		return heads;
	}
	
	public static List<ItemStack> getEpicHeads(){
		List<ItemStack> heads = new ArrayList<>();
		
		EPIC_HEADS.stream().forEach(block -> heads.add(new ItemStack(block.get())));
		
		return heads;
	}
	
	public static List<ItemStack> getLegendaryHeads(){
		List<ItemStack> heads = new ArrayList<>();
		
		LEGENDARY_HEADS.stream().forEach(block -> heads.add(new ItemStack(block.get())));
		
		return heads;
	}
	
	public static List<ItemStack> getAncientHeads(){
		List<ItemStack> heads = new ArrayList<>();
		
		ANCIENT_HEADS.stream().forEach(block -> heads.add(new ItemStack(block.get())));
		
		return heads;
	}
}