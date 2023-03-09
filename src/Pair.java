package src;

public class Pair {
    public Cluster c1;
    public Cluster c2;
    public double distance;

    public Pair(Cluster c1, Cluster c2, ClusterDistance distanceAlg){
        this.c1 = c1;
        this.c2 = c2;
        this.distance = distanceAlg.findDistance(c1, c2);

    }

}