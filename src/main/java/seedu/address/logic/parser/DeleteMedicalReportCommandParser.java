package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMedicalReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;



/**
 * Parses input arguments and creates a new DeleteMedicalReportCommand object
 */
public class DeleteMedicalReportCommandParser implements Parser<DeleteMedicalReportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMedicalReportCommand
     * and returns a DeleteMedicalReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMedicalReportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC);
        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC)) {
            return parseByIndex(args);
        } else {
            return parseByNric(args);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMedicalReportCommand
     * and returns an DeleteMedicalReportCommand object for execution.
     * Expects the format uses index.
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteMedicalReportCommand parseByIndex(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMedicalReportCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMedicalReportCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMedicalReportCommand
     * and returns an DeleteMedicalReportCommand object for execution.
     * Expects the format uses nric.
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteMedicalReportCommand parseByNric(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteMedicalReportCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC);

        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());

        return new DeleteMedicalReportCommand(nric);
    }



    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
