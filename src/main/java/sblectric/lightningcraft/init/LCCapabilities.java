package sblectric.lightningcraft.init;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import sblectric.lightningcraft.api.capabilities.ILightningUpgradable;
import sblectric.lightningcraft.api.capabilities.implementation.BaseLightningUpgradable;

/** LightningCraft capabilities */
public class LCCapabilities {
	
    /** Access to the lightning-upgradable capability */
    @CapabilityInject(ILightningUpgradable.class)
    public static Capability<ILightningUpgradable> LIGHTNING_UPGRADABLE = null;
    
    /** Lightning-upgradable storage handler */
    public static class CapabilityLightningUpgradable implements IStorage<ILightningUpgradable> {

		@Override
		public NBTBase writeNBT(Capability<ILightningUpgradable> capability, ILightningUpgradable instance, EnumFacing side) {
			return null;
		}

		@Override
		public void readNBT(Capability<ILightningUpgradable> capability, ILightningUpgradable instance, EnumFacing side, NBTBase nbt) {}
    	
    }
    
    /** Register the capabilities */
    public static void mainRegistry() {
    	CapabilityManager.INSTANCE.register(ILightningUpgradable.class, new CapabilityLightningUpgradable(), BaseLightningUpgradable.class);
    }

}
