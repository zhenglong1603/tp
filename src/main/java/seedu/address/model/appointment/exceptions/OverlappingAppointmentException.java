package seedu.address.model.appointment.exceptions;

/**
 * Signals that the operation will result in overlapping Appointments.
 * Appointments are considered overlapping if they have the same name
 * and overlapping start and end dates.
 */
public class OverlappingAppointmentException extends RuntimeException {
    public OverlappingAppointmentException(String message) {
        super(message);
    }
}
