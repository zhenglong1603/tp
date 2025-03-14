package seedu.address.model.doctor;

import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;

/**
 * Represents a Doctor in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Doctor {

    public final Name name;
    public final Nric nric;
    public final Specialisation specialisation;

    /**
     * Every field must be present and not null.
     */
    public Doctor(Name name, Nric nric, Specialisation specialisation) {
        checkNotNull(name, nric, specialisation);
        this.name = name;
        this.nric = nric;
        this.specialisation = specialisation;
    }

    private void checkNotNull(Name name, Nric nric, Specialisation specialisation) throws NullPointerException {
        if (name == null || nric == null || specialisation == null) {
            throw new NullPointerException();
        }
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public Specialisation getSpecialisation() {
        return specialisation;
    }
}
