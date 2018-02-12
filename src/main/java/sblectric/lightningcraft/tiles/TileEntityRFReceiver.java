package sblectric.lightningcraft.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.util.Effect;
import sblectric.lightningcraft.util.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;

/** The tile entity for RF -> LE conversion */
@Optional.Interface(iface = "cofh.api.energy.IEnergyReceiver", modid = "CoFHAPI")
public class TileEntityRFReceiver extends TileEntityRF implements IEnergyReceiver {
	
	public static final int maxStorage = 100000;
	public static final int rfPerTick = 512;
	
	public TileEntityRFReceiver() {
		storage = new EnergyStorage(maxStorage, rfPerTick, 0);
	}

	@Override
	public void update() {
		super.update();
		// if it has a direct cell connection, empty the internal buffer
		if(!world.isRemote && this.hasLPCell() && !this.isRemotelyPowered()) {
			// (default: 500 RF -> 1 LE with no terminal losses)
			// powering the block turns off the transfer
			// attempt this every 5 seconds
			if(world.getTotalWorldTime() % 100 == 0 && !world.isBlockPowered(pos) && 
					storage.getEnergyStored() >= LCConfig.RFtoLEConversion * 10 * 100 && 
					this.cellPower < this.maxPower - 100D * this.tileCell.efficiency) { // 100 LE (50000RF) at a time
				Effect.lightningGen(this.world, this.tileCell.getPos().up());
				storage.modifyEnergyStored(-LCConfig.RFtoLEConversion * 10 * 100);
				this.markDirty();
			}
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability != null) {
			if(capability == consumerCap) {
				return true;
			} else if(capability == producerCap) {
				return false;
			}
		}
		return super.hasCapability(capability, facing);
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
	public int receiveEnergy(EnumFacing from, int toReceive, boolean simulate) {
		if(!simulate) this.markDirty();
		return storage.receiveEnergy(toReceive, simulate);
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
