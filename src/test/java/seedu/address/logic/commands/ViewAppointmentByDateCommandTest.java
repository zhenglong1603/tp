package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ViewAppointmentByDateCommandTest {
    private Model model;
    private Person bob;

    public Person getBob() {
        return bob;
    }

    @BeforeEach
    void setUp() {
        model = new ModelManager();
        bob = new PersonBuilder(BOB).withName(VALID_NAME_BOB).withNric("S9999999B").build();

        model.addPerson(bob);
    }

    @AfterEach
    void tearDown() {
        bob = new PersonBuilder(BOB).withName(VALID_NAME_BOB).withNric("S9999999B").build();
        model.clearAppointments(bob);
    }

    @Test
    public void execute_validDate_success() {
        Nric nric = new Nric("S9856324A");
        Appointment appointment = new Appointment(
                "S9876543A",
                "Check-Up",
                LocalDateTime.parse("22-02-2025 12:00", DATE_TIME_FORMATTER),
                LocalDateTime.parse("22-02-2025 12:30", DATE_TIME_FORMATTER),
                nric.toString()
        );

        model.addAppointment(bob, appointment);

        LocalDate testDate = LocalDate.of(2025, 2, 22);
        ViewAppointmentByDateCommand command = new ViewAppointmentByDateCommand(testDate);
        String expectedMessage = String.format(ViewAppointmentByDateCommand.MESSAGE_SUCCESS, testDate);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false);
        assertCommandSuccess(command, model, expectedCommandResult, model);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        LocalDate testDate = LocalDate.now();
        ViewAppointmentByDateCommand command = new ViewAppointmentByDateCommand(testDate);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_noAppointmentsOnDate_showsNoAppointmentsMessage() throws CommandException {
        Model model = new ModelManager();
        LocalDate testDate = LocalDate.of(2025, 3, 23);
        ViewAppointmentByDateCommand command = new ViewAppointmentByDateCommand(testDate);

        CommandResult result = command.execute(model);
        assertEquals(
                String.format(ViewAppointmentByDateCommand.MESSAGE_NO_APPOINTMENT, testDate),
                result.getFeedbackToUser()
        );
    }
}
