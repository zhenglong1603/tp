package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.AppointmentListByDate;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an klinix
 */
public interface ReadOnlyKlinix {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns the person with the given {@code nric}.
     *
     */
    Person findPersonByNric(Nric nric);

    AppointmentListByDate getAppointmentsByDate();
}
