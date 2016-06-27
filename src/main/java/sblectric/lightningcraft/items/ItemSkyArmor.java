package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import sblectric.lightningcraft.ref.LCText;

/** Skyfather armor */
public class ItemSkyArmor extends ItemArmorLC {

	public ItemSkyArmor(ArmorMaterial mat, EntityEquipmentSlot armorType) {
		super(mat, armorType);
	}
	
	// item lore
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if(stack.getItem() == LCItems.skyHelm) {
			list.add(LCText.getSkyHelmLore());
		} else if(stack.getItem() == LCItems.skyChest) {
			list.add(LCText.getSkyChestLore());
		} else if(stack.getItem() == LCItems.skyLegs) {
			list.add(LCText.getSkyLegsLore());
		} else if(stack.getItem() == LCItems.skyBoots) {
			list.add(LCText.getSkyBootsLore());
		}
	}
	
	// do cool things with this armor
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		
		if(!world.isRemote) {
			if(itemStack.getItem() == LCItems.skyHelm) {
				player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 250, -1, true, false));
			} else if(itemStack.getItem() == LCItems.skyChest) {
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 25, 0, true, false));
			} else if(itemStack.getItem() == LCItems.skyLegs) {
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 25, -1, true, false));
			} else if(itemStack.getItem() == LCItems.skyBoots) {
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 25, 2, true, false));
				// movement speed handled in event handler
			}
		}
	}

}
