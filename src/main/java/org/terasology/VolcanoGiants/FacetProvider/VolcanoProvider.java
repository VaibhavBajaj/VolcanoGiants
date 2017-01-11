package org.terasology.VolcanoGiants.FacetProvider;

import org.slf4j.LoggerFactory;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Vector2f;
import org.terasology.math.geom.Rect2i;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.Updates;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

import org.slf4j.Logger;

@Updates(@Facet(SurfaceHeightFacet.class))
public class VolcanoProvider implements FacetProvider {

    private Noise mountainNoise;
    private float mountainHeight;
    public static int maxMountainHeight = 200;
    Logger logger = LoggerFactory.getLogger(VolcanoProvider.class);

    @Override
    public void setSeed(long seed) {
        mountainNoise = new SubSampledNoise(new SimplexNoise(seed), new Vector2f(0.0075f, 0.0075f), 1);
    }

    @Override
    public void process(GeneratingRegion region) {
        SurfaceHeightFacet facet = region.getRegionFacet(SurfaceHeightFacet.class);
        float additiveMountainHeight;
        Rect2i processRegion = facet.getWorldRegion();
        for (BaseVector2i position : processRegion.contents()) {
            additiveMountainHeight = mountainNoise.noise(position.getX(), position.getY()) * maxMountainHeight;
            additiveMountainHeight = TeraMath.clamp(additiveMountainHeight, 0, maxMountainHeight);
            mountainHeight = facet.getWorld(position) + additiveMountainHeight;
            facet.setWorld(position, mountainHeight);
        }
    }
}
