import java.util.Scanner;

/**
 * Starts the Yapper chatbot application.
 */
public class Yapper {
    private static final String SEPARATOR = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

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
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.equals("bye")) {
                break;
            }

            if (command.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
            } else if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                tasks[taskIndex].markAsDone();
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskIndex]);
            } else if (command.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(command.substring(7)) - 1;
                tasks[taskIndex].markAsNotDone();
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskIndex]);
            } else if (command.startsWith("todo ")) {
                tasks[taskCount] = new Todo(command.substring(5).trim());
                taskCount++;
                printTaskAdded(tasks[taskCount - 1], taskCount);
            } else if (command.startsWith("deadline ")) {
                String details = command.substring(9);
                int bySeparator = details.indexOf(" /by ");
                String description = details.substring(0, bySeparator).trim();
                String by = details.substring(bySeparator + 5).trim();
                tasks[taskCount] = new Deadline(description, by);
                taskCount++;
                printTaskAdded(tasks[taskCount - 1], taskCount);
            } else if (command.startsWith("event ")) {
                String details = command.substring(6);
                int fromSeparator = details.indexOf(" /from ");
                int toSeparator = details.indexOf(" /to ", fromSeparator + 7);
                String description = details.substring(0, fromSeparator).trim();
                String from = details.substring(fromSeparator + 7, toSeparator).trim();
                String to = details.substring(toSeparator + 5).trim();
                tasks[taskCount] = new Event(description, from, to);
                taskCount++;
                printTaskAdded(tasks[taskCount - 1], taskCount);
            }
            System.out.println(SEPARATOR);
        }

        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    /**
     * Prints the confirmation shown after a task is added.
     *
     * @param task Task that was added.
     * @param taskCount Current number of tasks.
     */
    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }
}
