package seedu.address.model.appointment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.model.appointment.exceptions.EmptyListException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppointmentListByDate {
    private ObservableMap<String, ObservableList<Appointment>> appointmentsByDate;

    public AppointmentListByDate() {
        appointmentsByDate = FXCollections.observableHashMap();
    }

    public void addAppointment(Appointment appointment) {
        String formattedDate = dateTmeFormatter(appointment.getStartDate());
        if (appointmentsByDate.containsKey(formattedDate)) {
            if (appointmentsByDate.get(formattedDate).contains(appointment)) {
                return;
            }
            appointmentsByDate.get(formattedDate).add(appointment);
        } else {
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            appointmentList.add(appointment);
            appointmentsByDate.put(formattedDate, appointmentList);
        }
    }

    public void removeAppointment(Appointment appointment) {
        String startDate = dateTmeFormatter(appointment.getStartDate());
        if (appointmentsByDate.containsKey(startDate)) {
            appointmentsByDate.get(startDate).remove(appointment);
        }
    }

    public ObservableList<Appointment> getAppointmentListByDate(LocalDate date) {
        String formattedDate = dateTmeFormatter(date);
        if (appointmentsByDate.containsKey(formattedDate)) {
            return appointmentsByDate.get(formattedDate);
        }
        appointmentsByDate.put(formattedDate, FXCollections.observableArrayList());
        return appointmentsByDate.get(formattedDate);
    }

    public void addAppointmentList(ObservableList<Appointment> appointmentList) throws EmptyListException {
        if (appointmentList.isEmpty()) {
            throw new EmptyListException();
        }
        LocalDate startDate = appointmentList.get(0).getStartDate();
        appointmentsByDate.put(dateTmeFormatter(startDate), appointmentList);
    }

    public String dateTmeFormatter(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    public void deleteAppointment(Appointment appointmentToDelete) {
        String startDate = dateTmeFormatter(appointmentToDelete.getStartDate());
        if (appointmentsByDate.containsKey(startDate)) {
            appointmentsByDate.get(startDate).remove(appointmentToDelete);
        }
    }
}
