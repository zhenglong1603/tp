package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the klinix.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "This is not a valid name.\n"
                    + "Names must start with a letter and can contain only alphabetic characters, "
                    + "spaces, apostrophes (’), hyphens (-), and periods (.).\n"
                    + "A name must not start with whitespace or special characters.\n"
                    + "Names may include suffixes like s/o, d/o, c/o, or @, followed by another valid name.\n"
                    + "Example: John Doe s/o John Doe.";

    /**
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX =
            "^[A-Za-z\\p{L}]+(?:['\\.\\-\\s]+[A-Za-z\\p{L}]+)*"
                    + "(?:\\s+(?:s/o|d/o|c/o|@)\\s+[A-Za-z\\p{L}]+(?:['\\.\\-\\s]+[A-Za-z\\p{L}]+)*)?$";
    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
