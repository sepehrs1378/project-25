package ClientPackage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class ControllerMatchInfoElements {
    @FXML
    private Label accountName;

    @FXML
    private Label timeLabel;

    @FXML
    private AnchorPane matchHistoryPane;


    public void setHistoryStatus(String opponentName, String winOrLoss, long seconds,
                                 long minutes, long hours, long days, long years){
        File file;
        Image image;
        ImageView imageView;
        accountName.setText(opponentName);
        if (winOrLoss.equals("Win")){
            file = new File("src/pics/match_history_menu_pics/win_image.png");
            image = new Image(file.toURI().toString());
            imageView = new ImageView(image);
            imageView.setLayoutX(367);
            matchHistoryPane.getChildren().add(imageView);
        }else {
            file = new File("src/pics/match_history_menu_pics/loss_image.png");
            image = new Image(file.toURI().toString());
            imageView = new ImageView(image);
            imageView.setLayoutX(367);
            matchHistoryPane.getChildren().add(imageView);
        }
        if (years == 0 && days == 0 && hours == 0 && minutes == 0) {
            timeLabel.setText(seconds + "seconds ago");
        } else if (years == 0 && days == 0 && hours == 0) {
            timeLabel.setText(minutes + "minutes ago");
        } else if (years == 0 && days == 0) {
            timeLabel.setText(hours + "hours ago");
        } else if (years == 0) {
            timeLabel.setText(days + "days ago");
        } else {
            timeLabel.setText(years + "years ago");
        }
    }
}
