package slimeattack07.threedee;

import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.NonNullList;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.datagen.DataBlockStates;
import slimeattack07.threedee.datagen.DataBlockTags;
import slimeattack07.threedee.datagen.DataItemModels;
import slimeattack07.threedee.datagen.DataItemTags;
import slimeattack07.threedee.datagen.DataLang;
import slimeattack07.threedee.datagen.DataLootTables;
import slimeattack07.threedee.datagen.DataRecipes;
import slimeattack07.threedee.init.AncientModelBlocks;
import slimeattack07.threedee.init.CommonModelBlocks;
import slimeattack07.threedee.init.EpicModelBlocks;
import slimeattack07.threedee.init.LegendaryModelBlocks;
import slimeattack07.threedee.init.RareModelBlocks;
import slimeattack07.threedee.init.TDBlocks;
import slimeattack07.threedee.init.TDItems;
import slimeattack07.threedee.init.TDRecipeSerializer;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.init.UncommonModelBlocks;
import slimeattack07.threedee.objects.blocks.ArtefactAnalyzer;
import slimeattack07.threedee.objects.blocks.ArtefactExchanger;
import slimeattack07.threedee.objects.blocks.CatalystCrop;
import slimeattack07.threedee.objects.blocks.CustomBlockBase;
import slimeattack07.threedee.objects.blocks.Handsaw;
import slimeattack07.threedee.objects.blocks.InteractBlock;
import slimeattack07.threedee.objects.blocks.ItemExchanger;
import slimeattack07.threedee.objects.blocks.Model;
import slimeattack07.threedee.objects.blocks.ModelAssembler;
import slimeattack07.threedee.objects.blocks.ModelFabricator;
import slimeattack07.threedee.objects.blocks.ModelRecycler;
import slimeattack07.threedee.objects.blocks.MortarPestle;
import slimeattack07.threedee.objects.blocks.Negotiator;
import slimeattack07.threedee.objects.blocks.TinyCauldron;
import slimeattack07.threedee.objects.items.ArtefactAnalyzerItem;
import slimeattack07.threedee.objects.items.ArtefactExchangerItem;
import slimeattack07.threedee.objects.items.BasicInterItem;
import slimeattack07.threedee.objects.items.ItemExchangerItem;
import slimeattack07.threedee.objects.items.ModelAssemblerItem;
import slimeattack07.threedee.objects.items.ModelFabricatorItem;
import slimeattack07.threedee.objects.items.ModelItem;
import slimeattack07.threedee.objects.items.ModelRecyclerItem;
import slimeattack07.threedee.objects.items.NegotiatorItem;
import slimeattack07.threedee.world.gen.OreGen;

@Mod("threedee")
@Mod.EventBusSubscriber(modid = Threedee.MOD_ID, bus = Bus.MOD)
public class Threedee {
/* TODO LIST: 
 * Add more machines?
 * Configs
 */
	
	public static Threedee instance;
	public static final String MOD_ID = "threedee";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static final int COMMON_HEADS = 114;
	public static final int UNCOMMON_HEADS = 107;
	public static final int RARE_HEADS = 104;
	public static final int EPIC_HEADS = 85;
	public static final int LEGENDARY_HEADS = 53;
	public static final int ANCIENT_HEADS = 80;
	
	public static final Comparator<ItemStack> HEAD_COMPARATOR = new Comparator<ItemStack>() {
		// first - second
	    @Override
	    public int compare(ItemStack m1, ItemStack m2) {
	    	return m1.getItem().getRegistryName().compareTo(m2.getItem().getRegistryName());
	     }
	};

	public static final CreativeModeTab TD_BLOCKS = new CreativeModeTab("threedee_blocks") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(TDBlocks.SMOOTH_ASPHALT.get().asItem());
		}
	};

	public static final CreativeModeTab TD_ITEMS = new CreativeModeTab("threedee_items") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(TDItems.DYE_PASTE.get());
		}
	};

	public static final CreativeModeTab COMMON_MODELS = new CreativeModeTab("threedee_common_models") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(CommonModelBlocks.APPLE.get().asItem());
		}
		
		
		@Override
		public void fillItemList(NonNullList<ItemStack> items) {
			super.fillItemList(items);

			items.sort(HEAD_COMPARATOR);
		}
	};
	
	public static final CreativeModeTab UNCOMMON_MODELS = new CreativeModeTab("threedee_uncommon_models") {
		@Override
		public Component getDisplayName() {
			return new TextComponent(ChatFormatting.GREEN + super.getDisplayName().getString());
		}
		
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(TDBlocks.UNCOMMON_HEADS.get(0).get().asItem());
		}
		
		@Override
		public void fillItemList(NonNullList<ItemStack> items) {
			super.fillItemList(items);

			items.sort(HEAD_COMPARATOR);
		}
	};
	
	public static final CreativeModeTab RARE_MODELS = new CreativeModeTab("threedee_rare_models") {
		@Override
		public Component getDisplayName() {
			return new TextComponent(ChatFormatting.AQUA + super.getDisplayName().getString());
		}
		
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(TDBlocks.RARE_HEADS.get(0).get().asItem());
		}
		
		@Override
		public void fillItemList(NonNullList<ItemStack> items) {
			super.fillItemList(items);

			items.sort(HEAD_COMPARATOR);
		}
	};
	
	public static final CreativeModeTab EPIC_MODELS = new CreativeModeTab("threedee_epic_models") {
		@Override
		public Component getDisplayName() {
			return new TextComponent(ChatFormatting.DARK_PURPLE + super.getDisplayName().getString());
		}
		
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(TDBlocks.EPIC_HEADS.get(0).get().asItem());
		}
		
		@Override
		public void fillItemList(NonNullList<ItemStack> items) {
			super.fillItemList(items);

			items.sort(HEAD_COMPARATOR);
		}
	};
	
	public static final CreativeModeTab LEGENDARY_MODELS = new CreativeModeTab("threedee_legendary_models") {
		@Override
		public Component getDisplayName() {
			return new TextComponent(ChatFormatting.GOLD + super.getDisplayName().getString());
		}
		
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(TDBlocks.LEGENDARY_HEADS.get(0).get().asItem());
		}
		
		@Override
		public void fillItemList(NonNullList<ItemStack> items) {
			super.fillItemList(items);

			items.sort(HEAD_COMPARATOR);
		}
	};
	
	public static final CreativeModeTab ANCIENT_MODELS = new CreativeModeTab("threedee_ancient_models") {
		@Override
		public Component getDisplayName() {
			return new TextComponent(ChatFormatting.RED + super.getDisplayName().getString());
		}
		
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(TDBlocks.ANCIENT_HEADS.get(0).get().asItem());
		}
		
		@Override
		public void fillItemList(NonNullList<ItemStack> items) {
			super.fillItemList(items);

			items.sort(HEAD_COMPARATOR);
		}
	};

	public Threedee() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		instance = this;
		
		TDBlocks.registerHeads();
		TDTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
		TDItems.TD_ITEMS.register(modEventBus);
		TDBlocks.TD_BLOCKS.register(modEventBus);
		CommonModelBlocks.COMMON.register(modEventBus);
		UncommonModelBlocks.UNCOMMON.register(modEventBus);
		RareModelBlocks.RARE.register(modEventBus);
		EpicModelBlocks.EPIC.register(modEventBus);
		LegendaryModelBlocks.LEGENDARY.register(modEventBus);
		AncientModelBlocks.ANCIENT.register(modEventBus);
				
		TDRecipeSerializer.SERIALIZERS.register(modEventBus);

		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, this::lootLoad);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, OreGen::onBiomeLoadingEvent);
		
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::clientRegistries);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event){
		DataGenerator generator = event.getGenerator();

		if(event.includeServer()){
			generator.addProvider(new DataRecipes(generator));
			generator.addProvider(new DataLootTables(generator));
			DataBlockTags block_tags = new DataBlockTags(generator, event.getExistingFileHelper());
			generator.addProvider(block_tags);
			generator.addProvider(new DataItemTags(generator, block_tags, event.getExistingFileHelper()));
		}

		if(event.includeClient()){
			generator.addProvider(new DataBlockStates(generator, event.getExistingFileHelper()));
			generator.addProvider(new DataItemModels(generator, event.getExistingFileHelper()));
			generator.addProvider(new DataLang(generator, "en_us"));
		}
	}
	
	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		TDBlocks.TD_BLOCKS.getEntries().stream()
				.filter(block -> (noCustomItem(block.get())))
				.map(RegistryObject::get).forEach(block -> {
					
					CreativeModeTab groupIn = TD_BLOCKS;
					BlockItem blockItem = null;

//					if (block instanceof CustomBlockBase)
//						groupIn = TD_MODELS;
					
					if (block instanceof InteractBlock)
						groupIn = TD_BLOCKS;

					if (block instanceof Model) {
						switch(((Model) block).getRarity()) {
							case COMMON: groupIn = COMMON_MODELS; break;
							case UNCOMMON: groupIn = UNCOMMON_MODELS; break;
							case RARE: groupIn = RARE_MODELS; break;
							case EPIC: groupIn = EPIC_MODELS; break;
							case LEGENDARY: groupIn = LEGENDARY_MODELS; break;
							
							default: groupIn = ANCIENT_MODELS;
						}
					}

					final Item.Properties properties = new Item.Properties().tab(groupIn);

					if (block instanceof Model)
						blockItem = new ModelItem(block, properties);
					else if (block instanceof MortarPestle)
						blockItem = new BasicInterItem(block, properties, "description.threedee.mortar_and_pestle");
					else if (block instanceof TinyCauldron)
						blockItem = new BasicInterItem(block, properties, "description.threedee.tiny_cauldron");
					else if (block instanceof Handsaw)
						blockItem = new BasicInterItem(block, properties, "description.threedee.handsaw");
					else if (block instanceof CustomBlockBase) {
							blockItem = new BlockItem(block, properties);
					}
					else
						blockItem = new BlockItem(block, properties);

					blockItem.setRegistryName(block.getRegistryName());
					registry.register(blockItem);
				});

		CommonModelBlocks.COMMON.getEntries().stream()
		.map(RegistryObject::get).forEach(block -> {
			registry.register(new ModelItem(block, new Item.Properties().tab(COMMON_MODELS)).setRegistryName(block.getRegistryName()));
		});
		
		UncommonModelBlocks.UNCOMMON.getEntries().stream()
		.map(RegistryObject::get).forEach(block -> {
			registry.register(new ModelItem(block, new Item.Properties().tab(UNCOMMON_MODELS)).setRegistryName(block.getRegistryName()));
		});
		
		RareModelBlocks.RARE.getEntries().stream()
		.map(RegistryObject::get).forEach(block -> {
			registry.register(new ModelItem(block, new Item.Properties().tab(RARE_MODELS)).setRegistryName(block.getRegistryName()));
		});
		
		EpicModelBlocks.EPIC.getEntries().stream()
		.map(RegistryObject::get).forEach(block -> {
			registry.register(new ModelItem(block, new Item.Properties().tab(EPIC_MODELS)).setRegistryName(block.getRegistryName()));
		});
		
		LegendaryModelBlocks.LEGENDARY.getEntries().stream()
		.map(RegistryObject::get).forEach(block -> {
			registry.register(new ModelItem(block, new Item.Properties().tab(LEGENDARY_MODELS)).setRegistryName(block.getRegistryName()));
		});
		
		AncientModelBlocks.ANCIENT.getEntries().stream()
		.map(RegistryObject::get).forEach(block -> {
			registry.register(new ModelItem(block, new Item.Properties().tab(ANCIENT_MODELS)).setRegistryName(block.getRegistryName()));
		});
		
		Block block = TDBlocks.MODEL_FABRICATOR.get();
		registry.register(new ModelFabricatorItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));

		block = TDBlocks.MODEL_ASSEMBLER.get();
		registry.register(new ModelAssemblerItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));

		block = TDBlocks.MODEL_RECYCLER.get();
		registry.register(new ModelRecyclerItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));
		
		block = TDBlocks.ARTEFACT_ANALYZER.get();
		registry.register(new ArtefactAnalyzerItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));
		
		block = TDBlocks.NEGOTIATOR.get();
		registry.register(new NegotiatorItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));
		
		block = TDBlocks.ARTEFACT_EXCHANGER.get();
		registry.register(new ArtefactExchangerItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));

		block = TDBlocks.ITEM_EXCHANGER.get();
		registry.register(new ItemExchangerItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));
	}
	
	public static boolean noCustomItem(Block block) {
		return !(block instanceof ModelFabricator || block instanceof ModelAssembler
				|| block instanceof ModelRecycler || block instanceof CatalystCrop
				|| block instanceof ArtefactAnalyzer || block instanceof Negotiator 
				|| block instanceof ArtefactExchanger || block instanceof ItemExchanger);
	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			OreGen.registerConfiguredFeatures();
		});
	}

	private void clientRegistries(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			setRenderLayers();
		});
	}

	public static void setRenderLayers(DeferredRegister<Block> reg) {
		reg.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			if(block instanceof CustomBlockBase) {
				CustomBlockBase base = (CustomBlockBase) block;
				ItemBlockRenderTypes.setRenderLayer(block, base.isTranslucent() ? RenderType.translucent() : RenderType.cutout());
			}
			else if(block instanceof CatalystCrop) 
				ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
		});
	}
	
	public static void setRenderLayers() {
		setRenderLayers(TDBlocks.TD_BLOCKS);
		setRenderLayers(CommonModelBlocks.COMMON);
		setRenderLayers(UncommonModelBlocks.UNCOMMON);
		setRenderLayers(RareModelBlocks.RARE);
		setRenderLayers(EpicModelBlocks.EPIC);
		setRenderLayers(LegendaryModelBlocks.LEGENDARY);
		setRenderLayers(AncientModelBlocks.ANCIENT);
	}
	
	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent event) {
		if(matchesDungeonLoot(event.getName().toString())) {
			LootPool.Builder pool_builder = LootPool.lootPool();
			pool_builder.setRolls(ConstantValue.exactly(1));
			
			LootPoolSingletonContainer.Builder<?> builder1 = LootItem.lootTableItem(TDItems.CATALYST_COMMON.get());
			builder1.setWeight(3);
			LootPoolSingletonContainer.Builder<?> builder2 = LootItem.lootTableItem(TDItems.CATALYST_UNCOMMON.get());
			builder2.setWeight(3);
			LootPoolSingletonContainer.Builder<?> builder3 = LootItem.lootTableItem(TDItems.CATALYST_RARE.get());
			builder3.setWeight(2);
			LootPoolSingletonContainer.Builder<?> builder4 = LootItem.lootTableItem(TDItems.CATALYST_EPIC.get());
			builder4.setWeight(1);
			LootPoolSingletonContainer.Builder<?> builder5 = LootItem.lootTableItem(TDItems.CATALYST_LEGENDARY.get());
			builder5.setWeight(1);
			LootPoolSingletonContainer.Builder<?> builder6 = LootItem.lootTableItem(Items.AIR);
			builder6.setWeight(10);
			
			pool_builder.add(builder1);
			pool_builder.add(builder2);
			pool_builder.add(builder3);
			pool_builder.add(builder4);
			pool_builder.add(builder5);
			pool_builder.add(builder6);
			
			LootPool pool = pool_builder.build();
			event.getTable().addPool(pool);
		}
	}
	
	public boolean matchesDungeonLoot(String event_name) {
		if(event_name.contains("minecraft:chests") && !event_name.contains("village")) {
			return (!(event_name.contains("igloo") || event_name.contains("dispenser") || event_name.contains("pillager") 
					|| event_name.contains("ship") || event_name.contains("bonus")));
		}
		
		return false;
	}
}