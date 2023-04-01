package ScheduleApp;

import ScheduleApp.Meetings.MeetingListController;
import ScheduleApp.Meetings.MeetingListModel;
import ScheduleApp.Meetings.MeetingListView;
import ScheduleApp.Shopping.ShoppingListController;
import ScheduleApp.Shopping.ShoppingListModel;
import ScheduleApp.Shopping.ShoppingListView;
import ScheduleApp.Tasks.TaskListController;
import ScheduleApp.Tasks.TaskListModel;
import ScheduleApp.Tasks.TaskListView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main extends Application {
    public static final TaskListModel taskListModel = new TaskListModel();
    public static final MeetingListModel meetingListModel = new MeetingListModel();
    public static final ShoppingListModel shoppingListModel = new ShoppingListModel();

    public static final TaskListView taskListView = new TaskListView();
    public static final TaskListController taskListController = new TaskListController();

    public static final MeetingListView meetingListView = new MeetingListView();
    public static final MeetingListController meetingListController = new MeetingListController();

    public static final ShoppingListView shoppingListView = new ShoppingListView();

    public static final ShoppingListController shoppingListController = new ShoppingListController();



    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader toDoListFxmlLoader = new FXMLLoader(Main.class.getResource("schedule-app-view.fxml"));
        BorderPane root = toDoListFxmlLoader.load();
        ((ScheduleAppController) toDoListFxmlLoader.getController()).setBorderPane(root);

        try {
            FXMLLoader taskListCentreFxmlLoader = new FXMLLoader(Main.class.getResource("task-list-view.fxml"));
            root.setCenter(taskListCentreFxmlLoader.load());
            root.setBottom(taskListView);
            ((TaskListController) taskListCentreFxmlLoader.getController()).setBorderPane(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");


        // Task sample data
        taskListModel.addTaskInTodaysTaskList("Task name", "Description", formatter.format(date), "11", "55", "PM", "Completed");
        taskListModel.addTaskInTodaysTaskList("Task name 2", "Description 2", formatter.format(date), "11", "55", "PM", "In Progress");

        // Meeting sample data
        meetingListModel.addMeetingInTodaysMeetingList("Meeting1", "Description", formatter.format(date), "11", "00", "PM", "11", "55", "PM", "55", "Completed");
        meetingListModel.addMeetingInTodaysMeetingList("Meeting2", "Description2", formatter.format(date), "11", "00", "PM", "11", "55", "PM", "55", "Completed");

        // Shopping sample data
        shoppingListModel.addItemInShoppingList("Bananas", false);
        shoppingListModel.addItemInShoppingList("Apples", false);

        Scene scene = new Scene(root, 1400, 800);


        primaryStage.setTitle("The Ultimate Schedule App!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}