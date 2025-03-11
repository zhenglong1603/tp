package seedu.address.model.doctor;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Name;

import static seedu.address.testutil.Assert.assertThrows;

public class DoctorTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Doctor(null, null, null));
    }
}
