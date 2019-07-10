import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class ControllerAuctionBuy {
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
