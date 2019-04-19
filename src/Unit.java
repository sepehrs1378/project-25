import java.util.ArrayList;
import java.util.List;

class Unit extends Card {
    private int hp;
    private int ap;
    private String typeOfAttack;
    private int range;
    private List<Flag> flags = new ArrayList<>();
    private String heroOrMinion;
    private Spell specialPower;
    private String StringSpecialPower;
    private String name;


    public int getHp() {
        return hp;
    }

    public int getAp() {
        return ap;
    }

    public String getTypeOfAttack() {
        return typeOfAttack;
    }

    public int getRange() {
        return range;
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

    public void setRange(int range) {
        this.range = range;
    }

    public void setHeroOrMinion(String heroOrMinion) {
        this.heroOrMinion = heroOrMinion;
    }

    public void addFlag(Flag newFlag){

    }

    public void dropFlags(){

    }

    public static void killUnit(Unit unit){

    }

    public void doSpecialPower(){

    }

    public void moveToCell(int x,int y){

    }

    public void attackUnit(Unit unit){

    }

    public void takeItem(Item item){

    }

    public String getStringSpecialPower() {
        return StringSpecialPower;
    }

    public void setStringSpecialPower(String stringSpecialPower) {
        StringSpecialPower = stringSpecialPower;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
