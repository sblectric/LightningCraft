package com.lightningcraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.lightningcraft.particles.LCParticles;
import com.lightningcraft.ref.RefMisc;

/** Send a message from server to client to spawn particles */
public class MessageSpawnParticle implements IMessage {
	
	private String name;
	private double x, y, z;
	private double vx, vy, vz;
	
	public MessageSpawnParticle() { }
	
	public MessageSpawnParticle(String name, double x, double y, double z, double vx, double vy, double vz) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		this.name = tag.getString("name");
		this.x = tag.getDouble("x");
		this.y = tag.getDouble("y");
		this.z = tag.getDouble("z");
		this.vx = tag.getDouble("vx");
		this.vy = tag.getDouble("vy");
		this.vz = tag.getDouble("vz");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("name", this.name);
		tag.setDouble("x", this.x);
		tag.setDouble("y", this.y);
		tag.setDouble("z", this.z);
		tag.setDouble("vx", this.vx);
		tag.setDouble("vy", this.vy);
		tag.setDouble("vz", this.vz);
		ByteBufUtils.writeTag(buf, tag);
	}
	
	/** spawn the particle */
	public static class Handler implements IMessageHandler<MessageSpawnParticle, IMessage> {
        @Override
        public IMessage onMessage(MessageSpawnParticle m, MessageContext ctx) {
        	if(RefMisc.DEBUG) System.out.println("Spawning particle");
        	LCParticles.spawnParticle(m.name, m.x, m.y, m.z, m.vx, m.vy, m.vz);
            return null; // no response in this case
        }
	}

}
