package sblectric.lightningcraft.util;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import sblectric.lightningcraft.api.IMysticGear;
import sblectric.lightningcraft.init.LCDimensions;
import sblectric.lightningcraft.init.LCPotions;

/** Helper methods related to Skyfather stuff */
public class SkyUtils {
	
	public static final int skyRepairTime = 5 * 20; // every 5 seconds * 20 tps
	static final Random random = new Random();
	
	// ---------- //
	// ITEM UTILS //
	// ---------- //
	
	/** Item is not being swung */
	public static boolean canWriteItemNBT(@Nonnull ItemStack stack, EntityPlayer player) {
		return !(player.isSwingInProgress && (player.getHeldItem(EnumHand.MAIN_HAND) == stack || player.getHeldItem(EnumHand.OFF_HAND) == stack));
	}
	
	/** Damage an itemstack */
	public static void damageStack(int damage, @Nonnull ItemStack stack, EntityLivingBase user, EntityEquipmentSlot slot, boolean zeroMax) {
		int cdamage = stack.getItemDamage();
		stack.damageItem(damage, user);
		if(!stack.isEmpty()) {
			boolean comp2 = zeroMax ? cdamage + damage >= stack.getMaxDamage() : cdamage + damage > stack.getMaxDamage();
			if(comp2) user.setItemStackToSlot(slot, ItemStack.EMPTY);
		}
	}
	
	/** Damage an itemstack */
	public static void damageStack(int damage, @Nonnull ItemStack stack, EntityLivingBase user, EntityEquipmentSlot slot) {
		damageStack(damage, stack, user, slot, false);
	}
	
	public static final double SWORD_ATTACK_SPEED = -2.4;
	
	/** Adjusts the speed of the item upwards */
	public static void setToolSpeedModifier(Item item, Multimap<String, AttributeModifier> multimap, UUID speedmod, double currentSpeed) {
		multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName()); // clear the attack speed
		multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), 
				new AttributeModifier(speedmod, "Tool modifier", currentSpeed + (item instanceof IMysticGear ? 0.6 : 0.3), 0));
	}
	
	// ------------ //
	// PLAYER UTILS //
	// ------------ //
	
	/** is the player insolent? */
	public static boolean isPlayerInsolent(EntityPlayerMP player) {
		return (/*player.getStatFile().hasAchievementUnlocked(LCAchievements.infuseSkyfather) ||*/ player.dimension == LCDimensions.underworldID)
				&& player.getActivePotionEffect(LCPotions.demonFriend) == null;
	}

}
