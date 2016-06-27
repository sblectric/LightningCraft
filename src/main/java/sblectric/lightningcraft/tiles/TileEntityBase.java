package sblectric.lightningcraft.tiles;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/** The base tile entity variety */
public abstract class TileEntityBase extends TileEntity implements ITickable {
	
	/** A random number generator */
	protected Random random = new Random();
	
	// Network stuff	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		return this.writeToNBT(tagCompound);
	}
 
	@Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.getPos(), 0, getUpdateTag());
    }
	
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
    }
	
	/** Gets the x-coordinate */
	public int getX() {
		return this.getPos().getX();
	}
	
	/** Gets the y-coordinate */
	public int getY() {
		return this.getPos().getY();
	}
	
	/** Gets the z-coordinate */
	public int getZ() {
		return this.getPos().getZ();
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		TileEntityBase tile = (TileEntityBase)this;
		return tile.getWorld().getTileEntity(tile.getPos()) != tile ? false : 
			player.getDistanceSq(tile.getX() + 0.5D, tile.getY() + 0.5D, tile.getZ() + 0.5D) <= 64.0D;
	}	

}
