package com.lightningcraft.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.registry.IRegistryItem;
import com.lightningcraft.util.ArmorHelper;
import com.lightningcraft.util.LCMisc;
import com.lightningcraft.util.StackHelper;

/** LightningCraft armor (basic) */
public abstract class ItemArmorLC extends ItemArmor implements IRegistryItem {
	
	private EnumRarity rarity;
	private ArmorMaterial mat;

	public ItemArmorLC(ArmorMaterial mat, EntityEquipmentSlot armorType, EnumRarity rarity) {
		super(mat, mat.ordinal(), armorType);
		this.mat = mat;
		this.rarity = rarity;
	}
	
	public ItemArmorLC(ArmorMaterial mat, EntityEquipmentSlot armorType) {
		this(mat, armorType, DYNAMIC);
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    	return StackHelper.areItemStacksEqualForCrafting(repair, ArmorHelper.getRepairStack(mat));
    }
	
	@Override
	public void setRarity() {
		if(rarity == DYNAMIC) rarity = LCMisc.getRarityFromStack(ArmorHelper.getRepairStack(mat));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack){
		return rarity;
	}

}
