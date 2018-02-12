package sblectric.lightningcraft.init;

import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sblectric.lightningcraft.enchantments.EnchantmentElectrostaticAura;
import sblectric.lightningcraft.enchantments.EnchantmentHandOfThor;
import sblectric.lightningcraft.enchantments.LCEnchantment;
import sblectric.lightningcraft.registry.RegistryHelper;

/** Enchantment class */
public class LCEnchantments {
	
	private static final List<Enchantment> ENCHANTMENTS = RegistryHelper.ENCHANTMENTS_TO_REGISTER;
	
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
		ENCHANTMENTS.add(handOfThor.setRegistryNameImplicit());
		ENCHANTMENTS.add(elecAura.setRegistryNameImplicit());
	}

}
