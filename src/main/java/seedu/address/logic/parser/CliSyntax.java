package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_NRIC = new Prefix("ic/");
    public static final Prefix PREFIX_FROM = new Prefix("from/");
    public static final Prefix PREFIX_TO = new Prefix("to/");
    public static final Prefix PREFIX_ALLERGY = new Prefix("al/");
    public static final Prefix PREFIX_ILLNESS = new Prefix("ill/");
    public static final Prefix PREFIX_SURGERY = new Prefix("sur/");
    public static final Prefix PREFIX_IMMUNIZATION = new Prefix("imm/");
    public static final Prefix PREFIX_DOSAGE = new Prefix("dos/");
    public static final Prefix PREFIX_DATE = new Prefix("date/");
    public static final Prefix PREFIX_AGE = new Prefix("age/");
    public static final Prefix PREFIX_ID = new Prefix("id/");
    public static final Prefix PREFIX_APPOINTMENT_DESCRIPTION = new Prefix("appt/");
    public static final Prefix PREFIX_BIRTHDATE = new Prefix("b/");
}
