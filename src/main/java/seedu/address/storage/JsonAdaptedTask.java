package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.Venue;

public class JsonAdaptedTask {

    public static final String MISSING_NAME_MESSAGE_FORMAT = "Task name field is missing!";

    private final String daysPriorToTaskDate;
    private final String taskName;
    private final String taskDate;
    private final String taskTime;
    private final String taskVenue;
    private final String isDone;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given {@code task}.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("daysPriorToTaskDate") String daysPriorToTaskDate,
                           @JsonProperty("taskName") String taskName,
                           @JsonProperty("taskDate") String taskDate, @JsonProperty("taskTime") String taskTime,
                           @JsonProperty("taskVenue") String taskVenue, @JsonProperty("isDone") String isDone) {
        this.daysPriorToTaskDate = daysPriorToTaskDate;
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskVenue = taskVenue;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        this.daysPriorToTaskDate = String.valueOf(Task.getDaysPriorToTaskDate());
        this.taskName = source.getTaskName().taskName;
        this.taskDate = source.getDate() == null
                ? null
                : source.getDate().toString();
        this.taskTime = source.getTime() == null
                ? null
                : source.getTime().toString();
        this.taskVenue = source.getVenue() == null
                ? null
                : source.getVenue().venue;
        this.isDone = String.valueOf(source.getDone());
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Task toModelType() throws IllegalValueException {

        if (taskName == null) {
            throw new IllegalValueException(MISSING_NAME_MESSAGE_FORMAT);
        }
        if (!TaskName.isValidTaskName(taskName)) {
            throw new IllegalValueException(TaskName.MESSAGE_CONSTRAINTS);
        }
        final TaskName modelName = new TaskName(taskName);

        TaskDate modelDate = null;
        if (taskDate != null) {
            if (TaskDate.isValidTaskDate(taskDate)) {
                modelDate = new TaskDate(taskDate);
            } else {
                throw new IllegalValueException(TaskDate.MESSAGE_CONSTRAINTS);
            }
        }

        TaskTime modelTime = null;
        if (taskTime != null) {
            if (TaskTime.isValidTaskTime(taskTime)) {
                modelTime = new TaskTime(taskTime);
            } else {
                throw new IllegalValueException(TaskTime.MESSAGE_CONSTRAINTS);
            }
        }

        Venue modelVenue = null;
        if (taskVenue != null) {
            if (Venue.isValidVenue(taskVenue)) {
                modelVenue = new Venue(taskVenue);
            } else {
                throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
            }
        }

        boolean modelIsDone = Boolean.parseBoolean(isDone);

        Task newTask = new Task(modelName, modelDate, modelTime, modelVenue);
        if (modelIsDone) {
            newTask.setDone();
        }

        int modelDaysPriorToTaskDate = Integer.parseInt(daysPriorToTaskDate);
        Task.setDaysPriorToTaskDate(modelDaysPriorToTaskDate);

        return newTask;
    };
}
