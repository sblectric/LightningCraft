package sblectric.lightningcraft.gui.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sblectric.lightningcraft.gui.server.ContainerStaticGenerator;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.tiles.TileEntityStaticGenerator;

/** The static generator GUI */
@SideOnly(Side.CLIENT)
public class GuiStaticGenerator extends GuiContainerLC {
	
	private static final ResourceLocation generatorGuiTextures = new ResourceLocation(RefStrings.MODID, "textures/gui/container/lpstaticgenerator.png");
	private TileEntityStaticGenerator tileGenerator;

	public GuiStaticGenerator(InventoryPlayer invPlayer, TileEntityStaticGenerator tile) {
		super(new ContainerStaticGenerator(invPlayer, tile));
		this.tileGenerator = tile;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		
		// bar status
		int width = 34;
		int height = 6;
		int texty = 61;
		int texty2 = (61 - 53) + 40;
		
		String string = this.tileGenerator.getName();
		this.fontRenderer.drawString(string, this.xSize / 2 - this.fontRenderer.getStringWidth(string) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);

		// draw the LE bar (avoid null pointer exceptions too!)
		if(tileGenerator.cellPower == 0) {
			width = 0;
		} else {
			width = (int)((tileGenerator.cellPower/tileGenerator.maxPower) * width);
		}
		Gui.drawRect(47, 53, 47 + width, 53 + height, 0xffffffff);
		this.fontRenderer.drawString(tileGenerator.cellPower + " LE", 46, texty, 4210752);
		
		// draw the stored charge bar
		width = 34;
		if(tileGenerator.storedCharge == 0) {
			width = 0;
		} else {
			width = (int)((tileGenerator.storedCharge/100D) * width);
		}
		Gui.drawRect(112, 40, 112 + width, 40 + height, 0xffffffff);
		this.fontRenderer.drawString(tileGenerator.storedCharge + " C", 111, texty2, 4210752);
		
	}

	@Override
	protected void drawBackground() {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(generatorGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	        int i1;

	        if (this.tileGenerator.isBurning())
	        {
	            i1 = this.tileGenerator.getBurnTimeRemainingScaled(12);
	            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
	        }

	        i1 = this.tileGenerator.getCookProgressScaled(24);
	        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}

}