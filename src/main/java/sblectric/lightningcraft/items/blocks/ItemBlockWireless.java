package sblectric.lightningcraft.items.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.blocks.BlockWireless;
import sblectric.lightningcraft.blocks.BlockWireless.WirelessSpecs;

/** The wireless block item */
public class ItemBlockWireless extends ItemBlockMeta {
	
	private BlockWireless block;

	public ItemBlockWireless(Block block) {
		super(block);
		this.block = (BlockWireless)block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		int meta = stack.getItemDamage();
		WirelessSpecs params = new WirelessSpecs();
		if(block.getTileEntityPerformance(meta, params)) { // transmitter
			list.add("Quality: " + (int)(params.quality * 100D) + "%");
			list.add("Range: " + (int)(params.range) + "m");
		} else { // receiver
			list.add("Rolloff: " + (params.rolloff * 100D) + "% / m²");
		}
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		switch(stack.getItemDamage() % BlockWireless.nTransmitters) {
		case 0:
			return EnumRarity.UNCOMMON;
		case 1:
			return EnumRarity.RARE;
		case 2:
			return EnumRarity.EPIC;
		default:
			return super.getRarity(stack);
		}
	}

}
