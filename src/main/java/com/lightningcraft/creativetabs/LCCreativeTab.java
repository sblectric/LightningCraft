package com.lightningcraft.creativetabs;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LCCreativeTab extends CreativeTabs {
	
	protected Item displayItem;
 
    public LCCreativeTab(String unlocalizedName) {
        super(unlocalizedName);
    }
	
    public LCCreativeTab(String unlocalizedName, Item item) {
        this(unlocalizedName);
        this.displayItem = item;
    }
    
    public LCCreativeTab(String unlocalizedName, Block item) {
        this(unlocalizedName, Item.getItemFromBlock(item));
    }
    
    public void updateItem(Item item) {
    	this.displayItem = item;
    }
    
    public void updateItem(Block item) {
    	updateItem(Item.getItemFromBlock(item));
    }

    @Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return displayItem;
	}
    
}