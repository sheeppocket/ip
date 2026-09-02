import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a task that must be completed by a specified date or time.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Creates an incomplete deadline.
     *
     * @param description Description of the task.
     * @param by Date and time by which the task must be completed.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toDataString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description
                + " | " + TaskDateTime.formatForStorage(by);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return by.toLocalDate().equals(date);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + TaskDateTime.formatForDisplay(by) + ")";
    }
}
