package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Klinix;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyKlinix;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.MedicalReport;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.util.ObservableLocalDateTime;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getKlinixFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setKlinixFilePath(Path klinixFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setKlinix(ReadOnlyKlinix newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyKlinix getKlinix() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person findPersonByNric(Nric nric) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMedicalReport(Person target, MedicalReport medicalReport) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMedicalReport(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getAppointments() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void changeDisplayedAppointments(LocalDate date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMedicineUsage(Person person, MedicineUsage medicineUsage) {
            // Since this is a stub, you can leave it empty or simulate behavior
            throw new UnsupportedOperationException("This method should not be called");
        }

        @Override
        public void deleteMedicineUsage(Person person, MedicineUsage medicineUsage) {
            // Since this is a stub, you can leave it empty or simulate behavior
            throw new UnsupportedOperationException("This method should not be called");
        }

        @Override
        public void clearMedicineUsage(Person person) {
            // Since this is a stub, you can leave it empty or simulate behavior
            throw new UnsupportedOperationException("This method should not be called");
        }

        @Override
        public void addAppointment(Person person, Appointment appointment) {
            // Since this is a stub, you can leave it empty or simulate behavior
            throw new UnsupportedOperationException("This method should not be called");
        }

        @Override
        public void deleteAppointment(Person person, Appointment appointment) {
            // Since this is a stub, you can leave it empty or simulate behavior
            throw new UnsupportedOperationException("This method should not be called");
        }

        @Override
        public void clearAppointments(Person person) {
            // Since this is a stub, you can leave it empty or simulate behavior
            throw new UnsupportedOperationException("This method should not be called");
        }

        @Override
        public boolean isAppointmentListEmpty() {
            return false;
        }

        @Override
        public List<Appointment> getOverlappingAppointments(Appointment appointment, List<Person> allPersons) {
            // Since this is a stub, you can leave it empty or simulate behavior
            throw new UnsupportedOperationException("This method should not be called");
        }

        @Override
        public ObservableLocalDateTime getAppointmentListDate() {
            return null;
        }

        @Override
        public void markAppointmentVisited(Person person, Appointment apptToMark) {
            throw new UnsupportedOperationException("This method should not be called");
        }

        @Override
        public void unmarkAppointmentVisited(Person person, Appointment apptToMark) {
            throw new UnsupportedOperationException("This method should not be called");
        }

        @Override
        public boolean checkValidMedicineUsage(Person person, MedicineUsage medicineUsage) {
            throw new UnsupportedOperationException("This method should not be called");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyKlinix getKlinix() {
            return new Klinix();
        }
    }
}

