package org.terasology.VolcanoGiants;

import java.awt.Polygon;
import java.awt.Rectangle;

import org.terasology.math.geom.Rect2i;
import org.terasology.math.geom.Vector3i;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.WhiteNoise;


public class Volcano {

    private Vector3i origin;
    private float maxRadius = 85;
    private float maxInnerRadius = 35;
    private float maxTopRadius = 5;
    private float maxHeight = 250;
    private float maxLength = 3;
    private Polygon VolcanoPoly;
    private Polygon InnerVolcanoPoly;
    private Polygon TopVolcanoPoly;
    private Noise noise;
    private int points;

    public Volcano(Vector3i origin, int pnum){
        long seed = Math.round(origin.length()*217645199);
        noise = new WhiteNoise(seed);
        this.origin=origin;
        createEllipticPolygon(pnum);
        points = pnum;
    }

    //Null Volcano
    public Volcano () {
        points = 0;
    }

    private void createEllipticPolygon(int pnum){


        int[] x = new int[pnum];
        int[] y = new int[pnum];
        int[] xInner = new int[pnum];
        int[] yInner = new int[pnum];
        int[] xTop = new int[pnum];
        int[] yTop = new int[pnum];

        double alpha;
        float yRadius, xRadius,length, xInnerRadius, yInnerRadius, xTopRadius, yTopRadius;

        xRadius =  Math.abs(noise.noise(origin.x(),origin.y())*maxRadius);
        yRadius = Math.abs(noise.noise(origin.z(),origin.y())*maxRadius);
        xInnerRadius = Math.abs(noise.noise(origin.x(),origin.y())*maxInnerRadius);
        yInnerRadius = Math.abs(noise.noise(origin.z(),origin.y())*maxInnerRadius);
        xTopRadius = Math.abs(noise.noise(origin.x(),origin.y())*maxTopRadius);
        yTopRadius = Math.abs(noise.noise(origin.z(),origin.y())*maxTopRadius);

        for(int i=0; i<pnum; i++){
            alpha = i * 2 * Math.PI / pnum;

           length = Math.abs(noise.noise(origin.y()*i,origin.x()*i)*maxLength);

            //Volcano Polygon points:
            x[i] = origin.x()+Math.round(xRadius*(float)Math.cos((double) alpha));
            y[i] = origin.z()+Math.round(yRadius*(float)Math.sin((double) alpha));

            x[i]=Math.round(x[i]+Math.signum((x[i]-origin.x()))*length);
            y[i]=Math.round(y[i]+Math.signum((y[i]-origin.z()))*length);

            //Inner Polygon points:
            xInner[i] = origin.x()+Math.round(xInnerRadius*(float)Math.cos((double) alpha));
            yInner[i] = origin.z()+Math.round(yInnerRadius*(float)Math.sin((double) alpha));

            xInner[i]=Math.round(xInner[i]-Math.signum((xInner[i]-origin.x()))*(2+Math.abs(length)));
            yInner[i]=Math.round(yInner[i]-Math.signum((yInner[i]-origin.z()))*(2+Math.abs(length)));

            //Top Polygon points
            xTop[i] = origin.x()+Math.round(xTopRadius*(float)Math.cos((double) alpha));
            yTop[i] = origin.z()+Math.round(yTopRadius*(float)Math.sin((double) alpha));

            xTop[i]=Math.round(xTop[i]);
            yTop[i]=Math.round(xTop[i]);
        }
        VolcanoPoly = new Polygon(x,y,pnum);
        InnerVolcanoPoly = new Polygon(xInner, yInner, pnum);
        TopVolcanoPoly = new Polygon(xTop, yTop, pnum);
    }

    public boolean TopVolcanoContains(Vector3i pos) {
        return TopVolcanoPoly.contains(pos.getX(), pos.getZ());
    }

    public boolean InnerVolcanoContains(Vector3i pos){
        return !TopVolcanoPoly.contains(pos.getX(), pos.getZ())
                && InnerVolcanoPoly.contains(pos.getX(),pos.getZ());
    }

    public boolean VolcanoContains(Vector3i pos){
        return !TopVolcanoPoly.contains(pos.getX(), pos.getZ())
                && !InnerVolcanoPoly.contains(pos.getX(),pos.getZ())
                && VolcanoPoly.contains(pos.getX(),pos.getZ());
    }

    public boolean BBContains(Vector3i pos){
        return VolcanoPoly.getBounds().contains(pos.getX(),pos.getZ());
    }

    public Rect2i getBB() {
        Rectangle AwtRect = VolcanoPoly.getBounds();
        Rect2i TeraRect = Rect2i.createFromMinAndMax(Math.round((float) AwtRect.getMinX()),
                Math.round((float) AwtRect.getMinY()), Math.round((float) AwtRect.getMaxX()),
                Math.round((float) AwtRect.getMaxY()));
        return TeraRect;
    }

    public Vector3i getOrigin(){
        return origin;
    }

    public boolean isNull() {

        if (points == 0) {
            return true;
        }
        else
            return false;
    }

    public boolean isInRange(Vector3i pos ) {
        if(origin == null){
            return false;
        }
        return Math.abs(pos.x()-origin.x())<=maxRadius && Math.abs(pos.z()-origin.z())<=maxRadius
                && Math.abs(pos.y()-origin.y())<=maxHeight;
    }

}

