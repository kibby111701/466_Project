package src;

public class EuclideanDistance implements PointDistance {

    public double findDistance(DataPoint p1, DataPoint p2) {
        double total = 0.0;

        for (int i=0; i<p1.data.size(); i++) {
            total += Math.pow(p1.data.get(i) - p2.data.get(i), 2);
        }

        return Math.sqrt(total);
    }
}
