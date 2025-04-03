package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Clears all appointments of a person by NRIC or index.
 */
public class ClearAppointmentsCommand extends Command {

    public static final String COMMAND_WORD = "clearappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all appointments of a person identified by NRIC, OR by the index number used in the "
            + "displayed patient list. However, it cannot be both NRIC and index.\n"
            + "Parameters for first method: "
            + PREFIX_NRIC + "NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A\n"
            + "Parameters for second method: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS_APPOINTMENT_NRIC = "Appointment successfully deleted from %s";
    public static final String MESSAGE_SUCCESS_APPOINTMENT_ID = "Appointment successfully deleted from person"
            + " at index %d";
    public static final String MESSAGE_SUCCESS_APPOINTMENTS_NRIC = "Appointments successfully deleted from %s";
    public static final String MESSAGE_SUCCESS_APPOINTMENTS_ID = "Appointments successfully deleted from person"
            + " at index %d";
    public static final String MESSAGE_NO_APPOINTMENT = "No appointments to clear!";
    public static final String MESSAGE_PERSON_NOT_FOUND_NRIC = "Patient with NRIC %s not found";
    public static final String MESSAGE_PERSON_NOT_FOUND_ID = "Patient at index %d not found";

    private final Nric nric;
    private final Index targetIndex;

    /**
     * Creates a ClearAppointmentsCommand to delete all appointments of
     * the person identified by {@code Nric}.
     */
    public ClearAppointmentsCommand(Nric nric) {
        requireNonNull(nric);
        this.nric = nric;
        this.targetIndex = null;
        super.setShowConfirmation(true);
    }

    /**
     * Creates a ClearAppointmentsCommand to delete all appointments of
     * the person specified at {@code Index}.
     */
    public ClearAppointmentsCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.nric = null;
        super.setShowConfirmation(true);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (nric != null) {
            return executeByNric(nric, model);
        } else {
            return executeByIndex(targetIndex, model);
        }
    }

    /**
     * Executes the command given nric and model
     * @param nric NRIC of the patient
     * @param model Model that contains the patient
     * @return Result of command execution
     * @throws CommandException if error occurs during execution
     */
    private CommandResult executeByNric(Nric nric, Model model) throws CommandException {
        requireAllNonNull(nric, model);
        Person person = model.findPersonByNric(nric);

        if (person == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND_NRIC, nric));
        }

        int appointmentCount = person.getAppointments().size();
        if (appointmentCount == 0) {
            return new CommandResult(MESSAGE_NO_APPOINTMENT);
        } else if (appointmentCount == 1) {
            model.clearAppointments(person);
            return new CommandResult(String.format(MESSAGE_SUCCESS_APPOINTMENT_NRIC, nric));
        } else {
            model.clearAppointments(person);
            return new CommandResult(String.format(MESSAGE_SUCCESS_APPOINTMENTS_NRIC, nric));
        }
    }

    /**
     * Executes the command given index and model
     * @param targetIndex Index of the person to execute on
     * @param model Model that contains the person
     * @return Results of command execution
     * @throws CommandException if error occurs during execution
     */
    private CommandResult executeByIndex(Index targetIndex, Model model) throws CommandException {
        requireAllNonNull(targetIndex, model);

        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person person = lastShownList.get(targetIndex.getZeroBased());

        if (person == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND_ID, targetIndex.getOneBased()));
        }

        int appointmentCount = person.getAppointments().size();
        if (appointmentCount == 0) {
            return new CommandResult(MESSAGE_NO_APPOINTMENT);
        } else if (appointmentCount == 1) {
            model.clearAppointments(person);
            return new CommandResult(String.format(MESSAGE_SUCCESS_APPOINTMENT_ID, targetIndex.getOneBased()));
        } else {
            model.clearAppointments(person);
            return new CommandResult(String.format(MESSAGE_SUCCESS_APPOINTMENTS_ID, targetIndex.getOneBased()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof ClearAppointmentsCommand)) {
            return false;
        }

        ClearAppointmentsCommand otherCommand = (ClearAppointmentsCommand) other;

        // Compare nric-based commands
        if (this.nric != null && otherCommand.nric != null) {
            return this.nric.equals(otherCommand.nric);
        }

        // Compare index-based commands
        if (this.targetIndex != null && otherCommand.targetIndex != null) {
            return this.targetIndex.equals(otherCommand.targetIndex);
        }

        // One uses nric, the other uses index
        return false;
    }
}
