package seedu.address.model.doctor;

import org.junit.jupiter.api.Test;
import seedu.address.model.doctor.Specialisation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class SpecialisationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Specialisation(null));
    }

    @Test
    public void constructor_invalidSpecialisation_throwsIllegalArgumentException() {
        String invalidSpecialisation = "";
        assertThrows(IllegalArgumentException.class, () -> new Specialisation(invalidSpecialisation));
    }

    @Test
    public void isValidSpecialisation() {
        // null Specialisation
        assertThrows(NullPointerException.class, () -> Specialisation.isValidSpecialisation(null));

        // invalid Specialisations
        assertFalse(Specialisation.isValidSpecialisation("")); // empty string
        assertFalse(Specialisation.isValidSpecialisation(" ")); // spaces only

        // valid Specialisations
        assertTrue(Specialisation.isValidSpecialisation("Dermatologist")); // alphabets only
        assertTrue(Specialisation.isValidSpecialisation("-")); // one character
        assertTrue(Specialisation.isValidSpecialisation("Paediatric Haematology & Oncology")); // long Specialisation
    }

    @Test
    public void equals() {
        Specialisation Specialisation = new Specialisation("Valid Specialisation");

        // same values -> returns true
        assertTrue(Specialisation.equals(new Specialisation("Valid Specialisation")));

        // same object -> returns true
        assertTrue(Specialisation.equals(Specialisation));

        // null -> returns false
        assertFalse(Specialisation.equals(null));

        // different types -> returns false
        assertFalse(Specialisation.equals(5.0f));

        // different values -> returns false
        assertFalse(Specialisation.equals(new Specialisation("Other Valid Specialisation")));
    }
}
