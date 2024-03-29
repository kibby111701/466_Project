package src;

import java.util.ArrayList;

public class Cluster {

    public ArrayList<Cluster> children;
    public ArrayList<DataPoint> dataPoints;
    public double purity;
    public double entropy;

    public Cluster(DataPoint point){ // for creating a cluster from one data point
        this.children = null;
        this.dataPoints = new ArrayList<>();
        dataPoints.add(point);
        this.purity = getPurity();
        this.entropy = getEntropy();
    }

    public Cluster(ArrayList<Cluster> children){ // for merging two existing clusters
        this.children = children;
        this.dataPoints = new ArrayList<>();
        for (Cluster child : children){
            this.dataPoints.addAll(child.dataPoints);
        }
        this.purity = getPurity();
        this.entropy = getEntropy();
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
                total += 1.0;
            }
        }
        return total / (double)this.dataPoints.size();
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
            return dataPoints.size() + " *Depth reached*" + " Purity: " + getPurity() + "\n";
        }
        else if(children == null){
            s = dataPoints.size() + "---(LEAF)";
        }
        else if(children.size() == 2 && maxDepth > 1){
            s = dataPoints.size() + " Purity: " + getPurity() + "\n" +
                    addTabs(curDepth) + "|\n" +
                    addTabs(curDepth) + "|\n" +
                    addTabs(curDepth) + "--- L " + children.get(0).printTree(maxDepth-1, curDepth+1) + "\n" +
                    addTabs(curDepth) + "|\n" +
                    addTabs(curDepth) + "|\n" +
                    addTabs(curDepth) + "--- R " + children.get(1).printTree(maxDepth-1, curDepth+1);
        }
        return s;
    }

    public double getPurity(){
        return Math.max(Math.max(this.getClassProportion(1), this.getClassProportion(2)),
                this.getClassProportion(3));
    }

    public double getEntropy() {
        double total = 0.0;

        for (int i = 1; i<4; i++) {
            double prop = this.getClassProportion(i);

            if (prop > 0) {
                total -= prop * log2(prop);
            }
        }

        return total;
    }

    private double log2(double number) { return Math.log(number) / Math.log(2); }

}
