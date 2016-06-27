package sblectric.lightningcraft.tiles;

import net.minecraft.nbt.NBTTagCompound;

/** The LE transmitter */
public class TileEntityLightningTransmitter extends TileEntityLightningUser {
	
	public double range;
	public double quality;
	
	public TileEntityLightningTransmitter(double range, double quality) {
		this.range = range;
		this.quality = quality;
	}
	
	public TileEntityLightningTransmitter() {}
	
	/** Note: all transmitter logic is handled in the receiver */
	@Override
	public void update() {}
	
	/** the receiver must know if the transmitter has a power source */
	@Override
	public boolean hasLPCell() {
		return super.hasLPCell();
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		range = tagCompound.getDouble("Range");
		quality = tagCompound.getDouble("Quality");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setDouble("Range", range);
		tagCompound.setDouble("Quality", quality);
		return tagCompound;
	}

}
