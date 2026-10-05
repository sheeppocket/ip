package yapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Owns the task collection and operations on tasks, independently of input and storage. */
public class TaskList {
    private final List<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this(List.of());
    }

    /** Copies loaded tasks so callers cannot add or remove entries through the original list. */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /** Adds a task to the end of the list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Removes and returns the task at a validated zero-based index. */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /** Marks a task at a validated zero-based index as completed. */
    public Task mark(int index) {
        Task task = get(index);
        task.markAsDone();
        return task;
    }

    /** Marks a task at a validated zero-based index as incomplete. */
    public Task unmark(int index) {
        Task task = get(index);
        task.markAsNotDone();
        return task;
    }

    /** Returns a task at a validated zero-based index. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /** Returns the number of tasks. */
    public int size() {
        return tasks.size();
    }

    /** Returns a structurally immutable snapshot for display or saving; task objects are shared. */
    public List<Task> asList() {
        return List.copyOf(tasks);
    }

    /** Finds zero-based indices of scheduled tasks, preserving their original list numbers. */
    public List<Integer> indicesOn(LocalDate date) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).occursOn(date)) {
                indices.add(i);
            }
        }
        return indices;
    }
}
