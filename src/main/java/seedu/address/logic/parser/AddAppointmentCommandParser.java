package seedu.address.logic.parser;

import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AddMedicalReportCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMedicineUsageCommand
     * and returns an AddMedicineUsage object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_DOCTOR_NRIC,
                        PREFIX_APPOINTMENT_DESCRIPTION, PREFIX_FROM, PREFIX_TO);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC, PREFIX_DOCTOR_NRIC,
                PREFIX_APPOINTMENT_DESCRIPTION, PREFIX_FROM, PREFIX_TO)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC, PREFIX_DOCTOR_NRIC,
                PREFIX_APPOINTMENT_DESCRIPTION, PREFIX_FROM, PREFIX_TO);

        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        String doctorNric = argMultimap.getValue(PREFIX_DOCTOR_NRIC).get();
        String appointmentDescription = argMultimap.getValue(PREFIX_APPOINTMENT_DESCRIPTION).get();
        String from = argMultimap.getValue(PREFIX_FROM).get().trim();
        String to = argMultimap.getValue(PREFIX_TO).get().trim();
        LocalDateTime startDate;
        LocalDateTime endDate;
        try {
            startDate = LocalDateTime.parse(from,
                    DATE_TIME_FORMATTER);
            endDate = LocalDateTime.parse(to,
                    DATE_TIME_FORMATTER);
            if (startDate.isAfter(endDate)) {
                throw new ParseException("Start date must be before the end date!");
            }
        } catch (DateTimeParseException e) {
            throw new ParseException(
                    "Invalid date format! Please check if the date is valid and use dd-MM-yyyy HH:mm.", e);
        }

        Appointment medicineUsage = new Appointment(
                doctorNric, appointmentDescription, startDate, endDate, nric.toString());

        return new AddAppointmentCommand(nric, medicineUsage);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
