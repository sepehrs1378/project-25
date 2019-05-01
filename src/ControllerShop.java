import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ControllerShop {
    private static final Request request = Request.getInstance();
    private static final ControllerShop ourInstance = new ControllerShop();
    private static final DataBase dataBase = DataBase.getInstance();
    private static final Account loggedInAccount = dataBase.getLoggedInAccount();
    private static final View view = View.getInstance();

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
        if (!request.getCommand().matches("^sell .+$")) {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
            return;
        }
        Pattern pattern = Pattern.compile("^sell (.+)$");
        Matcher matcher = pattern.matcher(request.getCommand());
        switch (loggedInAccount.getPlayerInfo().getCollection().sell(matcher.group(1))) {
            case NOT_IN_COLLECTION:
                view.printOutputMessage(OutputMessageType.NOT_IN_COLLECTION);
                break;
            case SOLD_SUCCESSFULLY:

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
            String[] strings = command.split("\\s+");
            Card card = findCardInShop(strings[1]);
            Usable usable = findUsableInShop(strings[1]);
            Collectable collectable = findCollectableInShop(strings[1]);
            showIdInShop(card, usable, collectable);
        } else if (command.matches("^search collection (.+)$")) {
            String[] strings = command.split("\\s+");

        } else {
            view.printOutputMessage(OutputMessageType.WRONG_COMMAND);
        }
    }

    public void help() {
        view.printHelp(HelpType.CONTROLLER_SHOP_HELP);
    }

    private Card findCardInShop(String cardName) {
        for (Card card : DataBase.getCardList()) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    private Usable findUsableInShop(String usableName){
        for (Usable usable : DataBase.getUsableList()){
            if (usable.getName().equals(usableName)){
                return usable;
            }
        }
        return null;
    }

    private Collectable findCollectableInShop(String collectableName){
        for (Collectable collectable : DataBase.getCollectableList()){
            if (collectable.getName().equals(collectableName)){
                return collectable;
            }
        }
        return null;
    }

    public void showIdInShop(Card card, Usable usable, Collectable collectable){
        if (card != null){
            view.showId(card.getId());
        }else if (usable != null){
            view.showId(usable.getId());
        }else if (collectable != null){
            view.showId(collectable.getId());
        }else {
            view.showCardOrItemDoesNotExist();
        }
    }
}
