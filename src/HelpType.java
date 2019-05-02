public enum HelpType {
    CONTROLLER_SHOP_HELP("exit" +
            "\nshow collection" +
            "\nsearch [item name | card name]" +
            "\nsearch collection [item name | card name]" +
            "\nbuy [item name | card name]" +
            "\nsell [card id | item id]" +
            "\nshow" +
            "\nhelp"),
    CONTROLLER_ACCOUNT_HELP(""),//todo write the message later
    CONTROLLER_COLLECTION_HELP("exit" +
            "\nshow" +
            "\nsearch [card name | item name]" +
            "\nsave" +
            "\ncreate deck [deck name]" +
            "\ndelete deck [deck name]" +
            "\nadd [card id | item id | hero id] to deck [deck name]" +
            "\nremove [card id | item id | hero id] from deck [deck name]" +
            "\nvalidate deck [deck name]" +
            "\nselect deck [deck name]" +
            "\nshow all decks" +
            "\nshow deck [deck name]" +
            "\nhelp"),
    BATTLE_COMMANDS_HELP("BATTLE COMMAND HELP:" +
            "\n\tgame info" +
            "\n\tshow my minions" +
            "\n\tshow opponent minions" +
            "\n\tshow card info [card id]" +
            "\n\tselect [card id]" +
            "\n\tmove to ([row],[column])" +
            "\n\tattack [opponent card id]" +
            "\n\tattack combo [opponent card id] [my card id] [my card id]..." +
            "\n\tuse special power ([row],[column])" +
            "\n\tshow hand" +
            "\n\tinsert [card name] in ([row],[column])" +
            "\n\tend turn" +
            "\n\tshow collectables" +
            "\n\tselect [collectable id])" +
            "\n\tshow next card" +
            "\n\tenter graveyard" +
            "\n\thelp" +
            "\n\tend game"),
    CONTROLLER_SINGLE_PLAYER_MENU("Story\nCustom game"),
    STORY_MODE_OPTINS("level1\nlevel2\nlevel3"),
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
