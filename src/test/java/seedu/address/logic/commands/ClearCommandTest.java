package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalKlinix;

import org.junit.jupiter.api.Test;

import seedu.address.model.Klinix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyKlinix_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyKlinix_success() {
        Model model = new ModelManager(getTypicalKlinix(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalKlinix(), new UserPrefs());
        expectedModel.setKlinix(new Klinix());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
