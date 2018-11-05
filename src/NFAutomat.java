/**
 *
 */
public class NFAutomat implements Automaton {
    @Override
    public boolean isValidTransition(int source, int target, char symbol) {
        return false;
    }

    @Override
    public void addTransition(int source, int target, char symbol) {

    }

    @Override
    public boolean isElement(String word) {
        return false;
    }

    @Override
    public String longestPrefix(String word) {
        return null;
    }
}
