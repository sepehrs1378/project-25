import javax.swing.text.html.ImageView;
import java.awt.*;

public class UnitImage {
    private ImageView imageView;
    private String id;
    private UnitStatus unitStatus;

    public UnitImage() {
        //todo
    }

    public UnitStatus getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(UnitStatus unitStatus) {
        this.unitStatus = unitStatus;
    }
}
