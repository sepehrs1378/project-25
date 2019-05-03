public class Item {
    private String id;
    private String name;
    private String description;

    public Item(String id, String description) {
        String[] strings = id.split("_");
        this.name = strings[1];
        this.id = id;
        this.description = description;
    }

    public Item clone() {
        return new Item(id, description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }
}
