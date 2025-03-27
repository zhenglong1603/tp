package seedu.address.model.util;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

public class ObservableLocalDate {
    private ObjectProperty<LocalDate> ObservableLocalDate =
            new SimpleObjectProperty<>(LocalDate.now()); // Initialize with today's date

    // Getter for the property (used for binding)
    public ObjectProperty<LocalDate> dateProperty() {
        return ObservableLocalDate;
    }

    // Getter for the value
    public LocalDate getDate() {
        return ObservableLocalDate.get();
    }

    // Setter for the value
    public void setDate(LocalDate newDate) {
        ObservableLocalDate.set(newDate);
    }

}
