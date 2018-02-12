package sblectric.lightningcraft.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import sblectric.lightningcraft.gui.ShortSender;
import sblectric.lightningcraft.tiles.TileEntityLightningReceiver;

/** The wireless receiver container */
public class ContainerLightningReceiver extends Container {

	private TileEntityLightningReceiver rx;
	private Short lowStored = null;
	private Short highStored = null;
	private Short lowMax = null;
	private Short highMax = null;
	private Short lowX = null;
	private Short highX = null;
	private Short lowZ = null;
	private Short highZ = null;

	public ContainerLightningReceiver(InventoryPlayer player, TileEntityLightningReceiver tile) {
		this.rx = tile;

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
	
	/** Send out the shorts */
	public int sendUpdate(IContainerListener craft, int n) {
		int cellPower = (int)(this.rx.storedPower * 10D);
		int maxPower = (int)this.rx.maxPower;
		int x = rx.txPos.getX(); int z = rx.txPos.getZ();
		craft.sendWindowProperty(this, n++, ShortSender.getLowShort(cellPower));
		craft.sendWindowProperty(this, n++, ShortSender.getHighShort(cellPower));
		craft.sendWindowProperty(this, n++, ShortSender.getLowShort(maxPower));
		craft.sendWindowProperty(this, n++, ShortSender.getHighShort(maxPower));
		craft.sendWindowProperty(this, n++, ShortSender.getLowShort(x));
		craft.sendWindowProperty(this, n++, ShortSender.getHighShort(x));
		craft.sendWindowProperty(this, n++, ShortSender.getLowShort(z));
		craft.sendWindowProperty(this, n++, ShortSender.getHighShort(z));
		return n;
	}
	
	@Override
	public void addListener(IContainerListener craft) {
		super.addListener(craft);
		int n = sendUpdate(craft, 0);
		craft.sendWindowProperty(this, n++, this.rx.txPos.getY());
		craft.sendWindowProperty(this, n++, this.rx.outOfRange ? 1 : 0);
		craft.sendWindowProperty(this, 100, (int)(this.rx.efficiency * 1000D));
	}

	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(int i = 0; i < this.listeners.size(); i++) {
			IContainerListener craft = this.listeners.get(i);
			int n = sendUpdate(craft, 0);
			craft.sendWindowProperty(this, n++, this.rx.txPos.getY());
			craft.sendWindowProperty(this, n++, this.rx.outOfRange ? 1 : 0);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if(par1 == 0) lowStored = (short)par2;
		if(par1 == 1) highStored = (short)par2;
		if(lowStored != null && highStored != null) rx.storedPower = ShortSender.getInt(lowStored, highStored) / 10D;
		if(par1 == 2) lowMax = (short)par2;
		if(par1 == 3) highMax = (short)par2;
		if(lowMax != null && highMax != null) rx.maxPower = ShortSender.getInt(lowMax, highMax);
		if(par1 == 4) lowX = (short)par2;
		if(par1 == 5) highX = (short)par2;
		if(lowX != null && highX != null) rx.txPos = new BlockPos(ShortSender.getInt(lowX, highX), rx.txPos.getY(), rx.txPos.getZ());
		if(par1 == 6) lowZ = (short)par2;
		if(par1 == 7) highZ = (short)par2;
		if(lowZ != null && highZ != null) rx.txPos = new BlockPos(rx.txPos.getX(), rx.txPos.getY(), ShortSender.getInt(lowZ, highZ));
		if(par1 == 8) rx.txPos = new BlockPos(rx.txPos.getX(), par2, rx.txPos.getZ());
		if(par1 == 9) rx.outOfRange = par2 == 1;
		if(par1 == 100) rx.efficiency = par2 / 1000D;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.rx.isUseableByPlayer(player);
	}
	
	/**
	* Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	*/
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(par2);

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
						return ItemStack.EMPTY;
					}
				}
				// item in action bar - place in player inventory
				else if (par2 >= INPUT+28 && par2 < INPUT+37 && !this.mergeItemStack(itemstack1, INPUT+1, INPUT+28, false))
				{
					return ItemStack.EMPTY;
				}
			}

			if (itemstack1.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}

}
