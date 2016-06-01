package com.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.lightningcraft.items.ifaces.IAutoRepair;
import com.lightningcraft.ref.LCText;

/** Mystic armor */
public class ItemMysticArmor extends ItemArmorLC implements IAutoRepair {

	public ItemMysticArmor(ArmorMaterial mat, EntityEquipmentSlot armorType) {
		super(mat, armorType);
	}
	
	// item lore
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if(stack.getItem() == LCItems.mysticHelm) {
			list.add(LCText.getSkyHelmLore());
			list.add(LCText.getMysticHelmLore());
		} else if(stack.getItem() == LCItems.mysticChest) {
			list.add(LCText.getMysticChestLore());
		} else if(stack.getItem() == LCItems.mysticLegs) {
			list.add(LCText.getSkyLegsLore());
			list.add(LCText.getMysticLegsLore());
		} else if(stack.getItem() == LCItems.mysticBoots) {
			list.add(LCText.getMysticBootsLore());
		}
	}
	
	// do cool things with this armor
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		
		if(!world.isRemote) {
			if(itemStack.getItem() == LCItems.mysticHelm) {
				player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 250, -1, true, false));
				player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 25, 0, true, false));
			} else if(itemStack.getItem() == LCItems.mysticChest) {
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 25, 1, true, false));
			} else if(itemStack.getItem() == LCItems.mysticLegs) {
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 25, -1, true, false));
				player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 25, 0, true, false));
			} else if(itemStack.getItem() == LCItems.mysticBoots) {
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 25, 3, true, false));
				// movement speed handled in event handler
			}
		}
	}

}
