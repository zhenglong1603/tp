package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.MedicalReport;
import seedu.address.model.person.Person;

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
    public Person findPersonByNric(String nric) {
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
                medicalReport
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
                null
        );

        klinix.setPerson(target, updatedPerson);
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
