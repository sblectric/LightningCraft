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
import sblectric.lightningcraft.recipes.LightningInfusionRecipes;

/** The lightning infusion table tile entity */
public class TileEntityLightningInfuser extends TileEntityLightningUser implements ISidedInventoryLC {
	
	private static final int[] slotsTop = new int[]{0};
	private static final int[] slotsBottom = new int[]{5};
	private static final int[] slotsSides = new int[]{1, 2, 3, 4};
	
	public static final int burnTime = 600; // time / LE in ticks (one recipe "cooks" in this period)
	
	private ItemStack[] infuserItemStacks = new ItemStack[6]; // six slots
	public int infuserBurnTime;
	public int infuserCookTime;
	public int currentBurnTime;
	private String infuserName;
	public int infusionCost;
	
	/** The lightning infusion table tile entity */
	public TileEntityLightningInfuser() {}
	
	public void infuserName(String string){
		this.infuserName = string;
	}
	
	@Override
	public int getSizeInventory() {
		return this.infuserItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.infuserItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if(this.infuserItemStacks[par1] != null) {
			ItemStack itemstack;
			if(this.infuserItemStacks[par1].stackSize <= par2){
				itemstack = this.infuserItemStacks[par1];
				this.infuserItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.infuserItemStacks[par1].splitStack(par2);
				if(this.infuserItemStacks[par1].stackSize == 0){
					this.infuserItemStacks[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		if (this.infuserItemStacks[slot] != null) {
			ItemStack itemstack = this.infuserItemStacks[slot];
			this.infuserItemStacks[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.infuserItemStacks[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.infuserName : LCBlocks.lightningInfuser.getLocalizedName();
	}

	@Override
	public boolean hasCustomName() {
		return this.infuserName != null && this.infuserName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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
	}

	/** Can the item be infused? */
	private boolean canInfuse() {
		
		// quick exit
		this.infusionCost = 0;
		if(!this.hasLPCell()) return false;
		
		if (this.infuserItemStacks[0] == null) {
			return false;
		} else {
			ItemStack itemstack = LightningInfusionRecipes.instance().getInfusionResult(this.infuserItemStacks[0], 
					this.infuserItemStacks[1], this.infuserItemStacks[2], this.infuserItemStacks[3], this.infuserItemStacks[4]);
			this.infusionCost = LightningInfusionRecipes.instance().getLastResultCost();
			if (itemstack == null) return false;
			if(this.infusionCost <= 0) return false;
			if(!this.canDrawCellPower(this.infusionCost)) return false;
			if (this.infuserItemStacks[5] == null) return true;
			if (!this.infuserItemStacks[5].isItemEqual(itemstack)) return false;
			int result = infuserItemStacks[5].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.infuserItemStacks[5].getMaxStackSize();
		}
	}

	/** Perform the infusion */
	private void infuseItem() {
		if (this.canInfuse()) {
			ItemStack itemstack = LightningInfusionRecipes.instance().getInfusionResult(this.infuserItemStacks[0], 
					this.infuserItemStacks[1], this.infuserItemStacks[2], this.infuserItemStacks[3], this.infuserItemStacks[4]);
			int cost = LightningInfusionRecipes.instance().getLastResultCost();
			
			// take away the power!
			this.drawCellPower(cost);
			
			if (this.infuserItemStacks[5] == null) {
				this.infuserItemStacks[5] = itemstack.copy();
			} else if (this.infuserItemStacks[5].getItem() == itemstack.getItem()) {
				this.infuserItemStacks[5].stackSize += itemstack.stackSize;
			}
			
			for(int i = 0; i < 5; i++) {
				if(infuserItemStacks[i] != null) {
					--this.infuserItemStacks[i].stackSize;
					
					if(this.infuserItemStacks[i].stackSize <= 0){
						this.infuserItemStacks[i] = null;
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
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.infuserItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");

			if (byte0 >= 0 && byte0 < this.infuserItemStacks.length) {
				this.infuserItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}

		this.infuserBurnTime = tagCompound.getShort("BurnTime");
		this.infuserCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = burnTime;

		if (tagCompound.hasKey("CustomName", 8)) {
			this.infuserName = tagCompound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("BurnTime", (short) this.infuserBurnTime);
		tagCompound.setShort("CookTime", (short) this.infuserBurnTime);
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.infuserItemStacks.length; ++i) {
			if (this.infuserItemStacks[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte) i);
				this.infuserItemStacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}

		tagCompound.setTag("Items", tagList);

		if (this.hasCustomName()) {
			tagCompound.setString("CustomName", this.infuserName);
		}
		return tagCompound;
	}
	
}
