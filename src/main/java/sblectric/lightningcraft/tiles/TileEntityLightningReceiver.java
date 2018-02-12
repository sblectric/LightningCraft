package sblectric.lightningcraft.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

/** The LE receiver */
public class TileEntityLightningReceiver extends TileEntityBase {
	
	public TileEntityLightningTransmitter tx;
	public BlockPos txPos;
	public double rolloff;
	public double efficiency;
	public double storedPower;
	public double maxPower;
	public boolean outOfRange;
	
	public TileEntityLightningReceiver(double rolloff) {
		this.txPos = new BlockPos(0, -2, 0);
		this.rolloff = rolloff;
	}
	
	public TileEntityLightningReceiver() {
		this(0);
	}
	
	public String getName() {
		return "Lightning Receiver";
	}
	
	/** Tx & Rx logic */
	@Override
	public void update() {
		if(!world.isRemote) {
			
			// look for the transmitter
			TileEntity tile;
			int txX = txPos.getX();
			int txY = txPos.getY();
			int txZ = txPos.getZ();
			boolean hasTx = true;
			this.outOfRange = false;
			
			// check if it exists at its coordinates
			if(txY >= 0 && (tile = world.getTileEntity(txPos)) != null && tile instanceof TileEntityLightningTransmitter) {
				tx = (TileEntityLightningTransmitter)tile;
				double distanceSq = tx.getDistanceSq(getX(), getY(), getZ());
				if(distanceSq <= tx.range * tx.range) {
					if(tx.hasLPCell()) {
						this.storedPower = tx.cellPower;
						this.maxPower = tx.maxPower;
						this.efficiency = tx.quality - distanceSq * rolloff;
					} else {
						hasTx = false;
					}
				} else {
					hasTx = false;
					this.outOfRange = true;
				}
			} else {
				hasTx = false;
			}
			
			if(!hasTx) {
				this.storedPower = 0;
				this.maxPower = -1;
				this.efficiency = -1;
			}
			
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		int x = tagCompound.getInteger("TransmitterX");
		int y = tagCompound.getInteger("TransmitterY");
		int z = tagCompound.getInteger("TransmitterZ");
		this.txPos = new BlockPos(x, y, z);
		this.storedPower = tagCompound.getDouble("StoredPower");
		this.maxPower = tagCompound.getDouble("MaxPower");
		this.rolloff = tagCompound.getDouble("Rolloff");
		this.efficiency = tagCompound.getDouble("Efficiency");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setDouble("TransmitterX", txPos.getX());
		tagCompound.setDouble("TransmitterY", txPos.getY());
		tagCompound.setDouble("TransmitterZ", txPos.getZ());
		tagCompound.setDouble("StoredPower", this.storedPower);
		tagCompound.setDouble("MaxPower", this.maxPower);
		tagCompound.setDouble("Rolloff", this.rolloff);
		tagCompound.setDouble("Efficiency", this.efficiency);
		return tagCompound;
	}

}
