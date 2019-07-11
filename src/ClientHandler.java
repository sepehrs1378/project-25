import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import javafx.application.Platform;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler extends Thread {
    private Socket socket;
    private NetworkDB networkDB = NetworkDB.getInstance();
    private Connection connection;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        connection = new Connection(socket);
        networkDB.addConnection(connection);
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        JsonStreamParser parser = connection.getParser();
        while (true) {
            JsonObject obj = parser.next().getAsJsonObject();
            Request request = yaGson.fromJson(obj.toString(), Request.class);
            switch (request.getRequestType()) {
                case login:
                    caseLogin(request);
                    break;
                case logout:
                    caseLogout(request);
                    break;
                case signUp:
                    caseSignUp(request);
                    break;
                case moveUnit:
                    caseMoveUnit(request);
                    break;
                case attackUnit:
                    caseAttackUnit(request);
                    break;
                case useSpecialPower:
                    caseUseSpecialPower(request);
                    break;
                case useCollectable:
                    caseUseCollectable(request);
                    break;
                case insertCard:
                    caseInsertCard(request);
                    break;
                case endTurn:
                    caseEndTurn(request);
                    break;
                case enterGraveYard:
                    caseEnterGraveYard(request);
                    break;
                case forfeit:
                    caseForfeit(request);
                    break;
                case selectUnit:
                    caseSelectUnit(request);
                    break;
                case findClassicMatch:
                    caseFindClassicMatch();
                    break;
                case findOneFlagMatch:
                    caseFindOneFlagMatch();
                    break;
                case findMultiFlagsMatch:
                    caseFindMultiFlagsMatch();
                    break;
                case cancelMatchFinding:
                    caseCancelMatchFinding();
                    break;
                case enterCollectoin:
                    caseEnterCollection(request);
                    break;
                case exitCollection:
                    caseExitCollection();
                    break;
                case sell:
                    caseSell(request);
                    break;
                case createDeck:
                    caseCreateDeck(request);
                    break;
                case removeDeck:
                    caseRemoveDeck(request);
                    break;
                case importDeck:
                    caseImportDeck(request);
                    break;
                case setMainDeck:
                    caseSetMainDeck(request);
                    break;
                case moveCardToDeck:
                    caseMoveCardToDeck(request);
                    break;
                case removeCardFromDeck:
                    caseRemoveCardFromDeck(request);
                    break;
                case sellCard:
                    caseSell(request);
                    break;
                case sendMessage:
                    caseSendMessage(request);
                    break;
                case enterGlobalChat:
                    caseEnterGlobalChat(request);
                    break;
                case exitGlobalChat:
                    caseExitGlobalChat(request);
                    break;
                case leaderBoard:
                    caseLeaderBoard();
                    break;
                case buy:
                    caseBuy(request);
                    break;
                case shop:
                    caseShop(request);
                    break;
                case gameFinished:
                    caseGameFinished(request);
                    break;
                case enterSellAuction:
                    caseEnterSellAuction(request);
                    break;
                case enterBuyAuction:
                    caseEnterBuyAuction(request);
                    break;
                case submitOffer:
                    caseSubmitOffer(request);
                    break;
                case exitSellAuction:
                    caseExitSellAuction(request);
                    break;
                case exitBuyAuction:
                    caseExitBuyAuction(request);
                    break;
                case close:
                    //todo
                    break;
            }
            Platform.runLater(() -> {
                Server.getInstance().updateCardList();
                Server.getInstance().updateUserList();
            });
            logCase(request);
            if (request.getRequestType().equals(RequestType.close))
                break;
        }
    }

    private void caseExitBuyAuction(Request request) {
        networkDB.getAccountStatusMap().put(connection.getAccount(),AccountStatus.online);
    }

    private void caseExitSellAuction(Request request) {
        Account seller = connection.getAccount();
        Auction auction = null;
        for (Auction auctionTemp : networkDB.getAuctionList()) {
            if (auctionTemp.getSeller().getUsername().equals(seller.getUsername())) {
                auction = auctionTemp;
                break;
            }
        }
        assert auction != null;
        Account buyer = auction.getBidders().get(auction.getHighestBidIndex());
        Card card = auction.getCard();
        seller.getPlayerInfo().getCollection().sellToOtherClient(seller, buyer, card.getId(), card, auction.getHighestBid());
        List<Object> objects = new ArrayList<>();
        objects.add(connection.getAccount());
        networkDB.sendResponseToClient(new Response(ResponseType.auctionSellExit, null, null, objects), connection);
        networkDB.getAuctionList().remove(auction);
        //todo send to clients
    }

    private void caseSubmitOffer(Request request) {
        JsonObject obj = (JsonObject) request.getObjectList().get(0);
        String accountName = obj.get("bidder").getAsString();
        int bidMoney = obj.get("bid").getAsInt();
        String cardName = obj.get("cardName").getAsString();
        String sellerName = obj.get("sellerName").getAsString();
        Auction auction = null;
        for (Auction auctionTemp : NetworkDB.getInstance().getAuctionList()) {
            if (auctionTemp.getSeller().getUsername().equals(sellerName)) {
                auction = auctionTemp;
                break;
            }
        }
        assert auction != null;
        if (cardName.equals(auction.getCard().getName())) {
            auction.addBidder(NetworkDB.getInstance().getAccountWithUserName(accountName), bidMoney);
            List<Object> objectList = new ArrayList<>();
            objectList.add(auction);
            Account account = networkDB.getAccountWithUserName(sellerName);
            Connection tempConnection = networkDB.getConnectionWithAccount(account);
            networkDB.sendResponseToClient(new Response(ResponseType.auctionSellUpdate, null
                    , null, objectList), tempConnection);

        }
    }

    private void caseEnterBuyAuction(Request request) {
        networkDB.getAccountStatusMap().put(connection.getAccount(), AccountStatus.auctionBuy);
        List<Object> objects = new ArrayList<>(getAuctionStrings());
        networkDB.sendResponseToClient(new Response(ResponseType.enterBuyAuction, null, null, objects), connection);
    }

    private void caseEnterSellAuction(Request request) {
        Card card = ((Card) request.getObjectList().get(0));
        String userName = request.getMessage();
        Account account = NetworkDB.getInstance().getAccountWithUserName(userName);
        card = account.getPlayerInfo().getCollection().getCardWithID(card.getId());
        Auction auction = new Auction(account, card);
        NetworkDB.getInstance().getAuctionList().add(auction);
        connection.setCurrentAuction(auction);
        networkDB.getAccountStatusMap().forEach((account1, accountStatus) -> {
            if (accountStatus == AccountStatus.auctionBuy) {
                List<Object> objects = new ArrayList<>(getAuctionStrings());
                networkDB.sendResponseToClient(new Response(ResponseType.auctionBuyUpdate, null, null, objects)
                        , networkDB.getConnectionWithAccount(account1));
            }
        });
    }

    private List<String> getAuctionStrings() {
        List<String> strings = new ArrayList<>();
        for (Auction auction : networkDB.getAuctionList()) {
            String string = auction.getSeller().getUsername() + "   " + auction.getCard().getName() + "   " + ((auction.getHighestBidder() == null) ? "empty" : auction.getHighestBidder()) + "   " + auction.getHighestBid();
            strings.add(string);
        }
        return strings;
    }

    private void caseGameFinished(Request request) {
        networkDB.getAccountStatusMap().put(connection.getAccount(), AccountStatus.online);
    }

    private void caseCancelMatchFinding() {
        Account account = connection.getAccount();
        networkDB.getAccountStatusMap().put(account, AccountStatus.online);
        networkDB.removeAccountFromWaitingList(account);
    }

    private void caseLeaderBoard() {
        networkDB.getAccountStatusMap().put(connection.getAccount(), AccountStatus.leaderBoard);
        List<Object> accountList = new ArrayList<>(networkDB.getAccountStatusMap().keySet());
        List<Integer> integers = new ArrayList<>();
        setIntegerList(accountList, integers);
        networkDB.sendResponseToClient(new Response(ResponseType.leaderBoard, null, integers, accountList), connection);
    }

    private void caseBuy(Request request) {
        OutputMessageType outputMessageType = connection.getAccount().getPlayerInfo().getCollection().buy(connection.getAccount(), request.getMessage());
        List<Object> accountList = new ArrayList<>();
        accountList.add(connection.getAccount());
        NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.buy, outputMessageType.getMessage(), null, accountList), connection);
    }

    private void caseShop(Request request) {
        NetworkDB.getInstance().getAccountStatusMap().put(connection.getAccount(), AccountStatus.shop);
        List<Object> cardList = new ArrayList<>(NetworkDB.getInstance().getCardList());
        List<Object> usableList = new ArrayList<>(NetworkDB.getInstance().getUsableList());
        List<Integer> integerList = new ArrayList<>();
        integerList.add(cardList.size());
        integerList.add(usableList.size());
        List<Object> cardsAndUsables = new ArrayList<>();
        cardList.forEach(e -> System.out.println(((Card) e).getName()));
        cardsAndUsables.addAll(cardList);
        cardsAndUsables.addAll(usableList);
        NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.shop, null, integerList, cardsAndUsables), connection);
    }

    private void caseSell(Request request) {
        Account account = connection.getAccount();
        OutputMessageType outputMessageType = account.getPlayerInfo().getCollection().sell(account, request.getMessage());
        List<Object> accountList = new ArrayList<>();
        accountList.add(account);
        networkDB.sendResponseToClient(new Response(ResponseType.sell, outputMessageType.getMessage(), null, accountList), connection);
    }

    private void caseRemoveCardFromDeck(Request request) {
        JsonObject jsonObject;
        String deckName;
        jsonObject = (JsonObject) request.getObjectList().get(0);
        deckName = jsonObject.get("deck").getAsString();
        String id = jsonObject.get("id").getAsString();
        connection.getAccount().getPlayerInfo().getCollection().removeCard(id, deckName);
    }

    private void caseMoveCardToDeck(Request request) {
        JsonObject jsonObject;
        String deckName;
        jsonObject = (JsonObject) request.getObjectList().get(0);
        deckName = jsonObject.get("deckName").getAsString();
        String cardId = jsonObject.get("cardID").getAsString();
        connection.getAccount().getPlayerInfo().getCollection().addCard(cardId, deckName);
    }

    private void caseExitCollection() {
        NetworkDB.getInstance().getAccountStatusMap().put(connection.getAccount(), AccountStatus.online);
    }

    private void caseImportDeck(Request request) {
        Deck deck;
        deck = (Deck) request.getObjectList().get(0);
        connection.getAccount().getPlayerInfo().getCollection().getDecks().add(deck);
    }

    private void caseSetMainDeck(Request request) {
        String deckName;
        Deck deck;
        deckName = request.getMessage();
        deck = connection.getAccount().getPlayerInfo().getCollection().getDeckByName(deckName);
        connection.getAccount().setMainDeck(deck);
    }

    private void caseRemoveDeck(Request request) {
        String deckName;
        deckName = request.getMessage();
        for (Deck tempDeck : connection.getAccount().getPlayerInfo().getCollection().getDecks()) {
            if (tempDeck.getName().equals(deckName)) {
                connection.getAccount().getPlayerInfo().getCollection().getDecks().remove(tempDeck);
                if (connection.getAccount().getMainDeck().getName().equals(deckName)) {
                    connection.getAccount().setMainDeck(null);
                }
                break;
            }
        }
    }

    private void caseCreateDeck(Request request) {
        Deck deck;
        deck = new Deck(request.getMessage());
        connection.getAccount().getPlayerInfo().getCollection().getDecks().add(deck);
    }

    private void caseEnterCollection(Request request) {
        NetworkDB.getInstance().getAccountStatusMap().put(connection.getAccount(), AccountStatus.collection);
    }

    private void caseExitGlobalChat(Request request) {
        Account account;
        account = networkDB.getAccountWithUserName(request.getMessage());
        networkDB.getAccountStatusMap().put(account, AccountStatus.online);
    }

    private void caseEnterGlobalChat(Request request) {
        Account account;
        account = networkDB.getAccountWithUserName(request.getMessage());
        networkDB.getAccountStatusMap().put(account, AccountStatus.chatting);
    }

    private void caseSendMessage(Request request) {
        ChatMessage chatMessage = (ChatMessage) request.getObjectList().get(0);
        for (Account tempAccount : networkDB.getAccountStatusMap().keySet()) {
            if (networkDB.getAccountStatusMap().get(tempAccount) == AccountStatus.chatting
                    && !tempAccount.getUsername().equals(chatMessage.getSender())) {
                Connection receiver = networkDB.getConnectionWithAccount(tempAccount);
                networkDB.sendResponseToClient(new Response(ResponseType.sendMessage, null, null, request.getObjectList()), receiver);
            }
        }
    }

    private void caseLogout(Request request) {
        Pattern pattern;
        Matcher matcher;
        Account account;
        pattern = Pattern.compile("userName:(\\w+)");
        matcher = pattern.matcher(request.getMessage());
        if (matcher.find()) {
            account = NetworkDB.getInstance().getAccount(matcher.group(1));
            if (account != null) {
                connection.setAccount(null);
                NetworkDB.getInstance().getAccountStatusMap().put(account, AccountStatus.offline);
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.logout, OutputMessageType.LOGGED_OUT_SUCCESSFULLY.getMessage(), null, null), connection);
                updateAccountsInLeaderBoard();
            }
        }
    }

    private void caseLogin(Request request) {
        Pattern pattern = Pattern.compile("userName:(\\w+)password:(\\w+)");
        Matcher matcher = pattern.matcher(request.getMessage());
        matcher.find();
        Account account = NetworkDB.getInstance().getAccount(matcher.group(1));
        if (account == null) {
            NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.login, OutputMessageType.ACCOUNT_DOESNT_EXIST.getMessage(), null, null), connection);
        } else {
            AccountStatus accountStatus = NetworkDB.getInstance().getAccountStatusMap().get(account);
            if (!matcher.group(2).equals(account.getPassword())) {
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.login, OutputMessageType.INVALID_PASSWORD.getMessage(), null, null), connection);
                return;
            }
            if (accountStatus == AccountStatus.offline) {
                List<Object> accountList = new ArrayList<>();
                accountList.add(account);
                NetworkDB.getInstance().getAccountStatusMap().put(account, AccountStatus.online);
                connection.setAccount(account);
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.login, OutputMessageType.LOGGED_IN_SUCCESSFULLY.getMessage(), null, accountList), connection);
                updateAccountsInLeaderBoard();
            } else {
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.login, OutputMessageType.ALREADY_LOGGED_IN.getMessage(), null, null), connection);
            }
        }
    }

    private void caseSignUp(Request request) {
        Pattern pattern;
        Matcher matcher;
        Account account;
        pattern = Pattern.compile("userName:(\\w+)password:(\\w+)");
        matcher = pattern.matcher(request.getMessage());
        matcher.find();
        account = NetworkDB.getInstance().getAccount(matcher.group(1));
        if (account != null) {
            NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.signUp, OutputMessageType.USERNAME_ALREADY_EXISTS.getMessage(), null, null), connection);
        } else {
            List<Object> accountList = new ArrayList<>();
            Account newAccount = new Account(matcher.group(1), matcher.group(2));
            connection.setAccount(newAccount);
            NetworkDB.getInstance().getAccountStatusMap().put(newAccount, AccountStatus.online);
            accountList.add(newAccount);
            NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.signUp, OutputMessageType.CREATED_ACCOUNT_SUCCESSFULLY.getMessage(), null, accountList), connection);
            updateAccountsInLeaderBoard();
        }
    }

    private void setIntegerList(List<Object> accountList, List<Integer> integerList) {
        for (Object object : accountList) {
            Account account = (Account) object;
            if (NetworkDB.getInstance().getAccountStatusMap().get(account).equals(AccountStatus.offline)) {
                integerList.add(0);
            } else {
                integerList.add(1);
            }
        }
    }

    private void updateAccountsInLeaderBoard() {
        List<Object> accountList = new ArrayList<>(NetworkDB.getInstance().getAccountStatusMap().keySet());
        List<Integer> integerList = new ArrayList<>();
        setIntegerList(accountList, integerList);
        for (Object object : accountList) {
            Account account = (Account) object;
            if (NetworkDB.getInstance().getAccountStatusMap().get(account).equals(AccountStatus.leaderBoard)) {
                NetworkDB.getInstance().sendResponseToClient(new Response(ResponseType.updateLeaderBoard, null, integerList, accountList), NetworkDB.getInstance().getConnectionWithAccount(account));
            }
        }
    }

    private void caseFindMultiFlagsMatch() {
        networkDB.addAccountWaitingForMultiFlags(connection.getAccount());
    }

    private void caseFindOneFlagMatch() {
        networkDB.addAccountWaitingForOneFlag(connection.getAccount());
    }

    private void caseFindClassicMatch() {
        networkDB.addAccountWaitingForClassic(connection.getAccount());
    }

    private void caseUseCollectable(Request request) {
        //todo use battle.useCollectable
    }

    private void caseUseSpecialPower(Request request) {
        Integer row = (Integer) request.getObjectList().get(0);
        Integer column = (Integer) request.getObjectList().get(1);
        Battle battle = connection.getCurrentBattle();
        Player player = networkDB.getPlayerWithAccount(connection.getAccount(), battle);
        List<Object> objects = new ArrayList<>();
        switch (battle.useSpecialPower(player, row, column, battle)) {
            case SPECIAL_POWER_USED:
                objects.add(row);
                objects.add(column);
                objects.add(battle);
                Response response = new Response(ResponseType.specialPowerUsed, null, null, objects);
                networkDB.sendResponseToPlayerAndOpponent(response, connection);
                break;
            case NO_HERO:
                System.out.println("no hero");
                //empty
                break;
            case SPECIAL_POWER_IN_COOLDOWN:
                System.out.println("in cooldown");
                //empty
                break;
            case HERO_HAS_NO_SPELL:
                System.out.println("hero no spell");
                //empty
                break;
            case NOT_ENOUGH_MANA:
                System.out.println("not enough mana");
                //empty
                break;
            case OUT_OF_BOUNDARIES:
                System.out.println("out of boundaries");
                //empty
                break;
            default:
                System.out.println("unhandled case!!!");
        }
    }

    private void caseInsertCard(Request request) {
        String id = request.getMessage();
        Integer row = (Integer) request.getObjectList().get(0);
        Integer column = (Integer) request.getObjectList().get(1);
        Battle battle = connection.getCurrentBattle();
        Player player = networkDB.getPlayerWithAccount(connection.getAccount(), battle);
        Card card = player.getHand().getCardById(id);
        switch (battle.insert(card, row, column, battle)) {
            case NO_SUCH_CARD_IN_HAND:
                //empty
                break;
            case NOT_ENOUGH_MANA:
                //empty
                break;
            case INVALID_NUMBER:
                //empty
                break;
            case NOT_NEARBY_FRIENDLY_UNITS:
                //empty
                break;
            case THIS_CELL_IS_FULL:
                //empty
                break;
            case CARD_INSERTED:
                List<Object> objects = new ArrayList<>();
                objects.add(row);
                objects.add(column);
                objects.add(card);
                objects.add(battle);
                Response response = new Response(ResponseType.cardInserted, card.getId(), null, objects);
                networkDB.sendResponseToPlayerAndOpponent(response, connection);
                break;
        }
    }

    private void caseEndTurn(Request request) {
        Battle battle = connection.getCurrentBattle();
        Response response;
        List<Object> objects = new ArrayList<>();
        objects.add(battle);
        switch (battle.nextTurn()) {
            case WINNER_PLAYER1:
                response = new Response(ResponseType.player1Won, null, null, objects);
                networkDB.sendResponseToPlayerAndOpponent(response, connection);
                break;
            case WINNER_PLAYER2:
                response = new Response(ResponseType.player2Won, null, null, objects);
                networkDB.sendResponseToPlayerAndOpponent(response, connection);
                break;
            case TURN_CHANGED:
                response = new Response(ResponseType.turnChanged, null, null, objects);
                networkDB.sendResponseToPlayerAndOpponent(response, connection);
                break;
            case INVALID_PLAYER:
                //empty
                break;
            default:
                System.out.println("unhandled case!!!");
        }
    }

    private void caseForfeit(Request request) {
        //todo maybe it isn't gonna be implemented
    }

    private void caseAttackUnit(Request request) {
        String id = request.getMessage();
        Battle battle = connection.getCurrentBattle();
        Player player = networkDB.getPlayerWithAccount(connection.getAccount(), battle);
        List<Object> objects = new ArrayList<>();
        Response response;
        switch (player.getSelectedUnit().attack(id, battle)) {
            case UNIT_AND_ENEMY_ATTACKED:
                objects.add(player.getSelectedUnit().getId());
                objects.add(id);
                objects.add(battle);
                response = new Response(ResponseType.unitAndEnemyAttacked, null, null, objects);
                networkDB.sendResponseToPlayerAndOpponent(response, connection);
                break;
            case UNIT_ATTACKED:
                objects.add(player.getSelectedUnit().getId());
                objects.add(id);
                objects.add(battle);
                response = new Response(ResponseType.unitAttacked, null, null, objects);
                networkDB.sendResponseToPlayerAndOpponent(response, connection);
                break;
            case ALREADY_ATTACKED:
                //empty
                break;
            case INVALID_CARD:
                //empty
                break;
            case TARGET_NOT_IN_RANGE:
                //empty
                break;
            case ATTACKED_FRIENDLY_UNIT:
                //empty
                break;
            default:
                System.out.println("unhandled case!!!!");
        }
        //todo
    }

    private void caseEnterGraveYard(Request request) {
        //todo
    }

    private void caseSelectUnit(Request request) {
        Battle battle = connection.getCurrentBattle();
        String id = request.getMessage();
        switch (battle.getPlayerInTurn().selectUnit(id, battle)) {
            case SELECTED:
                List<Object> objects = new ArrayList<>();
                objects.add(battle);
                Response response = new Response
                        (ResponseType.unitSelected, id, null, objects);
                networkDB.sendResponseToClient(response, connection);
                break;
            case INVALID_COLLECTABLE_CARD:
                //empty
                break;
            case ENEMY_UNIT_SELECTED:
                //empty
                break;
            case UNIT_IS_STUNNED:
                //empty
                break;
            default:
        }
    }

    private void caseMoveUnit(Request request) {
        Battle battle = connection.getCurrentBattle();
        Integer destinationRow = (Integer) request.getObjectList().get(0);
        Integer destinationColumn = (Integer) request.getObjectList().get(1);
        switch (battle.getBattleGround().moveUnit(destinationRow, destinationColumn, battle)) {
            case UNIT_MOVED:
                Unit selectedUnit = battle.getPlayerInTurn().getSelectedUnit();
                List<Object> objects = new ArrayList<>();
                objects.add(destinationRow);
                objects.add(destinationColumn);
                objects.add(battle);
                Response response = new Response(ResponseType.unitMoved, selectedUnit.getId()
                        , null, objects);
                networkDB.sendResponseToPlayerAndOpponent(response, connection);
                break;
            case OUT_OF_BOUNDARIES:
                //empty
                break;
            case UNIT_NOT_SELECTED:
                //empty
                break;
            case UNIT_ALREADY_MOVED:
                //empty
                break;
            case CELL_IS_FULL:
                //empty
                break;
            case CELL_OUT_OF_RANGE:
                //empty
                break;
            default:
                System.out.println("unhandled case!!!");
        }
    }

    private void logCase(Request request) {
        System.out.println(connection.getAccount().getUsername() + "-->>" + request.getRequestType());
    }
}