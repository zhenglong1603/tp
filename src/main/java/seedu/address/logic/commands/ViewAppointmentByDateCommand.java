package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.DateUtil.DATE_DISPLAY_FORMATTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * View appointments by date
 */
public class ViewAppointmentByDateCommand extends Command {
    public static final String COMMAND_WORD = "appton";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View appointments by date. \n"
            + "Parameter: "
            + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "23-03-2025";

    public static final String MESSAGE_SUCCESS = "Listed all appointments on %1$s";
    public static final String MESSAGE_NO_APPOINTMENT = "No appointments on %1$s";
    private final LocalDate localDate;

    public ViewAppointmentByDateCommand(LocalDate localDate) {
        this.localDate = localDate;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.changeDisplayedAppointments(localDate);
        if (model.isAppointmentListEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_APPOINTMENT, localDate.format(DATE_DISPLAY_FORMATTER)));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, localDate.format(DATE_DISPLAY_FORMATTER)));
    }
}
