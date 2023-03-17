package src;

public class CompleteLink implements LinkingMethod{

    PointDistance distanceAlg;

    public CompleteLink(PointDistance distanceAlg) {
        this.distanceAlg = distanceAlg;
    }

    @Override
    public double findDistance(Cluster c1, Cluster c2) {
        double bestDistance = this.distanceAlg.findDistance(c1.dataPoints.get(0), c2.dataPoints.get(0));
        for (int i=0; i<c1.dataPoints.size(); i++) {
            DataPoint p1 = c1.dataPoints.get(i);
            for (int j=0; j<c2.dataPoints.size(); j++) {
                DataPoint p2 = c2.dataPoints.get(j);
                double newDist = this.distanceAlg.findDistance(p1, p2);
                if (newDist > bestDistance) {
                    bestDistance = newDist;
                }
            }
        }
        return bestDistance;
    }

    @Override
    public String toString() {
        return "CompleteLink";
    }
}
