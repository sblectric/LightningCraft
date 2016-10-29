package sblectric.lightningcraft.recipes;

import sblectric.lightningcraft.blocks.BlockSlabLC;
import sblectric.lightningcraft.init.LCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

/** The LightningCraft fuel handler */
public class LCFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		Item item = fuel.getItem();
		Block block = Block.getBlockFromItem(item);
		int meta = fuel.getItemDamage();
		
		// now the cases
		if(block == LCBlocks.slabBlock && meta == BlockSlabLC.UNDER_PLANK) {
			return 150; // slab result
		}
		
		// default: not a fuel
		return 0;
	}

}
