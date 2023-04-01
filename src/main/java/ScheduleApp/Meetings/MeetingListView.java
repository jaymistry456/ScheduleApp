package ScheduleApp.Meetings;

import ScheduleApp.*;
import ScheduleApp.Meetings.MeetingOperations.AddMeeting;
import ScheduleApp.Meetings.MeetingOperations.DeleteMeeting;
import ScheduleApp.Meetings.MeetingOperations.EditMeeting;
import ScheduleApp.Meetings.MeetingOperations.ViewMeeting;
import ScheduleApp.Tasks.TaskOperations.DeleteTask;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MeetingListView extends Pane {
    String DELETE_MEETING_TYPE = "deleted";

    public MeetingListView() {
        Main.meetingListModel.todaysMeetingListProperty().addListener(new ListChangeListener<Meeting>() {
            @Override
            public void onChanged(Change<? extends Meeting> c) {
                try {
                    displayList("todays");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Main.meetingListModel.upcomingMeetingListProperty().addListener(new ListChangeListener<Meeting>() {
            @Override
            public void onChanged(Change<? extends Meeting> c) {
                try {
                    displayList("upcoming");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Main.meetingListModel.starredMeetingListProperty().addListener(new ListChangeListener<Meeting>() {
            @Override
            public void onChanged(Change<? extends Meeting> c) {
                try {
                    displayList("starred");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Main.meetingListModel.deletedMeetingListProperty().addListener(new ListChangeListener<Meeting>() {
            @Override
            public void onChanged(Change<? extends Meeting> c) {
                try {
                    displayList(DELETE_MEETING_TYPE);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void addTodaysMeetings(ListView<HBox> meetingListView) {
        for(Meeting m: Main.meetingListModel.todaysMeetingListProperty) {
            HBox hBox = new HBox(10);

            Label meetingName = new Label(m.getMeetingName());
            meetingName.setPrefWidth(150);
            meetingName.setFont(Font.font("Arial", 20));

            Label meetingDescription = new Label(m.getMeetingDescription());
            meetingDescription.setPrefWidth(200);
            meetingDescription.setFont(Font.font("Arial", 20));
            HBox.setHgrow(meetingDescription, Priority.ALWAYS);

            Label meetingDate = new Label(m.getMeetingDate());
            meetingDate.setPrefWidth(150);
            meetingDate.setFont(Font.font("Arial", 20));

            Label meetingStartTime = new Label(m.getMeetingStartHours() + ":" + m.getMeetingStartMinutes() + " " + m.getMeetingStartAMPM());
            meetingStartTime.setPrefWidth(120);
            meetingStartTime.setFont(Font.font("Arial", 20));

            Label meetingEndTime = new Label(m.getMeetingEndHours() + ":" + m.getMeetingEndMinutes() + " " + m.getMeetingEndAMPM());
            meetingEndTime.setPrefWidth(120);
            meetingEndTime.setFont(Font.font("Arial", 20));

            Label meetingDuration = new Label(m.getMeetingDuration());
            meetingDuration.setPrefWidth(90);
            meetingDuration.setFont(Font.font("Arial", 20));

            Label meetingStatus = new Label(m.getMeetingStatus());
            meetingStatus.setPrefWidth(120);
            meetingStatus.setFont(Font.font("Arial", 20));

            CheckBox meetingImportant = new CheckBox();
            meetingImportant.setPrefWidth(50);
            meetingImportant.setPadding(new Insets(5));

            if(m.getImportant()) {
                meetingImportant.setSelected(true);
            }
            else {
                meetingImportant.setSelected(false);
            }

            meetingImportant.setOnAction(e -> {
                if(meetingImportant.isSelected()) {
                    m.setImportant(true);
                    Main.meetingListModel.addMeetingInStarredMeetingList(m.getMeetingName(), m.getMeetingDescription(), m.getMeetingDate(), m.getMeetingStartHours(), m.getMeetingStartMinutes(), m.getMeetingStartAMPM(), m.getMeetingEndHours(), m.getMeetingEndMinutes(), m.getMeetingEndAMPM(), m.getMeetingDuration(), m.getMeetingStatus());
                }
                else {
                    m.setImportant(false);
                    for(Meeting meeting: Main.meetingListModel.starredMeetingListProperty) {
                        if(meeting.getMeetingName()==m.getMeetingName() && meeting.getMeetingDescription()==m.getMeetingDescription()) {
                            Main.meetingListModel.starredMeetingListProperty.remove(meeting);
                            break;
                        }
                    }
                }
            });

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);

            viewButton.setOnAction(e -> {
                try {
                    ViewMeeting.displayMeetingDetails(m);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("Edit");
            editButton.setPrefWidth(75);
            editButton.setPrefHeight(30);
            editButton.setOnAction(e -> {
                Meeting editedMeeting = null;
                try {
                    editedMeeting = EditMeeting.updateMeetingDetails(m.getMeetingName(), m.getMeetingDescription(), m.getMeetingDate(), m.getMeetingStartHours(), m.getMeetingStartMinutes(), m.getMeetingStartAMPM(), m.getMeetingEndHours(), m.getMeetingEndMinutes(), m.getMeetingEndAMPM(), m.getMeetingStatus());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                if(editedMeeting!=null) {
                    meetingName.setText(editedMeeting.getMeetingName());
                    meetingDescription.setText(editedMeeting.getMeetingDescription());
                    meetingDate.setText(editedMeeting.getMeetingDate());
                    meetingStartTime.setText(editedMeeting.getMeetingStartHours() + ":" + editedMeeting.getMeetingStartMinutes() + " " + editedMeeting.getMeetingStartAMPM());
                    meetingEndTime.setText(editedMeeting.getMeetingEndHours() + ":" + editedMeeting.getMeetingEndMinutes() + " " + editedMeeting.getMeetingEndAMPM());
                    meetingDuration.setText(editedMeeting.getMeetingDuration());
                    meetingStatus.setText(editedMeeting.getMeetingStatus());

                    m.setMeetingName(editedMeeting.getMeetingName());
                    m.setMeetingDescription(editedMeeting.getMeetingDescription());
                    m.setMeetingDate(editedMeeting.getMeetingDate());
                    m.setMeetingStartHours(editedMeeting.getMeetingStartHours());
                    m.setMeetingStartMinutes(editedMeeting.getMeetingStartMinutes());
                    m.setMeetingStartAMPM(editedMeeting.getMeetingStartAMPM());
                    m.setMeetingEndHours(editedMeeting.getMeetingEndHours());
                    m.setMeetingEndMinutes(editedMeeting.getMeetingEndMinutes());
                    m.setMeetingEndAMPM(editedMeeting.getMeetingEndAMPM());
                    m.setMeetingDuration(editedMeeting.getMeetingDuration());
                    m.setMeetingStatus(editedMeeting.getMeetingStatus());

                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");

                    if(!m.getMeetingDate().substring(4,6).equals(formatter.format(date))) {
                        Main.meetingListModel.todaysMeetingListProperty.remove(m);
                        Main.meetingListModel.addMeetingInUpcomingMeetingList(m.getMeetingName(), m.getMeetingDescription(), m.getMeetingDate(), m.getMeetingStartHours(), m.getMeetingStartMinutes(), m.getMeetingStartAMPM(), m.getMeetingEndHours(), m.getMeetingEndMinutes(), m.getMeetingEndAMPM(), m.getMeetingDuration(), m.getMeetingStatus());
                    }
                }

            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                Boolean deleteMeeting = null;
                try {
                    deleteMeeting = DeleteMeeting.deleteMeeting(m.getMeetingName(), false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if(deleteMeeting) {
                    Main.meetingListModel.deletedMeetingListProperty.add(m);
                    Main.meetingListModel.todaysMeetingListProperty.remove(m);
                }
            });


            hBox.getChildren().addAll(meetingName, meetingDescription, meetingDate, meetingStartTime, meetingEndTime, meetingDuration, meetingStatus, meetingImportant, viewButton, editButton, deleteButton);

            meetingListView.getItems().add(hBox);
        }
    }
    public void addUpcomingMeetings(ListView<HBox> meetingListView) {
        for(Meeting m: Main.meetingListModel.upcomingMeetingListProperty) {
            HBox hBox = new HBox(10);

            Label meetingName = new Label(m.getMeetingName());
            meetingName.setPrefWidth(150);
            meetingName.setFont(Font.font("Arial", 20));

            Label meetingDescription = new Label(m.getMeetingDescription());
            meetingDescription.setPrefWidth(200);
            meetingDescription.setFont(Font.font("Arial", 20));
            HBox.setHgrow(meetingDescription, Priority.ALWAYS);

            Label meetingDate = new Label(m.getMeetingDate());
            meetingDate.setPrefWidth(150);
            meetingDate.setFont(Font.font("Arial", 20));

            Label meetingStartTime = new Label(m.getMeetingStartHours() + ":" + m.getMeetingStartMinutes() + " " + m.getMeetingStartAMPM());
            meetingStartTime.setPrefWidth(120);
            meetingStartTime.setFont(Font.font("Arial", 20));

            Label meetingEndTime = new Label(m.getMeetingEndHours() + ":" + m.getMeetingEndMinutes() + " " + m.getMeetingEndAMPM());
            meetingEndTime.setPrefWidth(120);
            meetingEndTime.setFont(Font.font("Arial", 20));

            Label meetingDuration = new Label(m.getMeetingDuration());
            meetingDuration.setPrefWidth(90);
            meetingDuration.setFont(Font.font("Arial", 20));

            Label meetingStatus = new Label(m.getMeetingStatus());
            meetingStatus.setPrefWidth(120);
            meetingStatus.setFont(Font.font("Arial", 20));

            CheckBox meetingImportant = new CheckBox();
            meetingImportant.setPrefWidth(50);
            meetingImportant.setPadding(new Insets(5));

            if(m.getImportant()) {
                meetingImportant.setSelected(true);
            }
            else {
                meetingImportant.setSelected(false);
            }

            meetingImportant.setOnAction(e -> {
                if(meetingImportant.isSelected()) {
                    m.setImportant(true);
                    Main.meetingListModel.addMeetingInStarredMeetingList(m.getMeetingName(), m.getMeetingDescription(), m.getMeetingDate(), m.getMeetingStartHours(), m.getMeetingStartMinutes(), m.getMeetingStartAMPM(), m.getMeetingEndHours(), m.getMeetingEndMinutes(), m.getMeetingEndAMPM(), m.getMeetingDuration(), m.getMeetingStatus());
                }
                else {
                    m.setImportant(false);
                    for(Meeting meeting: Main.meetingListModel.starredMeetingListProperty) {
                        if(meeting.getMeetingName()==m.getMeetingName() && meeting.getMeetingDescription()==m.getMeetingDescription()) {
                            Main.meetingListModel.starredMeetingListProperty.remove(meeting);
                            break;
                        }
                    }
                }
            });

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);

            viewButton.setOnAction(e -> {
                try {
                    ViewMeeting.displayMeetingDetails(m);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("Edit");
            editButton.setPrefWidth(75);
            editButton.setPrefHeight(30);
            editButton.setOnAction(e -> {
                Meeting editedMeeting = null;
                try {
                    editedMeeting = EditMeeting.updateMeetingDetails(m.getMeetingName(), m.getMeetingDescription(), m.getMeetingDate(), m.getMeetingStartHours(), m.getMeetingStartMinutes(), m.getMeetingStartAMPM(), m.getMeetingEndHours(), m.getMeetingEndMinutes(), m.getMeetingEndAMPM(), m.getMeetingStatus());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                if(editedMeeting!=null) {
                    meetingName.setText(editedMeeting.getMeetingName());
                    meetingDescription.setText(editedMeeting.getMeetingDescription());
                    meetingDate.setText(editedMeeting.getMeetingDate());
                    meetingStartTime.setText(editedMeeting.getMeetingStartHours() + ":" + editedMeeting.getMeetingStartMinutes() + " " + editedMeeting.getMeetingStartAMPM());
                    meetingEndTime.setText(editedMeeting.getMeetingEndHours() + ":" + editedMeeting.getMeetingEndMinutes() + " " + editedMeeting.getMeetingEndAMPM());
                    meetingDuration.setText(editedMeeting.getMeetingDuration());
                    meetingStatus.setText(editedMeeting.getMeetingStatus());

                    m.setMeetingName(editedMeeting.getMeetingName());
                    m.setMeetingDescription(editedMeeting.getMeetingDescription());
                    m.setMeetingDate(editedMeeting.getMeetingDate());
                    m.setMeetingStartHours(editedMeeting.getMeetingStartHours());
                    m.setMeetingStartMinutes(editedMeeting.getMeetingStartMinutes());
                    m.setMeetingStartAMPM(editedMeeting.getMeetingStartAMPM());
                    m.setMeetingEndHours(editedMeeting.getMeetingEndHours());
                    m.setMeetingEndMinutes(editedMeeting.getMeetingEndMinutes());
                    m.setMeetingEndAMPM(editedMeeting.getMeetingEndAMPM());
                    m.setMeetingDuration(editedMeeting.getMeetingDuration());
                    m.setMeetingStatus(editedMeeting.getMeetingStatus());

                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");

                    if(m.getMeetingDate().substring(4,6).equals(formatter.format(date))) {
                        Main.meetingListModel.upcomingMeetingListProperty.remove(m);
                        Main.meetingListModel.addMeetingInTodaysMeetingList(m.getMeetingName(), m.getMeetingDescription(), m.getMeetingDate(), m.getMeetingStartHours(), m.getMeetingStartMinutes(), m.getMeetingStartAMPM(), m.getMeetingEndHours(), m.getMeetingEndMinutes(), m.getMeetingEndAMPM(), m.getMeetingDuration(), m.getMeetingStatus());
                    }
                }

            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                Boolean deleteMeeting = null;
                try {
                    deleteMeeting = DeleteMeeting.deleteMeeting(m.getMeetingName(), false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if(deleteMeeting) {
                    Main.meetingListModel.deletedMeetingListProperty.add(m);
                    Main.meetingListModel.upcomingMeetingListProperty.remove(m);
                }
            });


            hBox.getChildren().addAll(meetingName, meetingDescription, meetingDate, meetingStartTime, meetingEndTime, meetingDuration, meetingStatus, meetingImportant, viewButton, editButton, deleteButton);

            meetingListView.getItems().add(hBox);
        }
    }
    public void addStarredMeetings(ListView<HBox> meetingListView) {
        for(Meeting m: Main.meetingListModel.starredMeetingListProperty) {
            HBox hBox = new HBox(10);

            Label meetingName = new Label(m.getMeetingName());
            meetingName.setPrefWidth(150);
            meetingName.setFont(Font.font("Arial", 20));

            Label meetingDescription = new Label(m.getMeetingDescription());
            meetingDescription.setPrefWidth(200);
            meetingDescription.setFont(Font.font("Arial", 20));
            HBox.setHgrow(meetingDescription, Priority.ALWAYS);

            Label meetingDate = new Label(m.getMeetingDate());
            meetingDate.setPrefWidth(150);
            meetingDate.setFont(Font.font("Arial", 20));

            Label meetingStartTime = new Label(m.getMeetingStartHours() + ":" + m.getMeetingStartMinutes() + " " + m.getMeetingStartAMPM());
            meetingStartTime.setPrefWidth(120);
            meetingStartTime.setFont(Font.font("Arial", 20));

            Label meetingEndTime = new Label(m.getMeetingEndHours() + ":" + m.getMeetingEndMinutes() + " " + m.getMeetingEndAMPM());
            meetingEndTime.setPrefWidth(120);
            meetingEndTime.setFont(Font.font("Arial", 20));

            Label meetingDuration = new Label(m.getMeetingDuration());
            meetingDuration.setPrefWidth(90);
            meetingDuration.setFont(Font.font("Arial", 20));

            Label meetingStatus = new Label(m.getMeetingStatus());
            meetingStatus.setPrefWidth(120);
            meetingStatus.setFont(Font.font("Arial", 20));

            CheckBox meetingImportant = new CheckBox();
            meetingImportant.setSelected(true);
            meetingImportant.setPrefWidth(50);
            meetingImportant.setPadding(new Insets(5));

            meetingImportant.setOnAction(e -> {
                Main.meetingListModel.starredMeetingListProperty.remove(m);
                for(Meeting meeting: Main.meetingListModel.todaysMeetingListProperty) {
                    if(meeting.getMeetingName()==m.getMeetingName() && meeting.getMeetingDescription()==m.getMeetingDescription()) {
                        meeting.setImportant(false);
                        break;
                    }
                }
                for(Meeting meeting: Main.meetingListModel.upcomingMeetingListProperty) {
                    if(meeting.getMeetingName()==m.getMeetingName() && meeting.getMeetingDescription()==m.getMeetingDescription()) {
                        meeting.setImportant(false);
                        break;
                    }
                }
            });

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);

            viewButton.setOnAction(e -> {
                try {
                    ViewMeeting.displayMeetingDetails(m);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("Edit");
            editButton.setPrefWidth(75);
            editButton.setPrefHeight(30);
            editButton.setOnAction(e -> {
                Meeting editedMeeting = null;
                try {
                    editedMeeting = EditMeeting.updateMeetingDetails(m.getMeetingName(), m.getMeetingDescription(), m.getMeetingDate(), m.getMeetingStartHours(), m.getMeetingStartMinutes(), m.getMeetingStartAMPM(), m.getMeetingEndHours(), m.getMeetingEndMinutes(), m.getMeetingEndAMPM(), m.getMeetingStatus());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                if(editedMeeting!=null) {
                    meetingName.setText(editedMeeting.getMeetingName());
                    meetingDescription.setText(editedMeeting.getMeetingDescription());
                    meetingDate.setText(editedMeeting.getMeetingDate());
                    meetingStartTime.setText(editedMeeting.getMeetingStartHours() + ":" + editedMeeting.getMeetingStartMinutes() + " " + editedMeeting.getMeetingStartAMPM());
                    meetingEndTime.setText(editedMeeting.getMeetingEndHours() + ":" + editedMeeting.getMeetingEndMinutes() + " " + editedMeeting.getMeetingEndAMPM());
                    meetingDuration.setText(editedMeeting.getMeetingDuration());
                    meetingStatus.setText(editedMeeting.getMeetingStatus());

                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");

                    if (editedMeeting.getMeetingDate().substring(4, 6).equals(formatter.format(date)) && !editedMeeting.getMeetingDate().substring(4, 6).equals(m.getMeetingDate().substring(4, 6))) {
                        for(Meeting meeting: Main.meetingListModel.upcomingMeetingListProperty()) {
                            if(meeting.getMeetingName()==m.getMeetingName() && meeting.getMeetingDescription()==m.getMeetingDescription() && meeting.getMeetingDate()==m.getMeetingDate() && meeting.getMeetingStartHours()==m.getMeetingStartHours() && meeting.getMeetingStartMinutes()==m.getMeetingStartMinutes() && meeting.getMeetingStartAMPM()==m.getMeetingStartAMPM() && meeting.getMeetingEndHours()==m.getMeetingEndHours() && meeting.getMeetingEndMinutes()==m.getMeetingEndMinutes() && meeting.getMeetingEndAMPM()==m.getMeetingEndAMPM() && meeting.getMeetingDuration()==m.getMeetingDuration() && meeting.getMeetingStatus()==m.getMeetingStatus()) {
                                Main.meetingListModel.upcomingMeetingListProperty.remove(meeting);
                                break;
                            }
                        }
                        Main.meetingListModel.addMeetingInTodaysMeetingList(editedMeeting.getMeetingName(), editedMeeting.getMeetingDescription(), editedMeeting.getMeetingDate(), editedMeeting.getMeetingStartHours(), editedMeeting.getMeetingStartMinutes(), editedMeeting.getMeetingStartAMPM(), editedMeeting.getMeetingEndHours(), editedMeeting.getMeetingEndMinutes(), editedMeeting.getMeetingEndAMPM(), editedMeeting.getMeetingDuration(), editedMeeting.getMeetingStatus());
                        for(Meeting meeting: Main.meetingListModel.todaysMeetingListProperty()) {
                            if(meeting.getMeetingName()==editedMeeting.getMeetingName() && meeting.getMeetingDescription()==editedMeeting.getMeetingDescription() && meeting.getMeetingDate()==editedMeeting.getMeetingDate() && meeting.getMeetingStartHours()==editedMeeting.getMeetingStartHours() && meeting.getMeetingStartMinutes()==editedMeeting.getMeetingStartMinutes() && meeting.getMeetingStartAMPM()==editedMeeting.getMeetingStartAMPM() && meeting.getMeetingEndHours()==editedMeeting.getMeetingEndHours() && meeting.getMeetingEndMinutes()==editedMeeting.getMeetingEndMinutes() && meeting.getMeetingEndAMPM()==editedMeeting.getMeetingEndAMPM() && meeting.getMeetingDuration()==editedMeeting.getMeetingDuration() && meeting.getMeetingStatus()==editedMeeting.getMeetingStatus()) {
                                meeting.setImportant(true);
                                break;
                            }
                        }
                    }
                    else if (!editedMeeting.getMeetingDate().substring(4, 6).equals(formatter.format(date)) && !editedMeeting.getMeetingDate().substring(4, 6).equals(m.getMeetingDate().substring(4, 6))) {
                        for(Meeting meeting: Main.meetingListModel.todaysMeetingListProperty()) {
                            if(meeting.getMeetingName()==m.getMeetingName() && meeting.getMeetingDescription()==m.getMeetingDescription() && meeting.getMeetingDate()==m.getMeetingDate() && meeting.getMeetingStartHours()==m.getMeetingStartHours() && meeting.getMeetingStartMinutes()==m.getMeetingStartMinutes() && meeting.getMeetingStartAMPM()==m.getMeetingStartAMPM() && meeting.getMeetingEndHours()==m.getMeetingEndHours() && meeting.getMeetingEndMinutes()==m.getMeetingEndMinutes() && meeting.getMeetingEndAMPM()==m.getMeetingEndAMPM() && meeting.getMeetingDuration()==m.getMeetingDuration() && meeting.getMeetingStatus()==m.getMeetingStatus()) {
                                Main.meetingListModel.todaysMeetingListProperty.remove(meeting);
                                break;
                            }
                        }
                        Main.meetingListModel.addMeetingInUpcomingMeetingList(editedMeeting.getMeetingName(), editedMeeting.getMeetingDescription(), editedMeeting.getMeetingDate(), editedMeeting.getMeetingStartHours(), editedMeeting.getMeetingStartMinutes(), editedMeeting.getMeetingStartAMPM(), editedMeeting.getMeetingEndHours(), editedMeeting.getMeetingEndMinutes(), editedMeeting.getMeetingEndAMPM(), editedMeeting.getMeetingDuration(), editedMeeting.getMeetingStatus());
                        for(Meeting meeting: Main.meetingListModel.upcomingMeetingListProperty()) {
                            if(meeting.getMeetingName()==editedMeeting.getMeetingName() && meeting.getMeetingDescription()==editedMeeting.getMeetingDescription() && meeting.getMeetingDate()==editedMeeting.getMeetingDate() && meeting.getMeetingStartHours()==editedMeeting.getMeetingStartHours() && meeting.getMeetingStartMinutes()==editedMeeting.getMeetingStartMinutes() && meeting.getMeetingStartAMPM()==editedMeeting.getMeetingStartAMPM() && meeting.getMeetingEndHours()==editedMeeting.getMeetingEndHours() && meeting.getMeetingEndMinutes()==editedMeeting.getMeetingEndMinutes() && meeting.getMeetingEndAMPM()==editedMeeting.getMeetingEndAMPM() && meeting.getMeetingDuration()==editedMeeting.getMeetingDuration() && meeting.getMeetingStatus()==editedMeeting.getMeetingStatus()) {
                                meeting.setImportant(true);
                                break;
                            }
                        }
                    }

                    m.setMeetingName(editedMeeting.getMeetingName());
                    m.setMeetingDescription(editedMeeting.getMeetingDescription());
                    m.setMeetingDate(editedMeeting.getMeetingDate());
                    m.setMeetingStartHours(editedMeeting.getMeetingStartHours());
                    m.setMeetingStartMinutes(editedMeeting.getMeetingStartMinutes());
                    m.setMeetingStartAMPM(editedMeeting.getMeetingStartAMPM());
                    m.setMeetingEndHours(editedMeeting.getMeetingEndHours());
                    m.setMeetingEndMinutes(editedMeeting.getMeetingEndMinutes());
                    m.setMeetingEndAMPM(editedMeeting.getMeetingEndAMPM());
                    m.setMeetingDuration(editedMeeting.getMeetingDuration());
                    m.setMeetingStatus(editedMeeting.getMeetingStatus());
                }
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                try {
                    Boolean deleteTask = DeleteTask.deleteTask(m.getMeetingName(), false);
                    if (deleteTask) {
                        Main.meetingListModel.deletedMeetingListProperty.add(m);
                        for(Meeting meeting: Main.meetingListModel.todaysMeetingListProperty) {
                            if(meeting.getMeetingName()==m.getMeetingName() && meeting.getMeetingDescription()==m.getMeetingDescription() && meeting.getMeetingDate()==m.getMeetingDate() && meeting.getMeetingStartHours()==m.getMeetingStartHours() && meeting.getMeetingStartMinutes()==m.getMeetingStartMinutes() && meeting.getMeetingStartAMPM()==m.getMeetingStartAMPM() && meeting.getMeetingEndHours()==m.getMeetingEndHours() && meeting.getMeetingEndMinutes()==m.getMeetingEndMinutes() && meeting.getMeetingEndAMPM()==m.getMeetingEndAMPM() && meeting.getMeetingDuration()==m.getMeetingDuration() && meeting.getMeetingStatus()==m.getMeetingStatus()) {
                                Main.meetingListModel.todaysMeetingListProperty.remove(meeting);
                                break;
                            }
                        }
                        for(Meeting meeting: Main.meetingListModel.upcomingMeetingListProperty) {
                            if(meeting.getMeetingName()==m.getMeetingName() && meeting.getMeetingDescription()==m.getMeetingDescription() && meeting.getMeetingDate()==m.getMeetingDate() && meeting.getMeetingStartHours()==m.getMeetingStartHours() && meeting.getMeetingStartMinutes()==m.getMeetingStartMinutes() && meeting.getMeetingStartAMPM()==m.getMeetingStartAMPM() && meeting.getMeetingEndHours()==m.getMeetingEndHours() && meeting.getMeetingEndMinutes()==m.getMeetingEndMinutes() && meeting.getMeetingEndAMPM()==m.getMeetingEndAMPM() && meeting.getMeetingDuration()==m.getMeetingDuration() && meeting.getMeetingStatus()==m.getMeetingStatus()) {
                                Main.meetingListModel.upcomingMeetingListProperty.remove(meeting);
                                break;
                            }
                        }
                        Main.meetingListModel.starredMeetingListProperty.remove(m);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(meetingName, meetingDescription, meetingDate, meetingStartTime, meetingEndTime, meetingDuration, meetingStatus,meetingImportant, viewButton, editButton, deleteButton);

            meetingListView.getItems().add(hBox);
        }
    }
    public void addDeletedMeetings(ListView<HBox> meetingListView) {
        for(Meeting m: Main.meetingListModel.deletedMeetingListProperty) {
            HBox hBox = new HBox(15);

            Label meetingName = new Label(m.getMeetingName());
            meetingName.setPrefWidth(150);
            meetingName.setFont(Font.font("Arial", 20));

            Label meetingDescription = new Label(m.getMeetingDescription());
            meetingDescription.setPrefWidth(200);
            meetingDescription.setFont(Font.font("Arial", 20));
            HBox.setHgrow(meetingDescription, Priority.ALWAYS);

            Label meetingDate = new Label(m.getMeetingDate());
            meetingDate.setPrefWidth(150);
            meetingDate.setFont(Font.font("Arial", 20));

            Label meetingStartTime = new Label(m.getMeetingStartHours() + ":" + m.getMeetingStartMinutes() + " " + m.getMeetingStartAMPM());
            meetingStartTime.setPrefWidth(120);
            meetingStartTime.setFont(Font.font("Arial", 20));

            Label meetingEndTime = new Label(m.getMeetingEndHours() + ":" + m.getMeetingEndMinutes() + " " + m.getMeetingEndAMPM());
            meetingEndTime.setPrefWidth(120);
            meetingEndTime.setFont(Font.font("Arial", 20));

            Label meetingDuration = new Label(m.getMeetingDuration());
            meetingDuration.setPrefWidth(100);
            meetingDuration.setFont(Font.font("Arial", 20));

            Label meetingStatus = new Label(m.getMeetingStatus());
            meetingStatus.setPrefWidth(120);
            meetingStatus.setFont(Font.font("Arial", 20));

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);

            viewButton.setOnAction(e -> {
                try {
                    ViewMeeting.displayMeetingDetails(m);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button retoreButton = new Button("Retore");
            retoreButton.setPrefWidth(75);
            retoreButton.setPrefHeight(30);
            retoreButton.setOnAction(e -> {
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
                if(m.getMeetingDate().substring(4,6).equals(formatter.format(date))) {
                    Main.meetingListModel.addMeetingInTodaysMeetingList(m.getMeetingName(), m.getMeetingDescription(), m.getMeetingDate(), m.getMeetingStartHours(), m.getMeetingStartMinutes(), m.getMeetingStartAMPM(), m.getMeetingEndHours(), m.getMeetingEndMinutes(), m.getMeetingEndAMPM(), m.getMeetingDuration(), m.getMeetingStatus());
                }
                else {
                    Main.meetingListModel.addMeetingInUpcomingMeetingList(m.getMeetingName(), m.getMeetingDescription(), m.getMeetingDate(), m.getMeetingStartHours(), m.getMeetingStartMinutes(), m.getMeetingStartAMPM(), m.getMeetingEndHours(), m.getMeetingEndMinutes(), m.getMeetingEndAMPM(), m.getMeetingDuration(), m.getMeetingStatus());
                }
                Main.meetingListModel.deletedMeetingListProperty.remove(m);
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                try {
                    Boolean deleteTask = DeleteTask.deleteTask(m.getMeetingName(), true);
                    if(deleteTask) {
                        Main.meetingListModel.deletedMeetingListProperty.remove(m);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(meetingName, meetingDescription, meetingDate, meetingStartTime, meetingEndTime, meetingDuration, meetingStatus, viewButton, retoreButton, deleteButton);

            meetingListView.getItems().add(hBox);
        }
    }

    public void displayList(String meetingType) throws IOException {
        this.getChildren().clear();

        Pane root = new Pane();
        root.setPrefSize(1400, 585);

        ListView<HBox> meetingListView = new ListView<>();
        meetingListView.setPrefSize(1350, 500);
        meetingListView.setLayoutX(25);
        meetingListView.setLayoutY(5);
        meetingListView.setEditable(true);

        // Add titles
        HBox titles = new HBox(10);

        Label meetingNameTitle = new Label("Meeting Name");
        meetingNameTitle.setPrefWidth(150);
        meetingNameTitle.setFont(Font.font("Arial", 20));

        Label meetingDescriptionTitle = new Label("Meeting Description");
        meetingDescriptionTitle.setPrefWidth(200);
        meetingDescriptionTitle.setFont(Font.font("Arial", 20));
        HBox.setHgrow(meetingDescriptionTitle, Priority.ALWAYS);

        Label meetingDateTitle = new Label("Meeting Date");
        meetingDateTitle.setPrefWidth(150);
        meetingDateTitle.setFont(Font.font("Arial", 20));

        Label meetingStartTimeTitle = new Label("Start Time");
        meetingStartTimeTitle.setPrefWidth(120);
        meetingStartTimeTitle.setFont(Font.font("Arial", 20));

        Label meetingEndTimeTitle = new Label("End Time");
        meetingEndTimeTitle.setPrefWidth(120);
        meetingEndTimeTitle.setFont(Font.font("Arial", 20));

        Label meetingDurationTitle = new Label("Duration");
        meetingDurationTitle.setPrefWidth(90);
        meetingDurationTitle.setFont(Font.font("Arial", 20));

        Label meetingStatusTitle = new Label("Status");
        meetingStatusTitle.setPrefWidth(120);
        meetingStatusTitle.setFont(Font.font("Arial", 20));

        Label meetingImportantTitle = new Label("Star?");
        meetingImportantTitle.setPrefWidth(50);
        meetingImportantTitle.setFont(Font.font("Arial", 20));

        if(!Main.meetingListModel.getMeetingType().equals(DELETE_MEETING_TYPE)) {
            titles.getChildren().addAll(meetingNameTitle, meetingDescriptionTitle, meetingDateTitle, meetingStartTimeTitle, meetingEndTimeTitle, meetingDurationTitle, meetingStatusTitle, meetingImportantTitle);
        }
        else {
            titles.getChildren().addAll(meetingNameTitle, meetingDescriptionTitle, meetingDateTitle, meetingStartTimeTitle, meetingEndTimeTitle, meetingDurationTitle, meetingStatusTitle);
        }
        meetingListView.getItems().add(titles);

        // Add Meetings
        if(Main.meetingListModel.getMeetingType().equals("todays")) {
            addTodaysMeetings(meetingListView);
        }
        else if(Main.meetingListModel.getMeetingType().equals("upcoming")) {
            addUpcomingMeetings(meetingListView);
        }
        else if(Main.meetingListModel.getMeetingType().equals("starred")) {
            addStarredMeetings(meetingListView);
        }
        else if(Main.meetingListModel.getMeetingType().equals(DELETE_MEETING_TYPE)) {
            addDeletedMeetings(meetingListView);
        }

        // Add Button for adding meetings or emptying the deleted tasks
        if(!Main.meetingListModel.getMeetingType().equals(DELETE_MEETING_TYPE)) {
            // Add Button
            HBox addButtonHBox = new HBox(5);
            addButtonHBox.setPrefSize(1400, 50);
            Region regionOne = new Region();
            Button addMeetingButton = new Button("Add Meeting");
            addMeetingButton.setPrefSize(200, 40);
            addMeetingButton.setAlignment(Pos.CENTER);
            addMeetingButton.setStyle("-fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: green; -fx-font-size: 20");
            addMeetingButton.setTextFill(Color.WHITE);
            addMeetingButton.setId("addMeeting");
            addMeetingButton.setOnAction(event -> {
                try {
                    Meeting newMeeting = AddMeeting.getMeetingDetails();
                    if(newMeeting!=null) {
                        LocalDate date = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
                        System.out.println(newMeeting.getMeetingDate());
                        if(newMeeting.getMeetingDate().substring(4,6).equals(formatter.format(date))) {
                            Main.meetingListModel.addMeetingInTodaysMeetingList(newMeeting.getMeetingName(), newMeeting.getMeetingDescription(), newMeeting.getMeetingDate(), newMeeting.getMeetingStartHours(), newMeeting.getMeetingStartMinutes(), newMeeting.getMeetingStartAMPM(), newMeeting.getMeetingEndHours(), newMeeting.getMeetingEndMinutes(), newMeeting.getMeetingEndAMPM(), newMeeting.getMeetingDuration(), newMeeting.getMeetingStatus());
                        }
                        else {
                            Main.meetingListModel.addMeetingInUpcomingMeetingList(newMeeting.getMeetingName(), newMeeting.getMeetingDescription(), newMeeting.getMeetingDate(), newMeeting.getMeetingStartHours(), newMeeting.getMeetingStartMinutes(), newMeeting.getMeetingStartAMPM(), newMeeting.getMeetingEndHours(), newMeeting.getMeetingEndMinutes(), newMeeting.getMeetingEndAMPM(), newMeeting.getMeetingDuration(), newMeeting.getMeetingStatus());
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Region regionTwo = new Region();

            addButtonHBox.getChildren().addAll(regionOne, addMeetingButton, regionTwo);

            HBox.setHgrow(regionOne, Priority.ALWAYS);
            HBox.setHgrow(regionTwo, Priority.ALWAYS);

            addMeetingButton.setLayoutX(600);
            addMeetingButton.setLayoutY(512);

            root.getChildren().addAll(meetingListView, addMeetingButton);
        }
        else {
            HBox emptyButtonHBox = new HBox(5);

            Region regionOne = new Region();
            Button emptyButton = new Button("Empty Bin");
            emptyButton.setPrefSize(200, 40);
            emptyButton.setTextFill(Color.WHITE);
            emptyButton.setStyle("-fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: green; -fx-font-size: 20");
            emptyButton.setAlignment(Pos.CENTER);
            emptyButton.setId("emptyBin");
            emptyButton.setOnAction(event -> {
                try {
                    Boolean emptyBin = EmptyBin.emptyBin();
                    if(emptyBin) {
                        Main.meetingListModel.deletedMeetingListProperty.clear();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Region regionTwo = new Region();

            emptyButtonHBox.getChildren().addAll(regionOne, emptyButton, regionTwo);

            HBox.setHgrow(regionOne, Priority.ALWAYS);
            HBox.setHgrow(regionTwo, Priority.ALWAYS);

            emptyButton.setLayoutX(600);
            emptyButton.setLayoutY(512);

            root.getChildren().addAll(meetingListView, emptyButton);
        }

        this.getChildren().add(root);
    }
}


