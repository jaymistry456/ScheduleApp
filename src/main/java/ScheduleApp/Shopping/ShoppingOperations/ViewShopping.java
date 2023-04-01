package ScheduleApp.Shopping.ShoppingOperations;

import ScheduleApp.Main;
import ScheduleApp.Shopping.ShoppingItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewShopping {
    static ShoppingItem newItem;

    public static void displayItemDetails(ShoppingItem s) throws IOException {
        newItem = null;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view-shopping.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Shopping Details");

        Scene secondaryScene = new Scene(root, 700, 560);

        Label itemName = (Label) secondaryScene.lookup("#itemName");
        Label itemPurchased = (Label) secondaryScene.lookup("#itemPurchased");
        Button shoppingClose = (Button) secondaryScene.lookup("#shoppingClose");

        itemName.setText(s.getItemName());
        if(s.getPurchased()==true) {
            itemPurchased.setText("Yes");
        }
        else {
            itemPurchased.setText("No");
        }

        shoppingClose.setOnAction(e -> {
            secondaryStage.close();
        });

        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();
    }
}
