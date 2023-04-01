package ScheduleApp.Meetings;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class MeetingListModel {
    private String meetingType;
    SimpleListProperty<Meeting> todaysMeetingListProperty;
    SimpleListProperty<Meeting> upcomingMeetingListProperty;
    SimpleListProperty<Meeting> starredMeetingListProperty;
    SimpleListProperty<Meeting> deletedMeetingListProperty;

    public MeetingListModel() {
        meetingType = "todays";

        ArrayList<Meeting> todaysMeetingList = new ArrayList<Meeting>();
        ObservableList<Meeting> observableTodaysMeetingList = (ObservableList<Meeting>) FXCollections.observableArrayList(todaysMeetingList);
        todaysMeetingListProperty = new SimpleListProperty<Meeting>(observableTodaysMeetingList);

        ArrayList<Meeting> upcomingMeetingList = new ArrayList<Meeting>();
        ObservableList<Meeting> observableUpcomingMeetingList = (ObservableList<Meeting>) FXCollections.observableArrayList(upcomingMeetingList);
        upcomingMeetingListProperty = new SimpleListProperty<Meeting>(observableUpcomingMeetingList);

        ArrayList<Meeting> starredMeetingList = new ArrayList<Meeting>();
        ObservableList<Meeting> observableStarredMeetingList = (ObservableList<Meeting>) FXCollections.observableArrayList(starredMeetingList);
        starredMeetingListProperty = new SimpleListProperty<Meeting>(observableStarredMeetingList);

        ArrayList<Meeting> deletedMeetingList = new ArrayList<Meeting>();
        ObservableList<Meeting> observableDeletedMeetingList = (ObservableList<Meeting>) FXCollections.observableArrayList(deletedMeetingList);
        deletedMeetingListProperty = new SimpleListProperty<Meeting>(observableDeletedMeetingList);
    }

    public String getMeetingType() {
        return meetingType;
    }

    public SimpleListProperty<Meeting> todaysMeetingListProperty() {
        return this.todaysMeetingListProperty;
    }

    public SimpleListProperty<Meeting> upcomingMeetingListProperty() {
        return this.upcomingMeetingListProperty;
    }

    public SimpleListProperty<Meeting> starredMeetingListProperty() {
        return this.starredMeetingListProperty;
    }

    public SimpleListProperty<Meeting> deletedMeetingListProperty() {
        return this.deletedMeetingListProperty;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public void addMeetingInTodaysMeetingList(String meetingName, String meetingDescription, String meetingDate, String meetingStartHours, String meetingStartMinutes, String meetingStartAMPM, String meetingEndHours, String meetingEndMinutes, String meetingEndAMPM, String meetingDuration, String meetingStatus) {
        todaysMeetingListProperty.add(new Meeting(meetingName, meetingDescription, meetingDate, meetingStartHours, meetingStartMinutes, meetingStartAMPM, meetingEndHours, meetingEndMinutes, meetingEndAMPM, meetingDuration, meetingStatus));
    }

    public void addMeetingInUpcomingMeetingList(String meetingName, String meetingDescription, String meetingDate, String meetingStartHours, String meetingStartMinutes, String meetingStartAMPM, String meetingEndHours, String meetingEndMinutes, String meetingEndAMPM, String meetingDuration, String meetingStatus) {
        upcomingMeetingListProperty.add(new Meeting(meetingName, meetingDescription, meetingDate, meetingStartHours, meetingStartMinutes, meetingStartAMPM, meetingEndHours, meetingEndMinutes, meetingEndAMPM, meetingDuration, meetingStatus));
    }

    public void addMeetingInStarredMeetingList(String meetingName, String meetingDescription, String meetingDate, String meetingStartHours, String meetingStartMinutes, String meetingStartAMPM, String meetingEndHours, String meetingEndMinutes, String meetingEndAMPM, String meetingDuration, String meetingStatus) {
        starredMeetingListProperty.add(new Meeting(meetingName, meetingDescription, meetingDate, meetingStartHours, meetingStartMinutes, meetingStartAMPM, meetingEndHours, meetingEndMinutes, meetingEndAMPM, meetingDuration, meetingStatus));
    }

    public void addMeetingInDeletedMeetingList(String meetingName, String meetingDescription, String meetingDate, String meetingStartHours, String meetingStartMinutes, String meetingStartAMPM, String meetingEndHours, String meetingEndMinutes, String meetingEndAMPM, String meetingDuration, String meetingStatus) {
        deletedMeetingListProperty.add(new Meeting(meetingName, meetingDescription, meetingDate, meetingStartHours, meetingStartMinutes, meetingStartAMPM, meetingEndHours, meetingEndMinutes, meetingEndAMPM, meetingDuration, meetingStatus));
    }
}
