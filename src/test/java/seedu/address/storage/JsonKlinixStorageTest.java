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
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

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
        assertThrows(DataLoadingException.class, () -> readKlinix("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readKlinix_invalidPersonKlinix_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readKlinix("invalidPersonAddressBook.json"));
    }

    @Test
    public void readKlinix_invalidAndValidPersonKlinix_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readKlinix("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveKlinix_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        Klinix original = getTypicalKlinix();
        JsonKlinixStorage jsonAddressBookStorage = new JsonKlinixStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveKlinix(original, filePath);
        ReadOnlyKlinix readBack = jsonAddressBookStorage.readKlinix(filePath).get();
        assertEquals(original, new Klinix(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveKlinix(original, filePath);
        readBack = jsonAddressBookStorage.readKlinix(filePath).get();
        assertEquals(original, new Klinix(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAddressBookStorage.saveKlinix(original); // file path not specified
        readBack = jsonAddressBookStorage.readKlinix().get(); // file path not specified
        assertEquals(original, new Klinix(readBack));

    }

    @Test
    public void saveKlinix_nullKlinix_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveKlinix(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveKlinix(ReadOnlyKlinix addressBook, String filePath) {
        try {
            new JsonKlinixStorage(Paths.get(filePath))
                    .saveKlinix(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveKlinix_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveKlinix(new Klinix(), null));
    }
}
