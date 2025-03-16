package seedu.address.model.medicineusage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.medicineusage.exceptions.MedicineUsageNotFoundException;
import seedu.address.model.medicineusage.exceptions.OverlappingMedicineUsageException;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A list of medicine usages that enforces uniqueness between its elements and does not allow nulls.
 * A medicine usage is considered unique by comparing using {@code MedicineUsage#hasOverlap(MedicineUsage)}.
 * As such, adding and updating of medicine usages uses MedicineUsage#hasOverlap(MedicineUsage) for equality
 * to ensure that the medicine usage being added or updated is unique in terms of identity in the
 * MedicineUsageList. However, the removal of a medicine usage uses MedicineUsage#equals(Object) to ensure that the
 * medicine usage with exactly the same fields will be removed.
 * Supports a minimal set of list operations.
 *
 * @see MedicineUsage#hasOverlap(MedicineUsage)
 */
public class MedicineUsageList implements Iterable<MedicineUsage> {
    private final ObservableList<MedicineUsage> internalList = FXCollections.observableArrayList();
    private final ObservableList<MedicineUsage> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an overlap of medicine usage with the given argument.
     */
    public boolean containsOverlap(MedicineUsage toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::hasOverlap);
    }

    /**
     * Adds a medicine usage to the list.
     * The medicine usage must not overlap with existing medicine usage (with the same name) in the list.
     */
    public void add(MedicineUsage toAdd) {
        requireNonNull(toAdd);
        if (containsOverlap(toAdd)) {
            throw new OverlappingMedicineUsageException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the medicine usage {@code target} in the list with {@code editedMedicineUsage}.
     * To be used in the future if needed
     * {@code target} must exist in the list.
     * The medicine usage identity of {@code editedMedicineUsage} must not be the same as another existing
     * medicine usage in the list.
     */
    public void setMedicineUsage(MedicineUsage target, MedicineUsage editedMedicineUsage) {
        requireAllNonNull(target, editedMedicineUsage);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MedicineUsageNotFoundException();
        }

        if (!target.hasOverlap(editedMedicineUsage) && containsOverlap(editedMedicineUsage)) {
            throw new OverlappingMedicineUsageException();
        }

        internalList.set(index, editedMedicineUsage);
    }

    /**
     * Removes the equivalent medicine usage from the list.
     * The medicine usage must exist in the list.
     */
    public void remove(MedicineUsage toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MedicineUsageNotFoundException();
        }
    }

    public void setMedicineUsages(MedicineUsageList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setMedicineUsages(List<MedicineUsage> replacement) {
        requireAllNonNull(replacement);
        if (!medicineUsagesAreUnique(replacement)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(replacement);
    }

    public void reset() {
        internalList.clear();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<MedicineUsage> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<MedicineUsage> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MedicineUsageList)) {
            return false;
        }

        MedicineUsageList otherMedicineUsageList = (MedicineUsageList) other;
        return internalList.equals(otherMedicineUsageList.internalList);
    }

    /**
     * Returns true if {@code medicine usages} contains only unique medicine usages.
     */
    private boolean medicineUsagesAreUnique(List<MedicineUsage> medicineUsages) {
        for (int i = 0; i < medicineUsages.size() - 1; i++) {
            for (int j = i + 1; j < medicineUsages.size(); j++) {
                if (medicineUsages.get(i).hasOverlap(medicineUsages.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
