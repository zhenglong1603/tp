package seedu.address.model.appointment.exceptions;

/**
 * Signals that the list of appointments is empty.
 */
public class EmptyListException extends Exception {
    public EmptyListException() {
        super("The list of appointments are empty.");
    }
}
