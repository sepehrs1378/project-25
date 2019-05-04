import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerCollection {
    private static ControllerCollection ourInstance = new ControllerCollection();
    private Request request = Request.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private Account loggedInAccount = dataBase.getLoggedInAccount();
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
                case CREATE:
                    create();
                    break;
                case EXIT:
                    didExit = true;
                    break;
                case SHOW:
                    show();
                    break;
                case SEARCH:
                    search();
                    break;
                case SAVE:
                    //todo is it needed?
                    break;
                case DELETE:
                    delete(request);
                    break;
                case ADD:
                    add();
                    break;
                case REMOVE:
                    remove();
                    break;
                case VALIDATE:
                    validate(request);
                    break;
                case SELECT:
                    select();
                    break;
                case HELP:
                    help();
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    public void create() {
        Pattern pattern = Pattern.compile("^create deck (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        if (!matcher.find()) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        switch (loggedInAccount.getPlayerInfo().getCollection().createDeck(matcher.group(1))) {
            case DECK_ALREADY_EXISTS:
                view.printOutputMessage(OutputMessageType.DECK_ALREADY_EXISTS);
                break;
            case DECK_CREATED:
                view.printOutputMessage(OutputMessageType.DECK_CREATED);
                break;
            default:
        }
    }

    private void show() {
        if (request.getCommand().equals("show")) {
            view.showCardsAndItemsOfCollection(dataBase.getLoggedInAccount().getPlayerInfo().getCollection());
        } else if (request.getCommand().matches("show deck \\w+")) {
            PlayerCollection temp = dataBase.getLoggedInAccount().getPlayerInfo().getCollection();
            Deck deck = temp.getDeckByName(request.getCommand().split("\\s+")[2]);
            view.showDeck(deck, "");
        } else if (request.getCommand().equals("show all decks")) {
            Deck mainDeck = dataBase.getLoggedInAccount().getMainDeck();
            view.showAllDecks(dataBase.getLoggedInAccount().getPlayerInfo().getCollection(), mainDeck);
        }
    }

    private void help() {
        view.printHelp(HelpType.CONTROLLER_COLLECTION_HELP);
    }

    public void select() {
        Pattern pattern = Pattern.compile("^select deck (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        if (!matcher.find()) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        switch (loggedInAccount.getPlayerInfo().getCollection()
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
        Pattern pattern = Pattern.compile("^validate deck (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        if (!matcher.find()){
            view.printOutputMessage(OutputMessageType.DECK_DOESNT_EXIST);
            return;
        }
        switch (loggedInAccount.getPlayerInfo().getCollection().validateDeck(matcher.group(1))) {
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
        if (request.getCommand().matches("add .+ to deck .+")) {
            String[] order = request.getCommand().split(" ");
            OutputMessageType outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo()
                    .getCollection().addCard(order[1], order[4]);
            view.printOutputMessage(outputMessageType);
        }
    }

    public void delete(Request request) {
        Pattern pattern = Pattern.compile("^delete deck (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        if (!matcher.find()) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        switch (loggedInAccount.getPlayerInfo().getCollection().deleteDeck(matcher.group(1))) {
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
        if (request.getCommand().matches("remove .+ from deck .+")) {
            String[] order = request.getCommand().split("\\s+");
            OutputMessageType outputMessageType = dataBase.getLoggedInAccount().getPlayerInfo().getCollection()
                    .removeCard(order[1], order[4]);
            view.printOutputMessage(outputMessageType);
        }
    }

    public void search() {
        if (request.getCommand().matches("search\\s+.+")) {
            List<String> output = dataBase.getLoggedInAccount().getPlayerInfo().getCollection()
                    .searchCardOrItemWithName(request.getCommand().split("\\s+")[1]);
            view.printList(output);
        }
    }
}
