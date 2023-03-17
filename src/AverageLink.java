package src;

public class AverageLink implements LinkingMethod{

    PointDistance distanceAlg;

    public AverageLink(PointDistance distanceAlg) {
        this.distanceAlg = distanceAlg;
    }

    // gets avg of all pairwise distances
    @Override
    public double findDistance(Cluster c1, Cluster c2) {
        double totalDistance = 0;
        int numPointPars = 0;
        for (int i = 0; i < c1.dataPoints.size(); i++){
            DataPoint p1 = c1.dataPoints.get(i);
            for (int j = 0; j < c2.dataPoints.size(); j++){
                DataPoint p2 = c2.dataPoints.get(j);
                double thisDist = this.distanceAlg.findDistance(p1, p2);
                totalDistance += thisDist;
                numPointPars++;
            }
        }
        return totalDistance / numPointPars;
    }

    @Override
    public String toString() {
        return "AverageLink";
    }
}
