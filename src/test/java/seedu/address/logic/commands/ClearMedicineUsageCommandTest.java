package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.Address;
import seedu.address.model.person.BirthDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.MedicalReport;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

class ClearMedicineUsageCommandTest {
    private ModelManager model;
    private Person person;
    private Nric nric;
    private MedicineUsage medicineUsage;

    @BeforeEach
    void setUp() {
        model = new ModelManager();
        nric = new Nric("S1234567A");
        person = new Person(
                new Name("John Doe"),
                new Phone("12345678"),
                new Email("john@example.com"),
                nric,
                new BirthDate("01/01/1990"),
                new Address("123 Street"),
                new HashSet<>(),
                new MedicalReport("None", "None", "None", "None")
        );
        medicineUsage = new MedicineUsage("Paracetamol", "500mg", LocalDate.now(), LocalDate.now().plusDays(5));
        person.getMedicalReport().add(medicineUsage);
        model.addPerson(person);
    }

    @Test
    void execute_success() {
        ClearMedicineUsageCommand command = new ClearMedicineUsageCommand(nric);
        assertDoesNotThrow(() -> command.execute(model));
        assertTrue(person.getMedicalReport().getMedicineUsages().isEmpty());
    }

    @Test
    void execute_personNotFound_throwsCommandException() {
        Nric nonExistentNric = new Nric("S9999999Z");
        ClearMedicineUsageCommand command = new ClearMedicineUsageCommand(nonExistentNric);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    void equals_sameObject_returnsTrue() {
        ClearMedicineUsageCommand command = new ClearMedicineUsageCommand(nric);
        assertTrue(command.equals(command));
    }

    @Test
    void equals_differentObjectWithSameValues_returnsTrue() {
        ClearMedicineUsageCommand command1 = new ClearMedicineUsageCommand(nric);
        ClearMedicineUsageCommand command2 = new ClearMedicineUsageCommand(nric);
        assertTrue(command1.equals(command2));
    }

    @Test
    void equals_differentObject_returnsFalse() {
        ClearMedicineUsageCommand command = new ClearMedicineUsageCommand(nric);
        assertFalse(command.equals(new Object()));
    }

    @Test
    void equals_differentNric_returnsFalse() {
        ClearMedicineUsageCommand command1 = new ClearMedicineUsageCommand(new Nric("S7654321B"));
        ClearMedicineUsageCommand command2 = new ClearMedicineUsageCommand(nric);
        assertFalse(command1.equals(command2));
    }
}
