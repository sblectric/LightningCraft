package sblectric.lightningcraft.items.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
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
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
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
