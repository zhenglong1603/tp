package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Clears all appointments of a person by NRIC.
 */
public class ClearAppointmentsCommand extends Command {

    public static final String COMMAND_WORD = "clearappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all appointments of a person identified by NRIC.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A\n";

    public static final String MESSAGE_SUCCESS = "Appointments successfully deleted from %s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person with NRIC %s not found";

    private final Nric nric;

    /**
     * Creates an ClearAppointmentsCommand to delete all appointments of
     * the person identified by {@code Nric}.
     */
    public ClearAppointmentsCommand(Nric nric) {
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

        model.clearAppointments(person);
        return new CommandResult(String.format(MESSAGE_SUCCESS, nric));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ClearAppointmentsCommand
                && nric.equals(((ClearAppointmentsCommand) other).nric));
    }
}
