
package searchalgorithms;

/**
 *
 * @author Faraaz Malak
 * @PURPOSe: ADBSTRCT engine class, from which other engines inherit
 */
public abstract class Engine {

    protected String output = "";
    protected int nodesChecked = 1;

    abstract boolean checkGoalState();

    abstract boolean runEngine();

    public String getOutput() {
        return output;
    }

    ;
}
