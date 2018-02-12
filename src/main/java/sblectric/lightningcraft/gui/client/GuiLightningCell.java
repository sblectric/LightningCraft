package sblectric.lightningcraft.gui.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sblectric.lightningcraft.gui.server.ContainerLightningCell;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.tiles.TileEntityLightningCell;

/** The energy cell GUI */
@SideOnly(Side.CLIENT)
public class GuiLightningCell extends GuiContainer {
	
	private static final ResourceLocation lpCellGuiTextures = new ResourceLocation(RefStrings.MODID, "textures/gui/container/lpcell.png");
	private TileEntityLightningCell tileLPCell;

	public GuiLightningCell(InventoryPlayer invPlayer, TileEntityLightningCell tile) {
		super(new ContainerLightningCell(invPlayer, tile));
		this.tileLPCell = tile;		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		
		// bar status
		int width = 125;
		int height = 16;
		int texty = 36;
		
		String string = this.tileLPCell.cellName;
		this.fontRenderer.drawString(string, this.xSize / 2 - this.fontRenderer.getStringWidth(string) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);
		
		Gui.drawRect(25, 17, 25 + (int) (((float)tileLPCell.storedPower/(float)tileLPCell.maxPower) * width), 17 + height, 0xffffffff);
		
		this.fontRenderer.drawString("Stored LE:", 24, texty, 4210752);
		this.fontRenderer.drawString("Capacity:", 24, texty + 10, 4210752);
		this.fontRenderer.drawString("Terminal Efficiency:", 24, texty + 20, 4210752);
		String storedPower = tileLPCell.storedPower + " LE";
		String maxPower = (int)tileLPCell.maxPower + " LE";
		String efficiency = (int)(tileLPCell.efficiency * 100D) + "%";
		this.fontRenderer.drawString(storedPower, 151 - this.fontRenderer.getStringWidth(storedPower), texty, 4210752);
		this.fontRenderer.drawString(maxPower, 151 - this.fontRenderer.getStringWidth(maxPower), texty + 10, 4210752);
		this.fontRenderer.drawString(efficiency, 151 - this.fontRenderer.getStringWidth(efficiency), texty + 20, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(lpCellGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

}