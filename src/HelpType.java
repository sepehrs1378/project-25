public enum HelpType {
    CONTROLLER_SHOP_HELP("exit\nshow collection\nsearch [item name | card name]\n" +
            "search collection [item name | card name]\nbuy [item name | card name]\n" +
            "sell [card id | item id]\nshow\nhelp"),
    CONTROLLER_ACCOUNT_HELP(""),//todo write the message later
    CONTROLLER_COLLECTION_HELP(""),
    CONTROLLER_BATTLEMENU_HELP("Single Player\nMulti Player");//todo write the message later

    private String message;

    HelpType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
