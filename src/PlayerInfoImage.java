import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerInfoImage {
    private String heroId;
    private Label playerName;
    private List<ImageView> manaCells = new ArrayList<>();
    private ImageView heroFace = new ImageView();

    public PlayerInfoImage(int playerNumber, String heroId) {
        this.heroId = heroId;
        ControllerBattleCommands controllerBattleCommands = ControllerBattleCommands.getOurInstance();
        if (playerNumber == 1) {
            playerName = controllerBattleCommands.getPlayer1Label();
            heroFace = controllerBattleCommands.getP1HeroFace();
            manaCells = controllerBattleCommands.getPlayer1ManaCells();
        }
        if (playerNumber == 2) {
            playerName = controllerBattleCommands.getPlayer2Label();
            heroFace = controllerBattleCommands.getP2HeroFace();
            manaCells = controllerBattleCommands.getPlayer2ManaCells();
        }
        playerName.setText(getPlayerName());
        setHeroFace();
    }

    private void setHeroFace() {
        try {
            heroFace.setImage(new Image(new FileInputStream
                    ("src/ApProjectResources/units/" + getHeroName() + "/face")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMana(int mana) {
        Image manaEmptyCell = null;
        Image manaFullCell = null;
        try {
            manaEmptyCell = new Image(new FileInputStream("src/pics/manaEmptyCell.png"));
            manaFullCell = new Image(new FileInputStream("src/pics/manaFullCell.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < mana; i++)
            manaCells.get(i).setImage(manaFullCell);
        for (int i = mana; i < 9; i++) {
            manaCells.get(i).setImage(manaEmptyCell);
        }
    }

    private String getHeroName() {
        return heroId.split("_")[1];
    }

    private String getPlayerName() {
        return heroId.split("_")[0];
    }
}
