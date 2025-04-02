package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;



public class AddMedicalReportCommandParserTest {

    private final AddMedicalReportCommandParser parser = new AddMedicalReportCommandParser();

    @Test
    public void parse_invalidEmojiInput_throwsParseException() {
        String input = "ic/S1234567A al/👁 ill/Flu sur/Appendectomy imm/Vaccine";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_emptyField_throwsParseException() {
        String input = "ic/S1234567A al/ ill/Flu sur/Appendectomy imm/Vaccine";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_whitespaceOnlyField_throwsParseException() {
        String input = "ic/S1234567A al/   ill/Flu sur/Appendectomy imm/Vaccine";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_fieldWithSpecialCharacters_throwsParseException() {
        String input = "ic/S1234567A al/Dust$ ill/Flu sur/Appendectomy imm/^COVID-19";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_randomTextNoPrefix_throwsParseException() {
        String input = "ic/S1234567A ill/Flu hello world al/Dust";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_inputWithSlashInsideValue_throwsParseException() {
        String input = "ic/S1234567A al/Pollen ill/Flu/Cold sur/Appendectomy imm/Vaccine";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_inputWithOnlyNumbers_throwsParseException() {
        String input = "ic/S1234567A al/12345 ill/1234 sur/4567 imm/890";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_missingNricPrefix_throwsParseException() {
        String input = "al/Pollen ill/Flu sur/Appendectomy imm/COVID-19 Vaccine";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String input = "";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}
