import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuffImage {
    private final int BUFF_VIEW_SIZE = 50;
    private AnchorPane root;
    private ImageView buffView = new ImageView();
    private List<BuffImage> buffViewList = new ArrayList<>();
    private String id;
    private BuffType buffType;

    public BuffImage(BuffType buffType, AnchorPane root) {
        this.root = root;
        this.buffType = buffType;
        buffView.setFitWidth(BUFF_VIEW_SIZE);
        buffView.setFitHeight(BUFF_VIEW_SIZE);
        setImage(buffType);
    }

    public BuffType getBuffType() {
        return buffType;
    }

    public void setImage(BuffType buffType) {
        this.buffType = buffType;
        try {
            buffView.setImage(new Image(new FileInputStream
                    ("./src/ApProjectResources/buffs/" + buffType.toString() + "/effect")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void relocate(double x, double y) {
        buffView.setTranslateX(x);
        buffView.setTranslateY(y);
    }
}