package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.List;

import seedu.address.commons.util.AppointmentConflictFormatter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Adds an appointment to a person by NRIC.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "addappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an appointment to a patient identified by NRIC.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_APPOINTMENT_DESCRIPTION + "APPOINTMENT_TYPE "
            + PREFIX_FROM + "START_DATE "
            + PREFIX_TO + "END_DATE \n"
            + "Date is of the following format: dd-MM-yyyy HH:mm \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_APPOINTMENT_DESCRIPTION + "Check-Up "
            + PREFIX_FROM + "22-02-2025 11:00 "
            + PREFIX_TO + "22-02-2025 11:30\n";

    public static final String MESSAGE_SUCCESS = "Appointment successfully added to %s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Patient with NRIC %s not found";
    public static final String MESSAGE_APPOINTMENT_BEFORE_BIRTHDAY = "The added appointment starts before the "
            + "patient's birthday!";

    private final Nric nric;
    private final Appointment appointment;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     * to the person identified by {@code Nric}.
     */
    public AddAppointmentCommand(Nric nric, Appointment appointment) {
        requireNonNull(nric);
        requireNonNull(appointment);
        this.nric = nric;
        this.appointment = appointment;
        super.setShowConfirmation(true);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, OverlappingAppointmentException {
        requireNonNull(model);
        List<Person> allPersons = model.getFilteredPersonList();

        Person person = model.findPersonByNric(nric);
        if (person == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, nric));
        }

        if (!model.checkValidAppointment(person, appointment)) {
            throw new CommandException(String.format(MESSAGE_APPOINTMENT_BEFORE_BIRTHDAY));
        }

        List<Appointment> overlappingAppointments = model.getOverlappingAppointments(appointment, allPersons);

        List<Appointment> patientConflicts = overlappingAppointments.stream()
                .filter(existingAppointment ->
                        existingAppointment.getPatientNric().equals(appointment.getPatientNric()))
                .toList();

        if (!patientConflicts.isEmpty()) {
            throw new OverlappingAppointmentException(
                    AppointmentConflictFormatter.formatConflicts("Patient", patientConflicts, model)
            );
        }

        model.addAppointment(person, appointment);
        return new CommandResult(String.format(MESSAGE_SUCCESS, nric));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAppointmentCommand
                && nric.equals(((AddAppointmentCommand) other).nric)
                && appointment.equals(((AddAppointmentCommand) other).appointment));
    }

    @Override
    public String toString() {
        return String.format("AddAppointmentCommand{nric=%s, appointment=%s}", nric, appointment);
    }
}

