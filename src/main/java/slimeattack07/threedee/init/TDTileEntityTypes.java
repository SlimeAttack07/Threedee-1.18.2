package slimeattack07.threedee.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.tileentity.ArtefactAnalyzerTE;
import slimeattack07.threedee.tileentity.ArtefactExchangerTE;
import slimeattack07.threedee.tileentity.BasicInterTE;
import slimeattack07.threedee.tileentity.ModelAssemblerTE;
import slimeattack07.threedee.tileentity.ModelBlockTE;
import slimeattack07.threedee.tileentity.ModelFabricatorTE;
import slimeattack07.threedee.tileentity.ModelRecyclerTE;
import slimeattack07.threedee.tileentity.ItemExchangerTE;
import slimeattack07.threedee.tileentity.NegotiatorTE;
import slimeattack07.threedee.tileentity.hoppers.HopperAncientTE;
import slimeattack07.threedee.tileentity.hoppers.HopperCommonTE;
import slimeattack07.threedee.tileentity.hoppers.HopperEpicTE;
import slimeattack07.threedee.tileentity.hoppers.HopperLegendaryTE;
import slimeattack07.threedee.tileentity.hoppers.HopperRareTE;
import slimeattack07.threedee.tileentity.hoppers.HopperUncommonTE;

public class TDTileEntityTypes {
	public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITIES, Threedee.MOD_ID);

	public static final RegistryObject<BlockEntityType<BasicInterTE>> BASIC_INTERACT = TILE_ENTITY_TYPES.register(
			"basic_interact",
			() -> BlockEntityType.Builder.of(BasicInterTE::new, TDBlocks.MORTAR_AND_PESTLE_DIORITE.get(),
					TDBlocks.MORTAR_AND_PESTLE_ANDESITE.get(), TDBlocks.MORTAR_AND_PESTLE_GRANITE.get(),
					TDBlocks.TINY_CAULDRON_OAK.get(), TDBlocks.TINY_CAULDRON_BIRCH.get(),
					TDBlocks.TINY_CAULDRON_SPRUCE.get(), TDBlocks.TINY_CAULDRON_JUNGLE.get(),
					TDBlocks.TINY_CAULDRON_ACACIA.get(), TDBlocks.TINY_CAULDRON_DARK_OAK.get(),
					TDBlocks.HANDSAW_OAK.get(), TDBlocks.HANDSAW_BIRCH.get(), TDBlocks.HANDSAW_SPRUCE.get(),
					TDBlocks.HANDSAW_JUNGLE.get(), TDBlocks.HANDSAW_ACACIA.get(),
					TDBlocks.HANDSAW_DARK_OAK.get()).build(null));

	public static final RegistryObject<BlockEntityType<ModelAssemblerTE>> MODEL_ASSEMBLER = TILE_ENTITY_TYPES.register(
			"model_assembler",
			() -> BlockEntityType.Builder.of(ModelAssemblerTE::new, TDBlocks.MODEL_ASSEMBLER.get()).build(null));

	public static final RegistryObject<BlockEntityType<ModelFabricatorTE>> MODEL_FABRICATOR = TILE_ENTITY_TYPES
			.register("model_fabricator", () -> BlockEntityType.Builder
					.of(ModelFabricatorTE::new, TDBlocks.MODEL_FABRICATOR.get()).build(null));

	public static final RegistryObject<BlockEntityType<ModelRecyclerTE>> MODEL_RECYCLER = TILE_ENTITY_TYPES.register(
			"model_recycler",
			() -> BlockEntityType.Builder.of(ModelRecyclerTE::new, TDBlocks.MODEL_RECYCLER.get()).build(null));

	public static final RegistryObject<BlockEntityType<ArtefactAnalyzerTE>> ARTEFACT_ANALYZER = TILE_ENTITY_TYPES
			.register("artefact_analyzer", () -> BlockEntityType.Builder
					.of(ArtefactAnalyzerTE::new, TDBlocks.ARTEFACT_ANALYZER.get()).build(null));

	public static final RegistryObject<BlockEntityType<ModelBlockTE>> MODEL_BLOCK = TILE_ENTITY_TYPES.register(
			"model_block",
			() -> BlockEntityType.Builder.of(ModelBlockTE::new, TDBlocks.ANCIENT_HEADS.get(0).get()).build(null));

	public static final RegistryObject<BlockEntityType<NegotiatorTE>> NEGOTIATOR = TILE_ENTITY_TYPES.register(
			"negotiator",
			() -> BlockEntityType.Builder.of(NegotiatorTE::new, TDBlocks.NEGOTIATOR.get()).build(null));

	public static final RegistryObject<BlockEntityType<ArtefactExchangerTE>> ARTEFACT_EXCHANGER = TILE_ENTITY_TYPES
			.register("artefact_exchanger", () -> BlockEntityType.Builder
					.of(ArtefactExchangerTE::new, TDBlocks.ARTEFACT_EXCHANGER.get()).build(null));

	public static final RegistryObject<BlockEntityType<ItemExchangerTE>> ITEM_EXCHANGER = TILE_ENTITY_TYPES
			.register("item_exchanger", () -> BlockEntityType.Builder
					.of(ItemExchangerTE::new, TDBlocks.ITEM_EXCHANGER.get()).build(null));

	
	public static final RegistryObject<BlockEntityType<HopperCommonTE>> COMMON_HOPPER = TILE_ENTITY_TYPES
			.register("common_hopper", () -> BlockEntityType.Builder
					.of(HopperCommonTE::new, TDBlocks.HOPPER_COMMON.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<HopperUncommonTE>> UNCOMMON_HOPPER = TILE_ENTITY_TYPES
			.register("uncommon_hopper", () -> BlockEntityType.Builder
					.of(HopperUncommonTE::new, TDBlocks.HOPPER_UNCOMMON.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<HopperRareTE>> RARE_HOPPER = TILE_ENTITY_TYPES
			.register("rare_hopper", () -> BlockEntityType.Builder
					.of(HopperRareTE::new, TDBlocks.HOPPER_RARE.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<HopperEpicTE>> EPIC_HOPPER = TILE_ENTITY_TYPES
			.register("epic_hopper", () -> BlockEntityType.Builder
					.of(HopperEpicTE::new, TDBlocks.HOPPER_EPIC.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<HopperLegendaryTE>> LEGENDARY_HOPPER = TILE_ENTITY_TYPES
			.register("legendary_hopper", () -> BlockEntityType.Builder
					.of(HopperLegendaryTE::new, TDBlocks.HOPPER_LEGENDARY.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<HopperAncientTE>> ANCIENT_HOPPER = TILE_ENTITY_TYPES
			.register("ancient_hopper", () -> BlockEntityType.Builder
					.of(HopperAncientTE::new, TDBlocks.HOPPER_ANCIENT.get()).build(null));
}
