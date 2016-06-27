package sblectric.lightningcraft.items.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import sblectric.lightningcraft.blocks.BlockCoFH;

/** An item of the BlockCoFH type */
public class ItemBlockCoFH extends ItemBlockRarity {
	
	private BlockCoFH block;

	public ItemBlockCoFH(Block block) {
		super(block, EnumRarity.UNCOMMON);
		this.block = (BlockCoFH)block;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Max Transfer: " + block.getMaxRFPerTick() + " RF / tick");
		list.add("RF/LE: " + block.getRFLERatio() + " RF / 1 LE");
	}

}
