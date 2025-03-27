package seedu.address.logic;

public interface ICommandHistory {
    void add(String command);

    String getPrevious();

    String getNext();

    void clear();

    boolean isEmpty();

    boolean isFull();

    int size();

    boolean isPointerAtEnd();

    boolean isPointerAtStart();

    void setPointerToEnd();
}
