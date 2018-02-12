package sblectric.lightningcraft.creativetabs;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LCCreativeTab extends CreativeTabs {
	
	protected ItemStack displayStack;
 
    public LCCreativeTab(String unlocalizedName) {
        super(unlocalizedName);
    }
	
    public LCCreativeTab(String unlocalizedName, ItemStack stack) {
        this(unlocalizedName);
        this.displayStack = stack;
    }
    
    public LCCreativeTab(String unlocalizedName, Item item) {
    	this(unlocalizedName, new ItemStack(item));
    }
    
    public LCCreativeTab(String unlocalizedName, Block item) {
        this(unlocalizedName, new ItemStack(item));
    }
    
    public void updateItem(ItemStack stack) {
    	this.displayStack = stack;
    }
    
    public void updateItem(Item item) {
    	updateItem(new ItemStack(item));
    }
    
    public void updateItem(Block item) {
    	updateItem(new ItemStack(item));
    }

    @Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return displayStack;
	}
    
}