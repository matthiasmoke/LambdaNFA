import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Main class that runs a shell for UI
 */
public class Shell {

    private static boolean run;
    private static LambdaNFA automat;
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

    private static void evalInput(String input) {

        Scanner sc = new Scanner(input);
        String command = sc.next();
        switch (command.charAt(0)) {
            case 'i':
                if (command.equals(commands[0]) || command.length() == 1) {
                    if (sc.hasNextInt()) {
                        automat = new LambdaNFA(sc.nextInt());
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
                    if (sc.hasNext()) {
                        check(sc.next());
                    }
                }
                break;

            case 'p':
                if (command.equals(commands[3]) || command.length() == 1) {

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
                    System.out.println(automat.toString());
                }
                break;

            case 'q':
                if(command.equals(commands[7]) || command.length() == 1) {
                    run = false;
                }

            default:
                System.out.println(defaultErrorMessage);

        }
        sc.close();
    }

    private static void check(String word) {
        if (word.charAt(0) == '"' && word.charAt(word.length() - 1) == '"') {
            if (automat.isElement(word.substring(1,word.length() - 1))) {
                System.out.println(word + " is in language");
            } else {
                System.out.println(word + " is not in language");
            }
        } else {
            System.out.println(defaultErrorMessage);
        }
    }

    private static void add(int i, int j, char c) {
        if (i > 0 && j > 0 && c > 0) {
            automat.addTransition(i, j, c);
            automat.getState(i).precomputeNextSet();
        }
    }

    private static void printHelpInfo() {
        StringBuilder b = new StringBuilder();
        b.append(commands[0] + " n: Initializes a new LambdaNFA with n "
                + "states\n");
        b.append(commands[1] + " i j c: Adds a new transition from state "
                + "i to j with character c\n");
        b.append(commands[2] + "\"word\": Checks if word is in "
                + "language of the automate\n");
        b.append(commands[3] + "\"word\": Gets the longest prefix for word "
                + "in the automate\n");
        b.append(commands[4] + ": Generates an example automate\n");
        b.append(commands[6] + ": Displays the current automate\n");
        b.append(commands[7] + ": Quits the program\n");
        b.append("Every command can be executed by using the first letter "
                + "of the command with the certain parameters");
        System.out.println(b.toString());
    }

    private  static void generateNFA() {
        automat = new LambdaNFA(5);
        add(1,2,'~');
        add(2,2,'~');
        add(2,3,'a');
        add(2,4,'~');
        add(3,4,'~');
        add(3,4,'b');
        add(4,5,'a');
        add(4,1,'~');
    }
}
