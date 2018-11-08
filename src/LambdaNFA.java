/**
 * Represents an NFA
 */
public class LambdaNFA implements Automaton {

    private static final int START_STATE = 1;
    private static State[] states;


    /**
     * Initializes an NFA
     * @param numberOfStates maximum states NFA can have
     */
    public LambdaNFA(int numberOfStates) {
        states = new State[numberOfStates];
        initStates();
    }

    @Override
    public boolean isValidTransition(int source, int target, char symbol) {
        return false;
    }

    @Override
    public void addTransition(int source, int target, char symbol) {
        states[source - 1].addTransition(new Transition(symbol, states[target - 1]));
    }

    @Override
    public boolean isElement(String word) {
        return false;
    }

    @Override
    public String longestPrefix(String word) {
        return null;
    }

    @Override
    public String toString() {
        return "";
    }

    private void initStates() {
        for (int i = 0; i < states.length; i++) {
            states[i] = new State(START_STATE + i);

        }
    }
}
