package sblectric.lightningcraft.entities;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityLCLightningBolt extends EntityLightningBolt {

	/** Declares which state the lightning bolt is in. Whether it's in the air, hit the ground, etc. */
    private int lightningState;
    /** A random long that is used to change the vertex of the lightning rendered in RenderLightningBolt */
    public long boltVertex;
    /** Determines the time before the EntityLightningBolt is destroyed. It is a random integer decremented over time. */
    private int boltLivingTime;
    
    private boolean doSetFire;

    /** A lightning bolt that isn't annoying (quieter!) */
    public EntityLCLightningBolt(World world, double x, double y, double z, boolean setFire)
    {
    	super(world, x, 300, z, !setFire); // make the constructor not do anything really
        this.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
        this.lightningState = 2;
        this.boltVertex = this.rand.nextLong();
        this.boltLivingTime = this.rand.nextInt(3) + 1;
        this.doSetFire = setFire;
        BlockPos blockpos = new BlockPos(this);

        if (setFire && !world.isRemote && world.getGameRules().getBoolean("doFireTick") && 
        		(world.getDifficulty() == EnumDifficulty.NORMAL || world.getDifficulty() == EnumDifficulty.HARD) && world.isAreaLoaded(blockpos, 10)) {
            int i = MathHelper.floor(x);
            int j = MathHelper.floor(y);
            int k = MathHelper.floor(z);

            IBlockState state1 = world.getBlockState(new BlockPos(i, j, k));
            if (state1.getBlock().getMaterial(state1) == Material.AIR && Blocks.FIRE.canPlaceBlockAt(world, new BlockPos(i, j, k)))
            {
                world.setBlockState(new BlockPos(i, j, k), Blocks.FIRE.getDefaultState());
            }

            for (i = 0; i < 4; ++i)
            {
                j = MathHelper.floor(x) + this.rand.nextInt(3) - 1;
                k = MathHelper.floor(y) + this.rand.nextInt(3) - 1;
                int l = MathHelper.floor(z) + this.rand.nextInt(3) - 1;

                IBlockState state2 = world.getBlockState(new BlockPos(j, k, l));
                if (state2.getBlock().getMaterial(state2) == Material.AIR && Blocks.FIRE.canPlaceBlockAt(world, new BlockPos(j, k, l)))
                {
                    world.setBlockState(new BlockPos(j, k, l), Blocks.FIRE.getDefaultState());
                }
            }
        }
    }

    /**
     * Called to update the entity's position/logic.
     * This lightning is quiet!
     */
    @Override
	public void onUpdate()
    {
        if (this.lightningState == 2)
        {
        	
        	this.world.playSound(null, posX, posY, posZ, 
        			SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 3.0F, 2.8F + this.rand.nextFloat() * 0.2F);
        	this.world.playSound(null, posX, posY, posZ, 
        			SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.WEATHER, 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
        }

        --this.lightningState;

        if (this.lightningState < 0)
        {
            if (this.boltLivingTime == 0)
            {
                this.setDead();
            }
            else if (this.lightningState < -this.rand.nextInt(10))
            {
                --this.boltLivingTime;
                this.lightningState = 1;
                this.boltVertex = this.rand.nextLong();
                BlockPos blockpos = new BlockPos(this);

                if (doSetFire && !world.isRemote && world.getGameRules().getBoolean("doFireTick") && 
                		(world.getDifficulty() == EnumDifficulty.NORMAL || world.getDifficulty() == EnumDifficulty.HARD) && world.isAreaLoaded(blockpos, 10)) {
                    int i = MathHelper.floor(this.posX);
                    int j = MathHelper.floor(this.posY);
                    int k = MathHelper.floor(this.posZ);

                    IBlockState state = world.getBlockState(new BlockPos(i, j, k));
                    if (state.getBlock().getMaterial(state) == Material.AIR && Blocks.FIRE.canPlaceBlockAt(this.world, new BlockPos(i, j, k)))
                    {
                        world.setBlockState(new BlockPos(i, j, k), Blocks.FIRE.getDefaultState());
                    }
                }
            }
        }

        if (this.lightningState >= 0)
        {
            if (this.world.isRemote)
            {
                this.world.setLastLightningBolt(2);
            }
            else
            {
                double d0 = 3.0D;
                List list = this.world.getEntitiesWithinAABBExcludingEntity(this, 
                		new AxisAlignedBB(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + 6.0D + d0, this.posZ + d0));

                for (int l = 0; l < list.size(); ++l)
                {
                    Entity entity = (Entity)list.get(l);
                    if (!net.minecraftforge.event.ForgeEventFactory.onEntityStruckByLightning(entity, this))
                        entity.onStruckByLightning(this);
                }
            }
        }
    }
}
