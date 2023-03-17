package src;

import java.util.ArrayList;

public class DataPoint {
    public int pointID;
    public int classification;
    public ArrayList<Double> data;

    public DataPoint (int pointID, int classification, ArrayList<Double> data){
        this.pointID = pointID;
        this.classification = classification;
        this.data = data;
    }
}
