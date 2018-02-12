package sblectric.lightningcraft.tiles;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import sblectric.lightningcraft.integration.energy.EnergyApiHelper;
import sblectric.lightningcraft.util.EnergyStorage;
import sblectric.lightningcraft.util.RFUtils;
import sblectric.lightningcraft.util.TeslaUtils;

/** The abstract RF / TESLA tile entity */
public abstract class TileEntityRF extends TileEntityLightningUser {

	@CapabilityInject(ITeslaProducer.class)
	protected static Capability producerCap = null;

	@CapabilityInject(ITeslaConsumer.class)
	protected static Capability consumerCap = null;

	@CapabilityInject(ITeslaHolder.class)
	protected static Capability holderCap = null;

	/** The energy storage */
	protected EnergyStorage storage;

	/** Get the stored energy */
	public abstract int getEnergyStored(EnumFacing from);

	/** Set the stored energy */
	public abstract void setEnergyStored(int energy);

	/** Check for adjacent IEnergyHandlers and get energy to / from them as needed */
	@Override
	public void update() {
		if(!world.isRemote) {
			if(EnergyApiHelper.rfLoaded) RFUtils.handleAdjacentEnergy(this);
			if(EnergyApiHelper.teslaLoaded) TeslaUtils.tryPushEnergy(this);
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability != null && capability == holderCap) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability != null && (capability == holderCap || capability == producerCap || capability == consumerCap)) {
			return (T)storage;
		}
		return super.getCapability(capability, facing);
	}

}
