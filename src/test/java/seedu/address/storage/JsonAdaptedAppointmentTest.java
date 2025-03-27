package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

public class JsonAdaptedAppointmentTest {

    private static final String VALID_DOCTOR_NRIC = "S1234567A";
    private static final String VALID_APPOINTMENT_DESCRIPTION = "Dental Checkup";
    private static final LocalDate VALID_START_DATE = LocalDate.of(2020, 1, 1);
    private static final LocalDate VALID_END_DATE = LocalDate.of(2020, 1, 10);
    private static final String VALID_PATIENT_NRIC = "S1234567B";

    /**
     * Ensures that the JsonAdaptedAppointment correctly converts a valid Appointment object
     * into a JsonAdaptedAppointment
     */
    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        Appointment expectedAppointment = new Appointment(VALID_DOCTOR_NRIC, VALID_APPOINTMENT_DESCRIPTION,
                VALID_START_DATE, VALID_END_DATE, VALID_PATIENT_NRIC);
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_DOCTOR_NRIC,
                VALID_APPOINTMENT_DESCRIPTION,
                VALID_START_DATE, VALID_END_DATE, VALID_PATIENT_NRIC);

        assertEquals(expectedAppointment, appointment.toModelType());
    }

    @Test
    public void toModelType_nullDoctorNric_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(null, VALID_APPOINTMENT_DESCRIPTION,
                VALID_START_DATE, VALID_END_DATE, VALID_PATIENT_NRIC);
        String expectedMessage = String.format(JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT,
                "doctor nric");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullAppointmentDescription_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_DOCTOR_NRIC, null,
                VALID_START_DATE, VALID_END_DATE, VALID_PATIENT_NRIC);
        String expectedMessage = String.format(JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT,
                "appointment description");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_DOCTOR_NRIC,
                VALID_APPOINTMENT_DESCRIPTION,
                null, VALID_END_DATE, VALID_PATIENT_NRIC);
        String expectedMessage = String.format(JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT,
                "start date");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_DOCTOR_NRIC,
                VALID_APPOINTMENT_DESCRIPTION,
                VALID_START_DATE, null, VALID_PATIENT_NRIC);
        String expectedMessage = String.format(JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT,
                "end date");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullPatientNric_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_DOCTOR_NRIC,
                VALID_APPOINTMENT_DESCRIPTION,
                VALID_START_DATE, VALID_END_DATE, null);
        String expectedMessage = String.format(JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT,
                "patient nric");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }
}
