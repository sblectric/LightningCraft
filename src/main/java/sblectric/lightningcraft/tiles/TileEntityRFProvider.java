package sblectric.lightningcraft.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.util.RFStorage;
import cofh.api.energy.IEnergyProvider;

/** The tile entity for LE -> RF conversion */
@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHAPI")
public class TileEntityRFProvider extends TileEntityRF implements IEnergyProvider {
	
	public static final int maxStorage = 100000;
	public static final int rfPerTick = 1024;
	private RFStorage storage;
	
	public TileEntityRFProvider() {
		storage = new RFStorage(maxStorage, 0, rfPerTick);
	}

	@Override
	public void update() {
		super.update();
		// if it has a cell, fill the internal buffer
		if(!worldObj.isRemote && this.hasLPCell()) {
			// at a rate of 1 LE / tick, and the set conversion factor
			// (default: 1 LE -> 50 RF with no wireless losses)
			// powering the block turns off the transfer
			if(!worldObj.isBlockPowered(pos) && 
					storage.getEnergyStored() <= storage.getMaxEnergyStored() - LCConfig.RFtoLEConversion && 
					this.canDrawCellPower(1)) {
				this.drawCellPower(1);
				storage.modifyEnergyStored(LCConfig.RFtoLEConversion);
				this.markDirty();
			}
		}
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return storage.getEnergyStored();
	}
	
	@Override
	public void setEnergyStored(int energy) {
		storage.setEnergyStored(energy);
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return storage.getMaxEnergyStored();
	}

	// can be connected to RF on all sides
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int toExtract, boolean simulate) {
		if(!simulate) this.markDirty();
		return storage.extractEnergy(toExtract, simulate);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		storage.readFromNBT(tagCompound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		storage.writeToNBT(tagCompound);
		return tagCompound;
	}

}
