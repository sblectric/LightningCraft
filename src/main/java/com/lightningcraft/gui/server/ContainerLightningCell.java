package com.lightningcraft.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.gui.ShortSender;
import com.lightningcraft.tiles.TileEntityLightningCell;

/** The energy cell container */
public class ContainerLightningCell extends Container {
	
	private TileEntityLightningCell tileLPCell;
	private Short lowStored = null;
	private Short highStored = null;
	private Short lowMax = null;
	private Short highMax = null;
	
	public ContainerLightningCell(InventoryPlayer player, TileEntityLightningCell tile) {
		this.tileLPCell = tile;
		
		// player inventory placement
		int i;
		
		for(i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
	}
	
	/** Send out the shorts */
	public int sendUpdate(IContainerListener craft, int n) {
		int cellPower = (int)(this.tileLPCell.storedPower * 10D);
		int maxPower = (int)this.tileLPCell.maxPower;
		craft.sendProgressBarUpdate(this, n++, ShortSender.getLowShort(cellPower));
		craft.sendProgressBarUpdate(this, n++, ShortSender.getHighShort(cellPower));
		craft.sendProgressBarUpdate(this, n++, ShortSender.getLowShort(maxPower));
		craft.sendProgressBarUpdate(this, n++, ShortSender.getHighShort(maxPower));
		return n;
	}
	
	@Override
	public void addListener(IContainerListener craft) {
		super.addListener(craft);
		sendUpdate(craft, 0);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for(int i = 0; i < this.listeners.size(); i++) {
			IContainerListener craft = (IContainerListener) this.listeners.get(i);
			sendUpdate(craft, 0);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if(par1 == 0) lowStored = (short)par2;
		if(par1 == 1) highStored = (short)par2;
		if(lowStored != null && highStored != null) tileLPCell.storedPower = ShortSender.getInt(lowStored, highStored) / 10D;
		if(par1 == 2) lowMax = (short)par2;
		if(par1 == 3) highMax = (short)par2;
		if(lowMax != null && highMax != null) tileLPCell.maxPower = ShortSender.getInt(lowMax, highMax);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileLPCell.isUseableByPlayer(player);
	}
	
	/**
	* Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	*/
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		int INPUT = -1;

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// itemstack is in player inventory, try to place in slot
			if (par2 != INPUT)
			{
				// item in player's inventory, but not in action bar
				if (par2 >= INPUT+1 && par2 < INPUT+28)
				{
					// place in action bar
					if (!this.mergeItemStack(itemstack1, INPUT+28, INPUT+37, false))
					{
						return null;
					}
				}
				// item in action bar - place in player inventory
				else if (par2 >= INPUT+28 && par2 < INPUT+37 && !this.mergeItemStack(itemstack1, INPUT+1, INPUT+28, false))
				{
					return null;
				}
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}

}
