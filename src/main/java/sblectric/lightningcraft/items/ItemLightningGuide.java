package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.gui.client.GuiLightningGuide;
import sblectric.lightningcraft.init.LCAchievements;
import sblectric.lightningcraft.items.base.ItemMeta;

/** The lightning guide */
public class ItemLightningGuide extends ItemMeta {

	public ItemLightningGuide() {
		super(3, false, false);
		this.setMaxStackSize(1); // stack only one
	}
	
	/** Only show the first book in GUIs */
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List sub) {
		sub.add(new ItemStack(item));
	}

	/** show the book GUI */
	@SideOnly(Side.CLIENT)
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		Minecraft.getMinecraft().displayGuiScreen(new GuiLightningGuide(this.getDamage(stack)));
		return new ActionResult(EnumActionResult.SUCCESS, stack); // pass it through
	}

	/** upgrade the book to show Skyfather stuff when you unlock it */
	@Override
	public void onUpdate(ItemStack a, World world, Entity player, int par4, boolean par5) {
		if(!world.isRemote && player.ticksExisted % 10 == 0) { // serverside yo

			EntityPlayerMP p = (EntityPlayerMP)player;
			if(p.getStatFile().hasAchievementUnlocked(LCAchievements.reachUnderworld)) {
				this.setDamage(a, 2);
			} else if(p.getStatFile().hasAchievementUnlocked(LCAchievements.infuseSkyfather)) {
				this.setDamage(a, 1);
			} else { // keep forbidden knowledge from the masses
				this.setDamage(a, 0);
			}

		}
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		switch(stack.getItemDamage()) {
		case 1:
			return EnumRarity.UNCOMMON;
		case 2:
			return EnumRarity.RARE;
		default:
			return EnumRarity.COMMON;
		}
	}

}
