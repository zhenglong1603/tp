package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentListByDate;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.ObservableLocalDateTime;

/**
 * Wraps all data at the klinix level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Klinix implements ReadOnlyKlinix {

    private final UniquePersonList persons;
    private AppointmentListByDate appointmentsByDate;
    private ObservableList<Appointment> displayedAppointments;
    private ObservableLocalDateTime displayedAppointmentDate;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        appointmentsByDate = new AppointmentListByDate();
        displayedAppointments = FXCollections.observableArrayList();
        displayedAppointmentDate = new ObservableLocalDateTime();
    }

    public Klinix() {}

    /**
     * Creates a Klinix using the Persons in the {@code toBeCopied}
     */
    public Klinix(ReadOnlyKlinix toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    public void setAppointmentsByDate(AppointmentListByDate appointmentsByDate) {
        this.appointmentsByDate = appointmentsByDate;
    }

    /**
     * Resets the existing data of this {@code Klinix} with {@code newData}.
     */
    public void resetData(ReadOnlyKlinix newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setAppointmentsByDate(newData.getAppointmentsByDate());
        changeDisplayedAppointments(LocalDate.now());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the klinix.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the klinix.
     * The person must not already exist in the klinix.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the klinix.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the klinix.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code Klinix}.
     * {@code key} must exist in the klinix.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Klinix)) {
            return false;
        }

        Klinix otherKlinix = (Klinix) other;
        return persons.equals(otherKlinix.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    @Override
    public Person findPersonByNric(Nric nric) {
        requireNonNull(nric);

        for (Person person : persons) {
            if (person.getNric().equals(nric)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Adds a list of appointments to the klinix.
     * @param appointments
     */
    public void addAppointment(List<Appointment> appointments) {
        for (Appointment a : appointments) {
            this.appointmentsByDate.addAppointment(a);
        }
        refreshDisplayedAppointments();
    }

    /**
     * Adds an appointment to the klinix.
     * @param appointment
     */
    public void addAppointment(Appointment appointment) {
        this.appointmentsByDate.addAppointment(appointment);
        refreshDisplayedAppointments();
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the klinix.
     * The appointment identity of {@code editedAppointment} must not be the same as another existing appointment
     * in the klinix.
     */
    public void replaceAppointment(Appointment target, Appointment editedAppointment) {
        this.appointmentsByDate.replaceAppointment(target, editedAppointment);
        refreshDisplayedAppointments();
    }

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    public ObservableList<Appointment> getAppointmentsListByDate(LocalDate date) {
        this.displayedAppointments = this.appointmentsByDate.getAppointmentListByDate(date);
        return this.displayedAppointments;
    }

    /**
     * Changes the displayed appointments to the appointments on the given date.
     * @param date
     */
    public void changeDisplayedAppointments(LocalDate date) {
        this.displayedAppointmentDate.setDate(date);
        this.displayedAppointments.clear();
        this.displayedAppointments.addAll(this.appointmentsByDate.getAppointmentListByDate(date));
    }

    /**
     * Refreshes the displayed appointments to the appointments on the current displayed date.
     */
    public void refreshDisplayedAppointments() {
        changeDisplayedAppointments(displayedAppointmentDate.getDate().toLocalDate());
    }

    public ObservableList<Appointment> getDisplayedAppointments() {
        return this.displayedAppointments;
    }

    public AppointmentListByDate getAppointmentsByDate() {
        return this.appointmentsByDate;
    }

    /**
     * Deletes the appointment from the klinix.
     * @param appointmentToDelete
     */
    public void deleteAppointment(Appointment appointmentToDelete) {
        this.appointmentsByDate.deleteAppointment(appointmentToDelete);
        refreshDisplayedAppointments();
    }

    public ObservableLocalDateTime getAppointmentListDate() {
        return this.displayedAppointmentDate;
    }

    public ObservableList<MedicineUsage> getMedicineUsageListFromPersons() {
        ObservableList<Person> targetPersons = this.persons.asUnmodifiableObservableList();
        return targetPersons.stream()
                .flatMap(person -> person.getMedicineUsages().stream())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * updates the appointment lists with the new appointment data from the person.
     */
    public void updateAppointments(Person personToEdit, Person editedPerson) {
        List<Appointment> toBeReplaced = personToEdit.getAppointments();
        List<Appointment> toBeAdded = editedPerson.getAppointments();
        for (Appointment a : toBeReplaced) {
            replaceAppointment(a, toBeAdded.get(toBeReplaced.indexOf(a)));
        }
    }
}
