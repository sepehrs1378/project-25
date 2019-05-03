public enum HelpType {
    CONTROLLER_SHOP_HELP("Shop Commands:" +
            "\n\texit" +
            "\n\tshow collection" +
            "\n\tsearch [item name | card name]" +
            "\n\tsearch collection [item name | card name]" +
            "\n\tbuy [item name | card name]" +
            "\n\tsell [card id | item id]" +
            "\n\tshow" +
            "\n\thelp"),
    CONTROLLER_ACCOUNT_HELP("HELP ACCOUNT MENU"),//todo write the message later
    CONTROLLER_COLLECTION_HELP("Collection Commands:" +
            "\n\texit" +
            "\n\tshow" +
            "\n\tsearch [card name | item name]" +
            "\n\tsave" +
            "\n\tcreate deck [deck name]" +
            "\n\tdelete deck [deck name]" +
            "\n\tadd [card id | item id | hero id] to deck [deck name]" +
            "\n\tremove [card id | item id | hero id] from deck [deck name]" +
            "\n\tvalidate deck [deck name]" +
            "\n\tselect deck [deck name]" +
            "\n\tshow all decks" +
            "\n\tshow deck [deck name]" +
            "\n\thelp"),
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
    CONTROLLER_MAIN_MENU_HELP("EMPTY MAIN MENU"),
    MODES_HELP(Constants.FLAGS + "\n" + Constants.ONE_FLAG + "\n" + Constants.CLASSIC);//todo write the message later

    private String message;

    HelpType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
