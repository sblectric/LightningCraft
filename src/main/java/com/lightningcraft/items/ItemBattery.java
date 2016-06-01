package com.lightningcraft.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.lightningcraft.items.ifaces.IInventoryLEUser;
import com.lightningcraft.ref.LCText;

/** A battery for holding inventory LE */
public class ItemBattery extends ItemMeta {

	// number of icons per grade
	public static final int nIcons = 1;
	// # of grades of battery
	public static final int nGrades = 3;
	// combined amount
	public static final int nSubtypes = nIcons * nGrades;

	public ItemBattery() {
		super(nSubtypes, IInventoryLEUser.ILERarity);
		this.setMaxStackSize(1); // only single battery per stack
	}

	/** Like a meta-item, but subtypes are hidden except for empty and fully charged
	 *  and metadata is not used */
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < nGrades; i++) {
			// item stacks
			ItemStack empty = new ItemStack(item, 1, i);
			ItemStack charged = new ItemStack(item, 1, i);

			// set the charged tag
			NBTTagCompound tag = new NBTTagCompound();
			tag.setDouble("StoredEnergy", getMaxPower(charged));
			charged.setTagCompound(tag);

			// add them to the list
			list.add(empty);
			list.add(charged);
		}

	}
	
	/** fix strange duplication glitches */
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
		if(stack.stackSize < 1) stack = null;
	}

	/** Get the stored power in the item */
	public static double getStoredPower(ItemStack stack) {
		return stack == null || !stack.hasTagCompound() ? 0 : stack.getTagCompound().getDouble("StoredEnergy");
	}

	/** Set the stored power in the item */
	public static boolean setStoredPower(ItemStack stack, double power) {
		if(stack != null) {
			if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setDouble("StoredEnergy", power);
			return true;
		} else {
			return false;
		}
	}

	/** Modify the stored power in the item */
	public static boolean addStoredPower(ItemStack stack, double add) {
		return setStoredPower(stack, getStoredPower(stack) + add);
	}

	/** Max power the batteries hold */
	public static double getMaxPower(ItemStack stack) {
		return 50D * (stack.getItemDamage() + 1D);
	}

	/** Show current charge */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		double power = getStoredPower(stack);
		double maxpower = getMaxPower(stack);
		int charge = (int) (100D * (power / maxpower));
		list.add("Stored Energy: " + LCText.df.format(power) + "/" + LCText.df.format(maxpower) + " LE (" + charge + "%)");
	}

	/** Get the right model for the charge level */
//	@SideOnly(Side.CLIENT)
//	@Override
//	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
//		int displayPower = (int)Math.round(getStoredPower(stack) / (getMaxPower(stack) / (nSubtypes / nGrades - 1)));
//		displayPower = Math.min(Math.min(displayPower, nSubtypes / nGrades - 1) + stack.getItemDamage() * nIcons, nIcons * nGrades - 1);
//		return new ModelResourceLocation(RefStrings.MODID + ":" + this.getShorthandName() + "_" + displayPower, "inventory");
//	}

}
