package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ILLNESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMMUNIZATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SURGERY;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.MedicalReport;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Adds a medical report to a person identified by NRIC.
 */
public class AddMedicalReportCommand extends Command {

    public static final String COMMAND_WORD = "addmr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a medical report to a patient identified by NRIC.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_ALLERGY + "ALLERGY "
            + PREFIX_ILLNESS + "ILLNESS "
            + PREFIX_SURGERY + "SURGERY "
            + PREFIX_IMMUNIZATION + "IMMUNIZATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_ALLERGY + "Pollen "
            + PREFIX_ILLNESS + "Fever "
            + PREFIX_SURGERY + "Appendectomy "
            + PREFIX_IMMUNIZATION + "Flu";

    public static final String MESSAGE_SUCCESS = "Medical report successfully added to patient with NRIC %s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Patient with NRIC %s not found";

    private final Nric nric;
    private final MedicalReport medicalReport;

    /**
     * Creates an AddMedicalReportCommand to add the specified {@code MedicalReport}
     * to the person identified by {@code Nric}.
     */
    public AddMedicalReportCommand(Nric nric, MedicalReport medicalReport) {
        requireNonNull(nric);
        requireNonNull(medicalReport);
        this.nric = nric;
        this.medicalReport = medicalReport;
        super.setShowConfirmation(true);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person person = model.findPersonByNric(nric);
        if (person == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, nric));
        }

        if (medicalReport.isEmpty()) {
            throw new CommandException("There must be at least one field in the medical report.");
        }

        model.addMedicalReport(person, medicalReport);
        return new CommandResult(String.format(MESSAGE_SUCCESS, nric));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddMedicalReportCommand
                && nric.equals(((AddMedicalReportCommand) other).nric)
                && medicalReport.equals(((AddMedicalReportCommand) other).medicalReport));
    }
}
