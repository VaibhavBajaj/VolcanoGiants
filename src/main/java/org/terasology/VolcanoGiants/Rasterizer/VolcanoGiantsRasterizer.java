package org.terasology.VolcanoGiants.Rasterizer;

import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.facets.SurfaceHeightFacet;
import org.terasology.VolcanoGiants.FacetProvider.VolcanoProvider;

public class VolcanoGiantsRasterizer implements WorldRasterizer {

    private Block air;
    private Block stone;
    private Block lava;
    private Block hardStone;
    private VolcanoProvider volcanoProvider;

    @Override
    public void initialize() {
        air = CoreRegistry.get(BlockManager.class).getBlock(BlockManager.AIR_ID);
        stone = CoreRegistry.get(BlockManager.class).getBlock("Core:Stone");
        lava = CoreRegistry.get(BlockManager.class).getBlock("Core:Lava");
        hardStone = CoreRegistry.get(BlockManager.class).getBlock("Core:HardStone");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {

        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);

        for (Vector3i position : chunkRegion.getRegion()) {

            float surfaceHeight = surfaceHeightFacet.getWorld(position.x, position.z);

            if (position.getY() >= volcanoProvider.getMountainHeight() - 30) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), air);
            }
            if (position.getY() < surfaceHeight - 5) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), lava);
            } else if (position.getY() < surfaceHeight - 1) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), hardStone);
            } else if (position.getY() < surfaceHeight) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), stone);
            }
        }
    }
}