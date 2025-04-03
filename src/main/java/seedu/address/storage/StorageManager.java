package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyKlinix;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Klinix data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private KlinixStorage klinixStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code KlinixStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(KlinixStorage klinixStorage, UserPrefsStorage userPrefsStorage) {
        this.klinixStorage = klinixStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Klinix methods =================================

    @Override
    public Path getKlinixFilePath() {
        return klinixStorage.getKlinixFilePath();
    }

    @Override
    public Optional<ReadOnlyKlinix> readKlinix() throws DataLoadingException {
        return readKlinix(klinixStorage.getKlinixFilePath());
    }

    @Override
    public Optional<ReadOnlyKlinix> readKlinix(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return klinixStorage.readKlinix(filePath);
    }

    @Override
    public void saveKlinix(ReadOnlyKlinix klinix) throws IOException {
        saveKlinix(klinix, klinixStorage.getKlinixFilePath());
    }

    @Override
    public void saveKlinix(ReadOnlyKlinix klinix, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        klinixStorage.saveKlinix(klinix, filePath);
    }

}
