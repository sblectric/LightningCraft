package sblectric.lightningcraft.init;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sblectric.lightningcraft.ref.RefStrings;
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
import sblectric.lightningcraft.tiles.TileEntityEnergyProvider;
import sblectric.lightningcraft.tiles.TileEntityEnergyReceiver;
import sblectric.lightningcraft.tiles.TileEntityStaticGenerator;

/** Tile entities */
public class LCTileEntities {
	
	/** Register the tile entities */
	public static void mainRegistry() {
		registerTileEntity(TileEntityLightningCell.class);
		registerTileEntity(TileEntityLightningFurnace.class);
		registerTileEntity(TileEntityLightningCrusher.class);
		registerTileEntity(TileEntityLightningInfuser.class);
		registerTileEntity(TileEntityLightningBreaker.class);
		registerTileEntity(TileEntityLightningMiner.class);
		registerTileEntity(TileEntityStaticGenerator.class);
		registerTileEntity(TileEntityChargingPlate.class);
		registerTileEntity(TileEntityEnchReallocator.class);
		registerTileEntity(TileEntityLightningCannon.class);
		registerTileEntity(TileEntityLightningTransmitter.class);
		registerTileEntity(TileEntityLightningReceiver.class);
		registerTileEntity(TileEntityEnergyProvider.class);
		registerTileEntity(TileEntityEnergyReceiver.class);
	}
	
	/** Registration helper method */
	private static void registerTileEntity(Class<? extends TileEntity> tileClass) {
		GameRegistry.registerTileEntity(tileClass, RefStrings.MODID + ":" + tileClass.getSimpleName().toLowerCase());
	}

}
