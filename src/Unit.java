import javax.naming.CompositeName;
import java.util.ArrayList;
import java.util.List;

class Unit extends Card {
    private int hp;
    private int ap;
    private String typeOfAttack;
    private int minRange;
    private int maxRange;
    private List<Flag> flags = new ArrayList<>();
    private String heroOrMinion;
    private Spell specialPower;

    public int getHp() {
        return hp;
    }

    public int getAp() {
        return ap;
    }

    public String getTypeOfAttack() {
        return typeOfAttack;
    }

    public String getHeroOrMinion() {
        return heroOrMinion;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public void setTypeOfAttack(String typeOfAttack) {
        this.typeOfAttack = typeOfAttack;
    }

    public void setHeroOrMinion(String heroOrMinion) {
        this.heroOrMinion = heroOrMinion;
    }

    public void addFlag(Flag newFlag) {
        flags.add(newFlag);
    }

    public void dropFlags() {

    }

    public static void killUnit(Unit unit) {

    }

    public void doSpecialPower() {

    }

    public void moveToCell(int row, int column) {

    }

    public void attackUnit(Unit unit) {

    }

    public void counterAttackUnit(Unit unit) {

    }

    public void takeItem(Item item) {

    }

    public List<Flag> getFlags() {
        return flags;
    }

    public String getUnitClass() {
        if (minRange == 1 && maxRange == 1)
            return Constants.MELEE;
        if (minRange == 1)
            return Constants.HYBRID;
        return Constants.RANGED;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public int getMinRange() {
        return minRange;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    public Spell getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Spell specialPower) {
        this.specialPower = specialPower;
    }
}
