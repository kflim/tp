package seedu.address.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewTaskListCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;


public class HelpWindow extends AnchorPane {
    public static final String USER_GUIDE_URL = "https://ay2122s1-cs2103t-w10-1.github.io/tp/UserGuide.html";
    public static final String USER_GUIDE_MESSAGE = "For full details, refer to the user guide: " + USER_GUIDE_URL;
    public static final String HELP_MESSAGE = "For more detailed commands, type \"help [command]\"\n"
            + "To close this window, type \"close\"";
    private static final String APPLICATION_ICON = "/images/address_book_32.png";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final Hashtable<String, CommandDetail> commandTable = new Hashtable<>();

    private static Stage stage;
    private static final boolean isActive = false;
    private static HelpWindow helpWindow;

    private interface CommandDetail {
        void execute();
    }

    @FXML
    private Label helpMessage;
    @FXML
    private TextField textField;
    @FXML
    private Label additionalInfo;
    @FXML
    private TableView<Command> tableView;
    @FXML
    private TableColumn<Command, String> command;
    @FXML
    private TableColumn<Command, String> description;
    @FXML
    private Label userGuideMessage;
    @FXML
    private Button copyButton;

    /**
     * Creates a new HelpWindow.
     */
    private HelpWindow() {
        stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/HelpWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            AnchorPane ap = fxmlLoader.load();
            assert ap != null;

            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Help");
            stage.setResizable(false);
            stage.getIcons().add(
                    new Image(Objects.requireNonNull(MainApp.class.getResourceAsStream(APPLICATION_ICON)))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        fillCommandTable();
        helpMessage.setText(HELP_MESSAGE);
        additionalInfo.setText("");
        userGuideMessage.setText(USER_GUIDE_MESSAGE);
        copyButton.setText("Copy URL");
    }

    public static HelpWindow getWindow() {
        if (!HelpWindow.isActive) {
            helpWindow = new HelpWindow();
        }
        return helpWindow;
    }

    /**
     * Sets up the respective children of the AnchorPane.
     */
    @FXML
    public void initialize() {
        setupTextField();
        setupTable();
        setupButton();
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return stage.isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        stage.hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        stage.requestFocus();
    }

    private void setupButton() {
        copyButton.setOnMousePressed(event -> copyUrl());
    }

    private void setupColumns() {
        command.setCellValueFactory(
                new PropertyValueFactory<>("command")
        );

        description.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );
    }

    private void setupTextField() {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleUserInput(textField.getText());
                textField.setText("");
            }
        });
    }

    private void setupTable() {
        setupColumns();
        setupTableData();
    }

    private void setupTableData() {
        Person samplePerson = new Person(
                new Name("Amy Bee"), new Phone("123456789"), new Email("amy@gmail.com"),
                new Address("123, Jurong West Ave 6, #08-111"), new HashSet<>(), new ArrayList<>(), new Description(""),
                false
        );
        EditCommand.EditPersonDescriptor descriptor = new EditCommand.EditPersonDescriptor();
        descriptor.setName(samplePerson.getName());
        descriptor.setPhone(samplePerson.getPhone());
        descriptor.setEmail(samplePerson.getEmail());
        descriptor.setAddress(samplePerson.getAddress());
        descriptor.setTags(samplePerson.getTags());
        descriptor.setImportance(samplePerson.isImportant());

        EditTaskCommand.EditTaskDescriptor taskDescriptor = new EditTaskCommand.EditTaskDescriptor();

        ObservableList<Command> data = FXCollections.observableArrayList(
                new AddCommand(samplePerson), new AddTaskCommand(Index.fromZeroBased(0), new ArrayList<>()),
                new ClearCommand(), new DeleteCommand(Index.fromZeroBased(0)),
                new DeleteTaskCommand(Index.fromZeroBased(0), new ArrayList<>()),
                new DoneCommand(Index.fromZeroBased(0), new ArrayList<>()),
                new EditCommand(Index.fromZeroBased(0), descriptor),
                new EditTaskCommand(Index.fromZeroBased(0), Index.fromZeroBased(0), taskDescriptor), new ExitCommand(),
                new FindCommand(null), new ListCommand(), new ReminderCommand(),
                new SortCommand(false),
                new UndoCommand(Index.fromZeroBased(0), new ArrayList<>()), new ViewTaskListCommand()
        );

        tableView.setItems(data);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setSelectionModel(null);
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USER_GUIDE_URL);
        clipboard.setContent(url);
    }

    private void fillCommandTable() {
        commandTable.put(AddCommand.COMMAND_WORD, this::handleAdd);
        commandTable.put(AddTaskCommand.COMMAND_WORD, this::handleAddTask);
        commandTable.put(ClearCommand.COMMAND_WORD, this::handleClear);
        commandTable.put(DeleteCommand.COMMAND_WORD, this::handleDelete);
        commandTable.put(DeleteTaskCommand.COMMAND_WORD, this::handleDelTask);
        commandTable.put(DoneCommand.COMMAND_WORD, this::handleDoneTask);
        commandTable.put(EditCommand.COMMAND_WORD, this::handleEdit);
        commandTable.put(EditTaskCommand.COMMAND_WORD, this::handleEditTask);
        commandTable.put(ExitCommand.COMMAND_WORD, this::handleExit);
        commandTable.put(FindCommand.COMMAND_WORD, this::handleFind);
        commandTable.put(ListCommand.COMMAND_WORD, this::handleList);
        commandTable.put(ReminderCommand.COMMAND_WORD, this::handleReminder);
        commandTable.put(SortCommand.COMMAND_WORD, this::handleSort);
        commandTable.put(UndoCommand.COMMAND_WORD, this::handleUndoTask);
        commandTable.put(ViewTaskListCommand.COMMAND_WORD, this::handleViewTask);
        commandTable.put("close", this::handleCloseWindow);
    }

    private void handleUserInput(String userInput) {
        String[] words = userInput.trim().split(" ");
        if (areValidWords(words)) {
            performCommand(words, userInput.startsWith("close"));
        }
    }

    private boolean areValidWords(String[] words) {
        if (words[0].equals("help") && words.length == 1) {
            additionalInfo.setText("Enter the command that you wish to query after \"help\"!");
            return false;
        } else if (words[0].equals("close") && words.length == 1
                || words.length == 2 && words[0].equals("help") && isValidCommand(words[1])) {
            return true;
        } else {
            additionalInfo.setText("That is not a valid command");
            return false;
        }
    }

    public boolean isValidCommand(String userInput) {
        return commandTable.containsKey(userInput);
    }

    private void performCommand(String[] userInput, boolean isClose) {
        if (isClose) {
            commandTable.get(userInput[0]).execute();
        } else {
            commandTable.get(userInput[1]).execute();
        }
    }

    private void handleAdd() {
        additionalInfo.setText("Format: "
                + "add -n NAME -p PHONE_NUMBER -e EMAIL -a ADDRESS [-l TAG]… [-d DESCRIPTION] [-impt IMPORTANCE]"
                + "\nA person can have any number of labels "
                + "\nOnly true or false will be accepted for the IMPORTANCE value"
        );
    }

    private void handleAddTask() {
        additionalInfo.setText("Format: "
                + "add INDEX -tn TASK_NAME [-td TASK_DATE] [-tt TASK_TIME] [-ta TASK_ADDRESS]"
                + "\nAdds a task to the person at the specified INDEX"
        );
    }

    private void handleClear() {
        additionalInfo.setText("Format: clear \nClears all entries from ContactSH.");
    }

    private void handleDelete() {
        additionalInfo.setText("Format: "
                + "rm INDEX [-A]"
                + "\nDeletes the person at the specified index IF it is valid. "
                + "\nIf the optional -A flag is given, all of the current data will be deleted"
        );
    }

    private void handleDelTask() {
        additionalInfo.setText("Format: rm INDEX -ti TASK_INDEX… \nDeletes a task from the specified person.");
    }

    private void handleDoneTask() {
        additionalInfo.setText("Format: "
                + "donetask INDEX -ti TASK_INDEX…"
                + "\nMarks tasks attached to the person at the specified INDEX as done"
        );
    }

    private void handleEdit() {
        additionalInfo.setText("Format: "
                + "edit INDEX -ti TASK_INDEX [-tn TASK_NAME] [-td TASK_DATE] [-tt TASK_TIME] [-ta TASK_ADDRESS]…"
                + "\nEdits the person at the specified index IF it is valid"
        );
    }

    private void handleEditTask() {
        additionalInfo.setText("Format: "
                + "edit INDEX -ti TASK_INDEX [-tn TASK_NAME] [-td TASK_DATE] [-tt TASK_TIME] [-ta TASK_ADDRESS]…"
                + "\nEdits a task attached to the person (at the specified INDEX) according to the TASK_INDEX"
                + "\nAt least one of the optional fields must be provided."
        );
    }

    private void handleExit() {
        additionalInfo.setText("Format: exit" + "\nExits the program entirely");
    }

    private void handleFind() {
        additionalInfo.setText("Format: "
                + "find [-n NAME] [-p PHONE_NUMBER] [-e EMAIL] [-a ADDRESS] [-l TAG]… [-d DESCRIPTION] "
                + "[-tn TASK_NAME] KEYWORD [MORE_KEYWORDS]"
                + "\nPersons matching all the keywords will be returned"
                + "\nAt least one of the optional fields must be provided"
        );
    }

    private void handleList() {
        additionalInfo.setText("Format: list" + "\nShows a list of all persons in ContactSh");
    }

    private void handleReminder() {
        additionalInfo.setText("Format: "
                + "reminder [-s DAYS]"
                + "\nShows the number of days prior to a task's date for the task to be reminded as due soon"
                + "\nIf the optional field is provided, it sets the number of days to the provided number"
        );
    }

    private void handleSort() {
        additionalInfo.setText("Format: "
                + "sort [-r]"
                + "\nSort persons by the alphabetical order of their name."
                + "\nIf the optional -r flag is provided, a list of persons sorted in reverse order is displayed"
        );
    }

    private void handleUndoTask() {
        additionalInfo.setText("Format: "
                + "undotask INDEX -ti TASK_INDEX…"
                + "\nMarks task(s) indicated by the TASK_INDEX of person indicated by INDEX as not done."
        );
    }

    private void handleViewTask() {
        additionalInfo.setText("Format: "
                + "cat INDEX [-f KEYWORDS]"
                + "\nShows the tasks of person specified person"
                + "\nIf the optional field KEYWORDS is given, a filtered list of tasks will be shown"
        );
    }

    private void handleCloseWindow() {
        stage.close(); // May update to have a timer if possible
    }
}
