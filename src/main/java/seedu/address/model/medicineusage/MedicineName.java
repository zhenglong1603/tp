package seedu.address.model.medicineusage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a MedicineUsage's medicine name in the klinix.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class MedicineName {

    public static final String MESSAGE_CONSTRAINTS =
            "Medicine names should only contain -()+,.' alphanumeric characters and spaces. It should not be blank";

    /*
     * The first character of the medicine name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\p{Alnum}[\\p{Alnum} \\-()+,.']*";

    public final String fullName;

    /**
     * Constructs a {@code MedicineName}.
     *
     * @param name A valid name.
     */
    public MedicineName(String name) {
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

    /**
     * Checks if two medicine names have same name, this is a weaker equality.
     * @param toCompare medicine name to compare.
     * @return true if two medicine names are the same lowercase.
     */
    public boolean isSameName(MedicineName toCompare) {
        return fullName.equalsIgnoreCase(toCompare.fullName);
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
        if (!(other instanceof MedicineName)) {
            return false;
        }

        MedicineName otherName = (MedicineName) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
