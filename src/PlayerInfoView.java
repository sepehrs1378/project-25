import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;

public class PlayerInfoView {
    private int mana = 0;
    private ImageView manaBarView = new ImageView();

    public PlayerInfoView() {
        ControllerBattleCommands.getOurInstance().getBattleGroundPane().getChildren().add(manaBarView);
    }

    public void setManaAmount(int mana) {
        this.mana = mana;
        try {
            manaBarView.setImage(new Image(new FileInputStream("./src/ApProjectResources/ManaBar/" + mana)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}