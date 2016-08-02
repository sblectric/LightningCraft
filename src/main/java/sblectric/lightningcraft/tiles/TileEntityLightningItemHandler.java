package sblectric.lightningcraft.tiles;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

/** Superclass for lightning users that have inventory slots */
public abstract class TileEntityLightningItemHandler extends TileEntityLightningUser implements ISidedInventoryLC {
	
	protected InvWrapper itemCapability;

	public TileEntityLightningItemHandler() {
		itemCapability = new InvWrapper(this);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		} else {
			return super.hasCapability(capability, facing);
		}
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T)itemCapability;
		} else {
			return super.getCapability(capability, facing);
		}
	}

}
