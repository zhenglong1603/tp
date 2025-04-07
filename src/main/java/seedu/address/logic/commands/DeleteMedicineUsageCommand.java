package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Deletes a medicine usage of a person identified by Nric and the index number displayed in the person's details.
 */
public class DeleteMedicineUsageCommand extends Command {

    public static final String COMMAND_WORD = "deletemu";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a medicine usage of a patient given their NRIC and the index number displayed "
            + "in the patient's details.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_NRIC + "NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 " + PREFIX_NRIC + "S1234567A\n";

    public static final String MESSAGE_SUCCESS = "From patient %s, successfully deleted medicine usage:\n%s";
    public static final String MESSAGE_INVALID_MEDICINE_USAGE_DISPLAYED_INDEX = "Index is out of bounds.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Patient with NRIC %s not found";
    public static final String MESSAGE_EMPTY_MEDICINE_USAGES = "Patient has no existing medicine usages";

    private final Index targetId;
    private final Nric nric;

    /**
     * Creates an DeleteMedicineUsageCommand to delete a medicine usage of
     * the person identified by {@code Nric} at index {@code Index}.
     */
    public DeleteMedicineUsageCommand(Nric nric, Index targetId) {
        requireNonNull(targetId);
        this.nric = nric;
        this.targetId = targetId;
        super.setShowConfirmation(true);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person person = model.findPersonByNric(nric);
        if (person == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, nric));
        }

        ObservableList<MedicineUsage> medicineUsageList = person.getMedicineUsages();

        if (medicineUsageList.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_MEDICINE_USAGES);
        }

        if (targetId.getZeroBased() >= medicineUsageList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_MEDICINE_USAGE_DISPLAYED_INDEX));
        }

        MedicineUsage medUsageToDelete = medicineUsageList.get(targetId.getZeroBased());
        model.deleteMedicineUsage(person, medUsageToDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS, nric, medUsageToDelete.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteMedicineUsageCommand
                && nric.equals(((DeleteMedicineUsageCommand) other).nric));
    }
}
