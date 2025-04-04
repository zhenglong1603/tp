package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindMedicineUsageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MedicineUsageContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindMedicineUsageCommand object
 */
public class FindMedicineUsageCommandParser implements Parser<FindMedicineUsageCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMedicineUsageCommand
     * and returns a FindMedicineUsageCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindMedicineUsageCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMedicineUsageCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindMedicineUsageCommand(new MedicineUsageContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
