package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalKlinix;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.AppointmentListByDate;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class KlinixTest {

    private final Klinix klinix = new Klinix();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), klinix.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> klinix.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyKlinix_replacesData() {
        Klinix newData = getTypicalKlinix();
        klinix.resetData(newData);
        assertEquals(newData, klinix);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        KlinixStub newData = new KlinixStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> klinix.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> klinix.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInKlinix_returnsFalse() {
        assertFalse(klinix.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInKlinix_returnsTrue() {
        klinix.addPerson(ALICE);
        assertTrue(klinix.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInKlinix_returnsTrue() {
        klinix.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(klinix.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> klinix.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = Klinix.class.getCanonicalName() + "{persons=" + klinix.getPersonList() + "}";
        assertEquals(expected, klinix.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class KlinixStub implements ReadOnlyKlinix {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        KlinixStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public Person findPersonByNric(Nric nric) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AppointmentListByDate getAppointmentsByDate() {
            throw new AssertionError("This method should not be called.");
        }
    }

}
