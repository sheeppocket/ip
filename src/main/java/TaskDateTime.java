import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Parses task dates from supported user formats and formats them for display and storage.
 */
public final class TaskDateTime {
    private static final List<DateTimeFormatter> DATE_TIME_INPUT_FORMATS = List.of(
            DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("d/M/uuuu HHmm"),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    private static final List<DateTimeFormatter> DATE_INPUT_FORMATS = List.of(
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ofPattern("d/M/uuuu"));
    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("MMM d uuuu");
    private static final DateTimeFormatter DISPLAY_DATE_TIME =
            DateTimeFormatter.ofPattern("MMM d uuuu, h:mma");

    private TaskDateTime() {
    }

    /**
     * Parses a date, optionally followed by a four-digit 24-hour time.
     * Date-only values are represented as midnight on that date.
     *
     * @param value Date text supplied by the user or storage file.
     * @return Parsed date and time.
     * @throws IllegalArgumentException If the value does not match a supported format.
     */
    public static LocalDateTime parse(String value) {
        for (DateTimeFormatter formatter : DATE_TIME_INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
                // Try the next supported format.
            }
        }
        for (DateTimeFormatter formatter : DATE_INPUT_FORMATS) {
            try {
                return LocalDate.parse(value, formatter).atStartOfDay();
            } catch (DateTimeParseException ignored) {
                // Try the next supported format.
            }
        }
        throw new IllegalArgumentException("invalid date or time");
    }

    /** Returns a friendly representation suitable for chatbot output. */
    public static String formatForDisplay(LocalDateTime dateTime) {
        if (dateTime.toLocalTime().equals(java.time.LocalTime.MIDNIGHT)) {
            return dateTime.format(DISPLAY_DATE);
        }
        return dateTime.format(DISPLAY_DATE_TIME);
    }

    /** Returns the stable ISO representation used in the save file. */
    public static String formatForStorage(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
