package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindMedicineUsageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MedicineUsageContainsKeywordsPredicate;

// The test cases are adapted from a conversation with chatGPT

public class FindMedicineUsageCommandParserTest {

    private final FindMedicineUsageCommandParser parser = new FindMedicineUsageCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommand() throws ParseException {
        FindMedicineUsageCommand expectedCommand =
                new FindMedicineUsageCommand(new MedicineUsageContainsKeywordsPredicate(
                        Arrays.asList("paracetamol", "amoxicillin")));

        assertEquals(expectedCommand, parser.parse("paracetamol amoxicillin"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }
}
