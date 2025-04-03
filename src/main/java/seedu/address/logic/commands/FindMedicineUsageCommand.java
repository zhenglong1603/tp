package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.MedicineUsageContainsKeywordsPredicate;

/**
 * Finds and lists all medicine usages in the Klinix whose name contains any of the argument keywords.
 * Keywords matching is case-insensitive.
 * Matching medicine usages are displayed as a list of person containing them.
 */
public class FindMedicineUsageCommand extends Command {

    public static final String COMMAND_WORD = "findmu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all medicine usages whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list of persons (users) "
            + "with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " paracetamol amoxicillin";

    private final MedicineUsageContainsKeywordsPredicate predicate;

    public FindMedicineUsageCommand(MedicineUsageContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        int count = model.getFilteredPersonList().size();
        String patientWord = (count <= 1) ? "patient" : "patients";
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, count, patientWord));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindMedicineUsageCommand)) {
            return false;
        }

        FindMedicineUsageCommand otherFindMedicineUsageCommand = (FindMedicineUsageCommand) other;
        return predicate.equals(otherFindMedicineUsageCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
