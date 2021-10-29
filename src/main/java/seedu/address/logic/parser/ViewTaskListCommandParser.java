package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewTaskListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ViewTaskListCommand} object.
 */
public class ViewTaskListCommandParser implements Parser<ViewTaskListCommand> {
    private static final String VALID_INPUT_WITH_FLAGS_REGEX = "[0-9]\\s-f\\s[\\w\\s]+";
    private static final String VALID_INPUT_WITHOUT_FLAGS_REGEX = "[0-9]+";
    private static final String VALID_VIEW_ALL_REGEX = "-A";
    private static final String VALID_VIEW_ALL_REGEX_WITH_FLAG = "-A[\\s]*-f\\s[\\w\\s]+";

    private final Pattern validPatternWithoutFlags = Pattern.compile(VALID_INPUT_WITHOUT_FLAGS_REGEX);
    private final Pattern validPatternWithFlags = Pattern.compile(VALID_INPUT_WITH_FLAGS_REGEX);
    private final Pattern validViewAllWithoutFind = Pattern.compile(VALID_VIEW_ALL_REGEX);
    private final Pattern validViewAllWithFind = Pattern.compile(VALID_VIEW_ALL_REGEX_WITH_FLAG);

    private Matcher matcherWithoutFlags;
    private Matcher matcherWithFlags;
    private Matcher matcherViewAllWithoutFind;
    private Matcher matcherViewAllWithFind;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@code ViewTaskListCommand} and returns a ViewTaskListCommand object
     * for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewTaskListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        setMatchers(trimmedArgs);

        if (matcherViewAllWithoutFind.matches()) {
            return new ViewTaskListCommand();
        } else if (matcherViewAllWithFind.matches()) {
            return new ViewTaskListCommand(extractKeywords(trimmedArgs));
        } else {
            try {
                if (matcherWithoutFlags.matches()) {
                    Index index = ParserUtil.parseIndex(trimmedArgs);
                    return new ViewTaskListCommand(index);
                }
                if (matcherWithFlags.matches()) {
                    String[] flagAndKeywords = trimmedArgs.split(" ");
                    Index index = ParserUtil.parseIndex(flagAndKeywords[0]);
                    List<String> keywords = Arrays.asList(flagAndKeywords[2].split(" "));
                    return new ViewTaskListCommand(index, keywords);
                } else {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTaskListCommand.MESSAGE_USAGE));
                }
            } catch (ParseException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTaskListCommand.MESSAGE_USAGE));
            }
        }
    }

    private void setMatchers(String args) {
        matcherWithoutFlags = validPatternWithoutFlags.matcher(args);
        matcherWithFlags = validPatternWithFlags.matcher(args);
        matcherViewAllWithoutFind = validViewAllWithoutFind.matcher(args);
        matcherViewAllWithFind = validViewAllWithFind.matcher(args);
    }

    private List<String> extractKeywords(String args) {
        int idx = args.indexOf("-f");
        String keywords = args.substring(idx + 2).trim();
        List<String> keywordList = new ArrayList<String>(Arrays.asList(keywords.split(" ")));
        keywordList.removeIf(s -> s.equals(""));
        return keywordList;
    }
}
