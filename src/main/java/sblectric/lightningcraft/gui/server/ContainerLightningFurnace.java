package sblectric.lightningcraft.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import sblectric.lightningcraft.tiles.TileEntityLightningFurnace;

/** The lightning furnace container */
public class ContainerLightningFurnace extends ContainerLightningUser {
	
	private TileEntityLightningFurnace tileFurnace;
	
	public ContainerLightningFurnace(InventoryPlayer player, TileEntityLightningFurnace tile) {
		super(player, tile);
		this.tileFurnace = tile;
		
		// furnace slots
		this.addSlotToContainer(new Slot(tile, 0, 56, 17));
		this.addSlotToContainer(new SlotFurnaceOutput(player.player, tile, 1, 116, 35));
		
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
	
	@Override
	public void addListener(IContainerListener craft) {
		super.addListener(craft);
		craft.sendProgressBarUpdate(this, 0, this.tileFurnace.furnaceCookTime);
		craft.sendProgressBarUpdate(this, 1, this.tileFurnace.furnaceBurnTime);
		craft.sendProgressBarUpdate(this, 2, this.tileFurnace.currentBurnTime);
	}
	
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener craft = (IContainerListener) this.listeners.get(i);
			craft.sendProgressBarUpdate(this, 0, this.tileFurnace.furnaceCookTime);
			craft.sendProgressBarUpdate(this, 1, this.tileFurnace.furnaceBurnTime);
			craft.sendProgressBarUpdate(this, 2, this.tileFurnace.currentBurnTime);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2){
		super.updateProgressBar(par1, par2);
		if(par1 == 0){
			this.tileFurnace.furnaceCookTime = par2;
		}
		if(par1 == 1){
			this.tileFurnace.furnaceBurnTime = par2;
		}
		if(par1 == 2){
			this.tileFurnace.currentBurnTime = par2;
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileFurnace.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		int OUTPUT = 1;
		int INPUT = 0;

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// If itemstack is in Output stack
			if (par2 == OUTPUT)
			{
				// try to place in player inventory / action bar; add 36+1 because mergeItemStack uses < index,
				// so the last slot in the inventory won't get checked if you don't add 1
				if (!this.mergeItemStack(itemstack1, OUTPUT+1, OUTPUT+36+1, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			// itemstack is in player inventory, try to place in appropriate furnace slot
			else if (par2 != OUTPUT && par2 != INPUT)
			{
				// if it can be smelted, place in the input slots
				if (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null)
				{
					// try to place in either Input slot; add 1 to final input slot because mergeItemStack uses < index
					if (!this.mergeItemStack(itemstack1, INPUT, INPUT+1, false))
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
