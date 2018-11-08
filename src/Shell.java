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
                    "init", "add", "check", "prefix", "generate", "help", "quit"
            };

    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        runShell(reader);
    }

    private static void runShell(BufferedReader reader) throws IOException {
        run = true;

        while (run) {
            System.out.println("nfa> ");
            String input = reader.readLine();

            if (input != null) {
                evalInput(input.toLowerCase());
            }
        }
    }

    private static void evalInput(String input) {

        Scanner sc = new Scanner(input);
        String userCommand = sc.next();
        switch (userCommand.charAt(0)) {
            case 'i':
                if (userCommand.equals(commands[0])) {
                    if (sc.hasNextInt()) {
                        automat = new LambdaNFA(sc.nextInt());
                    }
                }
                break;

            case 'a':
                if (userCommand.equals(commands[1])) {
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
                if (userCommand.equals(commands[2])) {

                }
                break;

            case 'p':
                if (userCommand.equals(commands[3])) {

                }
                break;

            case 'g':
                if (userCommand.equals(commands[4])) {

                }
                break;

            case 'h':
                if (userCommand.equals(commands[5])) {
                    printHelpInfo();
                }
                break;

            case 'q':
                if (userCommand.equals(commands[6])) {

                }
                break;

        }
        sc.close();
    }

    private static void add(int i, int j, char c) {
        if (i > 0 && j > 0 && c > 0) {
            automat.addTransition(i, j, c);
        }
    }

    private static void printHelpInfo() {

    }

    private  static void generateNFA() {
        automat = new LambdaNFA(5);
        automat.addTransition(1,2,'a');
        automat.addTransition(2,3,'a');
        automat.addTransition(3,4,'a');
        automat.addTransition(4,5,'a');
        automat.addTransition(2,2,'a');
        automat.addTransition(2,4,'a');
    }
}
