package ScheduleApp.Shopping.ShoppingOperations;

import ScheduleApp.Main;
import ScheduleApp.Shopping.ShoppingItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditShopping {

    static ShoppingItem editedItem;

    public static ShoppingItem updateItemDetails(String itemName, Boolean purchased) throws IOException {
        editedItem = null;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("edit-shopping.fxml"));
        BorderPane root = fxmlLoader.load();


        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Edit Item");

        Scene secondaryScene = new Scene(root, 700, 560);

        TextField itemNameTextField = (TextField) secondaryScene.lookup("#itemNameInput");
        itemNameTextField.setText(itemName);

        String[] itemPurchased = {"Yes", "No"};
        ComboBox itemPurchasedComboBox = (ComboBox) secondaryScene.lookup("#itemPurchasedInput");
        itemPurchasedComboBox.setItems(FXCollections.observableArrayList(itemPurchased));
        if(purchased==true) {
            itemPurchasedComboBox.getSelectionModel().select("Yes");
        }
        else {
            itemPurchasedComboBox.getSelectionModel().select("No");
        }

        Button shoppingSubmit = (Button) secondaryScene.lookup("#shoppingSubmit");
        Button shoppingCancel = (Button) secondaryScene.lookup("#shoppingCancel");

        shoppingSubmit.setOnAction(e -> {
            Boolean setItemPurchased = false;

            if(itemPurchasedComboBox.getValue().toString()=="Yes") {
                setItemPurchased = true;
            }

            editedItem = new ShoppingItem(itemNameTextField.getText(), setItemPurchased);
            secondaryStage.close();
        });

        shoppingCancel.setOnAction(e -> {
            secondaryStage.close();
        });



        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait();
        if(editedItem!=null) {
            return editedItem;
        }
        return null;
    }
}

