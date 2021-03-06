package slimeattack07.threedee.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.DropRarity;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.objects.blocks.ArtefactAnalyzer;
import slimeattack07.threedee.objects.blocks.ArtefactExchanger;
import slimeattack07.threedee.objects.blocks.BlockBaseRotatable;
import slimeattack07.threedee.objects.blocks.CatalystCrop;
import slimeattack07.threedee.objects.blocks.Handsaw;
import slimeattack07.threedee.objects.blocks.Model;
import slimeattack07.threedee.objects.blocks.ModelAssembler;
import slimeattack07.threedee.objects.blocks.ModelFabricator;
import slimeattack07.threedee.objects.blocks.ModelRecycler;
import slimeattack07.threedee.objects.blocks.HopperRarity;
import slimeattack07.threedee.objects.blocks.ItemExchanger;
import slimeattack07.threedee.objects.blocks.MortarPestle;
import slimeattack07.threedee.objects.blocks.Negotiator;
import slimeattack07.threedee.objects.blocks.TinyCauldron;

public class TDBlocks {

	public static final DeferredRegister<Block> TD_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Threedee.MOD_ID);
	
	// Blocks
	public static final RegistryObject<Block> PULP_BLOCK = TD_BLOCKS.register("pulp_block", () -> blockBase(Material.SAND, SoundType.SAND, false));
	public static final RegistryObject<Block> WET_PULP_BLOCK = TD_BLOCKS.register("wet_pulp_block", () -> blockBase(Material.CLAY, SoundType.WET_GRASS, false));
	public static final RegistryObject<Block> HARD_PULP_BLOCK = TD_BLOCKS.register("hard_pulp_block", () -> blockBase(Material.WOOD, SoundType.WOOD, true));
	
	public static final RegistryObject<Block> COAL_POWDER_BLOCK = TD_BLOCKS.register("coal_powder_block", () -> blockBase(Material.SAND, SoundType.SAND, false));
	public static final RegistryObject<Block> ROUGH_ASPHALT = TD_BLOCKS.register("rough_asphalt", () -> blockBase(Material.STONE, SoundType.STONE, true));
	public static final RegistryObject<Block> SMOOTH_ASPHALT = TD_BLOCKS.register("smooth_asphalt", () -> blockBase(Material.STONE, SoundType.STONE, true));
	
	public static final RegistryObject<Block> OAK_CRATE = TD_BLOCKS.register("oak_crate", () -> blockBase(Material.WOOD, SoundType.WOOD, true));
	public static final RegistryObject<Block> SPRUCE_CRATE = TD_BLOCKS.register("spruce_crate", () -> blockBase(Material.WOOD, SoundType.WOOD, true));
	public static final RegistryObject<Block> BIRCH_CRATE = TD_BLOCKS.register("birch_crate", () -> blockBase(Material.WOOD, SoundType.WOOD, true));
	public static final RegistryObject<Block> JUNGLE_CRATE = TD_BLOCKS.register("jungle_crate", () -> blockBase(Material.WOOD, SoundType.WOOD, true));
	public static final RegistryObject<Block> ACACIA_CRATE = TD_BLOCKS.register("acacia_crate", () -> blockBase(Material.WOOD, SoundType.WOOD, true));
	public static final RegistryObject<Block> DARK_OAK_CRATE = TD_BLOCKS.register("dark_oak_crate", () -> blockBase(Material.WOOD, SoundType.WOOD, true));
	public static final RegistryObject<Block> CRIMSON_CRATE = TD_BLOCKS.register("crimson_crate", () -> blockBase(Material.WOOD, SoundType.WOOD, true));
	public static final RegistryObject<Block> WARPED_CRATE = TD_BLOCKS.register("warped_crate", () -> blockBase(Material.WOOD, SoundType.WOOD, true));
	
	public static final RegistryObject<Block> DECORATIVE_FURNACE = TD_BLOCKS.register("decorative_furnace", () -> BlockBaseRotatable.create(Blocks.COBBLESTONE, true));
	public static final RegistryObject<Block> DECORATIVE_BLAST_FURNACE = TD_BLOCKS.register("decorative_blast_furnace", () -> BlockBaseRotatable.create(Blocks.COBBLESTONE, true));
	public static final RegistryObject<Block> DECORATIVE_SMOKER = TD_BLOCKS.register("decorative_smoker", () -> BlockBaseRotatable.create(Blocks.COBBLESTONE, true));
	public static final RegistryObject<Block> DECORATIVE_LIT_FURNACE = TD_BLOCKS.register("decorative_lit_furnace", () -> BlockBaseRotatable.create(Blocks.COBBLESTONE, true));
	public static final RegistryObject<Block> DECORATIVE_LIT_BLAST_FURNACE = TD_BLOCKS.register("decorative_lit_blast_furnace", () -> BlockBaseRotatable.create(Blocks.COBBLESTONE, true));
	public static final RegistryObject<Block> DECORATIVE_LIT_SMOKER = TD_BLOCKS.register("decorative_lit_smoker", () -> BlockBaseRotatable.create(Blocks.COBBLESTONE, true));
	
	public static final RegistryObject<Block> ANCIENT_STONE = TD_BLOCKS.register("ancient_stone", () -> blockBase(Material.STONE, 5f, 1000f, SoundType.STONE, true));
	
	// crops
	public static final RegistryObject<Block> CATALYST_COMMON_CROP = TD_BLOCKS.register("catalyst_common_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_COMMON));
	public static final RegistryObject<Block> CATALYST_UNCOMMON_CROP = TD_BLOCKS.register("catalyst_uncommon_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_UNCOMMON));
	public static final RegistryObject<Block> CATALYST_RARE_CROP = TD_BLOCKS.register("catalyst_rare_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_RARE));
	public static final RegistryObject<Block> CATALYST_EPIC_CROP = TD_BLOCKS.register("catalyst_epic_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_EPIC));
	public static final RegistryObject<Block> CATALYST_LEGENDARY_CROP = TD_BLOCKS.register("catalyst_legendary_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_LEGENDARY));
	public static final RegistryObject<Block> CATALYST_ANCIENT_CROP = TD_BLOCKS.register("catalyst_ancient_crop", () -> new CatalystCrop(TDItems.CATALYST_SEED_ANCIENT));
	
	// Head-Based Models
//	public static final RegistryObject<Block> MODEL_COMMON_6 = TD_BLOCKS.register("model_common_6", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_COMMON_7 = TD_BLOCKS.register("model_common_7", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_COMMON_8 = TD_BLOCKS.register("model_common_8", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_COMMON_9 = TD_BLOCKS.register("model_common_9", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_COMMON_10 = TD_BLOCKS.register("model_common_10", () -> new CustomBlockBaseRotatable(10, 1));
//	
//	public static final RegistryObject<Block> MODEL_UNCOMMON_2 = TD_BLOCKS.register("model_uncommon_2", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_UNCOMMON_6 = TD_BLOCKS.register("model_uncommon_6", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_UNCOMMON_7 = TD_BLOCKS.register("model_uncommon_7", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_UNCOMMON_8 = TD_BLOCKS.register("model_uncommon_8", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_UNCOMMON_19 = TD_BLOCKS.register("model_uncommon_19", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_UNCOMMON_31 = TD_BLOCKS.register("model_uncommon_31", () -> new CustomBlockBaseRotatable(10, 1));
//	
//	public static final RegistryObject<Block> MODEL_RARE_1 = TD_BLOCKS.register("model_rare_1", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_RARE_2 = TD_BLOCKS.register("model_rare_2", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_RARE_3 = TD_BLOCKS.register("model_rare_3", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_RARE_4 = TD_BLOCKS.register("model_rare_4", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_RARE_5 = TD_BLOCKS.register("model_rare_5", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_RARE_6 = TD_BLOCKS.register("model_rare_6", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_RARE_12 = TD_BLOCKS.register("model_rare_12", () -> new CustomBlockBaseRotatable(10, 1));
//	
//	public static final RegistryObject<Block> MODEL_EPIC_1 = TD_BLOCKS.register("model_epic_1", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_EPIC_2 = TD_BLOCKS.register("model_epic_2", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_EPIC_3 = TD_BLOCKS.register("model_epic_3", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_EPIC_4 = TD_BLOCKS.register("model_epic_4", () -> new CustomBlockBaseRotatable(10, 1));
//	
//	public static final RegistryObject<Block> MODEL_LEGENDARY_1 = TD_BLOCKS.register("model_legendary_1", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_LEGENDARY_2 = TD_BLOCKS.register("model_legendary_2", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_LEGENDARY_3 = TD_BLOCKS.register("model_legendary_3", () -> new CustomBlockBaseRotatable(10, 1));
//	public static final RegistryObject<Block> MODEL_LEGENDARY_24 = TD_BLOCKS.register("model_legendary_24", () -> new CustomBlockBaseRotatable(10, 1));
	
	// Functional Blocks / Models
	public static final RegistryObject<Block> MORTAR_AND_PESTLE_DIORITE = TD_BLOCKS.register("mortar_and_pestle_diorite", () -> new MortarPestle());
	public static final RegistryObject<Block> MORTAR_AND_PESTLE_ANDESITE = TD_BLOCKS.register("mortar_and_pestle_andesite", () -> new MortarPestle());
	public static final RegistryObject<Block> MORTAR_AND_PESTLE_GRANITE = TD_BLOCKS.register("mortar_and_pestle_granite", () -> new MortarPestle());
	public static final RegistryObject<Block> MORTAR_AND_PESTLE_BASALT = TD_BLOCKS.register("mortar_and_pestle_basalt", () -> new MortarPestle());
	public static final RegistryObject<Block> MORTAR_AND_PESTLE_BLACKSTONE = TD_BLOCKS.register("mortar_and_pestle_blackstone", () -> new MortarPestle());
	public static final RegistryObject<Block> MORTAR_AND_PESTLE_DEEPSLATE = TD_BLOCKS.register("mortar_and_pestle_deepslate", () -> new MortarPestle());

	
	public static final RegistryObject<Block> TINY_CAULDRON_OAK = TD_BLOCKS.register("tiny_cauldron_oak", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_BIRCH = TD_BLOCKS.register("tiny_cauldron_birch", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_SPRUCE = TD_BLOCKS.register("tiny_cauldron_spruce", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_JUNGLE = TD_BLOCKS.register("tiny_cauldron_jungle", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_ACACIA = TD_BLOCKS.register("tiny_cauldron_acacia", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_DARK_OAK = TD_BLOCKS.register("tiny_cauldron_dark_oak", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_CRIMSON = TD_BLOCKS.register("tiny_cauldron_crimson", () -> new TinyCauldron());
	public static final RegistryObject<Block> TINY_CAULDRON_WARPED = TD_BLOCKS.register("tiny_cauldron_warped", () -> new TinyCauldron());
	
	public static final RegistryObject<Block> MODEL_FABRICATOR = TD_BLOCKS.register("model_fabricator", () -> new ModelFabricator());
	public static final RegistryObject<Block> MODEL_ASSEMBLER = TD_BLOCKS.register("model_assembler", () -> new ModelAssembler());
	public static final RegistryObject<Block> MODEL_RECYCLER = TD_BLOCKS.register("model_recycler", () -> new ModelRecycler());
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
	public static final RegistryObject<Block> HANDSAW_CRIMSON = TD_BLOCKS.register("handsaw_crimson", () -> new Handsaw());
	public static final RegistryObject<Block> HANDSAW_WARPED = TD_BLOCKS.register("handsaw_warped", () -> new Handsaw());
	
	public static final RegistryObject<Block> HOPPER_COMMON = TD_BLOCKS.register("hopper_common", () -> new HopperRarity(DropRarity.COMMON));
	public static final RegistryObject<Block> HOPPER_UNCOMMON = TD_BLOCKS.register("hopper_uncommon", () -> new HopperRarity(DropRarity.UNCOMMON));
	public static final RegistryObject<Block> HOPPER_RARE = TD_BLOCKS.register("hopper_rare", () -> new HopperRarity(DropRarity.RARE));
	public static final RegistryObject<Block> HOPPER_EPIC = TD_BLOCKS.register("hopper_epic", () -> new HopperRarity(DropRarity.EPIC));
	public static final RegistryObject<Block> HOPPER_LEGENDARY = TD_BLOCKS.register("hopper_legendary", () -> new HopperRarity(DropRarity.LEGENDARY));
	public static final RegistryObject<Block> HOPPER_ANCIENT = TD_BLOCKS.register("hopper_ancient", () -> new HopperRarity(DropRarity.ANCIENT));
	
	// Heads
	public static final List<RegistryObject<Block>> UNCOMMON_HEADS = new ArrayList<>();
	public static final List<RegistryObject<Block>> RARE_HEADS = new ArrayList<>();
	public static final List<RegistryObject<Block>> EPIC_HEADS = new ArrayList<>();
	public static final List<RegistryObject<Block>> LEGENDARY_HEADS = new ArrayList<>();
	public static final List<RegistryObject<Block>> ANCIENT_HEADS = new ArrayList<>();
	
	private static Block blockBase(Material mat, float hard, float resist, SoundType sound, boolean tool) {
		Properties prop = Block.Properties.of(mat).strength(hard, resist).sound(sound);
		
		return tool ? new Block(prop.requiresCorrectToolForDrops()) : new Block(prop);
	}
	
	private static Block blockBase(Material mat, SoundType sound, boolean tool) {
		Properties prop = Block.Properties.of(mat).strength(0.6f, 3.0f).sound(sound);
		
		return tool ? new Block(prop.requiresCorrectToolForDrops()) : new Block(prop);
	}
	
//	private static Block blockBase(Block block, boolean tool) {
//		Properties prop = Block.Properties.copy(block);
//		
//		return tool ? new Block(prop.requiresCorrectToolForDrops()) : new Block(prop);
//	}
	
	// This is temporary to not register heads I've removed. Once I've implemented the new registry system, I'll be able to remove this again.
	private static boolean skip(String rarity, int i) {
		switch(rarity) {
		case "u":{
			Integer[] a = new Integer[] {30, 34, 35, 61, 62, 63, 64, 65, 66};
			List<Integer> l = Arrays.asList(a);
			return l.contains(i);
		}
		case "r":{
			Integer[] a = new Integer[] {28, 32, 33, 40, 41, 42, 43, 44, 45, 46, 47, 48};
			List<Integer> l = Arrays.asList(a);
			return l.contains(i);
		}
		case "e":{
			Integer[] a = new Integer[] {30, 34};
			List<Integer> l = Arrays.asList(a);
			return l.contains(i);
		}
		case "l":{
			Integer[] a = new Integer[] {23};
			List<Integer> l = Arrays.asList(a);
			return l.contains(i);
		}
		default: return false;
		}
	}
	
	public static void registerHeads() {		
		for(int i = 0; i < Threedee.UNCOMMON_HEADS; i++) {
			if(skip("u", i))
				continue;
			
			final RegistryObject<Block> HEAD = TD_BLOCKS.register("head_uncommon_" + i, () -> new Model(1));
			UNCOMMON_HEADS.add(HEAD);
		}
		
		for(int i = 0; i < Threedee.RARE_HEADS; i++) {
			if(skip("r", i))
				continue;
			
			final RegistryObject<Block> HEAD = TD_BLOCKS.register("head_rare_" + i, () -> new Model(2));
			RARE_HEADS.add(HEAD);
		}
		
		for(int i = 0; i < Threedee.EPIC_HEADS; i++) {
			if(skip("e", i))
				continue;
			
			final RegistryObject<Block> HEAD = TD_BLOCKS.register("head_epic_" + i, () -> new Model(3));
			EPIC_HEADS.add(HEAD);
		}
		
		for(int i = 0; i < Threedee.LEGENDARY_HEADS; i++) {
			if(skip("l", i))
				continue;
			
			final RegistryObject<Block> HEAD = TD_BLOCKS.register("head_legendary_" + i, () -> new Model(4));
			LEGENDARY_HEADS.add(HEAD);
		}
		
		for(int i = 0; i < Threedee.ANCIENT_HEADS; i++) {
			if(skip("a", i))
				continue;
			
			final RegistryObject<Block> HEAD = TD_BLOCKS.register("head_ancient_" + i, () -> new Model(5));
			ANCIENT_HEADS.add(HEAD);
		}
	}
	
	public static List<ItemStack> getMortarPestles(){
		List<ItemStack> blocks = new ArrayList<>();
		
		blocks.add(new ItemStack(MORTAR_AND_PESTLE_ANDESITE.get()));
		blocks.add(new ItemStack(MORTAR_AND_PESTLE_DIORITE.get()));
		blocks.add(new ItemStack(MORTAR_AND_PESTLE_GRANITE.get()));
		blocks.add(new ItemStack(MORTAR_AND_PESTLE_BASALT.get()));
		blocks.add(new ItemStack(MORTAR_AND_PESTLE_BLACKSTONE.get()));
		blocks.add(new ItemStack(MORTAR_AND_PESTLE_DEEPSLATE.get()));
		
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
		blocks.add(new ItemStack(TINY_CAULDRON_CRIMSON.get()));
		blocks.add(new ItemStack(TINY_CAULDRON_WARPED.get()));
		
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
		blocks.add(new ItemStack(HANDSAW_CRIMSON.get()));
		blocks.add(new ItemStack(HANDSAW_WARPED.get()));
		
		return blocks;
	}
	
	public static List<ItemStack> getCommonHeads(){
		List<ItemStack> heads = new ArrayList<>();
		
		CommonModelBlocks.COMMON.getEntries().stream().forEach(block -> heads.add(new ItemStack(block.get())));
		
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