package src;

public class ManhattanDistance implements PointDistance {

    @Override
    public double findDistance(DataPoint p1, DataPoint p2) {
        double total = 0.0;

        for (int i=0; i<p1.data.size(); i++) {
            total += Math.abs(p1.data.get(i) - p2.data.get(i));
        }

        return total;
    }
}
