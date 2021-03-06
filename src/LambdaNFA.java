import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents an NFA
 */
public class LambdaNFA implements Automaton {

    static final char LAMBDA = '~';
    private static final int START_STATE = 1;
    private static State[] states;

    /**
     * Collection of end-states
     */
    private Collection<State> statesFinal = new LinkedList<>();

    // information about the condition of the state
    private boolean nextSetIsComputed = false;
    private boolean endStatesChecked = false;

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
        return (source > 0 && source <= states.length)
                && (target > 0 && target <= states.length)
                && ((symbol >= Automaton.FIRST_SYMBOL
                && symbol <= Automaton.LAST_SYMBOL)
                || symbol == LambdaNFA.LAMBDA);
    }

    @Override
    public void addTransition(int source, int target, char symbol) {
            states[source - 1].addTransition(new Transition(symbol,
                    states[target - 1]));

            //next set has to be computed again
            nextSetIsComputed = false;
            endStatesChecked = false;
    }

    @Override
    public boolean isElement(String word) {
        int cursor = -1;
        State currState;
        char symbol = 0;
        Queue<State> queue = new LinkedList<>();

        // Update state information if necessary before checking word
        checkEndStates();
        computeAllNextSets();

        queue.offer(new State());   // empty state as separator char
        queue.offer(states[START_STATE - 1]);
        queue.addAll(states[START_STATE - 1].getNext());

        while (!queue.isEmpty()) {
            currState = queue.poll();
            if (currState.getNumber() == 0) {
                ++cursor;
                if (cursor < word.length()) {
                    queue.offer(new State());
                    symbol = word.charAt(cursor); // move cursor
                }
            } else if (isInEndStates(currState) && cursor == word.length()) {
                return true; // state in F and word completely red -> accept
            } else if (cursor < word.length()) {
                for (State target : currState.getTargets(symbol)) {

                    queue.addAll(target.getNext());
                    queue.offer(target);
                }
            } // else no more symbols to read available
        }
        return false; // no state in F reached -> reject
    }

    @Override
    public String longestPrefix(String word) {
        int cursor = -1;
        State currState;
        char symbol = 0;
        String currString = "";
        String longestPrefix = "";
        Queue<State> queue = new LinkedList<>();

        // Update state information if necessary before searching longestPrefix
        checkEndStates();
        computeAllNextSets();

        queue.offer(new State());   // empty state as separator char
        queue.offer(states[START_STATE - 1]);
        queue.addAll(states[START_STATE - 1].getNext());

        while (!queue.isEmpty()) {
            currState = queue.poll();
            if (currState.getNumber() == 0) {
                ++cursor;
                if (cursor < word.length()) {
                    queue.offer(new State());
                    currString += symbol;      // add symbol to current string
                    symbol = word.charAt(cursor); // move cursor
                }
            } else if (isInEndStates(currState)) {

                // save string thats in language
                if (word.length() == cursor) {
                    longestPrefix = word;
                } else {
                    longestPrefix = currString;
                }
            } else if (cursor < word.length()) {
                for (State target : currState.getTargets(symbol)) {

                    queue.addAll(target.getNext());
                    queue.offer(target);
                }
            } // else no more symbols to read available
        }
        return longestPrefix;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        String nextLine = "";

        // check transitions for every state in automate
        for (State s : states) {
            for (Transition t : s.getOrderedTransitions()) {
                output.append(nextLine);
                nextLine = "\n";
                output.append(String.format("(%s, %s) %c", s.getNumber(),
                        t.getStatePointer().getNumber(), t.getCharacter()));
            }
        }
        return output.toString();
    }

    /**
     * Checks if given state is a final state
     * @param s state
     * @return true if s is in end-states
     */
    private boolean isInEndStates(State s) {
        for (State endSate : statesFinal) {
            if (s.getNumber() == endSate.getNumber()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Initializes the state array with states
     */
    private void initStates() {
        for (int i = 0; i < states.length; i++) {
            states[i] = new State(START_STATE + i);
        }
    }

    /**
     * Searches for states without that have no transitions to other states,
     * and adds them to end-states array
     */
    private void checkEndStates() {
        if (!endStatesChecked) {
            for (State s : states) {
                if (!s.hasTransitions()) {
                    statesFinal.add(s);
                }
            }
            endStatesChecked = true;
        }
    }

    /**
     * Computes the next-stets for all states in the NFA
     */
    private void computeAllNextSets() {
        if (!nextSetIsComputed) {
            for (State s : states) {
                s.precomputeNextSet();
            }
            nextSetIsComputed = true;
        }
    }
}
