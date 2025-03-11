package seedu.address.model.doctor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;

public class DoctorTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        Nric validNric = new Nric("S1234567D");
        Specialisation validSpecialisation = new Specialisation("Cardiology");
        assertThrows(NullPointerException.class, () -> new Doctor(null, validNric, validSpecialisation));
    }

    @Test
    public void constructor_nullNric_throwsNullPointerException() {
        Name validName = new Name("John Doe");
        Specialisation validSpecialisation = new Specialisation("Cardiology");
        assertThrows(NullPointerException.class, () -> new Doctor(validName, null, validSpecialisation));
    }

    @Test
    public void constructor_nullSpecialisation_throwsNullPointerException() {
        Name validName = new Name("John Doe");
        Nric validNric = new Nric("S1234567D");
        assertThrows(NullPointerException.class, () -> new Doctor(validName, validNric, null));
    }

    @Test
    public void constructor_validInputs_success() {
        Name validName = new Name("John Doe");
        Nric validNric = new Nric("S1234567D");
        Specialisation validSpecialisation = new Specialisation("Cardiology");
        Doctor doctor = new Doctor(validName, validNric, validSpecialisation);
        assertNotNull(doctor);
        assertEquals(validName, doctor.getName());
        assertEquals(validNric, doctor.getNric());
        assertEquals(validSpecialisation, doctor.getSpecialisation());
    }
}
