package sblectric.lightningcraft.integration.energy;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModAPIManager;

/** Energy helper class */
public class EnergyApiHelper {
	
	/** Is an RF API present? */
	public static boolean rfLoaded = true; // always true with Forge Energy
	
	/** Does TESLA exist? */
	public static boolean teslaLoaded = Loader.isModLoaded("tesla");
	
	/** Is any compatible API loaded? */
	public static boolean rfOrTeslaLoaded = rfLoaded || teslaLoaded;
	
	/** Are both APIs loaded? */
	public static boolean rfAndTeslaLoaded = rfLoaded && teslaLoaded;

}
