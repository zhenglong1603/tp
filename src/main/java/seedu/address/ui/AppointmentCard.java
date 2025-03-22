package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;

import javax.smartcardio.Card;

public class AppointmentCard extends UiPart<Region> {
    private static final String FXML = "AppointmentCard.fxml";
    public final Appointment appointment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label endDate;
    @FXML
    private Label startDate;
    @FXML
    private Label doctorNric;
    @FXML
    private Label description;

    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        doctorNric.setText(appointment.getDoctorNric());
        description.setText(appointment.getDescription());
        startDate.setText(appointment.getStartDate().toString());
        endDate.setText(appointment.getEndDate().toString());
    }
}
