import java.util.*;

/**
 * Represents an NFA state/node
 */
public class State {

    private List<Collection<Transition>> charAdj
            = new ArrayList<Collection<Transition>>();
    private int stateNumber;
    private static final int ALPHABET_NUMBER = 26;

    /**
     * Initializes a State
     * @param number
     * @param transition
     */
    public State(int number, Transition transition) {
        this.stateNumber = number;
        addTransition(transition);
    }

    /**
     *
     * @param number
     */
    public State(int number) {
        this.stateNumber = number;
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

        for(Transition t : charAdj.get(index)) {
            states.add(t.getStatePointer());
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
            adj.addAll(transitions);
        }
        Collections.sort(adj);
        return adj;
    }

    /**
     * Gets the position index for the given char in the transition collection
     * @param c char to get position for
     * @return index as integer
     */
    private int getTransitionListIndex(char c) {
        if ((int) c == 126) {
            return ALPHABET_NUMBER + 1;
        }

        return (int) c - (int) 'a';
    }
}
