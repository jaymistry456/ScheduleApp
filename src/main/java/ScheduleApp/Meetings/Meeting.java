package ScheduleApp.Meetings;

public class Meeting {
    private String meetingName;
    private String meetingDescription;
    private String meetingDate;
    private String meetingStartHours;
    private String meetingStartMinutes;
    private String meetingStartAMPM;
    private String meetingEndHours;
    private String meetingEndMinutes;
    private String meetingEndAMPM;
    private String meetingDuration;
    private String meetingStatus;
    private Boolean important = false;

    public Meeting(String meetingName, String meetingDescription, String meetingDate, String meetingStartHours, String meetingStartMinutes, String meetingStartAMPM, String meetingEndHours, String meetingEndMinutes, String meetingEndAMPM, String meetingDuration, String meetingStatus) {
        this.meetingName = meetingName;
        this.meetingDescription = meetingDescription;
        this.meetingDate = meetingDate;
        this.meetingStartHours = meetingStartHours;
        this.meetingStartMinutes = meetingStartMinutes;
        this.meetingStartAMPM = meetingStartAMPM;
        this.meetingEndHours = meetingEndHours;
        this.meetingEndMinutes = meetingEndMinutes;
        this.meetingEndAMPM = meetingEndAMPM;
        this.meetingDuration = meetingDuration;
        this.meetingStatus = meetingStatus;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingStartHours() {
        return meetingStartHours;
    }

    public void setMeetingStartHours(String meetingStartHours) {
        this.meetingStartHours = meetingStartHours;
    }

    public String getMeetingStartMinutes() {
        return meetingStartMinutes;
    }

    public void setMeetingStartMinutes(String meetingStartMinutes) {
        this.meetingStartMinutes = meetingStartMinutes;
    }

    public String getMeetingStartAMPM() {
        return meetingStartAMPM;
    }

    public void setMeetingStartAMPM(String meetingStartAMPM) {
        this.meetingStartAMPM = meetingStartAMPM;
    }

    public String getMeetingEndHours() {
        return meetingEndHours;
    }

    public void setMeetingEndHours(String meetingEndHours) {
        this.meetingEndHours = meetingEndHours;
    }

    public String getMeetingEndMinutes() {
        return meetingEndMinutes;
    }

    public void setMeetingEndMinutes(String meetingEndMinutes) {
        this.meetingEndMinutes = meetingEndMinutes;
    }

    public String getMeetingEndAMPM() {
        return meetingEndAMPM;
    }

    public void setMeetingEndAMPM(String meetingEndAMPM) {
        this.meetingEndAMPM = meetingEndAMPM;
    }

    public String getMeetingDuration() {
        return meetingDuration;
    }

    public void setMeetingDuration(String meetingDuration) {
        this.meetingDuration = meetingDuration;
    }

    public String getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(String meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public Boolean getImportant() {
        return important;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }
}

