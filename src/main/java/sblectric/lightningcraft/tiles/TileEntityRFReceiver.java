package sblectric.lightningcraft.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import cofh.api.energy.IEnergyReceiver;

import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.util.Effect;

/** The tile entity for RF -> LE conversion */
@Optional.Interface(iface = "cofh.api.energy.IEnergyReceiver", modid = "CoFHAPI")
public class TileEntityRFReceiver extends TileEntityRF implements IEnergyReceiver {
	
	public static final int maxStorage = 100000;
	public static final int rfPerTick = 512;
	private int storedRF;
	
	public TileEntityRFReceiver() {
		storedRF = 0;
	}

	@Override
	public void update() {
		// if it has a direct cell connection, empty the internal buffer
		if(!worldObj.isRemote && this.hasLPCell() && !this.isRemotelyPowered()) {
			// (default: 500 RF -> 1 LE with no terminal losses)
			// powering the block turns off the transfer
			// attempt this every 5 seconds
			if(worldObj.getTotalWorldTime() % 100 == 0 && !worldObj.isBlockPowered(pos) && this.storedRF >= LCConfig.RFtoLEConversion * 10 * 100 && 
					this.cellPower < this.maxPower - 100D * this.tileCell.efficiency) { // 100 LE (50000RF) at a time
				Effect.lightningGen(this.worldObj, this.tileCell.getPos().up());
				this.storedRF -= (LCConfig.RFtoLEConversion * 10 * 100);
				if(this.storedRF < 0) this.storedRF = 0;
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
	public int receiveEnergy(EnumFacing from, int toReceive, boolean simulate) {
		int maxReceive = Math.max(Math.min(toReceive, rfPerTick), 0);
		int receive = 0;
		if(maxStorage > storedRF + maxReceive) {
			receive = maxReceive;
			if(!simulate) storedRF += maxReceive;
		} else {
			receive = maxStorage - storedRF;
			if(!simulate) storedRF = maxStorage;
		}
		if(!simulate) this.markDirty();
		return receive;
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
