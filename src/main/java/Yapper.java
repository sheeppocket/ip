import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Starts the Yapper chatbot application.
 */
public class Yapper {
    private static final String SEPARATOR = "____________________________________________________________";
    private static final Path STORAGE_PATH = Path.of("data", "yapper.txt");

    public static void main(String[] args) {
        String banner = "__   __                               \n"
                + "\\ \\ / /_ _ _ __  _ __   ___ _ __     \n"
                + " \\ V / _` | '_ \\| '_ \\ / _ \\ '__|    \n"
                + "  | | (_| | |_) | |_) |  __/ |       \n"
                + "  |_|\\__,_| .__/| .__/ \\___|_|       \n"
                + "           |_|   |_|                  \n";

        System.out.println(SEPARATOR);
        System.out.print(banner);
        System.out.println(" WELL, HELLO THERE! I'm Yapper, your spectacularly talkative task companion!");
        System.out.println(" I am positively BURSTING with enthusiasm to organize your life. What shall we do?");
        System.out.println(SEPARATOR);

        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage(STORAGE_PATH);
        ArrayList<Task> tasks;
        try {
            tasks = storage.load();
        } catch (IOException | YapperException exception) {
            System.out.println(" WHOOPS-A-DAISY! Yapper could not load your saved tasks: "
                    + exception.getMessage());
            System.out.println(" Please repair or remove " + STORAGE_PATH + " before trying again.");
            System.out.println(SEPARATOR);
            return;
        }

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            CommandType commandType = CommandType.from(command);
            if (commandType == CommandType.BYE) {
                break;
            }

            try {
                switch (commandType) {
                case LIST -> printTaskList(tasks);
                case HELP -> printHelp();
                case MARK -> markTask(command, tasks, storage);
                case UNMARK -> unmarkTask(command, tasks, storage);
                case DELETE -> deleteTask(command, tasks, storage);
                case TODO -> addTask(new Todo(getTaskDescription(command, "todo")), tasks, storage);
                case DEADLINE -> addTask(parseDeadline(command), tasks, storage);
                case EVENT -> addTask(parseEvent(command), tasks, storage);
                case UNKNOWN -> throw new YapperException("Please precede a task with a command to add it. "
                        + "Otherwise, type 'help' for a list of commands.");
                case BYE -> throw new AssertionError("The bye command should be handled before dispatch.");
                }
            } catch (YapperException exception) {
                System.out.println(" WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: "
                        + exception.getMessage());
            } catch (IOException exception) {
                System.out.println(" WHOOPS-A-DAISY! Yapper could not save your tasks: "
                        + exception.getMessage());
            }
            System.out.println(SEPARATOR);
        }

        System.out.println(" FAREWELL, magnificent human! Yapper shall now stop talking--an historic occasion!");
        System.out.println(" Return soon, because your tasks and I will have an absolutely enormous amount to discuss.");
        System.out.println(SEPARATOR);
    }

    /**
     * Prints the confirmation shown after a task is added.
     *
     * @param task Task that was added.
     * @param taskCount Current number of tasks.
     */
    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println(" OHOHO, MAGNIFICENT! I have triumphantly added this dazzling new task:");
        System.out.println("   " + task);
        System.out.println(" Your legendary list now contains " + taskCount
                + " task(s). Yes, I counted them personally!");
    }

    /**
     * Prints the confirmation shown after a task is deleted.
     *
     * @param task Task that was deleted.
     * @param taskCount Current number of tasks.
     */
    private static void printTaskDeleted(Task task, int taskCount) {
        System.out.println(" BEHOLD! With one decisive flourish, I have removed this task from existence:");
        System.out.println("   " + task);
        System.out.println(" Your freshly streamlined list now contains " + taskCount
                + " task(s). Delightfully tidy!");
    }

    /** Adds a task and prints Yapper's confirmation. */
    private static void addTask(Task task, ArrayList<Task> tasks, Storage storage) throws IOException {
        tasks.add(task);
        storage.save(tasks);
        printTaskAdded(task, tasks.size());
    }

    /** Marks the task selected by a mark command as completed. */
    private static void markTask(String input, ArrayList<Task> tasks, Storage storage)
            throws YapperException, IOException {
        int taskIndex = parseTaskIndex(input, "mark", tasks.size());
        tasks.get(taskIndex).markAsDone();
        storage.save(tasks);
        System.out.println(" CONFETTI CANNONS! This task is now officially, unmistakably, spectacularly DONE:");
        System.out.println("  " + tasks.get(taskIndex));
    }

    /** Marks the task selected by an unmark command as incomplete. */
    private static void unmarkTask(String input, ArrayList<Task> tasks, Storage storage)
            throws YapperException, IOException {
        int taskIndex = parseTaskIndex(input, "unmark", tasks.size());
        tasks.get(taskIndex).markAsNotDone();
        storage.save(tasks);
        System.out.println(" PLOT TWIST! This task has returned to the thrilling realm of NOT DONE YET:");
        System.out.println("  " + tasks.get(taskIndex));
    }

    /** Removes the task selected by a delete command. */
    private static void deleteTask(String input, ArrayList<Task> tasks, Storage storage)
            throws YapperException, IOException {
        int taskIndex = parseTaskIndex(input, "delete", tasks.size());
        Task removedTask = tasks.remove(taskIndex);
        storage.save(tasks);
        printTaskDeleted(removedTask, tasks.size());
    }

    /** Extracts and validates a task description following a command word. */
    private static String getTaskDescription(String input, String command) throws YapperException {
        String description = input.substring(command.length()).trim();
        if (description.isEmpty()) {
            throw new YapperException("The " + command + " description cannot be empty. "
                    + "Try: " + command + " <task description>");
        }
        return description;
    }

    /** Parses and validates the task number supplied to a task-index command. */
    private static int parseTaskIndex(String input, String command, int taskCount)
            throws YapperException {
        String indexText = input.substring(command.length()).trim();
        if (indexText.isEmpty()) {
            throw new YapperException("Please specify a task number. Try: " + command + " <task number>");
        }

        try {
            int taskIndex = Integer.parseInt(indexText) - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
                throw new YapperException("Task number " + indexText + " is not in the list. "
                        + "Type 'list' to see the available task numbers.");
            }
            return taskIndex;
        } catch (NumberFormatException exception) {
            throw new YapperException("'" + indexText + "' is not a valid task number. "
                    + "Try: " + command + " <task number>");
        }
    }

    /** Parses a deadline command, including its required /by value. */
    private static Deadline parseDeadline(String input) throws YapperException {
        String details = getTaskDescription(input, "deadline");
        int bySeparator = details.indexOf(" /by ");
        if (bySeparator <= 0 || bySeparator + 5 >= details.length()) {
            throw new YapperException("A deadline needs a description and a due date. "
                    + "Try: deadline <task description> /by <date or time>");
        }
        String description = details.substring(0, bySeparator).trim();
        String by = details.substring(bySeparator + 5).trim();
        try {
            return new Deadline(description, TaskDateTime.parse(by));
        } catch (IllegalArgumentException exception) {
            throw new YapperException("Please use yyyy-MM-dd HHmm or d/M/yyyy HHmm for the deadline date."
                    + " For example: deadline return book /by 2019-12-02 1800");
        }
    }

    /** Parses an event command, including its required /from and /to values. */
    private static Event parseEvent(String input) throws YapperException {
        String details = getTaskDescription(input, "event");
        int fromSeparator = details.indexOf(" /from ");
        int toSeparator = fromSeparator < 0 ? -1 : details.indexOf(" /to ", fromSeparator + 7);
        if (fromSeparator <= 0 || toSeparator <= fromSeparator + 7 || toSeparator + 5 >= details.length()) {
            throw new YapperException("An event needs a description, start, and end. "
                    + "Try: event <task description> /from <start> /to <end>");
        }
        String description = details.substring(0, fromSeparator).trim();
        String from = details.substring(fromSeparator + 7, toSeparator).trim();
        String to = details.substring(toSeparator + 5).trim();
        try {
            java.time.LocalDateTime start = TaskDateTime.parse(from);
            java.time.LocalDateTime end = TaskDateTime.parse(to);
            if (end.isBefore(start)) {
                throw new YapperException("The event end cannot be before its start.");
            }
            return new Event(description, start, end);
        } catch (IllegalArgumentException exception) {
            throw new YapperException("Please use yyyy-MM-dd HHmm or d/M/yyyy HHmm for event dates."
                    + " For example: event workshop /from 2019-12-02 1400 /to 2019-12-02 1600");
        }
    }

    /** Prints all tasks currently stored by the chatbot. */
    private static void printTaskList(ArrayList<Task> tasks) {
        System.out.println(" ATTENTION, EVERYONE! Yapper proudly presents your complete and glorious task list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
    }

    /** Prints the running list of commands supported by the chatbot. */
    private static void printHelp() {
        System.out.println(" PREPARE YOURSELF! Here is Yapper's impressively comprehensive command repertoire:");
        System.out.println("  todo <description>");
        System.out.println("  deadline <description> /by <yyyy-MM-dd HHmm>");
        System.out.println("  event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        System.out.println("  list");
        System.out.println("  mark <task number>");
        System.out.println("  unmark <task number>");
        System.out.println("  delete <task number>");
        System.out.println("  help");
        System.out.println("  bye");
    }
}
