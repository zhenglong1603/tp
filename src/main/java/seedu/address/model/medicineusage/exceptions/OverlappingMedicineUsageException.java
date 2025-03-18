package seedu.address.model.medicineusage.exceptions;

/**
 * Signals that the operation will result in overlapping Medicine Usages (Medicine Usages are considered overlapping
 * if they have the same name and overlapping startDate, endDate).
 */
public class OverlappingMedicineUsageException extends RuntimeException {
    public OverlappingMedicineUsageException() {
        super("Operation would result in overlapping medicine usages with the same name");
    }
}

