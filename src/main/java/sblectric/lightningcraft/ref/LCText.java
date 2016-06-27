package sblectric.lightningcraft.ref;

import java.text.DecimalFormat;

import net.minecraft.util.text.translation.I18n;

public class LCText {
	
	public static final String secSign = "\u00a7";
	public static final DecimalFormat df = new DecimalFormat("0.0");
	public static final DecimalFormat af = new DecimalFormat("#.#");
	
	/** Set up formatting */
	public static void setupFormatting() {
		af.setDecimalSeparatorAlwaysShown(false);
	}
	
	// ----------------------------------------------------------- //
	// --- MISC LORE --------------------------------------------- //
	// ----------------------------------------------------------- //	
	
	/** Requires LE */
	public static String getInventoryLEUserLore() {
		return secSign + "e" + I18n.translateToLocal("lore.inventoryleuser");
	}
	
	/** Invincibility */
	public static String getInvincibilityLore() {
		return secSign + "f" + I18n.translateToLocal("lore.invincibility");
	}	
	
	// ----------------------------------------------------------- //
	// --- ARMOR LORE -------------------------------------------- //
	// ----------------------------------------------------------- //
	
	/** Auto-Repair I */
	public static String getAutoRepairLore() {
		return secSign + "a" + I18n.translateToLocal("lore.autorepair") + " I";
	}
	
	/** Auto-Repair II */
	public static String getAutoRepair2Lore() {
		return secSign + "a" + I18n.translateToLocal("lore.autorepair") + " II";
	}
	
	/** Night Vision */
	public static String getSkyHelmLore() {
		return secSign + "f" + I18n.translateToLocal("lore.skyhelm");
	}
	
	/** Water Breathing */
	public static String getMysticHelmLore() {
		return secSign + "b" + I18n.translateToLocal("lore.mystichelm");
	}
	
	/** Resistance I */
	public static String getSkyChestLore() {
		return secSign + "4" + I18n.translateToLocal("lore.skychest");
	}
	
	/** Resistance II */
	public static String getMysticChestLore() {
		return secSign + "4" + I18n.translateToLocal("lore.mysticchest");
	}
	
	/** Fire Resistance */
	public static String getSkyLegsLore() {
		return secSign + "6" + I18n.translateToLocal("lore.skylegs");
	}
	
	/** Sustenance */
	public static String getMysticLegsLore() {
		return secSign + "2" + I18n.translateToLocal("lore.mysticlegs");
	}
	
	/** Travel I */
	public static String getSkyBootsLore() {
		return secSign + "e" + I18n.translateToLocal("lore.skyboots");
	}
	
	/** Travel II */
	public static String getMysticBootsLore() {
		return secSign + "e" + I18n.translateToLocal("lore.mysticboots");
	}
	
	// ----------------------------------------------------------- //
	// --- TOOL LORE --------------------------------------------- //
	// ----------------------------------------------------------- //
	
	/** Charged */
	public static String getChargedLore() {
		return secSign + "b" + I18n.translateToLocal("lore.charged");
	}
	
	/** Summoning */
	public static String getSummonerLore() {
		return secSign + "e" + I18n.translateToLocal("lore.summoner");
	}
	
	/** Auto-Smelting */
	public static String getAutoSmeltLore() {
		return secSign + "c" + I18n.translateToLocal("lore.autosmelt");
	}
	
	/** Luck */
	public static String getFortuneBonusLore() {
		return secSign + "9" + I18n.translateToLocal("lore.fortunebonus");
	}
	
	/** Quick Farm */
	public static String getQuickFarmLore() {
		return secSign + "2" + I18n.translateToLocal("lore.quickfarm");
	}
	
	// ----------------------------------------------------------- //
	// --- SWORD LORE -------------------------------------------- //
	// ----------------------------------------------------------- //
	
	/** Soul Fracture */
	public static String getSoulSwordLore() {
		return secSign + "6" + I18n.translateToLocal("lore.soulsword");
	}
	
	/** Grave Uprising */
	public static String getZombieSwordLore() {
		return secSign + "4" + I18n.translateToLocal("lore.zombiesword");
	}
	
	/** In the Wind */
	public static String getFeatherSwordLore() {
		return secSign + "e" + I18n.translateToLocal("lore.feathersword");
	}
	
	/** Void Step */
	public static String getEnderSwordLore() {
		return secSign + "5" + I18n.translateToLocal("lore.endersword");
	}
	
	/** Eternal Flame */
	public static String getBlazeSwordLore() {
		return secSign + "c" + I18n.translateToLocal("lore.blazesword");
	}
	
	/** Frozen Solid */
	public static String getIceSwordLore() {
		return secSign + "9" + I18n.translateToLocal("lore.icesword");
	}
	
	// ----------------------------------------------------------- //
	// --- GODLY LORE -------------------------------------------- //
	// ----------------------------------------------------------- //
	
	/** It's oddly viscous... */
	public static String getDemonBloodLore() {
		return secSign + "4" + secSign + "o" + I18n.translateToLocal("lore.demonblood") + secSign + "r";
	}
	
	/** The Nether isn't deep enough! */
	public static String getUnderworldChargeLore() {
		return secSign + "3" + secSign + "o" + I18n.translateToLocal("lore.underworldcharge") + secSign + "r";
	}

	/** Dimensional Punch */
	public static String getHeavenSwordLore() {
		return secSign + "d" + I18n.translateToLocal("lore.heavensword");
	}
	
}

