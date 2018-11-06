import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a transition between to states of an NFA
 */
public class Transition implements Comparable<Transition>{

    private Collection<State> pointsTo = new ArrayList<>();


    /**
     *
     */
    public Transition() {

    }
    @Override
    public int compareTo(Transition o) {
        return 0;
    }
}
