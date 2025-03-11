package seedu.address.model.medicineusage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class MedicineUsageTest {

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        LocalDateTime now = LocalDateTime.now();
        // Test each parameter being null; all should throw NullPointerException
        assertThrows(NullPointerException.class, () ->
                new MedicineUsage(null, "2 pills", now, now.plusDays(1)));
        assertThrows(NullPointerException.class, () ->
                new MedicineUsage("Panadol", null, now, now.plusDays(1)));
        assertThrows(NullPointerException.class, () ->
                new MedicineUsage("Panadol", "2 pills", null, now.plusDays(1)));
        assertThrows(NullPointerException.class, () ->
                new MedicineUsage("Panadol", "2 pills", now, null));
    }

    @Test
    public void constructor_validParameters_success() {
        LocalDateTime now = LocalDateTime.now();
        MedicineUsage medicineUsage = new MedicineUsage("Panadol", "2 pills", now, now.plusDays(1));

        // Test that the object was created correctly
        assertEquals("Panadol", medicineUsage.getName());
        assertEquals("2 pills", medicineUsage.getDosage());
        assertEquals(now, medicineUsage.getStartDate());
        assertEquals(now.plusDays(1), medicineUsage.getEndDate());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        LocalDateTime now = LocalDateTime.now();
        MedicineUsage medicineUsage = new MedicineUsage("Panadol", "2 pills", now, now.plusDays(1));
        // Checking reflexivity
        assertTrue(medicineUsage.equals(medicineUsage));
    }

    @Test
    public void equals_sameNameOverlappingDates_returnsTrue() {
        LocalDateTime now = LocalDateTime.now();
        // First usage: starts now, ends after 1 day
        MedicineUsage usageA = new MedicineUsage("Panadol", "2 pills", now, now.plusDays(1));
        // Second usage: same name, overlapping the same time range
        // Overlaps from now until it ends after half a day
        MedicineUsage usageB = new MedicineUsage("Panadol", "2 pills", now.plusHours(12), now.plusDays(1));

        // Should be true, because name is the same and the date ranges overlap
        assertTrue(usageA.equals(usageB));
    }

    @Test
    public void equals_sameNameNoOverlap_returnsFalse() {
        LocalDateTime now = LocalDateTime.now();
        // usageA: from now until tomorrow
        MedicineUsage usageA = new MedicineUsage("Panadol", "2 pills", now, now.plusDays(1));
        // usageB: starts the day after usageA ends
        MedicineUsage usageB = new MedicineUsage("Panadol", "2 pills",
                now.plusDays(2), now.plusDays(3));

        // Should be false, because the date ranges do not overlap
        assertFalse(usageA.equals(usageB));
    }

    @Test
    public void equals_differentName_returnsFalse() {
        LocalDateTime now = LocalDateTime.now();
        MedicineUsage usageA = new MedicineUsage("Panadol", "2 pills", now, now.plusDays(1));
        // Different name, same date range
        MedicineUsage usageB = new MedicineUsage("Tylenol", "2 pills", now, now.plusDays(1));

        // Should be false, because the names are different
        assertFalse(usageA.equals(usageB));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        LocalDateTime now = LocalDateTime.now();
        MedicineUsage medicineUsage = new MedicineUsage("Panadol", "2 pills", now, now.plusDays(1));
        // Comparing with null should always return false
        assertFalse(medicineUsage.equals(null));
    }

    @Test
    public void equals_differentObjectType_returnsFalse() {
        LocalDateTime now = LocalDateTime.now();
        MedicineUsage medicineUsage = new MedicineUsage("Panadol", "2 pills", now, now.plusDays(1));
        // Compare with a completely different object type
        assertFalse(medicineUsage.equals("Some string"));
    }
}
