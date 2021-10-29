package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate extends AttributeContainsKeywordsPredicate {

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Person person) {
        return descriptionContainsWords(person, keywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }

    /**
     * Checks if a person's name contains the specified keywords.
     *
     * @param person A {@code Person} whose description might match the keywords.
     * @param keywords A {@code List<String>} that might contain words that match the description of the person.
     * @throws java.lang.NullPointerException if person or keywords is null
     */
    public boolean descriptionContainsWords(Person person, List<String> keywords) {
        requireNonNull(keywords);
        requireNonNull(person);

        String preppedDesc = person.getDescription().toString().toLowerCase();
        return StringUtil.containsWordsInOrderIgnoreCase(preppedDesc, keywords);
    }
}
