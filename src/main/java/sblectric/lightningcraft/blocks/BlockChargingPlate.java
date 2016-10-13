package sblectric.lightningcraft.blocks;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumRarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sblectric.lightningcraft.api.registry.ILightningCraftBlock;
import sblectric.lightningcraft.items.blocks.ItemBlockRarity;
import sblectric.lightningcraft.tiles.TileEntityChargingPlate;

/** The charging plate */
public class BlockChargingPlate extends BlockPressurePlate implements ILightningCraftBlock {

	public BlockChargingPlate() {
		super(Material.IRON, Sensitivity.MOBS);
	}

	// it definitely has a tile entity
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	// Like a regular pressure plate, but provides charging power
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityChargingPlate();
	}

	@Override
	public Class getItemClass() {
		return ItemBlockRarity.class;
	}

	@Override
	public Object[] getItemClassArgs() {
		return new Object[]{EnumRarity.UNCOMMON};
	}

}
