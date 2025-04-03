package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyKlinix;

/**
 * A class to access Klinix data stored as a json file on the hard disk.
 */
public class JsonKlinixStorage implements KlinixStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonKlinixStorage.class);

    private Path filePath;

    public JsonKlinixStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getKlinixFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyKlinix> readKlinix() throws DataLoadingException {
        return readKlinix(filePath);
    }

    /**
     * Similar to {@link #readKlinix()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyKlinix> readKlinix(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableKlinix> jsonKlinix = JsonUtil.readJsonFile(
                filePath, JsonSerializableKlinix.class);
        if (!jsonKlinix.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonKlinix.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveKlinix(ReadOnlyKlinix klinix) throws IOException {
        saveKlinix(klinix, filePath);
    }

    /**
     * Similar to {@link #saveKlinix(ReadOnlyKlinix)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveKlinix(ReadOnlyKlinix klinix, Path filePath) throws IOException {
        requireNonNull(klinix);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableKlinix(klinix), filePath);
    }

}
