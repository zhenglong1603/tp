package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicineusage.Dosage;
import seedu.address.model.medicineusage.MedicineName;
import seedu.address.model.person.Address;
import seedu.address.model.person.BirthDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "Rachel sig/ma";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    // Whitespace normalization
    @Test
    public void smartTrim_whitespaceOnly_returnsEmpty() {
        assertEquals("", ParserUtil.smartTrim("     "));
        assertEquals("", ParserUtil.smartTrim("\n\t  "));
    }

    @Test
    public void smartTrim_multipleSpaces_collapsesCorrectly() {
        assertEquals("a b c", ParserUtil.smartTrim("a     b\t  c"));
    }

    // Dash
    @Test
    public void smartTrim_dash_hasNoSpacesAround() {
        assertEquals("2023-2024", ParserUtil.smartTrim("2023 - 2024"));
    }

    // Plus
    @Test
    public void smartTrim_plus_hasNoSpacesAround() {
        assertEquals("A+B", ParserUtil.smartTrim(" A + B "));
    }

    // At symbol
    @Test
    public void smartTrim_atSymbol_hasNoSpacesAround() {
        assertEquals("name@example. com", ParserUtil.smartTrim("name @ example.com"));
    }

    // Colon
    @Test
    public void smartTrim_colon_hasNoSpacesAround() {
        assertEquals("Time:10AM", ParserUtil.smartTrim(" Time : 10AM "));
    }

    // Apostrophe
    @Test
    public void smartTrim_apostrophe_hasNoSpacesAround() {
        assertEquals("John's book", ParserUtil.smartTrim("John ' s book"));
    }

    // Period
    @Test
    public void smartTrim_period_hasNoSpaceBeforeOneAfter() {
        assertEquals("e. g. example", ParserUtil.smartTrim("e . g .example"));
    }

    // Comma
    @Test
    public void smartTrim_comma_hasNoSpaceBeforeOneAfter() {
        assertEquals("a, b, c", ParserUtil.smartTrim("a ,b ,  c"));
    }

    // Parentheses
    @Test
    public void smartTrim_leftParenthesis_hasOneSpaceBefore() {
        assertEquals("Vitamin C (500mg)", ParserUtil.smartTrim("Vitamin C( 500mg)"));
    }

    @Test
    public void smartTrim_rightParenthesis_hasOneSpaceAfter() {
        assertEquals("a (b) c", ParserUtil.smartTrim("a(b )  c"));
    }

    // Hash
    @Test
    public void smartTrim_hash_hasOneSpaceBefore() {
        assertEquals("Label #Tag", ParserUtil.smartTrim("Label#Tag"));
    }

    // Combination and real-world input
    @Test
    public void smartTrim_combination_fullFormatting() {
        String input = "  Dr .  John ' s  Clinic  (Room 3) - 24 + 7  @ hospital.com ";
        String expected = "Dr. John's Clinic (Room 3)-24+7@hospital. com";
        assertEquals(expected, ParserUtil.smartTrim(input));
    }

    @Test
    public void smartTrim_complexSpacingAndPunctuation() {
        String input = "This , is  a  test .  Check ( formatting ) -  now + then @here: ok";
        String expected = "This, is a test. Check (formatting)-now+then@here:ok";
        assertEquals(expected, ParserUtil.smartTrim(input));
    }

    // Empty input
    @Test
    public void smartTrim_emptyInput_returnsEmpty() {
        assertEquals("", ParserUtil.smartTrim(""));
    }

    @Test
    public void parseIndex_validInput_success2() throws Exception {
        assertEquals(Index.fromOneBased(1), ParserUtil.parseIndex(" 1 "));
        assertEquals(Index.fromOneBased(42), ParserUtil.parseIndex("   42\t"));
    }

    @Test
    public void parseIndex_invalidInput_throwsParseException2() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("-5"));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("abc"));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("  4a  "));
    }

    @Test
    public void parseName_valid_success() throws Exception {
        assertEquals(new Name("John Doe"), ParserUtil.parseName(" John  Doe "));
    }

    @Test
    public void parseName_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseName("###"));
    }

    @Test
    public void parseMedicineName_valid_success() throws Exception {
        assertEquals(new MedicineName("Paracetamol 500mg"), ParserUtil.parseMedicineName(" Paracetamol  500mg "));
    }

    @Test
    public void parseMedicineName_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedicineName("   "));
        assertThrows(ParseException.class, () -> ParserUtil.parseMedicineName("!!@@"));
    }

    @Test
    public void parseDosage_valid_success() throws Exception {
        assertEquals(new Dosage("1 tablet"), ParserUtil.parseDosage(" 1  tablet "));
    }

    @Test
    public void parseDosage_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDosage("!!"));
    }

    @Test
    public void parsePhone_valid_success() throws Exception {
        assertEquals(new Phone("91234567"), ParserUtil.parsePhone("  9123 4567 "));
    }

    @Test
    public void parsePhone_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone("12"));
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone("phone"));
    }

    @Test
    public void parseEmail_valid_success() throws Exception {
        assertEquals(new Email("test@example.com"), ParserUtil.parseEmail(" test @ example . com "));
    }

    @Test
    public void parseEmail_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail("test@@example.com"));
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail("test.com"));
    }

    @Test
    public void parseNric_valid_success() throws Exception {
        assertEquals(new Nric("S1234567D"), ParserUtil.parseNric(" S1234  567D "));
    }

    @Test
    public void parseNric_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNric("S12A"));
    }

    @Test
    public void parseBirthDate_valid_success() throws Exception {
        assertEquals(new BirthDate("01-01-2000"), ParserUtil.parseBirthDate("01  -  01 - 20 00"));
    }

    @Test
    public void parseBirthDate_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBirthDate("2000-01-01"));
        assertThrows(ParseException.class, () -> ParserUtil.parseBirthDate("invalid"));
    }

    @Test
    public void parseTag_valid_success() throws Exception {
        assertEquals(new Tag("urgent"), ParserUtil.parseTag(" urgent "));
    }

    @Test
    public void parseTag_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag("!!!"));
    }

    @Test
    public void parseTags_valid_success() throws Exception {
        Set<Tag> tags = ParserUtil.parseTags(List.of(" urgent ", " general "));
        assertTrue(tags.contains(new Tag("urgent")));
        assertTrue(tags.contains(new Tag("general")));
    }

    @Test
    public void parseAppointmentDescription_valid_success() throws Exception {
        assertEquals("Check-up (fasting)",
                ParserUtil.parseAppointmentDescription("  Check -  up  ( fasting ) "));
    }

    @Test
    public void parseAppointmentDescription_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAppointmentDescription(""));
    }

    @Test
    public void parseMedicalField_valid_success() throws Exception {
        assertEquals("Peanut allergy", ParserUtil.parseMedicalField("Peanut   allergy"));
    }

    @Test
    public void parseMedicalField_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedicalField("12345")); // only digits
        assertThrows(ParseException.class, () -> ParserUtil.parseMedicalField("")); // empty
    }

    @Test
    public void parseLocalDateTime_valid_success() throws Exception {
        LocalDateTime expected = LocalDateTime.of(2025, 4, 5, 14, 30);
        assertEquals(expected, ParserUtil.parseLocalDateTime("05-04-2025 14:30"));
    }

    @Test
    public void parseLocalDateTime_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocalDateTime("2025-04-05T14:30"));
        assertThrows(ParseException.class, () -> ParserUtil.parseLocalDateTime("invalid date"));
    }

    @Test
    public void parseLocalDate_valid_success() throws Exception {
        assertEquals(LocalDate.of(2024, 12, 31), ParserUtil.parseLocalDate("31-12-2024"));
    }

    @Test
    public void parseLocalDate_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocalDate("31/12/2024"));
        assertThrows(ParseException.class, () -> ParserUtil.parseLocalDate("abcd"));
    }
}
