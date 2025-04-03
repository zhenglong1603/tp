package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Klinix;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableKlinixTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableKlinixTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsKlinix.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonKlinix.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonKlinix.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableKlinix dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableKlinix.class).get();
        Klinix klinixFromFile = dataFromFile.toModelType();
        Klinix typicalPersonsKlinix = TypicalPersons.getTypicalKlinix();
        assertEquals(klinixFromFile, typicalPersonsKlinix);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableKlinix dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableKlinix.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableKlinix dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableKlinix.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableKlinix.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
