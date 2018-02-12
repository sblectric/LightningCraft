package sblectric.lightningcraft.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.IFurnace;

/** The lightning furnace tile entity */
public class TileEntityLightningFurnace extends TileEntityLightningItemHandler.Upgradable {
	
	private static final int top = 0;
	private static final int bottom = 1;

	private static final int[] slotsTop = new int[]{top};
	private static final int[] slotsBottom = new int[]{bottom};
	private static final int[] slotsSides = new int[]{top};

	private static final int burnTime = 80; // time / LE in ticks (two items cook in this period)

	public int furnaceBurnTime;
	public int furnaceCookTime;
	public int currentBurnTime;
	
	private boolean redo;
	
	/** The lightning furnace tile entity */
	public TileEntityLightningFurnace() {
		setSizeInventory(2); // only two slots
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.furnaceCookTime * par1 / (burnTime / 2);
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentBurnTime == 0) {
			this.currentBurnTime = (burnTime / 2);
		}

		return this.furnaceBurnTime * par1 / this.currentBurnTime;
	}

	public boolean isBurning() {
		return this.furnaceBurnTime > 0;
	}

	/** Furnace update */
	@Override
	public void update() {
		boolean burning = this.furnaceBurnTime > 0;
		boolean dosave = false;

		// burn time countdown
		if(this.furnaceBurnTime > 0) {
			--this.furnaceBurnTime;
		}

		if(this.world.isRemote) {
			// nothing really clientside here
		} else {
			// stoke the flames
			if (this.furnaceBurnTime == 0 && this.canSmelt()) {

				this.currentBurnTime = this.furnaceBurnTime = burnTime; // 2 second / LE default

				if (this.furnaceBurnTime > 0) {
					dosave = true;
					this.drawCellPower(1);
				}
			}

			// cook time countdown
			if (this.isBurning() && this.canSmelt()) {
				++this.furnaceCookTime;
				if (this.furnaceCookTime == burnTime / 2) { // 1 second / item default
					this.furnaceCookTime = 0;
					this.smeltItem();
					dosave = true;
				}
			} else {
				this.furnaceCookTime = 0;
			}

			// Update the burning state
			if (burning != this.furnaceBurnTime > 0) {
				dosave = true;
				IBlockState state = world.getBlockState(pos);
				((IFurnace)state.getBlock()).setBurning(state, world, pos, this.furnaceBurnTime > 0);
			}

		}

		if (dosave) {
			this.markDirty();
		}
		
		// run update twice a tick on upgrade
		if(isUpgraded() && !redo) {
			redo = true;
			update();
		}
		redo = false;
	}

	/** Can the item be smelted? */
	private boolean canSmelt() {

		// quick exit
		if(!this.hasLPCell() || !this.canDrawCellPower(1)) return false;

		if (this.getStack(0).isEmpty()) {
			return false;
		} else {
			ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.getStack(0));
			if (itemstack.isEmpty()) return false;
			if (this.getStack(1).isEmpty()) return true;
			if (!this.getStack(1).isItemEqual(itemstack)) return false;
			int result = getStack(1).getCount() + itemstack.getCount();
			return result <= getInventoryStackLimit() && result <= this.getStack(1).getMaxStackSize();
		}
	}

	/** Smelt the item */
	private void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.getStack(0));

			if (this.getStack(1).isEmpty()) {
				this.setStack(1, itemstack.copy());
			} else if (this.getStack(1).getItem() == itemstack.getItem()) {
				this.getStack(1).setCount(this.getStack(1).getCount() + itemstack.getCount());
			}

			this.getStack(0).setCount(this.getStack(0).getCount() - 1);

			if(this.getStack(0).getCount() <= 0) {
				this.setStack(0, ItemStack.EMPTY);
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return slot != bottom;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing facing) {
		return facing == EnumFacing.UP ? slotsTop : (facing == EnumFacing.DOWN ? slotsBottom : slotsSides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, EnumFacing facing) {
		return this.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, EnumFacing facing) {
		return slot == bottom;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		this.furnaceBurnTime = tagCompound.getShort("BurnTime");
		this.furnaceCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = burnTime;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setShort("BurnTime", (short) this.furnaceBurnTime);
		tagCompound.setShort("CookTime", (short) this.furnaceBurnTime);

		return tagCompound;
	}

}
