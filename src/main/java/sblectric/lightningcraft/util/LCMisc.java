package sblectric.lightningcraft.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.ref.Metal.Ingot;

/** Miscelleaneous helper methods */
public class LCMisc {
	
	/** Create six inventory wrappers for each inventory */
	public static SidedInvWrapper[] makeInvWrapper(ISidedInventory inv) {
		SidedInvWrapper[] wrapper = new SidedInvWrapper[6];
		for(int i = 0; i < 6; i++) {
			wrapper[i] = new SidedInvWrapper(inv, EnumFacing.getFront(i));
		}
		return wrapper;
	}
	
	/** Make an array of an object and an amount */
	public static Object[] makeArray(Object obj, int amt) {
		Object[] array = new Object[amt];
		Arrays.fill(array, obj);
		return array;
	}
	
	/** Gets an array of blocks from an array of full block registry names */
	public static Block[] getBlocksFromRegs(String[] regList) {
		Block[] blocks = new Block[regList.length];
		for(int i = 0; i < regList.length; i++) {
			blocks[i] = Block.REGISTRY.getObject(new ResourceLocation(regList[i]));
		}
		return blocks;
	}
	
	/** Get the rarity from an ItemStack */
	public static EnumRarity getRarityFromStack(ItemStack stack) {
		if(stack == null) return EnumRarity.COMMON;
		if(ItemStack.areItemStacksEqual(stack, new ItemStack(LCItems.ingot, 1, Ingot.ELEC))) {
			return EnumRarity.UNCOMMON;
		} else if(ItemStack.areItemStacksEqual(stack, new ItemStack(LCItems.ingot, 1, Ingot.SKY))) {
			return EnumRarity.RARE;
		} else if(ItemStack.areItemStacksEqual(stack, new ItemStack(LCItems.ingot, 1, Ingot.MYSTIC))) {
			return EnumRarity.EPIC;
		} else {
			return EnumRarity.COMMON;
		}
	}
	
	/** Does the double list contain check? Returns the sublist that contains check or null if it was not found */
	public static <T> List listListContains(List<List<T>> list, T check) {
		for(List l : list) {
			if(l.contains(check)) return l;
		}
		return null;
	}
	
	/** Get the position of an item in a player inventory */
	public static int posInInventory(EntityPlayer player, Item item, List<Integer> skip) {
		ItemStack[] mainInventory = player.inventory.mainInventory;
		for (Integer i = 0; i < mainInventory.length; i++) {
            if (!skip.contains(i) && mainInventory[i] != null && mainInventory[i].getItem() == item) {
                return i;
            }
        }
		return -1;
	}
	
	/** Get the position of an item in a player inventory */
	public static int posInInventory(EntityPlayer player, Item item) {
		IntList nothing = new IntList();
		return posInInventory(player, item, nothing);
	}
	
	/** is the mouse colliding with a box? */
	public static boolean mouseColl(int mouseX, int mouseY, AxisAlignedBB box) {
		if (mouseX >= box.minX && mouseX <= box.maxX && mouseY >= box.minY && mouseY <= box.maxY) {
			return true;
		} else {
			return false;
		}
	}
	
	/** is the object in the array? */
	public static boolean inArray(Object obj, Object[] array) {
		for(Object i : array) {
			if(obj == i) return true;
		}
		return false;
	}

	/** returns log2 of a specified chunk height */
	public static int log2(int chunkHeight) {
		return chunkHeight == 256 ? 8 : 7;
	}
	
	/** rotates numbers around like a shift register */
	public static int rotate(int n, int rot, int max) {
		int result = n + rot;
		while(result > max) result -= max;
		return result;
	}
	
	/** rotates numbers around like a shift register */
	public static int rotate(int n, int rot, int min, int max) {
		int result = n + rot;
		while(result > max) result -= max;
		while(result < min) result += min;
		return result;
	}
	
	/**
     * Returns the direction-shifted metadata for non-vanilla stair types.
     */
    public static int getMetadataWithOffsetStairs(EnumFacing facing, int meta) {
        if (facing == EnumFacing.SOUTH) {
            if (meta == 2) {
                return 3;
            }
            if (meta == 3) {
                return 2;
            }
        } else if (facing == EnumFacing.WEST) {
            if (meta == 0) {
                return 2;
            }
            if (meta == 1) {
                return 3;
            }
            if (meta == 2) {
                return 0;
            }
            if (meta == 3) {
                return 1;
            }
        } else if (facing == EnumFacing.EAST) {
            if (meta == 0) {
                return 2;
            }
            if (meta == 1) {
                return 3;
            }
            if (meta == 2) {
                return 1;
            }
            if (meta == 3) {
                return 0;
            }
        }
        return meta;
    }
    
    /**
     * Returns the direction-shifted metadata for chest types.
     */
    public static int getMetadataWithOffsetChest(EnumFacing facing, int meta) {
        if (facing == EnumFacing.NORTH) {
            if (meta == 2) {
                return 3;
            }
            if (meta == 3) {
                return 2;
            }
        } else if(facing == EnumFacing.WEST) {
            if (meta == 4) {
                return 2;
            }
            if (meta == 5) {
                return 3;
            }
            if (meta == 2) {
                return 5;
            }
            if (meta == 3) {
                return 4;
            }
        } else if(facing == EnumFacing.EAST) {
            if (meta == 4) {
                return 2;
            }
            if (meta == 5) {
                return 3;
            }
            if (meta == 2) {
                return 4;
            }
            if (meta == 3) {
                return 5;
            }
        }
        return meta;
    } 
    
    /** Gets the enchantments that exist on a specified item stack */
    public static List<NBTTagCompound> getEnchantments(ItemStack s) {
    	List enchList = new ArrayList<NBTTagCompound>();
    	
    	if(s != null && s.hasTagCompound()) {
    		NBTTagList enchants;
    		String ench = s.getItem() == Items.ENCHANTED_BOOK ? "StoredEnchantments" : "ench";
    		enchants = (NBTTagList) s.getTagCompound().getTag(ench);
    		
    		if(enchants != null && enchants.tagCount() > 0) {
    			for(int i = 0; i < enchants.tagCount(); i++) {
    				NBTTagCompound enchant = enchants.getCompoundTagAt(i);
    				enchList.add(enchant);
    			}
    		}
    	}
    	
    	return enchList;
    }
    
    /** Adds a list of enchantments to a specified item stack */
    public static boolean addEnchantments(ItemStack s, List<NBTTagCompound> enchList) {
    	if(s != null) {
	    	String ench = s.getItem() == Items.ENCHANTED_BOOK ? "StoredEnchantments" : "ench";
	    	if(!s.hasTagCompound()) s.setTagCompound(new NBTTagCompound());
	    	NBTTagList currentEnchants = (NBTTagList) s.getTagCompound().getTag(ench);
	    	
	    	if(currentEnchants == null) {
	    		currentEnchants = new NBTTagList();
	    	}
	    	
	    	for(int i = 0; i < enchList.size(); i++) {
	    		currentEnchants.appendTag(enchList.get(i));
	    	}
	    	
	    	s.getTagCompound().setTag(ench, currentEnchants);
	    	return true;
    	}
    	return false;
    }
}
