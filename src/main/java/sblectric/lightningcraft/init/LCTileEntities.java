package sblectric.lightningcraft.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import sblectric.lightningcraft.tiles.TileEntityChargingPlate;
import sblectric.lightningcraft.tiles.TileEntityEnchReallocator;
import sblectric.lightningcraft.tiles.TileEntityLightningBreaker;
import sblectric.lightningcraft.tiles.TileEntityLightningCannon;
import sblectric.lightningcraft.tiles.TileEntityLightningCell;
import sblectric.lightningcraft.tiles.TileEntityLightningCrusher;
import sblectric.lightningcraft.tiles.TileEntityLightningFurnace;
import sblectric.lightningcraft.tiles.TileEntityLightningInfuser;
import sblectric.lightningcraft.tiles.TileEntityLightningMiner;
import sblectric.lightningcraft.tiles.TileEntityLightningReceiver;
import sblectric.lightningcraft.tiles.TileEntityLightningTransmitter;
import sblectric.lightningcraft.tiles.TileEntityRFProvider;
import sblectric.lightningcraft.tiles.TileEntityRFReceiver;
import sblectric.lightningcraft.tiles.TileEntityStaticGenerator;

/** Tile entities */
public class LCTileEntities {
	
	/** Register the tile entities */
	public static void mainRegistry() {
		GameRegistry.registerTileEntity(TileEntityLightningCell.class, "TileEntityLightningCell");
		GameRegistry.registerTileEntity(TileEntityLightningFurnace.class, "TileEntityLightningFurnace");
		GameRegistry.registerTileEntity(TileEntityLightningCrusher.class, "TileEntityLightningCrusher");
		GameRegistry.registerTileEntity(TileEntityLightningInfuser.class, "TileEntityLightningInfuser");
		GameRegistry.registerTileEntity(TileEntityLightningBreaker.class, "TileEntityLightningBreaker");
		GameRegistry.registerTileEntity(TileEntityLightningMiner.class, "TileEntityLightningMiner");
		GameRegistry.registerTileEntity(TileEntityStaticGenerator.class, "TileEntityStaticGenerator");
		GameRegistry.registerTileEntity(TileEntityChargingPlate.class, "TileEntityChargingPlate");
		GameRegistry.registerTileEntity(TileEntityEnchReallocator.class, "TileEntityEnchReallocator");
		GameRegistry.registerTileEntity(TileEntityLightningCannon.class, "TileEntityLightningCannon");
		GameRegistry.registerTileEntity(TileEntityLightningTransmitter.class, "TileEntityLightningTransmitter");
		GameRegistry.registerTileEntity(TileEntityLightningReceiver.class, "TileEntityLightningReceiver");
		GameRegistry.registerTileEntity(TileEntityRFProvider.class, "TileEntityLERFProvider");
		GameRegistry.registerTileEntity(TileEntityRFReceiver.class, "TileEntityLERFReceiver");
	}

}
