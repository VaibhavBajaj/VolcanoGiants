package org.terasology.VolcanoGiants.Rasterizer;

import org.terasology.VolcanoGiants.Facet.VolcanoFacet;
import org.terasology.VolcanoGiants.Facet.VolcanoHeightFacet;
import org.terasology.VolcanoGiants.Volcano;
import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.facets.SurfaceHeightFacet;


public class VolcanoGiantsRasterizer implements WorldRasterizer {

    private Block air;
    private Block volcanoAsh;
    private Block stone;
    private Block lava;
    private Block hardStone;

    @Override
    public void initialize() {
        air = CoreRegistry.get(BlockManager.class).getBlock(BlockManager.AIR_ID);
        volcanoAsh = CoreRegistry.get(BlockManager.class).getBlock("Core:Soil");
        stone = CoreRegistry.get(BlockManager.class).getBlock("Core:Stone");
        lava = CoreRegistry.get(BlockManager.class).getBlock("Core:Lava");
        hardStone = CoreRegistry.get(BlockManager.class).getBlock("Core:HardStone");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {

        VolcanoHeightFacet volcanoHeightFacet = chunkRegion.getFacet(VolcanoHeightFacet.class);
        VolcanoFacet volcanoFacet = chunkRegion.getFacet(VolcanoFacet.class);

        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);

        for (Vector3i position : chunkRegion.getRegion()) {

            float surfaceHeight = surfaceHeightFacet.getWorld(position.x, position.z);
            float volcanoHeight = volcanoHeightFacet.getWorld(position.x, position.z);

            if(position.y() < surfaceHeight - 5) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), lava);
            }
            else if (position.y() < surfaceHeight - 1) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), hardStone);
            }
            else if (position.y() < surfaceHeight) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), stone);
            }

            Volcano volcano = volcanoFacet.getNearestVolcano(position);

            if(!volcano.isNull() && volcano.isInRange(position)) {

                if(volcano.TopVolcanoContains(position) && position.y() > volcanoHeight) {
                    chunk.setBlock(ChunkMath.calcBlockPos(position), air);
                }
                else if(volcano.InnerVolcanoContains(position) && position.y() == volcanoHeight) {
                    chunk.setBlock(ChunkMath.calcBlockPos(position), volcanoAsh);
                }
            }
        }
    }

}