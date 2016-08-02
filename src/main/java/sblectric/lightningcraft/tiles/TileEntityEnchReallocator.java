package sblectric.lightningcraft.tiles;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.blocks.IFurnace;
import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.util.LCMisc;

/** The enchantment reallocator tile entity */
public class TileEntityEnchReallocator extends TileEntityLightningItemHandler {
	
	private static final int[] slotsTop = new int[]{0};
	private static final int[] slotsBottom = new int[]{1};
	private static final int[] slotsSides = new int[]{1};
	
	private static final int lpBurnTime = 200; // time / LP in ticks (time it takes for enchantment transfer)
	
	private ItemStack[] reallocItemStacks = new ItemStack[2]; // only two slots
	public int reallocBurnTime;
	public int reallocCookTime;
	public int currentBurnTime;
	private String reallocName;
	
	public List<NBTTagCompound> topEnchs;
	public int nTopEnchs;
	public int lpCost;
	public int xpCost;
	public int xpPlayer;
	public EntityPlayer player = null;
	public boolean hasPlayer = false;
	
	@Override
	public int getSizeInventory() {
		return this.reallocItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.reallocItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if(this.reallocItemStacks[par1] != null) {
			ItemStack itemstack;
			if(this.reallocItemStacks[par1].stackSize <= par2){
				itemstack = this.reallocItemStacks[par1];
				this.reallocItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.reallocItemStacks[par1].splitStack(par2);
				if(this.reallocItemStacks[par1].stackSize == 0){
					this.reallocItemStacks[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		if (this.reallocItemStacks[slot] != null) {
			ItemStack itemstack = this.reallocItemStacks[slot];
			this.reallocItemStacks[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.reallocItemStacks[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.reallocName : LCBlocks.enchReallocator.getLocalizedName();
	}

	@Override
	public boolean hasCustomName() {
		return this.reallocName != null && this.reallocName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1; // one at a time m8
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.reallocItemStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tabCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tabCompound1.getByte("Slot");

			if (byte0 >= 0 && byte0 < this.reallocItemStacks.length) {
				this.reallocItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tabCompound1);
			}
		}

		this.reallocBurnTime = tagCompound.getShort("BurnTime");
		this.reallocCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = lpBurnTime;

		if (tagCompound.hasKey("CustomName", 8)) {
			this.reallocName = tagCompound.getString("CustomName");
		}
		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("BurnTime", (short) this.reallocBurnTime);
		tagCompound.setShort("CookTime", (short) this.reallocBurnTime);
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.reallocItemStacks.length; ++i) {
			if (this.reallocItemStacks[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte) i);
				this.reallocItemStacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}

		tagCompound.setTag("Items", tagList);

		if (this.hasCustomName()) {
			tagCompound.setString("CustomName", this.reallocName);
		}
		return tagCompound;
	}

	@SideOnly(Side.CLIENT)
	public int getReallocProgressScaled(int par1) {
		return this.reallocCookTime * par1 / lpBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public int getReallocTimeRemainingScaled(int par1) {
		if (this.currentBurnTime == 0) {
			this.currentBurnTime = lpBurnTime;
		}

		return this.reallocBurnTime * par1 / this.currentBurnTime;
	}

	public boolean isBurning() {
		return this.reallocBurnTime > 0;
	}

	@Override
	public void update() {
		boolean flag = this.reallocBurnTime > 0;
		boolean dosave = false;

		if (this.reallocBurnTime > 0) {
			--this.reallocBurnTime;
		}
		
		if (this.worldObj.isRemote) {
			// nothing client-only			
		} else {
			if (this.reallocBurnTime == 0 && this.canReallocate()) {
				this.currentBurnTime = this.reallocBurnTime = lpBurnTime;

				if (this.reallocBurnTime > 0) {
					dosave = true;
				}
			}

			if (this.isBurning() && this.canReallocate()) {
				++this.reallocCookTime;
				
				if (this.reallocCookTime == lpBurnTime) { // 1 unit / item default
					this.reallocCookTime = 0;
					this.reallocateEnchs();
					dosave = true;
				}
			} else {
				this.reallocCookTime = 0;
			}
			
			// Update the burning state
			if (flag != this.reallocBurnTime > 0) {
				dosave = true;
				IBlockState state = worldObj.getBlockState(pos);
				((IFurnace)state.getBlock()).setBurning(state, worldObj, pos, reallocBurnTime > 0);
			}
			
		}

		if (dosave) {
			this.markDirty();
		}
	}

	/** check if you can transfer enchantments from first item stack to second */
	private boolean canReallocate() {
		
		// set to null for now
		this.topEnchs = null;
		this.xpCost = -1;
		this.lpCost = -1;
		this.nTopEnchs = 0;
		
		// quick exit
		if(!this.hasLPCell()) return false;
		
		ItemStack top = this.reallocItemStacks[0];
		ItemStack bottom = this.reallocItemStacks[1];
		
		List<NBTTagCompound> topEnchs = LCMisc.getEnchantments(top);
		
		// set the top enchantments
		if(!topEnchs.isEmpty()) {
			this.topEnchs = topEnchs;
			this.nTopEnchs = topEnchs.size();
		}

		// good to go, now set some values and return
		if(top != null && bottom != null && !topEnchs.isEmpty() && (bottom.isItemEnchantable() || bottom.getItem() == Items.BOOK)) {
			this.xpCost = top.getRepairCost() + bottom.getRepairCost() + 5;
			this.lpCost = 50 + xpCost;
			// except this condition
			if(!this.canDrawCellPower(lpCost) || this.xpCost > this.xpPlayer || this.player == null || !this.hasPlayer) return false;
			return true;
		}
		return false;
	}
	
	/** perform the reallocation */
	private void reallocateEnchs() {
		if (this.canReallocate()) {
			
			// take away the required power and XP
			this.drawCellPower(lpCost);
			if(!this.player.capabilities.isCreativeMode) ((EntityPlayerMP)this.player).addExperienceLevel(-xpCost);
			this.hasPlayer = false;
			
			int topCost = this.reallocItemStacks[0].getRepairCost();
			int bottomCost = this.reallocItemStacks[1].getRepairCost();
			
			// remove all top item enchantments
			if(this.reallocItemStacks[0].getItem() == Items.ENCHANTED_BOOK) {
				this.reallocItemStacks[0] = new ItemStack(Items.BOOK, 1);
			} else if(this.reallocItemStacks[0].getTagCompound().hasKey("ench")) {
				this.reallocItemStacks[0].getTagCompound().removeTag("ench");
			}
			this.reallocItemStacks[0].setRepairCost(topCost + 5);
			
			// change the book type if needed
			if(this.reallocItemStacks[1].getItem() == Items.BOOK) {
				this.reallocItemStacks[1] = new ItemStack(Items.ENCHANTED_BOOK, 1);
			}
			
			// add the enchantments to the bottom item
			LCMisc.addEnchantments(this.reallocItemStacks[1], this.topEnchs);
			this.reallocItemStacks[1].setRepairCost(bottomCost + 5);
		}
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return true; // all slots (2) are valid
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
	
}
