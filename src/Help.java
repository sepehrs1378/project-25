public enum Help {
    CONTROLLER_SHOP_HELP("exit\nshow collection\nsearch [item name | card name]\n" +
            "search collection [item name | card name]\nbuy [item name | card name]\n" +
            "sell [card id | item id]\nshow\nhelp"),
    CONTROLLER_ACCOUNT_HELP(""),//todo write the message later
    CONTROLLER_COLLECTION_HELP("");//todo write the message later

    private String message;

    Help(String message) {
        this.message = message;
    }
}
