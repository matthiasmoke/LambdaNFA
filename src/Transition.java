import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a transition between to states of an NFA
 */
public class Transition implements Comparable<Transition>{

    private State statePointer;
    private char character;

    /**
     *
     */
    public Transition(char character, State state) {
        this.character = character;
        this.statePointer = state;
    }

    /**
     *
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

    public char getCharacter() {
        return this.character;
    }

    public State getStatePointer() {
        return statePointer;
    }

}
