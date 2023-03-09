package src;

import java.util.ArrayList;

public class DataPoint {
    public int pointID;
    public ArrayList<Double> data;

    public DataPoint (int pointID, ArrayList<Double> data){
        this.pointID = pointID;
        this.data = data;
    }
}
