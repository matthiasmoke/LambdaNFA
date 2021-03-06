import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Collections;
import java.util.HashMap;

/**
 * Represents an NFA state/node
 */
public class State {

    private int stateNumber;
    private static final int ALPHABET_NUMBER = 26;
    private List<Collection<Transition>> charAdj;
    private List<State> nextSet;

    /**
     * Creates an empty state that represents the separator-char "$"
     */
    public State() {
        stateNumber = 0;
    }

    /**
     * Creates an instance of state with given parameters
     * @param number number of state
     */
    public State(int number) {
        this.stateNumber = number;
        this.charAdj = new ArrayList<>(ALPHABET_NUMBER + 1);
        initTransitionList();
    }

    /**
     * Adds a transition from this to another state
     * @param transition transition to add
     */
    public void addTransition(Transition transition) {
        int index = getTransitionListIndex(transition.getCharacter());

        Collection<Transition> transitions = charAdj.get(index);

        if (transitions == null) {
            transitions = new LinkedList<Transition>();
            charAdj.set(index, transitions);
        }
        transitions.add(transition);
    }

    /**
     * Gets the target states for the given symbol
     * @param symbol char to search tragets for
     * @return traget-states or null if not targets are available
     */
    public Collection<State> getTargets(char symbol) {
        int index = getTransitionListIndex(symbol);
        Collection<State> states = new ArrayList<>();

        if (charAdj.get(index) != null) {
            for (Transition t : charAdj.get(index)) {
                states.add(t.getStatePointer());
            }
        }
        return states;
    }

    /**
     * Gets all transitions in ordered form
     * @return all transitions of the state or null if no transitions are there
     */
    public List<Transition> getOrderedTransitions() {
        List<Transition> adj = new LinkedList<Transition>();
        for (Collection<Transition> transitions : charAdj) {
            if (transitions != null) {
                adj.addAll(transitions);
            }
        }
        Collections.sort(adj);
        return adj;
    }

    /**
     * Computes the next-set for the state
     */
    public void precomputeNextSet() {
        nextSet = new LinkedList<>();
        Map<State, Boolean> visited = new HashMap<>();
        Queue<State> bfsQueue = new LinkedList<>();

        bfsQueue.offer(this);
        visited.put(this, true);

        while (!bfsQueue.isEmpty()) {
            State currState = bfsQueue.poll();
            if (currState != this) {
                nextSet.add(currState);
            }

            Collection<State> lambdaTargets
                    = currState.getTargets(LambdaNFA.LAMBDA);
            for (State lambdaTarget : lambdaTargets) {
                if (!visited.getOrDefault(lambdaTarget, false)) {
                    bfsQueue.offer(lambdaTarget);
                    visited.put(lambdaTarget, true);
                }
            }
        }
    }

    /**
     * Gets set of trough ’~’ reachable states of this sate
     * @return next-set or null if there are no next sets
     */
    public List<State> getNext() {
        return nextSet;
    }

    /**
     * Gets the number of the state
     * @return int that is the state's number
     */
    public int getNumber() {
        return stateNumber;
    }

    /**
     * Checks if the state has transitions to other states
     * @return true if it has transitions, false if not
     */
    public boolean hasTransitions() {
        for (Collection<Transition> transitionCollection : charAdj) {
            if (transitionCollection != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the position index for the given char in the transition collection
     * @param c char to get position for
     * @return index as integer
     */
    private int getTransitionListIndex(char c) {
        if ((int) c == LambdaNFA.LAMBDA) {
            return ALPHABET_NUMBER;
        }

        return (int) c - (int) 'a';
    }

    /**
     * Initializes the transition-list by setting each collection to null
     */
    private void initTransitionList() {
        for (int i = 0; i < ALPHABET_NUMBER + 1; i++) {
            charAdj.add(i, null);
        }
    }
}
