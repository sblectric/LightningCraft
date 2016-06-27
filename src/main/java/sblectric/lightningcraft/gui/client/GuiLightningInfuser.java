package sblectric.lightningcraft.gui.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sblectric.lightningcraft.gui.server.ContainerLightningInfuser;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.tiles.TileEntityLightningInfuser;

/** The lightning infusion table GUI */
@SideOnly(Side.CLIENT)
public class GuiLightningInfuser extends GuiContainer {
	
	public static final ResourceLocation infuserGuiTextures = new ResourceLocation(RefStrings.MODID, "textures/gui/container/lpinfuser.png");
	private TileEntityLightningInfuser tileInfuser;

	public GuiLightningInfuser(InventoryPlayer invPlayer, TileEntityLightningInfuser tile) {
		super(new ContainerLightningInfuser(invPlayer, tile));
		this.tileInfuser = tile;
		this.ySize = 178;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		
		String string = this.tileInfuser.getName();
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);

		// LP status
		int color = 4210752;
		String need;
		double power = tileInfuser.getActualNeededPower(tileInfuser.infusionCost);
		if(power > 0) {
			need = LCText.df.format(power) + " LE needed";
		} else {
			need = "No Infusion Selected";
		}
		String have = tileInfuser.cellPower + " LE available";
		if(tileInfuser.cellPower < power || tileInfuser.cellPower == 0) color = 0xC00000;
		this.fontRendererObj.drawString(need, 119 - this.fontRendererObj.getStringWidth(need) / 2, 60, color);
		this.fontRendererObj.drawString(have, 119 - this.fontRendererObj.getStringWidth(have) / 2, 70, color);
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(infuserGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	        int i1;

	        i1 = this.tileInfuser.getInfusionProgressScaled(24);
	        this.drawTexturedModalRect(k + 75, l + 40, 176, 14, i1 + 1, 16);
	}

}