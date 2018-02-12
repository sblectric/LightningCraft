package sblectric.lightningcraft.tiles;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import sblectric.lightningcraft.api.capabilities.implementation.BaseLightningUpgradable;
import sblectric.lightningcraft.init.LCCapabilities;
import sblectric.lightningcraft.tiles.ifaces.ISidedInventoryLC;
import sblectric.lightningcraft.util.LCMisc;

/** Superclass for lightning users that have inventory slots */
public abstract class TileEntityLightningItemHandler extends TileEntityLightningUser implements ISidedInventoryLC {
	
	protected SidedInvWrapper[] itemCapabilities;
	private ItemStack[] stacks;

	public TileEntityLightningItemHandler() {
		itemCapabilities = LCMisc.makeInvWrapper(this);
		stacks = new ItemStack[0]; // zero size by default
	}
	
	/** Set the size of the inventory, avoiding null ItemStacks */
	public void setSizeInventory(int size) {
		stacks = new ItemStack[size];
		for(int i = 0; i < size; i++) {
			stacks[i] = ItemStack.EMPTY;
		}
	}

	@Override
	public int getSizeInventory() {
		return stacks.length;
	}
	
	/** Get the specified stack */
	public @Nonnull ItemStack getStack(int index) {
		if(index >= stacks.length) return ItemStack.EMPTY;
		return stacks[index];
	}
	
	/** Set the specified stack */
	public boolean setStack(int index, @Nonnull ItemStack stack) {
		if(index >= stacks.length) return false;
		stacks[index] = stack;
		return true;
	}

	@Override
	public @Nonnull ItemStack getStackInSlot(int slot) {
		return stacks[slot];
	}

	@Override
	public @Nonnull ItemStack decrStackSize(int par1, int par2) {
		if(!stacks[par1].isEmpty()) {
			ItemStack itemstack;
			if(stacks[par1].getCount() <= par2) {
				itemstack = stacks[par1];
				stacks[par1] = ItemStack.EMPTY;
				return itemstack;
			} else {
				itemstack = stacks[par1].splitStack(par2);
				if(stacks[par1].getCount() == 0) {
					stacks[par1] = ItemStack.EMPTY;
				}
				return itemstack;
			}
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public @Nonnull ItemStack removeStackFromSlot(int slot) {
		if (!stacks[slot].isEmpty()) {
			ItemStack itemstack = stacks[slot];
			stacks[slot] = ItemStack.EMPTY;
			return itemstack;
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, @Nonnull ItemStack itemstack) {
		stacks[slot] = itemstack;
		if(!itemstack.isEmpty() && itemstack.getCount() > this.getInventoryStackLimit()) {
			itemstack.setCount(this.getInventoryStackLimit());
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
			return (T)itemCapabilities[facing.getIndex()];
		} else {
			return super.getCapability(capability, facing);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		setSizeInventory(this.getSizeInventory()); // refresh the stacks on load

		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound slotTag = tagList.getCompoundTagAt(i);
			byte slot = slotTag.getByte("Slot");

			if (slot >= 0 && slot < this.stacks.length) {
				this.stacks[slot] = new ItemStack(slotTag);
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.stacks.length; ++i) {
			if(!this.stacks[i].isEmpty()) {
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
	public static abstract class Upgradable extends TileEntityLightningItemHandler {
		
		private BaseLightningUpgradable upgrade = new BaseLightningUpgradable();
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			if(capability == LCCapabilities.LIGHTNING_UPGRADABLE) {
				return true;
			} else {
				return super.hasCapability(capability, facing);
			}
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(capability == LCCapabilities.LIGHTNING_UPGRADABLE) {
				return (T)upgrade;
			} else {
				return super.getCapability(capability, facing);
			}
		}

		public boolean isUpgraded() {
			return upgrade.isUpgraded();
		}
		
		public void setUpgraded(boolean upgraded) {
			upgrade.setUpgraded(upgraded);
		}
		
		@Override
		public void readFromNBT(NBTTagCompound tagCompound) {
			super.readFromNBT(tagCompound);
			upgrade.deserializeNBT(tagCompound);
		}
		
		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
			super.writeToNBT(tagCompound);
			return upgrade.serializeNBT(tagCompound);
		}
		
		@Override
		public String getName() {
			return this.getBlockType().getLocalizedName() + (isUpgraded() ? " (Upgr.)" : "");
		}

		@Override
		public boolean hasCustomName() {
			return true;
		}
		
	}

}
