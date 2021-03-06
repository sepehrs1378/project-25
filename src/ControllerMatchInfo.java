import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerMatchInfo implements Initializable {
    private static ControllerMatchInfo ourInstance = new ControllerMatchInfo();
    private static ClientDB clientDB = ClientDB.getInstance();
    public static Stage matchHistoryStage = null;

    @FXML
    private VBox matchHistoryBox;

    public ControllerMatchInfo() {
        ourInstance = this;
    }

    @FXML
    private ImageView closeBtn;

    @FXML
    void close(MouseEvent event) {
        Main.playWhenButtonClicked();
        matchHistoryStage.close();
    }

    @FXML
    void makeCloseBtnOpaque(MouseEvent event) {
        closeBtn.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCloseBtnTransparent(MouseEvent event) {
        closeBtn.setStyle("-fx-opacity: 0.6");
    }

    public static ControllerMatchInfo getInstance() {
        return ourInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<MatchInfo> matchList = clientDB.getLoggedInAccount().getMatchList();
        for (int i = matchList.size() - 1; i >= 0; i--) {
            MatchInfo matchInfo = matchList.get(i);
            String opponent = matchInfo.getOpponent();
            String winOrLoss;
            if (matchInfo.getWinner().equals(opponent)) {
                winOrLoss = "Loss";
            } else {
                winOrLoss = "Win";
            }
            matchInfo.calculatePassedTime();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ControllerMatchInfoElements.fxml"));
            try {
                Node node = fxmlLoader.load();
                ControllerMatchInfoElements controllerMatchInfoElements = fxmlLoader.getController();
                controllerMatchInfoElements.setHistoryStatus(opponent, winOrLoss, matchInfo.getDiffInSeconds(),
                        matchInfo.getDiffInMinutes(), matchInfo.getDiffInHours(), matchInfo.getDiffInDays(), matchInfo.getDiffInYears());
                matchHistoryBox.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
