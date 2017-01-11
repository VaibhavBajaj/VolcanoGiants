package org.terasology.VolcanoGiants.Rasterizer;

import java.util.Map.Entry;

import org.terasology.VolcanoGiants.Facet.GasCloudFacet;
import org.terasology.VolcanoGiants.PoisonousGasCloud;
import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;

public class GasCloudRasterizer implements WorldRasterizer {
    private Block poisonousSulphurGas;
    private Block air;

    @Override
    public void initialize() {
        poisonousSulphurGas = CoreRegistry.get(BlockManager.class).getBlock("VolcanoGiants:PoisonousSulphurGas");
        air = CoreRegistry.get(BlockManager.class).getBlock(BlockManager.AIR_ID);
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        GasCloudFacet gasCloudFacet = chunkRegion.getFacet(GasCloudFacet.class);

        for (Entry<BaseVector3i, PoisonousGasCloud> entry : gasCloudFacet.getWorldEntries().entrySet()) {
            // there should be a cloud here
            Vector3i gasCloudCentre = new Vector3i(entry.getKey());
            int extent = entry.getValue().getMaxRadius();
            Region3i cloudRegion = Region3i.createFromCenterExtents(gasCloudCentre, extent);

            // loop through each of the positions in the cube, ignoring the is
            for (Vector3i newBlockPosition : cloudRegion) {
                if (chunkRegion.getRegion().encompasses(newBlockPosition) && (chunk.getBlock(ChunkMath.calcBlockPos(newBlockPosition)) == air)) {
                    chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), poisonousSulphurGas);
                }
            }
        }
    }
}
