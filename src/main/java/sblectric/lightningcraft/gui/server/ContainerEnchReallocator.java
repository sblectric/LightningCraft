package sblectric.lightningcraft.gui.server;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import sblectric.lightningcraft.tiles.TileEntityEnchReallocator;
import sblectric.lightningcraft.util.LCMisc;

/** The enchantment reallocation station */
public class ContainerEnchReallocator extends ContainerLightningUser {
	
	private TileEntityEnchReallocator tileRealloc;
	
	public ContainerEnchReallocator(InventoryPlayer player, TileEntityEnchReallocator tile) {
		super(player, tile);
		this.tileRealloc = tile;
		
		int yoff_inv = 32;
		
		// reallocator slots
		this.addSlotToContainer(new Slot(tile, 0, 35, 17));
		this.addSlotToContainer(new Slot(tile, 1, 35, 53));
		
		// player inventory placement
		int i;
		
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
	public void addListener(IContainerListener craft) {
		super.addListener(craft);
		craft.sendProgressBarUpdate(this, 0, this.tileRealloc.reallocCookTime);
		craft.sendProgressBarUpdate(this, 1, this.tileRealloc.lpCost);
		craft.sendProgressBarUpdate(this, 2, this.tileRealloc.xpCost);
		craft.sendProgressBarUpdate(this, 3, this.tileRealloc.nTopEnchs);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for(int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener craft = this.listeners.get(i);
			craft.sendProgressBarUpdate(this, 0, this.tileRealloc.reallocCookTime);
			craft.sendProgressBarUpdate(this, 1, this.tileRealloc.lpCost);
			craft.sendProgressBarUpdate(this, 2, this.tileRealloc.xpCost);
			craft.sendProgressBarUpdate(this, 3, this.tileRealloc.nTopEnchs);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2){
		super.updateProgressBar(par1, par2);
		if(par1 == 0){
			this.tileRealloc.reallocCookTime = par2;
		}
		if(par1 == 1){
			this.tileRealloc.lpCost = par2;
		}
		if(par1 == 2){
			this.tileRealloc.xpCost = par2;
		}
		if(par1 == 3) {
			this.tileRealloc.nTopEnchs = par2;
		}		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileRealloc.isUseableByPlayer(player);
	}
	
	/** serverside XP deduction */
	@Override
	public boolean enchantItem(EntityPlayer player, int action) {
		this.tileRealloc.xpPlayer = player.experienceLevel;
		if(action > 0) {
			this.tileRealloc.player = player;
			this.tileRealloc.hasPlayer = true;
		} else if(action < 0) {
			this.tileRealloc.hasPlayer = false;
		}
		return true;
	}
	
	/**
	* Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	*/
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(par2);
		
		LinkedList<Integer> INPUT = new LinkedList<Integer>();
		
		int OUTPUT = 1; // no output
		INPUT.addLast(0);
		INPUT.addLast(1);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// itemstack is in player inventory, try to place in appropriate furnace slot
			if (!INPUT.contains(par2))
			{
				
				if(!LCMisc.getEnchantments(itemstack1).isEmpty()) {
					// try to place in either Input slot; add 1 to final input slot because mergeItemStack uses < index
					if (!this.mergeItemStack(itemstack1, INPUT.get(0), INPUT.get(0)+1, false))
					{
						return null;
					}
				}
				
				else if(LCMisc.getEnchantments(itemstack1).isEmpty() && (itemstack1.isItemEnchantable() || itemstack1.getItem() == Items.BOOK)) {
					// try to place in either Input slot; add 1 to final input slot because mergeItemStack uses < index
					if (!this.mergeItemStack(itemstack1, INPUT.get(1), INPUT.get(1)+1, false))
					{
						return null;
					}
				}
				
				// item in player's inventory, but not in action bar
				else if (par2 >= OUTPUT+1 && par2 < OUTPUT+28)
				{
					// place in action bar
					if (!this.mergeItemStack(itemstack1, OUTPUT+28, OUTPUT+37, false))
					{
						return null;
					}
				}
				// item in action bar - place in player inventory
				else if (par2 >= OUTPUT+28 && par2 < OUTPUT+37 && !this.mergeItemStack(itemstack1, OUTPUT+1, OUTPUT+28, false))
				{
					return null;
				}
			}
			// In one of the infuser slots; try to place in player inventory / action bar
			else if (!this.mergeItemStack(itemstack1, OUTPUT+1, OUTPUT+37, false))
			{
				return null;
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
