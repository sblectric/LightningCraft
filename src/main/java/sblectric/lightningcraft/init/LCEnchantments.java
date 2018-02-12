package sblectric.lightningcraft.init;

import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sblectric.lightningcraft.enchantments.EnchantmentElectrostaticAura;
import sblectric.lightningcraft.enchantments.EnchantmentHandOfThor;
import sblectric.lightningcraft.enchantments.LCEnchantment;

/** Enchantment class */
public class LCEnchantments {
	
	public static void mainRegistry() {
		addEnchantments();
		registerEnchantments();
	}
	
	public static LCEnchantment handOfThor;
	public static LCEnchantment elecAura;
	
	private static void addEnchantments() {
		handOfThor = new EnchantmentHandOfThor(Rarity.RARE);
		elecAura  = new EnchantmentElectrostaticAura(Rarity.RARE);
	}
	
	/** Now with correct (non-ID) registration! */
	private static void registerEnchantments() {
		GameRegistry.register(handOfThor.setRegistryNameImplicit());
		GameRegistry.register(elecAura.setRegistryNameImplicit());
	}

}
