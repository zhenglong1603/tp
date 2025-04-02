package seedu.address.model.medicineusage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Dosage in the klinix.
 * Guarantees: immutable; is valid as declared in {@link #isValidDosage(String)}
 */
public class Dosage {

    public static final String MESSAGE_CONSTRAINTS =
            "Dosage description should only contain alphanumeric characters, spaces, periods(.), forward slashes(/),"
                    + "hyphens(-), parentheses() and it should not be blank";

    /*
     * The first character of the dosage description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9.,/\\-()][a-zA-Z0-9\\s.,/\\-()]*$";

    public final String dosage;

    /**
     * Constructs a {@code MedicineName}.
     *
     * @param dosage A valid dosage's description.
     */
    public Dosage(String dosage) {
        requireNonNull(dosage);
        checkArgument(isValidDosage(dosage), MESSAGE_CONSTRAINTS);
        this.dosage = dosage;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDosage(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return dosage;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Dosage)) {
            return false;
        }

        Dosage otherDosage = (Dosage) other;
        return dosage.equals(otherDosage.dosage);
    }

    @Override
    public int hashCode() {
        return dosage.hashCode();
    }

}
