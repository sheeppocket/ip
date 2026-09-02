import java.time.LocalDateTime;

/**
 * Represents a task that takes place between specified start and end times.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates an incomplete event.
     *
     * @param description Description of the event.
     * @param from Start date and time of the event.
     * @param to End date and time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toDataString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description
                + " | " + TaskDateTime.formatForStorage(from)
                + " | " + TaskDateTime.formatForStorage(to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + TaskDateTime.formatForDisplay(from)
                + " to: " + TaskDateTime.formatForDisplay(to) + ")";
    }
}
