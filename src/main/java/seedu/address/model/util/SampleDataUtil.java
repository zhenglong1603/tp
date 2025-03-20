package seedu.address.model.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Klinix;
import seedu.address.model.ReadOnlyKlinix;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentList;
import seedu.address.model.person.Address;
import seedu.address.model.person.BirthDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.MedicalReport;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static List<Person> getSamplePersons() {
        List<Person> persons = new ArrayList<>();

        Person p1 = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Nric("S1234567A"),
                new BirthDate("01/01/1999"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"),
                new MedicalReport("Pollen", "Asthma", "Appendectomy", "COVID-19"),
                new AppointmentList());
        p1.add(new Appointment("S1234567A", "Checkup",
                LocalDate.of(2025, 5, 10), LocalDate.of(2025, 5, 10)));
        persons.add(p1);

        Person p2 = new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Nric("S1234568B"),
                new BirthDate("02/01/1999"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"),
                new MedicalReport("None", "Diabetes", "None", "Hepatitis B"),
                new AppointmentList());
        p2.add(new Appointment("S1234567A", "Dental Appointment",
                LocalDate.of(2025, 6, 15), LocalDate.of(2025, 6, 15)));
        persons.add(p2);

        Person p3 = new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Nric("S1234569C"),
                new BirthDate("03/01/1999"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"),
                new MedicalReport("Dust", "None", "Tonsillectomy", "Flu"),
                new AppointmentList());
        p3.add(new Appointment("S1234567A", "Flu Vaccination",
                LocalDate.of(2025, 7, 20), LocalDate.of(2025, 7, 20)));
        persons.add(p3);

        Person p4 = new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Nric("S1234569D"),
                new BirthDate("04/01/1999"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"),
                new MedicalReport("Peanuts", "Hypertension", "None", "None"),
                new AppointmentList());
        p4.add(new Appointment("S1234567A", "Eye Checkup",
                LocalDate.of(2025, 8, 25), LocalDate.of(2025, 8, 25)));
        persons.add(p4);

        Person p5 = new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Nric("S1234569E"),
                new BirthDate("05/01/1999"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"),
                new MedicalReport("None", "None", "None", "None"),
                new AppointmentList());
        persons.add(p5);

        Person p6 = new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Nric("S1234569F"),
                new BirthDate("06/01/1999"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"),
                new MedicalReport("Shellfish", "Gout", "Gallbladder Removal", "None"),
                new AppointmentList());
        p6.add(new Appointment("S1234567A", "Routine Checkup",
                LocalDate.of(2025, 9, 30), LocalDate.of(2025, 9, 30)));
        persons.add(p6);

        return persons;
    }



    public static ReadOnlyKlinix getSampleKlinix() {
        Klinix sampleAb = new Klinix();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}


