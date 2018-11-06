import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Main class that runs a shell for UI
 */
public class Shell {

    private static boolean run;
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
        switch (userCommand) {
            case "i":
                if (userCommand.equals(commands[0])) {

                }
                break;

            case "a":
                if (userCommand.equals(commands[1])) {

                }
                break;

            case "c":
                if (userCommand.equals(commands[2])) {

                }
                break;

            case "p":
                if (userCommand.equals(commands[3])) {

                }
                break;

            case "g":
                if (userCommand.equals(commands[4])) {

                }
                break;

            case "h":
                if (userCommand.equals(commands[5])) {
                    printHelpInfo();
                }
                break;

            case "q":
                if (userCommand.equals(commands[6])) {

                }
                break;

        }
        sc.close();
    }

    private static void printHelpInfo() {

    }
}
