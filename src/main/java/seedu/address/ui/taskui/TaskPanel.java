package seedu.address.ui.taskui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of tasks for a module.
 */
public class TaskPanel extends UiPart<Region> {

    private static final String FXML = "TaskPanel.fxml";

    @FXML
    private ListView<Task> tasksView;

    public TaskPanel(ObservableList<Task> taskList) {
        super(FXML);
        tasksView.setItems(taskList);
        tasksView.setCellFactory(listView -> new TaskPanel.TaskListViewCell());
        tasksView.setPadding(new Insets(0));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
                setStyle("-fx-background-color: #9d6365; -fx-background-radius: 15px; "
                        + "-fx-background-insets: 3px, 0px; -fx-padding: 10px");
            }
        }
    }

}
