package seedu.address.commons.util;

import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Utility class for formatting appointment conflict messages.
 */
public class AppointmentConflictFormatter {

    /**
     * Formats a message detailing overlapping appointments for a given role (e.g., patient).
     *
     * @param role      The role of the person with conflicting appointments (e.g., "Patient").
     * @param conflicts The list of overlapping appointments.
     * @param model     The model instance used to retrieve person details.
     * @return A formatted string describing the overlapping appointments.
     */
    public static String formatConflicts(String role, List<Appointment> conflicts, Model model) {
        StringBuilder overlapDetails = new StringBuilder(
                role + " has overlapping appointments with the following patients:\n");

        for (Appointment overlappingAppointment : conflicts) {
            Person overlappedPerson = model.findPersonByNric(new Nric(overlappingAppointment.getPatientNric()));

            String formattedTime = String.format("%s FROM %s TO %s",
                    overlappedPerson.getName().toString(),
                    overlappingAppointment.getStartDate().format(DATE_TIME_FORMATTER),
                    overlappingAppointment.getEndDate().format(DATE_TIME_FORMATTER)
            );

            overlapDetails.append("- ").append(formattedTime).append("\n");
        }

        return overlapDetails.toString();
    }
}

