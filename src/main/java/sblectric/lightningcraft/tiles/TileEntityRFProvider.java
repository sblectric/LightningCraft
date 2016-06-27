package sblectric.lightningcraft.tiles;

import sblectric.lightningcraft.config.LCConfig;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import cofh.api.energy.IEnergyProvider;

/** The tile entity for LE -> RF conversion */
@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHAPI")
public class TileEntityRFProvider extends TileEntityRF implements IEnergyProvider {
	
	public static final int maxStorage = 100000;
	public static final int rfPerTick = 1024;
	private int storedRF = 0;
	
	public TileEntityRFProvider() {
		storedRF = 0;
	}

	@Override
	public void update() {
		// if it has a cell, fill the internal buffer
		if(!worldObj.isRemote && this.hasLPCell()) {
			// at a rate of 1 LE / tick, and the set conversion factor
			// (default: 1 LE -> 50 RF with no wireless losses)
			// powering the block turns off the transfer
			if(!worldObj.isBlockPowered(pos) && this.storedRF <= maxStorage - LCConfig.RFtoLEConversion && this.canDrawCellPower(1)) {
				this.drawCellPower(1);
				this.storedRF += LCConfig.RFtoLEConversion;
				if(this.storedRF > maxStorage) this.storedRF = maxStorage;
				this.markDirty();
			}
		}
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return storedRF;
	}
	
	@Override
	public void setEnergyStored(int energy) {
		if(energy >= 0 && energy <= maxStorage) storedRF = energy;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return maxStorage;
	}

	// can be connected to RF on all sides
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int toExtract, boolean simulate) {
		int maxExtract = Math.max(Math.min(toExtract, rfPerTick), 0);
		int extract = 0;
		if(maxExtract > storedRF) {
			extract = storedRF;
			if(!simulate) storedRF = 0;
		} else {
			extract = maxExtract;
			if(!simulate) storedRF -= maxExtract;
		}
		if(!simulate) this.markDirty();
		return extract;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.storedRF = tagCompound.getInteger("storedRF");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("storedRF", this.storedRF);
		return tagCompound;
	}

}
