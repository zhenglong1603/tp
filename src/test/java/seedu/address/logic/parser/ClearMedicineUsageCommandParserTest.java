package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearMedicineUsageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

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
}

