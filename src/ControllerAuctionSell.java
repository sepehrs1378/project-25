import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerAuctionSell implements Initializable {
    private static ControllerAuctionSell instance;
    public ControllerAuctionSell(){
        instance = this;
    }
    public static ControllerAuctionSell getInstance(){
        return instance;
    }
    @FXML
    private Label timeLbl;

    @FXML
    private Label prizeLbl;

    @FXML
    private ListView<String> biddersList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Label getTimeLbl() {
        return timeLbl;
    }

    public Label getPrizeLbl() {
        return prizeLbl;
    }

    public ListView<String> getBiddersList() {
        return biddersList;
    }

    public void updateBiddersList(Map<String,Integer> bidders){
        biddersList.getItems().clear();
        LinkedHashMap<String,Integer> result = MapUtil.sortByValue(bidders);
        result.forEach((name, money)-> biddersList.getItems().add(String.format("%s         %d",name,money)));
        prizeLbl.setText(result.values().stream().max(Comparator.comparingInt(o -> o)).toString());
    }
}
