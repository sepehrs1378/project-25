public enum HelpType {
    CONTROLLER_SHOP_HELP("exit\nshow collection\nsearch [item name | card name]\n" +
            "search collection [item name | card name]\nbuy [item name | card name]\n" +
            "sell [card id | item id]\nshow\nhelp"),
    CONTROLLER_ACCOUNT_HELP(""),//todo write the message later
    CONTROLLER_COLLECTION_HELP("exit\nshow\nsearch [card name | item name]\n" +
            "save\ncreate deck [deck name]\ndelete deck [deck name]\nadd [card id | item id | hero id] to deck [deck name]" +
            "\nremove [card id | item id | hero id] from deck [deck name]" +
            "\nvalidate deck [deck name]" +
            "\nselect deck [deck name]" +
            "\nshow all decks" +
            "\nshow deck [deck name]" +
            "\nhelp"),
    CONTROLLER_BATTLEMENU_HELP("Single Player\nMulti Player"),
    MODES_HELP(Constants.FLAGS + "\n" + Constants.ONE_FLAG + "\n" + Constants.CLASSIC);//todo write the message later

    private String message;

    HelpType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
