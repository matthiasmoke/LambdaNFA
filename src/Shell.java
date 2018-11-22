import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Main class that runs a shell for UI
 */
 public final class Shell {

     private Shell() {

    }

    private static boolean run;
    private static LambdaNFA automate;
    private static  final String[] COMMANDS =
            {
                    "init", "add", "check", "prefix", "generate", "help",
                    "display", "quit"
            };
    private static final String DEFAULT_ERROR_MESSAGE
            = "Error! No valid input...";

    /**
     * Main method that starts the shell
     * @param args main
     * @throws IOException can occur
     */
    public static void main(String[] args) throws IOException {
        BufferedReader reader
                = new BufferedReader(new InputStreamReader(System.in));
        runShell(reader);
    }

    /**
     * Runs the shell-loop
     * @param reader for user-input
     * @throws IOException can occur
     */
    private static void runShell(BufferedReader reader) throws IOException {
        run = true;

        while (run) {
            System.out.print("nfa> ");
            String input = reader.readLine();

            if (input != null) {
                evalInput(input.toLowerCase());
            }
        }
    }

    /**
     * Evals the input of the user
     * @param input userinput
     */
    private static void evalInput(String input) {

        Scanner sc = new Scanner(input);

        if (sc.hasNext()) {
            String command = sc.next();

            switch (command.charAt(0)) {
                case 'i':
                    if (command.equals(COMMANDS[0]) || command.length() == 1) {
                        int states = 0;
                        if (sc.hasNextInt()) {
                            states = sc.nextInt();
                        }
                        initNFA(states);
                    }
                    break;

                case 'a':
                    if (command.equals(COMMANDS[1]) || command.length() == 1) {
                        int j = 0;
                        int i = 0;
                        char c = 0;
                        if (sc.hasNextInt()) {
                            i = sc.nextInt();
                        }
                        if (sc.hasNextInt()) {
                            j = sc.nextInt();
                        }
                        if (sc.hasNext()) {
                            c = sc.next().charAt(0);
                        }

                        add(i, j, c);
                    }
                    break;

                case 'c':
                    if (command.equals(COMMANDS[2]) || command.length() == 1) {
                        String word = "";
                        if (sc.hasNext()) {
                            word = sc.next();
                        }
                        check(word);
                    }
                    break;

                case 'p':
                    if (command.equals(COMMANDS[3]) || command.length() == 1) {
                        String word = "";
                        if (sc.hasNext()) {
                            word = sc.next();
                        }
                        longestPrefix(word);
                    }
                    break;

                case 'g':
                    if (command.equals(COMMANDS[4]) || command.length() == 1) {
                        generateNFA();
                    }
                    break;

                case 'h':
                    if (command.equals(COMMANDS[5]) || command.length() == 1) {
                        printHelpInfo();
                    }
                    break;

                case 'd':
                    if (command.equals(COMMANDS[6]) || command.length() == 1) {
                        if (checkLambdaNFA()) {
                            System.out.println(automate.toString());
                        }
                    }
                    break;

                case 'q':
                    if (command.equals(COMMANDS[7]) || command.length() == 1) {
                        run = false;
                    }
                    break;

                default:
                    System.out.println(DEFAULT_ERROR_MESSAGE);

            }
        }
        sc.close();
    }

    private static void initNFA(int number) {
        if (number > 0) {
            automate = new LambdaNFA(number);
        } else {
            System.out.println(DEFAULT_ERROR_MESSAGE);
        }
    }

    /**
     * Lets the NFA check if word is in its language and prints the result
     * @param word to check
     */
    private static void check(String word) {
        if (checkLambdaNFA()) {
            if (checkWordForSyntax(word)) {
                if (automate.isElement(word.substring(1, word.length() - 1))) {
                    System.out.println("In language.");
                } else {
                    System.out.println("Not in language.");
                }
            } else {
                System.out.println(DEFAULT_ERROR_MESSAGE);
            }
        }
    }

    /**
     * Prints the longest prefix returned by the NFA
     * @param word to check
     */
    private static void longestPrefix(String word) {
        if (checkLambdaNFA()) {
            if (!word.equals("\"") && checkWordForSyntax(word)) {

                //remove quotation marks and get prefix
                String prefix = automate.longestPrefix(
                        word.substring(1, word.length() - 1));
                if (!prefix.equals("")) {
                    System.out.println("\"" + prefix + "\"");
                } else {
                    System.out.println("No prefix in language.");
                }

            } else {
                System.out.println(DEFAULT_ERROR_MESSAGE);
            }
        }
    }

    /**
     * Adds a transition between two states of to the automate
     * @param i source
     * @param j target
     * @param c char value
     */
    private static void add(int i, int j, char c) {
        if (checkLambdaNFA()) {
            if (automate.isValidTransition(i, j, c)) {
                automate.addTransition(i, j, c);
            } else {
                System.out.println("Error! State can not be added!");
            }
        }
    }

    /**
     * Prints help-info for the user
     */
    private static void printHelpInfo() {
        StringBuilder b = new StringBuilder();
        b.append(COMMANDS[0]);
        b.append(" n:\t\t\tInitializes a new LambdaNFA with n states\n");

        b.append(COMMANDS[1]).append(" i j c:\t\tAdds a new transition ");
        b.append("from state i to j with character c\n");

        b.append(COMMANDS[2]).append(" \"word\":");
        b.append("\tChecks if word is in language of the automate\n");

        b.append(COMMANDS[3]).append(" \"word\":\tGets the longest prefix");
        b.append(" for word in the automate\n");

        b.append(COMMANDS[4]).append(":\t\tGenerates an example automate\n");
        b.append(COMMANDS[6]).append(":\t\tDisplays the current automate\n");
        b.append(COMMANDS[7]).append(":\t\t\tQuits the program\n");

        b.append("Every command can be executed by using the first letter ");
        b.append("of the command with the certain parameters");
        System.out.println(b.toString());
    }

    /**
     * Generates an LambdaNFA for testing purposes
     */
    private static void generateNFA() {
        automate = new LambdaNFA(5);
        automate.addTransition(1, 3, '~');
        automate.addTransition(1, 2, '~');
        automate.addTransition(2, 2, '~');
        automate.addTransition(2, 3, 'a');
        automate.addTransition(2, 4, '~');
        automate.addTransition(3, 4, '~');
        automate.addTransition(3, 4, 'b');
        automate.addTransition(4, 5, 'a');
        automate.addTransition(4, 1, '~');
    }

    /**
     * Checks if word is not empty and has quotation marks
     * @param word string to check
     * @return true if syntax matches
     */
    private static boolean checkWordForSyntax(String word) {
        return (!word.equals("")
                && word.charAt(0) == '"'
                && word.charAt(word.length() - 1) == '"');
    }

    /**
     * Checks if NFA is initialized
     * @return true if its initialized, false if null
     */
    private static boolean checkLambdaNFA() {
        if (automate != null) {
            return true;
        } else {
            System.out.println("Error! LambdaNFA is not initialized.");
            return false;
        }
    }
}
