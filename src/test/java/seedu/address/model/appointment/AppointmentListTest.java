package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;

public class AppointmentListTest {

    private AppointmentList appointmentList;
    private Appointment appointment1;
    private Appointment appointment2;
    private Appointment overlappingAppointment;

    @BeforeEach
    public void setUp() {
        appointmentList = new AppointmentList();

        // Define DateTimeFormatter
        DateTimeFormatter formatter = DATE_TIME_FORMATTER;

        // Create Appointment instances with LocalDateTime
        appointment1 = new Appointment(
                "Visit",
                LocalDateTime.parse("20-03-2025 10:00", formatter),
                LocalDateTime.parse("20-03-2025 11:00", formatter), "name 1");

        appointment2 = new Appointment(
                "Surgery",
                LocalDateTime.parse("19-03-2025 12:00", formatter),
                LocalDateTime.parse("23-03-2025 13:00", formatter), "name 2");

        overlappingAppointment = new Appointment(
                "Checkup",
                LocalDateTime.parse("16-03-2025 10:30", formatter),
                LocalDateTime.parse("23-03-2025 11:30", formatter), "name 3");
    }

    @Test
    public void testAddAppointment() {
        appointmentList.add(appointment1);
        appointmentList.add(appointment2);

        assertEquals(2, appointmentList.size());
        assertTrue(appointmentList.contains(appointment1));
        assertTrue(appointmentList.contains(appointment2));
    }

    @Test
    public void testRemoveAppointment() {
        appointmentList.add(appointment1);
        appointmentList.remove(appointment1);

        assertEquals(0, appointmentList.size());
        assertFalse(appointmentList.contains(appointment1));
    }

    @Test
    public void testRemoveNonExistingAppointment() {
        assertThrows(AppointmentNotFoundException.class, () -> appointmentList.remove(appointment1));
    }

    @Test
    public void testToString() {
        appointmentList.add(appointment1);
        appointmentList.add(appointment2);

        String expectedString = "Visit FROM 20-03-2025 10:00 TO 20-03-2025 11:00\n"
            + "Surgery FROM 19-03-2025 12:00 TO 23-03-2025 13:00\n";
        assertEquals(expectedString, appointmentList.toString());
    }
}

