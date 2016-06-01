package com.lightningcraft.worldgen.structure;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import com.lightningcraft.util.WeightedRandomChestContent;
import com.lightningcraft.util.WorldUtils;

/** Structure superclass */
public abstract class Feature extends StructureComponent {

	/** loot chests for the structure */
	protected LootChestGroup lootChests;

	/** The size of the bounding box for this feature in the X axis */
	protected int scatteredFeatureSizeX;
	/** The size of the bounding box for this feature in the Y axis */
	protected int scatteredFeatureSizeY;
	/** The size of the bounding box for this feature in the Z axis */
	protected int scatteredFeatureSizeZ;
	protected int structY = -1;

	/* Min and max spawn y positions */
	protected int spawnMinY;
	protected int spawnMaxY;

	public Feature() { }

	protected Feature(Random rand, int x, int y, int z, int sx, int sy, int sz, boolean useRandomCoordBaseMode) {
		super(0);
		this.scatteredFeatureSizeX = sx;
		this.scatteredFeatureSizeY = sy;
		this.scatteredFeatureSizeZ = sz;

		if(useRandomCoordBaseMode)
			this.setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));
		else
			this.setCoordBaseMode(EnumFacing.SOUTH);

		switch (this.getCoordBaseMode()) {
		case NORTH:
		case SOUTH:
			this.boundingBox = new StructureBoundingBox(x, y, z, x + sx - 1, y + sy - 1, z + sz - 1);
			break;
		default:
			this.boundingBox = new StructureBoundingBox(x, y, z, x + sz - 1, y + sy - 1, z + sx - 1);
		}
	}

	protected Feature(Random rand, int x, int y, int z, int sx, int sy, int sz) {
		this(rand, x, y, z, sx, sy, sz, true);
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tag)
	{
		tag.setInteger("Width", this.scatteredFeatureSizeX);
		tag.setInteger("Height", this.scatteredFeatureSizeY);
		tag.setInteger("Depth", this.scatteredFeatureSizeZ);
		tag.setInteger("HPos", this.structY);
		lootChests.writeToNBT(tag);
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound tag)
	{
		this.scatteredFeatureSizeX = tag.getInteger("Width");
		this.scatteredFeatureSizeY = tag.getInteger("Height");
		this.scatteredFeatureSizeZ = tag.getInteger("Depth");
		this.structY = tag.getInteger("HPos");
		lootChests.readFromNBT(tag);
	}

	/** Alternate version of this method that takes the LootChestGroup data with specific chest data */
	protected boolean generateStructureChestContents(World world, StructureBoundingBox box, Random rand, int x, int y, int z, int meta, int chestIndex) {
		return generateStructureChestContents(world, box, rand, x, y, z, meta,
				lootChests.getChestContents(chestIndex), lootChests.getStackCount(rand, chestIndex), lootChests.getIsTrapped(chestIndex));
	}

	/** Alternate version of this method that takes the LootChestGroup data [defaults] */
	protected boolean generateStructureChestContents(World world, StructureBoundingBox box, Random rand, int x, int y, int z, int meta) {
		return generateStructureChestContents(world, box, rand, x, y, z, meta, lootChests.getChestContents(), lootChests.getStackCount(rand), false);
	}

	/**
	 * Used to generate chests with items in it. ex: Temple Chests, Village Blacksmith Chests, Mineshaft Chests.
	 */
	protected boolean generateStructureChestContents(World world, StructureBoundingBox box, Random rand, int x, int y, int z, int meta, List<WeightedRandomChestContent> chest, int count, boolean isTrapped) {
		int i1 = this.getXWithOffset(x, z);
		int j1 = this.getYWithOffset(y);
		int k1 = this.getZWithOffset(x, z);
		BlockPos pos = new BlockPos(i1, j1, k1);
		Block chestBlock;

		if(isTrapped) {
			chestBlock = Blocks.TRAPPED_CHEST;
		} else {
			chestBlock = Blocks.CHEST;
		}

		if (world.getBlockState(pos).getBlock() != chestBlock) {
			IBlockState iblockstate = chestBlock.getStateFromMeta(meta);
			world.setBlockState(pos, iblockstate, 2);
			TileEntity tileentity = world.getTileEntity(pos);

			if (tileentity instanceof TileEntityChest) {
				WeightedRandomChestContent.generateChestContents(rand, chest, (TileEntityChest)tileentity, count);
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Used to generate chests with items in it. ex: Temple Chests, Village Blacksmith Chests, Mineshaft Chests.
	 */
	protected boolean generateStructureChestContents(World world, StructureBoundingBox box, Random rand, int x, int y, int z, int meta, List<WeightedRandomChestContent> chest, int count)
	{
		return generateStructureChestContents(world, box, rand, x, y, z, meta, chest, count, false);
	}

	/** placeBlockAtCurrentPosition wrapper */
	protected void addBlock(World world, int x, int y, int z, Block block, int meta) {
		boolean ignoreBox = false;
		if(meta < 0) {
			meta = 0;
			ignoreBox = true;
		}
		this.placeBlockAtCurrentPosition(world, block, meta, x, y, z, this.boundingBox, ignoreBox);
	}

	/** placeBlockAtCurrentPosition wrapper */
	protected void addBlock(World world, int x, int y, int z, int block, int meta) {
		this.placeBlockAtCurrentPosition(world, Block.getBlockById(block), meta, x, y, z, this.boundingBox, false);
	}

	/**
	 * current Position depends on currently set Coordinates mode, is computed here
	 */
	protected void placeBlockAtCurrentPosition(World world, Block b, int meta, int x, int y, int z, StructureBoundingBox box, boolean ignoreBox)
	{
		int i1 = this.getXWithOffset(x, z);
		int j1 = this.getYWithOffset(y);
		int k1 = this.getZWithOffset(x, z);

		if (box.isVecInside(new Vec3i(i1, j1, k1)) || ignoreBox)
		{
			IBlockState state = b.getStateFromMeta(meta);
			world.setBlockState(new BlockPos(i1, j1, k1), state, 2);
		}
	}

	/** place a spawner instead of a normal block */
	protected void addSpawner(World world, int x, int y, int z, Class<? extends Entity> entityClass) {
		// add the spawner block
		addBlock(world, x, y, z, Blocks.MOB_SPAWNER, -1);

		int i1 = this.getXWithOffset(x, z);
		int j1 = this.getYWithOffset(y);
		int k1 = this.getZWithOffset(x, z);
		BlockPos pos = new BlockPos(i1, j1, k1);

		// set its entity type
		TileEntityMobSpawner tile = (TileEntityMobSpawner)world.getTileEntity(pos);
		tile.getSpawnerBaseLogic().setEntityName((String)EntityList.CLASS_TO_NAME.get(entityClass));
	}

	/** Find spawn position for the structure */
	protected abstract boolean findSpawnPosition(World world, StructureBoundingBox box, int yoff);

}
