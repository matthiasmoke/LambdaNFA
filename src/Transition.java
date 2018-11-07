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
    @Override
    public int compareTo(Transition o) {
        return 0;
    }

    public char getCharacter() {
        return this.character;
    }

    public State getStatePointer() {
        return statePointer;
    }

}
