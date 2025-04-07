package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.DateUtil.DATE_FORMATTER;
import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;
import static seedu.address.commons.util.DateUtil.INVALID_DATETIME_MESSAGE;
import static seedu.address.commons.util.DateUtil.INVALID_DATE_MESSAGE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicineusage.Dosage;
import seedu.address.model.medicineusage.MedicineName;
import seedu.address.model.person.Address;
import seedu.address.model.person.BirthDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.MedicalReport;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String DATETIME_INVALID_SPECIFY = "\nInput to be corrected: ";
    public static final String DATE_INVALID_SPECIFY = "\nInput to be corrected: ";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.replaceAll("\\s+", ""); // Remove all white spaces
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = smartTrimName(name);
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code MedicineName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static MedicineName parseMedicineName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = smartTrim(name);
        if (!MedicineName.isValidName(trimmedName)) {
            throw new ParseException(MedicineName.MESSAGE_CONSTRAINTS);
        }
        return new MedicineName(trimmedName);
    }

    /**
     * Parses a {@code String dosage} into a {@code Dosage}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dosage} is invalid.
     */
    public static Dosage parseDosage(String dosage) throws ParseException {
        requireNonNull(dosage);
        String trimmedDosage = smartTrim(dosage);
        if (!Dosage.isValidDosage(trimmedDosage)) {
            throw new ParseException(Dosage.MESSAGE_CONSTRAINTS);
        }
        return new Dosage(trimmedDosage);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.replaceAll("\\s+", ""); // Remove all white spaces
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = smartTrim(address);
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.replaceAll("\\s+", ""); // Remove all white spaces
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String nric} into a {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.replaceAll("\\s+", ""); // Remove all white spaces
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code String birthDate} into a {@code BirthDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code birthDate} is invalid.
     */
    public static BirthDate parseBirthDate(String birthDate) throws ParseException {
        requireNonNull(birthDate);
        String trimmedBirthDate = birthDate.replaceAll("\\s+", ""); // Remove all white spaces
        if (!BirthDate.isValidBirthDate(trimmedBirthDate)) {
            throw new ParseException(BirthDate.MESSAGE_CONSTRAINTS);
        }
        return new BirthDate(trimmedBirthDate);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = smartTrim(tag);
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String appointmentDescription} into a {@code String appointmentDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code appointmentDescription} is empty.
     */
    public static String parseAppointmentDescription(String appointmentDescription) throws ParseException {
        requireNonNull(appointmentDescription);
        String trimmedAppointmentDescription = smartTrim(appointmentDescription);
        if (!Appointment.isValidDescription(trimmedAppointmentDescription)) {
            throw new ParseException(Appointment.DESCRIPTION_MESSAGE_CONSTRAINTS);
        }
        return trimmedAppointmentDescription;
    }

    /**
     * Parses a medical report field (e.g. allergy, illness, etc.) and validates its format.
     * Allowed characters: letters, numbers, spaces, commas, hyphens.
     * Must not be empty or contain only numbers.
     *
     * @throws ParseException if the given {@code medicalField} is empty.
     */
    //Solution below inspired by:
    //https://stackoverflow.com/questions/22990870/how-to-disable-emoji-from-being-entered-in-android-edittext
    public static String parseMedicalField(String field) throws ParseException {
        requireNonNull(field);
        String trimmedField = smartTrim(field);
        if (!MedicalReport.isValidMedicalField(trimmedField)) {
            throw new ParseException(MedicalReport.MESSAGE_CONSTRAINTS);
        }
        return trimmedField;
    }

    /**
     * Parses a {@code String dateTime} into a {@code localDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static LocalDateTime parseLocalDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = smartTrim(dateTime);
        try {
            return LocalDateTime.parse(trimmedDateTime, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new ParseException(INVALID_DATETIME_MESSAGE + DATETIME_INVALID_SPECIFY + trimmedDateTime, exception);
        }
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseLocalDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.replaceAll("\\s+", ""); // Remove all white spaces
        try {
            return LocalDate.parse(trimmedDate, DATE_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new ParseException(INVALID_DATE_MESSAGE + DATE_INVALID_SPECIFY + trimmedDate, exception);
        }
    }

    static String smartTrim(String input) {
        requireNonNull(input);
        // Handle characters one by one to avoid complication in rules conflict resolution
        String trimmed = input.trim().replaceAll("\\s+", " "); // Remove extra white spaces
        trimmed = trimmed.replaceAll("\\s*\\+\\s*", "+"); // Plus
        trimmed = trimmed.replaceAll("\\s*@\\s*", "@"); // At
        trimmed = trimmed.replaceAll("\\s*:\\s*", ":"); // Colon
        trimmed = trimmed.replaceAll("\\s*'\\s*", "'"); // Apostrophe
        trimmed = trimmed.replaceAll("\\s*\\.\\s*", ". "); // Period
        trimmed = trimmed.replaceAll("\\s*,\\s*", ", "); // Comma
        trimmed = trimmed.replaceAll("\\s*\\)\\s*", ") "); // Right parenthesis
        trimmed = trimmed.replaceAll("\\s*\\(\\s*", " ("); // Left parenthesis
        trimmed = trimmed.replaceAll("\\s*#\\s*", " #"); // Hash
        trimmed = trimmed.replaceAll("\\s*\\-\\s*", "-"); // Dash
        trimmed = trimmed.replaceAll("\\s+", " ").trim(); // Remove extra spaces again

        return trimmed;
    }

    static String smartTrimName(String input) {
        requireNonNull(input);
        // Handle characters one by one to avoid complication in rules conflict resolution
        String trimmed = input.trim().replaceAll("\\s+", " "); // Remove extra white spaces
        trimmed = trimmed.replaceAll("\\s*\\.\\s*", ". "); // Plus
        trimmed = trimmed.replaceAll("\\s*@\\s*", " @ "); // At
        trimmed = trimmed.replaceAll("\\s*:\\s*", ":"); // Colon
        trimmed = trimmed.replaceAll("\\s*'\\s*", "'"); // Apostrophe
        trimmed = trimmed.replaceAll("\\s*\\-\\s*", "-"); // Dash
        trimmed = trimmed.replaceAll("\\s+", " ").trim(); // Remove extra spaces again

        return trimmed;
    }
}
