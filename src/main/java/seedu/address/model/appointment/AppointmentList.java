package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;

/**
 * A list of appointments that enforces uniqueness between its elements and does not allow nulls.
 * An appointment is considered unique by comparing using {@code Appointment#hasOverlap(Appointment)}.
 * As such, adding and updating of appointments uses Appointment#hasOverlap(Appointment) for equality
 * to ensure that the appointment being added or updated is unique in terms of identity in the
 * AppointmentList. However, the removal of an appointment uses Appointment#equals(Object) to ensure that the
 * appointment with exactly the same fields will be removed.
 * Supports a minimal set of list operations.
 *
 * @see Appointment#hasOverlap(Appointment)
 */
public class AppointmentList implements Iterable<Appointment> {
    public static final AppointmentList EMPTY_APPOINTMENT_LIST =
            new AppointmentList();
    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    /**
     * Adds an appointment to the list.
     * The appointment must not overlap with existing appointment (with the same name) in the list.
     */
    public void add(Appointment toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the appointment {@code target} in the list with {@code newAppointment}.
     * To be used in the future if needed
     * {@code target} must exist in the list.
     * The appointment identity of {@code editedMedicineUsage} must not be the same as another existing
     * appointment in the list.
     */
    public void setAppointment(AppointmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setAppointment(List<Appointment> replacement) {
        requireAllNonNull(replacement);
        internalList.setAll(replacement);
    }

    /**
     * Removes the equivalent appointment from the list.
     * The appointment must exist in the list.
     */
    public void remove(Appointment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AppointmentNotFoundException();
        }
    }

    public void reset() {
        internalList.clear();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Appointment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentList)) {
            return false;
        }

        AppointmentList otherAppointmentList = (AppointmentList) other;
        return internalList.equals(otherAppointmentList.internalList);
    }

    /**
     * Returns the number of appointments in the list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Returns true if the list contains the specified appointment.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Appointment appointment : internalList) {
            sb.append(appointment.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the list.
     * The appointment identity of {@code editedAppointment} must not be the same as another existing
     * appointment in the list.
     */
    public void replaceAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }

        if (!target.hasOverlap(editedAppointment) && containsOverlap(editedAppointment)) {
            throw new OverlappingAppointmentException();
        }

        internalList.set(index, editedAppointment);
    }

}
