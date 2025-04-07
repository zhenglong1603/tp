package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

// The test cases are adapted from a conversation with chatGPT
public class JsonAdaptedAppointmentTest {

    private static final String VALID_APPOINTMENT_DESCRIPTION = "Dental Checkup";
    private static final LocalDateTime VALID_START_DATE = LocalDateTime.of(2020, 1, 1, 11, 0);
    private static final LocalDateTime VALID_END_DATE = LocalDateTime.of(2020, 1, 10, 11, 30);
    private static final String VALID_PATIENT_NRIC = "S1234567B";

    /**
     * Ensures that the JsonAdaptedAppointment correctly converts a valid Appointment object
     * into a JsonAdaptedAppointment
     */
    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        Appointment expectedAppointment = new Appointment(VALID_APPOINTMENT_DESCRIPTION,
                VALID_START_DATE, VALID_END_DATE, VALID_PATIENT_NRIC);
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_DESCRIPTION,
                VALID_START_DATE, VALID_END_DATE, VALID_PATIENT_NRIC, false);

        assertEquals(expectedAppointment, appointment.toModelType());
    }

    @Test
    public void toModelType_nullAppointmentDescription_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(null,
                VALID_START_DATE, VALID_END_DATE, VALID_PATIENT_NRIC, false);
        String expectedMessage = String.format(JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT,
                "appointment description");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_DESCRIPTION,
                null, VALID_END_DATE, VALID_PATIENT_NRIC, false);
        String expectedMessage = String.format(JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT,
                "start date");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_DESCRIPTION,
                VALID_START_DATE, null, VALID_PATIENT_NRIC, false);
        String expectedMessage = String.format(JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT,
                "end date");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullPatientNric_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                VALID_APPOINTMENT_DESCRIPTION,
                VALID_START_DATE, VALID_END_DATE, null, false);
        String expectedMessage = String.format(JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT,
                "patient nric");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }
}
