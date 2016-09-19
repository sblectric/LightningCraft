package sblectric.lightningcraft.util;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sblectric.lightningcraft.tiles.TileEntityRF;

/** TESLA helper things */
public class TeslaUtils {
	
	/** Provide (push-only) adjacent energy */
	public static void tryPushEnergy(TileEntityRF origin) {
		TileEntity adjacent;
		ITeslaProducer producer;
		ITeslaConsumer consumer;
		long calc;
		for(EnumFacing face : EnumFacing.values()) {
			// make sure the tile exists and isn't another export bus / LE generator
			if((adjacent = origin.getWorld().getTileEntity(origin.getPos().offset(face))) != null && 
					!(adjacent instanceof TileEntityRF)) {
				
				EnumFacing swap = face.getOpposite();
				
				// only push energy, unlike the RF handler
				if(origin.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, face) && 
						adjacent.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, swap)) {
					producer = origin.getCapability(TeslaCapabilities.CAPABILITY_PRODUCER, face);
					consumer = adjacent.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, swap);
				} else {
					continue; // nothing to do here
				}

				// make sure the operation is valid, then do it
				if((calc = producer.takePower(Long.MAX_VALUE, true)) > 0) {
					if(consumer.givePower(Long.MAX_VALUE, true) > 0) {
						long realTesla = consumer.givePower(calc, false);
						producer.takePower(realTesla, false);
					}
				}
			}
		}
	}

}
