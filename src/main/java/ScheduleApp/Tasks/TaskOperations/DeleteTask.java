package ScheduleApp.Tasks.TaskOperations;

import ScheduleApp.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteTask {

    static Boolean deleteTask = false;

    public static Boolean deleteTask(String task, Boolean recycleBin) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("delete-task.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Delete Task");

        Scene secondaryScene = new Scene(root, 700, 300);

        Button submit = (Button) secondaryScene.lookup("#submit");
        Button cancel = (Button) secondaryScene.lookup("#cancel");

        Label taskName = (Label) secondaryScene.lookup("#taskName");
        Label deleteLabel = (Label) secondaryScene.lookup("#deleteLabel");
        if(recycleBin) {
            deleteLabel.setText("Are you sure you want to delete this task permanently?");
        }
        else {
            deleteLabel.setText("Are you sure you want to move this task to Recycle Bin?");
        }

        taskName.setText(task);



        submit.setOnAction(e -> {
            deleteTask = true;
            secondaryStage.close();
        });

        cancel.setOnAction(e -> {
            deleteTask = false;
            secondaryStage.close();
        });



        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();
        return deleteTask;
    }
}


