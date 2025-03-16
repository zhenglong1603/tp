package seedu.address.model.medicineusage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.medicineusage.exceptions.MedicineUsageNotFoundException;
import seedu.address.model.medicineusage.exceptions.OverlappingMedicineUsageException;

class MedicineUsageListTest {
    private MedicineUsageList medicineUsageList;
    private MedicineUsage medicineUsage1;
    private MedicineUsage medicineUsage2;
    private MedicineUsage overlappingMedicineUsage;

    @BeforeEach
    void setUp() {
        medicineUsageList = new MedicineUsageList();
        medicineUsage1 = new MedicineUsage("Paracetamol", "500mg", LocalDate.now(),
                LocalDate.now().plusDays(5));
        medicineUsage2 = new MedicineUsage("Ibuprofen", "200mg", LocalDate.now().plusDays(6),
                LocalDate.now().plusDays(10));
        overlappingMedicineUsage = new MedicineUsage("Paracetamol", "500mg",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(7));
    }

    @Test
    void add_uniqueMedicineUsage_success() {
        medicineUsageList.add(medicineUsage1);
        assertTrue(medicineUsageList.asUnmodifiableObservableList().contains(medicineUsage1));
    }

    @Test
    void add_overlappingMedicineUsage_throwsException() {
        medicineUsageList.add(medicineUsage1);
        assertThrows(OverlappingMedicineUsageException.class, () -> medicineUsageList.add(overlappingMedicineUsage));
    }

    @Test
    void remove_existingMedicineUsage_success() {
        medicineUsageList.add(medicineUsage1);
        medicineUsageList.remove(medicineUsage1);
        assertFalse(medicineUsageList.asUnmodifiableObservableList().contains(medicineUsage1));
    }

    @Test
    void remove_nonExistingMedicineUsage_throwsException() {
        assertThrows(MedicineUsageNotFoundException.class, () -> medicineUsageList.remove(medicineUsage1));
    }

    @Test
    void setMedicineUsages_validList_success() {
        List<MedicineUsage> newUsages = Arrays.asList(medicineUsage1, medicineUsage2);
        medicineUsageList.setMedicineUsages(newUsages);
        assertEquals(newUsages.size(), medicineUsageList.asUnmodifiableObservableList().size());
    }

    @Test
    void setMedicineUsages_listWithOverlappingEntries_throwsException() {
        List<MedicineUsage> newUsages = Arrays.asList(medicineUsage1, overlappingMedicineUsage);
        assertThrows(OverlappingMedicineUsageException.class, () -> medicineUsageList.setMedicineUsages(newUsages));
    }

    @Test
    void reset_clearsAllMedicineUsages() {
        medicineUsageList.add(medicineUsage1);
        medicineUsageList.add(medicineUsage2);
        medicineUsageList.reset();
        assertTrue(medicineUsageList.asUnmodifiableObservableList().isEmpty());
    }

    @Test
    void containsOverlap_medicineUsageWithOverlap_returnsTrue() {
        medicineUsageList.add(medicineUsage1);
        assertTrue(medicineUsageList.containsOverlap(overlappingMedicineUsage));
    }

    @Test
    void containsOverlap_noOverlap_returnsFalse() {
        medicineUsageList.add(medicineUsage1);
        assertFalse(medicineUsageList.containsOverlap(medicineUsage2));
    }
}
