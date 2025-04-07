package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.medicineusage.MedicineName;

/**
 * Tests that a {@code Person}'s {@code Medicine Usage}s matches any of the keywords given.
 */
public class MedicineUsageContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public MedicineUsageContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        List<MedicineName> medicineNames = person.getMedicineUsageNames();
        return keywords.stream().anyMatch(keyword ->
                medicineNames.stream().anyMatch(name ->
                        StringUtil.containsPartialWordIgnoreCase(name.toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MedicineUsageContainsKeywordsPredicate)) {
            return false;
        }

        MedicineUsageContainsKeywordsPredicate otherMedicineUsageContainsKeywordsPredicate =
                (MedicineUsageContainsKeywordsPredicate) other;
        return keywords.equals(otherMedicineUsageContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
