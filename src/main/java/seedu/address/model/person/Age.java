package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's age in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class Age implements Comparable<Age> {

    public static final String MESSAGE_CONSTRAINTS =
            "Age should be a non-negative integer and it should not be blank";

    /*
     * The age must be a non-negative integer.
     */
    public static final String VALIDATION_REGEX = "\\d+";

    public final int value;

    /**
     * Constructs a {@code Age}.
     *
     * @param age A valid age.
     */
    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(age);
    }

    /**
     * Returns true if a given string is a valid age.
     */
    public static boolean isValidAge(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Age)) {
            return false;
        }

        Age otherAge = (Age) other;
        return value == otherAge.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public int compareTo(Age other) {
        return Integer.compare(this.value, other.value);
    }
}
