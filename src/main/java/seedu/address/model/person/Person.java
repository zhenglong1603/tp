package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Nric nric;
    private final BirthDate birthDate;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final MedicalReport medicalReport;
    private final AppointmentList appointmentList;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Nric nric, BirthDate birthDate,
                  Address address, Set<Tag> tags, MedicalReport medicalReport, AppointmentList appointmentList) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.nric = nric;
        this.birthDate = birthDate;
        this.address = address;
        this.tags.addAll(tags);
        this.medicalReport = medicalReport;
        this.appointmentList = appointmentList;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Nric getNric() {
        return nric;
    }

    public BirthDate getBirthDate() {
        return birthDate;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns the age of the person.
     */
    public int getAge() {
        return birthDate.getAge();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns the medical report of the person.
     */
    public MedicalReport getMedicalReport() {
        return medicalReport;
    }

    /**
     * Returns the appointmentList of the person.
     */
    public AppointmentList getAppointmentList() {
        return appointmentList;
    }

    public List<Appointment> getAppointments() {
        return appointmentList.asUnmodifiableObservableList();
    }

    public void addAppointment(Appointment toAdd) {
        appointmentList.add(toAdd);
    }

    public void deleteAppointment(Appointment toRemove) {
        appointmentList.remove(toRemove);
    }

    public void setAppointment(List<Appointment> newData) {
        appointmentList.setAppointment(newData);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getNric().equals(getNric());
    }


    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return nric.equals(otherPerson.nric) && name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone) && email.equals(otherPerson.email)
                && birthDate.equals(otherPerson.birthDate) && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && medicalReport.equals(otherPerson.medicalReport)
                && appointmentList.equals(otherPerson.appointmentList);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, nric, birthDate, address, tags, medicalReport, appointmentList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name)
                .add("phone", phone).add("email", email)
                .add("nric", nric).add("birthDate", birthDate).add("address", address)
                .add("tags", tags).add("medicalReport", medicalReport)
                .add("appointmentList", appointmentList).toString();
    }
}
