import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Collections;
import java.util.List;

public class ControllerCollection {
    private static ControllerCollection ourInstance = new ControllerCollection();
    private Request request = Request.getInstance();
    private DataBase dataBase = DataBase.getInstance();
    private View view = View.getInstance();

    public ControllerCollection() {
        ourInstance = this;
    }

    public static ControllerCollection getInstance() {
        return ourInstance;
    }

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView showCardsBtn;

    @FXML
    void makeShowCardsBtnOpaque(MouseEvent event) {
        showCardsBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeShowCardsBtnTransparent(MouseEvent event) {
        showCardsBtn.setStyle("-fx-opacity: 0.6");
    }

    @FXML
    private JFXTextField createNewDeckText;

    @FXML
    private JFXTextField cardInCollectionText;

    @FXML
    private JFXTextField deckNameText;

    @FXML
    private JFXTextField itemText;

    @FXML
    private JFXTextField deckNameItemText;

    @FXML
    void addItemToDeck(MouseEvent event) {
        if (itemText.getText().isEmpty()){
            return;
        }
        PlayerCollection playerCollection = dataBase.getLoggedInAccount().getPlayerInfo().getCollection();
        if (deckNameItemText.getText().isEmpty()){
            return;
        }
        Deck deck = playerCollection.getDeckByName(deckNameText.getText());
        Item item = playerCollection.getItemWithID(itemText.getText());
        deck.setItem(item);
    }



    @FXML
    void addCardToDeck(MouseEvent event) {
        if (cardInCollectionText.getText().isEmpty()){
            return;
        }
        if (deckNameText.getText().isEmpty()){
            return;
        }
        PlayerCollection playerCollection = dataBase.getLoggedInAccount().getPlayerInfo().getCollection();
        OutputMessageType outputMessageType =
                playerCollection.addCard(cardInCollectionText.getText(), deckNameText.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, outputMessageType.getMessage());
        alert.showAndWait();
    }


    @FXML
    void createNewDeck(MouseEvent event) {
        if (createNewDeckText.getText().isEmpty()){
            return;
        }
        OutputMessageType outputMessageType =
                dataBase.getLoggedInAccount().getPlayerInfo().getCollection().createDeck(createNewDeckText.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, outputMessageType.getMessage());
        alert.showAndWait();
    }

    @FXML
    void showCards(MouseEvent event) {
//        List<Card> cards = dataBase.getLoggedInAccount().getPlayerInfo().getCollection().getCards();
//        for (int i = 0; i < cards.size(); i++) {
//            Card card = cards.get(i);
//            if (card instanceof  Unit){
//
//            }else if (card instanceof Spell){
//
//            }
//
//        } todo
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
