package ScheduleApp.Tasks;

import ScheduleApp.EmptyBin;
import ScheduleApp.Main;
import ScheduleApp.Tasks.TaskOperations.AddTask;
import ScheduleApp.Tasks.TaskOperations.DeleteTask;
import ScheduleApp.Tasks.TaskOperations.EditTask;
import ScheduleApp.Tasks.TaskOperations.ViewTask;
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
import java.util.Objects;

public class TaskListView extends Pane {
    public TaskListView() {
        Main.taskListModel.todaysTaskListProperty().addListener(new ListChangeListener<Task>() {
            @Override
            public void onChanged(Change<? extends Task> c) {
                try {
                    displayList("todays");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Main.taskListModel.upcomingTaskListProperty().addListener(new ListChangeListener<Task>() {
            @Override
            public void onChanged(Change<? extends Task> c) {
                try {
                    displayList("upcoming");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Main.taskListModel.starredTaskListProperty().addListener(new ListChangeListener<Task>() {
            @Override
            public void onChanged(Change<? extends Task> c) {
                try {
                    displayList("starred");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Main.taskListModel.deletedTaskListProperty().addListener(new ListChangeListener<Task>() {
            @Override
            public void onChanged(Change<? extends Task> c) {
                try {
                    displayList("deleted");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void addTodaysTasks(ListView<HBox> taskListView) throws IOException {
        for(Task t: Main.taskListModel.todaysTaskListProperty) {
            HBox hBox = new HBox(15);

            Label taskName = new Label(t.getTaskName());
            taskName.setPrefWidth(200);
            taskName.setFont(Font.font("Arial", 20));

            Label taskDescription = new Label(t.getTaskDescription());
            taskDescription.setPrefWidth(300);
            taskDescription.setFont(Font.font("Arial", 20));
            HBox.setHgrow(taskDescription, Priority.ALWAYS);

            Label deadline = new Label(t.getDeadline());
            deadline.setPrefWidth(150);
            deadline.setFont(Font.font("Arial", 20));

            Label deadlinetime = new Label(t.getHours() + ":" + t.getMinutes() + " " + t.getAMPM());
            deadlinetime.setPrefWidth(150);
            deadlinetime.setFont(Font.font("Arial", 20));

            Label taskStatus = new Label(t.getStatus());
            taskStatus.setPrefWidth(125);
            taskStatus.setFont(Font.font("Arial", 20));


            CheckBox important = new CheckBox();
            important.setPrefWidth(50);
            important.setPadding(new Insets(5));

            if(t.getImportant()) {
                important.setSelected(true);
            }
            else {
                important.setSelected(false);
            }

            important.setOnAction(e -> {
                if(important.isSelected()) {
                    t.setImportant(true);
                    Main.taskListModel.addTaskInStarredListTaskList(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());
                }
                else {
                    t.setImportant(false);
                    for(Task task: Main.taskListModel.starredTaskListProperty) {
                        if(task.getTaskName()==t.getTaskName() && task.getTaskDescription()==t.getTaskDescription()) {
                            Main.taskListModel.starredTaskListProperty.remove(task);
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
                    ViewTask.displayTaskDetails(t);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("Edit");
            editButton.setPrefWidth(75);
            editButton.setPrefHeight(30);
            editButton.setOnAction(e -> {
                try {
                    Task editedTask = EditTask.updateTaskDetails(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());

                    if(editedTask!=null) {
                        taskName.setText(editedTask.getTaskName());
                        taskDescription.setText(editedTask.getTaskDescription());
                        deadline.setText(editedTask.getDeadline());
                        deadlinetime.setText(editedTask.getHours() + ":" + editedTask.getMinutes() + " " + editedTask.getAMPM());
                        taskStatus.setText(editedTask.getStatus());

                        t.setTaskName(editedTask.getTaskName());
                        t.setTaskDescription(editedTask.getTaskDescription());
                        t.setDeadline(editedTask.getDeadline());
                        t.setHours(editedTask.getHours());
                        t.setMinutes(editedTask.getMinutes());
                        t.setAMPM(editedTask.getAMPM());
                        t.setStatus(editedTask.getStatus());

                        LocalDate date = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");

                        if(!t.getDeadline().substring(4,6).equals(formatter.format(date))) {
                            Main.taskListModel.todaysTaskListProperty.remove(t);
                            Main.taskListModel.addTaskInUpcomingTaskList(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());
                        }
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                try {
                    Boolean deleteTask = DeleteTask.deleteTask(t.getTaskName(), false);
                    if(deleteTask) {
                        Main.taskListModel.deletedTaskListProperty.add(t);
                        Main.taskListModel.todaysTaskListProperty.remove(t);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(taskName, taskDescription, deadline, deadlinetime, taskStatus, important, viewButton, editButton, deleteButton);

            taskListView.getItems().add(hBox);
        }
    }
    public void addUpcomingTasks(ListView<HBox> taskListView) throws IOException {
        for(Task t: Main.taskListModel.upcomingTaskListProperty) {
            HBox hBox = new HBox(15);

            Label taskName = new Label(t.getTaskName());
            taskName.setPrefWidth(200);
            taskName.setFont(Font.font("Arial", 20));

            Label taskDescription = new Label(t.getTaskDescription());
            taskDescription.setPrefWidth(300);
            taskDescription.setFont(Font.font("Arial", 20));
            HBox.setHgrow(taskDescription, Priority.ALWAYS);

            Label deadline = new Label(t.getDeadline());
            deadline.setPrefWidth(150);
            deadline.setFont(Font.font("Arial", 20));

            Label deadlinetime = new Label(t.getHours() + ":" + t.getMinutes() + " " + t.getAMPM());
            deadlinetime.setPrefWidth(150);
            deadlinetime.setFont(Font.font("Arial", 20));

            Label taskStatus = new Label(t.getStatus());
            taskStatus.setPrefWidth(125);
            taskStatus.setFont(Font.font("Arial", 20));


            CheckBox important = new CheckBox();
            important.setPrefWidth(50);
            important.setPadding(new Insets(5));

            if(t.getImportant()) {
                important.setSelected(true);
            }
            else {
                important.setSelected(false);
            }

            important.setOnAction(e -> {
                if(important.isSelected()) {
                    t.setImportant(true);
                    Main.taskListModel.addTaskInStarredListTaskList(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());
                }
                else {
                    t.setImportant(false);
                    for(Task task: Main.taskListModel.starredTaskListProperty) {
                        if(task.getTaskName()==t.getTaskName() && task.getTaskDescription()==t.getTaskDescription()) {
                            Main.taskListModel.starredTaskListProperty.remove(task);
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
                    ViewTask.displayTaskDetails(t);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("Edit");
            editButton.setPrefWidth(75);
            editButton.setPrefHeight(30);
            editButton.setOnAction(e -> {
                try {
                    Task editedTask = EditTask.updateTaskDetails(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());

                    if(editedTask!=null) {
                        taskName.setText(editedTask.getTaskName());
                        taskDescription.setText(editedTask.getTaskDescription());
                        deadline.setText(editedTask.getDeadline());
                        deadlinetime.setText(editedTask.getHours() + ":" + editedTask.getMinutes() + " " + editedTask.getAMPM());
                        taskStatus.setText(editedTask.getStatus());

                        t.setTaskName(editedTask.getTaskName());
                        t.setTaskDescription(editedTask.getTaskDescription());
                        t.setDeadline(editedTask.getDeadline());
                        t.setHours(editedTask.getHours());
                        t.setMinutes(editedTask.getMinutes());
                        t.setAMPM(editedTask.getAMPM());
                        t.setStatus(editedTask.getStatus());

                        LocalDate date = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");

                        if(t.getDeadline().substring(4,6).equals(formatter.format(date))) {
                            Main.taskListModel.upcomingTaskListProperty.remove(t);
                            Main.taskListModel.addTaskInTodaysTaskList(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());
                        }
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                try {
                    Boolean deleteTask = DeleteTask.deleteTask(t.getTaskName(), false);
                    if(deleteTask) {
                        Main.taskListModel.deletedTaskListProperty.add(t);
                        Main.taskListModel.upcomingTaskListProperty.remove(t);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(taskName, taskDescription, deadline, deadlinetime, taskStatus, important, viewButton, editButton, deleteButton);

            taskListView.getItems().add(hBox);
        }
    }
    public void addStarredTasks(ListView<HBox> taskListView) throws IOException {
        for (Task t : Main.taskListModel.starredTaskListProperty) {
            HBox hBox = new HBox(15);

            Label taskName = new Label(t.getTaskName());
            taskName.setPrefWidth(200);
            taskName.setFont(Font.font("Arial", 20));

            Label taskDescription = new Label(t.getTaskDescription());
            taskDescription.setPrefWidth(300);
            taskDescription.setFont(Font.font("Arial", 20));
            HBox.setHgrow(taskDescription, Priority.ALWAYS);

            Label deadline = new Label(t.getDeadline());
            deadline.setPrefWidth(150);
            deadline.setFont(Font.font("Arial", 20));

            Label deadlinetime = new Label(t.getHours() + ":" + t.getMinutes() + " " + t.getAMPM());
            deadlinetime.setPrefWidth(150);
            deadlinetime.setFont(Font.font("Arial", 20));

            Label taskStatus = new Label(t.getStatus());
            taskStatus.setPrefWidth(125);
            taskStatus.setFont(Font.font("Arial", 20));


            CheckBox important = new CheckBox();
            important.setSelected(true);
            important.setPrefWidth(50);
            important.setPadding(new Insets(5));

            important.setOnAction(e -> {
                Main.taskListModel.starredTaskListProperty.remove(t);
                for(Task task: Main.taskListModel.todaysTaskListProperty) {
                    if(task.getTaskName()==t.getTaskName() && task.getTaskDescription()==t.getTaskDescription()) {
                        task.setImportant(false);
                        break;
                    }
                }
                for(Task task: Main.taskListModel.upcomingTaskListProperty) {
                    if(task.getTaskName()==t.getTaskName() && task.getTaskDescription()==t.getTaskDescription()) {
                        task.setImportant(false);
                        break;
                    }
                }
            });

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);
            viewButton.setOnAction(e -> {
                try {
                    ViewTask.displayTaskDetails(t);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("Edit");
            editButton.setPrefWidth(75);
            editButton.setPrefHeight(30);
            editButton.setOnAction(e -> {
                try {
                    Task editedTask = EditTask.updateTaskDetails(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());
                    if (editedTask != null) {
                        taskName.setText(editedTask.getTaskName());
                        taskDescription.setText(editedTask.getTaskDescription());
                        deadline.setText(editedTask.getDeadline());
                        deadlinetime.setText(editedTask.getHours() + ":" + editedTask.getMinutes() + " " + editedTask.getAMPM());
                        taskStatus.setText(editedTask.getStatus());

                        LocalDate date = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");

                        if (editedTask.getDeadline().substring(4, 6).equals(formatter.format(date)) && !editedTask.getDeadline().substring(4, 6).equals(t.getDeadline().substring(4, 6))) {
                            for(Task task: Main.taskListModel.upcomingTaskListProperty()) {
                                if(task.getTaskName()==t.getTaskName() && task.getTaskDescription()==t.getTaskDescription() && task.getDeadline()==t.getDeadline() && task.getHours()==t.getHours() && task.getMinutes()==t.getMinutes() && task.getAMPM()==t.getAMPM() && task.getStatus()==t.getStatus()) {
                                    Main.taskListModel.upcomingTaskListProperty.remove(task);
                                    break;
                                }
                            }
                            Main.taskListModel.addTaskInTodaysTaskList(editedTask.getTaskName(), editedTask.getTaskDescription(), editedTask.getDeadline(), editedTask.getHours(), editedTask.getMinutes(), editedTask.getAMPM(), editedTask.getStatus());
                            for(Task task: Main.taskListModel.todaysTaskListProperty()) {
                                if(task.getTaskName()==editedTask.getTaskName() && task.getTaskDescription()==editedTask.getTaskDescription() && task.getDeadline()==editedTask.getDeadline() && task.getHours()==editedTask.getHours() && task.getMinutes()==editedTask.getMinutes() && task.getAMPM()==editedTask.getAMPM() && task.getStatus()==editedTask.getStatus()) {
                                    task.setImportant(true);
                                    break;
                                }
                            }
                        }
                        else if (!editedTask.getDeadline().substring(4, 6).equals(formatter.format(date)) && !editedTask.getDeadline().substring(4, 6).equals(t.getDeadline().substring(4, 6))) {
                            for(Task task: Main.taskListModel.todaysTaskListProperty()) {
                                if(task.getTaskName()==t.getTaskName() && task.getTaskDescription()==t.getTaskDescription() && task.getDeadline()==t.getDeadline() && task.getHours()==t.getHours() && task.getMinutes()==t.getMinutes() && task.getAMPM()==t.getAMPM() && task.getStatus()==t.getStatus()) {
                                    Main.taskListModel.todaysTaskListProperty.remove(task);
                                    break;
                                }
                            }
                            Main.taskListModel.addTaskInUpcomingTaskList(editedTask.getTaskName(), editedTask.getTaskDescription(), editedTask.getDeadline(), editedTask.getHours(), editedTask.getMinutes(), editedTask.getAMPM(), editedTask.getStatus());
                            for(Task task: Main.taskListModel.upcomingTaskListProperty()) {
                                if(task.getTaskName()==editedTask.getTaskName() && task.getTaskDescription()==editedTask.getTaskDescription() && task.getDeadline()==editedTask.getDeadline() && task.getHours()==editedTask.getHours() && task.getMinutes()==editedTask.getMinutes() && task.getAMPM()==editedTask.getAMPM() && task.getStatus()==editedTask.getStatus()) {
                                    task.setImportant(true);
                                    break;
                                }
                            }
                        }

                        t.setTaskName(editedTask.getTaskName());
                        t.setTaskDescription(editedTask.getTaskDescription());
                        t.setDeadline(editedTask.getDeadline());
                        t.setHours(editedTask.getHours());
                        t.setMinutes(editedTask.getMinutes());
                        t.setAMPM(editedTask.getAMPM());
                        t.setStatus(editedTask.getStatus());
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                try {
                    Boolean deleteTask = DeleteTask.deleteTask(t.getTaskName(), false);
                    if (deleteTask) {
                        Main.taskListModel.deletedTaskListProperty.add(t);
                        for(Task task: Main.taskListModel.todaysTaskListProperty) {
                            if(task.getTaskName()==t.getTaskName() && task.getTaskDescription()==t.getTaskDescription()) {
                                Main.taskListModel.todaysTaskListProperty.remove(task);
                                break;
                            }
                        }
                        for(Task task: Main.taskListModel.upcomingTaskListProperty) {
                            if(task.getTaskName()==t.getTaskName() && task.getTaskDescription()==t.getTaskDescription()) {
                                Main.taskListModel.upcomingTaskListProperty.remove(task);
                                break;
                            }
                        }
                        Main.taskListModel.starredTaskListProperty.remove(t);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(taskName, taskDescription, deadline, deadlinetime, taskStatus, important, viewButton, editButton, deleteButton);

            taskListView.getItems().add(hBox);
        }
    }
    public void addDeletedTasks(ListView<HBox> taskListView) throws IOException {
        for(Task t: Main.taskListModel.deletedTaskListProperty) {
            HBox hBox = new HBox(15);

            Label taskName = new Label(t.getTaskName());
            taskName.setPrefWidth(200);
            taskName.setFont(Font.font("Arial", 20));

            Label taskDescription = new Label(t.getTaskDescription());
            taskDescription.setPrefWidth(300);
            taskDescription.setFont(Font.font("Arial", 20));
            HBox.setHgrow(taskDescription, Priority.ALWAYS);

            Label deadline = new Label(t.getDeadline());
            deadline.setPrefWidth(150);
            deadline.setFont(Font.font("Arial", 20));
            HBox.setHgrow(deadline, Priority.ALWAYS);

            Label deadlineTime = new Label(t.getHours() + ":" + t.getMinutes() + " " + t.getAMPM());
            deadlineTime.setPrefWidth(150);
            deadlineTime.setFont(Font.font("Arial", 20));

            Label taskStatus = new Label(t.getStatus());
            taskStatus.setPrefWidth(125);
            taskStatus.setFont(Font.font("Arial", 20));

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);
            viewButton.setOnAction(e -> {
                try {
                    ViewTask.displayTaskDetails(t);
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
                if(t.getDeadline().substring(4,6).equals(formatter.format(date))) {
                    Main.taskListModel.addTaskInTodaysTaskList(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());
                }
                else {
                    Main.taskListModel.addTaskInUpcomingTaskList(t.getTaskName(), t.getTaskDescription(), t.getDeadline(), t.getHours(), t.getMinutes(), t.getAMPM(), t.getStatus());
                }
                Main.taskListModel.deletedTaskListProperty.remove(t);
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                try {
                    Boolean deleteTask = DeleteTask.deleteTask(t.getTaskName(), true);
                    if(deleteTask) {
                        Main.taskListModel.deletedTaskListProperty.remove(t);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(taskName, taskDescription, deadline, deadlineTime, taskStatus, viewButton, retoreButton, deleteButton);

            taskListView.getItems().add(hBox);
        }
    }

    public void displayList(String taskType) throws IOException {
        this.getChildren().clear();

        Pane root = new Pane();
        root.setPrefSize(1400, 585);

        ListView<HBox> taskListView = new ListView<>();
        taskListView.setPrefSize(1350, 500);
        taskListView.setLayoutX(25);
        taskListView.setLayoutY(5);
        taskListView.setEditable(true);

        // Add titles
        HBox titles = new HBox(15);

        Label taskNameTitle = new Label("Task Name");
        taskNameTitle.setPrefWidth(200);
        taskNameTitle.setFont(Font.font("Arial", 20));

        Label taskNameDescriptionTitle = new Label("Task Description");
        taskNameDescriptionTitle.setPrefWidth(300);
        taskNameDescriptionTitle.setFont(Font.font("Arial", 20));
        HBox.setHgrow(taskNameDescriptionTitle, Priority.ALWAYS);

        Label deadlineDateTitle = new Label("Deadline Date");
        deadlineDateTitle.setPrefWidth(150);
        deadlineDateTitle.setFont(Font.font("Arial", 20));

        Label deadlineTimeTitle = new Label("Deadline Time");
        deadlineTimeTitle.setPrefWidth(150);
        deadlineTimeTitle.setFont(Font.font("Arial", 20));

        Label taskStatusTitle = new Label("Task Status");
        taskStatusTitle.setPrefWidth(125);
        taskStatusTitle.setFont(Font.font("Arial", 20));

        Label taskImportant = new Label("Star?");
        taskImportant.setPrefWidth(50);
        taskImportant.setFont(Font.font("Arial", 20));

        if(!Main.taskListModel.getTaskType().equals("deleted")) {
            titles.getChildren().addAll(taskNameTitle, taskNameDescriptionTitle, deadlineDateTitle, deadlineTimeTitle, taskStatusTitle, taskImportant);
        }
        else {
            titles.getChildren().addAll(taskNameTitle, taskNameDescriptionTitle, deadlineDateTitle, deadlineTimeTitle, taskStatusTitle);
        }
        taskListView.getItems().add(titles);

        // Add Tasks
        if(Main.taskListModel.getTaskType().equals("todays")) {
            addTodaysTasks(taskListView);
        }
        else if(Main.taskListModel.getTaskType().equals("upcoming")) {
            addUpcomingTasks(taskListView);
        }
        else if(Main.taskListModel.getTaskType().equals("starred")) {
            addStarredTasks(taskListView);
        }
        else if(Main.taskListModel.getTaskType().equals("deleted")) {
            addDeletedTasks(taskListView);
        }

        // Add Button for adding tasks or emptying the deleted tasks
        if(!Main.taskListModel.getTaskType().equals("deleted")) {
            // Add Button
            HBox addButtonHBox = new HBox(5);

            Region regionOne = new Region();
            Button addTaskButton = new Button("Add Task");
            addTaskButton.setPrefSize(200, 40);
            addTaskButton.setTextFill(Color.WHITE);
            addTaskButton.setStyle("-fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: green; -fx-font-size: 20");
            addTaskButton.setAlignment(Pos.CENTER);
            addTaskButton.setId("addTask");
            addTaskButton.setOnAction(event -> {
                try {
                    Task newTask = AddTask.getTaskDetails();
                    if(newTask!=null) {
                        LocalDate date = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
                        if(newTask.getDeadline().substring(4,6).equals(formatter.format(date))) {
                            Main.taskListModel.addTaskInTodaysTaskList(newTask.getTaskName(), newTask.getTaskDescription(), newTask.getDeadline(), newTask.getHours(), newTask.getMinutes(), newTask.getAMPM(), newTask.getStatus());
                        }
                        else {
                            Main.taskListModel.addTaskInUpcomingTaskList(newTask.getTaskName(), newTask.getTaskDescription(), newTask.getDeadline(), newTask.getHours(), newTask.getMinutes(), newTask.getAMPM(), newTask.getStatus());
                        }

                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Region regionTwo = new Region();

            addButtonHBox.getChildren().addAll(regionOne, addTaskButton, regionTwo);

            HBox.setHgrow(regionOne, Priority.ALWAYS);
            HBox.setHgrow(regionTwo, Priority.ALWAYS);

            addTaskButton.setLayoutX(600);
            addTaskButton.setLayoutY(512);
            root.getChildren().addAll(taskListView, addTaskButton);
        }
        else {
            // Empty Button
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
                        Main.taskListModel.deletedTaskListProperty.clear();
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

            root.getChildren().addAll(taskListView, emptyButton);
        }

        this.getChildren().add(root);
    }
}

