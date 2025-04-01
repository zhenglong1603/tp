package seedu.address.model.appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.EmptyListException;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;

/**
 * Represents a map of appointments grouped by date.
 * It contains lists of appointments for each date.
 * the key is the date in the format "dd-MM-yyyy"
 * the value is a list of appointments for that date
 */
public class AppointmentListByDate {
    private ObservableMap<String, ObservableList<Appointment>> appointmentsByDate;

    public AppointmentListByDate() {
        appointmentsByDate = FXCollections.observableHashMap();
    }

    /**
     * Adds an appointment to the list of appointments for the date of the appointment.
     * If the date does not exist, a new list is created.
     * If the appointment already exists in the list, it is not added.
     *
     * @param appointment the appointment to be added
     */
    public void addAppointment(Appointment appointment) {
        String formattedDate = dateTmeFormatter(appointment.getStartDate());
        if (appointmentsByDate.containsKey(formattedDate)) {
            if (appointmentsByDate.get(formattedDate).contains(appointment)) {
                return;
            }
            appointmentsByDate.get(formattedDate).add(appointment);
        } else {
            // should not reach here normally, but just in case
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            appointmentList.add(appointment);
            appointmentsByDate.put(formattedDate, appointmentList);
        }
    }

    /**
     * Removes the appointment from the list of appointments for the date of the appointment.
     *
     * @param appointment the appointment to be removed
     */
    public void removeAppointment(Appointment appointment) {
        String startDate = dateTmeFormatter(appointment.getStartDate());
        if (appointmentsByDate.containsKey(startDate)) {
            appointmentsByDate.get(startDate).remove(appointment);
        }
    }

    /**
     * Returns the list of appointments for the given date.
     * If the date does not exist, a new list is created.
     *
     * @param date the date to get the list of appointments for
     * @return the list of appointments for the given date
     */
    public ObservableList<Appointment> getAppointmentListByDate(LocalDate date) {
        String formattedDate = dateTmeFormatter(date);
        if (appointmentsByDate.containsKey(formattedDate)) {
            return appointmentsByDate.get(formattedDate);
        }
        appointmentsByDate.put(formattedDate, FXCollections.observableArrayList());
        return appointmentsByDate.get(formattedDate);
    }

    /**
     * Adds a list of appointments to the list of appointments for the date of the first appointment.
     * If the list is empty, an EmptyListException is thrown.
     *
     * @param appointmentList the list of appointments to be added
     * @throws EmptyListException if the list is empty
     */
    public void addAppointmentList(ObservableList<Appointment> appointmentList) throws EmptyListException {
        if (appointmentList.isEmpty()) {
            throw new EmptyListException();
        }
        LocalDateTime startDate = appointmentList.get(0).getStartDate();
        appointmentsByDate.put(dateTmeFormatter(startDate), appointmentList);
    }

    /**
     * Formats the date to a string in the format "dd-MM-yyyy".
     * This is used as the key in the map.
     *
     * @param date the date to be formatted
     * @return the formatted date
     */
    public String dateTmeFormatter(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }
    /**
     * Formats the date to a string in the format "dd-MM-yyyy".
     * This is used as the key in the map.
     *
     * @param date the date to be formatted
     * @return the formatted date
     */
    public String dateTmeFormatter(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    /**
     * Deletes the appointment from the list of appointments for the date of the appointment.
     *
     * @param appointmentToDelete the appointment to be deleted
     */
    public void deleteAppointment(Appointment appointmentToDelete) {
        String startDate = dateTmeFormatter(appointmentToDelete.getStartDate());
        if (appointmentsByDate.containsKey(startDate)) {
            appointmentsByDate.get(startDate).remove(appointmentToDelete);
        }
    }

    /**
     * Replaces the target appointment with the edited appointment.
     * If the target appointment does not exist, an AppointmentNotFoundException is thrown.
     * If the edited appointment overlaps with another appointment, an OverlappingAppointmentException is thrown.
     *
     * @param target the appointment to be replaced
     * @param editedAppointment the appointment to replace the target appointment
     * @throws AppointmentNotFoundException if the target appointment does not exist
     * @throws OverlappingAppointmentException if the edited appointment overlaps with another appointment
     */
    public void replaceAppointment(Appointment target, Appointment editedAppointment) {
        String startDate = dateTmeFormatter(target.getStartDate());
        if (appointmentsByDate.containsKey(startDate)) {
            ObservableList<Appointment> currentListOfAppointment = appointmentsByDate.get(startDate);
            int index = currentListOfAppointment.indexOf(target);

            if (index == -1) {
                throw new AppointmentNotFoundException();
            }

            if (!target.hasOverlap(editedAppointment)) {
                throw new OverlappingAppointmentException("Appointment has an overlap!");
            }

            currentListOfAppointment.set(index, editedAppointment);
            addAppointment(editedAppointment);
        }
    }
}
