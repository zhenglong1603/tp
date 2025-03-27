package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

public class ViewappointmentByDateCommand extends Command {
    public static final String COMMAND_WORD = "appton";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View appointments by date. "
            + "Parameters: "
            + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE +  "23-03-2025";

    public static final String MESSAGE_SUCCESS = "Listed all appointments on %1$s";
    public static final String MESSAGE_NO_APPOINTMENT = "No appointments on %1$s";
    private final LocalDate localDate;

    public ViewappointmentByDateCommand(LocalDate localDate) {
        this.localDate = localDate;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.changeDisplayedAppointments(localDate);

        return new CommandResult(String.format(MESSAGE_SUCCESS, localDate));
    }
}
