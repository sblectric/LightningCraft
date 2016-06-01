package com.lightningcraft.tiles;

import net.minecraft.util.EnumFacing;

/** The abstract RF tile entity */
public abstract class TileEntityRF extends TileEntityLightningUser {
	
	/** Get the stored energy */
	public abstract int getEnergyStored(EnumFacing from);
	
	/** Set the stored energy */
	public abstract void setEnergyStored(int energy);

}
