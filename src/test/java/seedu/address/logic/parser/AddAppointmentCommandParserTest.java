package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;

public class AddAppointmentCommandParserTest {
    private final AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        String userInput = " " + PREFIX_NRIC + "S1234567A "
                + PREFIX_APPOINTMENT_DESCRIPTION + "Routine Checkup "
                + PREFIX_FROM + "01-10-2025 10:00 "
                + PREFIX_TO + "01-10-2025 11:00";

        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(
                new Nric("S1234567A"),
                new Appointment("Routine Checkup",
                        LocalDateTime.of(2025, 10, 1, 10, 0),
                        LocalDateTime.of(2025, 10, 1, 11, 0),
                        "S1234567A"));

        AddAppointmentCommand actualCommand = parser.parse(userInput);

        // Log expected and actual commands
        System.out.println("Expected: " + expectedCommand);
        System.out.println("Actual: " + actualCommand);

        // Perform the assertion
        assertEquals(expectedCommand, actualCommand);
    }


    @Test
    public void parse_missingFields_failure() {
        String userInput = " " + PREFIX_NRIC + "S1234567A ";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDateFormat_failure() {
        String userInput = " " + PREFIX_NRIC + "S1234567A "
                + PREFIX_APPOINTMENT_DESCRIPTION + "Routine Checkup "
                + PREFIX_FROM + "10:00 2024-10-01 "
                + PREFIX_TO + "11:00 2024-10-01";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String userInput = " " + PREFIX_NRIC + "S1234567A "
                + PREFIX_NRIC + "S7654321B "
                + PREFIX_APPOINTMENT_DESCRIPTION + "Routine Checkup "
                + PREFIX_FROM + "2024-10-01 10:00 "
                + PREFIX_TO + "2024-10-01 11:00";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_emptyInput_failure() {
        String userInput = "";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
    }
}
