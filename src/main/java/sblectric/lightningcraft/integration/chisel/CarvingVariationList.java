package sblectric.lightningcraft.integration.chisel;

import java.util.List;

import sblectric.lightningcraft.util.JointList;
import team.chisel.api.carving.ICarvingVariation;

/** A carving variation list */
public class CarvingVariationList extends JointList<CarvingVariation> {
	
	public CarvingVariationList(CarvingVariation... vars) {
		for(CarvingVariation v : vars) {
			this.join(v);
		}
	}
	
	/** Returns the list as a list of {@link ICarvingVariation}s */
	public List<ICarvingVariation> getGeneralizedList() {
		List<ICarvingVariation> list = new JointList();
			for(CarvingVariation v : this) {
				list.add((ICarvingVariation)v);
			}
		return list;
	}

}
