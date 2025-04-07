package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.DeleteAppointmentCommand.MESSAGE_PERSON_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Marks an appointment as visited.
 */
public class UnmarkAppointmentVisitedCommand extends Command {
    public static final String COMMAND_WORD = "unmarkappt";
    public static final String MESSAGE_SUCCESS = "Marked appointment as not visited for person %s:\n%s";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "Index is out of bounds.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an appointment as not visited. \n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_NRIC + "[NRIC]'\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NRIC + "S1234567A";

    private final Index index;
    private final Nric nric;

    /**
     * Creates a MarkAppointmentVisitedCommand to mark the specified {@code Appointment} as not visited.
     */
    public UnmarkAppointmentVisitedCommand(Nric nric, Index index) {
        super();
        this.index = index;
        this.nric = nric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person person = model.findPersonByNric(nric);
        if (person == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, nric));
        }

        List<Appointment> appointmentList = person.getAppointments();

        if (index.getZeroBased() >= appointmentList.size()) {
            throw new CommandException(String.format(MESSAGE_INDEX_OUT_OF_BOUNDS));
        }

        Appointment apptToMark = appointmentList.get(index.getZeroBased());

        if (!apptToMark.getVisited()) {
            throw new CommandException("Appointment has already been marked as not visited.");
        }

        model.unmarkAppointmentVisited(person, apptToMark);

        return new CommandResult(String.format(MESSAGE_SUCCESS, nric, apptToMark.toString()));
    }
}
