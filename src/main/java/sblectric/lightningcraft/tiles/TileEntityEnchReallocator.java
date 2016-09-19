package sblectric.lightningcraft.tiles;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.blocks.ifaces.IFurnace;
import sblectric.lightningcraft.util.LCMisc;

/** The enchantment reallocator tile entity */
public class TileEntityEnchReallocator extends TileEntityLightningItemHandler.Upgradable {
	
	private static final int[] slotsTop = new int[]{0};
	private static final int[] slotsBottom = new int[]{1};
	private static final int[] slotsSides = new int[]{1};
	
	private static final int lpBurnTime = 200; // time / LP in ticks (time it takes for enchantment transfer)
	
	public int reallocBurnTime;
	public int reallocCookTime;
	public int currentBurnTime;
	
	private boolean redo;
	
	public List<NBTTagCompound> topEnchs;
	public int nTopEnchs;
	public int lpCost;
	public int xpCost;
	public int xpPlayer;
	public EntityPlayer player = null;
	public boolean hasPlayer = false;
	
	public TileEntityEnchReallocator() {
		stacks = new ItemStack[2]; // only two slots
	}

	@Override
	public int getInventoryStackLimit() {
		return 1; // one at a time m8
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
		
		// run update twice a tick on upgrade
		if(isUpgraded && !redo) {
			redo = true;
			update();
		}
		redo = false;
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
		
		ItemStack top = this.stacks[0];
		ItemStack bottom = this.stacks[1];
		
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
			
			int topCost = this.stacks[0].getRepairCost();
			int bottomCost = this.stacks[1].getRepairCost();
			
			// remove all top item enchantments
			if(this.stacks[0].getItem() == Items.ENCHANTED_BOOK) {
				this.stacks[0] = new ItemStack(Items.BOOK, 1);
			} else if(this.stacks[0].getTagCompound().hasKey("ench")) {
				this.stacks[0].getTagCompound().removeTag("ench");
			}
			this.stacks[0].setRepairCost(topCost + 5);
			
			// change the book type if needed
			if(this.stacks[1].getItem() == Items.BOOK) {
				this.stacks[1] = new ItemStack(Items.ENCHANTED_BOOK, 1);
			}
			
			// add the enchantments to the bottom item
			LCMisc.addEnchantments(this.stacks[1], this.topEnchs);
			this.stacks[1].setRepairCost(bottomCost + 5);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		this.reallocBurnTime = tagCompound.getShort("BurnTime");
		this.reallocCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = lpBurnTime;		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setShort("BurnTime", (short) this.reallocBurnTime);
		tagCompound.setShort("CookTime", (short) this.reallocBurnTime);

		return tagCompound;
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
