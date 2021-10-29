package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskListCard.fxml";

    public final Task task;
    public final int displayedIndex;

    @FXML
    private HBox descriptionPane;
    @FXML
    private Label id;
    @FXML
    private Label taskName;
    @FXML
    private Label taskDate;
    @FXML
    private Label taskTime;
    @FXML
    private Label taskVenue;

    /**
     * Creates a {@code TaskCard} with the given {@code Task} and index to display.
     */
    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        this.displayedIndex = displayedIndex;
        taskName.setText("Task: " + task.getTaskName().taskName);
        String taskDateStr = task.getDate() == null ? "" : "Date: " + task.getDate().taskDate.toString();
        String taskTimeStr = task.getTime() == null ? "" : "Time: " + task.getTime().taskTime.toString();
        String taskVenueStr = task.getVenue() == null ? "" : "Venue: " + task.getVenue().venue;
        taskDate.setText(taskDateStr);
        taskTime.setText(taskTimeStr);
        taskVenue.setText(taskVenueStr);
    }

    /**
     * Initialises properties of FXML nodes of this TaskCard using {@code vbox}'s properties.
     * @param value The extra amount to be reduced from the Text's width.
     */
    public void initialise(VBox vbox, double value) {

        // Sets width property of HBox to be dependent on VBox's width and padding property.
        descriptionPane.maxWidthProperty().bind(Bindings.createDoubleBinding(()->
                vbox.getWidth() - vbox.getPadding().getLeft() - vbox.getPadding().getRight() - 1,
                vbox.widthProperty(), vbox.paddingProperty()));

        // Sets width property of Text to be dependent on VBox's width and padding property.
        taskName.maxWidthProperty().bind(Bindings.createDoubleBinding(()->
                vbox.getWidth() - vbox.getPadding().getLeft() - vbox.getPadding().getRight() - value,
                vbox.widthProperty(), vbox.paddingProperty()));

        id.setText(displayedIndex + ". ");

        taskDate.setBackground(new Background(
                new BackgroundFill(javafx.scene.paint.Paint.valueOf("blue"), CornerRadii.EMPTY, Insets.EMPTY)));

        taskTime.setBackground(new Background(
                new BackgroundFill(javafx.scene.paint.Paint.valueOf("blueviolet"), CornerRadii.EMPTY, Insets.EMPTY)));

        taskVenue.setBackground(new Background(
                new BackgroundFill(javafx.scene.paint.Paint.valueOf("brown"), CornerRadii.EMPTY, Insets.EMPTY)));

        if (task.getDone()) {
            descriptionPane.setBackground(new Background(
                    new BackgroundFill(javafx.scene.paint.Paint.valueOf("green"), CornerRadii.EMPTY, Insets.EMPTY)));
        }
        task.updateDueDate();
        if (task.getIsDueSoon()) {
            descriptionPane.setBackground(new Background(
                    new BackgroundFill(javafx.scene.paint.Paint.valueOf("darkorange"),
                    CornerRadii.EMPTY, Insets.EMPTY)));
        }
        if (task.getIsOverdue()) {
            descriptionPane.setBackground(new Background(
                    new BackgroundFill(javafx.scene.paint.Paint.valueOf("red"), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
}
