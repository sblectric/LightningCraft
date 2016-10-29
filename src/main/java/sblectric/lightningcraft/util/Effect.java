package sblectric.lightningcraft.util;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import sblectric.lightningcraft.entities.EntityLCLightningBolt;
import sblectric.lightningcraft.init.LCNetwork;
import sblectric.lightningcraft.init.LCParticles;
import sblectric.lightningcraft.network.MessageSpawnParticle;

public class Effect {
	
	private static Random random = new Random();
	
	/** generated lightning */
	public static boolean lightningGen(World world, BlockPos pos) {
		if(!world.isRemote) {	
			 EntityLCLightningBolt lightning = new EntityLCLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false);
			 lightning.setPosition(pos.getX(), pos.getY() - 0.5, pos.getZ());
			 world.addWeatherEffect(lightning);
			 return true;
		}
		return false;
	}
	
	/** lightning summon code! (within +/-10 blocks Y) */
	public static boolean lightning(World world, double x, double y, double z, boolean isRandom, double range)
	{
		if(!world.isRemote) {
			double xoff;
			double zoff;
			if(isRandom) {
				xoff = random.nextDouble() * range * 2 - range; // 16 block range (default)
				zoff = random.nextDouble() * range * 2 - range;
			} else {
				xoff = 0;
				zoff = 0;
			}
			
			 x += xoff;
			 z += zoff;
			 
			 // seek a surface?
			 if(isRandom) {
				 Integer yy = WorldUtils.getOpenSurface(world, (int)x, (int)z, (int)y, 10); 
				 if(yy == null) return false;
				 y = yy;
			 }			 
	
			 EntityLCLightningBolt lightning = new EntityLCLightningBolt(world, x, y, z, true);
			 lightning.setPosition(x, y - 0.5, z);
			 world.addWeatherEffect(lightning);
			 return true;
		}
		return false;
	}
	
	/** lightning summon code! (within +/-10 blocks Y) */
    public static boolean lightning(World world, double x, double y, double z){
		return lightning(world, x, y, z, false, 0);
	}
	
	/** lightning summon code! (within +/-10 blocks Y) */
	public static boolean lightning(Entity entity, boolean isRandom, double range)
	{
		if(!entity.worldObj.isRemote) {
			double xoff;
			double zoff;
			if(isRandom) {
				xoff = random.nextDouble() * range*2 - range; // 16 block range (default)
				zoff = random.nextDouble() * range*2 - range;
			} else {
				xoff = 0;
				zoff = 0;
			}
			
			 World world = entity.worldObj;
			 double x = entity.posX + xoff;
			 double z = entity.posZ + zoff;
			 double y;
			 
			 // seek a surface?
			 if(isRandom) {
				 Integer yy = WorldUtils.getOpenSurface(world, (int)x, (int)z, (int)entity.posY, 10);
				 if(yy == null) return false;
				 y = yy;
			 } else {
				 y = entity.posY;
			 }			 
	
			 EntityLCLightningBolt lightning = new EntityLCLightningBolt(world, x, y, z, true);
			 lightning.setPosition(x, y - 0.5, z);
			 world.addWeatherEffect(lightning);
			 return true;
		}
		return false;
	}
	
	/** lightning summon code! (within +/-10 blocks Y) */
    public static boolean lightning(Entity entity, boolean isRandom){
		return lightning(entity, isRandom, 16);
	}
	
    /** special sword particle effect */
    public static void specialSword(String name, World world, double x, double y, double z) {
    	int dim = world.provider.getDimension();
    	for(int i = 0; i < LCParticles.swordParticleCount; i++) {
			float xoff = (random.nextFloat() - 0.5F) * LCParticles.swordParticleSpread;
			float yoff = (random.nextFloat() + 0.0F)  * LCParticles.swordParticleSpread;
			float zoff = (random.nextFloat() - 0.5F)  * LCParticles.swordParticleSpread;
			LCNetwork.net.sendToAllAround(new MessageSpawnParticle(name, x + xoff, y + yoff, z + zoff, 0, 0, 0), new TargetPoint(dim, x, y, z, 1024));
		}
    }
    
    /** summon enderman particles and play teleport sound (start / end configurable)*/
    public static void ender(World world, double x, double y, double z, double tx, double ty, double tz, boolean sound2x) {
    	short nParticles = 64;
    	float width = 1;
    	float height = 2;
    	int dim = world.provider.getDimension();

        for (int l = 0; l < nParticles; ++l)
        {
            double d6 = l / (nParticles - 1.0D);
            float f = (random.nextFloat() - 0.5F) * 0.2F;
            float f1 = (random.nextFloat() - 0.5F) * 0.2F;
            float f2 = (random.nextFloat() - 0.5F) * 0.2F;
            double d7 = tx + (x - tx) * d6 + (random.nextDouble() - 0.5D) * width * 2.0D;
            double d8 = ty + (y - ty) * d6 + random.nextDouble() * height;
            double d9 = tz + (z - tz) * d6 + (random.nextDouble() - 0.5D) * width * 2.0D;
            LCNetwork.net.sendToAllAround(new MessageSpawnParticle("portal", d7, d8, d9, f, f1, f2), new TargetPoint(dim, d7, d8, d9, 1024));
        }
        
		float pitch = 0.9F + random.nextFloat() * 0.2F;
		world.playSound(null, x , y, z, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, pitch);
		if(sound2x) world.playSound(null, tx , ty, tz, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, pitch);
        
    }
    
    /** summon enderman particles and play teleport sound (plays sound in start and end location) */
    public static void ender(World world, double x, double y, double z, double tx, double ty, double tz) {
    	ender(world, x, y, z, tx, ty, tz, true);
    }

}
