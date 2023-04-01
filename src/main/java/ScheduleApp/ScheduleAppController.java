package ScheduleApp;

import ScheduleApp.Meetings.MeetingListController;
import ScheduleApp.Shopping.ShoppingListController;
import ScheduleApp.Tasks.TaskListController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ScheduleAppController {
    @FXML
    private Label taskListTitle;
    @FXML
    private Label meetingListTitle;
    @FXML
    private Label shoppingListTitle;
    private BorderPane root;

    public void setBorderPane(BorderPane root) {
        this.root = root;
    }

    public BorderPane getBorderPane() {
        return this.root;
    }

    @FXML
    public void initialize() throws IOException {
        taskListTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, (event -> {
            taskListTitle.setStyle("-fx-font-size: 30; -fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: lightblue;");
            meetingListTitle.setStyle("-fx-font-size: 30;");
            shoppingListTitle.setStyle("-fx-font-size: 30;");
            FXMLLoader taskListFxmlLoader = new FXMLLoader(Main.class.getResource("task-list-view.fxml"));
            try {
                root.setCenter(taskListFxmlLoader.load());
                ((TaskListController) taskListFxmlLoader.getController()).setBorderPane(root);
                root.setBottom(Main.taskListView);
                taskListTitle.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        meetingListTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, (event -> {
            taskListTitle.setStyle("-fx-font-size: 30;");
            meetingListTitle.setStyle("-fx-font-size: 30; -fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: lightblue;");
            shoppingListTitle.setStyle("-fx-font-size: 30;");
            FXMLLoader meetingListFxmlLoader = new FXMLLoader(Main.class.getResource("meeting-list-view.fxml"));
            try {
                root.setCenter(meetingListFxmlLoader.load());
                ((MeetingListController) meetingListFxmlLoader.getController()).setBorderPane(root);
                root.setBottom(Main.meetingListView);
                taskListTitle.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }));

        shoppingListTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, (event -> {
            taskListTitle.setStyle("-fx-font-size: 30;");
            meetingListTitle.setStyle("-fx-font-size: 30;");
            shoppingListTitle.setStyle("-fx-font-size: 30; -fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: lightblue;");
            FXMLLoader shoppingListFxmlLoader = new FXMLLoader(Main.class.getResource("shopping-list-view.fxml"));
            try {
                root.setCenter(shoppingListFxmlLoader.load());
                ((ShoppingListController) shoppingListFxmlLoader.getController()).setBorderPane(root);
                root.setBottom(Main.shoppingListView);
                taskListTitle.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}

