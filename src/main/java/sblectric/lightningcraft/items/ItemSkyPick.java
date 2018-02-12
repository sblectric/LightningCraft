package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Multimap;

import sblectric.lightningcraft.api.IMysticGear;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.items.base.ItemPickaxeLC;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.util.SkyUtils;

/** The Skyfather pickaxe */
public class ItemSkyPick extends ItemPickaxeLC {

	public ItemSkyPick(ToolMaterial mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		if(EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
			if(LCConfig.autoSmelt) list.add(LCText.getAutoSmeltLore());
			if(this instanceof IMysticGear) list.add(LCText.getFortuneBonusLore());
		}
	}

	// speed modifier
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		if(equipmentSlot == EntityEquipmentSlot.MAINHAND) SkyUtils.setToolSpeedModifier(this, multimap, ATTACK_SPEED_MODIFIER, attackSpeed);
		return multimap;
	}
	
}
