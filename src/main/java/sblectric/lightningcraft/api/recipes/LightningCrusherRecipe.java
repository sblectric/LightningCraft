package sblectric.lightningcraft.api.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.api.util.StackHelper;

/** An individual crusher recipe */
public class LightningCrusherRecipe {
	private Object in;
	private List<ItemStack> excluding;
	private List<ItemStack> inCache;
	private ItemStack out;
	
	public LightningCrusherRecipe(ItemStack out, Object in, ItemStack... excluding) {
		if(!(in instanceof List || in instanceof ItemStack || in instanceof String)) {
			throw new IllegalArgumentException("Crusher recipe input invalid.");
		}
		this.in = in;
		this.excluding = new JointList();
		if(excluding != null) {
			for(ItemStack s : excluding) this.excluding.add(s.copy());
		}
		this.inCache = null;
		this.out = out;
	}
	
	/** Get the input as a raw object */
	public Object getInputRaw() {
		return in;
	}
	
	/** Get the input as a list of ItemStacks (no exclusions) */
	public List<ItemStack> getInputBase() {
		if(in instanceof List) { // list of stacks
			return (List<ItemStack>)in;
		} else if(in instanceof ItemStack) { // single item stack
			return new JointList().join(((ItemStack)in).copy());
		} else { // ore dict
			if(OreDictionary.doesOreNameExist((String)in)) {
				return OreDictionary.getOres((String)in);
			} else {
				return new JointList();
			}
		}
	}
	
	/** Get the input as a list of ItemStacks (with exclusions) */
	public List<ItemStack> getInput() {
		if(inCache == null) { // cached for speed
			List<ItemStack> base = new ArrayList(getInputBase());
			for(ItemStack ex : excluding) {
				Iterator<ItemStack> iterator = base.iterator();
				while(iterator.hasNext()) {
					ItemStack in = iterator.next();
					if(StackHelper.areItemStacksEqualForCrafting(in, ex)) {
						iterator.remove();
					}
				}
			}
			return (inCache = base);
		} else {
			return inCache;
		}
	}
	
	/** Get the output */
	public ItemStack getOutput() {
		return out.copy();
	}
	
	/** Get invalid inputs */
	public List<ItemStack> getExcluding() {
		return excluding;
	}
	
}
