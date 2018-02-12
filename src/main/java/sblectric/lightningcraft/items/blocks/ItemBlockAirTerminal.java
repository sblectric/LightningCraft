package sblectric.lightningcraft.items.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.blocks.BlockAirTerminal;
import sblectric.lightningcraft.ref.Metal.Rod;

/** The item for the air terminal block */
public class ItemBlockAirTerminal extends ItemBlockMeta {
	
	private BlockAirTerminal block;

	/** The item for the air terminal block */
	public ItemBlockAirTerminal(Block block) {
		super(block);
		this.block = (BlockAirTerminal)block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		list.add("Efficiency: " + (int)(block.getEfficiency(block.getStateFromMeta(stack.getItemDamage())) * 100D) + "%");
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		switch(stack.getItemDamage()) {
		case Rod.ELEC:
			return EnumRarity.UNCOMMON;
		case Rod.SKY:
			return EnumRarity.RARE;
		case Rod.MYSTIC:
			return EnumRarity.EPIC;
		default:
			return super.getRarity(stack);
		}
	}

}
