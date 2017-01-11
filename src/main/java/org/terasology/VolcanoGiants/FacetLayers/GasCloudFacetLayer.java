package org.terasology.VolcanoGiants.FacetLayers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;

import org.terasology.VolcanoGiants.Facet.GasCloudFacet;
import org.terasology.VolcanoGiants.PoisonousGasCloud;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Rect2i;
import org.terasology.math.geom.Vector2i;
import org.terasology.world.generation.Region;
import org.terasology.world.viewer.layers.AbstractFacetLayer;
import org.terasology.world.viewer.layers.Renders;

@Renders(value = GasCloudFacet.class, order = 200)
public class GasCloudFacetLayer extends AbstractFacetLayer {

    private Color fillColor = new Color(70, 71, 73, 128);
    private Color frameColor = new Color(70, 71, 73, 128);

    @Override
    public void render(BufferedImage img, Region region) {
        GasCloudFacet gasCloudFacet = region.getFacet(GasCloudFacet.class);

        Graphics2D g = img.createGraphics();

        int dx = region.getRegion().minX();
        int dy = region.getRegion().minZ();
        g.translate(-dx, -dy);

        for (Entry<BaseVector3i, PoisonousGasCloud> entry : gasCloudFacet.getWorldEntries().entrySet()) {
            int extent = entry.getValue().getMaxRadius();

            BaseVector3i center = entry.getKey();
            g.setColor(fillColor);
            g.fillRect(center.x() - extent, center.z() - extent, 2 * extent, 2 * extent);
            g.setColor(frameColor);
            g.drawRect(center.x() - extent, center.z() - extent, 2 * extent, 2 * extent);
        }

        g.dispose();
    }

    @Override
    public String getWorldText(Region region, int wx, int wy) {
        GasCloudFacet gasCloudFacet = region.getFacet(GasCloudFacet.class);

        for (Entry<BaseVector3i, PoisonousGasCloud> entry : gasCloudFacet.getWorldEntries().entrySet()) {
            int extent = entry.getValue().getMaxRadius();

            BaseVector3i center = entry.getKey();
            Vector2i min = new Vector2i(center.x() - extent, center.z() - extent);
            Vector2i max = new Vector2i(center.x() + extent, center.z() + extent);
            if (Rect2i.createFromMinAndMax(min, max).contains(wx, wy)) {
                return "Poisonous Sulphur Gas Cloud";
            }
        }

        return null;
    }

}
