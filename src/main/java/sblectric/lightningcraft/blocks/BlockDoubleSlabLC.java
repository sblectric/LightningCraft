package sblectric.lightningcraft.blocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.NonNullList;

/** The double slab block */
public class BlockDoubleSlabLC extends BlockSlabLC {
	
	@Override
	public boolean isDouble() {
		return true;
	}
	
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList list) {}

}
