import java.util.List;

public class ControllerCollection {
    private static ControllerCollection ourInstance = new ControllerCollection();
    private Request request = Request.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private View view = View.getInstance();

//    private ControllerCollection() {
//
//    }

    public static ControllerCollection getInstance() {
        return ourInstance;
    }

    public void main() {
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case EXIT:
                    didExit = true;
                    break;
                case SHOW:
                    show();
                    break;
                case SEARCH_NAME:
                    searchName();
                    break;
                case SAVE:
                    save();
                    break;
                case CREATE_DECK_NAME:
                    createDeckName();
                    break;
                case DELETE_DECK_NAME:
                    deleteDeckName();
                    break;
                case ADD_ID_TO_DECK_NAME:
                    addIdToDeckName();
                    break;
                case REMOVE_ID_FROM_DECK_NAME:
                    removeIdFromDeckName();
                    break;
                case VALIDATE_DECK_NAME:
                    validateDeckName();
                    break;
                case SELECT_DECK_NAME:
                    selectDeckName();
                    break;
                case SHOW_ALL_DECKS:
                    showAllDecks();
                    break;
                case SHOW_DECK_NAME:
                    showDeckName();
                    break;
                case HELP:
                    help();
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    public void createDeckName() {
        String deckName = request.getCommand().split(" ")[2];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().createDeck(deckName)) {
            case DECK_ALREADY_EXISTS:
                view.printOutputMessage(OutputMessageType.DECK_ALREADY_EXISTS);
                break;
            case DECK_CREATED:
                view.printOutputMessage(OutputMessageType.DECK_CREATED);
                break;
            default:
                view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
        }
    }

    private void show() {
        view.showCardsAndItemsOfCollection(dataBase.getLoggedInAccount().getPlayerInfo().getCollection());
    }

    private void showDeckName() {
        PlayerCollection temp = dataBase.getLoggedInAccount().getPlayerInfo().getCollection();
        Deck deck = temp.getDeckByName(request.getCommand().split("\\s+")[2]);
        view.showDeck(deck, "");
    }

    private void showAllDecks() {
        Deck mainDeck = dataBase.getLoggedInAccount().getMainDeck();
        view.showAllDecks(dataBase.getLoggedInAccount().getPlayerInfo().getCollection(), mainDeck);
    }

    private void help() {
        view.printHelp(HelpType.CONTROLLER_COLLECTION_HELP);
    }

    public void selectDeckName() {
        String deckName = request.getCommand().split(" ")[2];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection()
                .selectDeckAsMain(deckName)) {
            case DECK_DOESNT_EXIST:
                view.printOutputMessage(OutputMessageType.DECK_DOESNT_EXIST);
                break;
            case DECK_SELECTED:
                view.printOutputMessage(OutputMessageType.DECK_SELECTED);
                break;
            default:
        }
    }

    private void save() {
        //todo plz complete it
    }

    public void validateDeckName() {
        String deckName = request.getCommand().split(" ")[2];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().validateDeck(deckName)) {
            case DECK_DOESNT_EXIST:
                view.printOutputMessage(OutputMessageType.DECK_DOESNT_EXIST);
                break;
            case DECK_VALID:
                view.printOutputMessage(OutputMessageType.DECK_VALID);
                break;
            case DECK_NOT_VALID:
                view.printOutputMessage(OutputMessageType.DECK_NOT_VALID);
                break;
            default:
        }
    }

    public void addIdToDeckName() {
        String[] order = request.getCommand().split(" ");
        OutputMessageType outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo()
                .getCollection().addCard(order[1], order[4]);
        view.printOutputMessage(outputMessageType);
    }

    public void deleteDeckName() {
        String deckName = request.getCommand().split(" ")[2];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().deleteDeck(deckName)) {
            case DECK_DOESNT_EXIST:
                view.printOutputMessage(OutputMessageType.DECK_DOESNT_EXIST);
                break;
            case DECK_DELETED:
                view.printOutputMessage(OutputMessageType.DECK_DELETED);
                break;
            default:
                break;
        }
    }

    public void removeIdFromDeckName() {
        String[] order = request.getCommand().split("\\s+");
        OutputMessageType outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection()
                .removeCard(order[1], order[4]);
        view.printOutputMessage(outputMessageType);
    }

    private void searchName() {
        List<String> output = dataBase.getLoggedInAccount().getPlayerInfo().getCollection()
                .searchCardOrItemWithName(request.getCommand().split("\\s+")[1]);
        view.printList(output);
    }
}
