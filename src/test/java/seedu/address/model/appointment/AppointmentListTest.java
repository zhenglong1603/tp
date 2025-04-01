package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;

public class AppointmentListTest {

    private AppointmentList appointmentList;
    private Appointment appointment1;
    private Appointment appointment2;
    private Appointment overlappingAppointment;

    @BeforeEach
    public void setUp() {
        appointmentList = new AppointmentList();

        // Define DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Create Appointment instances with LocalDateTime
        appointment1 = new Appointment("S1234567B",
                "Visit",
                LocalDateTime.parse("2025-03-20 10:00", formatter),
                LocalDateTime.parse("2025-03-20 11:00", formatter), "name 1");

        appointment2 = new Appointment("T7654321A",
                "Surgery",
                LocalDateTime.parse("2025-03-19 12:00", formatter),
                LocalDateTime.parse("2025-03-23 13:00", formatter), "name 2");

        overlappingAppointment = new Appointment("T1234567B",
                "Checkup",
                LocalDateTime.parse("2025-03-16 10:30", formatter),
                LocalDateTime.parse("2025-03-23 11:30", formatter), "name 3");
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
    public void testContainsOverlap() {
        appointmentList.add(appointment1);
        assertThrows(OverlappingAppointmentException.class, () -> appointmentList.add(appointment1));
    }

    @Test
    public void testSetAppointment() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        appointmentList.add(appointment1);
        Appointment newAppointment = new Appointment("T1234567B",
                "Updated Visit",
                LocalDateTime.parse("2025-03-20 14:00", formatter),
                LocalDateTime.parse("2025-03-20 15:00", formatter), "name 1");
        appointmentList.setAppointment(appointment1, newAppointment);

        assertEquals(1, appointmentList.size());
        assertTrue(appointmentList.contains(newAppointment));
        assertFalse(appointmentList.contains(appointment1));
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

