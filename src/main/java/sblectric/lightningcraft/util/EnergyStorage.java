package sblectric.lightningcraft.util;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Optional;

/** Custom implementation of energy storage for RF and TESLA */
@Optional.InterfaceList({
		@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaConsumer", modid = "tesla"),
		@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaProducer", modid = "tesla"),
		@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaHolder", modid = "tesla")
})
public class EnergyStorage implements ITeslaConsumer, ITeslaProducer, ITeslaHolder {

	protected int energy;
	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;

	public EnergyStorage(int capacity) {
		this(capacity, capacity, capacity);
	}

	public EnergyStorage(int capacity, int maxTransfer) {
		this(capacity, maxTransfer, maxTransfer);
	}

	public EnergyStorage(int capacity, int maxReceive, int maxExtract) {
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	public EnergyStorage readFromNBT(NBTTagCompound nbt) {
		this.energy = nbt.getInteger("storedRF");

		if (energy > capacity) {
			energy = capacity;
		}
		return this;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (energy < 0) {
			energy = 0;
		}
		nbt.setInteger("storedRF", energy);
		return nbt;
	}

	public EnergyStorage setCapacity(int capacity) {
		this.capacity = capacity;

		if (energy > capacity) {
			energy = capacity;
		}
		return this;
	}

	public EnergyStorage setMaxTransfer(int maxTransfer) {
		setMaxReceive(maxTransfer);
		setMaxExtract(maxTransfer);
		return this;
	}

	public EnergyStorage setMaxReceive(int maxReceive) {
		this.maxReceive = maxReceive;
		return this;
	}

	public EnergyStorage setMaxExtract(int maxExtract) {
		this.maxExtract = maxExtract;
		return this;
	}

	public int getMaxReceive() {
		return maxReceive;
	}

	public int getMaxExtract() {
		return maxExtract;
	}

	/**
	 * This function is included to allow for server to client sync. Do not call this externally to the containing Tile Entity, as not all IEnergyHandlers
	 * are guaranteed to have it.
	 *
	 * @param energy
	 */
	public void setEnergyStored(int energy) {
		this.energy = energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	/**
	 * This function is included to allow the containing tile to directly and efficiently modify the energy contained in the EnergyStorage. Do not rely on this
	 * externally, as not all IEnergyHandlers are guaranteed to have it.
	 *
	 * @param energy
	 */
	public void modifyEnergyStored(int energy) {
		this.energy += energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}

	public int extractEnergy(int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
		}
		return energyExtracted;
	}

	public int getEnergyStored() {
		return energy;
	}

	public int getMaxEnergyStored() {
		return capacity;
	}

	// TESLA stuff from here
	
	@Override
	public long getStoredPower() {
		return energy;
	}

	@Override
	public long getCapacity() {
		return capacity;
	}

	@Override
	public long takePower(long power, boolean simulated) {
		if(power > Integer.MAX_VALUE) power = Integer.MAX_VALUE; // make sure no overflows happen on checks
		return extractEnergy((int)power, simulated);
	}

	@Override
	public long givePower(long power, boolean simulated) {
		if(power > Integer.MAX_VALUE) power = Integer.MAX_VALUE; // make sure no overflows happen on checks
		return receiveEnergy((int)power, simulated);
	}

}
