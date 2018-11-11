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
                    "quit", "display"
            };

    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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

            case 'q':
                if (command.equals(commands[6]) || command.length() == 1) {
                    run = false;
                }
                break;

            case 'd':
                if(command.equals(commands[7]) || command.length() == 1) {
                    System.out.println(automat.toString());
                }

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
        automat.addTransition(2,3,'b');
        automat.addTransition(3,4,'j');
        automat.addTransition(4,5,'g');
        automat.addTransition(2,2,'a');
        automat.addTransition(2,4,'a');
        automat.addTransition(1,4,'b');
        automat.addTransition(1,4,'a');
    }
}
