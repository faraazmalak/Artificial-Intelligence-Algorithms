package searchalgorithms;

import java.util.LinkedList;

/**
 *
 * @author Faraaz Malak
 */
public class DFS extends Engine {

    private Graph searchTree = null;
    private int totalTimeTaken = 0;
    private LinkedList<Node> goalState = new LinkedList<Node>();
    private LinkedList<Node> crossedNodes = new LinkedList<Node>();
    private LinkedList<Node> waitingNodes = new LinkedList<Node>();
    private int timeLimit = 0;
    private Node s3 = new Node("S3", 7);
    private Node s4 = new Node("S4", 12);
    private Node s1 = new Node("S1", 1);
    private Node s2 = new Node("S2", 3);
    private boolean goalReached = false;
    Node currentNode = null;
    private int steps=0;

    /**
     *
     * @author Faraaz Malak
     * Purpose: DFS Engine
     */
    /*
     * Cosntructor
     */
    public DFS() {
        populatePeople();
    }

    /*
     * Checks if Goal state has been recahed
     */
    public boolean checkGoalState() {


        for (int i = 0; i < goalState.size(); i++) {
            boolean result = false;
            for (int i2 = 0; i2 < crossedNodes.size(); i2++) {
                if (goalState.get(i).getNodeID().equals(crossedNodes.get(i2).getNodeID())) {
                    
                    result = true;
                }
            }
            if (result == false) {
                return false;
            }
        }
        return true;
    }

    /*
     * Returns seacrh statistics
     */
    public String getStatistics() {

        return "\n==================Statistics===================" + "\nTime: " + totalTimeTaken + "\nNodes Expanded: " + searchTree.getTotalVisitedNodes() + "\nNodesChecked: " + nodesChecked + "\nSteps: "+steps;





    }

    /*
     * Populates graph
     */
    private void populatePeople() {

        searchTree = new Graph();

        searchTree.addEdge(new Edge(s1, s2));
        searchTree.addEdge(new Edge(s1, s3));
        searchTree.addEdge(new Edge(s1, s4));

        searchTree.addEdge(new Edge(s2, s1));
        searchTree.addEdge(new Edge(s2, s3));
        searchTree.addEdge(new Edge(s2, s4));

        searchTree.addEdge(new Edge(s3, s2));
        searchTree.addEdge(new Edge(s3, s4));
        searchTree.addEdge(new Edge(s3, s1));

        searchTree.addEdge(new Edge(s4, s3));
        searchTree.addEdge(new Edge(s4, s1));
        searchTree.addEdge(new Edge(s4, s2));

        goalState.add(s1);
        goalState.add(s2);
        goalState.add(s3);
        goalState.add(s4);

    }

    /*
     * Updates total Time taken
     */
    public void updateTime(int newTime) {
        totalTimeTaken += newTime;
        steps+=1;


    }

    /*
     * Checks if given node has already crossed the bridge or not
     */
    private int nodeCrossed(Node targetNode) {
        for (int i = 0; i < crossedNodes.size(); i++) {
            if (targetNode.getNodeID().equals(crossedNodes.get(i).getNodeID())) {
                return i;
            }
        }
        return -1;

    }

    /*
     * Checks if supplied node is witing for another node to cross the bridge
     */
    private boolean nodeIsWaiting(Node targetNode) {

        for (int i = 0; i < waitingNodes.size(); i++) {
            if (waitingNodes.get(i).getNodeID().equals(targetNode.getNodeID())) {
                return true;
            }
        }
        return false;
    }

    /*
     * Sets time taken by S1 to travel across bridge
     */
    public void setS1Cost(int newCost) {

        s1.setCost(newCost);

    }

    /*
     * Sets time taken by S2 to travel across bridge
     */
    public void setS2Cost(int newCost) {
        s2.setCost(newCost);
    }

    /*
     * Sets time taken by S3 to travel across bridge
     */
    public void setS3Cost(int newCost) {
        s3.setCost(newCost);
    }

    /*
     * Sets time taken by S4 to travel across bridge
     */
    public void setS4Cost(int newCost) {
        s4.setCost(newCost);
    }

    /*
     * Sets time limit for all 4 nodes to cross bridge
     */
    public void setTimeLimit(int newTimeLimit) {

        timeLimit = newTimeLimit;

    }

    /*
     * Remove all nodes that have already been visied or already existed in visitedNodesList of searchTree
     */
    private LinkedList<Edge> refine(LinkedList<Edge> edgeList) {
        LinkedList<Edge> result = new LinkedList<Edge>();
       
        for (int i = 0; i < edgeList.size(); i++) {




            boolean furtherCheck = true;
            boolean checkResult = true;
            nodesChecked += 1;

            for (int i1 = 0; i1 < searchTree.getTotalVisitedNodes(); i1++) {
                Node visitedNode = searchTree.getVisitedNodeAt(i1);
                if ((visitedNode.getNodeID().equals(edgeList.get(i).getChild().getNodeID()))) {


                    furtherCheck = false;
                    break;

                }

            }

            if (furtherCheck == true) {

                for (int i1 = 0; i1 < searchTree.getTotalFringeEdges(); i1++) {
                    Edge fringeEdge = searchTree.getFringeEdgeAt(i1);
                    if ((fringeEdge.getChild().getNodeID().equals(edgeList.get(i).getChild().getNodeID()))) {


                        checkResult = false;
                        break;

                    }

                }
            }

            if (checkResult == true) {
                
                result.add(edgeList.get(i));
            }
        }




       
        return result;

    }
    /*
     * Finds path cost of 2 nodes crossing the bridge together
     */

    private int findPathCost(Node n1, Node n2) {
        if (n1.getCost() > n2.getCost()) {
            return n1.getCost();
        } else {
            return n2.getCost();
        }
    }

    /*
     * Starts the search
     */
    public boolean runEngine() {

        Edge currentEdge = null;
        currentNode = searchTree.getRootNode();
        waitingNodes.add(searchTree.getRootNode());
        
        do {

            //Check to see if goal state has been reached
            if (checkGoalState() == true && totalTimeTaken <= timeLimit) {
                goalReached = true;
                break;
            } else {

                //If currentNode is not the destination, the do the following

                //Check to see if currentNode has already been visited
                if (searchTree.checkVisitedNode(currentNode) == false) {


                    //If current node has not been visited, then check if current node has children
                    if ((currentEdge == null) || (currentEdge != null && searchTree.hasChildren(currentNode) && findPathCost(currentEdge.getParent(), currentEdge.getChild()) <= timeLimit)) {

                        System.out.println("Expanding Node: "+currentNode.getNodeID());
                        //If current node has children, then perform a DFS on this current node
                        LinkedList<Edge> result = refine(searchTree.expandNode(currentNode));

                        for (int i = 0; i < result.size(); i++) {
                            if (searchTree.hasChildren(result.get(i).getChild()) == true && searchTree.checkVisitedNode(result.get(i).getChild()) == false) {


                                searchTree.insertFringeNodeAt(0, result.get(i));
                                break;
                            }
                        }



                    }





                    searchTree.addVisitedNode(currentNode);
                    

                }








            }
            if (searchTree.getTotalFringeEdges() > 0) {
                currentEdge = searchTree.removeNextEdge();
                currentNode = currentEdge.getChild();
            } else {
                currentEdge = null;
                currentNode = null;

            }
            if (currentEdge != null) {
                Node parent = currentEdge.getParent();
                Node child = currentEdge.getChild();
                if (nodeIsWaiting(parent) == true) {
                    //If parent node is waiting for a child node to help the child cross, then do the following
                    int results = nodeCrossed(parent);
                    if (results != -1) {
                        //If parentNode is on other side of bridge, then move the parent node back, if
                        //cost of moving the parent is less than time limit specified by user
                        if (parent.getCost() <= timeLimit) {
                            output += "\nMoing back Node " + crossedNodes.get(results).getNodeID() + " with cost: " + crossedNodes.get(results).getCost();

                            crossedNodes.remove(results);
                            updateTime(parent.getCost());
                        } else {
                            output += "\nPath cost of moving " + parent.getNodeID() + " is greater than " + timeLimit + ".Node cannot be moved";

                        }


                    }

                    //Move parent and child across the bridge, if cost of moving them is less
                    //than time limit specified by user
                    if (findPathCost(parent, child) <= timeLimit) {
                        output += "\n" + parent.getNodeID() +"(cost "+parent.getCost()+ ") and " + child.getNodeID()+"(cost "+child.getCost() + ") are moving together with cost: " + findPathCost(parent, child);
                        crossedNodes.add(parent);
                        crossedNodes.add(child);
                        updateTime(findPathCost(parent, child));
                        waitingNodes.clear();
                    } else {
                        output += "\nCost of moving " + parent.getNodeID() + " and " + child.getNodeID() + " is " + findPathCost(parent, child) + ". Nodes cannot be moved together.";

                    }

                }

               

                //Add current Node to waiting nodes clist
                waitingNodes.add(currentNode);
            }
        } while (currentEdge != null && currentNode != null);



        //If goal has been reached in time limit specified by user, then return true
        return goalReached;



    }
}
