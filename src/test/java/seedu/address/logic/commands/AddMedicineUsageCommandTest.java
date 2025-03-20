package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.AppointmentList;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.Address;
import seedu.address.model.person.BirthDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.MedicalReport;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

class AddMedicineUsageCommandTest {
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
                new HashSet<Tag>(),
                new MedicalReport("None", "None", "None", "None"),
                new AppointmentList()
        );
        medicineUsage = new MedicineUsage("Paracetamol", "500mg", LocalDate.now(), LocalDate.now().plusDays(5));
        model.addPerson(person);
    }

    @Test
    void execute_success() {
        AddMedicineUsageCommand command = new AddMedicineUsageCommand(nric, medicineUsage);
        assertDoesNotThrow(() -> command.execute(model));
        assertTrue(person.getMedicalReport().getMedicineUsages().contains(medicineUsage));
    }

    @Test
    void execute_personNotFound_throwsCommandException() {
        Nric nonExistentNric = new Nric("S9999999Z");
        AddMedicineUsageCommand command = new AddMedicineUsageCommand(nonExistentNric, medicineUsage);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    void equals_sameObject_returnsTrue() {
        AddMedicineUsageCommand command = new AddMedicineUsageCommand(nric, medicineUsage);
        assertTrue(command.equals(command));
    }

    @Test
    void equals_differentObjectWithSameValues_returnsTrue() {
        AddMedicineUsageCommand command1 = new AddMedicineUsageCommand(nric, medicineUsage);
        AddMedicineUsageCommand command2 = new AddMedicineUsageCommand(nric, medicineUsage);
        assertTrue(command1.equals(command2));
    }

    @Test
    void equals_differentObject_returnsFalse() {
        AddMedicineUsageCommand command = new AddMedicineUsageCommand(nric, medicineUsage);
        assertFalse(command.equals(new Object()));
    }

    @Test
    void equals_differentNric_returnsFalse() {
        AddMedicineUsageCommand command1 = new AddMedicineUsageCommand(new Nric("S7654321B"), medicineUsage);
        AddMedicineUsageCommand command2 = new AddMedicineUsageCommand(nric, medicineUsage);
        assertFalse(command1.equals(command2));
    }

    @Test
    void equals_differentMedicineUsage_returnsFalse() {
        MedicineUsage differentMedicineUsage = new MedicineUsage("Ibuprofen", "200mg", LocalDate.now(),
                LocalDate.now().plusDays(5));
        AddMedicineUsageCommand command1 = new AddMedicineUsageCommand(nric, differentMedicineUsage);
        AddMedicineUsageCommand command2 = new AddMedicineUsageCommand(nric, medicineUsage);
        assertFalse(command1.equals(command2));
    }
}
