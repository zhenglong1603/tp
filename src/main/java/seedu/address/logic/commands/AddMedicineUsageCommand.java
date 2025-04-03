package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Adds a medicine usage to a person by NRIC.
 */
public class AddMedicineUsageCommand extends Command {

    public static final String COMMAND_WORD = "addmu";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a medicine usage to a patient identified by NRIC.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_NAME + "MEDICINE NAME "
            + PREFIX_DOSAGE + "DOSAGE "
            + PREFIX_FROM + "START DATE (dd-MM-yyyy) "
            + PREFIX_TO + "END DATE (dd-MM-yyyy)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_NAME + "Paracetamol "
            + PREFIX_DOSAGE + "Two 500mg tablets, 4 times in 24 hours "
            + PREFIX_FROM + "23-02-2025 "
            + PREFIX_TO + "27-02-2025\n";

    public static final String MESSAGE_SUCCESS = "Medicine usage successfully added to %s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Patient with NRIC %s not found";
    public static final String MESSAGE_MEDICINE_BEFORE_BIRTHDAY = "The added medicine usage starts before the "
            + "patient's birthday!";

    private final Nric nric;
    private final MedicineUsage medicineUsage;

    /**
     * Creates an AddMedicineUsageCommand to add the specified {@code MedicineUsage}
     * to the person identified by {@code Nric}.
     */
    public AddMedicineUsageCommand(Nric nric, MedicineUsage medicineUsage) {
        requireNonNull(nric);
        requireNonNull(medicineUsage);
        this.nric = nric;
        this.medicineUsage = medicineUsage;
        super.setShowConfirmation(true);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person person = model.findPersonByNric(nric);
        if (person == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, nric));
        }

        if (!model.checkValidMedicineUsage(person, medicineUsage)) {
            throw new CommandException(String.format(MESSAGE_MEDICINE_BEFORE_BIRTHDAY));
        }

        model.addMedicineUsage(person, medicineUsage);
        return new CommandResult(String.format(MESSAGE_SUCCESS, nric));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddMedicineUsageCommand
                && nric.equals(((AddMedicineUsageCommand) other).nric)
                && medicineUsage.equals(((AddMedicineUsageCommand) other).medicineUsage));
    }
}

