package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Klinix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MedicineUsageContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;

public class FindMedicineUsageCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new Klinix(), new UserPrefs());
        expectedModel = new ModelManager(new Klinix(), new UserPrefs());

        model.addPerson(new PersonBuilder().withName("Alice").build());
        expectedModel.addPerson(new PersonBuilder().withName("Alice").build());
    }

    @Test
    public void execute_zeroKeywords_noMatch() {
        MedicineUsageContainsKeywordsPredicate predicate =
                new MedicineUsageContainsKeywordsPredicate(Collections.singletonList("paracetamol"));
        FindMedicineUsageCommand command = new FindMedicineUsageCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);

        CommandResult result = command.execute(model);

        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
        assertEquals(String.format("%d persons listed!", model.getFilteredPersonList().size()),
                result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        MedicineUsageContainsKeywordsPredicate firstPredicate =
                new MedicineUsageContainsKeywordsPredicate(Collections.singletonList("paracetamol"));
        MedicineUsageContainsKeywordsPredicate secondPredicate =
                new MedicineUsageContainsKeywordsPredicate(Collections.singletonList("ibuprofen"));

        FindMedicineUsageCommand firstCommand = new FindMedicineUsageCommand(firstPredicate);
        FindMedicineUsageCommand secondCommand = new FindMedicineUsageCommand(secondPredicate);

        assertEquals(firstCommand, new FindMedicineUsageCommand(firstPredicate));
        assertNotEquals(null, firstCommand);
        assertNotEquals(firstCommand, secondCommand);
    }
}
