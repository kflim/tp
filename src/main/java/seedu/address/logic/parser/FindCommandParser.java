package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.DescriptionContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.IsImportantPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainKeywordsPredicate;
import seedu.address.model.person.TasksContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] flagAndKeywords = trimmedArgs.split(" ", 2);
        if (trimmedArgs.isEmpty() || flagAndKeywords.length == 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        } else {
            String[] nameKeywords = flagAndKeywords[1].split("\\s+");
            switch (flagAndKeywords[0]) {
            case "-n":
                return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
            case "-p":
                return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
            case "-e":
                return new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
            case "-a":
                return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
            case "-l":
                return new FindCommand(new TagsContainKeywordsPredicate(Arrays.asList(nameKeywords)));
            case "-d":
                return new FindCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
            case "-tn":
                return new FindCommand(new TasksContainKeywordsPredicate(Arrays.asList(nameKeywords)));
            case "-impt":
                return new FindCommand(new IsImportantPredicate(Arrays.asList(nameKeywords)));
            default:
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
    }

}
