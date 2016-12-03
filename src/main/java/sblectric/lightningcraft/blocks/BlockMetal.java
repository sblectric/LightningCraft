package sblectric.lightningcraft.blocks;

import java.util.List;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.blocks.base.BlockMeta;
import sblectric.lightningcraft.items.blocks.ItemBlockMetal;
import sblectric.lightningcraft.ref.Metal.Ingot;

/** A metal block */
public class BlockMetal extends BlockMeta {
	
	/** The block types */
	public static enum EnumType implements IStringSerializable {
	    ELEC(Ingot.ELEC),
	    SKY(Ingot.SKY),
	    MYSTIC(Ingot.MYSTIC);
	    
	    private static final EnumType DEFAULT = ELEC;
	    private int ID;
	    private String name;
	    
	    private EnumType(int meta) {
	        this.ID = meta;
	        this.name = Ingot.getNameFromMeta(meta).toLowerCase();
	    }
	    
	    public static EnumType getTypeFromMeta(int meta) {
			for(EnumType m : EnumType.values()) {
				if(m.ID == meta) return m;
			}
			return DEFAULT;
		}
	    
	    @Override
	    public String getName() {
	        return name;
	    }
	    
	    @Override
	    public String toString() {
	        return getName();
	    }

	    public int getID() {
	        return ID;
	    }
	}
	
	/** the property */
	protected IProperty type;

	/** A metal block */
	public BlockMetal(float hardness, float resistance) {
		super(Blocks.IRON_BLOCK, Ingot.count, hardness, resistance, false);
	}
	
	/** A metal block */
	public BlockMetal() {
		this(6.5f, 40.0f);
	}
	
	@Override
	protected List<IProperty> getProperties() {
		return new JointList().join(
			type = PropertyEnum.create("variant", EnumType.class)
		);		
	}
	
	@Override
	protected void setDefaultState() {
		this.setDefaultState(this.blockState.getBaseState().withProperty(type, EnumType.DEFAULT));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return getDefaultState().withProperty(type, EnumType.getTypeFromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumType)state.getValue(type)).getID();
	}
	
	// these metal blocks can now be used as a beacon base!
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
		return true;
	}
	
	@Override
	public Class getItemClass() {
		return ItemBlockMetal.class;
	}

}
