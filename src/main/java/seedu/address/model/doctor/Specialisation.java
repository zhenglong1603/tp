package seedu.address.model.doctor;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Doctor's specialisation in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSpecialisation(String)}
 */

public class Specialisation {
    public static final String MESSAGE_CONSTRAINTS = "Specialisation can take any values, and it should not be blank";

    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String value;
    /**
     * Constructs an {@code Specialisation}.
     *
     * @param specialisation A valid specialisation.
     */
    public Specialisation(String specialisation) {
        requireNonNull(specialisation);
        checkArgument(isValidSpecialisation(specialisation), MESSAGE_CONSTRAINTS);
        value = specialisation;
    }

    public static Boolean isValidSpecialisation(String specialisation) {
        return specialisation.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Specialisation)) {
            return false;
        }

        Specialisation otherSpecialisation = (Specialisation) obj;
        return otherSpecialisation.value.equals(value);
    }
}
