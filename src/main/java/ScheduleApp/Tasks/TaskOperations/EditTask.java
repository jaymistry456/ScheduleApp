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

public class EditTask {

    static Task editedTask;

    public static Task updateTaskDetails(String taskName, String taskDescription, String deadlineValue, String hoursValue, String minutesValue, String AMPMValue, String status) throws IOException {
        editedTask = null;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("edit-task.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Edit Task");

        Scene secondaryScene = new Scene(root, 700, 560);

        TextField taskNameTextField = (TextField) secondaryScene.lookup("#taskNameInput");
        taskNameTextField.setText(taskName);
        TextArea taskDescriptionTextArea = (TextArea) secondaryScene.lookup("#taskDescriptionInput");
        taskDescriptionTextArea.setText(taskDescription);

        DatePicker datePicker = (DatePicker) secondaryScene.lookup("#deadlineInput");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        LocalDate date = LocalDate.parse(deadlineValue , formatter);
        datePicker.setValue(date);


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
        hoursComboBox.getSelectionModel().select(hoursValue);


        ComboBox minutesComboBox = (ComboBox) secondaryScene.lookup("#minutes");
        minutesComboBox.setItems(FXCollections.observableArrayList(minutes));
        minutesComboBox.getSelectionModel().select(minutesValue);


        ComboBox AMPMComboBox = (ComboBox) secondaryScene.lookup("#AMPM");
        AMPMComboBox.setItems(FXCollections.observableArrayList(AMPM));
        AMPMComboBox.getSelectionModel().select(AMPMValue);


        RadioButton completed = (RadioButton) secondaryScene.lookup("#completed");
        RadioButton inProgress = (RadioButton) secondaryScene.lookup("#inProgress");
        RadioButton notStarted = (RadioButton) secondaryScene.lookup("#notStarted");


        if(status=="Completed") {
            completed.setSelected(true);
        }
        else if(status=="In Progress"){
            inProgress.setSelected(true);
        }
        else {
            notStarted.setSelected(true);
        }

        Button submit = (Button) secondaryScene.lookup("#submit");
        Button cancel = (Button) secondaryScene.lookup("#cancel");

        submit.setOnAction(e -> {
            LocalDate deadlineDate = datePicker.getValue();

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

            editedTask = new Task(taskNameTextField.getText(), taskDescriptionTextArea.getText(), formatter.format(deadlineDate), hoursComboBox.getValue().toString(), minutesComboBox.getValue().toString(), AMPMComboBox.getValue().toString(), taskStatus);
            secondaryStage.close();
        });

        cancel.setOnAction(e -> {
            secondaryStage.close();
        });



        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();
        if(editedTask!=null) {
            return editedTask;
        }
        return null;
    }
}
