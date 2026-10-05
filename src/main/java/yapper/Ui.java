package yapper;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Reads console input and displays Yapper's messages and task listings.
 */
public class Ui {
    /** Horizontal divider printed between console responses. */
    private static final String SEPARATOR = "____________________________________________________________";
    /** Reader for command lines from standard input. */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Returns whether another command is available, including piped input.
     *
     * @return Whether the input contains another line.
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads and trims the next command.
     *
     * @return Next input line with leading and trailing whitespace removed.
     * @throws java.util.NoSuchElementException If no input line remains.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Separates responses visually.
     */
    public void showSeparator() {
        System.out.println(SEPARATOR);
    }

    /**
     * Displays the banner and greeting.
     */
    public void showWelcome() {
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

    }

    /**
     * Displays the farewell on exit or end of input.
     */
    public void showGoodbye() {
        System.out.println(" FAREWELL, magnificent human! Yapper shall now stop talking--an historic occasion!");
        System.out.println(" Return soon, because your tasks and I will have an absolutely enormous"
                + " amount to discuss.");
        System.out.println(SEPARATOR);
    }

    /**
     * Prints the confirmation shown after a task is added.
     *
     * @param task Task that was added.
     * @param taskCount Current number of tasks.
     */
    public void printTaskAdded(Task task, int taskCount) {
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
    public void printTaskDeleted(Task task, int taskCount) {
        System.out.println(" BEHOLD! With one decisive flourish, I have removed this task from existence:");
        System.out.println("   " + task);
        System.out.println(" Your freshly streamlined list now contains " + taskCount
                + " task(s). Delightfully tidy!");
    }

    /**
     * Prints all tasks currently stored by the chatbot.
     *
     * @param tasks Tasks to display in order with one-based numbers.
     */
    public void printTaskList(List<Task> tasks) {
        System.out.println(" ATTENTION, EVERYONE! Yapper proudly presents your complete and glorious task list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints the running list of commands supported by the chatbot.
     */
    public void printHelp() {
        System.out.println(" PREPARE YOURSELF! Here is Yapper's impressively comprehensive command repertoire:");
        System.out.println("  todo <description>");
        System.out.println("  deadline <description> /by <yyyy-MM-dd HHmm>");
        System.out.println("  event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        System.out.println("  on <yyyy-MM-dd>");
        System.out.println("  list");
        System.out.println("  mark <task number>");
        System.out.println("  unmark <task number>");
        System.out.println("  delete <task number>");
        System.out.println("  help");
        System.out.println("  bye");
    }

    /**
     * Displays a command validation error.
     *
     * @param message Explanation to display.
     */
    public void showError(String message) {
        System.out.println(" WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: " + message);
    }

    /**
     * Explains why startup stopped without overwriting unreadable data.
     *
     * @param message Explanation of the load failure.
     * @param path Storage file the user should repair or remove.
     */
    public void showLoadingError(String message, Path path) {
        System.out.println(" WHOOPS-A-DAISY! Yapper could not load your saved tasks: " + message);
        System.out.println(" Please repair or remove " + path + " before trying again.");
        showSeparator();
    }

    /**
     * Reports a failure to persist a task change.
     *
     * @param message Explanation of the save failure.
     */
    public void showSavingError(String message) {
        System.out.println(" WHOOPS-A-DAISY! Yapper could not save your tasks: " + message);
    }

    /**
     * Confirms the task's new completion state.
     *
     * @param task Task to display.
     * @param isDone Whether to show the completed or incomplete confirmation.
     */
    public void showTaskStatus(Task task, boolean isDone) {
        if (isDone) {
            System.out.println(" CONFETTI CANNONS! This task is now officially, unmistakably, spectacularly DONE:");
        } else {
            System.out.println(" PLOT TWIST! This task has returned to the thrilling realm of NOT DONE YET:");
        }
        System.out.println("  " + task);
    }

    /**
     * Displays date matches with their original task numbers.
     *
     * @param date Calendar date to display and match.
     * @param tasks Complete task list from which to select matches.
     */
    public void printTasksOnDate(LocalDate date, TaskList tasks) {
        System.out.println(" HERE ARE YOUR SCHEDULED TASKS ON "
                + date.format(java.time.format.DateTimeFormatter.ofPattern("MMM d uuuu")) + ":");
        List<Integer> indices = tasks.indicesOn(date);
        for (int index : indices) {
            System.out.println(" " + (index + 1) + "." + tasks.get(index));
        }
        if (indices.isEmpty()) {
            System.out.println(" None--your calendar is gloriously clear!");
        }
    }
}
