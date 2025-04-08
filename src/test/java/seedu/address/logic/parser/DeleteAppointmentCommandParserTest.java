package seedu.address.logic.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

// The test cases are adapted from a conversation with chatGPT
class DeleteAppointmentCommandParserTest {

    private DeleteAppointmentCommandParser parser;

    @BeforeEach
    void setUp() {
        parser = new DeleteAppointmentCommandParser();
    }

    @Test
    void parse_validInput_success() throws ParseException {
        String input = "1 ic/S1234567A";
        DeleteAppointmentCommand command = parser.parse(input);

        assertEquals(new DeleteAppointmentCommand(new Nric("S1234567A"), Index.fromOneBased(1)), command);
    }

    @Test
    void parse_missingNric_throwsParseException() {
        String input = "1";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_invalidIndex_throwsParseException() {
        String input = "a ic/S1234567A";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_missingArguments_throwsParseException() {
        String input = "";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}
