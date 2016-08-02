package sblectric.lightningcraft.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.blocks.IFurnace;
import sblectric.lightningcraft.blocks.LCBlocks;

/** The lightning furnace tile entity */
public class TileEntityLightningFurnace extends TileEntityLightningItemHandler {

	private static final int[] slotsTop = new int[]{0};
	private static final int[] slotsBottom = new int[]{1};
	private static final int[] slotsSides = new int[]{0};

	private static final int burnTime = 80; // time / LE in ticks (two items cook in this period)

	private ItemStack[] furnaceItemStacks = new ItemStack[2]; // only two slots
	public int furnaceBurnTime;
	public int furnaceCookTime;
	public int currentBurnTime;
	private String furnaceName;
	
	/** The lightning furnace tile entity */
	public TileEntityLightningFurnace() {}

	public void furnaceName(String string) {
		this.furnaceName = string;
	}

	@Override
	public int getSizeInventory() {
		return this.furnaceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.furnaceItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if(this.furnaceItemStacks[par1] != null) {
			ItemStack itemstack;
			if(this.furnaceItemStacks[par1].stackSize <= par2){
				itemstack = this.furnaceItemStacks[par1];
				this.furnaceItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.furnaceItemStacks[par1].splitStack(par2);
				if(this.furnaceItemStacks[par1].stackSize == 0){
					this.furnaceItemStacks[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		if (this.furnaceItemStacks[slot] != null) {
			ItemStack itemstack = this.furnaceItemStacks[slot];
			this.furnaceItemStacks[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.furnaceItemStacks[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.furnaceName : LCBlocks.lightningFurnace.getLocalizedName();
	}

	@Override
	public boolean hasCustomName() {
		return this.furnaceName != null && this.furnaceName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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

		if(this.worldObj.isRemote) {
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
				IBlockState state = worldObj.getBlockState(pos);
				((IFurnace)state.getBlock()).setBurning(state, worldObj, pos, this.furnaceBurnTime > 0);
			}

		}

		if (dosave) {
			this.markDirty();
		}
	}

	/** Can the item be smelted? */
	private boolean canSmelt() {

		// quick exit
		if(!this.hasLPCell() || !this.canDrawCellPower(1)) return false;

		if (this.furnaceItemStacks[0] == null) {
			return false;
		} else {
			ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
			if (itemstack == null) return false;
			if (this.furnaceItemStacks[1] == null) return true;
			if (!this.furnaceItemStacks[1].isItemEqual(itemstack)) return false;
			int result = furnaceItemStacks[1].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.furnaceItemStacks[1].getMaxStackSize();
		}
	}

	/** Smelt the item */
	private void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);

			if (this.furnaceItemStacks[1] == null) {
				this.furnaceItemStacks[1] = itemstack.copy();
			} else if (this.furnaceItemStacks[1].getItem() == itemstack.getItem()) {
				this.furnaceItemStacks[1].stackSize += itemstack.stackSize;
			}

			--this.furnaceItemStacks[0].stackSize;

			if(this.furnaceItemStacks[0].stackSize <= 0){
				this.furnaceItemStacks[0] = null;
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
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");

			if (byte0 >= 0 && byte0 < this.furnaceItemStacks.length) {
				this.furnaceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}

		this.furnaceBurnTime = tagCompound.getShort("BurnTime");
		this.furnaceCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = burnTime;

		if (tagCompound.hasKey("CustomName", 8)) {
			this.furnaceName = tagCompound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("BurnTime", (short) this.furnaceBurnTime);
		tagCompound.setShort("CookTime", (short) this.furnaceBurnTime);
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
			if (this.furnaceItemStacks[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte) i);
				this.furnaceItemStacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}

		tagCompound.setTag("Items", tagList);

		if (this.hasCustomName()) {
			tagCompound.setString("CustomName", this.furnaceName);
		}
		return tagCompound;
	}

}
