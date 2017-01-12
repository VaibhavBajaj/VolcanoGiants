package org.terasology.VolcanoGiants.world.GasCloud;

import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.SparseObjectFacet3D;

public class GasCloudFacet extends SparseObjectFacet3D<GasCloud> {

    public GasCloudFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }
}
