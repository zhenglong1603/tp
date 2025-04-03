package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Klinix;
import seedu.address.model.ReadOnlyKlinix;

/**
 * Represents a storage for {@link Klinix}.
 */
public interface KlinixStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getKlinixFilePath();

    /**
     * Returns Klinix data as a {@link ReadOnlyKlinix}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyKlinix> readKlinix() throws DataLoadingException;

    /**
     * @see #getKlinixFilePath()
     */
    Optional<ReadOnlyKlinix> readKlinix(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyKlinix} to the storage.
     * @param klinix cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveKlinix(ReadOnlyKlinix klinix) throws IOException;

    /**
     * @see #saveKlinix(ReadOnlyKlinix)
     */
    void saveKlinix(ReadOnlyKlinix klinix, Path filePath) throws IOException;

}
