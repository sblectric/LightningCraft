package sblectric.lightningcraft.tiles;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.util.LCMisc;
import sblectric.lightningcraft.util.StackHelper;

/** The Lightning miner tile entity */
public class TileEntityLightningMiner extends TileEntityLightningItemHandler.Upgradable {

	public static final int nStacks = 9; // internal slots
	public static final int[] stacksInt = StackHelper.makeIntArray(nStacks);
	public static final int rangeStock = 16; // range of mining
	public static final int rangeUpgraded = 24;
	public static final int breakModTime = TileEntityLightningBreaker.breakModTime * 2;
	private static final int maxRetryFactor = 11;
	
	private static Block[] fillerBlocks = LCMisc.getBlocksFromRegs(LCConfig.minerFillerBlocks);

	public EnumMode mode = EnumMode.DEFAULT; // the miner's operating mode
	public boolean replaceBlocks = true; // whether the miner will attempt to replace mined blocks with dirt or cobblestone
	private int retries = 0;
	
	public TileEntityLightningMiner() {
		stacks = new ItemStack[nStacks];
	}

	/** Operating mode */
	public static enum EnumMode {
		NONE(0, "Disabled", 0),
		SHAFT(1, "1x1 Tunnel", 0.5),
		TUNNEL(2, "3x3 Tunnel", 0.8),
		ORES(3, "Seek Ores", 1.5),
		LOGS(4, "Seek Logs", 1.3),
		MATCHING(5, "Slot Matching", 1.8),
		ALL(6, "All in Range", 1.2);

		private static EnumMode DEFAULT = NONE;
		private int id;
		private String name;
		private double cost;

		private EnumMode(int id, String name, double cost) {
			this.id = id;
			this.name = name;
			this.cost = cost;
		}

		public String getName() {
			return this.name;
		}

		public int getID() {
			return this.id;
		}

		public double getCost() {
			return this.cost;
		}

		public static EnumMode assignMode(int id) {
			for(EnumMode m : EnumMode.values()) {
				if(m.id == id) return m;
			}
			return DEFAULT;
		}

	}

	// world-called update
	@Override
	public void update() {
		if(!worldObj.isRemote && worldObj.getTotalWorldTime() % breakModTime == 0) doOperation();
	}

	/** Do the mining operation */
	public void doOperation() {
		if(this.hasLPCell() && this.canDrawCellPower(mode.cost) && !worldObj.isBlockPowered(pos)) {
			int effectiveRange = getEffectiveRange();
			EnumFacing face = EnumFacing.getFront(this.getBlockMetadata());
			BlockPos initial = pos.offset(face, random.nextInt(effectiveRange) + 1);

			// Do different things depending on the mode
			switch(mode) {
			case NONE: // do nothing
				break;
			case SHAFT: // dig a straight tunnel 'range' blocks long
				this.mineBlock(initial); // select a random block in range to be mined, then mine it
				break;
			default: // multidimensional mining
				int width = mode == EnumMode.TUNNEL ? 2 : effectiveRange;
				int rad = width / 2;

				// get a random block in the specified range
				Axis mineAxis = face.getAxis();
				BlockPos find = initial;
				for(Axis a : Axis.values()) {
					if(a != mineAxis) find = find.offset(face.rotateAround(a), random.nextInt(width + 1) - rad);
				}
				
				// specialized processing ahead
				IBlockState state = worldObj.getBlockState(find);
				Block block = state == null ? null : state.getBlock();

				// is it ores-only?
				// a block is an ore to the miner if its unlocalized stack name contains "ore"
				// or has "ore" as the start of its oredict name
				boolean flag = false;
				if(mode == EnumMode.ORES) {
					if(block != null && Item.getItemFromBlock(block) != null) {
						ItemStack stack = new ItemStack(block, 1, block.getMetaFromState(state));
						if(stack.getUnlocalizedName().toLowerCase().contains("ore") || 
								StackHelper.oreDictNameStartsWith(stack, "ore")) flag = true;
					}
				// logs-only has a similar functionality
				} else if(mode == EnumMode.LOGS) {
					if(block != null && Item.getItemFromBlock(block) != null) {
						ItemStack stack = new ItemStack(block, 1, block.getMetaFromState(state));
						if(StackHelper.oreDictNameStartsWith(stack, "logWood")) flag = true;
					}
				// seeks only things that are also in its own inventory
				} else if(mode == EnumMode.MATCHING) {
					if(block != null && Item.getItemFromBlock(block) != null) {
						ItemStack stack = new ItemStack(block, 1, block.getMetaFromState(state));
						for(int slot : stacksInt) {
							if(StackHelper.areItemStacksEqualForCrafting(getStackInSlot(slot), stack)) {
								flag = true;
								break;
							}
						}
					}
				} else {
					flag = true;
				}

				// mine the block
				this.mineBlock(find, flag);
			}
		}
	}

	/** Actually mine the block at the specified position.
	 * Returns true if the block was successfully mined */
	private boolean mineBlock(BlockPos minePos) {
		return mineBlock(minePos, true);
	}

	/** Actually mine the block at the specified position, and only if cond is true.
	 * Returns true if the block was successfully mined */
	private boolean mineBlock(BlockPos minePos, boolean cond) {
		EnumFacing face = EnumFacing.getFront(this.getBlockMetadata());

		// make sure the block is valid and isn't unbreakable, then mine it
		IBlockState mineState = worldObj.getBlockState(minePos);
		Block mineBlock = mineState.getBlock();
		if(cond && mineBlock != null && !mineBlock.isAir(mineState, worldObj, minePos) && mineState.getBlockHardness(worldObj, minePos) >= 0) {
			List<ItemStack> drops = mineBlock.getDrops(this.worldObj, minePos, mineState, 0);
			float chance = ForgeEventFactory.fireBlockHarvesting(drops, this.worldObj, minePos, worldObj.getBlockState(minePos), 0, 1, false, null);
			
			if(random.nextFloat() <= chance) {

				// replace the mined block with a filler block or air
				boolean flag = false;
				if(replaceBlocks) {
					if(!LCMisc.inArray(mineBlock, fillerBlocks)) {
						for(int slot : stacksInt) {
							ItemStack s = this.getStackInSlot(slot);
							if(s != null) {
								Block block = Block.getBlockFromItem(s.getItem());
								if(block != null && s.getItemDamage() == 0 && LCMisc.inArray(block, fillerBlocks)) {
									s.stackSize--;
									if(s.stackSize <= 0) this.setInventorySlotContents(slot, null);
									worldObj.setBlockState(minePos, block.getDefaultState(), 3);
									flag = true; // success!
								}
							}
						}
					}
				} else { // only set to air when replacing is disabled
					worldObj.setBlockToAir(minePos);
					flag = true; // success!
				}

				// break the block upon success
				if(flag) {
					this.worldObj.playEvent(2001, minePos, Block.getStateId(mineState));
					this.drawCellPower(mode.cost);
					for(ItemStack drop : drops) {
						ItemStack leftover = TileEntityHopper.putStackInInventoryAllSlots(this, drop, face);
						if(leftover != null) Block.spawnAsEntity(worldObj, pos, leftover);
					}
					retries = 0;
					this.markDirty(); // mark the tile for saving
					return true;
				}
			}
		}

		// schedule a retry and return false
		rescheduleOperation();
		return false;
	}

	/** Get the effective range of the miner */
	public int getEffectiveRange() {
		return isUpgraded ? rangeUpgraded : rangeStock;
	}
	
	/** Get the number of permitted retries */
	private int getNumRetries() {
		return Math.min(LCConfig.minerMaxRetries, getEffectiveRange() * maxRetryFactor);
	}

	/** Reschedules the tile's update method */
	public void rescheduleOperation() {
		retries++;
		if(retries > getNumRetries()) retries = 0;
		if(retries < getNumRetries()) {
			((WorldServer)worldObj).addScheduledTask(() -> doOperation());
		}
	}

	/** Rotate the operating mode */
	public void rotateOperatingMode() {
		this.mode = EnumMode.assignMode((mode.id + 1) % EnumMode.values().length);
		this.markDirty();
	}

	/** Toggle block replacement */
	public void toggleBlockReplacement() {
		this.replaceBlocks = !this.replaceBlocks;
		this.markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		// operating mode
		this.mode = EnumMode.assignMode(tagCompound.getInteger("operatingMode"));
		this.replaceBlocks = tagCompound.getBoolean("replaceBlocks");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		// operating mode
		tagCompound.setInteger("operatingMode", this.mode.id);
		tagCompound.setBoolean("replaceBlocks", this.replaceBlocks);

		return tagCompound;
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return true; // all slots are valid
	}

	@Override
	public int[] getSlotsForFace(EnumFacing facing) {
		return stacksInt;
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack itemstack, EnumFacing facing) {
		return true;
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack itemstack, EnumFacing facing) {
		return true;
	}

}
