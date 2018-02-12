package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.items.base.ItemLC;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.tiles.TileEntityLightningReceiver;
import sblectric.lightningcraft.tiles.TileEntityLightningTransmitter;

/** The wireless joining item */
public class ItemWirelessMarker extends ItemLC {

	public ItemWirelessMarker() {
		super();
		this.setMaxStackSize(1);
	}

	/** Save a transmitter location and give it to a reciever */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		ItemStack stack = player.getHeldItem(hand);

		// make the tag compound if none exists
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());

		// transmitter code
		if(tile != null && tile instanceof TileEntityLightningTransmitter) {
			TileEntityLightningTransmitter tx = (TileEntityLightningTransmitter)tile;

			// get the required data from NBT
			boolean hasMark = stack.getTagCompound().getBoolean("hasMark");
			int txX, txY, txZ;

			// mark the position
			if(!hasMark) {
				if(!world.isRemote) {
					hasMark = true; // has position
					txX = tx.getX();
					txY = tx.getY();
					txZ = tx.getZ();
					stack.getTagCompound().setBoolean("hasMark", hasMark);
					stack.getTagCompound().setInteger("txX", txX);
					stack.getTagCompound().setInteger("txY", txY);
					stack.getTagCompound().setInteger("txZ", txZ);
				}
				return EnumActionResult.SUCCESS;
			}

			// receiver code
		} else if(tile != null && tile instanceof TileEntityLightningReceiver) {
			TileEntityLightningReceiver rx = (TileEntityLightningReceiver)tile;

			// get the required data from NBT
			boolean hasMark = stack.getTagCompound().getBoolean("hasMark");
			int txX = stack.getTagCompound().getInteger("txX");
			int txY = stack.getTagCompound().getInteger("txY");
			int txZ = stack.getTagCompound().getInteger("txZ");

			// save the position to the receiver
			if(hasMark) {
				if(!world.isRemote) {
					rx.txPos = new BlockPos(txX, txY, txZ);
					rx.markDirty();
					stack.getTagCompound().setBoolean("hasMark", hasMark);
				}
				return EnumActionResult.SUCCESS;
			}

		}
		return EnumActionResult.FAIL;
	}

	/** Fix creative mode tagging bug */
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
		if(!world.isRemote) {
			if(stack.getItemDamage() == 0 && stack.hasTagCompound() && stack.getTagCompound().getBoolean("hasMark")) {
				stack.setItemDamage(1);
			}
		}
	}

	/** item lore */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		String lore1, lore2;
		boolean addLore2 = true;
		if(stack.getItemDamage() == 1) {
			lore1 = LCText.secSign + "6Transmitter Marked";
			if(stack.hasTagCompound() && stack.getTagCompound().getBoolean("hasMark")) {
				int txX = stack.getTagCompound().getInteger("txX");
				int txY = stack.getTagCompound().getInteger("txY");
				int txZ = stack.getTagCompound().getInteger("txZ");			
				lore2 = "x: " + txX + ", y: " + txY + ", z: " + txZ;
			} else {
				lore2 = "";
				addLore2 = false;
			}
			list.add(lore1);
		} else {
			lore2 = "Unmarked";
		}
		if(addLore2) list.add(lore2);
	}

	/** Has the enchanted effect when a position is marked */
	@Override
	public boolean hasEffect(ItemStack stack) {
		if(stack.getItemDamage() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if(stack.getItemDamage() == 1) {
			return EnumRarity.RARE;
		} else {
			return EnumRarity.UNCOMMON;
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerRender() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}

}
