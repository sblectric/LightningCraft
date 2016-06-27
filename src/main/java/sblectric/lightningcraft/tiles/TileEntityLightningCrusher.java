package sblectric.lightningcraft.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import sblectric.lightningcraft.blocks.IFurnace;
import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.recipes.LightningCrusherRecipes;

/** The lightning crusher tile entity */
public class TileEntityLightningCrusher extends TileEntityLightningUser implements ISidedInventoryLC {

	private static final int[] slotsTop = new int[]{0};
	private static final int[] slotsBottom = new int[]{1};
	private static final int[] slotsSides = new int[]{0};

	public static final int burnTime = 160; // time / LE in ticks (two items crush in this period)
	public static final double energyUsage = 5;

	private ItemStack[] crusherItemStacks = new ItemStack[2]; // only two slots
	public int crusherBurnTime;
	public int crusherCookTime;
	public int currentBurnTime;
	private String crusherName;
	
	/** The lightning furnace tile entity */
	public TileEntityLightningCrusher() {}

	public void furnaceName(String string) {
		this.crusherName = string;
	}

	@Override
	public int getSizeInventory() {
		return this.crusherItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.crusherItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if(this.crusherItemStacks[par1] != null) {
			ItemStack itemstack;
			if(this.crusherItemStacks[par1].stackSize <= par2){
				itemstack = this.crusherItemStacks[par1];
				this.crusherItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.crusherItemStacks[par1].splitStack(par2);
				if(this.crusherItemStacks[par1].stackSize == 0){
					this.crusherItemStacks[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		if (this.crusherItemStacks[slot] != null) {
			ItemStack itemstack = this.crusherItemStacks[slot];
			this.crusherItemStacks[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.crusherItemStacks[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.crusherName : LCBlocks.lightningCrusher.getLocalizedName();
	}

	@Override
	public boolean hasCustomName() {
		return this.crusherName != null && this.crusherName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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
				if (this.crusherCookTime == burnTime / 2) { // 1 second / item default
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
	}

	/** Can the item be crushed? */
	private boolean canCrush() {

		// quick exit
		if(!this.hasLPCell() || !this.canDrawCellPower(energyUsage)) return false;

		if (this.crusherItemStacks[0] == null) {
			return false;
		} else {
			ItemStack itemstack = LightningCrusherRecipes.instance().getCrushingResult(this.crusherItemStacks[0]);
			if (itemstack == null) return false;
			if (this.crusherItemStacks[1] == null) return true;
			if (!this.crusherItemStacks[1].isItemEqual(itemstack)) return false;
			int result = crusherItemStacks[1].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.crusherItemStacks[1].getMaxStackSize();
		}
	}

	/** crush the item */
	private void crushItem() {
		if (this.canCrush()) {
			ItemStack itemstack = LightningCrusherRecipes.instance().getCrushingResult(this.crusherItemStacks[0]);

			if (this.crusherItemStacks[1] == null) {
				this.crusherItemStacks[1] = itemstack.copy();
			} else if (this.crusherItemStacks[1].getItem() == itemstack.getItem()) {
				this.crusherItemStacks[1].stackSize += itemstack.stackSize;
			}

			--this.crusherItemStacks[0].stackSize;

			if(this.crusherItemStacks[0].stackSize <= 0){
				this.crusherItemStacks[0] = null;
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
		this.crusherItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");

			if (byte0 >= 0 && byte0 < this.crusherItemStacks.length) {
				this.crusherItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}

		this.crusherBurnTime = tagCompound.getShort("BurnTime");
		this.crusherCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = burnTime;

		if (tagCompound.hasKey("CustomName", 8)) {
			this.crusherName = tagCompound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("BurnTime", (short) this.crusherBurnTime);
		tagCompound.setShort("CookTime", (short) this.crusherBurnTime);
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.crusherItemStacks.length; ++i) {
			if (this.crusherItemStacks[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte) i);
				this.crusherItemStacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}

		tagCompound.setTag("Items", tagList);

		if (this.hasCustomName()) {
			tagCompound.setString("CustomName", this.crusherName);
		}
		return tagCompound;
	}

}
