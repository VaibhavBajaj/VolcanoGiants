package org.terasology.VolcanoGiants.Facet;

import org.terasology.VolcanoGiants.PoisonousGasCloud;
import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.SparseObjectFacet3D;

public class GasCloudFacet extends SparseObjectFacet3D<PoisonousGasCloud> {

    public GasCloudFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }
}
