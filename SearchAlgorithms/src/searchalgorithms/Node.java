package searchalgorithms;

import java.util.LinkedList;

/**
 *
 * @author Faraaz Malak
 * @purpose Represents a node in the graph
 */
public class Node implements Cloneable {

    private String id = null;
    private int cost = 0;

    /*
     * For cloning node
     */
    public Object clone() {
        Node clonedNode = new Node(this.getNodeID(), this.getCost());
        return clonedNode;


    }

    /*
     * For getting node cost/heuristic
     */
    public int getCost() {
        return cost;
    }

    /*
     * For setting node cost/heuristic
     */
    public void setCost(int newCost) {
        cost = newCost;

    }

    /*
     * Returns nodeID
     */
    public String getNodeID() {
        return id;

    }

    /*
     * Constructor to initialize node
     */
    public Node(String newNodeID, int newCost) {


        id = newNodeID;
        cost = newCost;
    }
}
