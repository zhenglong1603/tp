package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Clears all medicine usages of a person by NRIC.
 */
public class ClearMedicineUsageCommand extends Command {

    public static final String COMMAND_WORD = "clearmu";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all medicine usages of a patient identified by NRIC.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A\n";

    public static final String MESSAGE_SUCCESS_MEDICINE = "Medicine usage successfully deleted from %s";
    public static final String MESSAGE_SUCCESS_MEDICINES = "Medicine usages successfully deleted from %s";
    public static final String MESSAGE_NO_MEDICINE = "Person with NRIC %s has no medicine usages to clear!";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Patient with NRIC %s not found";

    private final Nric nric;

    /**
     * Creates an ClearMedicineUsageCommand to delete all medicine usages of
     * the person identified by {@code Nric}.
     */
    public ClearMedicineUsageCommand(Nric nric) {
        requireNonNull(nric);
        this.nric = nric;
        super.setShowConfirmation(true);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person person = model.findPersonByNric(nric);
        if (person == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, nric));
        }

        int medicineCount = person.getMedicalReport().getMedicineUsages().size();
        if (medicineCount == 0) {
            return new CommandResult(String.format(MESSAGE_NO_MEDICINE, nric));
        } else if (medicineCount == 1) {
            model.clearMedicineUsage(person);
            return new CommandResult(String.format(MESSAGE_SUCCESS_MEDICINE, nric));
        } else {
            model.clearMedicineUsage(person);
            return new CommandResult(String.format(MESSAGE_SUCCESS_MEDICINES, nric));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ClearMedicineUsageCommand
                && nric.equals(((ClearMedicineUsageCommand) other).nric));
    }
}
