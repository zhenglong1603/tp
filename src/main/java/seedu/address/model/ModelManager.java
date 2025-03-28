package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentList;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.MedicalReport;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.util.ObservableLocalDate;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Klinix klinix;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyKlinix klinix, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(klinix, userPrefs);

        logger.fine("Initializing with address book: " + klinix + " and user prefs " + userPrefs);

        this.klinix = new Klinix(klinix);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.klinix.getPersonList());
    }

    public ModelManager() {
        this(new Klinix(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getKlinixFilePath() {
        return userPrefs.getKlinixFilePath();
    }

    @Override
    public void setKlinixFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setKlinixFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setKlinix(ReadOnlyKlinix klinix) {
        this.klinix.resetData(klinix);
    }

    @Override
    public ReadOnlyKlinix getKlinix() {
        return klinix;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return klinix.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        klinix.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        klinix.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        klinix.setPerson(target, editedPerson);
    }

    @Override
    public Person findPersonByNric(Nric nric) {
        requireNonNull(nric);
        return klinix.findPersonByNric(nric);
    }



    //=========== Medical Report =============================================================
    @Override
    public void addMedicalReport(Person target, MedicalReport medicalReport) {
        requireAllNonNull(target, medicalReport);

        Person updatedPerson = new Person(
                target.getName(),
                target.getPhone(),
                target.getEmail(),
                target.getNric(),
                target.getBirthDate(),
                target.getAddress(),
                target.getTags(),
                medicalReport,
                target.getAppointmentList()
        );

        klinix.setPerson(target, updatedPerson);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void deleteMedicalReport(Person target) {
        requireNonNull(target);

        Person updatedPerson = new Person(
                target.getName(),
                target.getPhone(),
                target.getEmail(),
                target.getNric(),
                target.getBirthDate(),
                target.getAddress(),
                target.getTags(),
                MedicalReport.EMPTY_MEDICAL_REPORT,
                target.getAppointmentList()
        );

        klinix.setPerson(target, updatedPerson);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
    //=========== Appointment =============================================================
    @Override
    public void addAppointment(Person target, Appointment appointment) {
        requireAllNonNull(target, appointment);

        AppointmentList updatedAppointments = target.getAppointmentList(); // Get current appointments
        updatedAppointments.add(appointment); // Add the new appointment

        Person updatedPerson = new Person(
                target.getName(),
                target.getPhone(),
                target.getEmail(),
                target.getNric(),
                target.getBirthDate(),
                target.getAddress(),
                target.getTags(),
                target.getMedicalReport(),
                updatedAppointments // Use updated list of appointments
        );

        klinix.setPerson(target, updatedPerson);
        klinix.addAppointment(appointment);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }


    @Override
    public void deleteAppointment(Person target, Appointment appointmentToDelete) {
        requireAllNonNull(target, appointmentToDelete);

        AppointmentList updatedAppointments = target.getAppointmentList(); // Get current appointments
        updatedAppointments.remove(appointmentToDelete); // Remove the specific appointment

        Person updatedPerson = new Person(
                target.getName(),
                target.getPhone(),
                target.getEmail(),
                target.getNric(),
                target.getBirthDate(),
                target.getAddress(),
                target.getTags(),
                target.getMedicalReport(),
                updatedAppointments // Use updated list of appointments
        );

        klinix.setPerson(target, updatedPerson);
        klinix.deleteAppointment(appointmentToDelete);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void clearAppointments(Person target) {
        requireNonNull(target);
        AppointmentList appointmentList = target.getAppointmentList();
        for (Appointment a : appointmentList) {
            klinix.deleteAppointment(a);
        }
        appointmentList.reset();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public ObservableLocalDate getAppointmentListDate() {
        return klinix.getAppointmentListDate();
    }

    @Override
    public void markAppointmentVisited(Person target, Appointment apptToMark) {
        requireAllNonNull(target, apptToMark);

        Appointment visited = new Appointment(
                apptToMark.getDoctorNric(),
                apptToMark.getDescription(),
                apptToMark.getStartDate(),
                apptToMark.getEndDate(),
                apptToMark.getPatientNric(),
                true
        );

        AppointmentList updatedAppointments = target.getAppointmentList(); // Get current appointments
        updatedAppointments.remove(apptToMark); // Remove the specific appointment
        updatedAppointments.add(visited);

        Person updatedPerson = new Person(
                target.getName(),
                target.getPhone(),
                target.getEmail(),
                target.getNric(),
                target.getBirthDate(),
                target.getAddress(),
                target.getTags(),
                target.getMedicalReport(),
                updatedAppointments // Use updated list of appointments
        );

        klinix.setPerson(target, updatedPerson);
        klinix.deleteAppointment(apptToMark);
        klinix.addAppointment(visited);
        klinix.refreshDisplayedAppointments();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void unmarkAppointmentVisited(Person target, Appointment apptToUnmark) {
        requireAllNonNull(target, apptToUnmark);

        Appointment visited = new Appointment(
                apptToUnmark.getDoctorNric(),
                apptToUnmark.getDescription(),
                apptToUnmark.getStartDate(),
                apptToUnmark.getEndDate(),
                apptToUnmark.getPatientNric(),
                false
        );

        AppointmentList updatedAppointments = target.getAppointmentList(); // Get current appointments
        updatedAppointments.remove(apptToUnmark); // Remove the specific appointment
        updatedAppointments.add(visited);

        Person updatedPerson = new Person(
                target.getName(),
                target.getPhone(),
                target.getEmail(),
                target.getNric(),
                target.getBirthDate(),
                target.getAddress(),
                target.getTags(),
                target.getMedicalReport(),
                updatedAppointments // Use updated list of appointments
        );

        klinix.setPerson(target, updatedPerson);
        klinix.deleteAppointment(apptToUnmark);
        klinix.addAppointment(visited);
        klinix.refreshDisplayedAppointments();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public ObservableList<Appointment> getAppointments() {
        return klinix.getDisplayedAppointments();
    }

    @Override
    public void changeDisplayedAppointments(LocalDate date) {
        klinix.changeDisplayedAppointments(date);
    }


    //=========== Medicine Usage =============================================================================
    @Override
    public void addMedicineUsage(Person target, MedicineUsage medicineUsage) {
        requireAllNonNull(target, medicineUsage);
        MedicalReport medicalReport = target.getMedicalReport();
        medicalReport.add(medicineUsage);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void deleteMedicineUsage(Person target, MedicineUsage medicineUsage) {
        requireAllNonNull(target, medicineUsage);
        target.deleteMedicineUsage(medicineUsage);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void clearMedicineUsage(Person target) {
        requireNonNull(target);
        MedicalReport medicalReport = target.getMedicalReport();
        medicalReport.reset();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return klinix.equals(otherModelManager.klinix)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
