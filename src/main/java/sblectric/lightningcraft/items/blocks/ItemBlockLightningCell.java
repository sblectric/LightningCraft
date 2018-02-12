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
import sblectric.lightningcraft.api.IInventoryLEUser;
import sblectric.lightningcraft.blocks.BlockLightningCell;

/** The Lightning Cell block item */
public class ItemBlockLightningCell extends ItemBlockMetal {

	private BlockLightningCell block;
	
	public ItemBlockLightningCell(Block block) {
		super(block);
		this.block = (BlockLightningCell)block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		list.add("Capacity: " + block.getMaxPower(block.getStateFromMeta(stack.getItemDamage())) + " LE");
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if(stack.getItemDamage() >= 3) {
			return IInventoryLEUser.ILERarity;
		} else {
			return super.getRarity(stack);
		}
	}

}
