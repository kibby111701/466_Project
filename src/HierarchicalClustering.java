package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HierarchicalClustering {

    public static final String FILENAME = "wine-clustering.csv";

    public static ArrayList<DataPoint> points;
    public static ArrayList<Cluster> clusters;
    public static PriorityQueue<Pair> pairsQueue;


    public static void main(String[] args){
    /*
    * steps:
    * 1. process file into DataPoint objects
    * 2. for each DataPoint, make a Cluster object containing that DataPoint (can be done in step 1)
    *   - store Clusters in a list
    * 3. compute all distances between cluster pairs
    *   - Need some sort of Pair object that holds two clusters and their distance
    *   - store Pairs in a list (maybe even a priority queue if we're feeling fancy)
    * 4. repeat until list of Clusters only has one Cluster:
    *   a. find Pair with smallest distance
    *   b. remove Clusters in Pair from the list of Clusters
    *   d. create a new Cluster that contains the clusters in the Pair
    *   e. create new Pairs by computing distances from the new Cluster to other Clusters
    * */

        points = new ArrayList<>();
        clusters = new ArrayList<>();
        process();

        pairsQueue = new PriorityQueue<>(1000, Comparator.comparingDouble(c -> c.distance));
        PointDistance distanceAlg = new EuclideanDistance();
        LinkingMethod linkingMethod = new SingleLink(distanceAlg);
        initializeQueue(linkingMethod);
        buildHierarchy(linkingMethod);

        System.out.println("hi");
        System.out.println(clusters.get(0));
    }

    // reads the file and creates datapoint and cluster objects
    public static void process(){
        Scanner read;

        try {
            read = new Scanner(new File(FILENAME));

            read.nextLine();

            String line;
            String[] vals;
            ArrayList<Double> data;

            int curID = 0;
            DataPoint curPoint;

            while (read.hasNextLine()){
                line = read.nextLine();
                vals = line.split(",");

                data = new ArrayList<>();
                for (String val : vals){
                    data.add(Double.parseDouble(val));
                }

                curPoint = new DataPoint(curID, data);
                points.add(curPoint);
                clusters.add(new Cluster(curPoint));

                curID++;
            }

            read.close();

        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    // fills the pair priority queue with all possible pairs of clusters
    public static void initializeQueue(LinkingMethod linkingMethod){
        Cluster c1;
        Cluster c2;

        for (int i = 0; i < clusters.size(); i++){
            for (int j = i+1; j < clusters.size(); j++){
                c1 = clusters.get(i);
                c2 = clusters.get(j);
                pairsQueue.add(new Pair(c1, c2, linkingMethod));
            }
        }
    }

    // merges nearby clusters until only one is left
    public static void buildHierarchy(LinkingMethod linkingMethod){
        Pair curPair;
        ArrayList<Cluster> newChildren;
        Cluster newC;
        while (clusters.size() > 1){
            curPair = pairsQueue.poll();
            if (curPair != null && clusters.contains(curPair.c1) && clusters.contains(curPair.c2)){
                newChildren = new ArrayList<>();
                newChildren.add(curPair.c1);
                newChildren.add(curPair.c2);
                newC = new Cluster(newChildren);

                clusters.remove(curPair.c1);
                clusters.remove(curPair.c2);

                addNewPairs(newC, linkingMethod);
                clusters.add(newC);
            }
        }
    }

    // given a new cluster, create pairs of that cluster with every other current cluster
    public static void addNewPairs(Cluster newC, LinkingMethod linkingMethod){
        for (Cluster cluster : clusters) {
            pairsQueue.add(new Pair(newC, cluster, linkingMethod));
        }
    }

}
