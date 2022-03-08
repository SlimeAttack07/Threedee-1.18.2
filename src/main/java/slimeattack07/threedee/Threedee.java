package slimeattack07.threedee;

import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.NonNullList;
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
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import slimeattack07.threedee.init.TDBlocks;
import slimeattack07.threedee.init.TDItems;
import slimeattack07.threedee.init.TDRecipeSerializer;
import slimeattack07.threedee.init.TDTileEntityTypes;
import slimeattack07.threedee.objects.blocks.ArtefactAnalyzer;
import slimeattack07.threedee.objects.blocks.ArtefactExchanger;
import slimeattack07.threedee.objects.blocks.CatalystCrop;
import slimeattack07.threedee.objects.blocks.CustomBlockBase;
import slimeattack07.threedee.objects.blocks.Handsaw;
import slimeattack07.threedee.objects.blocks.Head;
import slimeattack07.threedee.objects.blocks.HeadAssembler;
import slimeattack07.threedee.objects.blocks.HeadFabricator;
import slimeattack07.threedee.objects.blocks.HeadRecycler;
import slimeattack07.threedee.objects.blocks.InteractBlock;
import slimeattack07.threedee.objects.blocks.ItemExchanger;
import slimeattack07.threedee.objects.blocks.MortarPestle;
import slimeattack07.threedee.objects.blocks.Negotiator;
import slimeattack07.threedee.objects.blocks.TinyCauldron;
import slimeattack07.threedee.objects.items.ArtefactAnalyzerItem;
import slimeattack07.threedee.objects.items.ArtefactExchangerItem;
import slimeattack07.threedee.objects.items.BasicInterItem;
import slimeattack07.threedee.objects.items.HeadAssemblerItem;
import slimeattack07.threedee.objects.items.HeadBasedModelItem;
import slimeattack07.threedee.objects.items.HeadBlockItem;
import slimeattack07.threedee.objects.items.HeadFabricatorItem;
import slimeattack07.threedee.objects.items.HeadRecyclerItem;
import slimeattack07.threedee.objects.items.ItemExchangerItem;
import slimeattack07.threedee.objects.items.NegotiatorItem;
import slimeattack07.threedee.world.gen.OreGen;

@Mod("threedee")
@Mod.EventBusSubscriber(modid = Threedee.MOD_ID, bus = Bus.MOD)
public class Threedee {
/* TODO LIST: 
 * Trees & log blocks are broken
 * Sapling advancement uses normal sapling temporarily
 * Add more machines?
 * Configs
 * Catalyzed bonemeal fixes
 * Test if machines work in other dimensions since we use World.OVERWORLD in some places (I think..)
 * Test if comparator for head recycler works (prob doesnt)
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
	    	if (m1.getItem() instanceof HeadBlockItem) {
	    		HeadBlockItem i1 = (HeadBlockItem) m1.getItem();
	    		
	    		if(m2.getItem() instanceof HeadBlockItem) {
	    			HeadBlockItem i2 = (HeadBlockItem) m2.getItem();
	    			
	    			return i1.getHeadNumber() - i2.getHeadNumber();
	    		}
	    		return 1;
	    	}
	    	
	    	else if (m2.getItem() instanceof HeadBlockItem)
	    		return -1;
	    	
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

	public static final CreativeModeTab TD_MODELS = new CreativeModeTab("threedee_models") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(TDBlocks.RED_WATER_BOTTLE.get().asItem());
		}
	};

	public static final CreativeModeTab TD_COMMON_HEADS = new CreativeModeTab("threedee_common_heads") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(TDBlocks.COMMON_HEADS.get(0).get().asItem());
		}
		
		
		@Override
		public void fillItemList(NonNullList<ItemStack> items) {
			super.fillItemList(items);

			items.sort(HEAD_COMPARATOR);
		}
	};
	
	public static final CreativeModeTab TD_UNCOMMON_HEADS = new CreativeModeTab("threedee_uncommon_heads") {
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
	
	public static final CreativeModeTab TD_RARE_HEADS = new CreativeModeTab("threedee_rare_heads") {
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
	
	public static final CreativeModeTab TD_EPIC_HEADS = new CreativeModeTab("threedee_epic_heads") {
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
	
	public static final CreativeModeTab TD_LEGENDARY_HEADS = new CreativeModeTab("threedee_legendary_heads") {
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
	
	public static final CreativeModeTab TD_ANCIENT_HEADS = new CreativeModeTab("threedee_ancient_heads") {
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
//		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, TdOreGen::genOres);
				
		TDRecipeSerializer.SERIALIZERS.register(modEventBus);

		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, this::lootLoad);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, OreGen::onBiomeLoadingEvent);
		
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::clientRegistries);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		TDBlocks.TD_BLOCKS.getEntries().stream()
				.filter(block -> (noCustomItem(block.get())))
				.map(RegistryObject::get).forEach(block -> {
					
					CreativeModeTab groupIn = TD_BLOCKS;
					BlockItem blockItem = null;

					if (block instanceof CustomBlockBase)
						groupIn = TD_MODELS;
					
					if (block instanceof InteractBlock)
						groupIn = TD_BLOCKS;

					if (block instanceof Head) {
						switch(((Head) block).getRarity()) {
							case COMMON: groupIn = TD_COMMON_HEADS; break;
							case UNCOMMON: groupIn = TD_UNCOMMON_HEADS; break;
							case RARE: groupIn = TD_RARE_HEADS; break;
							case EPIC: groupIn = TD_EPIC_HEADS; break;
							case LEGENDARY: groupIn = TD_LEGENDARY_HEADS; break;
							
							default: groupIn = TD_ANCIENT_HEADS;
						}
					}

					final Item.Properties properties = new Item.Properties().tab(groupIn);

					if (block instanceof Head)
						blockItem = new HeadBlockItem(block, properties);
					else if (block instanceof MortarPestle)
						blockItem = new BasicInterItem(block, properties, "description.threedee.mortar_and_pestle");
					else if (block instanceof TinyCauldron)
						blockItem = new BasicInterItem(block, properties, "description.threedee.tiny_cauldron");
					else if (block instanceof Handsaw)
						blockItem = new BasicInterItem(block, properties, "description.threedee.handsaw");
					else if (block instanceof CustomBlockBase) {
						CustomBlockBase cbb = (CustomBlockBase) block;
						if (cbb.isBasedOnHead())
							blockItem = new HeadBasedModelItem(block, properties);
						else
							blockItem = new BlockItem(block, properties);
					}
					else
						blockItem = new BlockItem(block, properties);

					blockItem.setRegistryName(block.getRegistryName());
					registry.register(blockItem);
				});

		Block block = TDBlocks.HEAD_FABRICATOR.get();
		registry.register(new HeadFabricatorItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));

		block = TDBlocks.HEAD_ASSEMBLER.get();
		registry.register(new HeadAssemblerItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));

		block = TDBlocks.HEAD_RECYCLER.get();
		registry.register(new HeadRecyclerItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));
		
		block = TDBlocks.ARTEFACT_ANALYZER.get();
		registry.register(new ArtefactAnalyzerItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));
		
		block = TDBlocks.NEGOTIATOR.get();
		registry.register(new NegotiatorItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));
		
		block = TDBlocks.ARTEFACT_EXCHANGER.get();
		registry.register(new ArtefactExchangerItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));

		block = TDBlocks.ITEM_EXCHANGER.get();
		registry.register(new ItemExchangerItem(block, new Item.Properties().tab(TD_BLOCKS)).setRegistryName(block.getRegistryName()));

		LOGGER.info("blockitems registered"); // remove in final version
	}
	
	public static boolean noCustomItem(Block block) {
		return !(block instanceof HeadFabricator || block instanceof HeadAssembler
				|| block instanceof HeadRecycler || block instanceof CatalystCrop
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

	public static void setRenderLayers() {
		TDBlocks.TD_BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			// set translucent models
			

			// set cut-out models
			if (block instanceof CustomBlockBase || block instanceof CatalystCrop)
				ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
		});
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