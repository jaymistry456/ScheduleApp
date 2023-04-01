package ScheduleApp.Tasks.TaskOperations;

import ScheduleApp.Main;
import ScheduleApp.Tasks.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewTask {
    static Task newMeeting;

    public static void displayTaskDetails(Task t) throws IOException {
        newMeeting = null;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view-task.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Task Details");

        Scene secondaryScene = new Scene(root, 700, 560);

        Label taskName = (Label) secondaryScene.lookup("#taskName");
        Label taskDescription = (Label) secondaryScene.lookup("#taskDescription");
        Label taskDeadline = (Label) secondaryScene.lookup("#taskDeadline");
        Label taskTime = (Label) secondaryScene.lookup("#taskTime");
        Label taskStatus = (Label) secondaryScene.lookup("#taskStatus");
        Button taskClose = (Button) secondaryScene.lookup("#meetingClose");

        taskName.setText(t.getTaskName());
        taskDescription.setText(t.getTaskDescription());
        taskDeadline.setText(t.getDeadline());
        taskTime.setText(t.getHours() + ":" + t.getMinutes() + " " + t.getAMPM());
        taskStatus.setText(t.getStatus());
        taskClose.setOnAction(e -> {
            secondaryStage.close();
        });

        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();
    }
}
