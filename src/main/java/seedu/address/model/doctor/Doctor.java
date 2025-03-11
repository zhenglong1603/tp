package seedu.address.model.doctor;

    /**
     * Represents a Doctor in the address book.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */

public class Doctor {
    private final String Name;
    private final String NRIC;
    private final String Specialisation;

    public Doctor(String Name, String NRIC, String Specialisation) {
        this.Name = Name;
        this.NRIC = NRIC;
        this.Specialisation = Specialisation;
    }

    public String getName() {
        return Name;
    }

    public String getNRIC() {
        return NRIC;
    }

    public String getSpecialisation() {
        return Specialisation;
    }
}
