package seedu.address.model.doctor;

/**
 * Represents a Doctor in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Doctor {
    private final String name;
    private final String nric;
    private final String specialisation;

    /**
     * Every field must be present and not null.
     */
    public Doctor(String name, String nric, String specialisation) {
        this.name = name;
        this.nric = nric;
        this.specialisation = specialisation;
    }

    public String getName() {
        return name;
    }

    public String getNric() {
        return nric;
    }

    public String getSpecialisation() {
        return specialisation;
    }
}
