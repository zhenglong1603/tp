package seedu.address.model.person;

/**
 * Represents a Person's medical report in the address book.
 */
public class MedicalReport {

    public static final MedicalReport EMPTY_MEDICAL_REPORT =
            new MedicalReport("None", "None", "None", "None");

    private final String allergens;
    private final String illnesses;
    private final String surgeries;
    private final String immunizations;

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
}


