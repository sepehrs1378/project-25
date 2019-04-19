class Item {
    private String itemID;
    private String description;

    public Item(String itemID, String description) {
        this.itemID = itemID;
        this.description = description;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
