public enum OutputMessageType {
    WRONG_COMMAND("Wrong command"),
    A_UNIT_CANT_ATTACK_TARGET("A unit can't attack target"),
    COMBO_ATTACK_SUCCESSFUL("combo attack successful"),
    A_UNIT_CANT_USE_COMBO("A unit can't use combo attack"),
    BATTLE_NOT_FINISHED("Battle isn't finished yet..."),
    INVALID_PASSWORD("Invalid password"),
    INVALID_USERNAME("This user does not exist"),
    USERNAME_ALREADY_EXISTS("This username already exists"),
    NOT_IN_SHOP("Card/item doesn't exist in shop"),
    UNIT_IS_STUNNED("This unit is stunned"),
    A_UNIT_DOESNT_EXIST("A unit doesn't exist"),
    INSUFFICIENT_MONEY("You don't have enough money"),
    BOUGHT_SUCCESSFULLY("Card/item was bought successfully"),
    NOT_IN_COLLECTION("You don't have the card or item"),
    SOLD_SUCCESSFULLY("Card or item was sold successfully"),
    DECK_ALREADY_EXISTS("A deck with the specified name already exists"),
    INVALID_DECK_PLAYER1("player1's deck is invalid"),
    INVALID_DECK_PLAYER2("player2's deck is invalid"),
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
    NO_CARD_IN_BATTLEGROUND("There is no such card in battle ground"),
    CARD_ALREADY_IN_BATTLE("Card exists in Deck"),
    DECK_IS_FULL("Deck Is Full"),
    DECK_HAS_HERO("Deck Has Hero"),
    NO_SUCH_CARD_IN_DECK("no such card in deck"),
    ITEM_IS_EMTPY("there is no item in the selected deck"),
    HERO_IS_EMPTY("hero slot is empty in the deck"),
    FOUND_ITEM("found item with id: "),
    FOUND_CARD("found card with id: "),
    NO_SUCH_CARD_IN_COLLECTION("there is no such card in the PlayerCollection"),
    NO_HERO("there is no hero in battleGround"),
    INVALID_NUMBER("the number was out of battleGround boundaries"),
    NO_SUCH_CARD_IN_HAND("no such card in hand"),
    THIS_CELL_IS_FULL("this cell is full"),
    INVALID_COMMAND("invalid command"),
    CARD_INSERTED("Card Inserted"),
    NO_ERROR("");

    private String message;

    OutputMessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
