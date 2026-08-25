import java.util.Scanner;

/**
 * Starts the Yapper chatbot application.
 */
public class Yapper {
    private static final String SEPARATOR = "____________________________________________________________";

    public static void main(String[] args) {
        String banner = "__   __                               \n"
                + "\\ \\ / /_ _ _ __  _ __   ___ _ __     \n"
                + " \\ V / _` | '_ \\| '_ \\ / _ \\ '__|    \n"
                + "  | | (_| | |_) | |_) |  __/ |       \n"
                + "  |_|\\__,_| .__/| .__/ \\___|_|       \n"
                + "           |_|   |_|                  \n";

        System.out.println(SEPARATOR);
        System.out.print(banner);
        System.out.println(" Hello! I'm Yapper.");
        System.out.println(" What can I do for you?");
        System.out.println(SEPARATOR);

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            System.out.println(" " + command);
            System.out.println(SEPARATOR);
            command = scanner.nextLine();
        }

        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }
}
