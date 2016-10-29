package sblectric.lightningcraft.gui.server;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.gui.ShortSender;
import sblectric.lightningcraft.init.LCCapabilities;
import sblectric.lightningcraft.tiles.TileEntityLightningUser;

/** The superclass for all lightning user containers */
public abstract class ContainerLightningUser extends Container {
	
	private TileEntityLightningUser tile;
	private Short lowStored = null;
	private Short highStored = null;
	private Short lowMax = null;
	private Short highMax = null;
	
	protected ContainerLightningUser(InventoryPlayer player, TileEntityLightningUser tile) {
		this.tile = tile;
	}
	
	/** Send out the primary shorts */
	public final int sendUpdate(IContainerListener craft, int n) {
		int cellPower = (int)(this.tile.cellPower * 10D);
		int maxPower = (int)this.tile.maxPower;
		craft.sendProgressBarUpdate(this, n++, ShortSender.getLowShort(cellPower));
		craft.sendProgressBarUpdate(this, n++, ShortSender.getHighShort(cellPower));
		craft.sendProgressBarUpdate(this, n++, ShortSender.getLowShort(maxPower));
		craft.sendProgressBarUpdate(this, n++, ShortSender.getHighShort(maxPower));
		craft.sendProgressBarUpdate(this, n++, (int)(this.tile.getEfficiency() * 1000D));
		return n;
	}
	
	/** Send extra shorts to the client */
	public abstract void sendInfo(IContainerListener craft);
	
	/** Get extra shorts that were sent with sendInfo */
	@SideOnly(Side.CLIENT)
	public abstract void getInfo(short short1, short short2);

	@Override
	public final void addListener(IContainerListener craft) {
		super.addListener(craft);
		sendUpdate(craft, 1000);
		sendInfo(craft);
	}

	@Override
	public final void detectAndSendChanges() {
		super.detectAndSendChanges();
		for(int i = 0; i < this.listeners.size(); i++) {
			IContainerListener craft = this.listeners.get(i);
			sendUpdate(craft, 1000);
			sendInfo(craft);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final void updateProgressBar(int par1, int par2) {
		if(par1 == 1000) lowStored = (short)par2;
		if(par1 == 1001) highStored = (short)par2;
		if(lowStored != null && highStored != null) tile.cellPower = ShortSender.getInt(lowStored, highStored) / 10D;
		if(par1 == 1002) lowMax = (short)par2;
		if(par1 == 1003) highMax = (short)par2;
		if(lowMax != null && highMax != null) tile.maxPower = ShortSender.getInt(lowMax, highMax);
		if(par1 == 1004) {
			this.tile.setEfficiency(par2 / 1000D);
		}
		getInfo((short)par1, (short)par2);
	}
	
	/** Upgradable Lightning tile container. TILE MUST HAVE THE LIGHTNING UPGRADABLE CAPABILITY! */
	public static abstract class Upgradable extends ContainerLightningUser {
		
		private TileEntityLightningUser tile;
		
		protected Upgradable(InventoryPlayer player, TileEntityLightningUser tile) {
			super(player, tile);
			this.tile = tile;
		}

		@Override
		public void sendInfo(IContainerListener craft) {
			craft.sendProgressBarUpdate(this, 1500, tile.getCapability(LCCapabilities.LIGHTNING_UPGRADABLE, null).isUpgraded() ? 1 : 0);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void getInfo(short short1, short short2) {
			if(short1 == 1500) tile.getCapability(LCCapabilities.LIGHTNING_UPGRADABLE, null).setUpgraded(short2 > 0);
		}
		
	}
	
}
