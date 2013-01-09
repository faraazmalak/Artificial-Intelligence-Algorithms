package searchalgorithms;

import java.util.LinkedList;

/**
 *
 * @author Faraaz Malak
 * @purpose Hill Climbing Search Engine
 */
public class BEST extends Engine {

    private Graph searchTree;
    private Node goalState = null;
    Node currentNode = null;
    LinkedList<Edge> currentPath = new LinkedList<Edge>();
    Node kuching = new Node("Kuching", 100);
    Node serian = new Node("SERIAN", 10);
    Node seriaman = new Node("SERIAMAN", 25);
    Node bau = new Node("BAU", 1);
    Node sarikei = new Node("SARIKEI", 3);
    Node kapit = new Node("KAPIT", 6);
    Node sibu = new Node("SIBU", 0);
    Node bintulu = new Node("BINTULU", 0);
    Node miri = new Node("MIRI", 0);

    /*
     * Constructor
     */
    public BEST() {
        populateGraph();
    }


    /*
     * Get search statistics
     */
    public String getStatistics() {
        String route = currentPath.get(0).getParent().getNodeID();
        int cost = 0;

        for (int i = 0; i < currentPath.size(); i++) {
            route += " -> ";
            route += currentPath.get(i).getChild().getNodeID();
            cost += currentPath.get(i).getPathCost();
        }

        String stats = "\n==================Statistics===================" + "\nRoute: " + route + "\nTotal Number of paths to reach goal state: " + currentPath.size() + "\nTotal Cost to reach goal state: " + cost + "\n" + "\nNodes Visited: " + searchTree.getTotalVisitedNodes() + "\nNodesChecked: " + nodesChecked;

        return stats;

    }

    /*
     * Check if goal state has been reached
     */
    public boolean checkGoalState(Node currentNode) {



        if (goalState != null) {
            if (goalState.getNodeID().equals(currentNode.getNodeID())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /*
     * Populate Graph
     */
    private void populateGraph() {

        searchTree = new Graph();

        searchTree.addEdge(new Edge(kuching, serian, 1));
        searchTree.addEdge(new Edge(kuching, seriaman, 7));


        searchTree.addEdge(new Edge(serian, bau, 1));
        searchTree.addEdge(new Edge(serian, seriaman, 2));
        searchTree.addEdge(new Edge(serian, sarikei, 15));

        searchTree.addEdge(new Edge(seriaman, sarikei, 10));
        searchTree.addEdge(new Edge(seriaman, kapit, 4));
        searchTree.addEdge(new Edge(seriaman, sibu, 11));

        searchTree.addEdge(new Edge(bau, kuching, 2));

        searchTree.addEdge(new Edge(sarikei, bintulu, 5));
        searchTree.addEdge(new Edge(sarikei, miri, 3));

        searchTree.addEdge(new Edge(kapit, sarikei, 3));

        searchTree.addEdge(new Edge(miri, kapit, 6));





    }

    public void setGoal(String target) {
        if (target.equals("Sibu")) {
            goalState = sibu;

        } else if (target.equals("Bintulu")) {
            goalState = bintulu;
        } else if (target.equals("Miri")) {
            goalState = miri;
        }
    }
    /*
     * Evaluation function using f=g+h
     */

    public int evaluationFunction(Edge targetEdge) {

        int g = targetEdge.getPathCost();
        for (int i = 0; i < currentPath.size(); i++) {
            g += currentPath.get(i).getPathCost();
        }


        int h = targetEdge.getChild().getCost();

        return g + h;

    }


    /*
     * Finds best destination city
     */
    private Edge findBestEdge(LinkedList<Edge> edgeList) {
        int bestCost = evaluationFunction(edgeList.get(0));
        Edge bestEdge = edgeList.get(0);

        for (int i = 1; i < edgeList.size(); i++) {
            if (bestCost > evaluationFunction(edgeList.get(i))) {

                bestCost = evaluationFunction(edgeList.get(i));
                bestEdge = edgeList.get(i);

            }
        }


        return bestEdge;

    }

    /*
     * Remove all nodes that have already been visied or already existed in visitedNodesList of searchTree
     */
    private LinkedList<Edge> refine(LinkedList<Edge> edgeList) {
        LinkedList<Edge> result = new LinkedList<Edge>();

        for (int i = 0; i < edgeList.size(); i++) {

            nodesChecked += 1;

            boolean furtherCheck = true;
            boolean checkResult = true;


            for (int i1 = 0; i1 < searchTree.getTotalVisitedNodes(); i1++) {
                Node visitedNode = searchTree.getVisitedNodeAt(i1);


                if (visitedNode.getNodeID().equals(edgeList.get(i).getChild().getNodeID())) {



                    furtherCheck = false;
                    checkResult = false;
                    break;

                }

            }

            if (furtherCheck == true) {

                for (int i1 = 0; i1 < searchTree.getTotalFringeEdges(); i1++) {
                    Edge fringeEdge = searchTree.getFringeEdgeAt(i1);
                    if (!(fringeEdge.getChild().getNodeID().equals(edgeList.get(i).getChild().getNodeID()))) {

                        checkResult = true;
                        break;

                    }

                }
            }

            if (checkResult == true) {
                result.add(edgeList.get(i));
            }
            // }
        }
        return result;

    }

    private void backTrack() {
        //For each path in currentPath
        for (int i1 = 0; i1 < currentPath.size(); i1++) {
            boolean removePath = true;

            //Get most recently visited path in currentPath
            Edge currentPathEdge = currentPath.get(currentPath.size() - 1);


            for (int i2 = 0; i2 < searchTree.getTotalFringeEdges(); i2++) {

                //if child node of most recently visited path does not have outgoing routes AND is not the goal state, then
                //backtrack
                if ((currentPathEdge.getChild().getNodeID().equals(searchTree.getFringeEdgeAt(i2).getParent().getNodeID()) || currentPathEdge.getChild().getNodeID().equals(goalState.getNodeID())) && (searchTree.checkVisitedNode(currentPathEdge.getChild()) == true)) {

                    removePath = false;
                }
            }
            if (removePath == true) {

                currentPath.removeLast();



            }
        }
        currentPath.add(searchTree.getFirstFringeEdge());
    }


    /*
     * Stars search engine
     */
    public boolean runEngine() {

        Edge currentEdge = null;
        currentNode = searchTree.getRootNode();

        boolean goalReached = false;
        Node bestChildNode = null;
        Edge bestEdge = null;
        output += "\nSelected destination (goal): " + goalState.getNodeID() + "\n";
        output += "\n***************************\n";
        do {
            if (currentNode != null) {
                output += "\n\nCurrently at city " + currentNode.getNodeID();
            }

            //Check to see if goal state has been reached
            if (checkGoalState(currentNode) == true) {
                output += "\nGoal State reached: " + currentNode.getNodeID();

                goalReached = true;
                searchTree.addVisitedNode(currentNode);
                break;
            } else {
                //If currentNode is not the destination, the do the following

                //Check to see if currentNode has already been visited
                if (searchTree.checkVisitedNode(currentNode) == false) {

                    //Add current node to list of visited nodes
                    // System.out.println(totalPathCost)

                    searchTree.addVisitedNode(currentNode);

                    if (searchTree.hasChildren(currentNode) == true) {


                        //Expand currentNode to get children

                        LinkedList<Edge> result = refine(searchTree.expandNode(currentNode));


                        if (result.size() > 0) {
                            bestEdge = findBestEdge(result);
                            bestChildNode = bestEdge.getChild();

                            output += "\nDisplaying outgoing routes from " + currentNode.getNodeID();

                            //Loop through each child to determine best heuristic
                            for (int i = 0; i < result.size(); i++) {
                                output += "\nFrom: " + result.get(i).getParent().getNodeID() + " to: " + result.get(i).getChild().getNodeID() + " with cost of " + evaluationFunction(result.get(i));

                                if (!result.get(i).equals(bestEdge)) {

                                    searchTree.insertFringeNodeAt(0, result.get(i));
                                }

                            }



                            //Insert bestEdge to beginning of Fringe Edge list, so that it is processed in next loop
                            searchTree.insertFringeNodeAt(0, bestEdge);

//                            System.out.println("Displaying Current Path: ");
//                            for (int i = 0; i < currentPath.size(); i++) {
//                                System.out.println(" Parent: " + currentPath.get(i).getParent().getNodeID() + " Child: " + currentPath.get(i).getChild().getNodeID());
//                            }



                            output += "\nBest path selected: \nFrom: " + bestEdge.getParent().getNodeID() + " To: " + bestEdge.getChild().getNodeID() + " with cost of " + evaluationFunction(bestEdge);
                            currentPath.add(bestEdge);
                        } else {
                            output += "\nNo outgoing routes found from " + currentNode.getNodeID() + "\nBacktracking";

                            backTrack();

                        }



                    } else {
                        output += "\nNo outgoing routes found from " + currentNode.getNodeID() + "\nBacktracking";
                        backTrack();
                    }

                }

            }


            if (searchTree.getTotalFringeEdges() > 0) {
                currentEdge = searchTree.removeFirstFringeEdge();
                currentNode = currentEdge.getChild();
            } else {
                currentEdge = null;
                currentNode = null;

            }

            output += "\n***************************\n";

        } while (currentEdge != null && currentNode != null);

        System.out.println("Displaying Fringe: ");
        for(int i=0;i<searchTree.getTotalFringeEdges();i++)
        {
            System.out.println("Child: "+searchTree.getFringeEdgeAt(i).getParent().getNodeID());
        }

        return goalReached;



    }

    @Override
    boolean checkGoalState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
