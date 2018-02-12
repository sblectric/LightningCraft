package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.util.StackHelper;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.items.base.ItemLC;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.ref.Metal.Rod;
import sblectric.lightningcraft.util.Effect;
import sblectric.lightningcraft.util.SkyUtils;

/** The golf club for summoning lightning. */
public class ItemGolfClub extends ItemLC {

	public ItemGolfClub(){
		this.setMaxDamage(6); // pass damage through
		this.setMaxStackSize(1); // it acts as a tool!
		this.setHasSubtypes(false); // only damage
		this.canRepair = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		list.add(LCText.getSummonerLore());
	}

	/** Summon lightning when swung */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(!world.isRemote && hand == EnumHand.MAIN_HAND) {
			ItemStack stack = player.getHeldItem(hand);
			SkyUtils.damageStack(2, stack, player, EntityEquipmentSlot.MAINHAND, true);
			Effect.lightning(player, false);

			// golf club achievement
//			player.addStat(LCAchievements.swingGolfClub, 1);
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	// repair the item with given item
	@Override
	public boolean getIsRepairable(ItemStack a, ItemStack b) {
		ItemStack repairWith = new ItemStack(LCItems.rod, 0, Rod.IRON);
		if(StackHelper.areItemStacksEqualForCrafting(repairWith, b)){
			return true;
		} else {
			return false;
		}
	}

}
