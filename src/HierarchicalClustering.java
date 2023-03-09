package src;

public class HierarchicalClustering {

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
    }

}
