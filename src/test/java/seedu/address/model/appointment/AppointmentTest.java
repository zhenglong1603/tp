package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppointmentTest {

    private Appointment appointment;

    @BeforeEach
    public void setUp() {
        // Define DateTimeFormatter for the expected format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Create Appointment with LocalDateTime
        appointment = new Appointment("T1234567B",
                "Doctor Visit",
                LocalDate.parse("2025-03-20", formatter),
                LocalDate.parse("2025-03-20", formatter));
    }

    @Test
    public void testAppointmentCreation() {
        assertNotNull(appointment);
        assertEquals("Doctor Visit", appointment.getDescription());

        // Check if LocalDateTime is correctly set for start and end date
        assertEquals(LocalDate.parse("2025-03-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")), appointment.getStartDate());
        assertEquals(LocalDate.parse("2025-03-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")), appointment.getEndDate());
        assertEquals("T1234567B", appointment.getDoctorNric());
    }

    @Test
    public void testToString() {
        // Check the string representation of the Appointment
        String expectedString = "Doctor Visit FROM 20-03-2025 TO 20-03-2025";
        assertEquals(expectedString, appointment.toString());
    }
}

