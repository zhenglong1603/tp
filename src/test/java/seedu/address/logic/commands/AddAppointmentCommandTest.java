package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


class AddAppointmentCommandTest {
    private Model model;

    @BeforeEach
    void setUp() {
        model = new ModelManager();
        Person bob = new PersonBuilder(BOB).withName(VALID_NAME_BOB).withNric("S9999999B").build();
        Person amy = new PersonBuilder(AMY).withName(VALID_NAME_AMY).withNric("S9856324A").build();

        model.addPerson(bob);
        model.addPerson(amy);
    }

    @AfterEach
    void tearDown() {
        Person bob = new PersonBuilder(BOB).withName(VALID_NAME_BOB).withNric("S9999999B").build();
        Person amy = new PersonBuilder(AMY).withName(VALID_NAME_AMY).withNric("S9856324A").build();
        model.clearAppointments(bob);
        model.clearAppointments(amy);
    }

    @Test
    void execute_successfulAddition() throws CommandException {
        Nric nric = new Nric("S9856324A");
        Appointment appointment = new Appointment(
                "Check-Up",
                LocalDateTime.parse("22-02-2025 12:00", DATE_TIME_FORMATTER),
                LocalDateTime.parse("22-02-2025 12:30", DATE_TIME_FORMATTER),
                nric.toString()
        );

        AddAppointmentCommand command = new AddAppointmentCommand(nric, appointment);
        CommandResult result = command.execute(model);

        assertEquals(String.format(AddAppointmentCommand.MESSAGE_SUCCESS, nric), result.getFeedbackToUser());
        assertTrue(model.findPersonByNric(nric).getAppointmentList().contains(appointment));
    }

    @Test
    void execute_personNotFound_throwsCommandException() {
        Nric invalidNric = new Nric("S9999999Z");
        Appointment appointment = new Appointment(
                "Check-Up",
                LocalDateTime.parse("22-02-2025 12:00", DATE_TIME_FORMATTER),
                LocalDateTime.parse("22-02-2025 12:30", DATE_TIME_FORMATTER),
                invalidNric.toString()
        );

        AddAppointmentCommand command = new AddAppointmentCommand(invalidNric, appointment);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    void execute_overlappingAppointment_throwsOverlappingAppointmentException() {
        Nric nric = new Nric("S9856324A");
        Appointment existingAppointment = new Appointment(
                "Check-Up",
                LocalDateTime.parse("22-02-2025 10:30", DATE_TIME_FORMATTER),
                LocalDateTime.parse("22-02-2025 11:00", DATE_TIME_FORMATTER),
                nric.toString()
        );

        model.addAppointment(model.findPersonByNric(nric), existingAppointment);

        Appointment overlappingAppointment = new Appointment(
                "Check-Up",
                LocalDateTime.parse("22-02-2025 10:45", DATE_TIME_FORMATTER), // Overlaps with existing
                LocalDateTime.parse("22-02-2025 11:15", DATE_TIME_FORMATTER),
                nric.toString()
        );

        AddAppointmentCommand command = new AddAppointmentCommand(nric, overlappingAppointment);

        assertThrows(OverlappingAppointmentException.class, () -> command.execute(model));
    }
}
