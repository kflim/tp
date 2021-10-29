package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Shows/Sets the number of days prior to a task's date and time for the task to be
 * reminded as due soon.
 */
public class ReminderCommand extends Command {
    public static final String COMMAND_WORD = "reminder";
    public static final String SET_FLAG = " -s";
    public static final String NO_FLAG_DESCRIPTION = "Shows the number of days prior to a task's"
            + " date for the task to be reminded as due soon.";
    public static final String HAS_FLAG_DESCRIPTION = "Sets the number of days prior to a task's"
            + " date for the task to be reminded as due soon.\n"
            + "Parameters: DAYS (must be a positive integer)\n"
            + "Example: reminder -s 21";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + NO_FLAG_DESCRIPTION + "\n"
            + COMMAND_WORD + SET_FLAG + ": " + HAS_FLAG_DESCRIPTION;
    public static final String MESSAGE_SET_REMINDER_SUCCESS = "Days prior to remind is set to %d";
    public static final String MESSAGE_SHOW_REMINDER_SUCCESS = "Days prior to remind: %d";

    private final boolean isSet;
    private final int daysPriorToTaskDate;


    /**
     * Constructor to use if "-s" flag is not provided.
     */
    public ReminderCommand() {
        this.isSet = false;
        this.daysPriorToTaskDate = Task.getDaysPriorToTaskDate();
    }

    /**
     * Constructor to use if "-s" flag is provided.
     */
    public ReminderCommand(int daysPriorToTaskDate) {
        this.isSet = true;
        this.daysPriorToTaskDate = daysPriorToTaskDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (isSet) {
            if (daysPriorToTaskDate <= 0) {
                throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DAY_VALUE);
            }
            Task.setDaysPriorToTaskDate(daysPriorToTaskDate);
            return new CommandResult(String.format(MESSAGE_SET_REMINDER_SUCCESS, daysPriorToTaskDate));
        } else {
            return new CommandResult(String.format(MESSAGE_SHOW_REMINDER_SUCCESS, daysPriorToTaskDate));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderCommand // instanceof handles nulls
                && isSet == ((ReminderCommand) other).isSet
                && (!isSet || daysPriorToTaskDate == ((ReminderCommand) other).daysPriorToTaskDate));
    }

    public String getCommand() {
        return COMMAND_WORD;
    }

    public String getDescription() {
        return NO_FLAG_DESCRIPTION;
    }
}
