package src;

import java.util.ArrayList;

public class Cluster {

    public ArrayList<Cluster> children;
    public ArrayList<DataPoint> dataPoints;

    public Cluster(DataPoint point){ // for creating a cluster from one data point
        this.children = null;
        this.dataPoints = new ArrayList<>();
        dataPoints.add(point);
    }

    public Cluster(ArrayList<Cluster> children){ // for merging two existing clusters
        this.children = children;
        this.dataPoints = new ArrayList<>();
        for (Cluster child : children){
            this.dataPoints.addAll(child.dataPoints);
        }
    }
}
