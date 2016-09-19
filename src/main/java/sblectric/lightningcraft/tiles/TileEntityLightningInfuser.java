package sblectric.lightningcraft.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.blocks.ifaces.IFurnace;
import sblectric.lightningcraft.recipes.LightningInfusionRecipes;

/** The lightning infusion table tile entity */
public class TileEntityLightningInfuser extends TileEntityLightningItemHandler.Upgradable {
	
	private static final int[] slotsTop = new int[]{0};
	private static final int[] slotsBottom = new int[]{5};
	private static final int[] slotsSides = new int[]{1, 2, 3, 4};
	
	public static final int burnTime = 600; // time / LE in ticks (one recipe "cooks" in this period)
	
	public int infuserBurnTime;
	public int infuserCookTime;
	public int currentBurnTime;
	public int infusionCost;
	
	private boolean redo;
	
	/** The lightning infusion table tile entity */
	public TileEntityLightningInfuser() {
		stacks = new ItemStack[6]; // six slots
	}

	@SideOnly(Side.CLIENT)
	public int getInfusionProgressScaled(int par1) {
		return this.infuserCookTime * par1 / burnTime;
	}

	@SideOnly(Side.CLIENT)
	public int getInfusionTimeRemainingScaled(int par1) {
		if (this.currentBurnTime == 0) {
			this.currentBurnTime = burnTime;
		}

		return this.infuserBurnTime * par1 / this.currentBurnTime;
	}

	public boolean isBurning() {
		return this.infuserBurnTime > 0;
	}

	/** Infuser update */
	@Override
	public void update() {
		boolean burning = this.infuserCookTime > 0;
		boolean dosave = false;

		// burn countdown
		if (this.infuserBurnTime > 0) {
			--this.infuserBurnTime;
		}
		
		if (this.worldObj.isRemote) {
			// nothing client-only
		} else {
			if (this.infuserBurnTime == 0 && this.canInfuse()) {
				this.currentBurnTime = this.infuserBurnTime = burnTime;

				if (this.infuserBurnTime > 0) {
					dosave = true;
				}
			}
			
			// infusion time countdown
			if (this.isBurning() && this.canInfuse()) {
				++this.infuserCookTime;
				
				if (this.infuserCookTime == burnTime) { // 1 unit / item default
					this.infuserCookTime = 0;
					this.infuseItem();
					dosave = true;
				}
			} else {
				this.infuserCookTime = 0;
			}
			
			// Update the burning state
			if (burning != this.infuserCookTime > 0) {
				dosave = true;
				IBlockState state = worldObj.getBlockState(pos);
				((IFurnace)state.getBlock()).setBurning(state, worldObj, pos, this.infuserCookTime > 0);
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

	/** Can the item be infused? */
	private boolean canInfuse() {
		
		// quick exit
		this.infusionCost = 0;
		if(!this.hasLPCell()) return false;
		
		if (this.stacks[0] == null) {
			return false;
		} else {
			ItemStack itemstack = LightningInfusionRecipes.instance().getInfusionResult(this.stacks[0], 
					this.stacks[1], this.stacks[2], this.stacks[3], this.stacks[4]);
			this.infusionCost = LightningInfusionRecipes.instance().getLastResultCost();
			if (itemstack == null) return false;
			if(this.infusionCost <= 0) return false;
			if(!this.canDrawCellPower(this.infusionCost)) return false;
			if (this.stacks[5] == null) return true;
			if (!this.stacks[5].isItemEqual(itemstack)) return false;
			int result = stacks[5].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.stacks[5].getMaxStackSize();
		}
	}

	/** Perform the infusion */
	private void infuseItem() {
		if (this.canInfuse()) {
			ItemStack itemstack = LightningInfusionRecipes.instance().getInfusionResult(this.stacks[0], 
					this.stacks[1], this.stacks[2], this.stacks[3], this.stacks[4]);
			int cost = LightningInfusionRecipes.instance().getLastResultCost();
			
			// take away the power!
			this.drawCellPower(cost);
			
			if (this.stacks[5] == null) {
				this.stacks[5] = itemstack.copy();
			} else if (this.stacks[5].getItem() == itemstack.getItem()) {
				this.stacks[5].stackSize += itemstack.stackSize;
			}
			
			for(int i = 0; i < 5; i++) {
				if(stacks[i] != null) {
					--this.stacks[i].stackSize;
					
					if(this.stacks[i].stackSize <= 0){
						this.stacks[i] = null;
					}
				}
			
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return par1 == 5 ? false : true;
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
		return facing.getIndex() != 0 || par1 == 5 || itemstack.getItem() == Items.BUCKET;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		this.infuserBurnTime = tagCompound.getShort("BurnTime");
		this.infuserCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = burnTime;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setShort("BurnTime", (short) this.infuserBurnTime);
		tagCompound.setShort("CookTime", (short) this.infuserBurnTime);

		return tagCompound;
	}
	
}
