package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.items.base.ItemSpecialSword;
import sblectric.lightningcraft.ref.LCText;

/** The zombie sword class */
public class ItemZombieSword extends ItemSpecialSword {

	public ItemZombieSword(ToolMaterial mat) {
		super(mat, -0.2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		list.add(LCText.getZombieSwordLore());
	}

}
