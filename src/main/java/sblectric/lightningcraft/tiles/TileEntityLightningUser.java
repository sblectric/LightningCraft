package sblectric.lightningcraft.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

// class for tile entities that use LE
public abstract class TileEntityLightningUser extends TileEntityBase {
	
	public TileEntityLightningCell tileCell;
	public double cellPower;
	public double maxPower;
	private double efficiency;
	
	/** make sure the machine has a power cell to draw from */
	protected boolean hasLPCell() {
		if(world == null) return false;
		
		TileEntity tile;
		
		// check all around the block for a lightning cell
		for(int x = getX() - 1; x <= getX() + 1; x++) {
			for(int y = getY() - 1; y <= getY() + 1; y++) {
				for(int z = getZ() - 1; z <= getZ() + 1; z++) {
					
					if(!(x == getX() && y == getY() && z == getZ())) {
						tile = this.world.getTileEntity(new BlockPos(x, y, z));
						
						// it matches!
						if(tile != null && tile instanceof TileEntityLightningCell) {
							
							this.tileCell = (TileEntityLightningCell) tile;
							this.cellPower = this.tileCell.storedPower;
							this.maxPower = this.tileCell.maxPower;
							this.setEfficiency(1.0);
							return true;
						}
					}
				}
			}
		}
		
		// try to find wireless power (no wireless chains!)
		if(!(this instanceof TileEntityLightningTransmitter)) {
			for(int x = getX() - 1; x <= getX() + 1; x++) {
				for(int y = getY() - 1; y <= getY() + 1; y++) {
					for(int z = getZ() - 1; z <= getZ() + 1; z++) {
						
						if(!(x == getX() && y == getY() && z == getZ())) {
							tile = this.world.getTileEntity(new BlockPos(x, y, z));
							
							// it matches!
							if(tile != null && tile instanceof TileEntityLightningReceiver) {
								TileEntityLightningReceiver rx = (TileEntityLightningReceiver)tile;
								
								// the chain exists
								if(rx.tx != null && rx.tx.hasLPCell()) {
									this.tileCell = rx.tx.tileCell;
									this.cellPower = this.tileCell.storedPower;
									this.maxPower = this.tileCell.maxPower;
									this.setEfficiency(rx.efficiency);
									return true;
								}
							}
						}
					}
				}
			}
		}
		
		// no match
		this.tileCell = null;
		this.cellPower = 0;
		this.maxPower = -1;
		this.setEfficiency(-1);
		return false;
	}
	
	/** Machines know when they are on wireless power */
	protected boolean isRemotelyPowered() {
		return this.getEfficiency() < 1;
	}
	
	/** Can the amount of power be drawn? */
	protected boolean canDrawCellPower(double amount) {
		return getEfficiency() > 0 && 
				this.cellPower >= amount / getEfficiency() && this.tileCell.storedPower >= amount / getEfficiency();
	}
	
	/** Can the amount of power be added? */
	protected boolean canAddCellPower(double amount) {
		return getEfficiency() > 0 && 
				this.cellPower + amount * getEfficiency() <= maxPower && this.tileCell.storedPower + amount * getEfficiency() <= this.tileCell.maxPower;
	}
	
	/** gets the actual needed power by the machine due to inefficiencies */
	public double getActualNeededPower(double amount) {
		if(getEfficiency() > 0) {
			return amount / getEfficiency();
		} else {
			return Double.NaN;
		}
	}
	
	/** Draw power from the cell */
	protected boolean drawCellPower(double amount) {
		if(this.canDrawCellPower(amount)) {
			this.cellPower -= amount / getEfficiency();
			this.tileCell.storedPower -= amount / getEfficiency();
			return true;
		} else {
			return false;
		}
	}
	
	/** Add power to the cell */
	protected boolean addCellPower(double amount) {
		if(this.canAddCellPower(amount)) {
			this.cellPower += amount * getEfficiency();
			this.tileCell.storedPower += amount * getEfficiency();
			return true;
		} else {
			return false;
		}
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}

}
