package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Nric;

public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validNric_returnsDeleteCommand() {
        assertParseSuccess(parser, " ic/S1234567A", new DeleteCommand(new Nric("S1234567A")));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNric_throwsParseException() {
        assertParseFailure(parser, " ic/INVALID123", Nric.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_indexAndNricTogether_throwsParseException() {
        assertParseFailure(parser, "1 ic/S1234567A", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nricThenIndex_throwsParseException() {
        assertParseFailure(parser, "ic/S1234567A 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateNricPrefix_throwsParseException() {
        assertParseFailure(parser, " ic/S1234567A ic/S7654321B",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));
    }

    @Test
    public void parse_extraTextAfterIndex_throwsParseException() {
        assertParseFailure(parser, "1 extra", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
    }
}
