package sblectric.lightningcraft.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import sblectric.lightningcraft.tiles.TileEntityEnergy;

/** Energy helper things */
public class ForgeEnergyUtils {
	
	/** Provide and recieve adjacent energy */
	public static void handleAdjacentEnergy(TileEntityEnergy origin) {
		TileEntity adjacent;
		IEnergyStorage receiver;
		IEnergyStorage provider;
		int calc;
		for(EnumFacing face : EnumFacing.values()) {
			// make sure the tile exists and isn't another export bus / LE generator
			if((adjacent = origin.getWorld().getTileEntity(origin.getPos().offset(face))) != null && 
					!(adjacent instanceof TileEntityEnergy)) {
				
				EnumFacing swap = face.getOpposite();
				
				// check for providing or recieving cases
				if(origin.hasCapability(CapabilityEnergy.ENERGY, face) && adjacent.hasCapability(CapabilityEnergy.ENERGY, swap)) {
					provider = origin.getCapability(CapabilityEnergy.ENERGY, face);
					receiver = adjacent.getCapability(CapabilityEnergy.ENERGY, swap);
				} else {
					continue; // nothing to do here
				}

				// make sure the operation is valid, then do it
				if((calc = provider.extractEnergy(Integer.MAX_VALUE, true)) > 0) {
					if(provider.canExtract() && receiver.canReceive()){
						int realRF = receiver.receiveEnergy(calc, false);
						provider.extractEnergy(realRF, false);
					}
				}
			}
		}
	}

}
