package ScheduleApp.Tasks.TaskOperations;

import ScheduleApp.Main;
import ScheduleApp.Tasks.Task;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddTask {

    static Task newTask;

    public static Task getTaskDetails() throws IOException {
        newTask = null;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add-task.fxml"));
        BorderPane root = (BorderPane) fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Add Task");

        Scene secondaryScene = new Scene(root, 700, 560);

        TextField taskNameTextField = (TextField) secondaryScene.lookup("#taskNameInput");
        TextArea taskDescription = (TextArea) secondaryScene.lookup("#taskDescriptionInput");

        DatePicker datePicker = (DatePicker) secondaryScene.lookup("#deadlineInput");


        String hours[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] minutes = new String[60];
        String[] AMPM = {"AM", "PM"};

        for(int i=0; i<60; i++) {
            if(i<10) {
                minutes[i] = "0" + i;
            }
            else {
                minutes[i] = String.valueOf(i);
            }
        }

        ComboBox hoursComboBox = (ComboBox) secondaryScene.lookup("#hours");
        hoursComboBox.setItems(FXCollections.observableArrayList(hours));

        ComboBox minutesComboBox = (ComboBox) secondaryScene.lookup("#minutes");
        minutesComboBox.setItems(FXCollections.observableArrayList(minutes));

        ComboBox AMPMComboBox = (ComboBox) secondaryScene.lookup("#AMPM");
        AMPMComboBox.setItems(FXCollections.observableArrayList(AMPM));

        RadioButton completed = (RadioButton) secondaryScene.lookup("#completed");
        RadioButton inProgress = (RadioButton) secondaryScene.lookup("#inProgress");
        RadioButton notStarted = (RadioButton) secondaryScene.lookup("#notStarted");

        Button submit = (Button) secondaryScene.lookup("#submit");
        Button cancel = (Button) secondaryScene.lookup("#cancel");


        submit.setOnAction(e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            LocalDate date = datePicker.getValue();

            String taskStatus;
            if(completed.isSelected()) {
                taskStatus = "Completed";
            }
            else if(inProgress.isSelected()) {
                taskStatus = "In Progress";
            }
            else {
                taskStatus = "Not Started";
            }

            newTask = new Task(taskNameTextField.getText(), taskDescription.getText(), formatter.format(date), hoursComboBox.getValue().toString(),  minutesComboBox.getValue().toString(), AMPMComboBox.getValue().toString(), taskStatus);
            secondaryStage.close();
        });

        cancel.setOnAction(e -> {
            secondaryStage.close();
        });



        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();
        if(newTask!=null) {
            return newTask;
        }
        return null;
    }
}
