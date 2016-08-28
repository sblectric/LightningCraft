package sblectric.lightningcraft.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.blocks.ifaces.IFurnace;
import sblectric.lightningcraft.recipes.LightningCrusherRecipes;

/** The lightning crusher tile entity */
public class TileEntityLightningCrusher extends TileEntityLightningItemHandler.Upgradable {

	private static final int[] slotsTop = new int[]{0};
	private static final int[] slotsBottom = new int[]{1};
	private static final int[] slotsSides = new int[]{0};

	public static final int burnTime = 160; // time / LE in ticks (two items crush in this period)
	public static final double energyUsage = 5;

	public int crusherBurnTime;
	public int crusherCookTime;
	public int currentBurnTime;
	
	private boolean redo;
	
	/** The lightning crusher tile entity */
	public TileEntityLightningCrusher() {
		stacks = new ItemStack[2]; // only two slots
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

		if(this.worldObj.isRemote) {
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
				IBlockState state = worldObj.getBlockState(pos);
				((IFurnace)state.getBlock()).setBurning(state, worldObj, pos, this.crusherCookTime > 0);
			}

		}

		if (dosave) {
			this.markDirty();
		}
		
		// run update twice a tick on upgrade
		if(isUpgraded && !redo) {
			redo = true;
			update();
		}
		redo = false;
	}

	/** Can the item be crushed? */
	private boolean canCrush() {

		// quick exit
		if(!this.hasLPCell() || !this.canDrawCellPower(energyUsage)) return false;

		if (this.stacks[0] == null) {
			return false;
		} else {
			ItemStack itemstack = LightningCrusherRecipes.instance().getCrushingResult(this.stacks[0]);
			if (itemstack == null) return false;
			if (this.stacks[1] == null) return true;
			if (!this.stacks[1].isItemEqual(itemstack)) return false;
			int result = stacks[1].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.stacks[1].getMaxStackSize();
		}
	}

	/** crush the item */
	private void crushItem() {
		if (this.canCrush()) {
			ItemStack itemstack = LightningCrusherRecipes.instance().getCrushingResult(this.stacks[0]);

			if (this.stacks[1] == null) {
				this.stacks[1] = itemstack.copy();
			} else if (this.stacks[1].getItem() == itemstack.getItem()) {
				this.stacks[1].stackSize += itemstack.stackSize;
			}

			--this.stacks[0].stackSize;

			if(this.stacks[0].stackSize <= 0){
				this.stacks[0] = null;
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return par1 == 1 ? false : true;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing facing) {
		int par1 = facing.getIndex();
		return par1 == 0 ? slotsBottom : (par1 == 1 ? slotsSides : slotsTop);
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack itemstack, EnumFacing facing) {
		return this.isItemValidForSlot(par1, itemstack);
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack itemstack, EnumFacing facing) {
		return facing.getIndex() != 0 || par1 == 1 || itemstack.getItem() == Items.BUCKET;
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
