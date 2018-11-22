/**
 * Represents a transition between to states of an NFA
 */
public class Transition implements Comparable<Transition> {

    private State statePointer;
    private char character;

    /**
     * Creates an instance of Transition
     * @param character that the transition holds
     * @param state that the transition points to
     */
    public Transition(char character, State state) {
        this.character = character;
        this.statePointer = state;
    }

    /**
     * Compares two transition objects
     * @return {@code < 0} if {@code this < other},
     *         {@code = 0} if equal, and
     *         {@code > 0} if {@code this > other}.
     */
    @Override
    public int compareTo(Transition o) {
        if (o != null && o.statePointer.getNumber() > 0) {
            if (this.statePointer.getNumber() < o.statePointer.getNumber()) {
                return -1;
            }

            if (this.statePointer.getNumber() == o.statePointer.getNumber()) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * Gets the character of the transition
     * @return char
     */
    public char getCharacter() {
        return this.character;
    }

    /**
     * Gets the state on that the transition points
     * @return state
     */
    public State getStatePointer() {
        return statePointer;
    }

}
