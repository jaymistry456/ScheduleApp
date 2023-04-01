package ScheduleApp.Tasks;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TaskListModel {
    private String taskType;
    SimpleListProperty<Task> todaysTaskListProperty;
    SimpleListProperty<Task> upcomingTaskListProperty;
    SimpleListProperty<Task> starredTaskListProperty;
    SimpleListProperty<Task> deletedTaskListProperty;

    public TaskListModel() {
        taskType = "todays";

        ArrayList<Task> todaysTaskList = new ArrayList<Task>();
        ObservableList<Task> observableTodaysTaskList = (ObservableList<Task>) FXCollections.observableArrayList(todaysTaskList);
        todaysTaskListProperty = new SimpleListProperty<Task>(observableTodaysTaskList);

        ArrayList<Task> upcomingTaskList = new ArrayList<Task>();
        ObservableList<Task> observableUpcomingTaskList = (ObservableList<Task>) FXCollections.observableArrayList(upcomingTaskList);
        upcomingTaskListProperty = new SimpleListProperty<Task>(observableUpcomingTaskList);

        ArrayList<Task> starredTaskList = new ArrayList<Task>();
        ObservableList<Task> observableStarredTaskList = (ObservableList<Task>) FXCollections.observableArrayList(starredTaskList);
        starredTaskListProperty = new SimpleListProperty<Task>(observableStarredTaskList);

        ArrayList<Task> deletedTaskList = new ArrayList<Task>();
        ObservableList<Task> observableDeletedTaskList = (ObservableList<Task>) FXCollections.observableArrayList(deletedTaskList);
        deletedTaskListProperty = new SimpleListProperty<Task>(observableDeletedTaskList);
    }

    public String getTaskType() {
        return this.taskType;
    }

    public SimpleListProperty<Task> todaysTaskListProperty() {
        return this.todaysTaskListProperty;
    }
    public SimpleListProperty<Task> upcomingTaskListProperty() {
        return this.upcomingTaskListProperty;
    }
    public SimpleListProperty<Task> starredTaskListProperty() {
        return this.starredTaskListProperty;
    }
    public SimpleListProperty<Task> deletedTaskListProperty() {
        return this.deletedTaskListProperty;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void addTaskInTodaysTaskList(String taskName, String taskDescription, String deadline, String hours, String minutes, String AMPM, String status) {
        todaysTaskListProperty.add(new Task(taskName, taskDescription, deadline, hours, minutes, AMPM, status));
    }

    public void addTaskInUpcomingTaskList(String taskName, String taskDescription, String deadline, String hours, String minutes, String AMPM, String status) {
        upcomingTaskListProperty.add(new Task(taskName, taskDescription, deadline, hours, minutes, AMPM, status));
    }

    public void addTaskInStarredListTaskList(String taskName, String taskDescription, String deadline, String hours, String minutes, String AMPM, String status) {
        starredTaskListProperty.add(new Task(taskName, taskDescription, deadline, hours, minutes, AMPM, status));
    }

    public void addTaskInDeletedTaskList(String taskName, String taskDescription, String deadline, String hours, String minutes, String AMPM, String status) {
        deletedTaskListProperty.add(new Task(taskName, taskDescription, deadline, hours, minutes, AMPM, status));
    }

}
