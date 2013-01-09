package searchalgorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Faraaz Malak
 * @purpose: Represents a graph
 */
public class Graph {

    private LinkedList<Node> visitedNodes = new LinkedList<Node>();
    private LinkedList<Edge> fringeEdges = new LinkedList<Edge>();
    private Map<Node, LinkedList<Edge>> tree = new HashMap<Node, LinkedList<Edge>>();
    Node lastExpandedNode = null;
    Node rootNode = null;

    public Graph() {
        visitedNodes.clear();
        fringeEdges.clear();
        tree.clear();
        lastExpandedNode = null;



    }

    /*
     * Adds edge to the graph
     */
    public void addEdge(Edge newEdge) {

        if (tree.containsKey(newEdge.getParent()) == true) {


            tree.get(newEdge.getParent()).add(newEdge);

        } else {

            tree.put(newEdge.getParent(), new LinkedList<Edge>());
            LinkedList<Edge> list = tree.get(newEdge.getParent());
            list.add(newEdge);



        }

        if (rootNode == null) {
            rootNode = newEdge.getParent();


        }

    }

    /*
     * Gets root node
     */
    public Node getRootNode() {


        return rootNode;

    }

    /*
     * Gets the list of fringeEdges
     */
    public int getFringeSize() {

        return fringeEdges.size();
    }


    /*
     * Checks if specified node has children
     */
    public boolean hasChildren(Node targetNode) {
        if (tree.get(targetNode) == null || tree.get(targetNode).size() == 0) {
            return false;
        } else {
            return true;
        }
    }


    /*
     * Adds supplied node to list of visitedNodes
     */
    public void addVisitedNode(Node targetNode) {

       // System.out.println("Adding visited Node: "+targetNode.getNodeID());
        visitedNodes.add(targetNode);
    }

    /*
     * Gets total number of unvisited paths in graph
     */
    public int getTotalFringeEdges() {
        return fringeEdges.size();

    }

    /*
     * Gets fringe Edge at specified index
     */
    public Edge getFringeEdgeAt(int index) {
        return fringeEdges.get(index);
    }

    /*
     * Removes and returns fringe edge from fringeEdges
     */
    public Edge removeNextEdge() {
        if (fringeEdges.size() > 0) {
            Edge returnNode = fringeEdges.removeFirst();
            return returnNode;
        } else {
            return null;
        }


    }

    /*
     * Removes and returns fringe edge at specified index
     */
    public Edge removeFringeEdgeAt(int index) {
        if (fringeEdges.size() > 0) {
            Edge returnNode = fringeEdges.get(index);
            return returnNode;
        } else {
            return null;
        }


    }

    /*
     * Returns next egde in fringeEdges
     */
    public Edge getNextEdge() {
        if (fringeEdges.size() > 0) {
            Edge returnNode = fringeEdges.getFirst();
            return returnNode;
        } else {
            return null;
        }


    }

    /*
     * Gets first edge from fringeEdges
     */
    public Edge getFirstFringeEdge() {

        return fringeEdges.get(0);


    }

       /*
     * Gets first edge from fringeEdges
     */
    public Edge removeFirstFringeEdge() {

        return fringeEdges.remove(0);


    }

    /*
     * Returns last expanded node
     */
    public Node getLastExpandedNode() {
        return lastExpandedNode;
    }

    /*
     * Returns total number of visited nodes
     */
    public int getTotalVisitedNodes() {
        return visitedNodes.size();

    }

    /*
     * Inserts fringe edge list at specified index
     */
    public void insertFringeEdgeAt(int index, LinkedList<Edge> fringeList) {

        for (int i = 0; i < fringeList.size(); i++) {
            fringeEdges.add(index, fringeList.get(index));
            index += 1;
        }



    }

    /*
     * Inserts fringe edge at specified index
     */
    public void insertFringeNodeAt(int index, Edge fringeEdge) {

        fringeEdges.add(index, fringeEdge);



    }

    /*
     * Inserts fringe edge list
     */
    public void appendFringeEdge(LinkedList<Edge> fringeList) {

        for (int i = 0; i < fringeList.size(); i++) {
            fringeEdges.add(fringeList.get(i));



        }



    }

    /*
     * Inserts fringe edge
     */
    public void appendFringeEdge(Edge edge) {

        fringeEdges.add(edge);



    }

    /*
     * Returns fringeEdge size
     */
    public int getFringeEdgeSize() {
        return fringeEdges.size();
    }

    /*
     * Returns last edge from fringeEdges
     */
    public Edge getLastEdge() {
        if (fringeEdges.size() > 0) {
            Edge returnNode = fringeEdges.removeLast();

            return returnNode;
        } else {
            return null;
        }
    }

    /*
     * Returns visited node at specified index
     */
    public Node getVisitedNodeAt(int index) {
        return visitedNodes.get(index);
    }

    /*
     * Chekcs if spcified node has already been visited
     */
    public boolean checkVisitedNode(Node targetNode) {
        for (int i = 0; i < visitedNodes.size(); i++) {
            if (visitedNodes.get(i).getNodeID().equals(targetNode.getNodeID())) {
                return true;
            }
        }

        return false;
    }

    public LinkedList<Edge> getEdges(Node targetNode) {
        return tree.get(targetNode);

    }

    /*
     * Expand specified node
     */
    public LinkedList<Edge> expandNode(Node targetNode) {


        LinkedList<Edge> newFringeList = tree.get(targetNode);
        lastExpandedNode = targetNode;

        Set keySet = tree.keySet();
        Iterator<Node> it = keySet.iterator();


        return newFringeList;

    }
}
