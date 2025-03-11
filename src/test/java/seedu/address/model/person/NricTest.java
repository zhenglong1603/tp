package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "A123456";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        assertFalse(Nric.isValidNric(""));
        assertFalse(Nric.isValidNric(" "));
        assertFalse(Nric.isValidNric("A123456"));
        assertFalse(Nric.isValidNric("1234567A"));
        assertFalse(Nric.isValidNric("A12345678"));
        assertFalse(Nric.isValidNric("A123456B"));

        assertTrue(Nric.isValidNric("S1234567D"));
        assertTrue(Nric.isValidNric("T7654321F"));
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void equals() {
        Nric nric = new Nric("S1234567D");

        assertTrue(nric.equals(new Nric("S1234567D")));
        assertTrue(nric.equals(nric));
        assertFalse(nric.equals(null));
        assertFalse(nric.equals(5.0f));
        assertFalse(nric.equals(new Nric("T7654321F")));
    }
}
