package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.blocks.PortalUnderworld;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.init.LCCapabilities;
import sblectric.lightningcraft.init.LCNetwork;
import sblectric.lightningcraft.items.base.ItemMeta;
import sblectric.lightningcraft.network.MessageLightningUpgrade;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.ref.Material;
import sblectric.lightningcraft.util.Effect;

/** The material class */
public class ItemMaterial extends ItemMeta {

	public ItemMaterial() {
		super(Material.count, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		switch(stack.getItemDamage()) {
		case Material.DEMON_BLOOD:
			list.add(LCText.getDemonBloodLore());
			break;
		case Material.UNDER_CHARGE:
			list.add(LCText.getUnderworldChargeLore());
			break;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		int type = stack.getItemDamage();
		if(type == Material.ICHOR || type == Material.DIVINE_DUST) {
			return EnumRarity.EPIC;
		} else if(type >= Material.DEMON_BLOOD) {
			return EnumRarity.RARE;
		} else {
			return EnumRarity.COMMON;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		int i = stack.getItemDamage();
		if(i == Material.NETHER_NUGGET || i == Material.ENSORCELLED || i == Material.ICHOR || i == Material.DIVINE_DUST) {
			return true;
		} else {
			return false;
		}
	}

	/** Right-click the item on a block */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		switch(stack.getItemDamage()) {
		
		// Cell Upgrade usage
		case Material.UPGRADE:
			// upgrade an upgradable tile entity
			TileEntity tile = world.getTileEntity(pos);
			if(LCConfig.upgradeEnabled && tile != null && tile.hasCapability(LCCapabilities.LIGHTNING_UPGRADABLE, null) && 
					!tile.getCapability(LCCapabilities.LIGHTNING_UPGRADABLE, null).isUpgraded()) {
				EnumActionResult r = tile.getCapability(LCCapabilities.LIGHTNING_UPGRADABLE, null)
						.onLightningUpgrade(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);
				if(r == EnumActionResult.SUCCESS && !world.isRemote) {
					stack.setCount(stack.getCount() - 1); // transfer the upgrade to the tile entity on success
					tile.markDirty();
					
//					player.addStat(LCAchievements.upgradeMachine, 1); // give out the achievement
					
					// send a message to nearby clients to update the upgraded status
					LCNetwork.net.sendToAllAround(new MessageLightningUpgrade(pos), 
							new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 1024));
				}
				return r;
			} else {
				return EnumActionResult.FAIL;
			}

		// Underworld Charge usage
		case Material.UNDER_CHARGE:

			if(!world.isRemote) {
				if(PortalUnderworld.ignitePortal(world, pos.up())) {
					if(player.capabilities.isCreativeMode == false) stack.setCount(stack.getCount() - 1);
					Effect.lightning(world, pos.getX(), pos.getY() + 1, pos.getZ());
					if(stack.getCount() <= 0) stack = ItemStack.EMPTY;
				}
			}
			return EnumActionResult.SUCCESS;

		// Default usage
		default:
			return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		}
	}

}
