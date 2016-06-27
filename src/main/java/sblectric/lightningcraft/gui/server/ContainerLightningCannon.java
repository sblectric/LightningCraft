package sblectric.lightningcraft.gui.server;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import sblectric.lightningcraft.tiles.TileEntityLightningCannon;
import sblectric.lightningcraft.tiles.TileEntityLightningCannon.EnumMode;

/** The Lightning Cannon container */
public class ContainerLightningCannon extends ContainerLightningUser {

	private TileEntityLightningCannon tileCannon;

	public ContainerLightningCannon(InventoryPlayer player, TileEntityLightningCannon tile) {
		super(player, tile);
		this.tileCannon = tile;

		// player inventory placement
		int i;
		int yoff_inv = 32;

		for(i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + yoff_inv));
			}
		}
		
		for(i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142 + yoff_inv));
		}
	}
	
	@Override
	public boolean enchantItem(EntityPlayer player, int action) {
		switch(action) {
		case 0: // change operating mode
			tileCannon.rotateOperatingMode();
		}
		return true;
	}

	@Override
	public void addListener(IContainerListener craft) {
		super.addListener(craft);
		craft.sendProgressBarUpdate(this, 0, this.tileCannon.mode.getID());
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for(int i = 0; i < this.listeners.size(); i++) {
			IContainerListener craft = (IContainerListener) this.listeners.get(i);
			craft.sendProgressBarUpdate(this, 0, this.tileCannon.mode.getID());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		super.updateProgressBar(par1, par2);
		if(par1 == 0) {
			this.tileCannon.mode = EnumMode.assignMode(par2);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileCannon.isUseableByPlayer(player);
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
