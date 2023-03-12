package src;

import java.util.Objects;

public class Pair {
    public Cluster c1;
    public Cluster c2;
    public double distance;

    public Pair(Cluster c1, Cluster c2, LinkingMethod distanceAlg){
        this.c1 = c1;
        this.c2 = c2;
        this.distance = distanceAlg.findDistance(c1, c2);

    }

    @Override
    public int hashCode() {
        return Objects.hash(c1, c2, distance);
    }

    public boolean contains(Cluster c){
        return c.equals(c1) || c.equals(c2);
    }

}
