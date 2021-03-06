package sblectric.lightningcraft.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import sblectric.lightningcraft.integration.energy.EnergyApiHelper;
import sblectric.lightningcraft.items.blocks.ItemBlockCoFH;

/** A block that uses energy in some way */
public abstract class BlockEnergy extends BlockContainerLC {

	public BlockEnergy(Block parent, float hardness, float resistance) {
		super(parent, hardness, resistance);
	}
	
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList list) {
		if(EnergyApiHelper.rfOrTeslaLoaded) { // only expose the block when the RF API exists
			list.add(new ItemStack(this));
		}
	}
	
	/** Get the amount of RF/tick the block has */
	public abstract int getMaxRFPerTick();
	
	/** Get the conversion ratio (this many RF yields 1 LE) */
	public abstract int getRFLERatio();
	
	@Override
	public Class getItemClass() {
		return ItemBlockCoFH.class;
	}

}
