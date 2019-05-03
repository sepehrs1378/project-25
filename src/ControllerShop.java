import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ControllerShop {
    private static ControllerShop ourInstance = new ControllerShop();
    private Request request = Request.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private Account loggedInAccount = dataBase.getLoggedInAccount();
    private View view = View.getInstance();

    private ControllerShop() {

    }

    public static ControllerShop getOurInstance() {
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
                case SEARCH:
                    search();
                    break;
                case BUY:
                    buy();
                    break;
                case SELL:
                    sell();
                    break;
                case HELP:
                    help();
                    break;
                default:
                    System.out.println("!!!!!! bad input in ControllerShop.main");
                    System.exit(-1);
            }
        }
    }

    public void show() {
        if (request.getCommand().matches("show")) {
            view.showCardsAndItemsInShop();
        } else if (request.getCommand().matches("^show collection$")) {
            view.showCardsAndItemsOfCollection(dataBase.getLoggedInAccount().getPlayerInfo().getCollection());
        } else {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
        }
    }

    public void sell() {
        Pattern pattern = Pattern.compile("^sell (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        if (!matcher.find()) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        switch (loggedInAccount.getPlayerInfo().getCollection().sell(matcher.group(1))) {
            case NOT_IN_COLLECTION:
                view.printOutputMessage(OutputMessageType.NOT_IN_COLLECTION);
                break;
            case SOLD_SUCCESSFULLY:
                loggedInAccount.getPlayerInfo().getCollection().sell(matcher.group(1));
                view.printOutputMessage(OutputMessageType.SOLD_SUCCESSFULLY);
                break;
            default:
        }
    }

    public void buy() {
        if (!request.getCommand().matches("^buy .+$")) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        Pattern pattern = Pattern.compile("^buy (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        switch (loggedInAccount.getPlayerInfo().getCollection().buy(matcher.group(1))) {
            case NOT_IN_SHOP:
                view.printOutputMessage(OutputMessageType.NOT_IN_SHOP);
                break;
            case INSUFFICIENT_MONEY:
                view.printOutputMessage(OutputMessageType.INSUFFICIENT_MONEY);
                break;
            case CANT_HAVE_MORE_ITEMS:
                view.printOutputMessage(OutputMessageType.CANT_HAVE_MORE_ITEMS);
                break;
            case BOUGHT_SUCCESSFULLY:
                view.printOutputMessage(OutputMessageType.BOUGHT_SUCCESSFULLY);
                break;
            default:
        }
    }

    public void search() {
        String command = request.getCommand();
        if (command.matches("search (.+)$")) {
            dataBase.searchInShop(command);
        } else if (command.matches("^search collection (.+)$")) {
            String[] strings = command.split("\\s+");
            ArrayList<String> cardsAndItems =
                    new ArrayList<>(loggedInAccount.getPlayerInfo().getCollection().searchCardOrItemWithName(strings[2]));
            view.printList(cardsAndItems);
        } else {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
        }
    }

    public void help() {
        view.printHelp(HelpType.CONTROLLER_SHOP_HELP);
    }

    public void showIdInShop(Card card, Usable usable, Collectable collectable) {
        if (card != null) {
            view.showId(card.getId());
        } else if (usable != null) {
            view.showId(usable.getId());
        } else if (collectable != null) {
            view.showId(collectable.getId());
        } else {
            view.showCardOrItemDoesNotExist();
        }
    }
}
