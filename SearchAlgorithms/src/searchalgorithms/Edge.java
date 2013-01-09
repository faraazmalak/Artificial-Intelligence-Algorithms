package searchalgorithms;

/**
 *
 * @author Faraaz Malak
 * @PURPOSE rEPRESENTS AN EDGE OF THE GRAPH
 */
public class Edge {

    private Node parent;
    private Node child;
    private int pathCost;

    /*
     * Default Constructor for Edge
     */
    public Edge(Node newParent, Node newChild) {
        parent = newParent;
        child = newChild;



    }

    /*
     * Constructor for Edge
     */
    public Edge(Node newParent, Node newChild, int newPathCost) {
        parent = newParent;
        child = newChild;
        pathCost = newPathCost;


    }

    /*
     * Returns parent node in edge
     */
    public Node getParent() {
        return parent;

    }

    /*
     * Returns child node in edge
     */
    public Node getChild() {
        return child;
    }

    /*
     * Returns path cost
     */
    public int getPathCost() {


        return pathCost;

    }
}
