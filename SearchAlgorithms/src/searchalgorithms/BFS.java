package searchalgorithms;

import java.util.LinkedList;

/**
 *
 * @author Faraaz Malak
 * Purpose: Breadth First Search Engine
 */
public class BFS extends Engine {

    private Graph searchTree = new Graph();
    int totalTimeTaken = 0;
    private LinkedList<Edge> edgeList = null;
    private LinkedList<Node> crossedNodes = new LinkedList<Node>();
    private int timeLimit = -1;
    private LinkedList<Node> goalState = new LinkedList<Node>();
    private Node s1 = new Node("S1", 1);
    private Node s2 = new Node("S2", 3);
    private Node s3 = new Node("S3", 7);
    private Node s4 = new Node("S4", 12);
    private boolean goalReached = false;
    private Node currentNode = null;
    private int steps=0;

    /*
     * Constructor
     */
    public BFS() {
        populateGraph();
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

    private int evaluationFunction(Edge targetEdge) {
        if (targetEdge.getParent().getCost() > targetEdge.getChild().getCost()) {
            return targetEdge.getParent().getCost();
        } else {
            return targetEdge.getChild().getCost();
        }
    }
    /*
     * Checks if goal state has been reached
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
     * Allows user to set time limit
     */
    public void setTimeLimit(int newTimeLimit) {

        timeLimit = newTimeLimit;

    }

    /*
     * Populates graph
     */
    private void populateGraph() {


        searchTree = new Graph();

        goalState.add(s1);
        goalState.add(s2);
        goalState.add(s3);
        goalState.add(s4);



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
     * Finds path cost when 2 people travel together
     */

    private int findPathCost(Node n1, Node n2) {
        if (n1.getCost() > n2.getCost()) {
            return n1.getCost();
        } else {
            return n2.getCost();
        }
    }

    /*
     * Updates total Time taken
     */
    private void updateTime(int newTime) {
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
     * Moves people across teh bridge
     */
    public void movePeople(Edge currentEdge) {

        Node parent = currentEdge.getParent();
        Node child = currentEdge.getChild();



        //Check to see if parent has already crossed the bridge
        int result = nodeCrossed(parent);
        if (result != -1) {
            //If parent node is on other side of the bridge, then move it back (only if path cost
            //to move parent node is less than time limit specified by user)
            if (parent.getCost() <= timeLimit) {
                output += "\nMoving back node " + parent.getNodeID() + " with path cost :" + parent.getCost();
                updateTime(parent.getCost());
                crossedNodes.remove(result);
            }
            //Move parent and child nodes tn other side of the bridge(only if path cost
            //to move parent & child nodes is less than time limit specified by user)
            if (findPathCost(parent, child) <= timeLimit) {
                output += "\n" + parent.getNodeID() + "(cost " + parent.getCost() + ") and " + child.getNodeID() + "(cost " + child.getCost() + ") are moving with path cost:" + evaluationFunction(currentEdge);
                updateTime(evaluationFunction(currentEdge));

                crossedNodes.add(child);
                crossedNodes.add(parent);
            }



        } else if (evaluationFunction(currentEdge) > timeLimit && result != -1) {
            output += "\nCannot move back node " + parent.getNodeID() + " since its path cost > specified time limit";
        } else {

            //Move parent and child nodes tn other side of the bridge(only if path cost
            //to move parent & child nodes is less than time limit specified by user)
            if (findPathCost(parent, child) <= timeLimit) {
                output += "\n" + parent.getNodeID() + "(cost " + parent.getCost() + ") and " + child.getNodeID() + "(cost " + child.getCost() + ") are moving with path cost:" + evaluationFunction(currentEdge);
                updateTime(evaluationFunction(currentEdge));

                crossedNodes.add(child);
                crossedNodes.add(parent);
            }
        }




    }

    /*
     * Get search statistics
     */
    public String getStatistics() {


        return "\n==================Statistics===================" + "\nTime: " + totalTimeTaken + "\nNodes Expanded: " + searchTree.getTotalVisitedNodes() + "\nNodes checked: " + (nodesChecked) + "\nSteps: "+steps;







    }

    /*
     * Run the search engine
     */
    public boolean runEngine() {

        currentNode = searchTree.getRootNode();

        Edge currentEdge = null;

        do {



            if (checkGoalState() == true && totalTimeTaken <= timeLimit) {
                if (searchTree.checkVisitedNode(currentNode) == false) {
                    searchTree.addVisitedNode(currentNode);
                }
                goalReached = true;
                break;
            } else if (searchTree.checkVisitedNode(currentNode) == false) {




                if ((currentEdge == null) || (searchTree.hasChildren(currentNode) && findPathCost(currentEdge.getParent(), currentEdge.getChild()) <= timeLimit)) {




                    LinkedList<Edge> result = refine(searchTree.expandNode(currentNode));

                    if (result.size() > 0) {
                        searchTree.appendFringeEdge(result);
                    }

                    if (currentEdge != null) {
                        movePeople(currentEdge);
                    }
                } else {
                    output += "\nPathCost is greater than the specified time limit. Node cannot be expanded";
                }
                searchTree.addVisitedNode(currentNode);


            }



            if (searchTree.getTotalFringeEdges() > 0) {
                currentEdge = searchTree.removeNextEdge();
                currentNode = currentEdge.getChild();
            } else {
                currentEdge = null;
                currentNode = null;

            }

        } while (currentEdge != null && currentNode != null);



        return goalReached;
    }
}
