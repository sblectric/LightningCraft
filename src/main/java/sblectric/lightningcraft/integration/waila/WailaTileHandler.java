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
import sblectric.lightningcraft.blocks.BlockContainerLC;
import sblectric.lightningcraft.items.blocks.ItemBlockLightningCell;
import sblectric.lightningcraft.ref.Log;
import sblectric.lightningcraft.registry.IRegistryBlock;
import sblectric.lightningcraft.tiles.ifaces.ILightningUpgradable;

@Optional.Interface(iface = "mcp.mobius.waila.api.IWailaDataProvider", modid = "Waila")
public class WailaTileHandler implements IWailaDataProvider {
	
	public static final String callbackRegister = "sblectric.lightningcraft.integration.waila.WailaTileHandler.callbackRegister";
	
	/** Perform the registrations */
	@Optional.Method(modid = "Waila")
	public static void callbackRegister(IWailaRegistrar register) {
		WailaTileHandler instance = new WailaTileHandler();
		
		// register providers for all the blocks in the mod
		register.registerStackProvider(instance, IRegistryBlock.class);
		register.registerHeadProvider(instance, IRegistryBlock.class);
		register.registerBodyProvider(instance, IRegistryBlock.class);
		register.registerNBTProvider(instance, IRegistryBlock.class);
		
		Log.logger.info("Waila integration complete.");
	}
	
	@Override
	@Optional.Method(modid = "Waila")
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if(accessor.getBlock() instanceof BlockContainerLC) {
			return accessor.getBlock().getPickBlock(accessor.getBlockState(), accessor.getMOP(), 
					accessor.getWorld(), accessor.getPosition(), accessor.getPlayer());
		}
		return accessor.getStack();
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		int maxPower = 0;
		if(!(itemStack.getItem() instanceof ItemBlockLightningCell && (maxPower = (int)accessor.getNBTData().getDouble("maxPower")) > 0)) {
			itemStack.getItem().addInformation(itemStack, accessor.getPlayer(), currenttip, false);
		} else {
			currenttip.add("Capacity: " + maxPower + " LE");
		}
		TileEntity t;
		if((t = accessor.getTileEntity()) != null && t instanceof ILightningUpgradable && ((ILightningUpgradable)t).isUpgraded()) {
			currenttip.add("(Upgraded)");
		}
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		if (te != null) te.writeToNBT(tag);
        return tag;
	}

}
