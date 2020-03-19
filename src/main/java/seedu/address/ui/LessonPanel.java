package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;


/**
 * Panel containing the list of lessons.
 */
public class LessonPanel extends UiPart<Region> {
    private static final String FXML = "LessonPanel.fxml";

    @FXML
    private Label title;

    @FXML
    private ListView<Lesson> lessons;

    public LessonPanel(ObservableList<Lesson> lessonList) {
        super(FXML);
        lessons.setItems(lessonList);
        lessons.setCellFactory(listView -> new LessonPanel.LessonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Lesson} using a {@code LessonCard}.
     */
    class LessonListViewCell extends ListCell<Lesson> {
        @Override
        protected void updateItem(Lesson lesson, boolean empty) {
            super.updateItem(lesson, empty);

            if (empty || lesson == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LessonModCard(lesson, getIndex() + 1).getRoot());
            }
        }
    }
}
