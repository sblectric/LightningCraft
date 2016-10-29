package sblectric.lightningcraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.init.LCCapabilities;
import sblectric.lightningcraft.ref.RefMisc;

/** Send a message from server to clients to let them know a nearby machine was upgraded */
public class MessageLightningUpgrade implements IMessage {
	
	private BlockPos pos;
	private boolean upgraded;
	
	public MessageLightningUpgrade() {}
	
	public MessageLightningUpgrade(BlockPos pos) {
		this(pos, true);
	}
	
	public MessageLightningUpgrade(BlockPos pos, boolean upgraded) {
		this.pos = pos;
		this.upgraded = upgraded;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		pos = BlockPos.fromLong(tag.getLong("pos"));
		upgraded = tag.getBoolean("upgraded");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setLong("pos", pos.toLong());
		tag.setBoolean("upgraded", upgraded);
		ByteBufUtils.writeTag(buf, tag);
	}
	
	/** Update the upgraded state */
	public static class Handler implements IMessageHandler<MessageLightningUpgrade, IMessage> {
        @Override
        public IMessage onMessage(MessageLightningUpgrade m, MessageContext ctx) {
        	Minecraft.getMinecraft().addScheduledTask(() -> handle(m));
            return null; // no response in this case
        }
        
        /** Actually perform the upgrade client-side */
        @SideOnly(Side.CLIENT)
        public void handle(MessageLightningUpgrade m) {
        	World world = Minecraft.getMinecraft().theWorld;
        	TileEntity tile = world.getTileEntity(m.pos);
        	if(tile != null && tile.hasCapability(LCCapabilities.LIGHTNING_UPGRADABLE, null)) {
        		tile.getCapability(LCCapabilities.LIGHTNING_UPGRADABLE, null).setUpgraded(m.upgraded);
        		if(RefMisc.DEBUG) System.out.println("Machine upgraded at " + m.pos);
        	}
        }
	}

}
