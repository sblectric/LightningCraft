package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.items.base.ItemMeta;
import sblectric.lightningcraft.util.ISimpleLEUser;
import sblectric.lightningcraft.util.InventoryLE;
import sblectric.lightningcraft.util.InventoryLE.LECharge;

/** Item magnets */
public class ItemMagnet extends ItemMeta implements ISimpleLEUser {
	
	public static final double lpPerItemPerTick = 0.1D;
	public static final double t1Range = 8;
	public static final int nMagnets = 4;
	
	public ItemMagnet() {
		super(nMagnets);
		this.setMaxStackSize(1);
	}
	
	// item lore
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		LECharge charge = new LECharge();
		boolean charged = InventoryLE.addInformation(stack, player, list, par4, charge);
		String tier = "Range: " + (int)getRange(stack) + " blocks";
		list.add(tier);
	}
	
	// Magnet methods
	
	/** Start using it! */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    	player.setActiveHand(hand);
    	return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
    
    /** get the range of the magnet */
	public static double getRange(ItemStack stack) {
		return stack.isEmpty() ? 0 : (stack.getItemDamage() + 1) * t1Range;
	}
    
    /** Main usage method */
    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
    	World world = player.world;
    	if(!world.isRemote) {
    		
    		// draw items within a certain range towards the player
    		double range = getRange(stack);
    		AxisAlignedBB box = 
    				new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range);
    		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, box);
    		
    		// loop through the items
    		for(EntityItem item : items) {
    			
    			// check for available LP
    			ItemStack battery;
    			if((battery = getLESource((EntityPlayer)player, lpPerItemPerTick)).isEmpty()) {
    				break; // out of LP, no more magnetism
    			} else {
    				ItemBattery.addStoredPower(battery, -lpPerItemPerTick);
    			}
    			
    			double dx = player.posX - item.posX;
    			double dy = player.posY - item.posY;
    			double dz = player.posZ - item.posZ;
    			double dm = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
    			double vel = 0.3D;
    			
    			item.motionX = vel * dx / dm;
    			item.motionY = vel * dy / dm;
    			item.motionZ = vel * dz / dm;
    			item.velocityChanged = true;
    		}
    	
    	}
    }
    
    @Override
	public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
	public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }
    
}
