package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;

/**
 * A UI component that displays information of a {@code Appointment}.
 */
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
    @FXML
    private Label patientNric;
    @FXML
    private Label patientName;

    /**
     * Creates a {@code AppointmentCard} with the given {@code Appointment} and index to display.
     */
    public AppointmentCard(Appointment appointment, int displayedIndex, String pn) {
        super(FXML);
        this.appointment = appointment;
        doctorNric.setText(appointment.getDoctorNric());
        description.setText(appointment.getDescription());
        startDate.setText(appointment.getStartDate().toString());
        endDate.setText(appointment.getEndDate().toString());
        patientName.setText(pn);
        patientNric.setText(appointment.getPatientNric());
    }
}
