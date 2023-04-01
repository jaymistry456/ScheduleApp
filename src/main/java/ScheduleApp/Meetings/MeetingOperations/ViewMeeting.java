package ScheduleApp.Meetings.MeetingOperations;

import ScheduleApp.Main;
import ScheduleApp.Meetings.Meeting;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewMeeting {
    static Meeting newMeeting;

    public static void displayMeetingDetails(Meeting m) throws IOException {
        newMeeting = null;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view-meeting.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Meeting Details");

        Scene secondaryScene = new Scene(root, 700, 650);

        Label meetingName = (Label) secondaryScene.lookup("#meetingName");
        Label meetingDescription = (Label) secondaryScene.lookup("#meetingDescription");
        Label meetingDeadline = (Label) secondaryScene.lookup("#meetingDeadline");
        Label meetingStartTime = (Label) secondaryScene.lookup("#meetingStartTime");
        Label meetingEndTime = (Label) secondaryScene.lookup("#meetingEndTime");
        Label meetingDuration = (Label) secondaryScene.lookup("#meetingDuration");
        Label meetingStatus = (Label) secondaryScene.lookup("#meetingStatus");
        Button meetingClose = (Button) secondaryScene.lookup("#meetingClose");

        meetingName.setText(m.getMeetingName());
        meetingDescription.setText(m.getMeetingDescription());
        meetingDeadline.setText(m.getMeetingDate());
        meetingStartTime.setText(m.getMeetingStartHours() + ":" + m.getMeetingStartMinutes() + " " + m.getMeetingStartAMPM());
        meetingEndTime.setText(m.getMeetingEndHours() + ":" + m.getMeetingEndMinutes() + " " + m.getMeetingEndAMPM());
        meetingDuration.setText(m.getMeetingDuration());
        meetingStatus.setText(m.getMeetingStatus());

        meetingClose.setOnAction(e -> {
            secondaryStage.close();
        });

        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();
    }
}
