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

        return new DataPoint(-1, data);
    }

    public int getSize(){
        return dataPoints.size();
    }

    //adds tabs to tree output vir easy visuals
    private String addTabs(int num){
        String tabs = "";
        for(int i = num; i > 0; i--){
            tabs = tabs + "\t";
        }
        return tabs;
    }

    // prints tree to depth maxDepth
    public String printTree(int maxDepth, int curDepth){
        String s = "";
        if(maxDepth == 1){
            return dataPoints.size() + " *Depth reached*";
        }
        else if(children == null){
            s = dataPoints.size() + "---(LEAF)";
        }
        else if(children.size() == 2 && maxDepth > 1){
            s = dataPoints.size() + "\n" +
                    addTabs(curDepth) + "|\n" +
                    addTabs(curDepth) + "|\n" +
                    addTabs(curDepth) + "--- L " + children.get(0).printTree(maxDepth-1, curDepth+1) + "\n" +
                    addTabs(curDepth) + "|\n" +
                    addTabs(curDepth) + "|\n" +
                    addTabs(curDepth) + "--- R " + children.get(1).printTree(maxDepth-1, curDepth+1);
        }
        return s;
    }

}
