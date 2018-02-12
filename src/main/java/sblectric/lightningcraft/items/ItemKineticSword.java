package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import sblectric.lightningcraft.api.IKineticGear;
import sblectric.lightningcraft.items.base.ItemSwordLC;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.util.ISimpleLEUser;
import sblectric.lightningcraft.util.InventoryLE;
import sblectric.lightningcraft.util.InventoryLE.LECharge;

/** The kinetic sword */
public class ItemKineticSword extends ItemSwordLC implements ISimpleLEUser, IKineticGear {
	
	public static final double attackDamage = 10;

	public ItemKineticSword(ToolMaterial mat) {
		super(mat);
	}
	
	/** Custom attack damage */
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase user) {
		InventoryLE.hitEntity(stack, entity, user, attackDamage, true); // sword normally peaks at 10 damage -> 50 MAX
    	return super.hitEntity(stack, entity, user);
    }

	/** No standard attack damage */
	@Override
	public Multimap getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        return HashMultimap.create();
    }

	/** item lore */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		LECharge charge = new LECharge();
		boolean charged = InventoryLE.addInformation(stack, world, list, flag, charge);
		double damage = 0;

		// add more lore if there exists an LE source that isn't empty
		if(charged && charge.getCharge() > 0) {
			list.add(LCText.getAutoRepair2Lore());
			damage = Math.min(charge.getCharge(), attackDamage);
		}
		
		// attack damage
		list.add("");
		if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			list.add(LCText.secSign + "9+" + LCText.af.format(damage) + " Attack Damage");
		} else {
			list.add(LCText.secSign + "3+" + LCText.af.format(charge.getCharge()) + " Attack Damage (Sneaking)");
		}
	}

	/** LE rarity */
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return ILERarity;
	}

}
