package sblectric.lightningcraft.blocks;

import java.util.List;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import sblectric.lightningcraft.items.blocks.ItemBlockAirTerminal;
import sblectric.lightningcraft.ref.Metal.Rod;
import sblectric.lightningcraft.util.JointList;

/** The air terminal class */
public class BlockAirTerminal extends BlockMeta {
	
	/** The block types */
	public static enum EnumType implements IStringSerializable {
	    IRON(Rod.IRON),
	    STEEL(Rod.STEEL),
	    LEAD(Rod.LEAD),
	    TIN(Rod.TIN),
	    ALUM(Rod.ALUM),
	    GOLD(Rod.GOLD),
	    COPPER(Rod.COPPER),
	    ELEC(Rod.ELEC),
	    SKY(Rod.SKY),
	    MYSTIC(Rod.MYSTIC);
	    
	    private static final EnumType DEFAULT = IRON;
	    private int ID;
	    private String name;
	    
	    private EnumType(int meta) {
	        this.ID = meta;
	        this.name = Rod.getNameFromMeta(meta).toLowerCase();
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
	public BlockAirTerminal(float hardness, float resistance) {
		super(Blocks.IRON_BLOCK, Rod.count, hardness, resistance, true);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 0.9375F, 0.8F);
	}
	
	/** A metal block */
	public BlockAirTerminal() {
		this(3.0f, 30.0f);
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
	
	@Override
	public Class getItemClass() {
		return ItemBlockAirTerminal.class;
	}
	
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
	
	/** Get the efficiency of this air terminal */
	public double getEfficiency(IBlockState state) {
		switch(getMetaFromState(state)) {
		case Rod.IRON:
			return 0.30;
		case Rod.STEEL:
			return 0.35;
		case Rod.LEAD:
			return 0.40;
		case Rod.TIN:
			return 0.50;
		case Rod.ALUM:
			return 0.60;
		case Rod.GOLD:
			return 0.70;
		case Rod.COPPER:
			return 0.75;
		case Rod.ELEC:
			return 0.90;
		case Rod.SKY:
			return 1.00;
		case Rod.MYSTIC:
			return 1.10;
		default:
			return 0.25; // odd case
		}
	}

}
