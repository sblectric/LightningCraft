package sblectric.lightningcraft.gui.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sblectric.lightningcraft.gui.server.ContainerLightningReceiver;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.tiles.TileEntityLightningReceiver;

/** The lightning energy receiver GUI */
@SideOnly(Side.CLIENT)
public class GuiLightningReceiver extends GuiContainer {
	
	private static final ResourceLocation lpRXGuiTextures = new ResourceLocation(RefStrings.MODID, "textures/gui/container/lpreceiver.png");
	private TileEntityLightningReceiver rx;

	public GuiLightningReceiver(InventoryPlayer invPlayer, TileEntityLightningReceiver tile) {
		super(new ContainerLightningReceiver(invPlayer, tile));
		this.rx = tile;
		this.ySize = 198;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		
		// bar status
		int width = 125;
		int height = 16;
		int texty = 36;
		
		String string = rx.getName();
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) / 2, 6, 0x404040);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 0x404040);

		if(rx.efficiency > -1) {
			Gui.drawRect(25, 17, 25 + (int) (rx.storedPower/rx.maxPower * width), 17 + height, 0xffffffff);
			
			this.fontRendererObj.drawString("Linked LE:", 24, texty, 0x404040);
			this.fontRendererObj.drawString("Capacity:", 24, texty + 10, 0x404040);
			this.fontRendererObj.drawString("Efficiency:", 24, texty + 20, 0x404040);
			this.fontRendererObj.drawString("Tx Distance: ", 24, texty + 30, 0x404040);

			String storedPower = rx.storedPower + " LE";
			String maxPower = (int)rx.maxPower + " LE";
			String efficiency = LCText.df.format(rx.efficiency * 100D) + "%";
			double deltaX = rx.txPos.getX() - rx.getX();
			double deltaY = rx.txPos.getY() - rx.getY();
			double deltaZ = rx.txPos.getZ() - rx.getZ();
			String distance = LCText.df.format(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ)) + "m";
			String position = "x: " + rx.txPos.getX() + ", y: " + rx.txPos.getY() + ", z: " + rx.txPos.getZ();
			
			this.fontRendererObj.drawString(storedPower, 151 - this.fontRendererObj.getStringWidth(storedPower), texty, 0x404040);
			this.fontRendererObj.drawString(maxPower, 151 - this.fontRendererObj.getStringWidth(maxPower), texty + 10, 0x404040);
			this.fontRendererObj.drawString(efficiency, 151 - this.fontRendererObj.getStringWidth(efficiency), texty + 20, 0x404040);
			this.fontRendererObj.drawString(distance, 151 - this.fontRendererObj.getStringWidth(distance), texty + 30, 0x404040);
			this.fontRendererObj.drawString(position, 151 - this.fontRendererObj.getStringWidth(position), texty + 40, 0x606060);
		} else if(rx.outOfRange) {
			this.fontRendererObj.drawString("Transmitter Out of Range", 24, texty, 0xC00000);
		} else {
			this.fontRendererObj.drawString("No Valid Transmitter Found", 24, texty, 0xC00000);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(lpRXGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

}