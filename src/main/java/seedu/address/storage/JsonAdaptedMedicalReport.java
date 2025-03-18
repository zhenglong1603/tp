package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicineusage.MedicineUsage;
import seedu.address.model.person.MedicalReport;


/**
 * Jackson-friendly version of {@link MedicalReport}.
 */
class JsonAdaptedMedicalReport {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Medical Report's %s field is missing!";

    private final String allergens;
    private final String illnesses;
    private final String surgeries;
    private final String immunizations;
    private final List<JsonAdaptedMedicineUsage> medicineUsages = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMedicalReport} with the given medical report details.
     */
    public JsonAdaptedMedicalReport(@JsonProperty("allergy") String allergy, @JsonProperty("illness") String illness,
            @JsonProperty("surgery") String surgery, @JsonProperty("immunization") String immunization,
            @JsonProperty("medicineUsages") List<JsonAdaptedMedicineUsage> medicineUsages) {
        this.allergens = allergy;
        this.illnesses = illness;
        this.surgeries = surgery;
        this.immunizations = immunization;
        if (medicineUsages != null) {
            this.medicineUsages.addAll(medicineUsages);
        }
    }

    /**
     * Converts a given {@code MedicalReport} into this class for Jackson use.
     */
    public JsonAdaptedMedicalReport(MedicalReport source) {
        this.allergens = source.getAllergens();
        this.illnesses = source.getIllnesses();
        this.surgeries = source.getSurgeries();
        this.immunizations = source.getImmunizations();


    }

    /**
     * Converts this Jackson-friendly adapted medical report object into the model's {@code MedicalReport} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medical report.
     */
    public MedicalReport toModelType() throws IllegalValueException {
        final List<MedicineUsage> personMedicineUsages = new ArrayList<>();
        for (JsonAdaptedMedicineUsage medicineUsage : this.medicineUsages) {
            personMedicineUsages.add(medicineUsage.toModelType());
        }

        if (allergens == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "allergy"));
        }
        if (illnesses == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "illness"));
        }
        if (surgeries == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "surgery"));
        }
        if (immunizations == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "immunization"));
        }

        MedicalReport medicalReport = new MedicalReport(allergens, illnesses, surgeries, immunizations);
        medicalReport.setMedicineUsages(personMedicineUsages);

        return medicalReport;
    }
}

