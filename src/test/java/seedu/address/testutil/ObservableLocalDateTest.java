package seedu.address.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.util.ObservableLocalDate;

class ObservableLocalDateTest {

    @Test
    void constructor_default_initializesWithCurrentDate() {
        ObservableLocalDate observableDate = new ObservableLocalDate();
        assertNotNull(observableDate.getDate());
    }

    @Test
    void dateProperty_returnsCorrectProperty() {
        ObservableLocalDate observableDate = new ObservableLocalDate();
        ObjectProperty<LocalDate> property = observableDate.dateProperty();
        assertNotNull(property);
        assertEquals(observableDate.getDate(), property.get());
    }

    @Test
    void getDate_returnsCurrentDateByDefault() {
        ObservableLocalDate observableDate = new ObservableLocalDate();
        LocalDate expectedDate = LocalDate.now();
        assertEquals(expectedDate, observableDate.getDate());
    }

    @Test
    void setDate_updatesDateCorrectly() {
        ObservableLocalDate observableDate = new ObservableLocalDate();
        LocalDate newDate = LocalDate.of(2023, 12, 25);

        observableDate.setDate(newDate);

        assertEquals(newDate, observableDate.getDate());
        assertEquals(newDate, observableDate.dateProperty().get());
    }

    @Test
    void setDate_withNull_throwsException() {
        ObservableLocalDate observableDate = new ObservableLocalDate();
        try {
            observableDate.setDate(null);
        } catch (NullPointerException e) {
            // Expected behavior
            return;
        }
        // If we get here, the test failed
        throw new AssertionError("Expected NullPointerException was not thrown");
    }

    @Test
    void propertyChange_notifiesListeners() {
        ObservableLocalDate observableDate = new ObservableLocalDate();
        LocalDate initialDate = observableDate.getDate();
        LocalDate newDate = LocalDate.of(2024, 1, 1);

        final boolean[] listenerCalled = {false};

        observableDate.dateProperty().addListener((obs, oldVal, newVal) -> {
            assertEquals(initialDate, oldVal);
            assertEquals(newDate, newVal);
            listenerCalled[0] = true;
        });

        observableDate.setDate(newDate);

        assertEquals(true, listenerCalled[0], "Listener was not called");
    }

    @Test
    void multipleSetDate_worksCorrectly() {
        ObservableLocalDate observableDate = new ObservableLocalDate();
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2023, 6, 15);

        observableDate.setDate(date1);
        assertEquals(date1, observableDate.getDate());

        observableDate.setDate(date2);
        assertEquals(date2, observableDate.getDate());

        assertNotEquals(date1, observableDate.getDate());
    }
}
