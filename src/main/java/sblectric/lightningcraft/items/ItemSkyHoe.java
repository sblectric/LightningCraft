package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.items.base.ItemHoeLC;
import sblectric.lightningcraft.ref.LCText;

/** The Skyfather hoe */
public class ItemSkyHoe extends ItemHoeLC {

	public ItemSkyHoe(ToolMaterial mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		list.add(LCText.getQuickFarmLore());
	}
	
	/** hoe three blocks in a row, centered on the block you're hoeing */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);

		// when sneaking, this hoe acts normal
		if(player.isSneaking()) {
			return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		}

		// get the direction
		int xMode = 1;
		int zMode = 0;
		int direction = MathHelper.floor(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if(direction == 0 || direction == 2) {
			xMode = 0;
			zMode = 1;
		}
		
		// check if the operation is possible
		for(int i = -1; i <= 1; i++) {
			Block block = world.getBlockState(pos.add(i * xMode, 0, i * zMode)).getBlock();
			if (!(facing != EnumFacing.DOWN && 
					world.getBlockState(pos.add(i * xMode, 1, i * zMode)).getBlock().isReplaceable(world, pos.add(i * xMode, 1, i * zMode)) &&
					(block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.FARMLAND))) {
				if(i == 0) {
					return EnumActionResult.FAIL;
				} else { // on failure of quick farm, revert to normal functionality
					return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
				}
			}
		}
		
		boolean flag = false;
		// 5x5 mode
		if(world.getBlockState(pos).getBlock() == Blocks.FARMLAND) {
			for(int i = -2; i <= 2; i++) {
				for(int j = -2; j <= 2; j++) {
					flag |= super.onItemUse(player, world, pos.add(i, 0, j), hand, facing, hitX, hitY, hitZ) == EnumActionResult.SUCCESS;
				}
			}
		// 1x3 mode
		} else {
			for(int i = -1; i <= 1; i++) {
				flag |= super.onItemUse(player, world, pos.add(i * xMode, 0, i * zMode), hand, facing, hitX, hitY, hitZ) == EnumActionResult.SUCCESS;
			}
		}
		return flag ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
	}

}
