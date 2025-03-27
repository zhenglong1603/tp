package seedu.address.model.util;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Wraps a {@link LocalDate} as a JavaFX property.
 */
public class ObservableLocalDate {
    private ObjectProperty<LocalDate> observableLocalDate =
            new SimpleObjectProperty<>(LocalDate.now()); // Initialize with today's date

    // Getter for the property (used for binding)
    public ObjectProperty<LocalDate> dateProperty() {
        return observableLocalDate;
    }

    // Getter for the value
    public LocalDate getDate() {
        return observableLocalDate.get();
    }

    // Setter for the value
    public void setDate(LocalDate newDate) throws NullPointerException {
        if (newDate == null) {
            throw new NullPointerException();
        }
        observableLocalDate.set(newDate);
    }

}
