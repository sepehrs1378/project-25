public enum OutputMessageType {
    WRONG_COMMAND("Wrong command"),
    BATTLE_NOT_FINISHED("Battle isn't finished yet..."),
    INVALID_PASSWORD("Invalid password"),
    INVALID_USERNAME("This user does not exist"),
    USERNAME_ALREADY_EXISTS("This username already exists"),
    NOT_IN_SHOP("Card/item doesn't exist in shop"),
    INSUFFICIENT_MONEY("You don't have enough money"),
    BOUGHT_SUCCESSFULLY("Card/item was bought successfully"),
    NOT_IN_COLLECTION("You don't have the card or item"),
    SOLD_SUCCESSFULLY("Card or item was sold successfully"),
    DECK_ALREADY_EXISTS("A deck with the specified name already exists"),
    INVALID_DECK_PLAYER1(""),//todo
    INVALID_DECK_PLAYER2(""),//todo
    CANT_HAVE_MORE_ITEMS("You can't have more items"),
    DECK_CREATED("Deck was created"),
    DECK_DOESNT_EXIST("Deck doesn't exist"),
    DECK_DELETED("Deck was deleted"),
    DECK_SELECTED("Deck was selected as main deck"),
    DECK_VALID("Deck is Valid"),
    DECK_NOT_VALID("Deck is NOT valid"),
    SELECTED("Card/unit selected"),
    INVALID_COLLECTABLE_CARD("Invalid card/collectable id"),
    INVALID_COLLECTABLE("Invalid collectable id"),
    INVALID_CARD("Invalid card id"),
    UNIT_NOT_SELECTED("No unit is selected"),
    UNIT_MOVED("Selected unit moved"),
    TARGET_NOT_IN_RANGE("Opponent unit is NOT in range"),
    ATTACKED_SUCCESSFULLY(""),
    ALREADY_ATTACKED("Unit has already attacked"),
    NO_ERROR("");

    private String message;

    OutputMessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
