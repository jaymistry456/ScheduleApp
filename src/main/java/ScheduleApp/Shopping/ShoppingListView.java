package ScheduleApp.Shopping;

import ScheduleApp.EmptyBin;
import ScheduleApp.Main;
import ScheduleApp.Shopping.ShoppingOperations.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;

public class ShoppingListView extends Pane {

    public ShoppingListView() {
        Main.shoppingListModel.shoppingListProperty().addListener(new ListChangeListener<ShoppingItem>() {
            @Override
            public void onChanged(Change<? extends ShoppingItem> c) {
                try {
                    displayList("shopping list");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Main.shoppingListModel.shoppingWishListListProperty().addListener(new ListChangeListener<ShoppingItem>() {
            @Override
            public void onChanged(Change<? extends ShoppingItem> c) {
                try {
                    displayList("wish list");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Main.shoppingListModel.shoppingDeletedListProperty().addListener(new ListChangeListener<ShoppingItem>() {
            @Override
            public void onChanged(Change<? extends ShoppingItem> c) {
                try {
                    displayList("deleted list");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void addToShoppingList(ListView<HBox> shoppingListView) {
        for(ShoppingItem s: Main.shoppingListModel.shoppingListProperty) {
            HBox hBox = new HBox(30);

            Label itemName = new Label(s.getItemName());
            itemName.setPrefWidth(400);
            itemName.setFont(Font.font("Arial", 20));

            CheckBox purchased = new CheckBox();
            purchased.setPrefWidth(100);
            purchased.setPadding(new Insets(5));
            purchased.setPadding(new Insets(5, 0, 0, 20));

            if(s.getPurchased()) {
                purchased.setSelected(true);
            }
            else {
                purchased.setSelected(false);
            }

            purchased.setOnAction(e -> {
                if(purchased.isSelected()) {
                    s.setPurchased(true);
                }
                else {
                    s.setPurchased(false);
                }
            });

            Button wishListButton = new Button("Add to WishList");
            wishListButton.setPrefWidth(150);
            wishListButton.setPrefHeight(30);
            wishListButton.setOnAction(e -> {
                Main.shoppingListModel.shoppingWishListListProperty.add(s);
                Main.shoppingListModel.shoppingListProperty.remove(s);
            });

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);
            viewButton.setOnAction(e -> {
                try {
                    ViewShopping.displayItemDetails(s);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("Edit");
            editButton.setPrefWidth(75);
            editButton.setPrefHeight(30);
            editButton.setOnAction(e -> {
                try {
                    ShoppingItem editedItem = EditShopping.updateItemDetails(s.getItemName(), s.getPurchased());

                    if(editedItem!=null) {
                        itemName.setText(editedItem.getItemName());
                        if(editedItem.getPurchased()) {
                            purchased.setSelected(true);
                        }
                        else {
                            purchased.setSelected(false);
                        }

                        s.setItemName(editedItem.getItemName());
                        s.setPurchased(editedItem.getPurchased());
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
                    Boolean deleteItem = DeleteShopping.deleteItem(s.getItemName(), false);
                    if(deleteItem) {
                        Main.shoppingListModel.shoppingDeletedListProperty.add(s);
                        Main.shoppingListModel.shoppingListProperty().remove(s);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(itemName, purchased, wishListButton, viewButton, editButton, deleteButton);

            shoppingListView.getItems().add(hBox);
        }
    }
    public void addToWishList(ListView<HBox> shoppingListView) {
        for(ShoppingItem s: Main.shoppingListModel.shoppingWishListListProperty) {
            HBox hBox = new HBox(30);

            Label itemName = new Label(s.getItemName());
            itemName.setPrefWidth(400);
            itemName.setFont(Font.font("Arial", 20));

            Button shoppingListButton = new Button("Add to Shopping List");
            shoppingListButton.setPrefWidth(150);
            shoppingListButton.setPrefHeight(30);
            shoppingListButton.setOnAction(e -> {
                Main.shoppingListModel.shoppingListProperty().add(s);
                Main.shoppingListModel.shoppingWishListListProperty.remove(s);
            });

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);
            viewButton.setOnAction(e -> {
                try {
                    ViewShopping.displayItemDetails(s);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button editButton = new Button("Edit");
            editButton.setPrefWidth(75);
            editButton.setPrefHeight(30);
            editButton.setOnAction(e -> {
                try {
                    ShoppingItem editedItem = EditShopping.updateItemDetails(s.getItemName(), s.getPurchased());

                    if(editedItem!=null) {
                        itemName.setText(editedItem.getItemName());
                        s.setItemName(editedItem.getItemName());
                        s.setPurchased(editedItem.getPurchased());
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
                    Boolean deleteItem = DeleteShopping.deleteItem(s.getItemName(), true);
                    if(deleteItem) {
                        Main.shoppingListModel.shoppingWishListListProperty.remove(s);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(itemName, shoppingListButton, viewButton, editButton, deleteButton);

            shoppingListView.getItems().add(hBox);
        }
    }
    public void addToDeletedList(ListView<HBox> shoppingListView) {
        for(ShoppingItem s: Main.shoppingListModel.shoppingDeletedListProperty) {
            HBox hBox = new HBox(30);

            Label itemName = new Label(s.getItemName());
            itemName.setPrefWidth(400);
            itemName.setFont(Font.font("Arial", 20));


            Button restoreButton = new Button("Restore");
            restoreButton.setPrefWidth(75);
            restoreButton.setPrefHeight(30);
            restoreButton.setOnAction(e -> {
                Main.shoppingListModel.shoppingListProperty.add(s);
                Main.shoppingListModel.shoppingDeletedListProperty.remove(s);
                try {
                    Main.shoppingListView.displayList("deleted");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button viewButton = new Button("View");
            viewButton.setPrefWidth(75);
            viewButton.setPrefHeight(30);
            viewButton.setOnAction(e -> {
                try {
                    ViewShopping.displayItemDetails(s);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(75);
            deleteButton.setPrefHeight(30);
            deleteButton.setOnAction(e -> {
                try {
                    Boolean deleteItem = DeleteShopping.deleteItem(s.getItemName(), true);
                    if(deleteItem) {
                        Main.shoppingListModel.shoppingDeletedListProperty.remove(s);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            hBox.getChildren().addAll(itemName, restoreButton, viewButton, deleteButton);

            shoppingListView.getItems().add(hBox);
        }
    }

    public void displayList(String shoppingListType) throws IOException {
        this.getChildren().clear();

        Pane root = new Pane();
        root.setPrefSize(1400, 585);

        ListView<HBox> shoppingListView = new ListView<>();
        shoppingListView.setPrefSize(1350, 500);
        shoppingListView.setLayoutX(25);
        shoppingListView.setLayoutY(5);
        shoppingListView.setEditable(true);

        HBox titles = new HBox(15);

        Label itemNameTitle = new Label("Item Name");
        itemNameTitle.setPrefWidth(400);
        itemNameTitle.setFont(Font.font("Arial", 20));


        Label itemPurchased = new Label("Purchased");
        itemPurchased.setPrefWidth(100);
        itemPurchased.setFont(Font.font("Arial", 20));

        if(Main.shoppingListModel.getShoppingListType().equals("shopping list")) {
            titles.getChildren().addAll(itemNameTitle, itemPurchased);
        }
        else {
            titles.getChildren().addAll(itemNameTitle);
        }

        shoppingListView.getItems().add(titles);

        // Add shopping items
        if(Main.shoppingListModel.getShoppingListType().equals("shopping list")) {
            addToShoppingList(shoppingListView);
        }
        else if(Main.shoppingListModel.getShoppingListType().equals("wish list")) {
            addToWishList(shoppingListView);
        }
        else if(Main.shoppingListModel.getShoppingListType().equals("deleted")) {
            addToDeletedList(shoppingListView);
        }

        // Add Button for adding items to shopping list or wish list or deleted items
        if(!Main.shoppingListModel.getShoppingListType().equals("deleted")) {
            // Add Button
            HBox addButtonHBox = new HBox(5);

            Region regionOne = new Region();
            Button addItemButton = new Button("Add Item");
            addItemButton.setPrefSize(200, 40);
            addItemButton.setTextFill(Color.WHITE);
            addItemButton.setStyle("-fx-background-radius: 5px, 5px, 5px, 5px; -fx-background-color: green; -fx-font-size: 20");
            addItemButton.setAlignment(Pos.CENTER);
            addItemButton.setId("addItem");
            addItemButton.setOnAction(event -> {
                try {
                    ShoppingItem newItem = AddShopping.getItemDetails();
                    if(newItem!=null) {
                        if(shoppingListType=="shopping list") {
                            Main.shoppingListModel.shoppingListProperty().add(newItem);
                        }
                        else if(shoppingListType=="wish list") {
                            Main.shoppingListModel.shoppingWishListListProperty().add(newItem);
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Region regionTwo = new Region();

            addButtonHBox.getChildren().addAll(regionOne, addItemButton, regionTwo);

            HBox.setHgrow(regionOne, Priority.ALWAYS);
            HBox.setHgrow(regionTwo, Priority.ALWAYS);

            addItemButton.setLayoutX(600);
            addItemButton.setLayoutY(512);

            root.getChildren().addAll(shoppingListView, addItemButton);
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
                        Main.shoppingListModel.shoppingDeletedListProperty.clear();
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


            root.getChildren().addAll(shoppingListView, emptyButton);
        }

        this.getChildren().add(root);
    }
}


