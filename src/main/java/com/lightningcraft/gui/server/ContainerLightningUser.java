package com.lightningcraft.gui.server;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.gui.ShortSender;
import com.lightningcraft.tiles.TileEntityLightningUser;

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
	
	/** Send out the shorts */
	public int sendUpdate(IContainerListener craft, int n) {
		int cellPower = (int)(this.tile.cellPower * 10D);
		int maxPower = (int)this.tile.maxPower;
		craft.sendProgressBarUpdate(this, n++, ShortSender.getLowShort(cellPower));
		craft.sendProgressBarUpdate(this, n++, ShortSender.getHighShort(cellPower));
		craft.sendProgressBarUpdate(this, n++, ShortSender.getLowShort(maxPower));
		craft.sendProgressBarUpdate(this, n++, ShortSender.getHighShort(maxPower));
		return n;
	}

	@Override
	public void addListener(IContainerListener craft) {
		super.addListener(craft);
		int n = sendUpdate(craft, 1000);
		craft.sendProgressBarUpdate(this, n++, (int)(this.tile.getEfficiency() * 1000D));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for(int i = 0; i < this.listeners.size(); i++) {
			IContainerListener craft = (IContainerListener) this.listeners.get(i);
			int n = sendUpdate(craft, 1000);
			craft.sendProgressBarUpdate(this, n++, (int)(this.tile.getEfficiency() * 1000D));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if(par1 == 1000) lowStored = (short)par2;
		if(par1 == 1001) highStored = (short)par2;
		if(lowStored != null && highStored != null) tile.cellPower = ShortSender.getInt(lowStored, highStored) / 10D;
		if(par1 == 1002) lowMax = (short)par2;
		if(par1 == 1003) highMax = (short)par2;
		if(lowMax != null && highMax != null) tile.maxPower = ShortSender.getInt(lowMax, highMax);
		if(par1 == 1004) {
			this.tile.setEfficiency(par2 / 1000D);
		}
	}
	
}
