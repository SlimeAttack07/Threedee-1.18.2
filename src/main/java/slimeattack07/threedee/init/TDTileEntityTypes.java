package slimeattack07.threedee.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.Threedee;
import slimeattack07.threedee.tileentity.ArtefactAnalyzerTE;
import slimeattack07.threedee.tileentity.ArtefactExchangerTE;
import slimeattack07.threedee.tileentity.BasicInterTE;
import slimeattack07.threedee.tileentity.HeadAssemblerTE;
import slimeattack07.threedee.tileentity.HeadBlockTE;
import slimeattack07.threedee.tileentity.HeadFabricatorTE;
import slimeattack07.threedee.tileentity.HeadRecyclerTE;
import slimeattack07.threedee.tileentity.HopperRarityTE;
import slimeattack07.threedee.tileentity.ItemExchangerTE;
import slimeattack07.threedee.tileentity.NegotiatorTE;

public class TDTileEntityTypes {
	// public static BlockEntityType<?> HEAD;
	public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITIES, Threedee.MOD_ID);

	public static final RegistryObject<BlockEntityType<BasicInterTE>> TD_BASICINTER = TILE_ENTITY_TYPES.register(
			"td_basicinter",
			() -> BlockEntityType.Builder.of(BasicInterTE::new, TDBlocks.MORTAR_AND_PESTLE_DIORITE.get(),
					TDBlocks.MORTAR_AND_PESTLE_ANDESITE.get(), TDBlocks.MORTAR_AND_PESTLE_GRANITE.get(),
					TDBlocks.TINY_CAULDRON_OAK.get(), TDBlocks.TINY_CAULDRON_BIRCH.get(),
					TDBlocks.TINY_CAULDRON_SPRUCE.get(), TDBlocks.TINY_CAULDRON_JUNGLE.get(),
					TDBlocks.TINY_CAULDRON_ACACIA.get(), TDBlocks.TINY_CAULDRON_DARK_OAK.get(),
					TDBlocks.HANDSAW_OAK.get(), TDBlocks.HANDSAW_BIRCH.get(), TDBlocks.HANDSAW_SPRUCE.get(),
					TDBlocks.HANDSAW_JUNGLE.get(), TDBlocks.HANDSAW_ACACIA.get(),
					TDBlocks.HANDSAW_DARK_OAK.get()).build(null));

	public static final RegistryObject<BlockEntityType<HeadAssemblerTE>> TD_HEADASSEMBLER = TILE_ENTITY_TYPES.register(
			"td_headassembler",
			() -> BlockEntityType.Builder.of(HeadAssemblerTE::new, TDBlocks.HEAD_ASSEMBLER.get()).build(null));

	public static final RegistryObject<BlockEntityType<HeadFabricatorTE>> TD_HEADFABRICATOR = TILE_ENTITY_TYPES
			.register("td_headfabricator", () -> BlockEntityType.Builder
					.of(HeadFabricatorTE::new, TDBlocks.HEAD_FABRICATOR.get()).build(null));

	public static final RegistryObject<BlockEntityType<HeadRecyclerTE>> TD_HEADRECYCLER = TILE_ENTITY_TYPES.register(
			"td_headrecycler",
			() -> BlockEntityType.Builder.of(HeadRecyclerTE::new, TDBlocks.HEAD_RECYCLER.get()).build(null));

	public static final RegistryObject<BlockEntityType<ArtefactAnalyzerTE>> TD_ARTEFACTANALYZER = TILE_ENTITY_TYPES
			.register("td_artefactanalyzer", () -> BlockEntityType.Builder
					.of(ArtefactAnalyzerTE::new, TDBlocks.ARTEFACT_ANALYZER.get()).build(null));

	public static final RegistryObject<BlockEntityType<HeadBlockTE>> TD_HEADBLOCK = TILE_ENTITY_TYPES.register(
			"td_headblock",
			() -> BlockEntityType.Builder.of(HeadBlockTE::new, TDBlocks.ANCIENT_HEADS.get(0).get()).build(null));

	public static final RegistryObject<BlockEntityType<NegotiatorTE>> TD_NEGOTIATOR = TILE_ENTITY_TYPES.register(
			"td_negotiator",
			() -> BlockEntityType.Builder.of(NegotiatorTE::new, TDBlocks.NEGOTIATOR.get()).build(null));

	public static final RegistryObject<BlockEntityType<ArtefactExchangerTE>> TD_ARTEFACTEXCHANGER = TILE_ENTITY_TYPES
			.register("td_artefactexchanger", () -> BlockEntityType.Builder
					.of(ArtefactExchangerTE::new, TDBlocks.ARTEFACT_EXCHANGER.get()).build(null));

	public static final RegistryObject<BlockEntityType<ItemExchangerTE>> TD_ITEMEXCHANGER = TILE_ENTITY_TYPES
			.register("td_itemexchanger", () -> BlockEntityType.Builder
					.of(ItemExchangerTE::new, TDBlocks.ITEM_EXCHANGER.get()).build(null));

	
	public static final RegistryObject<BlockEntityType<HopperRarityTE>> TD_HOPPERRARITY = TILE_ENTITY_TYPES.register(
			"td_hopperrarity",
			() -> BlockEntityType.Builder.of(HopperRarityTE::new, TDBlocks.HOPPER_COMMON.get(),
					TDBlocks.HOPPER_UNCOMMON.get(), TDBlocks.HOPPER_RARE.get(), TDBlocks.HOPPER_EPIC.get(),
					TDBlocks.HOPPER_LEGENDARY.get(), TDBlocks.HOPPER_ANCIENT.get()).build(null));
}
