package seedu.address.ui.taskui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox cardbox;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label taskTime;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");

        if (task.getDescription() != null) {
            description.setText(task.getDescription().toString());
        } else {
            description.setText("");
        }

        if (!task.getTimeString().isEmpty()) {
            taskTime.setText(task.getTimeString());
        } else {
            taskTime.setText("");
            cardbox.getChildren().remove(taskTime);
        }
        cardbox.setMinHeight(0);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
