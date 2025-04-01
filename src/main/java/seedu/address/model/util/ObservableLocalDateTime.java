package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Wraps a {@link LocalDateTime} as a JavaFX property.
 */
public class ObservableLocalDateTime {
    private ObjectProperty<LocalDateTime> observableLocalDateTime =
            new SimpleObjectProperty<>(LocalDateTime.now()); // Initialize with today's date

    // Getter for the property (used for binding)
    public ObjectProperty<LocalDateTime> dateProperty() {
        return observableLocalDateTime;
    }

    // Getter for the value
    public LocalDateTime getDate() {
        return observableLocalDateTime.get();
    }

    // Setter for the value
    public void setDate(LocalDate newDate) throws NullPointerException {
        if (newDate == null) {
            throw new NullPointerException();
        }
        observableLocalDateTime.set(newDate.atStartOfDay());
    }

}
