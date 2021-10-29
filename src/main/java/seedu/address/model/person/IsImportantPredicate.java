package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.StringUtil;

public class IsImportantPredicate extends AttributeContainsKeywordsPredicate {

    public IsImportantPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Person person) {
        return isImportantPerson(person, keywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsImportantPredicate // instanceof handles nulls
                && keywords.equals(((IsImportantPredicate) other).keywords)); // state check
    }

    /**
     * Checks if a person's isImportant is equal to keywords.
     *
     * @param person A {@code Person} whose isImportant might match the keywords.
     * @param keywords A {@code List<String>} that might contain words that match the isImportant of the person.
     * @throws java.lang.NullPointerException if person or keywords is null
     */
    public boolean isImportantPerson(Person person, List<String> keywords) {
        requireNonNull(keywords);
        requireNonNull(person);

        String preppedImportant = String.valueOf(person.isImportant()).toLowerCase();
        return StringUtil.containsWordsInOrderIgnoreCase(preppedImportant, keywords);
    }
}
