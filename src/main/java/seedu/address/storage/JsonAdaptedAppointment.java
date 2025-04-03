package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final String appointmentDescription;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String patientNric;
    private final Boolean visited;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    public JsonAdaptedAppointment(@JsonProperty("appointmentDescription") String appointmentDescription,
                                  @JsonProperty("startDate") LocalDateTime startDate,
                                  @JsonProperty("endDate") LocalDateTime endDate,
                                  @JsonProperty("patientNric") String patientNric,
                                  @JsonProperty("visited") Boolean visited) {
        this.appointmentDescription = appointmentDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.patientNric = patientNric;
        this.visited = visited;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        appointmentDescription = source.getDescription();
        startDate = source.getStartDateTime();
        endDate = source.getEndDateTime();
        patientNric = source.getPatientNric();
        visited = source.getVisited();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException, DateTimeParseException {
        if (appointmentDescription == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "appointment description"));
        }

        if (startDate == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "start date"));
        }

        if (endDate == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "end date"));
        }

        if (patientNric == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "patient nric"));
        }

        if (visited == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "visited"));
        }

        return new Appointment(appointmentDescription, startDate, endDate, patientNric, visited);
    }
}

