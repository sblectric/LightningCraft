package com.lightningcraft.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.blocks.IFurnace;
import com.lightningcraft.blocks.LCBlocks;
import com.lightningcraft.entities.EntityLCLightningBolt;
import com.lightningcraft.util.Effect;

/** The static generator */
public class TileEntityStaticGenerator extends TileEntityLightningUser implements ISidedInventoryLC {
	
	private static final int[] slotsTop = new int[]{0};
	
	private static final int lpBurnTime = 10; // time / LP in ticks (one item "cooks" in this period)
	
	private ItemStack[] generatorItemStacks = new ItemStack[1]; // one slot
	public int generatorBurnTime;
	public int generatorCookTime;
	public int currentBurnTime;
	private String generatorName;
	public double storedCharge;
	private static final double chargePerBlock = 0.8;
	private static final double chanceOfDrain = 0.25;

	public void infuserName(String string){
		this.generatorName = string;
	}
	
	@Override
	public int getSizeInventory() {
		return this.generatorItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.generatorItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if(this.generatorItemStacks[par1] != null) {
			ItemStack itemstack;
			if(this.generatorItemStacks[par1].stackSize <= par2){
				itemstack = this.generatorItemStacks[par1];
				this.generatorItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.generatorItemStacks[par1].splitStack(par2);
				if(this.generatorItemStacks[par1].stackSize == 0){
					this.generatorItemStacks[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		if (this.generatorItemStacks[slot] != null) {
			ItemStack itemstack = this.generatorItemStacks[slot];
			this.generatorItemStacks[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.generatorItemStacks[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.generatorName : LCBlocks.staticGenerator.getLocalizedName();
	}

	@Override
	public boolean hasCustomName() {
		return this.generatorName != null && this.generatorName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.generatorItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");

			if (byte0 >= 0 && byte0 < this.generatorItemStacks.length) {
				this.generatorItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}

		this.generatorBurnTime = tagCompound.getShort("BurnTime");
		this.generatorCookTime = tagCompound.getShort("CookTime");
		this.storedCharge = tagCompound.getDouble("StoredCharge");
		this.currentBurnTime = lpBurnTime;

		if (tagCompound.hasKey("CustomName", 8)) {
			this.generatorName = tagCompound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("BurnTime", (short) this.generatorBurnTime);
		tagCompound.setShort("CookTime", (short) this.generatorBurnTime);
		tagCompound.setDouble("StoredCharge", this.storedCharge);
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.generatorItemStacks.length; ++i) {
			if (this.generatorItemStacks[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte) i);
				this.generatorItemStacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}

		tagCompound.setTag("Items", tagList);

		if (this.hasCustomName()) {
			tagCompound.setString("CustomName", this.generatorName);
		}
		return tagCompound;
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
		
		if (this.worldObj.isRemote) {
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
				IBlockState state = worldObj.getBlockState(pos);
				((IFurnace)state.getBlock()).setBurning(state, worldObj, pos, this.generatorCookTime > 0);
			}
			
		}

		if (dosave) {
			this.markDirty();
		}
	}

	private boolean canGenerate() {
		
		// quick exit (can't generate on a remote power source)
		if(!this.hasLPCell() || this.isRemotelyPowered()) return false;
		
		if (this.generatorItemStacks[0] != null) {
			if(this.tileCell.storedPower < 0.5) return false;
			if(this.generatorItemStacks[0].getItem() instanceof ItemBlock) return true;
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
				Effect.lightningGen(this.worldObj, this.tileCell.getPos().up());
				this.storedCharge = 0; // equalize the charge
			}
			
			// remove the item
			if(generatorItemStacks[0] != null) {
				--this.generatorItemStacks[0].stackSize;
				
				if(this.generatorItemStacks[0].stackSize <= 0){
					this.generatorItemStacks[0] = null;
				}
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return true;
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
		return true;
	}
	
}
