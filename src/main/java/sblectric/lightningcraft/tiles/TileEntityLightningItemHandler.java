package sblectric.lightningcraft.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import sblectric.lightningcraft.tiles.ifaces.ILightningUpgradable;
import sblectric.lightningcraft.tiles.ifaces.ISidedInventoryLC;

/** Superclass for lightning users that have inventory slots */
public abstract class TileEntityLightningItemHandler extends TileEntityLightningUser implements ISidedInventoryLC {
	
	protected InvWrapper itemCapability;
	protected ItemStack[] stacks;

	public TileEntityLightningItemHandler() {
		itemCapability = new InvWrapper(this);
		stacks = new ItemStack[0]; // zero size by default
	}

	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return stacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if(stacks[par1] != null) {
			ItemStack itemstack;
			if(stacks[par1].stackSize <= par2) {
				itemstack = stacks[par1];
				stacks[par1] = null;
				return itemstack;
			} else {
				itemstack = stacks[par1].splitStack(par2);
				if(stacks[par1].stackSize == 0) {
					stacks[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		if (stacks[slot] != null) {
			ItemStack itemstack = stacks[slot];
			stacks[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		stacks[slot] = itemstack;
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		} else {
			return super.hasCapability(capability, facing);
		}
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T)itemCapability;
		} else {
			return super.getCapability(capability, facing);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.stacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound slotTag = tagList.getCompoundTagAt(i);
			byte slot = slotTag.getByte("Slot");

			if (slot >= 0 && slot < this.stacks.length) {
				this.stacks[slot] = ItemStack.loadItemStackFromNBT(slotTag);
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.stacks.length; ++i) {
			if(this.stacks[i] != null) {
				NBTTagCompound slotTag = new NBTTagCompound();
				slotTag.setByte("Slot", (byte)i);
				this.stacks[i].writeToNBT(slotTag);
				tagList.appendTag(slotTag);
			}
		}
		
		tagCompound.setTag("Items", tagList);
		
		return tagCompound;
	}
	
	/** The Lightning upgradable variant */
	public static abstract class Upgradable extends TileEntityLightningItemHandler implements ILightningUpgradable {
		
		protected boolean isUpgraded;
		
		@Override
		public EnumActionResult onLightningUpgrade(ItemStack stack, EntityPlayer player, World world, BlockPos pos, 
				EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			isUpgraded = true;
			return EnumActionResult.SUCCESS; // on success, uses an upgrade
		}

		@Override
		public boolean isUpgraded() {
			return isUpgraded;
		}
		
		@Override
		public void setUpgraded(boolean upgraded) {
			isUpgraded = upgraded;
		}
		
		@Override
		public void readFromNBT(NBTTagCompound tagCompound) {
			super.readFromNBT(tagCompound);
			this.isUpgraded = tagCompound.getBoolean("isUpgraded");
		}
		
		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
			super.writeToNBT(tagCompound);
			tagCompound.setBoolean("isUpgraded", this.isUpgraded);
			return tagCompound;
		}
		
		@Override
		public String getName() {
			return this.getBlockType().getLocalizedName() + (isUpgraded ? " (Upgr.)" : "");
		}

		@Override
		public boolean hasCustomName() {
			return true;
		}
		
	}

}
