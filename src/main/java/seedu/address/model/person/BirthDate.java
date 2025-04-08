package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.DATE_FORMATTER;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's birth date in the klinix.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthDate(String)}
 */
public class BirthDate implements Comparable<BirthDate> {

    public static final String MESSAGE_CONSTRAINTS =
            "Birth date should be in the format dd-MM-yyyy and should not be in the future.";

    public final LocalDate value;

    /**
     * Constructs a {@code BirthDate}.
     *
     * @param birthDate A valid birth date.
     */
    public BirthDate(String birthDate) {
        requireNonNull(birthDate);
        checkArgument(isValidBirthDate(birthDate), MESSAGE_CONSTRAINTS);
        this.value = LocalDate.parse(birthDate, DATE_FORMATTER);
    }

    private static boolean isDateInPast(String birthDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate date = LocalDate.parse(birthDate, DATE_FORMATTER);
        return date.isBefore(currentDate) || date.isEqual(currentDate);
    }

    /**
     * Returns true if a given string is a valid birthdate.
     */
    public static boolean isValidBirthDate(String test) {
        try {
            LocalDate.parse(test, DATE_FORMATTER);
            return isDateInPast(test);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the current age of the person.
     */
    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        int age = currentDate.getYear() - value.getYear();
        if (currentDate.getDayOfYear() < value.getDayOfYear()) {
            age--;
        }
        return age;
    }

    /**
     * Checks if birthday is after a certain day
     * @param toCompare day to compare
     * @return true if it is after {@code toCompare}, false otherwise
     */
    public boolean isAfter(LocalDate toCompare) {
        return value.isAfter(toCompare);
    }

    /**
     * Checks if birthday is before a certain day
     * @param toCompare day to compare
     * @return true if it is before {@code toCompare}, false otherwise
     */
    public boolean isBefore(LocalDate toCompare) {
        return value.isBefore(toCompare);
    }

    @Override
    public String toString() {
        return value.format(DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BirthDate)) {
            return false;
        }

        BirthDate otherBirthDate = (BirthDate) other;
        return value.equals(otherBirthDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(BirthDate other) {
        return value.compareTo(other.value);
    }

}
