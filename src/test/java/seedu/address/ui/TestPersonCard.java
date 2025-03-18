package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.control.Label;
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
 * Contains tests for the {@code PersonCard} class.
 */
public class TestPersonCard extends ApplicationTest {

    private PersonCard personCard;

    @BeforeEach
    public void setUp() throws Exception {
        System.setProperty("jdk.gtk.version", "3");
        registerPrimaryStage();
        Set<Tag> tags = new HashSet<>();
        Person person = new Person(
                new Name("John Doe"),
                new Phone("12345678"),
                new Email("johnd@example.com"),
                new Nric("S1234567A"),
                new BirthDate("01/01/1990"),
                new Address("123, Jurong West Ave 6, #08-111"),
                tags,
                new MedicalReport("None", "None", "None", "None"));
        personCard = new PersonCard(person, 1);
    }

    /**
     * Tests the {@code display} method in {@code PersonCard} class.
     */
    @Test
    public void display() {
        Node cardNode = personCard.getRoot(); // Assuming getRootNode() returns the root Node of PersonCard
        assertEquals("1. ", ((Label) cardNode.lookup("#id")).getText());
        assertEquals("John Doe", ((Label) cardNode.lookup("#name")).getText());
        assertEquals("S1234567A", ((Label) cardNode.lookup("#nric")).getText());
        assertEquals("35", ((Label) cardNode.lookup("#age")).getText());
        assertEquals("12345678", ((Label) cardNode.lookup("#phone")).getText());
        assertEquals("123, Jurong West Ave 6, #08-111", ((Label) cardNode.lookup("#address")).getText());
        assertEquals("johnd@example.com", ((Label) cardNode.lookup("#email")).getText());
    }
}
