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

    public static final String MESSAGE_SUCCESS_APPOINTMENT = "Appointment successfully deleted from %s";
    public static final String MESSAGE_SUCCESS_APPOINTMENTS = "Appointments successfully deleted from %s";
    public static final String MESSAGE_NO_APPOINTMENT = "No appointments to clear!";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Patient with NRIC %s not found";

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

        int appointmentCount = person.getAppointments().size();
        if (appointmentCount == 0) {
            return new CommandResult(MESSAGE_NO_APPOINTMENT);
        } else if (appointmentCount == 1) {
            model.clearAppointments(person);
            return new CommandResult(String.format(MESSAGE_SUCCESS_APPOINTMENT, nric));
        } else {
            model.clearAppointments(person);
            return new CommandResult(String.format(MESSAGE_SUCCESS_APPOINTMENTS, nric));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ClearAppointmentsCommand
                && nric.equals(((ClearAppointmentsCommand) other).nric));
    }
}
