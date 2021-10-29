package seedu.address.ui;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Panel containing the task list of every person.
 */
public class AllTaskListPanel extends UiPart<Region> {
    private static final String FXML = "AllTaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private VBox vbox;
    @FXML
    private Label allTaskListPanelHeader;
    @FXML
    private TreeView<Tuple> allTaskListView;

    private TreeItem<Tuple> dummyNode;

    /**
     * Creates a {@code AllTaskListPanel} with the given {@code ObservableList}.
     */
    public AllTaskListPanel(ObservableList<Person> personList) {
        super(FXML);

        // Set up the treeNode.
        Tuple dummyTuple = new Tuple(new Name("dummy"), true, new Task("dummy"), 0);
        dummyNode = new TreeItem<>(dummyTuple);
        allTaskListView.setRoot(dummyNode);

        updateRootNode(personList);

        allTaskListView.showRootProperty().set(false);
        allTaskListView.setCellFactory(listView -> new AllTaskListPanel.AllTaskListViewCell());
    }

    /**
     * Updates the TreeView with the newly updated {@code personList} to show changes.
     */
    public void updateTreeView(ObservableList<Person> personList) {
        if (personList.isEmpty()) {
            allTaskListView.getRoot().getChildren().clear();
        } else {
            updateRootNode(personList);
        }
    }

    /**
     * Updates the root node with the newly updated {@code personList} to show changes.
     */
    private void updateRootNode(ObservableList<Person> personList) {
        requireNonNull(personList);

        ObservableList<TreeItem<Tuple>> childNodes = allTaskListView.getRoot().getChildren();

        int i = 0;
        int j = 0;
        for (; j < personList.size(); j++) {
            for (; i < childNodes.size() && j < personList.size(); i++) {
                Name nodeName = childNodes.get(i).getValue().getName();
                Person person = personList.get(j);
                Name personName = person.getName();

                if (!nodeName.toString().equals(personName.toString())) {
                    childNodes.remove(i);
                    i--;
                    continue;
                }
                updateChildNode(childNodes.get(i).getChildren(), personList.get(j));
                j++;
            }
            if (j >= personList.size()) {
                break;
            }
            addNewChildNode(childNodes, personList.get(j));
            i++;
        }
        // To clear trailing childNodes.
        childNodes.remove(i, childNodes.size());
    }

    /**
     * Adds a new child node under the root node.
     */
    private void addNewChildNode(ObservableList<TreeItem<Tuple>> childNodes, Person person) {
        requireAllNonNull(childNodes, person);

        Tuple childNodeTuple = new Tuple(person.getName(), true, new Task("dummy"), 0);
        TreeItem<Tuple> childNode = new TreeItem<>(childNodeTuple);
        childNode.setExpanded(true);

        // Listener to make child node non-collapsable.
        childNode.expandedProperty().addListener(observable -> {
            if (!childNode.isExpanded()) {
                childNode.setExpanded(true);
            }
        });

        int i = 0;
        for (Task task : person.getTasks()) {
            Tuple childNodeTaskTuple = new Tuple(person.getName(), false, task, i++ + 1);
            TreeItem<Tuple> childNodeTask = new TreeItem<>(childNodeTaskTuple);
            childNode.getChildren().add(childNodeTask);
        }

        childNodes.add(childNode);
    }

    /**
     * Updates the task nodes under the specified child node.
     */
    private void updateChildNode(ObservableList<TreeItem<Tuple>> childNodeTasks, Person person) {
        requireAllNonNull(childNodeTasks, person);

        int i = 0;
        int j = 0;
        for (; j < person.getTasks().size(); j++) {
            for (; i < childNodeTasks.size() && j < person.getTasks().size(); i++) {
                Task task = person.getTasks().get(j);
                Task childTask = childNodeTasks.get(i).getValue().getTask();

                if (!task.equals(childTask)) {
                    childNodeTasks.remove(i);
                    i--;
                    continue;
                }
                childNodeTasks.get(i).getValue().setIndex(i + 1);
                j++;
            }

            if (j >= person.getTasks().size()) {
                break;
            }

            TreeItem<Tuple> newChildNodeTask = new TreeItem<>(
                    new Tuple(person.getName(), false, person.getTasks().get(j), i + 1));
            childNodeTasks.add(newChildNodeTask);
            i++;
        }
        // To clear trailing childNodes.
        childNodeTasks.remove(i, childNodeTasks.size());
    }

    /**
     * Custom {@code TreeCell} that displays the graphics of a {@code Task} using a {@code TaskCard}
     * or a {@code Name}.
     */
    class AllTaskListViewCell extends TreeCell<Tuple> {
        @Override
        protected void updateItem(Tuple tuple, boolean empty) {
            super.updateItem(tuple, empty);

            if (empty || tuple == null) {
                setGraphic(null);
                setText(null);
            } else {
                boolean isNameHeader = tuple.isNameHeader();

                if (isNameHeader) {
                    Name name = tuple.getName();
                    Label label = new Label(name.toString());
                    label.setStyle("-fx-text-fill: cornsilk; -fx-font-weight: bold;");
                    setGraphic(label);
                } else {
                    Task task = tuple.getTask();
                    TaskCard tc = new TaskCard(task, tuple.getIndex());
                    tc.initialise(vbox, 150);
                    setGraphic(tc.getRoot());
                }
            }
        }
    }
}
