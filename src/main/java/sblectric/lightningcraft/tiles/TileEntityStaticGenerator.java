package sblectric.lightningcraft.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.IFurnace;
import sblectric.lightningcraft.entities.EntityLCLightningBolt;
import sblectric.lightningcraft.util.Effect;

/** The static generator */
public class TileEntityStaticGenerator extends TileEntityLightningItemHandler.Upgradable {
	
	private static final int[] slotsTop = new int[]{0};
	
	private static final int lpBurnTime = 10; // time / LP in ticks (one item "cooks" in this period)
	private static final double chargePerBlock = 0.8;
	private static final double chanceOfDrain = 0.25;
	
	public int generatorBurnTime;
	public int generatorCookTime;
	public int currentBurnTime;
	public double storedCharge;
	
	private boolean redo;
	
	public TileEntityStaticGenerator() {
		setSizeInventory(1); // one slot
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.generatorCookTime * par1 / lpBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentBurnTime == 0) {
			this.currentBurnTime = lpBurnTime;
		}

		return this.generatorBurnTime * par1 / this.currentBurnTime;
	}

	public boolean isBurning() {
		return this.generatorBurnTime > 0;
	}

	@Override
	public void update() {
		boolean burning = this.generatorCookTime > 0;
		boolean dosave = false;

		if (this.generatorBurnTime > 0) {
			--this.generatorBurnTime;
		}
		
		if (this.world.isRemote) {
			// nothing client-only
		} else {
			if (this.generatorBurnTime == 0 && this.canGenerate()) {
				this.currentBurnTime = this.generatorBurnTime = lpBurnTime;

				if (this.generatorBurnTime > 0) {
					dosave = true;
				}
			}

			if (this.isBurning() && this.canGenerate()) {
				++this.generatorCookTime;
				
				if (this.generatorCookTime == lpBurnTime) { // 1 unit / item default
					this.generatorCookTime = 0;
					this.generate();
					dosave = true;
				}
			} else {
				this.generatorCookTime = 0;
			}
			
			// Update the burning state
			if (burning != this.generatorCookTime > 0) {
				dosave = true;
				IBlockState state = world.getBlockState(pos);
				((IFurnace)state.getBlock()).setBurning(state, world, pos, this.generatorCookTime > 0);
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

	private boolean canGenerate() {
		
		// quick exit (can't generate on a remote power source)
		if(!this.hasLPCell() || this.isRemotelyPowered()) return false;
		
		if (!this.getStack(0).isEmpty()) {
			if(this.tileCell.storedPower < 0.5) return false;
			if(this.getStack(0).getItem() instanceof ItemBlock) return true;
		}
		return false;
	}

	private void generate() {
		if (this.canGenerate()) {
			
			double currentCost;
			EntityLCLightningBolt lightning;
		
			// take away the power!
			currentCost = (random.nextDouble() <= chanceOfDrain) ? 0.5 : 0.01;
			this.drawCellPower(currentCost); // not enough, Johnny
			
			// add charge to the cell
			this.storedCharge += chargePerBlock;
			
			// it's full, spawn in lightning!
			if(this.storedCharge >= 100 && this.cellPower < this.maxPower - 100D * this.tileCell.efficiency) {
				Effect.lightningGen(this.world, this.tileCell.getPos().up());
				this.storedCharge = 0; // equalize the charge
			}
			
			// remove the item
			if(!getStack(0).isEmpty()) {
				this.getStack(0).setCount(this.getStack(0).getCount() - 1);
				
				if(this.getStack(0).getCount() <= 0){
					this.setStack(0, ItemStack.EMPTY);
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		this.generatorBurnTime = tagCompound.getShort("BurnTime");
		this.generatorCookTime = tagCompound.getShort("CookTime");
		this.storedCharge = tagCompound.getDouble("StoredCharge");
		this.currentBurnTime = lpBurnTime;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setShort("BurnTime", (short) this.generatorBurnTime);
		tagCompound.setShort("CookTime", (short) this.generatorBurnTime);
		tagCompound.setDouble("StoredCharge", this.storedCharge);

		return tagCompound;
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack stack) {
		return stack != null && stack.getItem() instanceof ItemBlock;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing facing) {
		return slotsTop;
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack itemstack, EnumFacing facing) {
		return this.isItemValidForSlot(par1, itemstack);
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack itemstack, EnumFacing facing) {
		return false;
	}
	
}
