package sblectric.lightningcraft.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.tiles.TileEntityLightningMiner;
import sblectric.lightningcraft.tiles.TileEntityLightningMiner.EnumMode;
import sblectric.lightningcraft.util.IntList;

/** The lightning miner: 9 inventory slots */
public class ContainerLightningMiner extends ContainerLightningUser.Upgradable {

	private TileEntityLightningMiner tile;

	public ContainerLightningMiner(InventoryPlayer player, TileEntityLightningMiner tile) {
		super(player, tile);
		this.tile = tile;

		int yoff_inv = 32;
		int i;

		// reallocator slots
		for(i = 0; i < TileEntityLightningMiner.nStacks; ++i) {
			this.addSlotToContainer(new Slot(tile, i, 8 + i * 18, 20));
		}

		// player inventory placement		
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
			tile.rotateOperatingMode();
			break;
		case 1: // toggle block replacement
			tile.toggleBlockReplacement();
			break;
		}
		return true;
	}

	@Override
	public void sendInfo(IContainerListener craft) {
		super.sendInfo(craft);
		craft.sendProgressBarUpdate(this, 0, this.tile.mode.getID());
		craft.sendProgressBarUpdate(this, 1, this.tile.replaceBlocks ? 1 : 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getInfo(short par1, short par2) {
		super.getInfo(par1, par2);
		if(par1 == 0) this.tile.mode = EnumMode.assignMode(par2);
		if(par1 == 1) this.tile.replaceBlocks = par2 > 0;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUseableByPlayer(player);
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(par2);

		IntList INPUT = new IntList();
		for(int i : TileEntityLightningMiner.stacksInt) INPUT.add(i);
		int OUTPUT = INPUT.getLast();

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// itemstack is in player inventory, try to place in appropriate furnace slot
			if (!INPUT.contains(par2))
			{
				// try to place in an input slot; add 1 to final input slot because mergeItemStack uses < index
				if (!this.mergeItemStack(itemstack1, INPUT.getFirst(), INPUT.getLast() + 1, false))
				{
					return ItemStack.EMPTY;
				}
				
				slot.onSlotChange(itemstack1, itemstack);
			}

			// item in player's inventory, but not in action bar
			else if(par2 >= OUTPUT+1 && par2 < OUTPUT+28)
			{
				// place in action bar
				if (!this.mergeItemStack(itemstack1, OUTPUT+28, OUTPUT+37, false))
				{
					return ItemStack.EMPTY;
				}
			}
			// item in action bar - place in player inventory
			else if (par2 >= OUTPUT+28 && par2 < OUTPUT+37 && !this.mergeItemStack(itemstack1, OUTPUT+1, OUTPUT+28, false))
			{
				return ItemStack.EMPTY;
			}

			// In one of the infuser slots; try to place in player inventory / action bar
			else if (!this.mergeItemStack(itemstack1, OUTPUT+1, OUTPUT+37, false))
			{
				return ItemStack.EMPTY;
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
				return null;
			}

			slot.onTake(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}
}
