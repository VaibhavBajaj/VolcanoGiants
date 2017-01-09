package org.terasology.VolcanoGiants.Facet;

import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

public class VolcanoHeightFacet extends SurfaceHeightFacet {

    public VolcanoHeightFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }

    public int getWorldIndex(BaseVector2i pos) {
        return getWorldIndex(pos.x(),pos.y());
    }
}
