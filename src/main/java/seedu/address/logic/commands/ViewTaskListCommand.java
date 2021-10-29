package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.TaskMatchesKeywordPredicate;

/**
 * Displays the task list of a specified person.<br>
 * Displays the task list of every person if "-A" flag is provided.
 */
public class ViewTaskListCommand extends Command {
    public static final String COMMAND_WORD = "cat";
    public static final String ALL_FLAG = "-A";
    public static final String DISPLAY_SINGLE_DESCRIPTION = "Displays the task list of a "
            + "person specified by index number"
            + " used in the displayed person list";
    public static final String DISPLAY_ALL_DESCRIPTION = "Displays the task list of every person";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": " + DISPLAY_SINGLE_DESCRIPTION + "\n"
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2\n"
            + COMMAND_WORD + " " + ALL_FLAG + ": " + DISPLAY_ALL_DESCRIPTION;

    public static final String MESSAGE_VIEW_TASKS_SUCCESS = "Viewing %1$s's tasks";

    public static final String MESSAGE_VIEW_TASKS_ALL_SUCCESS = "Viewing all task list.";

    private final Index targetIndex;

    private final boolean isDisplayAll;

    private List<String> keywords = new ArrayList<>();

    private boolean hasFilter = false;

    /** Constructor used if user wants to view all task lists. */
    public ViewTaskListCommand() {
        isDisplayAll = true;
        targetIndex = Index.fromOneBased(1);
    }

    /**
     * Constructor used if user wants to find within the view all task list panel.
     */
    public ViewTaskListCommand(List<String> keywords) {
        isDisplayAll = true;
        hasFilter = true;
        this.keywords = keywords;
        targetIndex = Index.fromOneBased(1);
    }

    /** Constructor used if user wants to view a specific {@code Person}'s task list . */
    public ViewTaskListCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        isDisplayAll = false;
    }

    /** Constructor for ViewTaskListCommand. */
    public ViewTaskListCommand(Index targetIndex, List<String> keywords) {
        this.targetIndex = targetIndex;
        this.keywords = keywords;
        this.hasFilter = true;
        isDisplayAll = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isDisplayAll) {
            CommandResult cr = new CommandResult(MESSAGE_VIEW_TASKS_ALL_SUCCESS);
            if (hasFilter) {
                model.setViewAllTasksFindPred(new TaskMatchesKeywordPredicate(keywords));
            } else {
                model.setViewAllTasksFindPred(task -> true);
            }
            cr.setDisplayAllTaskList();
            cr.setWriteCommand();
            return cr;
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        if ((targetIndex.getZeroBased() >= lastShownList.size())) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(targetIndex.getZeroBased());
        if (hasFilter) {
            TaskMatchesKeywordPredicate predicate = new TaskMatchesKeywordPredicate(keywords);
            model.displayFilteredPersonTaskList(personToView, predicate);
        } else {
            model.displayPersonTaskList(personToView);
        }

        CommandResult cr = new CommandResult(String.format(MESSAGE_VIEW_TASKS_SUCCESS, personToView.getName()));
        cr.setDisplaySingleTaskList();
        return cr;
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTaskListCommand // instanceof handles nulls
                && targetIndex.equals(((ViewTaskListCommand) other).targetIndex)
                && keywords.equals(((ViewTaskListCommand) other).keywords)
                && hasFilter == ((ViewTaskListCommand) other).hasFilter); // state check
    }

    public String getCommand() {
        return COMMAND_WORD;
    }

    public String getDescription() {
        return "Displays the task list of a person specified by index number used in the displayed person list";
    }
}
