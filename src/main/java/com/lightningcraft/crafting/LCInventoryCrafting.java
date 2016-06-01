package com.lightningcraft.crafting;

import java.util.List;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

/** A helper class for checking recipes */
public class LCInventoryCrafting extends InventoryCrafting {
	
	protected ItemStack[] stackList;

	private LCInventoryCrafting(Container c, int xSz, int ySz) {
		super(c, xSz, ySz);
        int k = xSz * ySz;
        this.stackList = new ItemStack[k];
	}
	
	private LCInventoryCrafting(int xSz, int ySz) {
		this(null, xSz, ySz);
	}
	
	public LCInventoryCrafting(int xSz, int ySz, List<ItemStack> contents) {
		this(xSz, ySz);
		int a = 0;
		for(ItemStack i : contents) {
			this.setInventorySlotContents(a++, i);
		}
	}
	
	public LCInventoryCrafting(int xSz, int ySz, ItemStack... contents) {
		this(xSz, ySz);
		int a = 0;
		for(ItemStack i : contents) {
			this.setInventorySlotContents(a++, i);
		}
	}
	
    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
	public void setInventorySlotContents(int index, ItemStack stack) {
        this.stackList[index] = stack;
    }
    
    /**
     * Returns the stack in slot i
     */
    @Override
	public ItemStack getStackInSlot(int index)
    {
        return index >= this.getSizeInventory() ? null : this.stackList[index];
    }

}
