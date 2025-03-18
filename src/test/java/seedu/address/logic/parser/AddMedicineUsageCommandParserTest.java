package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddMedicineUsageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.Nric;

class AddMedicineUsageCommandParserTest {
    private AddMedicineUsageCommandParser parser;

    @BeforeEach
    void setUp() {
        parser = new AddMedicineUsageCommandParser();
    }

    @Test
    void parse_validInput_success() throws ParseException {
        String input = " ic/S1234567A n/Paracetamol dos/500mg from/01-01-2025 to/05-01-2025";
        AddMedicineUsageCommand command = parser.parse(input);
        assertNotNull(command);
    }

    @Test
    void parse_invalidDateFormat_throwsParseException() {
        String input = " ic/S1234567A n/Paracetamol dos/500mg from/2025-01-01 to/2025-01-05";
        assertThrows(DateTimeParseException.class, () -> parser.parse(input));
    }

    @Test
    void arePrefixesPresent_allPrefixesPresent_returnsTrue() throws ParseException {
        String input = " ic/S1234567A n/Paracetamol dos/500mg from/01-01-2025 to/05-01-2025";
        AddMedicineUsageCommand command = parser.parse(input);
        assertNotNull(command);
    }

    @Test
    void arePrefixesPresent_missingPrefixes_returnsFalse() {
        String input = " n/Paracetamol dos/500mg from/01-01-2025 to/05-01-2025";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_duplicatePrefixes_throwsParseException() {
        String input = " ic/S1234567A n/Paracetamol n/Ibuprofen dos/500mg from/01-01-2025 to/05-01-2025";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_correctValues_parsedCorrectly() throws ParseException {
        String input = " ic/S1234567A n/Paracetamol dos/500mg from/01-01-2025 to/05-01-2025";
        AddMedicineUsageCommand command = parser.parse(input);
        Nric expectedNric = new Nric("S1234567A");
        MedicineUsage expectedMedicineUsage = new MedicineUsage("Paracetamol", "500mg",
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5));
        assertEquals(new AddMedicineUsageCommand(expectedNric, expectedMedicineUsage), command);
    }
}

