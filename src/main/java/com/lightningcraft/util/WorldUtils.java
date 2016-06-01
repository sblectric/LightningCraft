package com.lightningcraft.util;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

/** World helper class */
public class WorldUtils {
	
	private static final Block[] blacklist = new Block[]{Blocks.LAVA, Blocks.FIRE, Blocks.FLOWING_LAVA, Blocks.CACTUS};
	static final Random random = new Random();
	
	/** Check to see if the chunks exist between the supplied coordinates */
	public static boolean checkChunksExist(World world, int xFrom, int yFrom, int zFrom, int xTo, int yTo, int zTo) {
		if (yFrom >= 0 && yTo < 256) {
            for (int k1 = xFrom; k1 <= xTo; k1 += 4) {
                for (int l1 = zFrom; l1 <= zTo; l1 += 4) {
                    if (!world.isBlockLoaded(new BlockPos(k1, (yFrom + yTo) / 2, l1))) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
	}
	
	/** An easy way to prime a chunk */
	public static void primeChunk(ChunkPrimer primer, IBlockState[] state) {
		int i = 0;
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				for(int y = 0; y < 256; y++) {
					if(i < state.length) primer.setBlockState(x, y, z, state[i++]);
				}
			}
		}
	}
	
	/** Get the closest player to a point in the world */
    public static EntityPlayer getClosestPlayer(World world, double x, double y, double z, double distance) {
        double d0 = -1.0D;
        EntityPlayer entityplayer = null;

        for (int i = 0; i < world.playerEntities.size(); ++i)
        {
            EntityPlayer entityplayer1 = (EntityPlayer)world.playerEntities.get(i);

            if (EntitySelectors.NOT_SPECTATING.apply(entityplayer1))
            {
                double d1 = entityplayer1.getDistanceSq(x, y, z);

                if ((distance < 0.0D || d1 < distance * distance) && (d0 == -1.0D || d1 < d0))
                {
                    d0 = d1;
                    entityplayer = entityplayer1;
                }
            }
        }

        return entityplayer;
    }
    
	/** Easy way to check block and metadata in the world */
	public static boolean blockMatches(World world, BlockPos pos, Block block, int meta) {
		IBlockState state = world.getBlockState(pos);
		return state.getBlock() == block && block.getMetaFromState(state) == meta;
	}
	
	/** Get a block from a world like 1.7.10 */
	public static Block getBlock(World world, int x, int y, int z) {
		return world.getBlockState(new BlockPos(x, y, z)).getBlock();
	}
	
	/**
     * Returns the closest vulnerable player within the given radius, or null if none is found.
     */
    public static EntityPlayer getClosestVulnerablePlayer(World world, double x, double y, double z, double dist, List<UUID> excludePlayers)
    {
        double d4 = -1.0D;
        EntityPlayer entityplayer = null;

        for (int i = 0; i < world.playerEntities.size(); ++i)
        {
            EntityPlayer entityplayer1 = (EntityPlayer)world.playerEntities.get(i);

            if (entityplayer1 != null && !excludePlayers.contains(entityplayer1.getUniqueID()) && !entityplayer1.capabilities.disableDamage && entityplayer1.isEntityAlive())
            {
                double d5 = entityplayer1.getDistanceSq(x, y, z);
                double d6 = dist;

                if (entityplayer1.isSneaking())
                {
                    d6 = dist * 0.800000011920929D;
                }

                if (entityplayer1.isInvisible())
                {
                    float f = entityplayer1.getArmorVisibility();

                    if (f < 0.1F)
                    {
                        f = 0.1F;
                    }

                    d6 *= 0.7F * f;
                }

                if ((dist < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4))
                {
                    d4 = d5;
                    entityplayer = entityplayer1;
                }
            }
        }

        return entityplayer;
    }
    
    /**
     * Returns the closest vulnerable entity within the given radius, or null if none is found.
     */
    public static Entity getClosestVulnerableEntityOfType(World world, double x, double y, double z, double dist, Class type, Class... excludes)
    {
        double d4 = -1.0D;
        Entity entity = null;

        for (int i = 0; i < world.loadedEntityList.size(); ++i)
        {
            Entity entity1 = (Entity)world.loadedEntityList.get(i);

            if (entity1 != null && type.isInstance(entity1) && !entity1.isEntityInvulnerable(DamageSource.generic) && entity1.isEntityAlive())
            {
            	// ignore the excludes
            	boolean skip = false;
            	for(Class c : excludes) {
            		if(c.isInstance(entity1)) skip = true;
            	}
            	if(!skip) {
            		
	                double d5 = entity1.getDistanceSq(x, y, z);
	                double d6 = dist;
	
	                if (entity1.isSneaking())
	                {
	                    d6 = dist * 0.800000011920929D;
	                }
	
	                if (entity1.isInvisible())
	                {
	                	float f;
	                	if(entity1 instanceof EntityPlayer) {
	                		f = ((EntityPlayer)entity1).getArmorVisibility();
	                	} else {
	                		f = 0;
	                	}
	
	                    if (f < 0.1F)
	                    {
	                        f = 0.1F;
	                    }
	
	                    d6 *= 0.7F * f;
	                }
	
	                if ((dist < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4))
	                {
	                    d4 = d5;
	                    entity = entity1;
	                }
	            }
            }
        }

        return entity;
    }

	/** gets the area height near your position */
	public static Integer getOpenSurface(World world, int x, int z, int starty, int distance, boolean onSolidBlock) {
		starty += 2;
		for(int y = starty + distance; y >= starty - distance; y--) {
			Block block;
			if(getBlock(world, x, y + 2, z) == Blocks.AIR && getBlock(world, x, y + 1, z) == Blocks.AIR && (block = getBlock(world, x, y, z)) != Blocks.AIR) {
				if(!LCMisc.inArray(block, blacklist) && (onSolidBlock == false || block.isOpaqueCube(world.getBlockState(new BlockPos(x, y, z))))) {
					if(y < Math.max(world.getActualHeight() - 1, starty)) { // stay off the bedrock ceiling
						return y + 1;
					}
				}
			}
		}
		return null;
	}
	
	/** gets the area height near your position */
	public static Integer getOpenSurfaceReverse(World world, int x, int z, int starty, int distance, boolean onSolidBlock) {
		starty += 2;
		for(int y = starty - distance; y <= starty + distance; y++) {
			Block block;
			if(getBlock(world, x, y + 2, z) == Blocks.AIR && getBlock(world, x, y + 1, z) == Blocks.AIR && (block = getBlock(world, x, y, z)) != Blocks.AIR) {
				if(!LCMisc.inArray(block, blacklist) && (onSolidBlock == false || block.isOpaqueCube(world.getBlockState(new BlockPos(x, y, z))))) {
					if(y < Math.max(world.getActualHeight() - 1, starty)) { // stay off the bedrock ceiling
						return y + 1;
					}
				}
			}
		}
		return null;
	}
	
	/** gets the area height near your position */
	public static Integer getOpenSurfaceCentered(World world, int x, int z, int starty, int distance, boolean onSolidBlock) {
		Integer y;
		y = getOpenSurfaceReverse(world, x, z, starty - distance / 2, distance / 2, onSolidBlock);
		if(y == null) y = getOpenSurface(world, x, z, starty + distance / 2, distance / 2, onSolidBlock);
		return y;
	}
	
	/** gets the area height near your position */
	public static Integer getOpenSurface(World world, int x, int z, int starty, int distance) {
		return getOpenSurface(world, x, z, starty, distance, false);
	}
	
	/** gets the area height near your position */
	public static Integer getOpenCeiling(World world, int x, int z, int starty, int endy, int airBlocks) {
		for(int y = endy; y >= starty; y--) {
			if(canSpawnAtCeilingPosition(world, x, y - 1, z, airBlocks)) {
				if(!LCMisc.inArray(getBlock(world, x, y, z), blacklist)) {
					if(y < Math.max(world.getActualHeight() - 1, starty)) { // stay off the bedrock ceiling
						return y - 1;
					}
				}
			}
		}
		return null;
	}
	
	/** Is there airAbove blocks above the position, and is it on onBlock? */
	public static boolean canSpawnAtPosition(World world, int x, int y, int z, Block onBlock, int airAbove) {
		if(getBlock(world, x, y - 1, z) == onBlock) {
			for(int i = 0; i < airAbove; i++) {
				if(getBlock(world, x, y + i, z) != Blocks.AIR) return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
	/** Is there airAbove blocks above the position? */
	public static boolean canSpawnAtPosition(World world, int x, int y, int z, int airAbove) {
		if(getBlock(world, x, y - 1, z) != Blocks.AIR) {
			for(int i = 0; i < airAbove; i++) {
				if(getBlock(world, x, y + i, z) != Blocks.AIR) return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
	/** Give us some wiggle room */
	public static boolean canSpawnAtPositionLoosely(World world, int x, int y, int z, int airAbove, int marginDown, int marginUp) {
		for(int j = y - marginDown; j <= y + marginUp; j++) {
			if(canSpawnAtPosition(world, x, j, z, airAbove)) {
				return true;
			}
		}
		return false;
	}
	
	/** Is there airAbove blocks below the position? */
	public static boolean canSpawnAtCeilingPosition(World world, int x, int y, int z, int airBelow) {
		if(getBlock(world, x, y + 1, z) != Blocks.AIR) {
			for(int i = 0; i < airBelow; i++) {
				if(getBlock(world, x, y - i, z) != Blocks.AIR) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/** gets an entity from UUID value */
	public static Entity getEntityFromUUID(World world, UUID id) {
		for (Entity e : (List<Entity>)world.loadedEntityList) {
			if (e.getPersistentID().toString().equals(id.toString())) {
				return e;
			}
		}
		return null;
	}
	
	/** gets an entity living base from UUID value */
	public static EntityLivingBase getEntityLivingBaseFromUUID(World world, UUID id, boolean isPlayer) {
		Entity e;
		
		if(!isPlayer) {
			e = getEntityFromUUID(world, id);
		} else {
			e = getEntityPlayerFromUUID(world, id);
		}
		
		if(e instanceof EntityLivingBase) {
			return (EntityLivingBase)e;
		} else {
			return null;
		}
	}
	
	/** gets an entity living base from UUID value */
	public static EntityLivingBase getEntityLivingBaseFromUUID(World world, UUID id) {
		return getEntityLivingBaseFromUUID(world, id, false);
	}
	
	/** gets an entity living base from UUID value */
	public static EntityPlayer getEntityPlayerFromUUID(World world, UUID id) {
		for (EntityPlayer e : (List<EntityPlayer>)world.playerEntities) {
			if (e.getPersistentID().toString().equals(id.toString())) {
				return e;
			}
		}
		return null;
	}
	
	/** Spawn a hostile mob near the player in between mindist and maxdist at a random angle
	 * @return Returns true if the mob was successfully spawned
	 */
	public static boolean spawnMobNearPlayer(EntityPlayer player, EntityMob mob, double mindist, double maxdist) {
		// try this distance and angle
		double dd = maxdist - mindist;
		double dist = mindist + random.nextDouble() * dd;
		float angle = (float)(random.nextDouble() * 2 * Math.PI);
		
		// get corresponding position
		double x = player.posX - MathHelper.sin(angle) * dist;
		double z = player.posZ + MathHelper.cos(angle) * dist;
		Integer y = getOpenSurface(player.worldObj, (int)x, (int)z, (int)player.posY, 10, true);
		if(y == null) return false;
		
		// spawning
		if(player.worldObj.getCombinedLight(new BlockPos((int)x, y, (int)z), 0) <= 7) {
			mob.setPositionAndUpdate(x, y, z);
			return player.worldObj.spawnEntityInWorld(mob);
		}
		
		return false;
	}
	
}
