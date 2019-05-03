import java.net.PortUnreachableException;

public class Constants {
    private Constants() {
    }

    public static final String NONE = "none";
    public static final String ALL = "all";

    public static final String PASSIVE = "passive";
    public static final String CASTABLE = "castable";

    public static final String FLAGS = "flags";
    public static final String ONE_FLAG = "one-flag";
    public static final String CLASSIC = "classic";

    public static final String FRIEND = "friend";
    public static final String ENEMY = "enemy";

    public static final String HERO = "hero";
    public static final String MINION = "minion";
    public static final String HERO_MINION = "hero and minions";
    public static final String PLAYER = "player";

    public static final String CELL = "cell";

    public static final String MELEE = "melee";
    public static final String RANGED = "ranged";
    public static final String HYBRID = "hybrid";
    public static final String MELLE_RANGED = "melee and ranged";
    public static final String RANGED_HYBRID = "ranged and hybrid";
    public static final String MELEE_HYBRID = "melee and hybrid";

    public static final String POSITIVE = "positive";
    public static final String NEGATIVE = "negative";

    public static final int BATTLE_GROUND_WIDTH = 5;
    public static final int BATTLE_GROUND_LENGTH = 9;

    public static final int NUMBER_OF_HAND_CARDS = 5;

    public static final String DISARM = "disarm";
    public static final String POISON = "poison";
    public static final String ENEMY_CARD_SPELL = "enemy card spell";
    public static final String WEAKER_AP = "weaker ap";
    public static final String HOLY_BUFF = "holy buff";

    public static final String ID_PATTERN = "^(.+)_(.+)_(\\d+)$";
}