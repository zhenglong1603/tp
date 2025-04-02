package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;

import java.time.LocalDateTime;

/**
 * Represents a Appointment.
 */
public class Appointment {
    public static final String DESCRIPTION_MESSAGE_CONSTRAINTS =
            "Appointment description must contain at least 1 alphabetic"
            + " character and has a limit of 40 characters.";
    public static final String DOCTOR_NRIC_MESSAGE_CONSTRAINTS =
            "Doctor NRIC should be a valid format starting with a letter, followed by 7 digits, "
                    + "and ending with a letter.";
    public static final String DESCRIPTION_VALIDATION_REGEX = "^(?!\\s*$)(?=.*[a-zA-Z]).{1,40}$";
    public static final String DOCTOR_NRIC_VALIDATION_REGEX = "^[A-Z][0-9]{7}[A-Z]$";
    private final String description;
    private final String doctorNric;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String patientNric;
    private final Boolean visited;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param description the description of the appointment.
     * @param doctorNric the string of the doctor in charge.
     * @param startDate the start time in dd-MM-yyyy-HH-mm format.
     * @param endDate the end time in dd-MM-yyyy-HH-mm format.
     * @param patientNric the string of the patient.
     */
    public Appointment(
            String doctorNric, String description, LocalDateTime startDate, LocalDateTime endDate,
            String patientNric, Boolean visited) {
        requireNonNull(description);
        requireNonNull(startDate);
        requireNonNull(endDate);
        checkArgument(isValidDoctorNric(doctorNric), DOCTOR_NRIC_MESSAGE_CONSTRAINTS);
        checkArgument(isValidDescription(description), DESCRIPTION_MESSAGE_CONSTRAINTS);
        this.description = description;
        this.doctorNric = doctorNric;
        this.startDate = startDate;
        this.endDate = endDate;
        this.patientNric = patientNric;
        this.visited = visited;
    }

    /**
     * Constructs an {@code Appointment}.
     *
     * @param description the description of the appointment.
     * @param doctorNric the string of the doctor in charge.
     * @param startDate the start time in dd-MM-yyyy HH:mm format.
     * @param endDate the end time in dd-MM-yyyy HH:mm format.
     * @param patientNric the string of the patient.
     */
    public Appointment(
            String doctorNric, String description, LocalDateTime startDate, LocalDateTime endDate,
            String patientNric) {
        requireNonNull(description);
        requireNonNull(startDate);
        requireNonNull(endDate);
        checkArgument(isValidDoctorNric(doctorNric), DOCTOR_NRIC_MESSAGE_CONSTRAINTS);
        checkArgument(isValidDescription(description), DESCRIPTION_MESSAGE_CONSTRAINTS);
        this.description = description;
        this.doctorNric = doctorNric;
        this.startDate = startDate;
        this.endDate = endDate;
        this.patientNric = patientNric;
        this.visited = false;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDoctorNric() {
        return this.doctorNric;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
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
                && doctorNric.equals(otherAppt.doctorNric)
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
     * Returns true if a given string is a valid doctor NRIC.
     */
    public static boolean isValidDoctorNric(String test) {
        return test.matches(DOCTOR_NRIC_VALIDATION_REGEX);
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
                + startDate.format(DATE_TIME_FORMATTER)
                + " TO "
                + endDate.format(DATE_TIME_FORMATTER);
    }

    public boolean getVisited() {
        return visited;
    }

}
