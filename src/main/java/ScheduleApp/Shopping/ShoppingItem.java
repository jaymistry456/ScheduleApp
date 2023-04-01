package ScheduleApp.Shopping;

public class ShoppingItem {
    private String itemName;
    private Boolean purchased=false;

    public ShoppingItem(String itemName, Boolean purchased) {
        this.itemName = itemName;
        this.purchased = purchased;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }
}
