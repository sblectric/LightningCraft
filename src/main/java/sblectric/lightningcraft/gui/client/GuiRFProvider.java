package sblectric.lightningcraft.gui.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.gui.server.ContainerRFProvider;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.tiles.TileEntityRFProvider;

/** The RF provider GUI */
@SideOnly(Side.CLIENT)
public class GuiRFProvider extends GuiContainer {
	
	private static final ResourceLocation lpCellGuiTextures = new ResourceLocation(RefStrings.MODID, "textures/gui/container/lpcell.png");
	private TileEntityRFProvider tileRF;

	public GuiRFProvider(InventoryPlayer invPlayer, TileEntityRFProvider tile) {
		super(new ContainerRFProvider(invPlayer, tile));
		this.tileRF = tile;		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		
		// bar status
		int width = 125;
		int height = 16;
		int texty = 36;
		
		String string = LCBlocks.rfProvider.getLocalizedName();
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);
		
		Gui.drawRect(25, 17, 25 + (int) (((float)tileRF.getEnergyStored(null)/(float)TileEntityRFProvider.maxStorage) * width), 17 + height, 0xffffffff);
		
		this.fontRendererObj.drawString("Stored RF:", 24, texty, 4210752);
		this.fontRendererObj.drawString("Capacity:", 24, texty + 10, 4210752);
		this.fontRendererObj.drawString("Cell Energy:", 24, texty + 20, 4210752);
		String storedPower = tileRF.getEnergyStored(null) + " RF";
		String maxPower = TileEntityRFProvider.maxStorage + " RF";
		String storedLE = (int)tileRF.cellPower + " LE";
		this.fontRendererObj.drawString(storedPower, 151 - this.fontRendererObj.getStringWidth(storedPower), texty, 4210752);
		this.fontRendererObj.drawString(maxPower, 151 - this.fontRendererObj.getStringWidth(maxPower), texty + 10, 4210752);
		this.fontRendererObj.drawString(storedLE, 151 - this.fontRendererObj.getStringWidth(storedLE), texty + 20, 4210752);
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