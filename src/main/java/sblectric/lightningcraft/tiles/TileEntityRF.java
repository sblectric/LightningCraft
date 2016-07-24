package sblectric.lightningcraft.tiles;

import net.minecraft.util.EnumFacing;
import sblectric.lightningcraft.util.RFUtils;

/** The abstract RF tile entity */
public abstract class TileEntityRF extends TileEntityLightningUser {
	
	/** Get the stored energy */
	public abstract int getEnergyStored(EnumFacing from);
	
	/** Set the stored energy */
	public abstract void setEnergyStored(int energy);
	
	/** Check for adjacent IEnergyHandlers and get energy from them as needed */
	@Override
	public void update() {
		if(!worldObj.isRemote) {
			try {
				RFUtils.handleAdjacentEnergy(this);
			} catch(Exception e) {
				// ignore the operation when the above method does not exist
			}
		}
	}

}
