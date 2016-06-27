package sblectric.lightningcraft.registry;

import java.util.Random;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Interface to help with object registration */
public interface IRegistryBase {
	
	public static final Random random = new Random();
	public static final EnumRarity DYNAMIC = null;
	
	/** register this item with the renderer */
	@SideOnly(Side.CLIENT)
	public void registerRender();

}
