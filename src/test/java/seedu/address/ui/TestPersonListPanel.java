package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TestPersonListPanel {

    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void parsePersonData_validPerson_correctString() {
        MedicalReport medicalReport = new MedicalReport("None", "None", "None","None");
        Set<Tag> tags = new HashSet<>();
        Person person = new Person(
                new Name("John Doe"),
                new Phone("12345678"),
                new Email("johndoe@example.com"),
                new Nric("S1234567A"),
                new BirthDate("05/05/1985"),
                new Address("123 Main St"),
                tags,
                medicalReport
        );

        String expected = "Name: John Doe\n" +
                "NRIC: S1234567A\n" +
                "Birthday: 05/05/1985\n" +
                "Age: 39\n\n" +
                "Phone: 12345678\n" +
                "Email: johndoe@example.com\n" +
                "Address: 123 Main St\n\n" +
                "Drug Allergies: None\n" +
                "Illnesses: None\n" +
                "Surgeries: None\n" +
                "Immunizations: None\n\n" +
                "Medicine Usages: \n";

        assertEquals(expected, PersonListPanel.parsePersonData(person));
    }

    @Test
    public void parsePersonData_personWithMedicineUsages_correctString() {
        Set<Tag> tags = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startdate = LocalDate.parse("2020-01-01", formatter);
        LocalDate enddate = LocalDate.parse("2020-12-31", formatter);
        MedicalReport medicalReport = new MedicalReport("Peanuts", "Asthma", "Appendectomy", "Flu");
        ObservableList<MedicineUsage> medicineUsages = FXCollections.observableArrayList(
                new MedicineUsage("Medicine1", "Dosage1", startdate, enddate),
                new MedicineUsage("Medicine2", "Dosage2", startdate, enddate)
        );

        Person person = new Person(
                new Name("Jane Doe"),
                new Phone("87654321"),
                new Email("janedoe@example.com"),
                new Nric("S7654321B"),
                new BirthDate("05/05/1985"),
                new Address("456 Another St"),
                tags,
                medicalReport
        );

        medicalReport.add(medicineUsages.get(0));
        medicalReport.add(medicineUsages.get(1));

        String expected = "Name: Jane Doe\n" +
                "NRIC: S7654321B\n" +
                "Birthday: 05/05/1985\n" +
                "Age: 39\n\n" +
                "Phone: 87654321\n" +
                "Email: janedoe@example.com\n" +
                "Address: 456 Another St\n\n" +
                "Drug Allergies: Peanuts\n" +
                "Illnesses: Asthma\n" +
                "Surgeries: Appendectomy\n" +
                "Immunizations: Flu\n\n" +
                "Medicine Usages: \n" +
                "1: Medicine1, Dosage1, from 01 January 2020 to 31 December 2020\n" +
                "2: Medicine2, Dosage2, from 01 January 2020 to 31 December 2020\n";

        assertEquals(expected, PersonListPanel.parsePersonData(person));
    }
}