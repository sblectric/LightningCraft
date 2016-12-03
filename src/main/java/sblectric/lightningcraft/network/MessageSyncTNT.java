package sblectric.lightningcraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sblectric.lightningcraft.entities.EntityLCTNTPrimed;
import sblectric.lightningcraft.ref.RefMisc;

/** Send a message from server to client to tell it what variant of TNT will blow up */
public class MessageSyncTNT implements IMessage {
	
	private int uid, variant;
	
	public MessageSyncTNT() { }
	
	public MessageSyncTNT(EntityLCTNTPrimed entity) {
		this.uid = entity.getEntityId();
		this.variant = entity.variant;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		this.uid = tag.getInteger("uid");
		this.variant = tag.getInteger("variant");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("uid", this.uid);
		tag.setInteger("variant", this.variant);
		ByteBufUtils.writeTag(buf, tag);
	}
	
	/** spawn the particle */
	public static class Handler implements IMessageHandler<MessageSyncTNT, IMessage> {
        @Override
        public IMessage onMessage(MessageSyncTNT m, MessageContext ctx) {
        	if(RefMisc.DEBUG) System.out.println("Syncing TNT type for rendering");
        	Minecraft.getMinecraft().addScheduledTask(() -> handle(m));
            return null; // no response in this case
        }
        
        /** Do the sync */
        public void handle(MessageSyncTNT m) {
        	EntityLCTNTPrimed t = (EntityLCTNTPrimed)Minecraft.getMinecraft().theWorld.getEntityByID(m.uid);
        	t.variant = m.variant;
        }
        
	}

}
