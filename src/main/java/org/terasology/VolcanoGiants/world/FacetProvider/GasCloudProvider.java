package org.terasology.VolcanoGiants.world.FacetProvider;

import org.terasology.VolcanoGiants.world.Facet.GasCloudFacet;
import org.terasology.VolcanoGiants.world.PoisonousGasCloud;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Rect2i;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.WhiteNoise;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetBorder;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

@Produces(GasCloudFacet.class)
@Requires(@Facet(value = SurfaceHeightFacet.class, border = @FacetBorder(sides = 2)))
public class GasCloudProvider implements FacetProvider {

    private Noise noise;
    private float maxMountainHeight = VolcanoProvider.maxMountainHeight;

    @Override
    public void setSeed(long seed) {
        noise = new WhiteNoise(seed);
    }

    @Override
    public void process(GeneratingRegion region) {

        Border3D border = region.getBorderForFacet(GasCloudFacet.class).extendBy(0, 4, 2);
        GasCloudFacet facet = new GasCloudFacet(region.getRegion(), border);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        Rect2i worldRegion = surfaceHeightFacet.getWorldRegion();

        for (int wz = worldRegion.minY(); wz <= worldRegion.maxY(); wz++) {
            for (int wx = worldRegion.minX(); wx <= worldRegion.maxX(); wx++) {
                int surfaceHeight = TeraMath.floorToInt(surfaceHeightFacet.getWorld(wx, wz));

                // check if height is within this region
                if (surfaceHeight >= facet.getWorldRegion().minY()
                        && surfaceHeight > 0.65 * maxMountainHeight &&
                        surfaceHeight <= 0.77 * maxMountainHeight &&
                        surfaceHeight <= facet.getWorldRegion().maxY()) {
                    facet.setWorld(wx, surfaceHeight, wz, new PoisonousGasCloud());
                }
            }
        }

        region.setRegionFacet(GasCloudFacet.class, facet);
    }
}
