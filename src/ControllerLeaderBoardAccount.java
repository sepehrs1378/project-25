import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerLeaderBoardAccount {
    @FXML
    private AnchorPane accountPane;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label winsLabel;

    @FXML
    private Label lossesLabel;

    @FXML
    private Label statusLabel;

    @FXML
    void makeAccountPaneOpaque(MouseEvent event) {
        accountPane.setStyle("-fx-opacity: 1; -fx-background-color: #04006b");
    }

    @FXML
    void makeAccountPaneTransparent(MouseEvent event) {
        accountPane.setStyle("-fx-opacity: 0.9; -fx-background-color: #04006b");
    }

    public void setAccountLabels(Account account, Integer integer){
        usernameLabel.setText(account.getUsername());
        winsLabel.setText(Integer.toString(account.getNumberOfWins()));
        lossesLabel.setText(Integer.toString(account.getNumberOfLosses()));
        if (integer == 0){
            statusLabel.setText(Constants.OFFLINE);
        }else {
            statusLabel.setText(Constants.ONLINE);
        }
    }
}
