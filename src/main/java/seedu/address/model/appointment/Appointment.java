package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a Appointment.
 */
public class Appointment {
    public static final String DESCRIPTION_MESSAGE_CONSTRAINTS =
            "Appointment description must contain at least 1 alphabetic"
            + " character and has a limit of 40 characters.";

    public static final String DESCRIPTION_VALIDATION_REGEX = "^(?!\\s*$)(?=.*[a-zA-Z]).{1,40}$";
    private final String description;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String patientNric;
    private final Boolean visited;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param description the description of the appointment.
     * @param startDate the start time in dd-MM-yyyy-HH-mm format.
     * @param endDate the end time in dd-MM-yyyy-HH-mm format.
     * @param patientNric the string of the patient.
     */
    public Appointment(
            String description, LocalDateTime startDate, LocalDateTime endDate,
            String patientNric, Boolean visited) {
        requireNonNull(description);
        requireNonNull(startDate);
        requireNonNull(endDate);
        checkArgument(isValidDescription(description), DESCRIPTION_MESSAGE_CONSTRAINTS);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.patientNric = patientNric;
        this.visited = visited;
    }

    /**
     * Constructs an {@code Appointment}.
     *
     * @param description the description of the appointment.
     * @param startDate the start time in dd-MM-yyyy HH:mm format.
     * @param endDate the end time in dd-MM-yyyy HH:mm format.
     * @param patientNric the string of the patient.
     */
    public Appointment(
            String description, LocalDateTime startDate, LocalDateTime endDate,
            String patientNric) {
        requireNonNull(description);
        requireNonNull(startDate);
        requireNonNull(endDate);
        checkArgument(isValidDescription(description), DESCRIPTION_MESSAGE_CONSTRAINTS);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.patientNric = patientNric;
        this.visited = false;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getStartDateTime() {
        return this.startDate;
    }

    public LocalDateTime getEndDateTime() {
        return this.endDate;
    }

    public LocalDate getStartDate() {
        return this.startDate.toLocalDate();
    }

    public String getPatientNric() {
        return this.patientNric;
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
                && startDate.equals(otherAppt.startDate)
                && endDate.equals(otherAppt.endDate)
                && patientNric.equals(otherAppt.patientNric);
    }

    /**
     * Returns true if a given string is a valid appointment description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    /**
     * Returns true if both appointments have the same name and overlapping time.
     * This defines a weak notion of equality between two medicine usages.
     */
    public boolean hasOverlap(Appointment other) {
        if (other == this) {
            return true;
        }

        boolean hasNoTimeOverlap = other.getStartDateTime().isAfter(this.getEndDateTime())
                || other.getEndDateTime().isBefore(this.getStartDateTime());

        // Modify logic to check for same date overlap (same start and end date)
        return other.getDescription().equals(this.getDescription())
                && !hasNoTimeOverlap;
    }


    @Override
    public String toString() {
        return description
                + " FROM "
                + startDate.format(DATE_TIME_FORMATTER)
                + " TO "
                + endDate.format(DATE_TIME_FORMATTER);
    }

    public boolean getVisited() {
        return visited;
    }

}
