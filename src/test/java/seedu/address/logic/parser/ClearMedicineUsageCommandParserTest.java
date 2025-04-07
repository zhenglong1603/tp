package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearMedicineUsageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

// The test cases are adapted from a conversation with chatGPT

class ClearMedicineUsageCommandParserTest {
    private ClearMedicineUsageCommandParser parser;

    @BeforeEach
    void setUp() {
        parser = new ClearMedicineUsageCommandParser();
    }

    @Test
    void parse_validInput_success() throws ParseException {
        String input = " ic/S1234567A";
        ClearMedicineUsageCommand command = parser.parse(input);
        assertNotNull(command);
    }

    @Test
    void parse_missingRequiredField_throwsParseException() {
        String input = " ";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_invalidNricFormat_throwsParseException() {
        String input = " ic/INVALID123";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_correctValues_parsedCorrectly() throws ParseException {
        String input = " ic/S1234567A";
        ClearMedicineUsageCommand command = parser.parse(input);
        Nric expectedNric = new Nric("S1234567A");
        assertEquals(new ClearMedicineUsageCommand(expectedNric), command);
    }

    @Test
    void parse_validIndexOnly_success() throws ParseException {
        String input = " 2"; // index = 2
        ClearMedicineUsageCommand command = parser.parse(input);
        assertEquals(new ClearMedicineUsageCommand(Index.fromOneBased(2)), command);
    }

    @Test
    void parse_indexAndNricTogether_throwsParseException() {
        String input = " 2 ic/S1234567A"; // Both index and NRIC provided
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_indexWithExtraText_throwsParseException() {
        String input = " 1 extra";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_emptyString_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    void parse_nricWithPreamble_throwsParseException() {
        String input = "garbage ic/S1234567A";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_indexWithMultipleNrics_throwsParseException() {
        String input = "ic/S1234567A ic/S7654321B";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}

