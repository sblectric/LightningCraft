package sblectric.lightningcraft.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import sblectric.lightningcraft.tiles.TileEntityRF;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

/** RF helper things */
public class RFUtils {
	
	/** Provide and recieve adjacent energy */
	@Optional.Method(modid = "CoFHAPI")
	public static void handleAdjacentEnergy(TileEntityRF origin) {
		TileEntity adjacent;
		IEnergyReceiver receiver;
		IEnergyProvider provider;
		int calc;
		for(EnumFacing face : EnumFacing.values()) {
			// make sure the tile exists and isn't another export bus / LE generator
			if((adjacent = origin.getWorld().getTileEntity(origin.getPos().offset(face))) != null && 
					!(adjacent instanceof TileEntityRF)) {
				
				// check for providing or recieving cases
				if(origin instanceof IEnergyProvider && adjacent instanceof IEnergyReceiver) {
					receiver = (IEnergyReceiver)adjacent;
					provider = (IEnergyProvider)origin;
				} else if(origin instanceof IEnergyReceiver && adjacent instanceof IEnergyProvider) {
					receiver = (IEnergyReceiver)origin;
					provider = (IEnergyProvider)adjacent;
				} else {
					continue; // nothing to do here
				}

				// make sure the operation is valid, then do it
				if((calc = provider.extractEnergy(face, Integer.MAX_VALUE, true)) > 0) {
					EnumFacing swap = face.getOpposite();
					if(receiver.canConnectEnergy(swap)){
						int realRF = receiver.receiveEnergy(swap, calc, false);
						provider.extractEnergy(face, realRF, false);
					}
				}
			}
		}
	}

}
