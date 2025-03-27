package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class MarkAppointmentVisitedCommand extends Command {
    public static final String COMMAND_WORD = "markappt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an appointment as visited. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return null;
    }
}
