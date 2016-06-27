package sblectric.lightningcraft.ref;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.ModAPIManager;

public class RefMisc {
	public static final Boolean DEV = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
	public static boolean DEBUG = false;
}
