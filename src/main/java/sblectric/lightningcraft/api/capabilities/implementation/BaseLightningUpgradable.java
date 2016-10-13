package sblectric.lightningcraft.api.capabilities.implementation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import sblectric.lightningcraft.api.capabilities.ILightningUpgradable;

/** Default implementation of the ILightningUpgradable capability */
public class BaseLightningUpgradable implements ILightningUpgradable, INBTSerializable<NBTTagCompound> {

	private boolean isUpgraded;
	
	@Override
	public EnumActionResult onLightningUpgrade(ItemStack stack, EntityPlayer player, World world, BlockPos pos, 
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		this.setUpgraded(true);
		return EnumActionResult.SUCCESS; // on success, uses an upgrade
	}

	@Override
	public boolean isUpgraded() {
		return isUpgraded;
	}
	
	@Override
	public void setUpgraded(boolean upgraded) {
		isUpgraded = upgraded;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound tagCompound) {
		this.isUpgraded = tagCompound.getBoolean("isUpgraded");
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		return serializeNBT(new NBTTagCompound());
	}
	
	public NBTTagCompound serializeNBT(NBTTagCompound existingTag) {
		existingTag.setBoolean("isUpgraded", this.isUpgraded);
		return existingTag;
	}

}
