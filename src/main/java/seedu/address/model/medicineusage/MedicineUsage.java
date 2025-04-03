package seedu.address.model.medicineusage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;

import seedu.address.commons.util.DateUtil;

/**
 * Represents a Medicine Usage of a patient in the clinic.
 */
public class MedicineUsage {
    private final MedicineName name;
    private final Dosage dosage;
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Constructs a MedicineUsage object
     * @param name Name of the medicine
     * @param dosage Dosage of the medicine
     * @param startDate Patient starts taking medicine on this date
     * @param endDate Patient stops taking medicine on this date
     */
    public MedicineUsage(MedicineName name, Dosage dosage, LocalDate startDate, LocalDate endDate) {
        requireAllNonNull(name, dosage, startDate, endDate);
        this.name = name;
        this.dosage = dosage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public MedicineName getName() {
        return name;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStringStartDate() {
        return DateUtil.getDisplayableDate(startDate);
    }

    public String getStringEndDate() {
        return DateUtil.getDisplayableDate(endDate);
    }

    /**
     * Returns true if both medicine usages have the same name.
     * This defines the weakest notion of equality between two medicine usages.
     */

    public boolean isSameMedicine(MedicineUsage otherMedicineUsage) {
        if (otherMedicineUsage == this) {
            return true;
        }

        return otherMedicineUsage != null && otherMedicineUsage.getName().equals(getName());
    }

    /**
     * Returns true if both medicine usages have the same name and overlapping time.
     * This defines a weak notion of equality between two medicine usages.
     */

    public boolean hasOverlap(MedicineUsage other) {
        if (other == this) {
            return true;
        }

        boolean hasNoTimeOverlap = other.getStartDate().isAfter(this.getEndDate())
                || other.getEndDate().isBefore(this.getStartDate());

        return other.getName().isSameName(this.getName()) && !hasNoTimeOverlap;
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

    @Override
    public String toString() {
        return String.format("%s, %s, from %s to %s", name, dosage,
                DateUtil.getDisplayableDate(startDate),
                DateUtil.getDisplayableDate(endDate));
    }

}
