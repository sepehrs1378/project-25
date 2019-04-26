import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerCollection {
    private static final Request request = Request.getInstance();
    private static final ControllerCollection ourInstance = new ControllerCollection();
    private static final DataBase dataBase = DataBase.getInstance();
    private static final Account loggedInAccount = dataBase.getLoggedInAccount();
    private static final View view = View.getInstance();

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
                    ourInstance.create();
                    break;
                case EXIT:
                    didExit = true;
                    break;
                case SHOW:
                    ourInstance.show();
                    break;
                case SEARCH:
                    break;
                case SAVE:
                    //todo is it needed?
                    break;
                case DELETE:
                    ourInstance.delete(request);
                    break;
                case ADD:
                    break;
                case REMOVE:
                    break;
                case VALIDATE:
                    ourInstance.validate(request);
                    break;
                case SELECT:
                    ourInstance.select();
                    break;
                case HELP:
                    ourInstance.help();
                    break;
                default:
                    System.out.println("!!!!!! bad input in ControllerCollection.main");
                    System.exit(-1);
            }
        }
    }

    public void create() {
        if (!request.getCommand().matches("^create deck .+$")) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        Pattern pattern = Pattern.compile("^create deck (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
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
            Deck deck=temp.getDeckByName(request.getCommand().split("\\s+")[2]);
            view.showDeck(deck,"");
        }else if(request.getCommand().equals("show all decks")){
            Deck mainDeck=dataBase.getLoggedInAccount().getMainDeck();
            view.showAllDecks(dataBase.getLoggedInAccount().getPlayerInfo().getCollection(),mainDeck);
        }
    }

    private void help() {
        view.printHelp(HelpType.CONTROLLER_COLLECTION_HELP);
    }

    public void select() {
        if (!request.getCommand().matches("^select deck .+$")) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        Pattern pattern = Pattern.compile("^select deck (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
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
        if (!request.getCommand().matches("^validate deck .+$")) {
            view.printOutputMessage(OutputMessageType.DECK_DOESNT_EXIST);
            return;
        }
        Pattern pattern = Pattern.compile("^validate deck (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
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

    }

    public void delete(Request request) {
        if (request.getCommand().matches("^delete deck .+$")) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        Pattern pattern = Pattern.compile("^delete deck (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
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

    }

    public void search() {

    }
}
