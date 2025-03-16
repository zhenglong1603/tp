package seedu.address.model.medicineusage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

import seedu.address.model.person.Person;

/**
 * Represents a Medicine Usage of a patient in the clinic.
 */
public class MedicineUsage {
    private final String name;
    private final String dosage;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    /**
     * Constructs a MedicineUsage object
     * @param name Name of the medicine
     * @param dosage Dosage of the medicine
     * @param startDate Patient starts taking medicine on this date
     * @param endDate Patient stops taking medicine on this date
     */
    public MedicineUsage(String name, String dosage, LocalDateTime startDate, LocalDateTime endDate) {
        requireAllNonNull(name, dosage, startDate, endDate);
        this.name = name;
        this.dosage = dosage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Returns true if both medicine usages have the same name and overlapping time.
     * This defines a weaker notion of equality between two medicine usages.
     */

    public boolean hasOverlap(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MedicineUsage)) {
            return false;
        }

        MedicineUsage otherMedicineUsage = (MedicineUsage) other;
        boolean hasNoTimeOverlap = otherMedicineUsage.getStartDate().isAfter(this.getEndDate())
                || otherMedicineUsage.getEndDate().isBefore(this.getStartDate());

        return otherMedicineUsage.getName().equals(this.getName()) && !hasNoTimeOverlap;
    }

    /**
     * Returns true if both medicine usages have the same identity and data fields.
     * This defines a stronger notion of equality between two medicine usages.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MedicineUsage)) {
            return false;
        }

        MedicineUsage otherMedicineUsage = (MedicineUsage) other;
        return name.equals(otherMedicineUsage.name)
                && dosage.equals(otherMedicineUsage.dosage)
                && startDate.equals(otherMedicineUsage.startDate)
                && endDate.equals(otherMedicineUsage.endDate);
    }

}
