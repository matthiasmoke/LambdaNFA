import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Main class that runs a shell for UI
 */
final class Shell {

    private static boolean run;
    private static LambdaNFA automata;
    private static  final String[] commands =
            {
                    "init", "add", "check", "prefix", "generate", "help",
                    "display", "quit"
            };
    private static final String defaultErrorMessage
            = "Error! No valid input...";

    public static void main(String[] args) throws IOException{
        BufferedReader reader
                = new BufferedReader(new InputStreamReader(System.in));
        runShell(reader);
    }

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
                    if (command.equals(commands[0]) || command.length() == 1) {
                        if (sc.hasNextInt()) {
                            automata = new LambdaNFA(sc.nextInt());
                        } else {
                            System.out.println(defaultErrorMessage);
                        }
                    }
                    break;

                case 'a':
                    if (command.equals(commands[1]) || command.length() == 1) {
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

                        add(i,j,c);
                    }
                    break;

                case 'c':
                    if (command.equals(commands[2]) || command.length() == 1) {
                        String word = "";
                        if (sc.hasNext()) {
                            word = sc.next();
                        }
                        check(word);
                    }
                    break;

                case 'p':
                    if (command.equals(commands[3]) || command.length() == 1) {
                        String word = "";
                        if (sc.hasNext()) {
                            word = sc.next();
                        }
                        longestPrefix(word);
                    }
                    break;

                case 'g':
                    if (command.equals(commands[4]) || command.length() == 1) {
                        generateNFA();
                    }
                    break;

                case 'h':
                    if (command.equals(commands[5]) || command.length() == 1) {
                        printHelpInfo();
                    }
                    break;

                case 'd':
                    if (command.equals(commands[6]) || command.length() == 1) {
                        if (checkLambdaNFA()) {
                            System.out.println(automata.toString());
                        }
                    }
                    break;

                case 'q':
                    if(command.equals(commands[7]) || command.length() == 1) {
                        run = false;
                    }
                    break;

                default:
                    System.out.println(defaultErrorMessage);

            }
        }
        sc.close();
    }

    private static void check(String word) {

        if (checkLambdaNFA()) {
            if (checkWordForSyntax(word)) {
                if (automata.isElement(word.substring(1,word.length() - 1))) {
                    System.out.println(word + " is in language");
                } else {
                    System.out.println(word + " is not in language");
                }
            } else {
                System.out.println(defaultErrorMessage);
            }
        }
    }

    private static void longestPrefix(String word) {

        if (checkLambdaNFA()) {
            if (checkWordForSyntax(word)) {

                //remove quotation marks and get prefix
                String prefix = automata.longestPrefix(
                        word.substring(1,word.length() - 1));
                if (!prefix.equals("")) {
                    System.out.println(prefix);
                } else {
                    System.out.println("No longest prefix");
                }

            } else {
                System.out.println(defaultErrorMessage);
            }
        }
    }

    private static void add(int i, int j, char c) {
        if (checkLambdaNFA()) {
            if (automata.isValidTransition(i, j, c)) {
                automata.addTransition(i, j, c);
            } else {
                System.out.println("Error! State can not be added!");
            }
        }
    }

    private static void printHelpInfo() {
        StringBuilder b = new StringBuilder();
        b.append(commands[0]);
        b.append(" n:\t\t\tInitializes a new LambdaNFA with n states\n");

        b.append(commands[1]).append(" i j c:\t\tAdds a new transition ");
        b.append("from state i to j with character c\n");

        b.append(commands[2]).append(" \"word\":");
        b.append("\tChecks if word is in language of the automate\n");

        b.append(commands[3]).append("\"word\":\tGets the longest prefix");
        b.append(" for word in the automate\n");

        b.append(commands[4]).append(":\t\tGenerates an example automate\n");
        b.append(commands[6]).append(":\t\tDisplays the current automate\n");
        b.append(commands[7]).append(":\t\t\tQuits the program\n");

        b.append("Every command can be executed by using the first letter ");
        b.append("of the command with the certain parameters");
        System.out.println(b.toString());
    }

    private static void generateNFA() {
        automata = new LambdaNFA(5);
        automata.addTransition(1,2,'~');
        automata.addTransition(2,2,'~');
        automata.addTransition(2,3,'a');
        automata.addTransition(2,4,'~');
        automata.addTransition(3,4,'~');
        automata.addTransition(3,4,'b');
        automata.addTransition(4,5,'a');
        automata.addTransition(4,1,'~');
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

    private static boolean checkLambdaNFA() {
        if (automata != null) {
            return true;
        } else {
            System.out.println("Error! LambdaNFA is not initialized.");
            return false;
        }
    }
}
