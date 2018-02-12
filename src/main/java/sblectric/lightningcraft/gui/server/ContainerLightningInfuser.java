package sblectric.lightningcraft.gui.server;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import sblectric.lightningcraft.recipes.LightningInfusionRecipes;
import sblectric.lightningcraft.tiles.TileEntityLightningInfuser;

/** The lightning infusion table container */
public class ContainerLightningInfuser extends ContainerLightningUser.Upgradable {
	
	private TileEntityLightningInfuser tileInfuser;
	public static final int xoff = -14;
	public static final int yoff_inf = 4;
	public static final int yoff_inv = 12;
	
	public ContainerLightningInfuser(InventoryPlayer player, TileEntityLightningInfuser tile) {
		super(player, tile);
		this.tileInfuser = tile;
		
		// infusion slots
		this.addSlotToContainer(new Slot(tile, 0, 48 + xoff, 37 + yoff_inf));
		this.addSlotToContainer(new Slot(tile, 1, 48 + xoff, 17 + yoff_inf));
		this.addSlotToContainer(new Slot(tile, 2, 68 + xoff, 37 + yoff_inf));
		this.addSlotToContainer(new Slot(tile, 3, 48 + xoff, 57 + yoff_inf));
		this.addSlotToContainer(new Slot(tile, 4, 28 + xoff, 37 + yoff_inf));
		this.addSlotToContainer(new SlotFurnaceOutput(player.player, tile, 5, 125 + xoff, 32 + yoff_inf));
		
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
	public void sendInfo(IContainerListener craft) {
		super.sendInfo(craft);
		craft.sendWindowProperty(this, 0, this.tileInfuser.infuserCookTime);
		craft.sendWindowProperty(this, 1, this.tileInfuser.infusionCost);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getInfo(short par1, short par2) {
		super.getInfo(par1, par2);
		if(par1 == 0) this.tileInfuser.infuserCookTime = par2;
		if(par1 == 1) this.tileInfuser.infusionCost = par2;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileInfuser.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(par2);
		
		LinkedList<Integer> INPUT = new LinkedList<Integer>();
		
		int OUTPUT = 5;
		INPUT.addLast(0);
		INPUT.addLast(1);
		INPUT.addLast(2);
		INPUT.addLast(3);
		INPUT.addLast(4);

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
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			// itemstack is in player inventory, try to place in appropriate furnace slot
			else if (par2 != OUTPUT && !INPUT.contains(par2))
			{
				// if it can be infused, place in the input slots
				if(LightningInfusionRecipes.instance().hasBaseResult(itemstack1))
				{
					// try to place in either Input slot; add 1 to final input slot because mergeItemStack uses < index
					if (!this.mergeItemStack(itemstack1, INPUT.get(0), INPUT.get(4)+1, false))
					{
						return ItemStack.EMPTY;
					}
				} else {
					// try to place in either Input slot; add 1 to final input slot because mergeItemStack uses < index
					if (!this.mergeItemStack(itemstack1, INPUT.get(1), INPUT.get(4)+1, false))
					{
						return ItemStack.EMPTY;
					}
				}
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
				return ItemStack.EMPTY;
			}

			slot.onTake(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}
}
