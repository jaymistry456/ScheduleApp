package ScheduleApp.Meetings.MeetingOperations;

import ScheduleApp.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteMeeting {

    static Boolean deleteThisMeeting = false;

    public static Boolean deleteMeeting(String meetingName, Boolean recycleBin) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("delete-meeting.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Delete Task");

        Scene secondaryScene = new Scene(root, 700, 300);

        Button meetingSubmit = (Button) secondaryScene.lookup("#meetingSubmit");
        Button meetingCancel = (Button) secondaryScene.lookup("#meetingCancel");

        Label meetingNameLabel = (Label) secondaryScene.lookup("#meetingNameLabel");
        Label meetingDeleteLabel = (Label) secondaryScene.lookup("#meetingDeleteLabel");

        if(recycleBin) {
            meetingDeleteLabel.setText("Are you sure you want to delete this meeting permanently?");
        }
        else {
            meetingDeleteLabel.setText("Are you sure you want to move this meeting to Recycle Bin?");
        }

        meetingNameLabel.setText(meetingName);



        meetingSubmit.setOnAction(e -> {
            deleteThisMeeting = true;
            secondaryStage.close();
        });

        meetingCancel.setOnAction(e -> {
            deleteThisMeeting = false;
            secondaryStage.close();
        });

        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();
        return deleteThisMeeting;
    }
}
