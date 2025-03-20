package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;
    private ResultDisplay resultDisplay;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, ResultDisplay resultDisplay) {
        super(FXML);
        this.resultDisplay = resultDisplay;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
                updateResultDisplay(newValue != null ? parsePersonData(newValue).toString() : "");
            }
        });
    }

    /**
     * Parses the selected person's details and updates the result display.
     */
    public static String parsePersonData(Person person) {
        requireNonNull(person);
        StringBuilder result = new StringBuilder();
        result.append("Name: ").append(person.getName()).append("\n");
        result.append("NRIC: ").append(person.getNric()).append("\n");
        result.append("Birthday: ").append(person.getBirthDate()).append("\n");
        result.append("Age: ").append(person.getAge()).append("\n");
        result.append("\n");
        result.append("Phone: ").append(person.getPhone()).append("\n");
        result.append("Email: ").append(person.getEmail()).append("\n");
        result.append("Address: ").append(person.getAddress()).append("\n");
        result.append("\n");
        result.append("Drug Allergies: ").append(person.getMedicalReport().getAllergens()).append("\n");
        result.append("Illnesses: ").append(person.getMedicalReport().getIllnesses()).append("\n");
        result.append("Surgeries: ").append(person.getMedicalReport().getSurgeries()).append("\n");
        result.append("Immunizations: ").append(person.getMedicalReport().getImmunizations()).append("\n");
        result.append("\n");

        ObservableList<MedicineUsage> list = person.getMedicalReport().getMedicineUsages();
        result.append("Medicine Usages: ").append("\n");
        for (int i = 0; i < list.size(); i++) {
            result.append(i + 1).append(": ").append(list.get(i).toString()).append("\n");
        }
        if (list.isEmpty()) {
            result.append("No medicine usages found").append("\n");
        }

        result.append("\n");
        List<Appointment> appointments = person.getAppointments();
        result.append("Appointments: ").append("\n");
        for (int i = 0; i < appointments.size(); i++) {
            result.append(i + 1).append(": ").append(appointments.get(i).toString()).append("\n");
        }

        if (appointments.isEmpty()) {
            result.append("No appointments found").append("\n");
        }
        return result.toString();
    }

    /**
     * Updates the result display with the selected person's details.
     */
    public void updateResultDisplay(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setFeedbackToUser(feedbackToUser);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
