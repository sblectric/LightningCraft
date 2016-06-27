package sblectric.lightningcraft.events;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import sblectric.lightningcraft.achievements.LCAchievements;
import sblectric.lightningcraft.enchantments.LCEnchantments;
import sblectric.lightningcraft.entities.EntityLCZombie;
import sblectric.lightningcraft.items.ItemBlazeSword;
import sblectric.lightningcraft.items.ItemChargedSword;
import sblectric.lightningcraft.items.ItemEnderSword;
import sblectric.lightningcraft.items.ItemFeatherSword;
import sblectric.lightningcraft.items.ItemHammer;
import sblectric.lightningcraft.items.ItemIceSword;
import sblectric.lightningcraft.items.ItemMysticArmor;
import sblectric.lightningcraft.items.ItemSoulSword;
import sblectric.lightningcraft.items.ItemSpecialSword;
import sblectric.lightningcraft.items.ItemZombieSword;
import sblectric.lightningcraft.util.Effect;
import sblectric.lightningcraft.util.IntList;
import sblectric.lightningcraft.util.WorldUtils;

/** Combat events */
public class CombatEvents {

	private static final Random random = new Random();
	
	/** Handle living hurt events */
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		if(event.getEntity() instanceof EntityLivingBase && !event.getEntity().worldObj.isRemote) {
			handleLightningSwordStrike(event);
			handleSpecialStrike(event);
			handleElecAuraHurt(event);
		}
	}

	/** Check for Hand of Thor strikes */
	public void handleLightningSwordStrike(LivingHurtEvent event) {
		// make sure this hit is a valid weapon strike
		EntityLivingBase target = (EntityLivingBase)event.getEntity();
		if(target == null || target.isEntityInvulnerable(event.getSource())) return;
		EntityLivingBase user = null;
		if(event.getSource().getEntity() instanceof EntityLivingBase) user = (EntityLivingBase)event.getSource().getEntity();
		if(user == null) return;
		ItemStack weapon = user.getHeldItem(EnumHand.MAIN_HAND); if(weapon == null) return;

		// Hand of Thor enchantment
		int j = EnchantmentHelper.getEnchantmentLevel(LCEnchantments.handOfThor, weapon);
		int m = LCEnchantments.handOfThor.getMaxLevel();
		Item w = weapon.getItem();
		
		// divine overkill achievement
		int s = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, weapon);
		if(!user.worldObj.isRemote && user instanceof EntityPlayer && j == 3 && s == 5) {
			((EntityPlayer)user).addStat(LCAchievements.mysticHammer, 1);
		}

		// different chances based on item type
		// hammers: 50% chance, up to 100% with top Hand of Thor
		// charged swords: 15% chance, up to 45%
		// regular weaponry: 0% chance, up to 10%
		double init = w instanceof ItemChargedSword ? (w instanceof ItemHammer ? 0.5 : 0.15) : 0;
		double var = w instanceof ItemChargedSword ? (w instanceof ItemHammer ? 0.5 : 0.3) : 0.1;
		if(!user.isSneaking() && random.nextDouble() < (init + var * (double)j / (double)m)) {
			Effect.lightning(target, false);
			weapon.damageItem(2, user);
		}
	}

	/** Strike back with Electrostatic Aura */
	public void handleElecAuraHurt(LivingHurtEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if(event.getSource().getEntity() instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase) event.getSource().getEntity();

				// Electrostatic Aura handling
				if(!attacker.isEntityInvulnerable(event.getSource())) { 
					IntList ench = new IntList();
					ItemStack helm = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
					ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
					ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
					ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
					int lv = 0, a = 0;
					int damageHelm = 0, damageChest = 0, damageLegs = 0, damageBoots = 0;
					if(helm != null) {
						damageHelm = EnchantmentHelper.getEnchantmentLevel(LCEnchantments.elecAura, helm);
						ench.add(damageHelm);
						if(helm.getItem() instanceof ItemMysticArmor) ++a;
					}
					if(chest != null) {
						damageChest = EnchantmentHelper.getEnchantmentLevel(LCEnchantments.elecAura, chest);
						ench.add(damageChest);
						if(chest.getItem() instanceof ItemMysticArmor) ++a;
					}
					if(legs != null) {
						damageLegs = EnchantmentHelper.getEnchantmentLevel(LCEnchantments.elecAura, legs);
						ench.add(damageLegs);
						if(legs.getItem() instanceof ItemMysticArmor) ++a;
					}
					if(boots != null) {
						damageBoots = EnchantmentHelper.getEnchantmentLevel(LCEnchantments.elecAura, boots);
						ench.add(damageBoots);
						if(boots.getItem() instanceof ItemMysticArmor) ++a;
					}

					// get the max enchantment
					for(int i : ench) {
						lv += i; // the effect stacks
					}

					// now apply a thorns-like lightning defense
					// the effect stacks (maximum of 24% chance with all armor enchanted)
					if(lv > 0) {
						// EA I = 2% chance per item
						// EA II = 4% chance per item
						// EA III = 6% chance per item
						if(random.nextDouble() <= lv * 2/100D) {
							Effect.lightning(attacker, false);
							if(damageHelm > 0) helm.damageItem(4 * damageHelm, player);
							if(damageChest > 0) chest.damageItem(4 * damageChest, player);
							if(damageLegs > 0) legs.damageItem(4 * damageLegs, player);
							if(damageBoots > 0) boots.damageItem(4 * damageBoots, player);
							
							// achievement get (full Mystic armor, full elecAura)
							if(a >= 4 && lv >= 12) {
								player.addStat(LCAchievements.mysticArmor, 1);
							}
						}
					}
				}				
			}
		}
	}

	/** sword effects here */
	public void handleSpecialStrike(LivingHurtEvent event) {
		// definitions
		EntityLivingBase target = (EntityLivingBase)event.getEntity();
		World world = target.worldObj;
		Entity src = event.getSource().getEntity();
		float nextHealth = target.getHealth() - event.getAmount();

		// ignore invalid damage
		if(event.getAmount() <= 0 || target.isEntityInvulnerable(event.getSource())) return;

		if(src != null && src instanceof EntityLivingBase) { // serverside
			EntityLivingBase user = (EntityLivingBase) src;
			ItemStack sword = user.getHeldItem(EnumHand.MAIN_HAND);

			// check to see if the sword is special
			if(sword != null && sword.getItem() instanceof ItemSpecialSword) {
				Item w = sword.getItem();
				String effect = null;

				// Soul Sword
				if(w instanceof ItemSoulSword) {
					effect = "soulSteal";

					// steal health and add some to your own (armor piercing)
					float health = target.getHealth();
					float steal = health * 0.15F;
					LivingSoulStealEvent soulEvent = new LivingSoulStealEvent(target, steal);
					if(!MinecraftForge.EVENT_BUS.post(soulEvent)) { // only proceed if the event is not canceled
						steal = soulEvent.getAmount(); // modify stealing as needed here
						nextHealth -= steal;
						target.setHealth(health - steal); // steal 15% of health
						user.heal(steal * 0.2515F * (random.nextFloat() + 1F)); // chance to add up to 8% to your own
					}

				// Zombie Sword (don't attack your own zombie helpers!)
				} else if(w instanceof ItemZombieSword && !(target instanceof EntityLCZombie && ((EntityLCZombie)target).getOwner() == user)) {
					effect = "zombieSummon";
					
					// summon a zombie to attack the entity you attacked
					EntityZombie zombie = new EntityLCZombie(world, target, user);
					zombie.setPosition(target.posX + random.nextFloat() - 0.5F, target.posY, target.posZ + random.nextFloat() - 0.5F);
					world.spawnEntityInWorld(zombie);
					zombie.playLivingSound();

				// Winged Sword
				} else if(w instanceof ItemFeatherSword) {
					effect = "featherAttack";

					// send them flying
					float knockbackModifier = 10F;
					target.addVelocity(-MathHelper.sin(user.rotationYaw * 
							(float)Math.PI / 180.0F) * knockbackModifier  * 0.5F, 
							0.1D, MathHelper.cos(user.rotationYaw * 
									(float)Math.PI / 180.0F) * knockbackModifier  * 0.5F);
					target.motionX *= 0.6D;
					target.motionZ *= 0.6D;

				// Ender Sword
				} else if(w instanceof ItemEnderSword) {
					effect = "enderAttack";

					// teleport
					float distance = 2.5F + random.nextFloat();
					double posX, posZ;
					Integer posY;
					posX = target.posX + (MathHelper.sin(target.rotationYawHead * (float)Math.PI / 180.0F) * distance);
					posZ = target.posZ - (MathHelper.cos(target.rotationYawHead * (float)Math.PI / 180.0F) * distance);
					posY = WorldUtils.getOpenSurface(world, (int)posX, (int)posZ, (int)target.posY, 6);

					// make sure the teleport is successful
					if(posY != null) {

						// display the effect and sound
						Effect.ender(world, user.posX, user.posY, user.posZ, posX, posY, posZ, true);

						// update player
						user.rotationYaw = target.rotationYawHead;
						user.rotationYawHead = target.rotationYawHead;
						user.setPositionAndUpdate(posX, posY, posZ);
					}
					
				// Blaze Sword
				} else if(w instanceof ItemBlazeSword) {
					effect = "blazeAttack";
					
					// light the target on fire for an hour (!)
					target.setFire(3600);
				
				// Ice Sword
				} else if(w instanceof ItemIceSword) {
					effect = "iceAttack";
				
					// give the target extreme slowness and no jumping ability for 3 seconds
					target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 5));
					target.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 60, 128));
				}
				
				// show the effect
				if(effect != null) Effect.specialSword(effect, world, target.posX, target.posY, target.posZ);
				
				// achievement
				if(user instanceof EntityPlayer && nextHealth <= 0) {
					((EntityPlayer)user).addStat(LCAchievements.specialSwordKill, 1);
				}
			}
		}
	}

}
