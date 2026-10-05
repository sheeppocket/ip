/**
 * Interprets command words and validates their arguments before execution.
 */
public class Parser {
    /**
     * Identifies the command represented by trimmed user input.
     */
    public CommandType parseCommandType(String input) {
        return CommandType.from(input);
    }

    /**
     * Creates a todo from its command, rejecting an empty description.
     */
    public Todo parseTodo(String input) throws YapperException {
        return new Todo(getTaskDescription(input, "todo"));
    }

    /**
     * Extracts and validates a task description following a command word.
     */
    private String getTaskDescription(String input, String command) throws YapperException {
        String description = input.substring(command.length()).trim();
        if (description.isEmpty()) {
            throw new YapperException("The " + command + " description cannot be empty. "
                    + "Try: " + command + " <task description>");
        }
        return description;
    }

    /**
     * Parses and validates the task number supplied to a task-index command.
     */
    public int parseTaskIndex(String input, String command, int taskCount)
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

    /**
     * Parses a deadline command, including its required /by value.
     */
    public Deadline parseDeadline(String input) throws YapperException {
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

    /**
     * Parses an event command, including its required /from and /to values.
     */
    public Event parseEvent(String input) throws YapperException {
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

    /**
     * Parses the date argument of an on command.
     */
    public java.time.LocalDate parseDate(String input) throws YapperException {
        String dateText = input.substring("on".length()).trim();
        if (dateText.isEmpty()) {
            throw new YapperException("Please specify a date. Try: on <yyyy-MM-dd>");
        }
        java.time.LocalDate date;
        try {
            date = TaskDateTime.parseDate(dateText);
        } catch (IllegalArgumentException exception) {
            throw new YapperException("Please use yyyy-MM-dd or d/M/yyyy after 'on'. "
                    + "For example: on 2019-12-02");
        }

        return date;
    }
}
