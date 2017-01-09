package org.terasology.VolcanoGiants.Facet;

import org.terasology.VolcanoGiants.Volcano;
import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseFacet3D;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;


public class VolcanoFacet extends BaseFacet3D {

    protected Set<Volcano> volcanoes = new LinkedHashSet<>();
    protected boolean enabled = true;
    protected Volcano NullVolcano = new Volcano();

    public VolcanoFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }

    /**
     * @param volcano
     */
    public void add(Volcano volcano) {
        volcanoes.add(volcano);
    }

    /**
     * @return volcanoes
     */
    public Set<Volcano> getVolcanoes() {
        return Collections.unmodifiableSet(volcanoes);
    }

    public Volcano getNearestVolcano(Vector3i pos){

        if(volcanoes.isEmpty()){
            return NullVolcano;
        }

        Volcano nearest = (Volcano) volcanoes.toArray()[0];

        double dis=9999;

        for(Volcano volcano : volcanoes){
            double newDis =  pos.distance(volcano.getOrigin());
            if(dis > newDis){
                nearest = volcano;
                dis=newDis;
            }
        }

        return nearest;
    }

    public int getWorldIndex(BaseVector3i pos) {
        return getWorldIndex(pos.x(), pos.y(),pos.z());
    }

    public boolean isEnabled() { return enabled; }

    public void setEnabled( boolean enabled ){ this.enabled = enabled; }
}