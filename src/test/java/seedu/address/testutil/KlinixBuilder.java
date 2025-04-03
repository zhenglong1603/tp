package seedu.address.testutil;

import seedu.address.model.Klinix;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Klinix objects.
 * Example usage: <br>
 *     {@code KlinixBuilder kb = new KlinixBuilder().withPerson("John", "Doe").build();}
 */
public class KlinixBuilder {

    private Klinix klinix;

    public KlinixBuilder() {
        klinix = new Klinix();
    }

    public KlinixBuilder(Klinix klinix) {
        this.klinix = klinix;
    }

    /**
     * Adds a new {@code Person} to the {@code Klinix} that we are building.
     */
    public KlinixBuilder withPerson(Person person) {
        klinix.addPerson(person);
        return this;
    }

    public Klinix build() {
        return klinix;
    }
}
