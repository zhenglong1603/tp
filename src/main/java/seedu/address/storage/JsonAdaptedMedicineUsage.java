package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.medicineusage.MedicineName;
import seedu.address.model.medicineusage.MedicineUsage;

/**
 * Jackson-friendly version of {@link MedicineUsage}.
 */
public class JsonAdaptedMedicineUsage {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Medicine Usage's %s field is missing!";

    private final String name;
    private final String dosage;
    private final String startDate;
    private final String endDate;

    /**
     * Constructs a {@code JsonAdaptedMedicineUsage} with the given medicine usage details.
     */
    public JsonAdaptedMedicineUsage(@JsonProperty("name") String name, @JsonProperty("dosage") String dosage,
                                    @JsonProperty("startDate") String startDate,
                                    @JsonProperty("endDate") String endDate) {
        this.name = name;
        this.dosage = dosage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Converts a given {@code MedicineUsage} into this class for Jackson use.
     */
    public JsonAdaptedMedicineUsage(MedicineUsage source) {
        name = source.getName().fullName;
        dosage = source.getDosage();
        startDate = source.getStringStartDate();
        endDate = source.getStringEndDate();
    }

    /**
     * Converts this Jackson-friendly adapted medicine usage object into the model's {@code MedicineUsage} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medicine usage.
     */
    public MedicineUsage toModelType() throws IllegalValueException, DateTimeParseException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicineName.class.getSimpleName()));
        }
        if (!MedicineName.isValidName(name)) {
            throw new IllegalValueException(MedicineName.MESSAGE_CONSTRAINTS);
        }
        final MedicineName modelName = new MedicineName(name);

        if (dosage == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "dosage"));
        }

        if (startDate == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "start date"));
        }

        if (endDate == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "end date"));
        }

        LocalDate formattedStartDate = LocalDate.parse(startDate, DateUtil.getDisplayDateFormatter());
        LocalDate formattedEndDate = LocalDate.parse(endDate, DateUtil.getDisplayDateFormatter());

        return new MedicineUsage(modelName, dosage, formattedStartDate, formattedEndDate);
    }
}
