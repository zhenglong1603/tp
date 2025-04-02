package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.medicineusage.Dosage;
import seedu.address.model.medicineusage.MedicineName;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.testutil.PersonBuilder;

public class MedicineUsageContainsKeywordsPredicateTest {

    @Test
    public void test_medicineNameContainsKeyword_returnsTrue() {
        MedicineUsage med = new MedicineUsage(new MedicineName("Paracetamol"), new Dosage("2 pills"),
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10));
        MedicalReport report = new MedicalReport("", "", "", "");
        report.add(med);

        Person person = new PersonBuilder()
                .withMedicalReport(report)
                .build();

        MedicineUsageContainsKeywordsPredicate predicate =
                new MedicineUsageContainsKeywordsPredicate(Collections.singletonList("paracetamol"));
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_medicineNameDoesNotMatch_returnsFalse() {
        MedicineUsage med = new MedicineUsage(new MedicineName("Ibuprofen"), new Dosage("1 tablet"),
                LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 5));
        MedicalReport report = new MedicalReport("", "", "", "");
        report.add(med);

        Person person = new PersonBuilder()
                .withMedicalReport(report)
                .build();

        MedicineUsageContainsKeywordsPredicate predicate =
                new MedicineUsageContainsKeywordsPredicate(Collections.singletonList("paracetamol"));
        assertFalse(predicate.test(person));
    }

    @Test
    public void test_multipleKeywords_oneMatch() {
        MedicineUsage med = new MedicineUsage(new MedicineName("Ibuprofen"), new Dosage("1 tablet"),
                LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 5));
        MedicalReport report = new MedicalReport("", "", "", "");
        report.add(med);

        Person person = new PersonBuilder()
                .withMedicalReport(report)
                .build();

        List<String> keywords = Arrays.asList("paracetamol", "ibuprofen");
        MedicineUsageContainsKeywordsPredicate predicate =
                new MedicineUsageContainsKeywordsPredicate(keywords);

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_equals() {
        List<String> firstKeywords = Collections.singletonList("panadol");
        List<String> secondKeywords = Collections.singletonList("amoxicillin");

        MedicineUsageContainsKeywordsPredicate firstPredicate =
                new MedicineUsageContainsKeywordsPredicate(firstKeywords);
        MedicineUsageContainsKeywordsPredicate secondPredicate =
                new MedicineUsageContainsKeywordsPredicate(secondKeywords);

        assertEquals(firstPredicate, new MedicineUsageContainsKeywordsPredicate(firstKeywords)); // same value
        assertNotEquals(firstPredicate, secondPredicate); // different keywords
        assertNotEquals(null, firstPredicate);
        assertNotEquals("some string", firstPredicate);
    }
}

