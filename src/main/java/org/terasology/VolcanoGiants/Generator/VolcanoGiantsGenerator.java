package org.terasology.VolcanoGiants.Generator;

import org.terasology.VolcanoGiants.FacetProvider.GasCloudProvider;
import org.terasology.VolcanoGiants.FacetProvider.SurfaceProvider;
import org.terasology.VolcanoGiants.FacetProvider.VolcanoProvider;
import org.terasology.VolcanoGiants.Rasterizer.GasCloudRasterizer;
import org.terasology.VolcanoGiants.Rasterizer.VolcanoGiantsRasterizer;
import org.terasology.engine.SimpleUri;
import org.terasology.registry.In;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;

@RegisterWorldGenerator(id = "volcanoGiants", displayName = "Volcano Giants")
public class VolcanoGiantsGenerator extends BaseFacetedWorldGenerator{

    @In
    private WorldGeneratorPluginLibrary worldGeneratorPluginLibrary;

    public VolcanoGiantsGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld() {
        return new WorldBuilder(worldGeneratorPluginLibrary)
                .addProvider(new GasCloudProvider())
                .addProvider(new SurfaceProvider())
                .addProvider(new SeaLevelProvider(0))
                .addProvider(new VolcanoProvider())
                .addRasterizer(new VolcanoGiantsRasterizer())
                .addRasterizer(new GasCloudRasterizer());
    }
}
