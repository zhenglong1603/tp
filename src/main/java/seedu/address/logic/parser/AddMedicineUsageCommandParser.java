package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.AddMedicineUsageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AddMedicalReportCommand object
 */
public class AddMedicineUsageCommandParser implements Parser<AddMedicineUsageCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMedicineUsageCommand
     * and returns an AddMedicineUsage object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMedicineUsageCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_NAME, PREFIX_DOSAGE, PREFIX_FROM, PREFIX_TO);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC, PREFIX_NAME, PREFIX_DOSAGE, PREFIX_FROM, PREFIX_TO)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMedicineUsageCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC, PREFIX_NAME, PREFIX_DOSAGE, PREFIX_FROM, PREFIX_TO);

        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        String name = argMultimap.getValue(PREFIX_NAME).get();
        String dosage = argMultimap.getValue(PREFIX_DOSAGE).get();
        LocalDate startDate = LocalDate.parse(argMultimap.getValue(PREFIX_FROM).get(),
                DateUtil.getDateFormatter());
        LocalDate endDate = LocalDate.parse(argMultimap.getValue(PREFIX_TO).get(),
                DateUtil.getDateFormatter());

        MedicineUsage medicineUsage = new MedicineUsage(name, dosage, startDate, endDate);

        return new AddMedicineUsageCommand(nric, medicineUsage);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
