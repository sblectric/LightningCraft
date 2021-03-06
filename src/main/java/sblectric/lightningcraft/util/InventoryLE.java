package sblectric.lightningcraft.util;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.items.ItemBattery;
import sblectric.lightningcraft.ref.LCText;

public class InventoryLE {
	
	public static final double repairSpeedModifier = 2.25;
	public static final double breakUsage = 1/5D;
	public static final double energyUsage = 1/50D;
	public static final double strikeUsage = 1/5D;
	
	public static class LECharge {
		private double power;
		
		public LECharge(double charge) {
			power = charge;
		}
		
		public LECharge() {
			power = 0;
		}
		
		public double getCharge() {
			return power;
		}
		
		public void setCharge(double charge) {
			power = charge;
		}
		
	}

	/** Add the appropriate LP lore */
	@SideOnly(Side.CLIENT)
	public static boolean addInformation(@Nonnull ItemStack stack, World world, List list, ITooltipFlag flag, LECharge charge) {
		list.add(LCText.getInventoryLEUserLore());
		
		EntityPlayer player = Minecraft.getMinecraft().player;
		boolean charged = hasLESource(player);
		charge.setCharge(getAvailablePower(player));
		
		// LP lore
		String lpLore = LCText.secSign + (charged ? (charge.getCharge() > 0 ? "b" + LCText.df.format(charge.getCharge()) : "cNo") + 
				" LE available" : "cNo LE Source Found");
		list.add(lpLore);
		
		return charged;
	}
	
	/** Get the available power in the specified player's inventory */
	public static double getAvailablePower(EntityPlayer user) {
		if(hasLESource(user)) {
			int invPos = 0;
			double power = 0;
			IntList avoid = new IntList();
			while((invPos = LCMisc.posInInventory(user, LCItems.battery, avoid)) >= 0) {
				// check this inventory position
				if(invPos >= 0) {
					ItemStack battery = user.inventory.mainInventory.get(invPos);
					power = Math.max(power, ItemBattery.getStoredPower(battery));
					avoid.add(invPos);
				}
			}
			return power;
		}
		return 0;
	}

	/** Is there an LE source? */
	public static boolean hasLESource(EntityPlayer user) {
		return user != null && LCMisc.posInInventory(user, LCItems.battery) >= 0;
	}

	/** Find a battery to use */
	public static @Nonnull ItemStack getLESource(EntityPlayer user, double lpNeeded) {
		if(hasLESource(user)) {
			int invPos = 0;
			IntList avoid = new IntList();
			while((invPos = LCMisc.posInInventory(user, LCItems.battery, avoid)) >= 0) {
				// check this inventory position
				if(invPos >= 0) {
					ItemStack battery = user.inventory.mainInventory.get(invPos);
					
					if(ItemBattery.getStoredPower(battery) >= lpNeeded) {
						return battery;
					} else {
						avoid.add(invPos);
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	/** Hit an entity with this weapon */
	public static void hitEntity(@Nonnull ItemStack stack, EntityLivingBase entity, EntityLivingBase user, double normalDamage, boolean maxDamage) {
		if(user instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)user;
			DamageSource source = DamageSource.causePlayerDamage(player);
			float damage = 0;
			double charge = getAvailablePower(player);
			boolean sneaking = player.isSneaking() && maxDamage;
			
			// expend up to normalDamage / leStrikeUsage LE per attack
			if(!sneaking) {
				damage = (float)Math.min(charge, normalDamage);
			// expend 50 LE per attack
			} else {
				damage = (float)Math.min(charge, 50);
			}
			
			// consume the power (2LE per normal swing)
			if(damage > 0) {
				double cost = damage * strikeUsage;
				ItemStack battery = getLESource(player, cost);
				ItemBattery.addStoredPower(battery, -cost);
			}
			
			// finally, direct your built up hatred toward the enemy
			entity.attackEntityFrom(source, damage);
		}
	}
	
	/** Variable tool efficiency based on current charge,
	 *  Peak powered efficiency is between skyfather and mystic efficiency */
	public static float getEfficiency(@Nonnull ItemStack tool, float baseEff) {
		float newEff;
		double charge;
		if(tool.hasTagCompound()) {
			charge = tool.getTagCompound().getDouble("availablePower");
		} else {
			charge = 0;
		}
		double multiplier = (LCItems.mysticMat.getEfficiency() - 2) / baseEff;
		double take = Math.min(charge / breakUsage, multiplier);
		
			if(charge > 0) {
				newEff = baseEff * (float)take;
			} else {
				newEff = baseEff;
			}
		
		return newEff;
	}
	
	/** Updates stored power that the tool can use */
	public static void updateToolPower(@Nonnull ItemStack tool, EntityPlayer player) {
		if(!tool.hasTagCompound()) tool.setTagCompound(new NBTTagCompound());
		if(SkyUtils.canWriteItemNBT(tool, player) && tool.getTagCompound().getDouble("availablePower") != getAvailablePower(player)) {
			tool.getTagCompound().setDouble("availablePower", getAvailablePower(player));
		}
	}
	
	/** When a block is broken, take power away */
	public static void onBlockBreak(@Nonnull ItemStack tool, EntityPlayer player, float baseEff) {
		double charge = getAvailablePower(player);
		double multiplier = (LCItems.mysticMat.getEfficiency() - 2) / baseEff;
		double take = Math.min(charge, multiplier) * energyUsage;
		ItemStack battery = getLESource(player, take);
		ItemBattery.addStoredPower(battery, -take);
		tool.getTagCompound().setDouble("availablePower", getAvailablePower(player)); // update the power!
	}

}
