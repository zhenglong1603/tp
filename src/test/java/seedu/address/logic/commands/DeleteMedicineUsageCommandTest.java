package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.MedicalReport;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class DeleteMedicineUsageCommandTest {

    private Model model;
    private Person personWithMedicineUsage;
    private Nric validNric;
    private Index validIndex;
    private MedicineUsage medicineUsage;

    @SuppressWarnings("checkstyle:WhitespaceAfter")
    @BeforeEach
    void setUp() {
        model = new ModelManager(seedu.address.testutil.TypicalPersons.getTypicalKlinix(), new UserPrefs());

        validNric = new Nric("S1234567A");
        validIndex = Index.fromZeroBased(0);

        // Create a medicine usage entry
        medicineUsage = new MedicineUsage("Paracetamol", "2 times a day",
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10));

        // Create a MedicalReport and add medicine usage
        MedicalReport medicalReport = new MedicalReport("", "", "", "");
        medicalReport.add(medicineUsage);

        // Assign medical report to a person
        personWithMedicineUsage = new PersonBuilder()
                .withNric(validNric.toString())
                .withMedicalReport(medicalReport)
                .build();

        model.addPerson(personWithMedicineUsage);
    }

    @Test
    void execute_personNotFound_throwsCommandException() {
        DeleteMedicineUsageCommand command = new DeleteMedicineUsageCommand(new Nric("S9999999Z"), validIndex);
        assertThrows(CommandException.class, () -> command.execute(model),
                DeleteMedicineUsageCommand.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromZeroBased(5); // Out of bounds
        DeleteMedicineUsageCommand command = new DeleteMedicineUsageCommand(validNric, invalidIndex);

        assertThrows(CommandException.class, () -> command.execute(model),
                DeleteMedicineUsageCommand.MESSAGE_INVALID_MEDICINE_USAGE_DISPLAYED_INDEX);
    }

    @Test
    void execute_validIndex_success() throws CommandException {
        DeleteMedicineUsageCommand command = new DeleteMedicineUsageCommand(validNric, validIndex);

        CommandResult result = command.execute(model);
        String expectedMessage = String.format(DeleteMedicineUsageCommand.MESSAGE_SUCCESS,
                validNric, medicineUsage.toString());

        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify medicine usage is deleted
        ObservableList<MedicineUsage> remainingUsages =
                model.findPersonByNric(validNric).getMedicalReport().getMedicineUsages();
        assertEquals(0, remainingUsages.size(), "Medicine usage should be deleted");
    }

    @Test
    void equals() {
        DeleteMedicineUsageCommand command1 = new DeleteMedicineUsageCommand(validNric, validIndex);
        DeleteMedicineUsageCommand command2 = new DeleteMedicineUsageCommand(validNric, validIndex);
        DeleteMedicineUsageCommand command3 = new DeleteMedicineUsageCommand(new Nric("S7654321B"), validIndex);

        assertEquals(command1, command2);
        assertNotEquals(command1, command3);
    }
}
