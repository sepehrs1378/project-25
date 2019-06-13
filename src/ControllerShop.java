import java.util.ArrayList;

class ControllerShop {
    private static ControllerShop ourInstance;
    private Request request = Request.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private View view = View.getInstance();

    public ControllerShop() {
        ourInstance = this;
    }

    public static ControllerShop getOurInstance() {
        return ourInstance;
    }

    public void main() {
        boolean didExit = false;
        while (!didExit) {
            request.getNewCommand();
            switch (request.getType()) {
                case SHOW_COLLECTION:
                    showCollection();
                    break;
                case SEARCH_NAME:
                    searchName();
                    break;
                case SEARCH_COLLECTION_NAME:
                    searchCollectionName();
                    break;
                case BUY_NAME:
                    buyName();
                    break;
                case SELL_ID:
                    sellId();
                    break;
                case SHOW:
                    show();
                    break;
                case EXIT:
                    didExit = true;
                    break;
                case HELP:
                    help();
                    break;
                default:
                    view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            }
        }
    }

    public void show() {
        view.showCardsAndItemsInShop();
    }

    private void showCollection() {
        view.showCardsAndItemsOfCollection(dataBase.getLoggedInAccount().getPlayerInfo().getCollection());
    }

    public void sellId() {
        String id = request.getCommand().split(" ")[1];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().sell(id)) {
            case NOT_IN_COLLECTION:
                view.printOutputMessage(OutputMessageType.NOT_IN_COLLECTION);
                break;
            case SOLD_SUCCESSFULLY:
                dataBase.getLoggedInAccount().getPlayerInfo().getCollection().sell(id);
                view.printOutputMessage(OutputMessageType.SOLD_SUCCESSFULLY);
                break;
            default:
        }
    }

    private void buyName() {
        String name = request.getCommand().split(" ")[1];
        switch (dataBase.getLoggedInAccount().getPlayerInfo().getCollection().buy(name)) {
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

    private void searchName() {
        dataBase.searchInShop(request.getCommand());
    }

    private void searchCollectionName() {
        String[] strings = request.getCommand().split("\\s+");
        ArrayList<String> cardsAndItems =
                new ArrayList<>(dataBase.getLoggedInAccount().getPlayerInfo().getCollection().searchCardOrItemWithName(strings[2]));
        view.printList(cardsAndItems);
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
