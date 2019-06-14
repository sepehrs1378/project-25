import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CardBackGroundController {
    @FXML
    private AnchorPane cardPane;

    @FXML
    private Label spellOrUnit;

    @FXML
    private ImageView card;

    @FXML
    private Label numberBoughtLabel;

    @FXML
    private Label manaLabel;

    @FXML
    private Label apLabel;

    @FXML
    private Label hpLabel;

    @FXML
    private Label cardName;

    @FXML
    private Label priceLable;

    @FXML
    void buyCard(MouseEvent event) {

    }

    @FXML
    void makeCardOpaque(MouseEvent event) {
        card.setStyle("-fx-opacity: 1");
    }

    @FXML
    void makeCardTransparent(MouseEvent event) {
        card.setStyle("-fx-opacity: 0.8");
    }

    public void setLabelsOfCardOrItem(Object object){
        if (object instanceof Card){
            Card newCard = (Card) object;
            if (newCard instanceof Unit){
                Unit unit = (Unit) newCard;
                priceLable.setText(Integer.toString(unit.getPrice()));
                apLabel.setText(Integer.toString(unit.getAp()));
                hpLabel.setText(Integer.toString(unit.getHp()));
                manaLabel.setText(Integer.toString(unit.getMana()));
                cardName.setText(unit.getName());
                spellOrUnit.setText(unit.getHeroOrMinion());
            }else if (newCard instanceof Spell){
                Spell spell = (Spell) newCard;
                priceLable.setText(Integer.toString(spell.getPrice()));
                manaLabel.setText(Integer.toString(spell.getMana()));
                cardName.setText(spell.getName());
                spellOrUnit.setText("Spell");
            }
        }
        if (object instanceof Usable){
            Usable usable = (Usable) object;
            cardName.setText(usable.getName());
            priceLable.setText(Integer.toString(usable.getPrice()));
            spellOrUnit.setText("Usable Item");
        }
    }

    public AnchorPane getCardPane(){
        return cardPane;
    }
}
