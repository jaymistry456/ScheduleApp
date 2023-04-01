package ScheduleApp.Meetings;

import ScheduleApp.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.IOException;

public class MeetingListController {
    @FXML
    private ToggleGroup meetingsGroup;
    @FXML
    private ToggleButton todayMeetings;
    @FXML
    private ToggleButton upcomingMeetings;
    @FXML
    private ToggleButton starredMeetings;
    @FXML
    private ToggleButton deletedMeetings;
    @FXML
    private Label meetingListType;
    private BorderPane root;

    public void setBorderPane(BorderPane root) {
        this.root = root;
    }

    @FXML
    public void initialize() throws IOException {
        meetingListType.setText("Today's Meetings");
        meetingListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
        try {
            meetingsGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
                public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {
                    if (new_toggle==null || new_toggle==todayMeetings) {
                        meetingListType.setText("Today's Meetings");
                        meetingListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
                        try {
                            Main.meetingListModel.setMeetingType("todays");
                            Main.meetingListView.displayList("todays");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        root.setBottom(Main.meetingListView);
                    }
                    else if (new_toggle==upcomingMeetings) {
                        meetingListType.setText("Upcoming Meetings");
                        meetingListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
                        try {
                            Main.meetingListModel.setMeetingType("upcoming");
                            Main.meetingListView.displayList("upcoming");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        root.setBottom(Main.meetingListView);
                    }
                    else if (new_toggle==starredMeetings) {
                        meetingListType.setText("Important Meetings");
                        meetingListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
                        try {
                            Main.meetingListModel.setMeetingType("starred");
                            Main.meetingListView.displayList("starred");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        root.setBottom(Main.meetingListView);
                    }
                    else if(new_toggle==deletedMeetings) {
                        meetingListType.setText("Deleted Meetings");
                        meetingListType.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 30));
                        try {
                            Main.meetingListModel.setMeetingType("deleted");
                            Main.meetingListView.displayList("deleted");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        root.setBottom(Main.meetingListView);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


