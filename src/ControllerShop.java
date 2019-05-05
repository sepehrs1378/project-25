import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ControllerShop {
    private static ControllerShop ourInstance = new ControllerShop();
    private Request request = Request.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private View view = View.getInstance();

    private ControllerShop() {

    }

    public static ControllerShop getOurInstance() {
        return ourInstance;
    }

    public void main() {
        boolean Exit = false;
        while (!Exit) {
            request.getNewCommand();
            switch (request.getType()) {
                case SHOW:
                    show();
                    break;
                case SEARCH:
                    search();
                    break;
                case EXIT:
                    Exit = true;
                    break;
                case SELL:
                    sell();
                    break;
                case HELP:
                    help();
                    break;
                case BUY:
                    buy();
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
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
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().sell(matcher.group(1))) {
            case NOT_IN_COLLECTION:
                view.printOutputMessage(OutputMessageType.NOT_IN_COLLECTION);
                break;
            case SOLD_SUCCESSFULLY:
                dataBase.getLoggedInAccount().getPlayerInfo().getCollection().sell(matcher.group(1));
                view.printOutputMessage(OutputMessageType.SOLD_SUCCESSFULLY);
                break;
            default:
        }
    }

    public void buy() {
        Pattern pattern = Pattern.compile("^buy (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        if (!matcher.find()) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().buy(matcher.group(1))) {
            case INSUFFICIENT_MONEY:
                view.printOutputMessage(OutputMessageType.INSUFFICIENT_MONEY);
                break;
            case CANT_HAVE_MORE_ITEMS:
                view.printOutputMessage(OutputMessageType.CANT_HAVE_MORE_ITEMS);
                break;
            case BOUGHT_SUCCESSFULLY:
                view.printOutputMessage(OutputMessageType.BOUGHT_SUCCESSFULLY);
                break;
            case NOT_IN_SHOP:
                view.printOutputMessage(OutputMessageType.NOT_IN_SHOP);
                break;
            default:
        }
    }

    public void search() {
        String command = request.getCommand();
        if (command.matches("^search collection (.+)$")) {
            String[] strings = command.split("\\s+");
            ArrayList<String> cardsAndItems =
                    new ArrayList<>(dataBase.getLoggedInAccount().getPlayerInfo().getCollection().searchCardOrItemWithName(strings[2]));
            view.printList(cardsAndItems);
        } else if (command.matches("^search (.+)$")) {
            dataBase.searchInShop(command);
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
