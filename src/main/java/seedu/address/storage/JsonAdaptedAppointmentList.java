package seedu.address.storage;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentList;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;

/**
 * Jackson-friendly version of the AppointmentList that can be used for JSON serialization/deserialization.
 */
public class JsonAdaptedAppointmentList {

    private final List<JsonAdaptedAppointment> appointments;

    /**
     * Constructs a {@code JsonAdaptedAppointmentList} from the given {@code AppointmentList}.
     */
    @JsonCreator
    public JsonAdaptedAppointmentList(@JsonProperty("appointments") List<JsonAdaptedAppointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * Converts this Jackson-friendly version of an AppointmentList into the model's {@code AppointmentList}.
     */
    public AppointmentList toModelType() {
        AppointmentList appointmentList = new AppointmentList();
        for (JsonAdaptedAppointment jsonAppointment : appointments) {
            try {
                Appointment appointment = jsonAppointment.toModelType();
                appointmentList.add(appointment); // This will throw if there's an overlap
            } catch (OverlappingAppointmentException | IllegalValueException e) {
                // Handle exceptions for overlapping appointments, if needed.
                // In most cases, you might want to log or print this issue.
            }
        }
        return appointmentList;
    }

    /**
     * Converts the given {@code AppointmentList} into this Jackson-friendly version.
     */
    public static JsonAdaptedAppointmentList fromModelType(AppointmentList source) {
        List<JsonAdaptedAppointment> adaptedAppointments = source.asUnmodifiableObservableList().stream()
                .map(JsonAdaptedAppointment::new) // Converts each Appointment to JsonAdaptedAppointment
                .collect(Collectors.toList());
        return new JsonAdaptedAppointmentList(adaptedAppointments);
    }

    /**
     * Get the list of appointments in this adapted list.
     */
    public List<JsonAdaptedAppointment> getAppointments() {
        return appointments;
    }
}
