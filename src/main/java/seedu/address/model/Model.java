package seedu.address.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.MedicalReport;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.util.ObservableLocalDateTime;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getKlinixFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setKlinixFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setKlinix(ReadOnlyKlinix klinix);

    /** Returns the AddressBook */
    ReadOnlyKlinix getKlinix();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns the person with the given NRIC.
     */
    Person findPersonByNric(Nric nric);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Adds a medical report to the person.
     */
    void addMedicalReport(Person target, MedicalReport medicalReport);

    /**
     * Deletes medical report from the person.
     */
    void deleteMedicalReport(Person target);

    ObservableList<Appointment> getAppointments();

    void changeDisplayedAppointments(LocalDate date);

    /**
     * Adds a medicine usage to a person
     */
    void addMedicineUsage(Person target, MedicineUsage medicineUsage);

    /**
     * Deletes a medicine usage from a person.
     */
    void deleteMedicineUsage(Person target, MedicineUsage medicineUsage);

    /**
     * Clears all medicine usages from a person
     */
    void clearMedicineUsage(Person target);

    /**
     * Adds appointment to a person
     */
    void addAppointment(Person target, Appointment appointment);

    /**
     * Deletes a specific appointment from the person's list of appointments.
     */
    void deleteAppointment(Person target, Appointment appointmentToDelete);

    /**
     * Clears all appointments from a person
     */
    void clearAppointments(Person target);

    List<Appointment> getOverlappingAppointments(Appointment newAppointmentPerson, List<Person> allPersons);

    ObservableLocalDateTime getAppointmentListDate();

    void markAppointmentVisited(Person person, Appointment apptToMark);

    void unmarkAppointmentVisited(Person person, Appointment apptToMark);
}
