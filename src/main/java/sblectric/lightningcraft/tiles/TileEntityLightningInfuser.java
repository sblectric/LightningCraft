package sblectric.lightningcraft.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.IFurnace;
import sblectric.lightningcraft.recipes.LightningInfusionRecipes;

/** The lightning infusion table tile entity */
public class TileEntityLightningInfuser extends TileEntityLightningItemHandler.Upgradable {
	
	private static final int top = 0;
	private static final int inf1 = 1;
	private static final int inf2 = 2;
	private static final int inf3 = 3;
	private static final int inf4 = 4;
	private static final int bottom = 5;
	
	private static final int[] slotsTop = new int[]{top};
	private static final int[] slotsBottom = new int[]{bottom};
	private static final int[] slotsSides = new int[]{inf1, inf2, inf3, inf4};
	
	public static final int burnTime = 600; // time / LE in ticks (one recipe "cooks" in this period)
	
	public int infuserBurnTime;
	public int infuserCookTime;
	public int currentBurnTime;
	public int infusionCost;
	
	private boolean redo;
	
	/** The lightning infusion table tile entity */
	public TileEntityLightningInfuser() {
		setSizeInventory(6); // six slots
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
		
		if (this.world.isRemote) {
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
				IBlockState state = world.getBlockState(pos);
				((IFurnace)state.getBlock()).setBurning(state, world, pos, this.infuserCookTime > 0);
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

	/** Can the item be infused? */
	private boolean canInfuse() {
		
		// quick exit
		this.infusionCost = 0;
		if(!this.hasLPCell()) return false;
		
		if (this.getStack(0).isEmpty()) {
			return false;
		} else {
			ItemStack itemstack = LightningInfusionRecipes.instance().getInfusionResult(this.getStack(0), 
					this.getStack(1), this.getStack(2), this.getStack(3), this.getStack(4));
			this.infusionCost = LightningInfusionRecipes.instance().getLastResultCost();
			if (itemstack.isEmpty()) return false;
			if(this.infusionCost <= 0) return false;
			if(!this.canDrawCellPower(this.infusionCost)) return false;
			if (this.getStack(5).isEmpty()) return true;
			if (!this.getStack(5).isItemEqual(itemstack)) return false;
			int result = getStack(5).getCount() + itemstack.getCount();
			return result <= getInventoryStackLimit() && result <= this.getStack(5).getMaxStackSize();
		}
	}

	/** Perform the infusion */
	private void infuseItem() {
		if (this.canInfuse()) {
			ItemStack itemstack = LightningInfusionRecipes.instance().getInfusionResult(this.getStack(0), 
					this.getStack(1), this.getStack(2), this.getStack(3), this.getStack(4));
			int cost = LightningInfusionRecipes.instance().getLastResultCost();
			
			// take away the power!
			this.drawCellPower(cost);
			
			if (this.getStack(5).isEmpty()) {
				this.setStack(5, itemstack.copy());
			} else if (this.getStack(5).getItem() == itemstack.getItem()) {
				this.getStack(5).setCount(this.getStack(5).getCount() + itemstack.getCount());
			}
			
			for(int i = 0; i < 5; i++) {
				if(!getStack(i).isEmpty()) {
					this.getStack(i).setCount(this.getStack(i).getCount() - 1);
					
					if(this.getStack(i).getCount() <= 0){
						this.setStack(i, ItemStack.EMPTY);
					}
				}
			
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
