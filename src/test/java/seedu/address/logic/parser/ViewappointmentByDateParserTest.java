package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.util.DateUtil.DATE_FORMATTER;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewAppointmentByDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ViewappointmentByDateParserTest {

    private final ViewAppointmentByDateParser parser = new ViewAppointmentByDateParser();
    private final DateTimeFormatter formatter = DATE_FORMATTER;

    @Test
    public void parse_missingDatePrefix_throwsParseException() {
        String userInput = "15-12-2023"; // Missing prefix
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAppointmentByDateCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }

    @Test
    public void parse_emptyPreamble_throwsParseException() {
        String userInput = "preamble " + PREFIX_DATE + "15-12-2023";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAppointmentByDateCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "2023-12-15"; // Wrong format
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_emptyDateValue_throwsParseException() {
        String userInput = " " + PREFIX_DATE; // No date provided
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAppointmentByDateCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        String userInput = " " + PREFIX_DATE + "2112-02-2023"; // Invalid date

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
