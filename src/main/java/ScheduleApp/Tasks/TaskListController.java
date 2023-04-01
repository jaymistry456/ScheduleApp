package ScheduleApp.Tasks;

import ScheduleApp.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.IOException;

public class TaskListController {
    @FXML
    private ToggleGroup tasksGroup;
    @FXML
    private ToggleButton today;
    @FXML
    private ToggleButton upcoming;
    @FXML
    private ToggleButton starred;
    @FXML
    private ToggleButton bucketlist;
    @FXML
    private ToggleButton deleted;
    @FXML
    private Label taskListType;
    private BorderPane root;

    public void setBorderPane(BorderPane root) {
        this.root = root;
    }

    @FXML
    public void initialize() throws IOException {
        taskListType.setText("Today's Tasks");
        taskListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
        try {
            tasksGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
                public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {

                    if (new_toggle==null || new_toggle==today) {
                        taskListType.setText("Today's Tasks");
                        taskListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
                        try {
                            Main.taskListModel.setTaskType("todays");
                            Main.taskListView.displayList("todays");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        root.setBottom(Main.taskListView);
                    }
                    else if (new_toggle==upcoming) {
                        taskListType.setText("Upcoming Tasks");
                        taskListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
                        try {
                            Main.taskListModel.setTaskType("upcoming");
                            Main.taskListView.displayList("upcoming");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        root.setBottom(Main.taskListView);
                    }
                    else if (new_toggle==starred) {
                        taskListType.setText("Important Tasks");
                        taskListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
                        try {
                            Main.taskListModel.setTaskType("starred");
                            Main.taskListView.displayList("starred");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        root.setBottom(Main.taskListView);
                    }
                    else if(new_toggle==deleted) {
                        taskListType.setText("Deleted Tasks");
                        taskListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
                        try {
                            Main.taskListModel.setTaskType("deleted");
                            Main.taskListView.displayList("deleted");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        root.setBottom(Main.taskListView);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

