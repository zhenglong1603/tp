package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import seedu.address.model.util.ObservableLocalDateTime;

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
    public boolean isAppointmentListEmpty() {
        return klinix.getDisplayedAppointments().isEmpty();
    }

    @Override
    public List<Appointment> getOverlappingAppointments(Appointment newAppointment, List<Person> allPersons) {
        LocalDateTime newStart = newAppointment.getStartDate();
        LocalDateTime newEnd = newAppointment.getEndDate();

        List<Appointment> overlappingAppointments = new ArrayList<>(); // List to store overlapping appointments

        // Loop through all appointments in the system
        for (Person person : allPersons) { // Assuming allPersons is a list of all persons in the system
            for (Appointment existingAppointment : person.getAppointments()) {
                LocalDateTime existingStart = existingAppointment.getStartDate();
                LocalDateTime existingEnd = existingAppointment.getEndDate();

                // Check if the new appointment overlaps with any existing appointment
                boolean startsBeforeExistingEnds = newStart.isBefore(existingEnd);
                boolean endsAfterExistingStarts = newEnd.isAfter(existingStart);

                if (startsBeforeExistingEnds && endsAfterExistingStarts) {
                    overlappingAppointments.add(existingAppointment); // Add to the list of overlaps
                }
            }
        }

        return overlappingAppointments; // Return the list of overlapping appointments
    }

    @Override
    public ObservableLocalDateTime getAppointmentListDate() {
        return klinix.getAppointmentListDate();
    }

    @Override
    public void markAppointmentVisited(Person target, Appointment apptToMark) {
        requireAllNonNull(target, apptToMark);

        Appointment visited = new Appointment(
                apptToMark.getDescription(),
                apptToMark.getStartDate(),
                apptToMark.getEndDate(),
                apptToMark.getPatientNric(),
                true
        );

        AppointmentList updatedAppointments = target.getAppointmentList(); // Get current appointments
        updatedAppointments.replaceAppointment(apptToMark, visited);

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
        klinix.replaceAppointment(apptToMark, visited);
        klinix.refreshDisplayedAppointments();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void unmarkAppointmentVisited(Person target, Appointment apptToUnmark) {
        requireAllNonNull(target, apptToUnmark);

        Appointment unvisitedAppointment = new Appointment(
                apptToUnmark.getDescription(),
                apptToUnmark.getStartDate(),
                apptToUnmark.getEndDate(),
                apptToUnmark.getPatientNric(),
                false
        );

        AppointmentList updatedAppointments = target.getAppointmentList(); // Get current appointments
        updatedAppointments.replaceAppointment(apptToUnmark, unvisitedAppointment);

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
        klinix.replaceAppointment(apptToUnmark, unvisitedAppointment);
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

    /**
     * Checks if a person is born before a medicine usage start day (for validity check)
     * @param target person to compare
     * @param medicineUsage medicine usage to compare
     * @return true if the person's birthday is before the medicine usage's start date
     */
    @Override
    public boolean checkValidMedicineUsage(Person target, MedicineUsage medicineUsage) {
        requireAllNonNull(target, medicineUsage);
        return !target.bornAfter(medicineUsage.getStartDate());
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
