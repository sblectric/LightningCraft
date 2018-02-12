package sblectric.lightningcraft.integration.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import sblectric.lightningcraft.api.registry.ILightningCraftBlock;
import sblectric.lightningcraft.blocks.base.BlockContainerLC;
import sblectric.lightningcraft.init.LCCapabilities;
import sblectric.lightningcraft.items.blocks.ItemBlockLightningCell;
import sblectric.lightningcraft.ref.Log;

@Optional.Interface(iface = "mcp.mobius.waila.api.IWailaDataProvider", modid = "waila")
public class WailaTileHandler implements IWailaDataProvider {
	
	public static final String callbackRegister = "sblectric.lightningcraft.integration.waila.WailaTileHandler.callbackRegister";
	
	/** Perform the registrations */
	@Optional.Method(modid = "waila")
	public static void callbackRegister(IWailaRegistrar register) {
		WailaTileHandler instance = new WailaTileHandler();
		
		// register providers for all the blocks in the mod
		register.registerStackProvider(instance, ILightningCraftBlock.class);
		register.registerHeadProvider(instance, ILightningCraftBlock.class);
		register.registerBodyProvider(instance, ILightningCraftBlock.class);
		register.registerNBTProvider(instance, ILightningCraftBlock.class);
		
		Log.logger.info("Waila integration complete.");
	}
	
	@Override
	@Optional.Method(modid = "waila")
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if(accessor.getBlock() instanceof BlockContainerLC) {
			return accessor.getBlock().getPickBlock(accessor.getBlockState(), accessor.getMOP(), 
					accessor.getWorld(), accessor.getPosition(), accessor.getPlayer());
		}
		return accessor.getStack();
	}

	@Override
	@Optional.Method(modid = "waila")
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "waila")
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		int maxPower = 0;
		if(!(itemStack.getItem() instanceof ItemBlockLightningCell && (maxPower = (int)accessor.getNBTData().getDouble("maxPower")) > 0)) {
			itemStack.getItem().addInformation(itemStack, accessor.getPlayer(), currenttip, false);
		} else {
			currenttip.add("Capacity: " + maxPower + " LE");
		}
		TileEntity t = accessor.getTileEntity();
		if(t != null && t.hasCapability(LCCapabilities.LIGHTNING_UPGRADABLE, null) && 
				t.getCapability(LCCapabilities.LIGHTNING_UPGRADABLE, null).isUpgraded()) {
			currenttip.add("(Upgraded)");
		}
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "waila")
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "waila")
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		if (te != null) te.writeToNBT(tag);
        return tag;
	}

}
