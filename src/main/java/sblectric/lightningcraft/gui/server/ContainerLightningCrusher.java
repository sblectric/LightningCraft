package sblectric.lightningcraft.gui.server;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.recipes.LightningCrusherRecipes;
import sblectric.lightningcraft.tiles.TileEntityLightningCrusher;

/** The lightning crusher container */
public class ContainerLightningCrusher extends ContainerLightningUser.Upgradable {
	
	private TileEntityLightningCrusher tileCrusher;
	
	public ContainerLightningCrusher(InventoryPlayer player, TileEntityLightningCrusher tile) {
		super(player, tile);
		this.tileCrusher = tile;
		
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
	public void sendInfo(IContainerListener craft) {
		super.sendInfo(craft);
		craft.sendWindowProperty(this, 0, this.tileCrusher.crusherCookTime);
		craft.sendWindowProperty(this, 1, this.tileCrusher.crusherBurnTime);
		craft.sendWindowProperty(this, 2, this.tileCrusher.currentBurnTime);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getInfo(short par1, short par2) {
		super.getInfo(par1, par2);
		if(par1 == 0) this.tileCrusher.crusherCookTime = par2;
		if(par1 == 1) this.tileCrusher.crusherBurnTime = par2;
		if(par1 == 2) this.tileCrusher.currentBurnTime = par2;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileCrusher.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(par2);

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
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			// itemstack is in player inventory, try to place in appropriate furnace slot
			else if (par2 != OUTPUT && par2 != INPUT)
			{
				// if it can be crushed, place in the input slots
				if (LightningCrusherRecipes.instance().getCrushingResult(itemstack1) != null)
				{
					// try to place in either Input slot; add 1 to final input slot because mergeItemStack uses < index
					if (!this.mergeItemStack(itemstack1, INPUT, INPUT+1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				// item in player's inventory, but not in action bar
				else if (par2 >= OUTPUT+1 && par2 < OUTPUT+28)
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
