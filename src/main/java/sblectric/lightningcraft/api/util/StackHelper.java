package sblectric.lightningcraft.api.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import sblectric.lightningcraft.api.recipes.LightningInfusionRecipe;

/** Helps with ItemStack functionality */
public class StackHelper {

	private static Map<String, Integer> oreMap = new HashMap();
	private static Map<String, Integer> subMap = new HashMap();

	public static Random rand = new Random();

	/** Animate an ItemStack based on makeItemStackFromString (clientside only) */
	@SideOnly(Side.CLIENT)
	public static @Nonnull ItemStack animateItemStackFromString(String stackString, boolean change) {
		int ores;
		if(OreDictionary.doesOreNameExist(stackString)) {
			ores = OreDictionary.getOres(stackString).size();
		} else {
			ores = 1;
		}
		int oreIndex = 0;
		int subIndex = 0;
		ItemStack result;

		if(oreMap.containsKey(stackString))	oreIndex = oreMap.get(stackString);
		if(change) {
			if(ores > 0) {
				oreIndex = rand.nextInt(ores);
			} else {
				oreIndex = 0;
			}
			oreMap.put(stackString, oreIndex);
		}

		ItemStack primary = makeItemStackFromString(stackString, oreIndex);
		if(primary.isEmpty()) return ItemStack.EMPTY;

		NonNullList<ItemStack> subItems = NonNullList.create();
		primary.getItem().getSubItems(null, subItems);

		if(subMap.containsKey(stackString))	subIndex = subMap.get(stackString);
		if(primary.getItemDamage() == OreDictionary.WILDCARD_VALUE && !subItems.isEmpty()) {
			if(change) {
				subIndex = rand.nextInt(subItems.size());
				subMap.put(stackString, subIndex);
			}
			result = subItems.get(subIndex);
		} else {
			result = primary;
		}
		return result;
	}

	/** Returns true if the specified string corresponds to an oredict entry */
	private static boolean isStringOreDict(String stackString) {
		if(stackString == LightningInfusionRecipe.nullIdentifier) return false;
		return OreDictionary.doesOreNameExist(stackString);
	}

	/** Creates a new ItemStack from the string acquired from makeStringFromItemStack or an oredict name, with an oredict index option */
	public static @Nonnull ItemStack makeItemStackFromString(String stackString, int oreIndex) {
		if(stackString == LightningInfusionRecipe.nullIdentifier) return ItemStack.EMPTY;
		try { // try to load it as a regular NBT stack
			if(!isStringOreDict(stackString)) {
				return new ItemStack(JsonToNBT.getTagFromJson(stackString));
			} else {
				throw new NBTException("OreDict exists", "", 0);
			}
		} catch(NBTException e) { // now try to get it as an oredict entry
			List<ItemStack> list;
			if(isStringOreDict(stackString) && oreIndex < (list = OreDictionary.getOres(stackString)).size()) {
				return list.get(oreIndex); // yep
			} else {
				return ItemStack.EMPTY; // guess not
			}
		}
	}

	/** Creates a new ItemStack from the string acquired from makeStringFromItemStack or an oredict name */
	public static @Nonnull ItemStack makeItemStackFromString(String stackString) {
		return makeItemStackFromString(stackString, 0);
	}

	/** Get metadata from a string version of an ItemStack */
	public static int getMetaFromString(String stackString) {
		if(!isStringOreDict(stackString)) {
			ItemStack stack = makeItemStackFromString(stackString);
			return !stack.isEmpty() ? stack.getItemDamage() : 0;
		} else {
			return -1; // oredict flag
		}
	}

	/** Change a string's metadata information (assumes the stack is non-null) */
	public static String changeStringMeta(String stackString, int newMeta) {
		try {
			if(!isStringOreDict(stackString)) {
				ItemStack stack = makeItemStackFromString(stackString);
				stack.setItemDamage(newMeta);
				return makeStringFromItemStack(stack);
			}
		} catch(NullPointerException e) {}
		return stackString;
	}
	
	/** Change a string's NBT information (assumes the stack is non-null) */
	public static String changeStringNBT(String stackString, NBTTagCompound newTag) {
		try {
			if(!isStringOreDict(stackString)) {
				ItemStack stack = makeItemStackFromString(stackString);
				stack.setTagCompound(newTag);
				return makeStringFromItemStack(stack);
			}
		} catch(NullPointerException e) {}
		return stackString;
	}

	/** Strip a stack of its metadata information (just set it to zero) */
	public static String stripMetaFromString(String stackString) {
		return changeStringMeta(stackString, 0);
	}
	
	/** Strip a stack of its NBT information (just set it to null) */
	public static String stripNBTFromString(String stackString) {
		return changeStringNBT(stackString, null);
	}

	/** Turn an ItemStack or oredict name into a String */
	public static String makeStringFromItemStack(@Nonnull Object stack) {
		if(stack instanceof ItemStack) {
			ItemStack s = (ItemStack)stack;
			return s.writeToNBT(new NBTTagCompound()).toString();
		} else if(stack instanceof String) {
			return (String)stack;
		} else {
			throw new IllegalArgumentException("Parameter must be a String or an ItemStack, was given " + stack.getClass());
		}
	}

	/** An unlimited type of areItemStacksEqual for crafting recipes (non-amount sensitive, cannot be null) */
	public static boolean areItemStacksEqualForCrafting(@Nonnull ItemStack... stacks) {
		ItemStack comp = stacks[0];
		if(comp.isEmpty()) return false;
		ItemStack comp1 = comp.copy(); comp1.setCount(1);
		for(int n = 1; n < stacks.length; n++) {
			if(stacks[n].isEmpty()) return false;
			ItemStack comp2 = stacks[n].copy(); comp2.setCount(1);
			if(!ItemStack.areItemStacksEqual(comp1, comp2)) { // try regular and oredict
				if(comp1.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
					comp2.setItemDamage(OreDictionary.WILDCARD_VALUE);
					if(!ItemStack.areItemStacksEqual(comp1, comp2)) return false;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Sets the color for the specified armor ItemStack.
	 */
	public static boolean setStackColor(@Nonnull ItemStack stack, Color color) {
		if(!(stack.getItem() instanceof ItemArmor))
			return false;
		Item armor = stack.getItem();

		if (!(armor == Items.LEATHER_BOOTS || armor == Items.LEATHER_LEGGINGS || 
				armor == Items.LEATHER_CHESTPLATE || armor == Items.LEATHER_HELMET)) {
			return false;
		} else {
			NBTTagCompound nbttagcompound = stack.getTagCompound();
			if(nbttagcompound == null) nbttagcompound = new NBTTagCompound();
			NBTTagCompound disp = new NBTTagCompound();
			disp.setInteger("color", color.getRGB());
			nbttagcompound.setTag("display", disp);
			stack.setTagCompound(nbttagcompound);
			return true;
		}
	}

	/** Gets the enchantments that exist on a specified item stack */
	public static List<NBTTagCompound> getEnchantments(@Nonnull ItemStack s) {
		List enchList = new ArrayList<NBTTagCompound>();

		if(!s.isEmpty() && s.hasTagCompound()) {
			NBTTagList enchants;
			String ench = s.getItem() == Items.ENCHANTED_BOOK ? "StoredEnchantments" : "ench";
			enchants = (NBTTagList) s.getTagCompound().getTag(ench);

			if(enchants != null && enchants.tagCount() > 0) {
				for(int i = 0; i < enchants.tagCount(); i++) {
					NBTTagCompound enchant = enchants.getCompoundTagAt(i);
					enchList.add(enchant);
				}
			}
		}

		return enchList;
	}

	/** Adds a list of enchantments to a specified item stack */
	public static boolean addEnchantments(@Nonnull ItemStack s, List<NBTTagCompound> enchList) {
		if(!s.isEmpty()) {
			String ench = s.getItem() == Items.ENCHANTED_BOOK ? "StoredEnchantments" : "ench";
			if(!s.hasTagCompound()) s.setTagCompound(new NBTTagCompound());
			NBTTagList currentEnchants = (NBTTagList) s.getTagCompound().getTag(ench);

			if(currentEnchants == null) {
				currentEnchants = new NBTTagList();
			}

			for(int i = 0; i < enchList.size(); i++) {
				currentEnchants.appendTag(enchList.get(i));
			}

			s.getTagCompound().setTag(ench, currentEnchants);
			return true;
		}
		return false;
	}
	
	/** Returns true if the ItemStack has an oredict entry that starts with 'start' */
	public static boolean oreDictNameStartsWith(@Nonnull ItemStack ore, String start) {
		for(int i : OreDictionary.getOreIDs(ore)) {
			if(OreDictionary.getOreName(i).startsWith(start)) return true;
		}
		return false;
	}
	
	/** Returns true if the ItemStack has an oredict entry that equals 'equals' */
	public static boolean oreDictNameEquals(@Nonnull ItemStack ore, String equals) {
		for(int i : OreDictionary.getOreIDs(ore)) {
			if(OreDictionary.getOreName(i).equals(equals)) return true;
		}
		return false;
	}

	/** Make an integer array based on the size of 'stacks' */
	public static int[] makeIntArray(@Nonnull ItemStack[] stacks) {
		return makeIntArray(stacks.length);
	}
	
	/** Make an integer array based on a stack size */
	public static int[] makeIntArray(int size) {
		int[] ints = new int[size];
		for(int i = 0; i < size; i++) ints[i] = i;
		return ints;
	}

}
