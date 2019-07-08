import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Card {
    private String id;
    private String name;
    private int price;
    private int mana = 0;
    private boolean isCustom;


    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public Card(String id, String name, int price, int mana) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.mana = mana;
        isCustom = false;
    }

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getMana() {
        return mana;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getOwner() {
        Pattern pattern = Pattern.compile(Constants.ID_PATTERN);
        Matcher matcher = pattern.matcher(id);
        return NetworkDB.getInstance().getAccountWithUserName(matcher.group(1));
    }

    public Card clone() {
        return new Card(id, name, price, mana);
    }
}