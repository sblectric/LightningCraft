package sblectric.lightningcraft.tiles;

import net.minecraftforge.fml.common.registry.GameRegistry;

/** Tile entities */
public class LCTileEntities {
	
	/** Register the tile entities */
	public static void registerTileEntities() {
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
