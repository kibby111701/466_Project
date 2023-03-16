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

    public DataPoint computeCentroid(){
        ArrayList<Double> data = new ArrayList<>();

        int pointSize = this.dataPoints.get(0).data.size();

        for (int i = 0; i < pointSize; i++){
            double amount = 0;

            for (DataPoint point : this.dataPoints){
                amount += point.data.get(i);
            }

            data.add(amount / this.dataPoints.size());
        }

        return new DataPoint(-1, 0, data);
    }

    public double getClassProportion(int classification){ // returns proportion of datapoints in cluster that have the specified classification
        double total = 0.0;
        for (DataPoint point : this.dataPoints){
            if (point.classification == classification){
                total += 1;
            }
        }
        return total/this.dataPoints.size();
    }
}
