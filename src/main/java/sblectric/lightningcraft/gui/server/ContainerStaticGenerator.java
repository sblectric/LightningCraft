package sblectric.lightningcraft.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import sblectric.lightningcraft.tiles.TileEntityStaticGenerator;

/** The lightning static generator container */
public class ContainerStaticGenerator extends ContainerLightningUser.Upgradable {
	
	private TileEntityStaticGenerator tileGenerator;
	
	public ContainerStaticGenerator(InventoryPlayer player, TileEntityStaticGenerator tile) {
		super(player, tile);
		this.tileGenerator = tile;
		
		// generator slot
		this.addSlotToContainer(new Slot(tile, 0, 56, 17));
		
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
	public void sendInfo(IContainerListener craft) {
		super.sendInfo(craft);
		craft.sendProgressBarUpdate(this, 0, this.tileGenerator.generatorCookTime);
		craft.sendProgressBarUpdate(this, 1, this.tileGenerator.generatorBurnTime);
		craft.sendProgressBarUpdate(this, 2, this.tileGenerator.currentBurnTime);
		craft.sendProgressBarUpdate(this, 3, (int)(this.tileGenerator.storedCharge * 10F));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getInfo(short par1, short par2) {
		super.getInfo(par1, par2);
		if(par1 == 0) this.tileGenerator.generatorCookTime = par2;
		if(par1 == 1) this.tileGenerator.generatorBurnTime = par2;
		if(par1 == 2) this.tileGenerator.currentBurnTime = par2;
		if(par1 == 3) this.tileGenerator.storedCharge = par2 / 10D;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileGenerator.isUseableByPlayer(player);
	}
	
	/**
	* Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	*/
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(par2);

		int INPUT = 0;

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// itemstack is in player inventory, try to place in slot
			if (par2 != INPUT)
			{
				// if it's a block, place in the input slot
				if (itemstack.getItem() instanceof ItemBlock)
				{
					// try to place in either Input slot; add 1 to final input slot because mergeItemStack uses < index
					if (!this.mergeItemStack(itemstack1, INPUT, INPUT+1, false))
					{
						return null;
					}
				}
				// item in player's inventory, but not in action bar
				else if (par2 >= INPUT+1 && par2 < INPUT+28)
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
			// In one of the infuser slots; try to place in player inventory / action bar
			else if (!this.mergeItemStack(itemstack1, INPUT+1, INPUT+37, false))
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
