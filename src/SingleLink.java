package src;

public class SingleLink implements LinkingMethod {
    PointDistance distanceAlg;

    public SingleLink(PointDistance distanceAlg) {
        this.distanceAlg = distanceAlg;
    }

    // Find the Euclidean Distance between two clusters utilizing Single Link Method
    @Override
    public double findDistance(Cluster c1, Cluster c2) {

        if (c1.dataPoints.size() > 0 && c2.dataPoints.size() > 0){
            double bestDistance = this.distanceAlg.findDistance(c1.dataPoints.get(0), c2.dataPoints.get(0));

            for (int i=0; i<c1.dataPoints.size(); i++) {
                DataPoint p1 = c1.dataPoints.get(i);

                for (int j=0; j<c2.dataPoints.size(); j++) {
                    DataPoint p2 = c2.dataPoints.get(j);
                    double newDist = this.distanceAlg.findDistance(p1, p2);

                    if (newDist < bestDistance) {
                        bestDistance = newDist;
                    }
                }
            }

            return bestDistance;
        }

        return 0;
    }
}
