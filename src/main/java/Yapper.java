import java.io.IOException;
import java.nio.file.Path;

/**
 * Coordinates command parsing, task operations, persistence, and the console UI.
 */
public class Yapper {
    /** Default save file relative to the working directory. */
    private static final Path STORAGE_PATH = Path.of("data", "yapper.txt");

    /** Console input and output handler. */
    private final Ui ui;
    /** Command and argument parser. */
    private final Parser parser;
    /** Persistence handler for the configured save file. */
    private final Storage storage;
    /** Save file path included in startup error messages. */
    private final Path storagePath;
    /** Working task list, initialized after a successful load. */
    private TaskList tasks;

    /**
     * Creates an application that stores tasks at the supplied path.
     *
     * @param storagePath File used to load and save tasks.
     */
    public Yapper(Path storagePath) {
        this.storagePath = storagePath;
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(storagePath);
    }

    /**
     * Loads saved tasks and processes commands until bye or the end of input.
     */
    public void run() {
        ui.showWelcome();
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException | YapperException exception) {
            ui.showLoadingError(exception.getMessage(), storagePath);
            return;
        }

        while (ui.hasNextCommand()) {
            String command = ui.readCommand();
            CommandType commandType = parser.parseCommandType(command);
            if (commandType == CommandType.BYE) {
                break;
            }
            try {
                execute(commandType, command);
            } catch (YapperException exception) {
                ui.showError(exception.getMessage());
            } catch (IOException exception) {
                ui.showSavingError(exception.getMessage());
            }
            ui.showSeparator();
        }
        ui.showGoodbye();
    }

    /**
     * Dispatches one command, saving changes before displaying their confirmation.
     *
     * @param commandType Recognized command type.
     * @param command Trimmed command line including its arguments.
     * @throws YapperException If the command or its arguments are invalid.
     * @throws IOException If saving fails; the in-memory change is not rolled back.
     * @throws AssertionError If BYE reaches dispatch instead of being handled by the input loop.
     */
    private void execute(CommandType commandType, String command) throws YapperException, IOException {
        switch (commandType) {
        case LIST -> ui.printTaskList(tasks.asList());
        case HELP -> ui.printHelp();
        case MARK -> {
            Task task = tasks.mark(parser.parseTaskIndex(command, "mark", tasks.size()));
            storage.save(tasks.asList());
            ui.showTaskStatus(task, true);
        }
        case UNMARK -> {
            Task task = tasks.unmark(parser.parseTaskIndex(command, "unmark", tasks.size()));
            storage.save(tasks.asList());
            ui.showTaskStatus(task, false);
        }
        case DELETE -> {
            Task task = tasks.delete(parser.parseTaskIndex(command, "delete", tasks.size()));
            storage.save(tasks.asList());
            ui.printTaskDeleted(task, tasks.size());
        }
        case TODO -> addTask(parser.parseTodo(command));
        case DEADLINE -> addTask(parser.parseDeadline(command));
        case EVENT -> addTask(parser.parseEvent(command));
        case ON -> ui.printTasksOnDate(parser.parseDate(command), tasks);
        case UNKNOWN -> throw new YapperException("Please precede a task with a command to add it. "
                + "Otherwise, type 'help' for a list of commands.");
        case BYE -> throw new AssertionError("The bye command should be handled before dispatch.");
        }
    }

    /**
     * Adds and persists a parsed task before showing its confirmation.
     *
     * @param task Parsed task to append.
     * @throws IOException If saving fails; the task remains in memory.
     */
    private void addTask(Task task) throws IOException {
        tasks.add(task);
        storage.save(tasks.asList());
        ui.printTaskAdded(task, tasks.size());
    }

    /**
     * Starts Yapper using its default relative storage path.
     *
     * @param args Command-line arguments; not used.
     */
    public static void main(String[] args) {
        new Yapper(STORAGE_PATH).run();
    }
}
