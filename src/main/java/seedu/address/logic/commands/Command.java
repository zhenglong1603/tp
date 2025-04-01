package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.ConfirmWindow;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected boolean showConfirmation = false;
    private ConfirmWindow confirmWindow;

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Executes the command and returns the result message.
     * This method is used to show a confirmation dialog before executing the command.
     * If the user confirms the operation, the command will be executed.
     * @return CommandResult of the operation
     * @throws CommandException
     */
    public CommandResult executeCommand(Model model) throws CommandException {
        this.confirmWindow = new ConfirmWindow();
        if (showConfirmation) {
            if (confirmWindow.showAndWait()) {
                return execute(model);
            } else {
                return new CommandResult("Operation cancelled");
            }
        } else {
            return execute(model);
        }
    }

    public void setShowConfirmation(boolean showConfirmation) {
        this.showConfirmation = showConfirmation;
    }

    public boolean getShowConfirmation() {
        return showConfirmation;
    }

}
