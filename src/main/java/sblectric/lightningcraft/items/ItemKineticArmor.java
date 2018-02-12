package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.IKineticGear;
import sblectric.lightningcraft.items.base.ItemArmorLC;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.util.ISimpleLEUser;
import sblectric.lightningcraft.util.InventoryLE;
import sblectric.lightningcraft.util.InventoryLE.LECharge;

/** kinetic armor will enable invincibility when LP exists in the inventory */
public class ItemKineticArmor extends ItemArmorLC implements ISimpleLEUser, IKineticGear {
	
	public static final double protectionCost = 0.5;
	public static final double protectionDamper = 5.0;
	public static final double soulCost = 3.0;
	
	public ItemKineticArmor(ArmorMaterial mat, EntityEquipmentSlot armorType) {
		super(mat, armorType);
	}
	
	/** item lore */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		LECharge charge = new LECharge();
		boolean charged = InventoryLE.addInformation(stack, world, list, flag, charge);
		
		// add more lore if there exists an LP source that isn't empty
		if(charged && charge.getCharge() > 0) {
			list.add(LCText.getAutoRepair2Lore());
			list.add(LCText.getInvincibilityLore());
		}
	}
    
    /** can't repair this armor :( */
    @Override
	public boolean getIsRepairable(ItemStack a, ItemStack b) {
    	return false;
    }
    
	/** LP rarity */
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return ILERarity;
	}
	
}
