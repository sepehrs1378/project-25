public enum HelpType {
    CONTROLLER_SHOP_HELP("Shop Commands:" +
            "\n\texit" +
            "\n\tshow collection" +
            "\n\tsearch [item name | card name]" +
            "\n\tsearch collection [item name | card name]" +
            "\n\tbuy [item name | card name]" +
            "\n\tsell [card id | item id]" +
            "\n\tshow"),
    CONTROLLER_ACCOUNT_HELP("Account Commands:\n" +
            "\tcreate account [username]\n" +
            "\tlogin [username]\n" +
            "\tshow leaderboard\n"),
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
            "\n\tshow deck [deck name]"),
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
            "\n\tend game"),
    CONTROLLER_SINGLE_PLAYER_MENU("Story\nCustom game"),
    STORY_MODE_OPTIONS("level1\nlevel2\nlevel3"),
    CONTROLLER_BATTLE_MENU_HELP("Commands:\n" +
            "\tenter singleplayer\n" +
            "\tenter multiplayer"),
    CONTROLLER_MAIN_MENU_HELP("Main Menu Commands:\n" +
            "\tenter collection\n" +
            "\tenter shop\n" +
            "\tenter battle\n" +
            "\tlogout\n" +
            "\tmatch history"),
    MODES_HELP("flags\none_flag\nclassic"),

    CONTROLLER_MULTI_PLAYER_MENU("Commands:\n" +
            "\tselect [username]\n" +
            "\texit\n"),
    CONTOROLLER_SINGLE_PLAYER_MENU("Commands:\n" +
            "\t...");
    private String message;

    HelpType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
