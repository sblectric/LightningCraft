package sblectric.lightningcraft.blocks;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/** The double slab block */
public class BlockDoubleSlabLC extends BlockSlabLC {
	
	@Override
	public boolean isDouble() {
		return true;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {}

}
