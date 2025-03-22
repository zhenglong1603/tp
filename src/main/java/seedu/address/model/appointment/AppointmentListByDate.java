package seedu.address.model.appointment;

import seedu.address.model.appointment.exceptions.EmptyListException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class AppointmentListByDate {
    private HashMap<String, ArrayList<Appointment>> appointmentsByDate;

    public AppointmentListByDate() {
        appointmentsByDate = new HashMap<>();
    }

    public void addAppointment(Appointment appointment) {
        String formattedDate = dateTmeFormatter(appointment.getStartDate());
        if (appointmentsByDate.containsKey(formattedDate)) {
            appointmentsByDate.get(formattedDate).add(appointment);
        } else {
            ArrayList<Appointment> appointmentList = new ArrayList<>();
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

    public ArrayList<Appointment> getAppointmentListByDate(LocalDate date) {
        return appointmentsByDate.get(dateTmeFormatter(date));
    }

    public void addAppointmentList(ArrayList<Appointment> appointmentList) throws EmptyListException {
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
}
