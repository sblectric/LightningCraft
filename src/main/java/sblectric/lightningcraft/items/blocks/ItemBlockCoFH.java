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
import sblectric.lightningcraft.blocks.base.BlockEnergy;

/** An item of the BlockCoFH type */
public class ItemBlockCoFH extends ItemBlockRarity {
	
	private BlockEnergy block;

	public ItemBlockCoFH(Block block) {
		super(block, EnumRarity.UNCOMMON);
		this.block = (BlockEnergy)block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		list.add("Max Transfer: " + block.getMaxRFPerTick() + " RF / tick");
		list.add("RF/LE: " + block.getRFLERatio() + " RF / 1 LE");
	}

}
