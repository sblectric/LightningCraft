package sblectric.lightningcraft.blocks;

import java.util.List;

import sblectric.lightningcraft.items.blocks.ItemSlabLC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** The double slab block */
public class BlockDoubleSlabLC extends BlockSlabLC {
	
	@Override
	public boolean isDouble() {
		return true;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {}

}
