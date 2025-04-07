package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Deletes an appointment of a person by unique index.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "deleteappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an appointment of a patient given their NRIC and the index number displayed "
            + "in the patient's details.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_NRIC + "NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 " + PREFIX_NRIC + "S1234567A\n";

    public static final String MESSAGE_SUCCESS = "From patient %s, successfully deleted appointment:\n%s";
    public static final String MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX = "Index is out of bounds.";
    public static final String MESSAGE_EMPTY_APPOINTMENT = "Patient has no existing appointments!";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Patient with NRIC %s not found";

    private final Index targetId;
    private final Nric nric;

    /**
     * Creates an DeleteAppointmentCommand to delete a particular appointment of
     * the person identified by {@code Nric}.
     */
    public DeleteAppointmentCommand(Nric nric, Index targetId) {
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

        List<Appointment> appointmentList = person.getAppointments();
        int appointmentCount = appointmentList.size();
        if (appointmentCount == 0) {
            throw new CommandException(String.format(MESSAGE_EMPTY_APPOINTMENT));
        }

        if (targetId.getZeroBased() >= appointmentList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX));
        }

        Appointment apptToDelete = appointmentList.get(targetId.getZeroBased());
        model.deleteAppointment(person, apptToDelete);


        return new CommandResult(String.format(MESSAGE_SUCCESS, nric, apptToDelete.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteAppointmentCommand
                && nric.equals(((DeleteAppointmentCommand) other).nric));
    }
}
