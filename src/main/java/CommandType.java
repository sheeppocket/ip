/**
 * Represents the commands understood by Yapper.
 *
 * <p>An enum is suitable because the command vocabulary is a small, fixed set.
 * Each constant stores the word users type, keeping command recognition in one
 * place instead of scattering string comparisons throughout the application.</p>
 */
public enum CommandType {
    LIST("list", false),
    HELP("help", false),
    MARK("mark", true),
    UNMARK("unmark", true),
    DELETE("delete", true),
    TODO("todo", true),
    DEADLINE("deadline", true),
    EVENT("event", true),
    BYE("bye", false),
    UNKNOWN("", false);

    private final String keyword;
    private final boolean acceptsArguments;

    CommandType(String keyword, boolean acceptsArguments) {
        this.keyword = keyword;
        this.acceptsArguments = acceptsArguments;
    }

    /**
     * Identifies the command word at the start of the user's input.
     *
     * @param input Trimmed user input.
     * @return Matching command, or {@link #UNKNOWN} if no command matches.
     */
    public static CommandType from(String input) {
        for (CommandType command : values()) {
            if (command != UNKNOWN && command.matches(input)) {
                return command;
            }
        }
        return UNKNOWN;
    }

    /** Returns whether the input begins with this complete command word. */
    private boolean matches(String input) {
        return input.equals(keyword) || acceptsArguments && input.startsWith(keyword + " ");
    }
}
