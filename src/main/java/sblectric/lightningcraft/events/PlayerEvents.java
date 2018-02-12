package sblectric.lightningcraft.events;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.IKineticGear;
import sblectric.lightningcraft.api.IMysticGear;
import sblectric.lightningcraft.api.IPotionEffectProvider;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.blocks.PortalUnderworld;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.init.LCDimensions;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.init.LCPotions;
import sblectric.lightningcraft.items.ItemBattery;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.util.InventoryLE;
import sblectric.lightningcraft.util.SkyUtils;

/** Player events */
public class PlayerEvents {

	public static final int repairTime = 5 * 20; // every 5 seconds * 20 tps
	static final float walkBoostSky = 0.35123F;
	static final float walkBoostMystic = 0.55123F;
	static final float stepBoost = 1.01789F;
	static final UUID speedBootsID = UUID.fromString("caac9ed0-a4a7-42c1-af76-0482f06f34d8");

	/** Handle all player updates */
	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent e) {
		if(e.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)e.getEntity();
			
			// serverside things
			if (!player.world.isRemote) {
				EntityPlayerMP mp = (EntityPlayerMP)player;
				
				// repair tools and armor
				handleRepair(mp);		
				
				// handle potion things
				handleServerPotionEffects(mp);
				
				// handle portal things
				handleTeleportation(mp);
			}
			
			// common things
			onStepBootsWear(player);
		}
	}
	
	/** Handle rendering updates */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerRenderTick(RenderTickEvent e) {
		EntityPlayer player = Minecraft.getMinecraft().player; if(player == null) return;
		
		// handle potion rendering
		handleClientPotionEffects(player);
	}
	
	/** Handle clientside potion events */
	private void handleClientPotionEffects(EntityPlayer player) {
		// night vision doesn't work in the Underworld
		if(player.dimension == LCDimensions.underworldID) {
			if(player.isPotionActive(MobEffects.NIGHT_VISION)) {
				player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
			}
		}
	}
	
	/** Handle serverside potion events */
	private void handleServerPotionEffects(EntityPlayer player) {
		
		// get player inventory and check for IPotionEffectProviders
		for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack s = player.inventory.getStackInSlot(i);
			if(!s.isEmpty() && s.getItem() instanceof IPotionEffectProvider) {
				IPotionEffectProvider item = (IPotionEffectProvider)s.getItem();
				if(item.canApplyEffect(s, player, i)) {
					for(PotionEffect e : item.getEffects(s, player, i)) {
						player.addPotionEffect(e);
					}
				}
			}
		}
		
		// night vision doesn't work in the Underworld
		if(player.dimension == LCDimensions.underworldID) {
			if(player.isPotionActive(MobEffects.NIGHT_VISION)) {
				player.removePotionEffect(MobEffects.NIGHT_VISION);
			}
		}
		
		// remove active potions with duration 0
		if (player.isPotionActive(LCPotions.demonFriend) && player.getActivePotionEffect(LCPotions.demonFriend).getDuration() == 0) {
			player.removePotionEffect(LCPotions.demonFriend);
		}
	}
	
	/** Handle portal stuff */
	private void handleTeleportation(EntityPlayerMP mp) {
		if(PortalUnderworld.portalStatus.checkCooldown(mp) && PortalUnderworld.portalStatus.getPortal(mp)) {
			PortalUnderworld.doTeleport(mp);
			PortalUnderworld.portalStatus.resetCooldown(mp);
		} else {
			PortalUnderworld.portalStatus.setPortal(mp, false);
		}
	}
	
	/** Handle boots that give a step boost */
	private void onStepBootsWear(EntityPlayer player) {
		boolean isMystic = false;
		IAttributeInstance attributeinstance = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		AttributeModifier mod = new AttributeModifier(speedBootsID, "skyBootsSpeedModifier", walkBoostSky, 2).setSaved(false);
		AttributeModifier mystic = new AttributeModifier(speedBootsID, "mysticBootsSpeedModifier", walkBoostMystic, 2).setSaved(false);
		if(player.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null) {
			if(player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == LCItems.skyBoots) {
				// default state
			} else if(player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == LCItems.mysticBoots) {
				isMystic = true;
				mod = mystic; // extended state
			} else {
				removeAttributes(player, attributeinstance, mod);
				mod = null;
			}
			if(mod != null) {
				if(attributeinstance.getModifier(speedBootsID) == null) attributeinstance.applyModifier(mod);
				if(!player.isSneaking()) {
					player.stepHeight = stepBoost * (isMystic ? 2F : 1F);
				} else {
					player.stepHeight = 0.5f;
				}
			}
		} else {
			removeAttributes(player, attributeinstance, mod);
		}
	}
	
	/** Remove step and speed attributes when needed */
	private void removeAttributes(EntityPlayer player, IAttributeInstance instance, AttributeModifier mod) {
		if(instance.getModifier(speedBootsID) != null) instance.removeModifier(mod);
		if(player.stepHeight == stepBoost || player.stepHeight == stepBoost * 2F) player.stepHeight = 0.5F;
	}

	/** Check items that should be repaired each tick */
	private void handleRepair(EntityPlayer player) {
		JointList<ItemStack> inv = new JointList<ItemStack>().join(player.inventory.mainInventory).join(player.inventory.armorInventory);
		for(ItemStack stack : inv) {
			if(stack != null) {
				if(LCConfig.autoRepair && stack.getItem() instanceof IMysticGear) {
					doAutoRepair(player, stack, 1);
				} else if(stack.getItem() instanceof IKineticGear) {
					doKineticRepair(player, stack, InventoryLE.repairSpeedModifier);
				}
			}
		}
	}

	/** Repairs the stack each tick as necessary */
	private boolean doAutoRepair(EntityPlayer player, ItemStack stack, double speed) {
		if(SkyUtils.canWriteItemNBT(stack, player)) {
			int damage = stack.getItemDamage();

			if(damage > 0) {
				if(player.ticksExisted % MathHelper.floor(repairTime / speed) == 0) {
					stack.setItemDamage(damage - 1);
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	/** Repairs the stack with an LE cost */
	private boolean doKineticRepair(EntityPlayer player, ItemStack stack, double speed) {
		ItemStack battery = InventoryLE.getLESource(player, InventoryLE.energyUsage);
		if(battery != null && doAutoRepair(player, stack, speed)) {
			ItemBattery.addStoredPower(battery, -InventoryLE.energyUsage);
			return true;
		}
		return false;
	}
	
	/** Handle tooltips */
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onTooltipShow(ItemTooltipEvent e) {
		if(e.getItemStack() != null) {
			showAutoRepairTooltip(e.getItemStack(), e.getToolTip());
		}
	}
	
	/** Show auto-repair tooltips */
	private void showAutoRepairTooltip(ItemStack stack, List list) {
		if(stack.getItem() instanceof IMysticGear) {
			list.add(1, LCText.getAutoRepairLore());
		}
	}

}
