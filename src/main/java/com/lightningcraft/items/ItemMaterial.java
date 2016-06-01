package com.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.achievements.LCAchievements;
import com.lightningcraft.blocks.PortalUnderworld;
import com.lightningcraft.ref.LCText;
import com.lightningcraft.ref.Material;
import com.lightningcraft.tiles.TileEntityLightningCell;
import com.lightningcraft.util.Effect;

/** The material class */
public class ItemMaterial extends ItemMeta {

	public ItemMaterial() {
		super(Material.count, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
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
		int meta = stack.getItemDamage();
		if(meta >= Material.ICHOR) {
			return EnumRarity.EPIC;
		} else if(meta >= Material.DEMON_BLOOD) {
			return EnumRarity.RARE;
		} else {
			return EnumRarity.COMMON;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		int i = stack.getItemDamage();
		if(i == Material.NETHER_NUGGET || i == Material.ENSORCELLED || i == Material.ICHOR) {
			return true;
		} else {
			return false;
		}
	}

	/** Cell upgrading */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		switch(stack.getItemDamage()) {
		// Cell Upgrade usage
		case Material.CELL_UPGRADE:
			// check if the tile entity is correct
			TileEntity tile = world.getTileEntity(pos);
			if(tile != null && tile instanceof TileEntityLightningCell) {
				TileEntityLightningCell tlc = (TileEntityLightningCell)tile;
				// upgrade time!
				if(!tlc.isUpgraded) {
					player.addStat(LCAchievements.upgradeCell, 1);
					tlc.isUpgraded = true;
					tlc.maxPower *= 1.5;
					tlc.cellName += " (Upgr.)";
					
					if(!world.isRemote) {
						--stack.stackSize; // transfer it to the cell
						return EnumActionResult.SUCCESS;
					}
				}
			}
			return EnumActionResult.FAIL;

		// Underworld Charge usage
		case Material.UNDER_CHARGE:

			if(!world.isRemote) {
				if(PortalUnderworld.ignitePortal(world, pos.up())) {
					if(player.capabilities.isCreativeMode == false) stack.stackSize--;
					Effect.lightning(world, pos.getX(), pos.getY() + 1, pos.getZ());
					if(stack.stackSize <= 0) stack = null;
				}
			}
			return EnumActionResult.SUCCESS;

		// Default usage
		default:
			return super.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);
		}
	}

}
