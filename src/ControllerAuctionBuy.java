import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class ControllerAuctionBuy {
    private static ControllerAuctionBuy instance;
    public ControllerAuctionBuy(){
        instance = this;
    }
    public static ControllerAuctionBuy getInstance(){
        return instance;
    }

    @FXML
    private Button submitBtn;

    @FXML
    private TextField offerTxt;

    @FXML
    private ListView<String> auctionsList;

    @FXML
    private ImageView backBtn;

    @FXML
    void submit(ActionEvent event) {
        if(auctionsList.getSelectionModel().getSelectedItem() == null){
            new Alert(Alert.AlertType.ERROR,"please select a card to offer money!").showAndWait();
            return;
        }
        if (offerTxt.getText()==null||offerTxt.getText()==""||!offerTxt.getText().matches("\\d+")){
            new Alert(Alert.AlertType.ERROR,"please enter a valid offer!").showAndWait();
            return;
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("bidder",ClientDB.getInstance().getLoggedInAccount().getUsername() );
        obj.addProperty("bid", Integer.parseInt(offerTxt.getText()));
        obj.addProperty("cardName",auctionsList.getSelectionModel().getSelectedItem().split("\\s+")[1]);
        obj.addProperty("sellerName",auctionsList.getSelectionModel().getSelectedItem().split("\\s+")[0]);
        //todo check string pattern later
        List<Object> objects = new ArrayList<>();
        objects.add(obj);
        new ServerRequestSender(new Request(RequestType.submitOffer,null,null,objects)).start();
    }

    @FXML
    void goBack(MouseEvent event) {

    }

    @FXML
    void makeBackBtnOpaque(MouseEvent event) {

    }

    @FXML
    void makeBackBtnTransparent(MouseEvent event) {

    }

    public void upadateAuctionList(List<String> auctionInfo){
        auctionsList.getItems().clear();
        for (String string:auctionInfo){
            auctionsList.getItems().add(string);
        }

    }
}

