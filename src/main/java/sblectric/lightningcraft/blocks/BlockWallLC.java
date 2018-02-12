package sblectric.lightningcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import sblectric.lightningcraft.api.registry.ILightningCraftBlock;
import sblectric.lightningcraft.ref.RefMisc;

/** Class for walls */
public class BlockWallLC extends BlockWall implements ILightningCraftBlock {
	
	private boolean redoState = false;

	public BlockWallLC() {
		super(Blocks.STONE);
		redoState = true;
		ReflectionHelper.setPrivateValue(Block.class, this, createBlockState(), RefMisc.DEV ? "blockState" : "field_176227_L");
		this.setDefaultState(this.blockState.getBaseState().withProperty(UP, false).withProperty(NORTH, false).withProperty(EAST, false).
				withProperty(SOUTH, false).withProperty(WEST, false));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList list) {
		list.add(new ItemStack(this));
	}
	
	@Override
    protected BlockStateContainer createBlockState() {
		if(redoState) {
			return new BlockStateContainer(this, new IProperty[]{UP, NORTH, EAST, WEST, SOUTH});
		} else {
			return super.createBlockState();
		}
    }
	
    /**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
    
    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

}
