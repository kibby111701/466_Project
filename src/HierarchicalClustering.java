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

//        makeClustering();
//
//        points = new ArrayList<>();
//        clusters = new ArrayList<>();
//        process();
//
//        pairsQueue = new PriorityQueue<>(1000, Comparator.comparingDouble(c -> c.distance));
//        LinkingMethod linkingMethod = new SingleLink(distanceAlg);
//        LinkingMethod linkingMethod = new CentroidLink(distanceAlg);
//        LinkingMethod linkingMethod = new CompleteLink(distanceAlg);
//        LinkingMethod linkingMethod = new AverageLink(distanceAlg);
//        initializeQueue(linkingMethod);
//        buildHierarchy(linkingMethod);
//        System.out.println("hi");
//        System.out.println(clusters.get(0).printTree(3, 0));

        PointDistance distanceAlg = new EuclideanDistance();
        ArrayList<LinkingMethod> allLinks = new ArrayList<>();
        allLinks.add(new AverageLink(distanceAlg));
        allLinks.add(new CentroidLink(distanceAlg));
        allLinks.add(new SingleLink(distanceAlg));
        allLinks.add(new CompleteLink(distanceAlg));

        for(LinkingMethod link : allLinks){
            runLinking(link, 3);
        }

//        System.out.println(clusters.get(0));
    }

    // runs the entire program with one linking method
    // NOTE: reprocesses data
    public static void runLinking(LinkingMethod linkMethod, int printDepth){
        points = new ArrayList<>();
        clusters = new ArrayList<>();
        process();
        pairsQueue = new PriorityQueue<>(1000, Comparator.comparingDouble(c -> c.distance));
        initializeQueue(linkMethod);
        buildHierarchy(linkMethod);
        System.out.println(linkMethod);
        System.out.println(clusters.get(0).printTree(printDepth, 0));
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

            int lineNum = 1;
            int classification;
            DataPoint curPoint;

            while (read.hasNextLine()){
                line = read.nextLine();
                vals = line.split(",");

                data = new ArrayList<>();
                for (String val : vals){
                    data.add(Double.parseDouble(val));
                }

                if (lineNum < 60){
                    classification = 1;
                } else if (lineNum >= 60 && lineNum < 131){
                    classification = 2;
                } else {
                    classification = 3;
                }

                curPoint = new DataPoint(lineNum, classification, data);
                points.add(curPoint);
                clusters.add(new Cluster(curPoint));

                lineNum += 1;
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
