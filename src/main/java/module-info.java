module ScheduleApp {
    requires javafx.controls;
    requires javafx.fxml;


    opens ScheduleApp to javafx.fxml;
    exports ScheduleApp;
    exports ScheduleApp.Tasks;
    opens ScheduleApp.Tasks to javafx.fxml;
    exports ScheduleApp.Tasks.TaskOperations;
    opens ScheduleApp.Tasks.TaskOperations to javafx.fxml;
    exports ScheduleApp.Meetings;
    opens ScheduleApp.Meetings to javafx.fxml;
    exports ScheduleApp.Meetings.MeetingOperations;
    opens ScheduleApp.Meetings.MeetingOperations to javafx.fxml;
    exports ScheduleApp.Shopping;
    opens ScheduleApp.Shopping to javafx.fxml;
    exports ScheduleApp.Shopping.ShoppingOperations;
    opens ScheduleApp.Shopping.ShoppingOperations to javafx.fxml;
}