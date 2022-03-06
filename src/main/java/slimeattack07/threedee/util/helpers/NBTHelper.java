package slimeattack07.threedee.util.helpers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import slimeattack07.threedee.tileentity.ArtefactAnalyzerTE;
import slimeattack07.threedee.tileentity.ArtefactExchangerTE;
import slimeattack07.threedee.tileentity.BasicInterTE;
import slimeattack07.threedee.tileentity.HeadAssemblerTE;
import slimeattack07.threedee.tileentity.HeadBlockTE;
import slimeattack07.threedee.tileentity.HeadFabricatorTE;
import slimeattack07.threedee.tileentity.HeadRecyclerTE;
import slimeattack07.threedee.tileentity.ItemExchangerTE;
import slimeattack07.threedee.tileentity.NegotiatorTE;

public class NBTHelper {

	public static CompoundTag toNBT(Object o) {
		if (o instanceof ItemStack) {
			return writeItemStack((ItemStack) o);
		}
		
		if (o instanceof HeadAssemblerTE) {
			return writeHeadAssembler((HeadAssemblerTE) o);
		}
		
		if (o instanceof HeadFabricatorTE) {
			return writeHeadFabricator((HeadFabricatorTE) o);
		}
		
		if (o instanceof HeadRecyclerTE) {
			return writeHeadRecycler((HeadRecyclerTE) o);
		}
		
		if (o instanceof BasicInterTE) {
			return writeBasicInter((BasicInterTE) o);
		}
		
		if (o instanceof ArtefactAnalyzerTE) {
			return writeArtefactAnalyzer((ArtefactAnalyzerTE) o);
		}
		
		if (o instanceof HeadBlockTE) {
			return writeHeadBlock((HeadBlockTE) o);
		}
		
		if (o instanceof NegotiatorTE) {
			return writeNegotiator((NegotiatorTE) o);
		}
		
		if (o instanceof ArtefactExchangerTE) {
			return writeArtefactExchanger((ArtefactExchangerTE) o);
		}
		
		if (o instanceof ItemExchangerTE) {
			return writeItemExchanger((ItemExchangerTE) o);
		}
		
		return null;
	}
	
	private static CompoundTag writeBasicInter(BasicInterTE o) {
		CompoundTag compound = new CompoundTag();
		
		compound = safePutBoolean("stackmode", o.stackmode, compound);
		compound = safePutString("lastrecipe", o.last_recipe, compound);
		
		return compound;
	}
	
	private static CompoundTag writeHeadAssembler(HeadAssemblerTE o) {
		CompoundTag compound = new CompoundTag();
		
		compound = safePutBoolean("stackmode", o.stackmode, compound);
		compound = safePutInt("dye", o.dye_amount, compound);
		compound = safePutString("lastrecipe", o.last_recipe, compound);
		
		return compound;
	}
	
	private static CompoundTag writeHeadFabricator(HeadFabricatorTE o) {
		CompoundTag compound = new CompoundTag();
		
		compound = safePutBoolean("stackmode", o.stackmode, compound);
		compound = safePutInt("catalyst_amount", o.catalyst_amount, compound);
		compound = safePutInt("timepershot", o.time_per_shot, compound);
		compound = safePutInt("currenttime", o.current_time, compound);
		compound = safePutString("lastrecipe", o.last_recipe, compound);
		compound = safePutString("loottable", o.loot_table, compound);
		
		return compound;
	}
	
	private static CompoundTag writeHeadRecycler(HeadRecyclerTE o) {
		CompoundTag compound = new CompoundTag();
		
		compound = safePutBoolean("stackmode", o.stackmode, compound);
		compound = safePutInt("currenttime", o.current_time, compound);
		compound = safePutString("lastrecipe", o.last_recipe, "", compound);
		CompoundTag outputs = new CompoundTag();
		CompoundTag cor_outputs = new CompoundTag();
		CompoundTag rec_times = new CompoundTag();
		CompoundTag amounts = new CompoundTag();
		
		if(o.outputs != null) {
			for(int i = 0; i < o.outputs.size(); i++) {
				if(o.amounts.get(i) > 0) {
					outputs = safePutItemStack("" + i, o.outputs.get(i), outputs);
					cor_outputs = safePutItemStack("" + i, o.cor_outputs.get(i), cor_outputs);
					rec_times = safePutFloat("" + i, o.rec_times.get(i), rec_times);
					amounts = safePutInt("" + i, o.amounts.get(i), amounts);
				}
			}
		}
		
		compound.put("outputs", outputs);
		compound.put("cor_outputs", cor_outputs);
		compound.put("rec_times", rec_times);
		compound.put("amounts", amounts);
		
		return compound;
	}
	
	private static CompoundTag writeArtefactAnalyzer(ArtefactAnalyzerTE o) {
		CompoundTag compound = new CompoundTag();
		
		compound = safePutBoolean("running", o.running, compound);
		compound = safePutBoolean("item_can_be_sold", o.item_can_be_sold, compound);
		compound = safePutInt("tickstocraft", o.ticks_to_craft, compound);
		compound = safePutInt("currenttime", o.current_time, compound);
		compound = safePutString("lastrecipe", o.last_recipe, compound);
		compound = safePutString("loottable", o.loot_table, compound);
		compound = safePutItemStack("result", o.result, compound);
		
		return compound;
	}
	
	private static CompoundTag writeHeadBlock(HeadBlockTE o) {
		CompoundTag compound = new CompoundTag();
		
		compound = safePutBoolean("can_be_sold", o.can_be_sold, compound);
		compound = safePutBoolean("prices_known", o.prices_known, compound);
		compound = safePutInt("min", o.min, compound);
		compound = safePutInt("max", o.max, compound);
		
		return compound;
	}
	
	private static CompoundTag writeNegotiator(NegotiatorTE o) {
		CompoundTag compound = new CompoundTag();
		
		compound = safePutInt("currenttime", o.current_time, compound);
		compound = safePutBoolean("running", o.running, compound);
		compound = safePutItemStack("input", o.input, compound);
		
		return compound;
	}
	
	private static CompoundTag writeArtefactExchanger(ArtefactExchangerTE o) {
		CompoundTag compound = new CompoundTag();

		compound = safePutItemStack("card", o.card, compound);
		
		return compound;
	}
	
	private static CompoundTag writeItemExchanger(ItemExchangerTE o) {
		CompoundTag compound = new CompoundTag();

		compound = safePutItemStack("card", o.card, compound);
		compound = safePutString("last_recipe", o.last_recipe, compound);
		
		return compound;
	}
	
	private static CompoundTag writeItemStack(ItemStack stack) {
		CompoundTag compound = new CompoundTag();
		
		if(stack == ItemStack.EMPTY) {
			compound.putString("iamempty", "");
			return compound;
		}
		
		compound = safePutInt("count", stack.getCount(), compound);
		compound = safePutString("item", stack.getItem().getRegistryName().toString(), "minecraft:air", compound);
		compound.putByte("type", (byte) 0);
		
		if(stack.hasTag())
			compound.put("nbt", stack.getTag());
		
		return compound;
	}
	
	@Nullable
	public static Object fromNBT(@Nonnull CompoundTag compound) {
		switch (compound.getByte("type")) {
		case 0:
			return readItemStack(compound);
		default:
			return null;
		}
	}
	
	private static ItemStack readItemStack(CompoundTag compound) {
		
		if(compound.contains("iamempty"))
			return ItemStack.EMPTY;
		
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("item")));
		int count = compound.getInt("count");
		ItemStack stack = new ItemStack(item, count);
		
		if(compound.contains("nbt")) 
			stack.setTag(compound.getCompound("nbt"));
		
		return stack;
	}
	
	private static CompoundTag safePutBoolean(String name, boolean value, boolean default_val, CompoundTag compound) {
		try {
			compound.putBoolean(name, value);
		} catch (NullPointerException e) {
			compound.putBoolean(name, default_val);
		}
		
		return compound;
	}
	
	private static CompoundTag safePutBoolean(String name, boolean value, CompoundTag compound) {
		return safePutBoolean(name, value, false, compound);
	}
	
	private static CompoundTag safePutInt(String name, int value, int default_val, CompoundTag compound) {
		try {
			compound.putInt(name, value);
		} catch (NullPointerException e) {
			compound.putInt(name, default_val);
		}
		
		return compound;
	}
	
	private static CompoundTag safePutInt(String name, int value, CompoundTag compound) {
		return safePutInt(name, value, 0, compound);
	}
	
	private static CompoundTag safePutFloat(String name, float value, float default_val, CompoundTag compound) {
		try {
			compound.putFloat(name, value);
		} catch (NullPointerException e) {
			compound.putFloat(name, default_val);
		}
		
		return compound;
	}
	
	private static CompoundTag safePutFloat(String name, float value, CompoundTag compound) {
		return safePutFloat(name, value, 0, compound);
	}
	
	
	private static CompoundTag safePutString(String name, String value, String default_val, CompoundTag compound) {
		try {
			compound.putString(name, value);
		} catch (NullPointerException e) {
			compound.putString(name, default_val);
		}
		
		return compound;
	}
	
	private static CompoundTag safePutString(String name, String value, CompoundTag compound) {
		return safePutString(name, value, "", compound);
	}
	
	private static CompoundTag safePutItemStack(String name, ItemStack stack, CompoundTag compound) {
		try {
			compound.put(name, toNBT(stack));
		} catch (NullPointerException | IllegalArgumentException e) {
			compound.put(name, toNBT(new ItemStack(Items.AIR, 0)));
		}
		
		return compound;
	}
}