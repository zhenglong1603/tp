package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Deletes a medical report from a person identified by NRIC.
 */
public class DeleteMedicalReportCommand extends Command {

    public static final String COMMAND_WORD = "deleteMedicalReport";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a medical report from a person identified by NRIC.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A";

    public static final String MESSAGE_SUCCESS = "Medical report successfully deleted from %s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person with NRIC %s not found";
    public static final String MESSAGE_MEDICAL_REPORT_NOT_FOUND = "Medical report not found for %s";

    private final Nric nric;

    /**
     * Creates a DeleteMedicalReportCommand to remove the medical report of the specified person.
     * @param nric The NRIC of the person whose medical report is to be deleted.
     */
    public DeleteMedicalReportCommand(Nric nric) {
        requireNonNull(nric);
        this.nric = nric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person person = model.findPersonByNric(nric);
        if (person == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, nric));
        }

        if (person.getMedicalReport() == null) {
            throw new CommandException(String.format(MESSAGE_MEDICAL_REPORT_NOT_FOUND, nric));
        }

        model.deleteMedicalReport(person);
        return new CommandResult(String.format(MESSAGE_SUCCESS, nric));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteMedicalReportCommand
                && nric.equals(((DeleteMedicalReportCommand) other).nric));
    }
}
