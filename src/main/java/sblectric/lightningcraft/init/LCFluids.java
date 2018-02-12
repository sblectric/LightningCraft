package sblectric.lightningcraft.init;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.fluids.BlockFluidLC;
import sblectric.lightningcraft.fluids.ModFluid;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.registry.RegistryHelper;

/** Mod liquids and such */
public class LCFluids {
	
	private static final List<Block> FLUIDS = RegistryHelper.FLUIDS_TO_REGISTER;
	private static Map<Fluid, BlockFluidBase> fluidBlocks;
	private static Map<BlockFluidBase, String> fluidBlockNames;
	
	/** The main fluid registry */
	public static void mainRegistry() {
		fluidBlocks = new HashMap();
		fluidBlockNames = new HashMap();
		registerFluids();
	}
	
	public static Fluid moltenElectricium;
	public static BlockFluidBase moltenBlockElectricium;
	
	public static Fluid moltenSkyfather;
	public static BlockFluidBase moltenBlockSkyfather;
	
	public static Fluid moltenMystic;
	public static BlockFluidBase moltenBlockMystic;
	
	/** Register the fluids and fluid blocks */
	private static void registerFluids() {
		moltenElectricium = createFluid("electricium", 2000, 10000, 800, 12, new Color(32, 250, 250).getRGB());
		moltenSkyfather = createFluid("skyfather", 2000, 10000, 900, 12, new Color(52, 52, 52).getRGB());
		moltenMystic = createFluid("mystic", 2000, 10000, 1000, 12, new Color(180, 0, 0).getRGB());
		
		moltenBlockElectricium = registerFluidBlock(moltenElectricium, "molten_electricium");
		moltenBlockSkyfather = registerFluidBlock(moltenSkyfather, "molten_skyfather");
		moltenBlockMystic = registerFluidBlock(moltenMystic, "molten_mystic");
	}
	
	/** Create a fluid with the given properties */
	private static Fluid createFluid(String name, int density, int viscosity, int temperature, int luminosity, int tintColor) {
		Fluid fluid = new ModFluid(name, new ResourceLocation(RefStrings.MODID, "blocks/molten_metal_still"), 
				new ResourceLocation(RefStrings.MODID, "blocks/molten_metal_flow"), tintColor);
		fluid.setDensity(density);
		fluid.setViscosity(viscosity);
		fluid.setTemperature(temperature);
		fluid.setLuminosity(luminosity);
		fluid.setUnlocalizedName(RefStrings.MODID + ":" + name);
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		return fluid;
	}
	
	/** Register a fluid block with the specified fluid */
	private static BlockFluidClassic registerFluidBlock(Fluid fluid, String blockName) {
		BlockFluidClassic block = new BlockFluidLC(fluid);
		
		ResourceLocation location = new ResourceLocation(RefStrings.MODID, blockName);
		block.setRegistryName(location);
		block.setUnlocalizedName(location.toString());
		FLUIDS.add(block);

		fluidBlocks.put(fluid, block);
		fluidBlockNames.put(block, blockName);
		return block;
	}
	
	/** Make sure to set up the rendering info */
	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		for(Fluid fluid : fluidBlocks.keySet()) {
			BlockFluidBase block = fluidBlocks.get(fluid);
			final ModelResourceLocation fluidModelLocation = new ModelResourceLocation(
					RefStrings.MODID + ":" + fluidBlockNames.get(block), "fluid");
			ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
				@Override
				protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
					return fluidModelLocation;
				}
			});
		}
	}

}
