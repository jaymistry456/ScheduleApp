package ScheduleApp.Shopping;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ShoppingListModel {
    private String shoppingListType;
    SimpleListProperty<ShoppingItem> shoppingListProperty;
    SimpleListProperty<ShoppingItem> shoppingWishListListProperty;
    SimpleListProperty<ShoppingItem> shoppingDeletedListProperty;


    public ShoppingListModel() {
        shoppingListType = "shopping list";

        ArrayList<ShoppingItem> shoppingList = new ArrayList<ShoppingItem>();
        ObservableList<ShoppingItem> observableShoppingList = (ObservableList<ShoppingItem>) FXCollections.observableArrayList(shoppingList);
        shoppingListProperty = new SimpleListProperty<ShoppingItem>(observableShoppingList);

        ArrayList<ShoppingItem> shoppingWishList = new ArrayList<ShoppingItem>();
        ObservableList<ShoppingItem> observableShoppingWishListList = (ObservableList<ShoppingItem>) FXCollections.observableArrayList(shoppingWishList);
        shoppingWishListListProperty = new SimpleListProperty<ShoppingItem>(observableShoppingWishListList);

        ArrayList<ShoppingItem> shoppingDeletedList = new ArrayList<ShoppingItem>();
        ObservableList<ShoppingItem> observableShoppingDeletedList = (ObservableList<ShoppingItem>) FXCollections.observableArrayList(shoppingDeletedList);
        shoppingDeletedListProperty = new SimpleListProperty<ShoppingItem>(observableShoppingDeletedList);
    }

    public String getShoppingListType() {
        return shoppingListType;
    }

    public SimpleListProperty<ShoppingItem> shoppingListProperty() {
        return this.shoppingListProperty;
    }

    public SimpleListProperty<ShoppingItem> shoppingWishListListProperty() {
        return this.shoppingWishListListProperty;
    }

    public SimpleListProperty<ShoppingItem> shoppingDeletedListProperty() {
        return this.shoppingDeletedListProperty;
    }

    public void setShoppingListType(String shoppingListType) {
        this.shoppingListType = shoppingListType;
    }

    public void addItemInShoppingList(String itemName, Boolean purchased) {
        shoppingListProperty.add(new ShoppingItem(itemName, purchased));
    }

    public void addItemInShoppingWishList(String itemName, Boolean purchased) {
        shoppingWishListListProperty.add(new ShoppingItem(itemName, purchased));
    }

    public void addItemInShoppingDeletedList(String itemName, Boolean purchased) {
        shoppingDeletedListProperty.add(new ShoppingItem(itemName, purchased));
    }

}

