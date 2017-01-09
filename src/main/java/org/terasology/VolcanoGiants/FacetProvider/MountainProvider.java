package org.terasology.VolcanoGiants.FacetProvider;

import org.terasology.math.geom.Vector2f;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Rect2i;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

@Updates(@Facet(SurfaceHeightFacet.class))
public class MountainProvider implements FacetProvider {

    private Noise mountainNoise;

    @Override
    public void setSeed(long seed) {
        mountainNoise = new SubSampledNoise(new SimplexNoise(seed), new Vector2f(0.0075f, 0.0075f), 1);
    }

    @Override
    public void process(GeneratingRegion region) {
        SurfaceHeightFacet facet = region.getRegionFacet(SurfaceHeightFacet.class);
        float mountainHeight = 250;
        float additiveMountainHeight;
        // loop through every position on our 2d array
        Rect2i processRegion = facet.getWorldRegion();
        for (BaseVector2i position : processRegion.contents()) {
            // scale our max mountain height to noise (between -1 and 1)
            additiveMountainHeight = mountainNoise.noise(position.getX(), position.getY()) * mountainHeight;
            // dont bother subtracting mountain height,  that will allow unaffected regions
            additiveMountainHeight = TeraMath.clamp(additiveMountainHeight, 0, mountainHeight);

            facet.setWorld(position, facet.getWorld(position) + additiveMountainHeight);
        }
    }
}