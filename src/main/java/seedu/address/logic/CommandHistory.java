package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the history of commands entered by the user.
 */
public class CommandHistory implements ICommandHistory {
    private static final int MAX_SIZE = 100;
    private final List<String> history;
    private int pointer;

    /**
     * Creates a CommandHistory object with the given history.
     */
    public CommandHistory(List<String> history) {
        this.history = history;
        this.pointer = history.size();
    }

    /**
     * Creates a CommandHistory object with an empty history.
     */
    public CommandHistory() {
        this.history = new ArrayList<>();
        pointer = 0;
    }

    @Override
    public void add(String command) {
        if (this.isFull()) {
            history.remove(0);
        }
        history.add(command);
        setPointerToEnd();
    }

    @Override
    public String getPrevious() {
        if (this.isEmpty()) {
            return "";
        }
        if (isPointerAtStart()) {
            return history.get(0);
        }
        pointer--;
        return history.get(pointer);
    }

    @Override
    public String getNext() {
        if (this.isEmpty() || isPointerAtEnd()) {
            return "";
        }
        pointer++;
        if (isPointerAtEnd()) {
            return "";
        }
        return history.get(pointer);
    }

    @Override
    public void clear() {
        history.clear();
        pointer = 0;
    }

    @Override
    public boolean isEmpty() {
        return history.isEmpty();
    }

    public boolean isFull() {
        return history.size() == MAX_SIZE;
    }

    @Override
    public int size() {
        return history.size();
    }

    @Override
    public boolean isPointerAtStart() {
        return pointer == 0;
    }

    @Override
    public boolean isPointerAtEnd() {
        return pointer == history.size();
    }

    @Override
    public void setPointerToEnd() {
        pointer = history.size();
    }
}
