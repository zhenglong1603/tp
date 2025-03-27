package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final String doctorNric;
    private final String appointmentDescription;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String patientNric;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    public JsonAdaptedAppointment(@JsonProperty("doctorNRIC") String doctorNric,
                                  @JsonProperty("appointmentDescription") String appointmentDescription,
                                  @JsonProperty("startDate") LocalDate startDate,
                                  @JsonProperty("endDate") LocalDate endDate,
                                  @JsonProperty("patientNric") String patientNric) {
        this.doctorNric = doctorNric;
        this.appointmentDescription = appointmentDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.patientNric = patientNric;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        doctorNric = source.getDoctorNric();
        appointmentDescription = source.getDescription();
        startDate = source.getStartDate();
        endDate = source.getEndDate();
        patientNric = source.getPatientNric();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException, DateTimeParseException {
        if (doctorNric == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "doctor nric"));
        }

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

        return new Appointment(doctorNric, appointmentDescription, startDate, endDate, patientNric);
    }
}

