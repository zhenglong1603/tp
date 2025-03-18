package seedu.address.model.person;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.medicineusage.MedicineUsageList;

/**
 * Represents a Person's medical report in the address book.
 */
public class MedicalReport {

    public static final MedicalReport EMPTY_MEDICAL_REPORT =
            new MedicalReport("None", "None", "None", "None");

    public final String value;
    private final String allergens;
    private final String illnesses;
    private final String surgeries;
    private final String immunizations;
    private final MedicineUsageList medicineUsages;

    /**
     * Constructs a {@code MedicalReport} with the specified medical report details.
     *
     * @param allergens A string representing the allergens of the person.
     * @param illnesses A string representing the illnesses of the person.
     * @param surgeries A string representing the surgeries of the person.
     * @param immunizations A string representing the immunizations of the person.
     */
    public MedicalReport(String allergens, String illnesses, String surgeries, String immunizations) {
        this.allergens = allergens;
        this.illnesses = illnesses;
        this.surgeries = surgeries;
        this.immunizations = immunizations;
        this.medicineUsages = new MedicineUsageList();
        this.value = this.toString();
    }

    public String getAllergens() {
        return allergens;
    }

    public String getIllnesses() {
        return illnesses;
    }

    public String getSurgeries() {
        return surgeries;
    }

    public String getImmunizations() {
        return immunizations;
    }

    public ObservableList<MedicineUsage> getMedicineUsages() {
        return medicineUsages.asUnmodifiableObservableList();
    }

    public void add(MedicineUsage toAdd) {
        medicineUsages.add(toAdd);
    }

    public void remove(MedicineUsage toRemove) {
        medicineUsages.remove(toRemove);
    }

    public void setMedicineUsages(List<MedicineUsage> newData) {
        medicineUsages.setMedicineUsages(newData);
    }

    public void reset() {
        medicineUsages.reset();
    }

    @Override
    public String toString() {
        return (
                "  ➤ Allergens: " + allergens + "\n"
                + "  ➤ Illnesses: " + illnesses + "\n"
                + "  ➤ Surgeries: " + surgeries + "\n"
                + "  ➤ Immunizations: " + immunizations
            );
    }
}


