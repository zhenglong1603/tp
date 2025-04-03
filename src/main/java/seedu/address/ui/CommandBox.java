package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.ICommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final ICommandHistory commandHistory = new CommandHistory();
    private String partiallyTypedCommand = "";

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty()
                .addListener((unused1, unused2, unused3) -> setStyleToDefault());

        // if the user types a character, the command history pointer should be set to the end
        commandTextField.setOnKeyTyped(event -> {
            if (event.getCharacter() != null) {
                commandHistory.setPointerToEnd();
            }
        });

        // event listener to handle up and down arrow key presses for browsing command history
        commandTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case UP:
                if (this.commandHistory.isEmpty() || this.commandHistory.isPointerAtStart()) {
                    break;
                }
                if (this.commandHistory.isPointerAtEnd()) {
                    partiallyTypedCommand = commandTextField.getText();
                }
                String previousCommand = commandHistory.getPrevious();
                if (previousCommand == null) {
                    previousCommand = "";
                }
                commandTextField.setText(previousCommand);
                commandTextField.positionCaret(previousCommand.length());
                break;
            case DOWN:
                if (this.commandHistory.isEmpty() || this.commandHistory.isPointerAtEnd()) {
                    break;
                }
                String nextCommand = commandHistory.getNext();
                if (nextCommand == null) {
                    nextCommand = "";
                }
                if (commandHistory.isPointerAtEnd()) {
                    commandTextField.setText(partiallyTypedCommand);
                    commandTextField.positionCaret(partiallyTypedCommand.length());
                } else {
                    commandTextField.setText(nextCommand);
                    commandTextField.positionCaret(nextCommand.length());
                }
                break;
            default:
                break;
            }
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        if (!commandText.isBlank()) {
            commandHistory.add(commandText);
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText(""); // this is making the revert style revert
        } catch (CommandException | ParseException e) {
            commandTextField.setText(""); // this is making the revert style revert
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
