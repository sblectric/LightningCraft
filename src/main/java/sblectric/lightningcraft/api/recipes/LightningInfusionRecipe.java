package sblectric.lightningcraft.api.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.api.util.StackHelper;

/** A single infusion recipe */
public class LightningInfusionRecipe {
	
	public static final String nullIdentifier = "NULL";
	public static final int itemsNeeded = 4;
	
	private final ItemStack output;
	private final String infuse;
	private final List<String> items;
	private final int cost;
	private boolean nbtSensitive;
	
	/** A new infusion recipe with Items, Blocks, ItemStacks, or oreDict entries */
	public LightningInfusionRecipe(ItemStack output, int cost, Object infuse, Object... surrounding) {
		List items = new ArrayList(Arrays.asList(surrounding));
		
		// make sure the size is exact
		if(items.size() > itemsNeeded) {
			throw new IllegalArgumentException("There must be at most " + itemsNeeded + " surrounding items in an infusion recipe.");
		}
		while(items.size() < itemsNeeded) {
			items.add(null); // pad the infusion recipe with nulls
		}
		
		// set the output and cost
		this.output = output;
		this.cost = cost;
		
		// set the infused item
		if(infuse == null) {
			throw new IllegalArgumentException("The infused item cannot be null!");
		} else if(infuse instanceof Block) {
			this.infuse = StackHelper.makeStringFromItemStack(new ItemStack((Block)infuse));
		} else if(infuse instanceof Item) {
			this.infuse = StackHelper.makeStringFromItemStack(new ItemStack((Item)infuse));
		} else {
			this.infuse = StackHelper.makeStringFromItemStack(infuse);
		}
		
		// set the surrounding items
		this.items = new JointList();
		for(Object o : items) {
			if(o == null) {
				this.items.add(nullIdentifier);
			} else if(o instanceof Block) {
				this.items.add(StackHelper.makeStringFromItemStack(new ItemStack((Block)o)));
			} else if(o instanceof Item) {
				this.items.add(StackHelper.makeStringFromItemStack(new ItemStack((Item)o)));
			} else {
				this.items.add(StackHelper.makeStringFromItemStack(o));
			}
		}
	}
	
	/** Gets the ItemStack output of this recipe */
	public ItemStack getOutput() {
		return output;
	}
	
	/** Gets the cost of this infusion */
	public int getCost() {
		return cost;
	}
	
	/** Gets the item to be infused */
	public String getInfuseItem() {
		return infuse;
	}
	
	/** Gets the item to be infused as a list of possible ItemStack strings */
	public List<String> getInfuseItemAsOre() {
		JointList<String> list = new JointList();
		if(OreDictionary.doesOreNameExist(infuse)) {
			for(ItemStack s : OreDictionary.getOres(infuse)) {
				list.add(StackHelper.makeStringFromItemStack(s));
			}
		}
		return list;
	}
	
	/** Get the items around the item to be infused */
	public List<String> getItems() {
		return items;
	}
	
	/** Get the items around the item to be infused as a list of list of possible ItemStack strings */
	public List<List<String>> getItemsAsOres() {
		JointList<List<String>> list = new JointList();
		for(String name : items) {
			JointList<String> list2 = new JointList();
			if(OreDictionary.doesOreNameExist(name)) {
				for(ItemStack s : OreDictionary.getOres(name)) {
					list2.add(StackHelper.makeStringFromItemStack(s));
				}
			}
			list.add(list2);
		}
		return list;
	}
	
	/** Set the recipe to be NBT sensitive, i.e. potions */
	public LightningInfusionRecipe setNBTSensitive() {
		this.nbtSensitive = true;
		return this;
	}
	
	/** Is the recipe NBT sensitive? */
	public boolean getNBTSensitive() {
		return this.nbtSensitive;
	}
	
}
