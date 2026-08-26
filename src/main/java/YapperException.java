/**
 * Represents an error caused by an invalid command entered by the user.
 */
public class YapperException extends Exception {
    /**
     * Creates an exception with a user-friendly explanation of the error.
     *
     * @param message Message to show to the user.
     */
    public YapperException(String message) {
        super(message);
    }
}
