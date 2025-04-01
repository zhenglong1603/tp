package seedu.address.testutil;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.util.ObservableLocalDateTime;

class ObservableLocalDateTimeTest {

    @Test
    void constructor_default_initializesWithCurrentDate() {
        ObservableLocalDateTime observableDate = new ObservableLocalDateTime();
        assertNotNull(observableDate.getDate());
    }

    @Test
    void dateProperty_returnsCorrectProperty() {
        ObservableLocalDateTime observableDateTime = new ObservableLocalDateTime();
        ObjectProperty<LocalDateTime> property = observableDateTime.dateProperty();
        assertNotNull(property);
        assertEquals(observableDateTime.getDate(), property.get());
    }

    @Test
    void getDate_returnsCurrentDateByDefault() {
        ObservableLocalDateTime observableDate = new ObservableLocalDateTime();
        LocalDateTime expectedDate = LocalDateTime.now();
        LocalDateTime actualDate = observableDate.getDate();

        // Allow a margin of 1 second for initialization differences
        assertTrue(expectedDate.isBefore(actualDate.plusSeconds(1))
                && expectedDate.isAfter(actualDate.minusSeconds(1)));
    }

    @Test
    void setDate_updatesDateCorrectly() {
        ObservableLocalDateTime observableDate = new ObservableLocalDateTime();
        LocalDate newDate = LocalDate.of(2023, 12, 25);

        observableDate.setDate(newDate);

        LocalDateTime expectedDateTime = newDate.atStartOfDay();
        assertEquals(expectedDateTime, observableDate.getDate());
        assertEquals(expectedDateTime, observableDate.dateProperty().get());
    }

    @Test
    void setDate_withNull_throwsException() {
        ObservableLocalDateTime observableDate = new ObservableLocalDateTime();
        assertThrows(NullPointerException.class, () -> observableDate.setDate(null));
    }

    void propertyChange_notifiesListeners() {
        ObservableLocalDateTime observableDate = new ObservableLocalDateTime();

        // Get the initial date (as LocalDateTime) from the observableDate
        LocalDateTime initialDateTime = observableDate.getDate();

        // Define the new date as LocalDateTime (use .atStartOfDay() to get midnight time)
        LocalDateTime newDateTime = LocalDate.of(2024, 1, 1).atStartOfDay();

        final boolean[] listenerCalled = {false};

        observableDate.dateProperty().addListener((obs, oldVal, newVal) -> {
            // Compare the LocalDateTime values correctly
            assertEquals(initialDateTime, oldVal);
            assertEquals(newDateTime, newVal);
            listenerCalled[0] = true;
        });

        // Update the observable date
        observableDate.setDate(LocalDate.of(2024, 1, 1));

        // Check if the listener was called
        assertTrue(listenerCalled[0], "Listener was not called");
    }

    @Test
    void multipleSetDate_worksCorrectly() {
        ObservableLocalDateTime observableDate = new ObservableLocalDateTime();
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2023, 6, 15);

        observableDate.setDate(date1);
        assertEquals(date1, observableDate.getDate().toLocalDate());

        observableDate.setDate(date2);
        assertEquals(date2, observableDate.getDate().toLocalDate());

        assertNotEquals(date1, observableDate.getDate().toLocalDate());
    }
}

