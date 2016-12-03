package sblectric.lightningcraft.worldgen.structure;

import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.util.IntList;
import sblectric.lightningcraft.util.WeightedRandomChestContent;

/** Class that defines a number of loot chests for a structure */
public class LootChestGroup {
	
	private int numChests;
	private JointList<Boolean> isPlaced = new JointList();
	private JointList<Boolean> isTrapped = new JointList();
	private JointList<WeightedRandomChestContent> defaultContents = new JointList();
	private JointList<JointList<WeightedRandomChestContent>> chestContents = new JointList();
	private IntList minChestStacks = new IntList();
	private IntList maxChestStacks = new IntList();
	private int minDefaultStacks;
	private int maxDefaultStacks;
	
	/** Class that defines a number of loot chests for a structure */
	public LootChestGroup(int numChests, int minStacks, int maxStacks, WeightedRandomChestContent[] chestContents) {
		this.numChests = numChests;
		this.isPlaced.join(false, numChests);
		this.minChestStacks.join(minDefaultStacks = minStacks, numChests);
		this.maxChestStacks.join(maxDefaultStacks = maxStacks, numChests);
		this.defaultContents = new JointList<WeightedRandomChestContent>().join(chestContents);
		this.chestContents.join(defaultContents, numChests);
		this.isTrapped.join(false, numChests);
	}
	
	/** Set the default chest contents */
	public void setChestContents(WeightedRandomChestContent[] chestContents) {
		this.defaultContents = new JointList<WeightedRandomChestContent>().join(chestContents);
	}
	
	/** Set the contents for a specific chest in the group */
	public void setChestContents(int index, WeightedRandomChestContent[] chestContents) {
		JointList<WeightedRandomChestContent> singleContents = new JointList<WeightedRandomChestContent>().join(chestContents);
		this.chestContents.set(index, singleContents);
	}
	
	/** Gets the default chest contents as an array */
	public List<WeightedRandomChestContent> getChestContents() {
		return defaultContents;
	}
	
	/** Gets the chest contents of the specified chest as an array */
	public List<WeightedRandomChestContent> getChestContents(int index) {
		return chestContents.get(index);
	}
	
	/** Sets the chest #index status as placed. */
	public void setChestPlaced(int index, boolean status) {
		if(index < isPlaced.size()) {
			this.isPlaced.set(index, status);
		}
	}
	
	/** Gets the chest #index placement status. */
	public boolean getChestPlaced(int index) {
		if(index < isPlaced.size()) {
			return this.isPlaced.get(index);
		} else {
			return false;
		}
	}
	
	/** Sets stack counts for the provided index */
	public void setStackMinMax(int index, int min, int max) {
		minChestStacks.set(index, min);
		maxChestStacks.set(index, max);
	}
	
	/** Gets a random stack count based on minChestStacks and maxChestStacks and the provided index */
	public int getStackCount(Random rand, int index) {
		return rand.nextInt(maxChestStacks.get(index) - minChestStacks.get(index) + 1) + minChestStacks.get(index);
	}
	
	/** Gets a random stack count based on minDefaultStacks and maxDefaultStacks */
	public int getStackCount(Random rand) {
		return rand.nextInt(maxDefaultStacks - minDefaultStacks + 1) + minDefaultStacks;
	}
	
	/** Is the specified chest trapped? */
	public boolean getIsTrapped(int index) {
		return this.isTrapped.get(index);
	}
	
	/** Set the specified chest as trapped */
	public void setIsTrapped(int index, boolean status) {
		this.isTrapped.set(index, status);
	}
	
	/** Write the chest placement progress to NBT */
	public void writeToNBT(NBTTagCompound tag) {
		for(int i = 0; i < numChests; i++) {
			tag.setBoolean("placedChest" + i, this.isPlaced.get(i));
		}		
	}
	
	/** Get the chest placement progress from NBT */
	public void readFromNBT(NBTTagCompound tag) {
		for(int i = 0; i < numChests; i++) {
			this.isPlaced.set(i, tag.getBoolean("placedChest" + i));
		}
	}

}
