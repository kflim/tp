package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "man";

    public static final String DESCRIPTION = "Shows program usage instructions.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": " + DESCRIPTION + "\n"
            + "Parameters: " + "[COMMAND]\n"
            + "Example: " + COMMAND_WORD + " add";


    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private boolean isShowHelp = false;

    private String commandWord = null;

    /**
     * Constructor for a HelpCommand to show help window.
     */
    public HelpCommand() {
        isShowHelp = true;
    }

    /**
     * Constructor for a HelpCommand to show help for a specified command.
     * @param commandSpecified The command specified to show help for.
     */
    public HelpCommand(String commandSpecified) {
        this.commandWord = commandSpecified;
    }

    @Override
    public CommandResult execute(Model model) {
        if (isShowHelp) {
            return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        } else {
            requireNonNull(commandWord);
            CommandResult commandResult = new CommandResult(DESCRIPTION);

            switch (commandWord) {

            case AddCommand.COMMAND_WORD:
                commandResult = new CommandResult(AddCommand.MESSAGE_USAGE);
                break;

            case EditCommand.COMMAND_WORD:
                commandResult = new CommandResult(EditCommand.MESSAGE_USAGE);
                break;

            case DeleteCommand.COMMAND_WORD:
                commandResult = new CommandResult(DeleteCommand.MESSAGE_USAGE);
                break;

            case ClearCommand.COMMAND_WORD:
                commandResult = new CommandResult(ClearCommand.MESSAGE_USAGE);
                break;

            case FindCommand.COMMAND_WORD:
                commandResult = new CommandResult(FindCommand.MESSAGE_USAGE);
                break;

            case SortCommand.COMMAND_WORD:
                commandResult = new CommandResult(SortCommand.MESSAGE_USAGE);
                break;

            case ListCommand.COMMAND_WORD:
                commandResult = new CommandResult(ListCommand.MESSAGE_USAGE);
                break;

            case ExitCommand.COMMAND_WORD:
                commandResult = new CommandResult(ExitCommand.MESSAGE_USAGE);
                break;

            case HelpCommand.COMMAND_WORD:
                commandResult = new CommandResult(HelpCommand.MESSAGE_USAGE);
                break;

            case AddTaskCommand.COMMAND_WORD:
                commandResult = new CommandResult(AddTaskCommand.MESSAGE_USAGE);
                break;

            case DeleteTaskCommand.COMMAND_WORD:
                commandResult = new CommandResult(DeleteTaskCommand.MESSAGE_USAGE);
                break;

            case EditTaskCommand.COMMAND_WORD:
                commandResult = new CommandResult(EditTaskCommand.MESSAGE_USAGE);
                break;

            case ViewTaskListCommand.COMMAND_WORD:
                commandResult = new CommandResult(ViewTaskListCommand.MESSAGE_USAGE);
                break;

            case DoneCommand.COMMAND_WORD:
                commandResult = new CommandResult(DoneCommand.MESSAGE_USAGE);
                break;

            case UndoCommand.COMMAND_WORD:
                commandResult = new CommandResult(UndoCommand.MESSAGE_USAGE);
                break;

            default:
                break;
            }

            return commandResult;
        }
    }

    public String getCommand() {
        return COMMAND_WORD;
    }

    public String getDescription() {
        return DESCRIPTION;
    }
}
