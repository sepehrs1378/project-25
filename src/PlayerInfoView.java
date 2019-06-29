import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;

public class PlayerInfoView {
    private final int manaBarViewSize = 50;
    private final String manaBarDarkStyle = "-fx-opacity:0.2;";
    private final String manaBarLightStyle = "-fx-opcaity:1;";
    private int playerNumber;
    private int mana;
    private ImageView manaBarView = new ImageView();

    public PlayerInfoView(int playerNumber) {
        AnchorPane root = ControllerBattleCommands.getOurInstance().getBattleGroundPane();
        this.playerNumber = playerNumber;
        if (playerNumber == 1) {
            manaBarView.relocate(50, 50);
        } else {
            manaBarView.relocate(500, 50);
            setManaBarDark();
        }
        manaBarView.resize(manaBarViewSize, manaBarViewSize);
//        setManaAmount(2);
        addToRoot(root);
    }

//    public void setManaAmount(int mana) {
//        this.mana = mana;
//        try {
//            manaBarView.setImage(new Image(new FileInputStream("./src/ApProjectResources/ManaBar/" + mana)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void setManaBarDark() {
        manaBarView.setStyle(manaBarDarkStyle);
    }

    public void setManaBarBright() {
        manaBarView.setStyle(manaBarLightStyle);
    }

    public void addToRoot(AnchorPane root) {
        root.getChildren().add(manaBarView);
    }
}