package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.ViewAppointmentByDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewappointmentByDateCommand object
 */
public class ViewAppointmentByDateParser implements Parser<ViewAppointmentByDateCommand> {

    private static final String MESSAGE_INVALID_DATE_FORMAT =
            "Please enter a valid date. The correct format is dd-MM-yyyy";

    @Override
    public ViewAppointmentByDateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewAppointmentByDateCommand.MESSAGE_USAGE));
        }
        LocalDate date = LocalDate.now();

        try {
            date = LocalDate.parse(argMultimap.getValue(PREFIX_DATE).get(), DateUtil.getDateFormatter());
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT,
                    ViewAppointmentByDateCommand.MESSAGE_USAGE));
        }

        return new ViewAppointmentByDateCommand(date);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
