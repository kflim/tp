package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RemindCommand} object.
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {
    private static final String VALID_SET_REMINDER_REGEX = "-s\\s.*";
    private final Pattern validSetReminder = Pattern.compile(VALID_SET_REMINDER_REGEX);

    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@code RemindCommand} and returns a RemindCommand object
     * for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ReminderCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        Matcher matcherSetReminder = validSetReminder.matcher(trimmedArgs);

        if (matcherSetReminder.matches()) {
            try {
                String daysPriorToTaskDateArg = extractReminderDaysArg(trimmedArgs);
                int daysPriorToTaskDate = Integer.parseInt(daysPriorToTaskDateArg);
                return new ReminderCommand(daysPriorToTaskDate);
            } catch (NumberFormatException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
            }
        } else if (trimmedArgs.isEmpty()) {
            return new ReminderCommand();
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }
    }

    private String extractReminderDaysArg(String args) {
        String daysPriorToTaskDateArg = args.substring(2);
        return daysPriorToTaskDateArg.trim();
    }
}

