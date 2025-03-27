package seedu.address.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class CommandHistoryTest {
    private CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    void setUp() {
        commandHistory.clear();
    }

    @Test
    void add_addsCommandToHistory() {
        commandHistory.add("command1");
        commandHistory.add("command2");

        assertEquals(2, commandHistory.size());
        assertFalse(commandHistory.isEmpty());
    }

    @Test
    void add_removesOldestCommandWhenMaxSizeExceeded() {
        for (int i = 0; i < 101; i++) {
            commandHistory.add("command" + i);
        }

        assertEquals(100, commandHistory.size());
        assertEquals("command100", commandHistory.getPrevious());
    }

    @Test
    void getPrevious_returnsPreviousCommand() {
        commandHistory.add("command1");
        commandHistory.add("command2");

        assertEquals("command2", commandHistory.getPrevious());
        assertEquals("command1", commandHistory.getPrevious());
    }

    @Test
    void getPrevious_staysAtFirstCommand() {
        commandHistory.add("command1");

        assertEquals("command1", commandHistory.getPrevious());
        assertEquals("command1", commandHistory.getPrevious());
    }

    @Test
    void getNext_returnsNextCommand() {
        commandHistory.add("command1");
        commandHistory.add("command2");
        commandHistory.getPrevious();
        commandHistory.getPrevious();
        assertEquals("command2", commandHistory.getNext());
        assertEquals("", commandHistory.getNext());

    }

    @Test
    void getNext_staysAsEmptyString() {
        commandHistory.add("command1");

        assertEquals("", commandHistory.getNext());
    }

    @Test
    void clear_clearsHistory() {
        commandHistory.add("command1");
        commandHistory.add("command2");

        commandHistory.clear();

        assertTrue(commandHistory.isEmpty());
        assertEquals(0, commandHistory.size());
    }

    @Test
    void getPrevious_returnsEmptyStringWhenHistoryIsEmpty() {
        assertEquals("", commandHistory.getPrevious());
    }

    @Test
    void getNext_returnsEmptyStringWhenHistoryIsEmpty() {
        assertEquals("", commandHistory.getNext());
    }

    @Test
    void isEmpty_returnsTrueWhenHistoryIsEmpty() {
        assertTrue(commandHistory.isEmpty());
    }

    @Test
    void isEmpty_returnsFalseWhenHistoryIsNotEmpty() {
        commandHistory.add("command1");

        assertFalse(commandHistory.isEmpty());
    }

    @Test
    void size_returnsCorrectSize() {
        assertEquals(0, commandHistory.size());

        commandHistory.add("command1");
        assertEquals(1, commandHistory.size());

        commandHistory.add("command2");
        assertEquals(2, commandHistory.size());
    }

    @Test
    void constructor_withInitialHistory_initializesCorrectly() {
        CommandHistory historyWithInitialData =
                new CommandHistory(Arrays.asList("command1", "command2"));

        assertEquals(2, historyWithInitialData.size());
        assertEquals("command2", historyWithInitialData.getPrevious());
    }

    @Test
    void isFull_returnsTrueWhenHistoryIsFull() {
        for (int i = 0; i < 100; i++) {
            commandHistory.add("command" + i);
        }

        assertTrue(commandHistory.isFull());
    }

    @Test
    void isCursorAtEnd_returnsTrueWhenPointerIsAtEnd() {
        commandHistory.add("command1");
        commandHistory.add("command2");

        commandHistory.getNext();

        assertTrue(commandHistory.isPointerAtEnd());
    }

    @Test
    void setCursorToEnd_setsPointerToEnd() {
        commandHistory.add("command1");
        commandHistory.add("command2");

        commandHistory.getPrevious();
        commandHistory.getPrevious();

        commandHistory.setPointerToEnd();

        assertTrue(commandHistory.isPointerAtEnd());
    }

    @Test
    void isPointerAtStart_returnsTrueWhenPointerIsAtStart() {
        commandHistory.add("command1");
        commandHistory.add("command2");

        commandHistory.getPrevious();
        commandHistory.getPrevious();

        assertTrue(commandHistory.isPointerAtStart());
    }
}
