package com.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.achievements.LCAchievements;
import com.lightningcraft.ref.LCText;
import com.lightningcraft.ref.Metal.Rod;
import com.lightningcraft.util.Effect;
import com.lightningcraft.util.SkyUtils;
import com.lightningcraft.util.StackHelper;

/** The golf club for summoning lightning. */
public class ItemGolfClub extends ItemLC {

	public ItemGolfClub(){
		this.setMaxDamage(6); // pass damage through
		this.setMaxStackSize(1); // it acts as a tool!
		this.setHasSubtypes(false); // only damage
		this.canRepair = true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(LCText.getSummonerLore());
	}

	/** Summon lightning when swung */
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if(!world.isRemote && hand == EnumHand.MAIN_HAND) {
			SkyUtils.damageStack(2, stack, player, EntityEquipmentSlot.MAINHAND, true);
			Effect.lightning(player, false);

			// golf club achievement
			player.addStat(LCAchievements.swingGolfClub, 1);
		}
		return super.onItemRightClick(stack, world, player, hand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	// repair the item with given item
	@Override
	public boolean getIsRepairable(ItemStack a, ItemStack b) {
		ItemStack repairWith = new ItemStack(LCItems.rod, 0, Rod.IRON);
		if(StackHelper.areItemStacksEqualForCrafting(repairWith, b)){
			return true;
		} else {
			return false;
		}
	}

}
