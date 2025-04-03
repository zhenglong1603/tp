package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class BirthDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BirthDate(null));
    }

    @Test
    public void constructor_invalidBirthDate_throwsIllegalArgumentException() {
        String invalidBirthDate = "32-13-2020";
        assertThrows(IllegalArgumentException.class, () -> new BirthDate(invalidBirthDate));
    }

    @Test
    public void isValidBirthDate() {
        assertThrows(NullPointerException.class, () -> BirthDate.isValidBirthDate(null));

        assertFalse(BirthDate.isValidBirthDate(""));
        assertFalse(BirthDate.isValidBirthDate(" "));
        assertFalse(BirthDate.isValidBirthDate("32-13-2020"));
        assertFalse(BirthDate.isValidBirthDate("2020/12/31"));
        assertFalse(BirthDate.isValidBirthDate("31/12-2020"));

        assertTrue(BirthDate.isValidBirthDate("01-01-2000"));
        assertTrue(BirthDate.isValidBirthDate("31-12-1999"));
    }

    @Test
    public void getAge() {
        BirthDate birthDate = new BirthDate("01-01-2000");
        int currentYear = LocalDate.now().getYear();
        int expectedAge = currentYear - 2000;
        if (LocalDate.now().getDayOfYear() < LocalDate.of(2000, 1, 1).getDayOfYear()) {
            expectedAge--;
        }
        assertEquals(expectedAge, birthDate.getAge());
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void equals() {
        BirthDate birthDate = new BirthDate("01-01-2000");

        assertTrue(birthDate.equals(new BirthDate("01-01-2000")));
        assertTrue(birthDate.equals(birthDate));
        assertFalse(birthDate.equals(null));
        assertFalse(birthDate.equals(5.0f));
        assertFalse(birthDate.equals(new BirthDate("02-01-2000")));
    }

    @Test
    public void compareTo() {
        BirthDate birthDate1 = new BirthDate("01-01-2000");
        BirthDate birthDate2 = new BirthDate("02-01-2000");

        assertTrue(birthDate1.compareTo(birthDate2) < 0);
        assertTrue(birthDate2.compareTo(birthDate1) > 0);
        assertTrue(birthDate1.compareTo(new BirthDate("01-01-2000")) == 0);
    }
}
