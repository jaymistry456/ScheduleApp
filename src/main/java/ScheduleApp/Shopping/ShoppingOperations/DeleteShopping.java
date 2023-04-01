package ScheduleApp.Shopping.ShoppingOperations;

import ScheduleApp.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteShopping {

    static Boolean deleteItem = false;

    public static Boolean deleteItem(String shoppingName, Boolean recycleBin) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("delete-shopping.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Delete Task");

        Scene secondaryScene = new Scene(root, 700, 300);

        Label shoppingNameLabel = (Label) secondaryScene.lookup("#itemNameLabel");
        Label shoppingDeleteLabel = (Label) secondaryScene.lookup("#itemDeleteLabel");

        Button shoppingSubmit = (Button) secondaryScene.lookup("#shoppingSubmit");
        Button shoppingCancel = (Button) secondaryScene.lookup("#shoppingCancel");

        if(recycleBin) {
            shoppingDeleteLabel.setText("Are you sure you want to delete this item permanently?");
        }
        else {
            shoppingDeleteLabel.setText("Are you sure you want to move this item to Recycle Bin?");
        }

        shoppingNameLabel.setText(shoppingName);


        shoppingSubmit.setOnAction(e -> {
            deleteItem = true;
            secondaryStage.close();
        });

        shoppingCancel.setOnAction(e -> {
            deleteItem = false;
            secondaryStage.close();
        });

        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();
        return deleteItem;
    }
}

