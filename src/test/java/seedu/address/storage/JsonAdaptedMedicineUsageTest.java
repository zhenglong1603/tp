package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicineusage.MedicineUsage;

/**
 * Test class for JsonAdaptedMedicineUsage.
 */
public class JsonAdaptedMedicineUsageTest {

    private static final String VALID_NAME = "Paracetamol";
    private static final String VALID_DOSAGE = "500mg twice daily";
    private static final String VALID_START_DATE = "23-02-2025";
    private static final String VALID_END_DATE = "25-02-2025";

    /**
     * Ensures that JsonAdaptedMedicineUsage correctly converts to a valid MedicineUsage object.
     */
    @Test
    public void toModelType_validMedicineUsageDetails_returnsMedicineUsage() throws Exception {
        MedicineUsage expected = new MedicineUsage(VALID_NAME, VALID_DOSAGE,
                LocalDate.of(2025, 2, 23), LocalDate.of(2025, 2, 25));
        JsonAdaptedMedicineUsage adapted = new JsonAdaptedMedicineUsage(expected);
        assertEquals(expected, adapted.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedMedicineUsage adapted = new JsonAdaptedMedicineUsage(null,
                VALID_DOSAGE, VALID_START_DATE, VALID_END_DATE);
        String expectedMessage = String.format(JsonAdaptedMedicineUsage.MISSING_FIELD_MESSAGE_FORMAT, "medicine name");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_nullDosage_throwsIllegalValueException() {
        JsonAdaptedMedicineUsage adapted = new JsonAdaptedMedicineUsage(VALID_NAME,
                null, VALID_START_DATE, VALID_END_DATE);
        String expectedMessage = String.format(JsonAdaptedMedicineUsage.MISSING_FIELD_MESSAGE_FORMAT, "dosage");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        JsonAdaptedMedicineUsage adapted = new JsonAdaptedMedicineUsage(VALID_NAME,
                VALID_DOSAGE, null, VALID_END_DATE);
        String expectedMessage = String.format(JsonAdaptedMedicineUsage.MISSING_FIELD_MESSAGE_FORMAT, "start date");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        JsonAdaptedMedicineUsage adapted = new JsonAdaptedMedicineUsage(VALID_NAME,
                VALID_DOSAGE, VALID_START_DATE, null);
        String expectedMessage = String.format(JsonAdaptedMedicineUsage.MISSING_FIELD_MESSAGE_FORMAT, "end date");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidDateFormat_throwsDateTimeParseException() {
        JsonAdaptedMedicineUsage adapted = new JsonAdaptedMedicineUsage(VALID_NAME,
                VALID_DOSAGE, "2025/02/23", VALID_END_DATE);
        assertThrows(Exception.class, adapted::toModelType);
    }
}
