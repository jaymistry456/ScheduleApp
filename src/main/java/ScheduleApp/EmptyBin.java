package ScheduleApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EmptyBin {
    static Boolean emptyBin = false;

    public static Boolean emptyBin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("empty-bin.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Empty Bin");

        Scene secondaryScene = new Scene(root, 700, 250);

        Button submit = (Button) secondaryScene.lookup("#submit");
        Button cancel = (Button) secondaryScene.lookup("#cancel");

        submit.setOnAction(e -> {
            emptyBin = true;
            secondaryStage.close();
        });

        cancel.setOnAction(e -> secondaryStage.close());


        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();

        return emptyBin;
    }

}
