import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Card {
    private static final DataBase dataBase = DataBase.getInstance();
    private String id;
    private String name;
    private int price;
    private int mana;

    public Card(String id, String name, int price, int mana) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.mana = mana;
    }

    

    public Account getOwner() {
        Pattern pattern = Pattern.compile(Constants.ID_PATTERN);
        Matcher matcher = pattern.matcher(id);
        return dataBase.getAccountWithUsername(matcher.group(1));
    }

    public Card clone() {
        return new Card(id, name, price, mana);
    }
}