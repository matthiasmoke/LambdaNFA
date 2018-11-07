/**
 * Represents an NFA
 */
public class LambdaNFA implements Automaton {

    private static final int START_STATE = 1;
    private static int STATES;

    /**
     * Initializes an NFA
     * @param numberOfStates maximum states NFA can have
     */
    public LambdaNFA(int numberOfStates) {
        STATES = numberOfStates;
    }

    @Override
    public boolean isValidTransition(int source, int target, char symbol) {
        return false;
    }

    @Override
    public void addTransition(int source, int target, char symbol) {

    }

    @Override
    public boolean isElement(String word) {
        return false;
    }

    @Override
    public String longestPrefix(String word) {
        return null;
    }
}
