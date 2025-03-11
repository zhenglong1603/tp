package seedu.address.model.person;

public class MedicalReport {
    private String allergens;
    private String illnesses;
    private String surgeries;
    private String immunizations;

    public MedicalReport(String allergens, String illnesses, String surgeries, String immunizations) {
        this.allergens = allergens;
        this.illnesses = illnesses;
        this.surgeries = surgeries;
        this.immunizations = immunizations;
    }
}
