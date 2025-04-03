package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentList;
import seedu.address.model.person.Address;
import seedu.address.model.person.BirthDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.MedicalReport;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

class ClearAppointmentsCommandTest {
    private ModelManager model;
    private Person person;
    private Nric nric;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        model = new ModelManager();
        nric = new Nric("S1234567A");
        appointment = new Appointment("Checkup",
                LocalDateTime.of(2025, 10, 1, 10, 0),
                LocalDateTime.of(2025, 10, 1, 11, 0),
                "S1234567A");

        AppointmentList appointments = new AppointmentList();
        appointments.add(appointment);

        person = new Person(
                new Name("John Doe"),
                new Phone("12345678"),
                new Email("john@example.com"),
                nric,
                new BirthDate("01-01-1990"),
                new Address("123 Street"),
                new HashSet<>(),
                new MedicalReport("None", "None", "None", "None"),
                appointments
        );
        model.addPerson(person);
    }

    @Test
    void execute_nricWithOneAppointment_success() {
        ClearAppointmentsCommand command = new ClearAppointmentsCommand(nric);
        CommandResult result = assertDoesNotThrow(() -> command.execute(model));
        assertEquals(String.format(ClearAppointmentsCommand.MESSAGE_SUCCESS_APPOINTMENT_NRIC, nric),
                result.getFeedbackToUser());
    }

    @Test
    void execute_nricWithNoAppointments_showsMessage() {
        model.clearAppointments(person);
        ClearAppointmentsCommand command = new ClearAppointmentsCommand(nric);
        CommandResult result = assertDoesNotThrow(() -> command.execute(model));
        assertEquals(ClearAppointmentsCommand.MESSAGE_NO_APPOINTMENT, result.getFeedbackToUser());
    }

    @Test
    void execute_nricWithMultipleAppointments_success() {
        Appointment another = new Appointment("Dental",
                LocalDateTime.of(2025, 10, 2, 14, 0),
                LocalDateTime.of(2025, 10, 2, 15, 0),
                "S1234567A");
        person.addAppointment(another);
        ClearAppointmentsCommand command = new ClearAppointmentsCommand(nric);
        CommandResult result = assertDoesNotThrow(() -> command.execute(model));
        assertEquals(String.format(ClearAppointmentsCommand.MESSAGE_SUCCESS_APPOINTMENTS_NRIC, nric),
                result.getFeedbackToUser());
    }

    @Test
    void execute_nricNotFound_throwsCommandException() {
        ClearAppointmentsCommand command = new ClearAppointmentsCommand(new Nric("S9999999Z"));
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    void execute_indexWithOneAppointment_success() {
        ClearAppointmentsCommand command = new ClearAppointmentsCommand(Index.fromOneBased(1));
        CommandResult result = assertDoesNotThrow(() -> command.execute(model));
        assertEquals(String.format(ClearAppointmentsCommand.MESSAGE_SUCCESS_APPOINTMENT_ID, 1),
                result.getFeedbackToUser());
    }

    @Test
    void execute_indexWithMultipleAppointments_success() {
        Appointment another = new Appointment("Dental",
                LocalDateTime.of(2025, 10, 2, 14, 0),
                LocalDateTime.of(2025, 10, 2, 15, 0),
                "S1234567A");
        person.addAppointment(another);
        ClearAppointmentsCommand command = new ClearAppointmentsCommand(Index.fromOneBased(1));
        CommandResult result = assertDoesNotThrow(() -> command.execute(model));
        assertEquals(String.format(ClearAppointmentsCommand.MESSAGE_SUCCESS_APPOINTMENTS_ID, 1),
                result.getFeedbackToUser());
    }

    @Test
    void execute_indexWithNoAppointments_showsMessage() {
        model.clearAppointments(person);
        ClearAppointmentsCommand command = new ClearAppointmentsCommand(Index.fromOneBased(1));
        CommandResult result = assertDoesNotThrow(() -> command.execute(model));
        assertEquals(ClearAppointmentsCommand.MESSAGE_NO_APPOINTMENT, result.getFeedbackToUser());
    }

    @Test
    void execute_indexOutOfBounds_throwsCommandException() {
        ClearAppointmentsCommand command = new ClearAppointmentsCommand(Index.fromOneBased(5));
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    void equals_sameObject_returnsTrue() {
        ClearAppointmentsCommand command = new ClearAppointmentsCommand(nric);
        assertTrue(command.equals(command));
    }

    @Test
    void equals_sameNric_returnsTrue() {
        ClearAppointmentsCommand command1 = new ClearAppointmentsCommand(nric);
        ClearAppointmentsCommand command2 = new ClearAppointmentsCommand(nric);
        assertTrue(command1.equals(command2));
    }

    @Test
    void equals_differentNric_returnsFalse() {
        ClearAppointmentsCommand command1 = new ClearAppointmentsCommand(nric);
        ClearAppointmentsCommand command2 = new ClearAppointmentsCommand(new Nric("S7654321B"));
        assertFalse(command1.equals(command2));
    }

    @Test
    void equals_differentMode_returnsFalse() {
        ClearAppointmentsCommand command1 = new ClearAppointmentsCommand(nric);
        ClearAppointmentsCommand command2 = new ClearAppointmentsCommand(Index.fromOneBased(1));
        assertFalse(command1.equals(command2));
    }
}
