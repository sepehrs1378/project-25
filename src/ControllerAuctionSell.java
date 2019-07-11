import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAuctionSell implements Initializable {
    private static ControllerAuctionSell instance;

    public ControllerAuctionSell() {
        instance = this;
    }

    public static ControllerAuctionSell getInstance() {
        return instance;
    }

    @FXML
    private Label timeLbl;

    @FXML
    private Label prizeLbl;

    @FXML
    private ListView<String> biddersList;

    public Label getTimeLbl() {
        return timeLbl;
    }

    public Label getPrizeLbl() {
        return prizeLbl;
    }

    public ListView<String> getBiddersList() {
        return biddersList;
    }

    public void updateBiddersList(List<String> bidders, List<Integer> bids) {
        biddersList.getItems().clear();
        for (int i = 0; i < bidders.size(); i++) {
            biddersList.getItems().add(bidders.get(i) + "      " + bids.get(i));
        }
        prizeLbl.setText(getMax(bids)+"");

    }

    private int getMax(List<Integer> integers) {
        if (integers.size() == 0) {
            return 0;
        }
        int max = integers.get(0);
        for (Integer i : integers) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(() -> {
            int counter =30;
            while (counter>0) {
                counter--;
                final int temp=counter;
                Platform.runLater(() -> {
                    timeLbl.setText(temp+ "");
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            new ServerRequestSender(new Request(RequestType.exitSellAuction,null,null,null));
        }).start();
    }
}
