package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.DATE_FORMATTER;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a Appointment.
 */
public class Appointment {
    public static final String MESSAGE_CONSTRAINTS = "Appointment description must contain at least 1 alphabetic"
            + " character and has a limit of 70 characters.";
    public static final String VALIDATION_REGEX = "^(?!\\s*$)(?=.*[a-zA-Z]).{1,70}$";
    private final String description;
    private final String doctorNric;
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param description the description of the appointment.
     * @param doctorNric the string of the doctor in charge.
     * @param startDate the start time in dd-MM-yyyy-HH-mm format.
     * @param endDate the end time in dd-MM-yyyy-HH-mm format.
     */
    public Appointment(String doctorNric, String description, LocalDate startDate, LocalDate endDate) {
        requireNonNull(description);
        requireNonNull(startDate);
        requireNonNull(endDate);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
        this.doctorNric = doctorNric;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDoctorNric() {
        return this.doctorNric;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
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
                && doctorNric.equals(otherAppt.doctorNric)
                && startDate.equals(otherAppt.startDate)
                && endDate.equals(otherAppt.endDate);
    }

    /**
     * Returns true if a given string is a valid appointment description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if both appointments have the same name and overlapping time.
     * This defines a weak notion of equality between two medicine usages.
     */
    public boolean hasOverlap(Appointment other) {
        if (other == this) {
            return true;
        }

        boolean hasNoTimeOverlap = other.getStartDate().isAfter(this.getEndDate())
                || other.getEndDate().isBefore(this.getStartDate());

        // Modify logic to check for same date overlap (same start and end date)
        return other.getDescription().equals(this.getDescription())
                && other.getDoctorNric().equals(this.getDoctorNric())
                && !hasNoTimeOverlap;
    }


    @Override
    public String toString() {
        return description
                + " FROM "
                + startDate.format(DATE_FORMATTER)
                + " TO "
                + endDate.format(DATE_FORMATTER);
    }

}
