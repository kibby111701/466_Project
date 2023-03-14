package src;

public class CentroidLink implements LinkingMethod{
    PointDistance distanceAlg;

    public CentroidLink(PointDistance distanceAlg){
        this.distanceAlg = distanceAlg;
    }


    @Override
    public double findDistance(Cluster c1, Cluster c2) {
        DataPoint centroid1 = c1.computeCentroid();
        DataPoint centroid2 = c2.computeCentroid();
        return this.distanceAlg.findDistance(centroid1, centroid2);

    }
}
