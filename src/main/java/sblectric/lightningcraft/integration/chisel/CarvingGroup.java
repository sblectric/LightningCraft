package sblectric.lightningcraft.integration.chisel;

import java.util.List;

import net.minecraftforge.fml.common.Optional;
import sblectric.lightningcraft.ref.RefStrings;
import team.chisel.api.carving.ICarvingGroup;
import team.chisel.api.carving.ICarvingVariation;

@Optional.Interface(iface = "team.chisel.api.carving.ICarvingGroup", modid = "chisel")
public class CarvingGroup implements ICarvingGroup {
	
	private CarvingVariationList variations;
	private String uName;
	
	public CarvingGroup(String uName, CarvingVariationList variations) {
		this.variations = variations;		
		this.uName = uName;
	}
	
	@Override
	public String getName() {
		return RefStrings.MODID + ".chisel." + uName;
	}

	@Override
	public String getSound() {
		return null;
	}

	@Override
	public void setSound(String sound) {}

	@Override
	public String getOreName() {
		return null;
	}

	@Override
	public void setOreName(String oreName) {}

	@Override
	public List<ICarvingVariation> getVariations() {
		return variations.getGeneralizedList();
	}

	@Override
	public void addVariation(ICarvingVariation variation) {
		variations.add((CarvingVariation)variation);
	}

	@Override
	public boolean removeVariation(ICarvingVariation variation) {
		return variations.remove(variation);
	}

}
