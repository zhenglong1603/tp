package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearAppointmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

class ClearAppointmentsCommandParserTest {
    private ClearAppointmentsCommandParser parser;

    @BeforeEach
    void setUp() {
        parser = new ClearAppointmentsCommandParser();
    }

    @Test
    void parse_validNric_success() throws ParseException {
        String input = " ic/S1234567A";
        ClearAppointmentsCommand command = parser.parse(input);
        assertEquals(new ClearAppointmentsCommand(new Nric("S1234567A")), command);
    }

    @Test
    void parse_validIndex_success() throws ParseException {
        String input = " 1";
        ClearAppointmentsCommand command = parser.parse(input);
        assertEquals(new ClearAppointmentsCommand(Index.fromOneBased(1)), command);
    }

    @Test
    void parse_invalidNricFormat_throwsParseException() {
        String input = " ic/INVALID123";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_indexAndNricTogether_throwsParseException() {
        String input = " 1 ic/S1234567A";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_nricThenIndex_throwsParseException() {
        String input = " ic/S1234567A 1";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_duplicateNricPrefixes_throwsParseException() {
        String input = " ic/S1234567A ic/S7654321B";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_extraTextAfterIndex_throwsParseException() {
        String input = " 1 extra";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_emptyInput_throwsParseException() {
        String input = " ";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}
