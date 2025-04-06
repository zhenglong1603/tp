package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

    //---------------- Tests for isNonZeroUnsignedInteger --------------------------------------

    @Test
    public void isNonZeroUnsignedInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0")); // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullPartial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsPartialWordIgnoreCase("typical sentence",
                null));
    }

    @Test
    public void containsWordIgnoreCase_emptyPartial_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter cannot be empty", ()
            -> StringUtil.containsPartialWordIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsPartialWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter should be a single word", ()
            -> StringUtil.containsPartialWordIgnoreCase("typical sentence", "aaa BBB"));
    }

    @Test
    public void containsPartialWordIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsPartialWordIgnoreCase(null, "abc"));
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsPartialWordIgnoreCase_validInputs_correctResult() {
        // Empty sentence or keyword
        assertFalse(StringUtil.containsPartialWordIgnoreCase("", "abc")); // empty sentence
        assertThrows(IllegalArgumentException.class, () ->
                StringUtil.containsPartialWordIgnoreCase("abc", ""));

        // Sentence contains keyword as partial substring
        assertTrue(StringUtil.containsPartialWordIgnoreCase("paracetamol ibuprofen", "mol")); // partial middle
        assertTrue(StringUtil.containsPartialWordIgnoreCase("Vitamin C + Zinc", "vit")); // partial front
        assertTrue(StringUtil.containsPartialWordIgnoreCase("Amoxicillin", "illin")); // partial end

        // Keyword longer than any word in sentence – no match
        assertFalse(StringUtil.containsPartialWordIgnoreCase("aaa bbb ccc", "bbbbbb")); // too long

        // Case-insensitive match
        assertTrue(StringUtil.containsPartialWordIgnoreCase("AAA bBb ccc", "bbb")); // match with different case
        assertTrue(StringUtil.containsPartialWordIgnoreCase("Zyrtec (Cetirizine)", "CETI")); // caps vs lowercase
        assertTrue(StringUtil.containsPartialWordIgnoreCase("Dr. John's formula", "john")); // with punctuation

        // Sentence has leading/trailing/multiple spaces
        assertTrue(StringUtil.containsPartialWordIgnoreCase("   aaa   bbb   ccc   ", "bb")); // extra spaces
        assertTrue(StringUtil.containsPartialWordIgnoreCase("   Aaa", "aaa")); // leading spaces
        assertTrue(StringUtil.containsPartialWordIgnoreCase("ccc   ", "c")); // trailing spaces

        // Match is full word
        assertTrue(StringUtil.containsPartialWordIgnoreCase("abc def ghi", "def"));

        // Match is entire sentence
        assertTrue(StringUtil.containsPartialWordIgnoreCase("hydrocortisone", "hydrocortisone"));

        // Match is across symbols
        assertTrue(StringUtil.containsPartialWordIgnoreCase("Aspirin + Paracetamol", "+"));
        assertTrue(StringUtil.containsPartialWordIgnoreCase("Aspirin-Paracetamol", "-"));
        assertTrue(StringUtil.containsPartialWordIgnoreCase("Cough, Cold, Fever", ","));

        // No match
        assertFalse(StringUtil.containsPartialWordIgnoreCase("Ibuprofen", "paracetamol"));
        assertFalse(StringUtil.containsPartialWordIgnoreCase("abc def", "xyz"));
    }


    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
            .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getDetails(null));
    }

}
