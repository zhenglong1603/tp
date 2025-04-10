package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyKlinix;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;

/**
 * Panel containing the list of appointments.
 */
public class AppointmentListPanel extends UiPart<Region> {
    private static final String FXML = "AppointmentList.fxml";
    private final Logger logger = LogsCenter.getLogger(AppointmentListPanel.class);
    private final ReadOnlyKlinix klinix;

    @FXML
    private ListView<Appointment> appointmentListView;

    /**
     * Creates a {@code AppointmentListPanel} with the given {@code ObservableList}.
     */
    public AppointmentListPanel(ObservableList<Appointment> appointmentList, ReadOnlyKlinix klinix) {
        super(FXML);
        this.klinix = klinix;
        appointmentListView.setItems(appointmentList);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Appointment} using a {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<Appointment> {
        @Override
        protected void updateItem(Appointment appointment, boolean empty) {
            super.updateItem(appointment, empty);

            if (empty || appointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(
                        new AppointmentCard(
                                appointment, getIndex() + 1,
                                klinix.findPersonByNric(
                                        new Nric(appointment.getPatientNric()))
                                        .getName().toString())
                                .getRoot());
            }
        }
    }
}
