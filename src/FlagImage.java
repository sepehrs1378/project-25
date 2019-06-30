import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.stream.FileImageInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FlagImage {
    private int row = 0;
    private int column = 0;
    private ImageView flagView;
    private String id;
    public FlagImage(){
        try {
            flagView = new ImageView(new Image(new FileInputStream("./src/ApProjectResources/flag/flag")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(flagView);
        }
    }
    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setFlagView(ImageView flagView) {
        this.flagView = flagView;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public ImageView getFlagView() {
        return flagView;
    }

    public String getId() {
        return id;
    }
}
