package seedu.address.model.person;

/**
 * Represents a Person's medical report in the address book.
 */
public class MedicalReport {
    private String allergens;
    private String illnesses;
    private String surgeries;
    private String immunizations;

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
}

