package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.DATE_TIME_DISPLAY_FORMATTER;

import java.time.LocalDateTime;

/**
 * Represents a Appointment.
 */
public class Appointment {
    public static final String MESSAGE_CONSTRAINTS = "Appointment description must contain at least 1 alphabetic"
            + " character and has a limit of 70 characters.";
    public static final String VALIDATION_REGEX = "^(?!\\s*$)(?=.*[a-zA-Z]).{1,70}$";
    private final String description;
    private final String doctorInCharge;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param description the description of the appointment.
     * @param doctorInCharge the string of the doctor in charge.
     * @param startDate the start time in dd-MM-yyyy-HH-mm format.
     * @param endDate the end time in dd-MM-yyyy-HH-mm format.
     */
    public Appointment(String description, String doctorInCharge, LocalDateTime startDate, LocalDateTime endDate) {
        requireNonNull(description);
        requireNonNull(startDate);
        requireNonNull(endDate);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
        this.doctorInCharge = doctorInCharge;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDoctorInCharge() {
        return this.doctorInCharge;
    }

    public LocalDateTime getStart() {
        return this.startDate;
    }

    public LocalDateTime getEnd() {
        return this.endDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppt = (Appointment) other;
        return description.equals(otherAppt.description)
                && doctorInCharge.equals(otherAppt.doctorInCharge)
                && startDate.equals(otherAppt.startDate)
                && endDate.equals(otherAppt.endDate);
    }

    /**
     * Returns true if a given string is a valid appointment description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return description
                + " FROM "
                + startDate.format(DATE_TIME_DISPLAY_FORMATTER)
                + " TO "
                + endDate.format(DATE_TIME_DISPLAY_FORMATTER);
    }
}
