package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Klinix;
import seedu.address.model.ReadOnlyKlinix;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * An Immutable Klinix that is serializable to JSON format.
 */
@JsonRootName(value = "klinix")
class JsonSerializableKlinix {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final ArrayList<Appointment> appointments = new ArrayList<Appointment>();

    /**
     * Constructs a {@code JsonSerializableKlinix} with the given persons.
     */
    @JsonCreator
    public JsonSerializableKlinix(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyKlinix} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableKlinix}.
     */
    public JsonSerializableKlinix(ReadOnlyKlinix source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this klinix into the model's {@code Klinix} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Klinix toModelType() throws IllegalValueException {
        Klinix klinix = new Klinix();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (klinix.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            klinix.addPerson(person);
            klinix.addAppointment(person.getAppointments());
        }
        return klinix;
    }

}
