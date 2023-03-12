package src;

public class PlaceholderDistance implements ClusterDistance { // placeholder algorithm to be replaced later
    @Override
    public double findDistance(Cluster c1, Cluster c2) {
        if (c1.dataPoints.size() > 0 && c2.dataPoints.size() > 0){
            return Math.abs(c1.dataPoints.get(0).data.get(0) - c2.dataPoints.get(0).data.get(0));
        }
        return 0;
    }
}
