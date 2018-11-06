import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *Represents an NFA state/node
 */
public class State {

    private List<Collection<Transition>> charAdj
            = new ArrayList<Collection<Transition>>();
    private int stateNumber;
    private char[] possibleCharacters;

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
     *Adds a transition from this to another state
     * @param trans
     */
    public void addTransition(Transition trans) {

    }

    /**
     *
     * @param symbol
     * @return
     */
    public Collection<State> getTargets(char symbol) {

    }

    /**
     *
     * @return
     */
    public List<Transition> getOrderedTransitions() {

    }
}
