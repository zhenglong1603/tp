package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Controller for a confirm page
 */
public class ConfirmWindow extends UiPart<Stage> {

    public static final String CONFIRM_MESSAGE = "Are you sure about this change?";
    private static final String FXML = "ConfirmWindow.fxml";
    private boolean confirmed = false;

    public ConfirmWindow(Stage root) {
        super(FXML, root);
    }

    public ConfirmWindow() {
        this(new Stage());
    }

    /**
     * Sets up the confirm window
     */
    public boolean showAndWait() {
        getRoot().initModality(Modality.APPLICATION_MODAL);
        getRoot().setTitle("Confirmation");
        getRoot().showAndWait();
        return isConfirmed();
    }

    @FXML
    private void handleOk() {
        confirmed = true;
        getRoot().close();
    }

    @FXML
    private void handleCancel() {
        confirmed = false;
        getRoot().close();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
