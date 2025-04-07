package seedu.address.model.medicineusage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

// The test cases are adapted from a conversation with chatGPT

public class MedicineUsageTest {

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        LocalDate today = LocalDate.now();
        // Test each parameter being null; all should throw NullPointerException
        assertThrows(NullPointerException.class, () ->
                new MedicineUsage(null, new Dosage("2 pills"), today, today.plusDays(1)));
        assertThrows(NullPointerException.class, () ->
                new MedicineUsage(new MedicineName("Panadol"), null, today, today.plusDays(1)));
        assertThrows(NullPointerException.class, () ->
                new MedicineUsage(new MedicineName("Panadol"), new Dosage("2 pills"), null, today.plusDays(1)));
        assertThrows(NullPointerException.class, () ->
                new MedicineUsage(new MedicineName("Panadol"), new Dosage("2 pills"), today, null));
    }

    @Test
    public void constructor_validParameters_success() {
        LocalDate today = LocalDate.now();
        MedicineUsage medicineUsage = new MedicineUsage(new MedicineName("Panadol"),
                new Dosage("2 pills"), today, today.plusDays(1));

        // Test that the object was created correctly
        assertEquals("Panadol", medicineUsage.getName().toString()); // Use toString for checking the name
        assertEquals("2 pills", medicineUsage.getDosage().toString()); // Use toString for checking the dosage
        assertEquals(today, medicineUsage.getStartDate());
        assertEquals(today.plusDays(1), medicineUsage.getEndDate());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        LocalDate today = LocalDate.now();
        MedicineUsage medicineUsage = new MedicineUsage(new MedicineName("Panadol"),
                new Dosage("2 pills"), today, today.plusDays(1));
        // Checking reflexivity
        assertTrue(medicineUsage.equals(medicineUsage));
    }

    @Test
    public void equals_sameNameOverlappingDates_returnsTrue() {
        LocalDate today = LocalDate.now();
        // First usage: starts today, ends after 1 day
        MedicineUsage usageA = new MedicineUsage(new MedicineName("Panadol"),
                new Dosage("2 pills"), today, today.plusDays(1));
        // Second usage: same name, overlapping (starts within the first usage's period)
        MedicineUsage usageB = new MedicineUsage(new MedicineName("Panadol"),
                new Dosage("2 pills"), today.plusDays(1), today.plusDays(2));

        // Should be true because name is the same and the date ranges overlap
        assertTrue(usageA.hasOverlap(usageB));
    }

    @Test
    public void equals_sameNameNoOverlap_returnsFalse() {
        LocalDate today = LocalDate.now();
        // usageA: from today until tomorrow
        MedicineUsage usageA = new MedicineUsage(new MedicineName("Panadol"),
                new Dosage("2 pills"), today, today.plusDays(1));
        // usageB: starts the day after usageA ends
        MedicineUsage usageB = new MedicineUsage(new MedicineName("Panadol"), new Dosage("2 pills"),
                today.plusDays(2), today.plusDays(3));

        // Should be false because the date ranges do not overlap
        assertFalse(usageA.hasOverlap(usageB));
    }

    @Test
    public void equals_differentName_returnsFalse() {
        LocalDate today = LocalDate.now();
        MedicineUsage usageA = new MedicineUsage(new MedicineName("Panadol"),
                new Dosage("2 pills"), today, today.plusDays(1));
        // Different name, same date range
        MedicineUsage usageB = new MedicineUsage(new MedicineName("Tylenol"),
                new Dosage("2 pills"), today, today.plusDays(1));

        // Should be false because the names are different
        assertFalse(usageA.equals(usageB));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        LocalDate today = LocalDate.now();
        MedicineUsage medicineUsage = new MedicineUsage(new MedicineName("Panadol"),
                new Dosage("2 pills"), today, today.plusDays(1));
        // Comparing with null should always return false
        assertFalse(medicineUsage.equals(null));
    }

    @Test
    public void equals_differentObjectType_returnsFalse() {
        LocalDate today = LocalDate.now();
        MedicineUsage medicineUsage = new MedicineUsage(new MedicineName("Panadol"),
                new Dosage("2 pills"), today, today.plusDays(1));
        // Compare with a completely different object type
        assertFalse(medicineUsage.equals("Some string"));
    }
}
