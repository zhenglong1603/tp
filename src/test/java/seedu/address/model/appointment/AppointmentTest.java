package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppointmentTest {

    private Appointment appointment;

    @BeforeEach
    public void setUp() {
        // Define DateTimeFormatter for the expected format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        // Create Appointment with LocalDateTime
        appointment = new Appointment("T1234567B",
                "Doctor Visit",
                LocalDateTime.parse("2025-03-20 12:00", formatter),
                LocalDateTime.parse("2025-03-20 12:30", formatter), "name 1");
    }

    @Test
    public void testAppointmentCreation() {
        assertNotNull(appointment);
        assertEquals("Doctor Visit", appointment.getDescription());

        assertEquals(LocalDateTime.parse("2025-03-20 12:00",
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), appointment.getStartDate());
        assertEquals(LocalDateTime.parse("2025-03-20 12:30",
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), appointment.getEndDate());
        assertEquals("T1234567B", appointment.getDoctorNric());
    }

    @Test
    public void testToString() {
        // Check the string representation of the Appointment
        String expectedString = "Doctor Visit FROM 20-03-2025 12:00 TO 20-03-2025 12:30";
        assertEquals(expectedString, appointment.toString());
    }
}

