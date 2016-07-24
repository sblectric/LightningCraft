package sblectric.lightningcraft.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockPattern.PatternHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.achievements.LCAchievements;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.dimensions.LCDimensions;
import sblectric.lightningcraft.dimensions.TeleporterLC;
import sblectric.lightningcraft.particles.LCParticles;
import sblectric.lightningcraft.ref.Log;
import sblectric.lightningcraft.registry.IRegistryBlock;
import sblectric.lightningcraft.sounds.LCSoundEvents;
import sblectric.lightningcraft.util.WorldUtils;

import com.google.common.cache.LoadingCache;

/** The underworld portal block */
public class PortalUnderworld extends BlockPortal implements IRegistryBlock {
	
	/** Save the portal status for each player */
	public static PortalStatus portalStatus = new PortalStatus();
	
	/** Try to fill a portal at this location */
	public static boolean ignitePortal(World world, BlockPos pos) {
		if(LCConfig.portalEnabled) {
			return LCBlocks.underPortal.trySpawnPortal(world, pos);
		} else {
			return false;
		}
	}
	
	/** The underworld portal block */
	public PortalUnderworld() {
		super();
		this.setBlockUnbreakable(); // no more portal breaking!
	}
	
	@Override
	public Class getItemClass() {
		return null; // no item for the portal
	}
	
	/** No blocks to show */
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {}

	/** No updates for this block */
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}
	
	/** Notify the server of a dimension change */
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity e) {
		if(e.getRidingEntity() == null && e instanceof EntityPlayerMP) {
			portalStatus.setPortal((EntityPlayerMP)e, true);
		}
	}
	
	/** Actually teleport the player here */
	public static void doTeleport(EntityPlayerMP p) {
		MinecraftServer server = p.mcServer;
		if(p.timeUntilPortal > 0) {
			p.timeUntilPortal = 10;
		} else if(p.dimension != LCDimensions.underworldID) {
			p.timeUntilPortal = 10;
			Log.logger.info("Sending player " + p.getName() + " to Underworld");
			p.addStat(LCAchievements.reachUnderworld, 1);
			server.getPlayerList().transferPlayerToDimension(p, LCDimensions.underworldID, 
					new TeleporterLC(p.dimension, server.worldServerForDimension(LCDimensions.underworldID)));
		} else {
			p.timeUntilPortal = 10;
			Log.logger.info("Sending player " + p.getName() + " to Overworld");
			server.getPlayerList().transferPlayerToDimension(p, 0, 
					new TeleporterLC(p.dimension, server.worldServerForDimension(0)));
		}
		portalStatus.setPortal(p, false); // teleporting over
	}

	/** Place the portal blocks */
	@Override
    public boolean trySpawnPortal(World worldIn, BlockPos pos) {
        Size size = new Size(worldIn, pos, EnumFacing.Axis.X);

        if (size.isSizeValid() && size.count == 0) {
            size.setPortalState();
            return true;
        } else {
            Size size1 = new Size(worldIn, pos, EnumFacing.Axis.Z);

            if (size1.isSizeValid() && size1.count == 0) {
                size1.setPortalState();
                return true;
            } else {
                return false;
            }
        }
    }
	
	@Override
	public PatternHelper createPatternHelper(World world, BlockPos pos) {
		
        EnumFacing.Axis axis = EnumFacing.Axis.Z;
        Size psize = new Size(world, pos, EnumFacing.Axis.X);
        LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.createLoadingCache(world, true);

        if (!psize.isSizeValid())
        {
            axis = EnumFacing.Axis.X;
            psize = new Size(world, pos, EnumFacing.Axis.Z);
        }

        if (!psize.isSizeValid())
        {
            return new PatternHelper(pos, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
        }
        else
        {
            int[] aint = new int[EnumFacing.AxisDirection.values().length];
            EnumFacing enumfacing = psize.face1.rotateYCCW();
            BlockPos blockpos = psize.pos.up(psize.getSizeY() - 1);

            for (EnumFacing.AxisDirection enumfacing$axisdirection : EnumFacing.AxisDirection.values())
            {
                PatternHelper phelp = new PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection ? blockpos : 
                	blockpos.offset(psize.face1, psize.getSizeX() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection, axis), 
                	EnumFacing.UP, loadingcache, psize.getSizeX(), psize.getSizeY(), 1);

                for (int i = 0; i < psize.getSizeX(); ++i)
                {
                    for (int j = 0; j < psize.getSizeY(); ++j)
                    {
                        BlockWorldState bws = phelp.translateOffset(i, j, 1);

                        if (bws.getBlockState() != null && bws.getBlockState().getBlock().getMaterial(bws.getBlockState()) != Material.AIR)
                        {
                            ++aint[enumfacing$axisdirection.ordinal()];
                        }
                    }
                }
            }

            EnumFacing.AxisDirection enumfacing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;

            for (EnumFacing.AxisDirection enumfacing$axisdirection2 : EnumFacing.AxisDirection.values())
            {
                if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()])
                {
                    enumfacing$axisdirection1 = enumfacing$axisdirection2;
                }
            }

            return new PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection1 ? blockpos : 
            	blockpos.offset(psize.face1, psize.getSizeX() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection1, axis), 
            	EnumFacing.UP, loadingcache, psize.getSizeX(), psize.getSizeY(), 1);
        }
    }
	
	/**
	 * Called when a neighboring block changes.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block neighborBlock)
	{
		EnumFacing.Axis axis = (EnumFacing.Axis)state.getValue(AXIS);

		if (axis == EnumFacing.Axis.X)
		{
			Size size = new Size(worldIn, pos, EnumFacing.Axis.X);

			if (!size.isSizeValid() || size.count < size.sizeX * size.sizeY) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
		}
		else if (axis == EnumFacing.Axis.Z)
		{
			Size size1 = new Size(worldIn, pos, EnumFacing.Axis.Z);

			if (!size1.isSizeValid() || size1.count < size1.sizeX * size1.sizeY) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(100) == 0) {
			worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 
					LCSoundEvents.portalUnderworld, SoundCategory.AMBIENT, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double d0 = (double)((float)pos.getX() + rand.nextFloat());
			double d1 = (double)((float)pos.getY() + rand.nextFloat());
			double d2 = (double)((float)pos.getZ() + rand.nextFloat());
			double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			int j = rand.nextInt(2) * 2 - 1;

			if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this) {
				d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
				d3 = (double)(rand.nextFloat() * 2.0F * (float)j);
			} else {
				d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
				d5 = (double)(rand.nextFloat() * 2.0F * (float)j);
			}

			LCParticles.spawnParticle("underPortal", d0, d1, d2, d3, d4, d5);
		}
	}
	
	/** The portal status (now with string mappings!) */
	public static class PortalStatus {
		private Map<String, Boolean> portal;
		private Map<String, Integer> cooldown;
		
		private PortalStatus() {
			portal = new HashMap();
			cooldown = new HashMap();
		}
		
		public boolean getPortal(EntityPlayerMP player) {
			if(portal.containsKey(player.getName())) return portal.get(player.getName());
			return false;
		}
		
		public void setPortal(EntityPlayerMP player, boolean doportal) {
			if(portal.containsKey(player.getName()) || doportal) {
				portal.put(player.getName(), doportal);
			}
		}
		
		public void resetCooldown(EntityPlayerMP player) {
			cooldown.put(player.getName(), LCConfig.portalCooldown);
		}
		
		public boolean checkCooldown(EntityPlayerMP player) {
			int i = cooldown.containsKey(player.getName()) ? cooldown.get(player.getName()) : 0;
			if(i <= 0) {
				return true;
			} else {
				cooldown.put(player.getName(), i - 1);
				return false;
			}
		}
		
	}

	/** Portal sizing */
	public static class Size {
		private final World world;
		private final EnumFacing.Axis axis;
		private final EnumFacing face1;
		private final EnumFacing face2;
		private int count = 0;
		private BlockPos pos;
		private int sizeY;
		private int sizeX;

		/** Portal sizing */
		public Size(World worldIn, BlockPos pos, EnumFacing.Axis axis) {
			this.world = worldIn;
			this.axis = axis;

			if (axis == EnumFacing.Axis.X) {
				this.face2 = EnumFacing.EAST;
				this.face1 = EnumFacing.WEST;
			} else {
				this.face2 = EnumFacing.NORTH;
				this.face1 = EnumFacing.SOUTH;
			}

			// get the bottom of the portal
			for (BlockPos blockpos = pos; pos.getY() > blockpos.getY() - 21 && pos.getY() > 0 && 
					this.isValidBlock(worldIn.getBlockState(pos.down()).getBlock()); pos = pos.down());

			int i = this.calcSize(pos, this.face2) - 1;

			if (i >= 0) {
				this.pos = pos.offset(this.face2, i);
				this.sizeX = this.calcSize(this.pos, this.face1);

				if (this.sizeX < 2 || this.sizeX > 21) {
					this.pos = null;
					this.sizeX = 0;
				}
			}

			if (this.pos != null) {
				this.sizeY = this.updateSize();
			}
		}

		/** Calculate the size of the portal */
		protected int calcSize(BlockPos pos, EnumFacing face) {
			int i;

			for (i = 0; i < 22; ++i) {
				BlockPos blockpos = pos.offset(face, i);

				if (!this.isValidBlock(this.world.getBlockState(blockpos).getBlock()) || 
						!WorldUtils.blockMatches(world, blockpos.down(), LCBlocks.stoneBlock, BlockStone.DEMON)) {
					break;
				}
			}

			return WorldUtils.blockMatches(world, pos.offset(face, i), LCBlocks.stoneBlock, BlockStone.DEMON) ? i : 0;
		}

		/** Returns the Y size of the portal */
		public int getSizeY() {
			return this.sizeY;
		}

		/** Returns the X size of the portal */
		public int getSizeX() {
			return this.sizeX;
		}

		/** Update the size of the portal */
		protected int updateSize() {
			label24:
				for (this.sizeY = 0; this.sizeY < 21; ++this.sizeY) {
					for (int i = 0; i < this.sizeX; ++i) {
						BlockPos blockpos = this.pos.offset(this.face1, i).up(this.sizeY);
						Block block = this.world.getBlockState(blockpos).getBlock();

						if (!this.isValidBlock(block)) {
							break label24;
						}

						if (block == LCBlocks.underPortal) {
							++this.count;
						}

						if (i == 0) {
							if(!WorldUtils.blockMatches(world, blockpos.offset(this.face2), LCBlocks.stoneBlock, BlockStone.DEMON)) {
								break label24;
							}
						} else if (i == this.sizeX - 1) {
							if(!WorldUtils.blockMatches(world, blockpos.offset(this.face1), LCBlocks.stoneBlock, BlockStone.DEMON)) {
								break label24;
							}
						}
					}
				}

			for (int j = 0; j < this.sizeX; ++j) {
				if(!WorldUtils.blockMatches(world, this.pos.offset(this.face1, j).up(this.sizeY), LCBlocks.stoneBlock, BlockStone.DEMON)) {
					this.sizeY = 0;
					break;
				}
			}
	
			if (this.sizeY <= 21 && this.sizeY >= 2) {
				return this.sizeY;
			} else {
				this.pos = null;
				this.sizeX = 0;
				this.sizeY = 0;
				return 0;
			}
		}

		/** Returns true if the specified block is a valid part of the portal */
		protected boolean isValidBlock(Block block) {
			return block.getMaterial(block.getDefaultState()) == Material.AIR || block == LCBlocks.underPortal;
		}

		/** Returns true if the portal's size is valid */
		public boolean isSizeValid() {
			return this.pos != null && this.sizeX >= 2 && this.sizeX <= 21 && this.sizeY >= 2 && this.sizeY <= 21;
		}

		/** Set the portal blocks in the frame */
		public void setPortalState() {
			for (int i = 0; i < this.sizeX; ++i) {
				BlockPos blockpos = this.pos.offset(this.face1, i);
				for (int j = 0; j < this.sizeY; ++j) {
					this.world.setBlockState(blockpos.up(j), LCBlocks.underPortal.getDefaultState().withProperty(BlockPortal.AXIS, this.axis), 2);
				}
			}
		}
	}

}
