package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalKlinix;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Klinix;
import seedu.address.model.ReadOnlyKlinix;

public class JsonKlinixStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonKlinixStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readKlinix_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readKlinix(null));
    }

    private java.util.Optional<ReadOnlyKlinix> readKlinix(String filePath) throws Exception {
        return new JsonKlinixStorage(Paths.get(filePath)).readKlinix(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readKlinix("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readKlinix("notJsonFormatKlinix.json"));
    }

    @Test
    public void readKlinix_invalidPersonKlinix_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readKlinix("invalidPersonKlinix.json"));
    }

    @Test
    public void readKlinix_invalidAndValidPersonKlinix_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readKlinix("invalidAndValidPersonKlinix.json"));
    }

    @Test
    public void readAndSaveKlinix_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempKlinix.json");
        Klinix original = getTypicalKlinix();
        JsonKlinixStorage jsonKlinixStorage = new JsonKlinixStorage(filePath);

        // Save in new file and read back
        jsonKlinixStorage.saveKlinix(original, filePath);
        ReadOnlyKlinix readBack = jsonKlinixStorage.readKlinix(filePath).get();
        assertEquals(original, new Klinix(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonKlinixStorage.saveKlinix(original, filePath);
        readBack = jsonKlinixStorage.readKlinix(filePath).get();
        assertEquals(original, new Klinix(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonKlinixStorage.saveKlinix(original); // file path not specified
        readBack = jsonKlinixStorage.readKlinix().get(); // file path not specified
        assertEquals(original, new Klinix(readBack));

    }

    @Test
    public void saveKlinix_nullKlinix_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveKlinix(null, "SomeFile.json"));
    }

    /**
     * Saves {@code klinix} at the specified {@code filePath}.
     */
    private void saveKlinix(ReadOnlyKlinix klinix, String filePath) {
        try {
            new JsonKlinixStorage(Paths.get(filePath))
                    .saveKlinix(klinix, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveKlinix_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveKlinix(new Klinix(), null));
    }
}
