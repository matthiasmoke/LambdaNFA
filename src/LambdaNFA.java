import java.util.ArrayList;
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
    private static Collection<State> statesFinal = new ArrayList<>();


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
        if (isValidTransition(source, target, symbol)) {
            states[source - 1].addTransition(new Transition(symbol,
                    states[target - 1]));
        }
    }

    @Override
    public boolean isElement(String word) {
        LinkedList<State> queue = new LinkedList<>();
        queue.offer(); queue.offer(start);
        int cursor = -1;

        while (!queue.isEmpty()) {
                state = queue.poll();
                if (state == $) {
                    ++cursor;
                    if (cursor < word.length()) {
                        queue.offer($);
        // one symbol completely red
                        symbol = word.charAt(cursor); // move cursor
                    }
                } else if (state in F && cursor == word.length()) {
                    return true; // state in F reached and word completely red -> accept
                } else if (cursor < word.length()) {
                    foreach (target reachable from source state via symbol) {
                        queue.offer(target); queue.offer(next(target));
                    }
                } // else no more symbols to read available
            }
        return false; // no state in F reached -> reject
    }

    @Override
    public String longestPrefix(String word) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        String nextLine = "";

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

    private void initStates() {
        for (int i = 0; i < states.length; i++) {
            if (i == states.length -1) {
                statesFinal.add(new State(states.length));
            } else {
                states[i] = new State(START_STATE + i);
            }
        }
    }
}
