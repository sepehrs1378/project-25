import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerCollection {
    private static ControllerCollection ourInstance = new ControllerCollection();
    private Request request = Request.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private View view = View.getInstance();

    private ControllerCollection() {

    }

    public static ControllerCollection getInstance() {
        return ourInstance;
    }

    public void main() {
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
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
                    default:
                        view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
                }
            }
        }
    }

    public void create() {
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
        if (request.getCommand().matches("show deck \\w+")) {
            PlayerCollection temp = dataBase.getLoggedInAccount().getPlayerInfo().getCollection();
            Deck deck = temp.getDeckByName(request.getCommand().split("\\s+")[2]);
            view.showDeck(deck, "");
        } else if (request.getCommand().

                equals("show all decks")) {
            Deck mainDeck = dataBase.getLoggedInAccount().getMainDeck();
            view.showAllDecks(dataBase.getLoggedInAccount().getPlayerInfo().getCollection(), mainDeck);
        } else {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
        }
    }

    private void help() {
        view.printHelp(HelpType.CONTROLLER_COLLECTION_HELP);
    }

    public void select() {
        Pattern pattern = Pattern.compile("^select deck (\\w+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        if (!matcher.find()) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection()
                .selectDeckAsMain(matcher.group(1))) {
            case DECK_DOESNT_EXIST:
                view.printOutputMessage(OutputMessageType.DECK_DOESNT_EXIST);
                break;
            case DECK_SELECTED:
                view.printOutputMessage(OutputMessageType.DECK_SELECTED);
                break;
            default:
        }
    }

    public void validate(Request request) {
        Pattern pattern = Pattern.compile("^validate deck (\\w+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        if (!matcher.find()) {
            view.printOutputMessage(OutputMessageType.DECK_DOESNT_EXIST);
            return;
        }
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().validateDeck(matcher.group(1))) {
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

    public void add() {
        if (request.getCommand().matches("add \\w+ to deck \\w+")) {
            String[] order = request.getCommand().split(" ");
            OutputMessageType outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo()
                    .getCollection().addCard(order[1], order[4]);
            view.printOutputMessage(outputMessageType);
        } else view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
    }

    public void delete(Request request) {
        Pattern pattern = Pattern.compile("^delete deck (\\w+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        if (!matcher.find()) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().deleteDeck(matcher.group(1))) {
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

    public void remove() {
        if (request.getCommand().matches("remove \\w+ from deck \\w+")) {
            String[] order = request.getCommand().split("\\s+");
            OutputMessageType outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection()
                    .removeCard(order[1], order[4]);
            view.printOutputMessage(outputMessageType);
        } else view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
    }

    public void search() {
        if (request.getCommand().matches("search\\s+\\w+")) {
            List<String> output = dataBase.getLoggedInAccount().getPlayerInfo().getCollection()
                    .searchCardOrItemWithName(request.getCommand().split("\\s+")[1]);
            view.printList(output);
        } else view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
    }
}
