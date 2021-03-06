package sblectric.lightningcraft.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.IFurnace;
import sblectric.lightningcraft.recipes.LightningCrusherRecipes;

/** The lightning crusher tile entity */
public class TileEntityLightningCrusher extends TileEntityLightningItemHandler.Upgradable {
	
	private static final int top = 0;
	private static final int bottom = 1;

	private static final int[] slotsTop = new int[]{top};
	private static final int[] slotsBottom = new int[]{bottom};
	private static final int[] slotsSides = new int[]{top};

	public static final int burnTime = 160; // time / LE in ticks (two items crush in this period)
	public static final double energyUsage = 5;

	public int crusherBurnTime;
	public int crusherCookTime;
	public int currentBurnTime;
	
	private boolean redo;
	
	/** The lightning crusher tile entity */
	public TileEntityLightningCrusher() {
		setSizeInventory(2); // only two slots
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.crusherCookTime * par1 / (burnTime / 2);
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentBurnTime == 0) {
			this.currentBurnTime = (burnTime / 2);
		}

		return this.crusherBurnTime * par1 / this.currentBurnTime;
	}

	public boolean isBurning() {
		return this.crusherBurnTime > 0;
	}

	/** Crusher update */
	@Override
	public void update() {
		boolean burning = this.crusherCookTime > 0;
		boolean dosave = false;

		// burn time countdown
		if(this.crusherBurnTime > 0) {
			--this.crusherBurnTime;
		}

		if(this.world.isRemote) {
			// nothing really clientside here
		} else {
			// stoke the flames
			if (this.crusherBurnTime == 0 && this.canCrush()) {

				this.currentBurnTime = this.crusherBurnTime = burnTime; // 2 second / LE default

				if (this.crusherBurnTime > 0) {
					dosave = true;
					this.drawCellPower(energyUsage);
				}
			}

			// cook time countdown
			if (this.isBurning() && this.canCrush()) {
				++this.crusherCookTime;
				if (this.crusherCookTime == burnTime / 2) { // 1s / item default
					this.crusherCookTime = 0;
					this.crushItem();
					dosave = true;
				}
			} else {
				this.crusherCookTime = 0;
			}

			// Update the burning state
			if (burning != this.crusherCookTime > 0) {
				dosave = true;
				IBlockState state = world.getBlockState(pos);
				((IFurnace)state.getBlock()).setBurning(state, world, pos, this.crusherCookTime > 0);
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

	/** Can the item be crushed? */
	private boolean canCrush() {

		// quick exit
		if(!this.hasLPCell() || !this.canDrawCellPower(energyUsage)) return false;

		if (this.getStack(0).isEmpty()) {
			return false;
		} else {
			ItemStack itemstack = LightningCrusherRecipes.instance().getCrushingResult(this.getStack(0));
			if (itemstack.isEmpty()) return false;
			if (this.getStack(1).isEmpty()) return true;
			if (!this.getStack(1).isItemEqual(itemstack)) return false;
			int result = getStack(1).getCount() + itemstack.getCount();
			return result <= getInventoryStackLimit() && result <= this.getStack(1).getMaxStackSize();
		}
	}

	/** crush the item */
	private void crushItem() {
		if (this.canCrush()) {
			ItemStack itemstack = LightningCrusherRecipes.instance().getCrushingResult(this.getStack(0));

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
		return facing == EnumFacing.DOWN ? slotsBottom : (facing == EnumFacing.UP ? slotsTop : slotsSides);
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

		this.crusherBurnTime = tagCompound.getShort("BurnTime");
		this.crusherCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = burnTime;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setShort("BurnTime", (short) this.crusherBurnTime);
		tagCompound.setShort("CookTime", (short) this.crusherBurnTime);
		
		return tagCompound;
	}

}
