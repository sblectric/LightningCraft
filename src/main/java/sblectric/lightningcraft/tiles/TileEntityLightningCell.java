package sblectric.lightningcraft.tiles;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import sblectric.lightningcraft.api.capabilities.implementation.BaseLightningUpgradable;
import sblectric.lightningcraft.blocks.BlockAirTerminal;
import sblectric.lightningcraft.entities.EntityLCLightningBolt;
import sblectric.lightningcraft.init.LCAchievements;
import sblectric.lightningcraft.init.LCCapabilities;
import sblectric.lightningcraft.ref.Metal.Rod;
import sblectric.lightningcraft.util.Effect;
import sblectric.lightningcraft.util.WeatherUtils;
import sblectric.lightningcraft.util.WorldUtils;

/** The power cell tile entity */
public class TileEntityLightningCell extends TileEntityBase {

	// public fields
	public double storedPower;
	public double maxPower;
	public double efficiency;
	public int cooldownTime;
	public String cellName;
	public boolean creative;

	// private fields
	private boolean topTierTerminal = false;
	private boolean didCheck = false;

	/** The power cell tile entity */
	public TileEntityLightningCell(double mp, String name, boolean creative) {
		this.maxPower = mp;
		this.cellName = name;
		if(creative) {
			this.creative = creative;
			this.storedPower = this.maxPower;
		}
	}

	public TileEntityLightningCell() {}

	@Override
	public void update() {
		// variables
		EntityLCLightningBolt lightning;
		boolean dosave = false;

		// clientside first
		if (worldObj.isRemote) {
			isAirTerminalPresent(); // update efficiency info
			return;
		}

		// now serverside

		// normalize stored power
		if(this.storedPower > this.maxPower) this.storedPower = this.maxPower;
		if(this.storedPower < 0) this.storedPower = 0;
		if(this.creative) this.storedPower = this.maxPower;

		// top tier achievement
		if(this.didCheck == false && this.maxPower == 30000 && this.topTierTerminal) {
			EntityPlayerMP player = (EntityPlayerMP)WorldUtils.getClosestPlayer(worldObj, getX(), getY(), getZ(), 16);
			if(player != null && player.getStatFile().canUnlockAchievement(LCAchievements.perfectCell)) {
				player.addStat(LCAchievements.perfectCell, 1);
				this.didCheck = true;
			}
		}

		// cooldown time
		if(this.cooldownTime > 0) { 
			this.cooldownTime--;
			dosave = true;
		}

		// make sure there's an air terminal above the cell
		if(isAirTerminalPresent()) {

			// random chance of lightning strike (1/100000 of a chance per tick)
			// goes up to 1/1000 when it's storming
			double chance = 1D/100000D;
			if(this.worldObj.isThundering()) chance = 1D/1000D;
			if(random.nextDouble() <= chance && this.storedPower < this.maxPower - 100D * this.efficiency) {
				Effect.lightningGen(this.worldObj, pos.up());
			}

			// get lightning strikes near it (internal / external source)
			// near = within 5 block radius XZ / 3 block radius Y
			AxisAlignedBB box = new AxisAlignedBB(this.getX() - 5, this.getY() - 2, this.getZ() - 5, this.getX() + 5, this.getY() + 4, this.getZ() + 5);
			List<EntityLightningBolt> bolts = WeatherUtils.getLightningBoltsWithinAABB(this.worldObj, box);

			// lightning has struck the air terminal, now charge the lightning cell!
			// then comes the 2 second cooldown
			// don't process if the cell is full!
			if(!bolts.isEmpty() && this.cooldownTime <= 0 && this.storedPower < this.maxPower - 100D * this.efficiency) {

				// remove the entities from the world (don't charge multiple cells!!!)
				for(EntityLightningBolt bolt : bolts) {
					if(bolt.isDead) return; // stop executing the function now!
					this.worldObj.removeEntity(bolt);
				}

				this.storedPower += 100D * this.efficiency;
				this.cooldownTime = 40;
				dosave = true;
			}
		}

		// force a save if the state changes
		if(dosave) this.markDirty();

	}
	
	// upgrade behavior
	private BaseLightningUpgradable upgrade = new BaseLightningUpgradable() { // modified basic implementation
		@Override
		public EnumActionResult onLightningUpgrade(ItemStack stack, EntityPlayer player, World world, BlockPos pos, 
				EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			setUpgraded(true);
			maxPower *= 1.5;
			cellName += " (Upgr.)";
			return EnumActionResult.SUCCESS; // on success, uses an upgrade
		}
	};
	
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

	/** Is this cell upgraded? */
	public boolean isUpgraded() {
		return upgrade.isUpgraded();
	}

	/** Set the cell to an upgraded state */
	public void setUpgraded(boolean upgraded) {
		upgrade.setUpgraded(upgraded);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1) {
		super.writeToNBT(par1);
		par1.setDouble("storedPower", this.storedPower);
		par1.setDouble("maxPower", this.maxPower);
		par1.setInteger("cooldownTime", this.cooldownTime);
		par1.setBoolean("topTierCheck", this.didCheck);
		par1.setString("customName", this.cellName);
		par1.setBoolean("isCreative", this.creative);
		upgrade.serializeNBT(par1);
		return par1;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1) {
		super.readFromNBT(par1);
		this.storedPower = par1.getDouble("storedPower");
		this.maxPower = par1.getDouble("maxPower");
		this.cooldownTime = par1.getInteger("cooldownTime");
		this.didCheck = par1.getBoolean("topTierCheck");
		this.cellName = par1.getString("customName");
		this.creative = par1.getBoolean("isCreative");
		upgrade.deserializeNBT(par1);
	}

	/** checks to see if there is an air terminal on top (also sets the efficiency) */
	public boolean isAirTerminalPresent() {
		// get the block above
		this.topTierTerminal = false;
		IBlockState state = this.worldObj.getBlockState(getPos().up());
		Block test = state.getBlock();
		int meta = test.getMetaFromState(state);
		boolean flag;

		if(test instanceof BlockAirTerminal) {
			flag = true;
			this.efficiency = ((BlockAirTerminal)test).getEfficiency(state);
			if(meta == Rod.MYSTIC) this.topTierTerminal = true;
		} else {
			flag = false;
			this.efficiency = 0;
		}
		return flag;
	}

}
