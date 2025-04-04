package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the klinix.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "This is not a valid name. \n"
                    + "Names should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank. "
                    + "Names can also contain special characters such as ' and -.\n"
                    + "The first character of the name must not be a whitespace.\n "
                    + "Names can also contain suffixes such as s/o, d/o, c/o, @.\n"
                    + "Example: John Doe s/o John Doe";

    /**
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.[A-Za-z\\p{L}]+
     */
    public static final String VALIDATION_REGEX =
            "^[A-Za-z0-9\\p{L}]+(?:['\\.\\-\\s]+[A-Za-z0-9\\p{L}]+)*"
            + "(?:\\s+(?:s/o|d/o|c/o|@)\\s+[A-Za-z0-9\\p{L}]+(?:['\\.\\-\\s]+[A-Za-z0-9\\p{L}]+)*)?$";
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
