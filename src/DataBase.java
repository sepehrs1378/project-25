import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBase {
    private static DataBase ourInstance = new DataBase();
    private List<Usable> usableList = new ArrayList<>();
    private List<Collectable> collectableList = new ArrayList<>();
    private List<Card> cardList = new ArrayList<>();
    private List<Account> accountList = new ArrayList<>();
    private Account loggedInAccount;
    private Battle currentBattle;
    private Account computerPlayerLevel1 = new Account();
    private Account computerPlayerLevel2 = new Account();
    private Account computerPlayerLevel3 = new Account();
    private Account computerPlayerCostume = new Account();

    public static DataBase getInstance() {
        return ourInstance;
    }

    private DataBase() {
    }

    public void makeCardSpells() {
        //1
        Target totalDisarmTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        DisarmBuff totalDisarmBuff = new DisarmBuff(1000, true, false);
        Spell totalDisarm = new Spell("shop_totalDisarm_1", "totalDisarm", 1000, 0, 0, 0, 0, totalDisarmTarget, totalDisarmBuff, SpellActivationType.ON_CAST, "", false);
        cardList.add(totalDisarm);

        //2
        Target areaDispelTarget = new Target(Constants.HERO_MINION, 2, 2, Constants.ALL, false, false, 0, Constants.ALL);
        Spell areaDispel = new Spell("shop_areaDispel_1", "areaDispel", 1500, 2, 0, 0, 0, areaDispelTarget, (Buff) null, SpellActivationType.ON_CAST, "", true);
        cardList.add(areaDispel);

        //3
        Target empowerTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        Spell empower = new Spell("shop_empower_1", "empower", 250, 1, 2, 0, 0, empowerTarget, (Buff) null, SpellActivationType.ON_CAST, "", false);
        cardList.add(empower);

        //4
        Target fireBallTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell fireBall = new Spell("shop_fireBall_1", "fireBall", 400, 1, 0, -4, 0, fireBallTarget, (Buff) null, SpellActivationType.ON_CAST, "", false);
        cardList.add(fireBall);

        //5
        Target godStrengthTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.ALL);
        Spell godStrength = new Spell("shop_godStrength_1", "godStrength", 450, 2, 4, 0, 0, godStrengthTarget, (Buff) null, SpellActivationType.ON_CAST, "", false);
        cardList.add(godStrength);

        //6
        Target hellFireTarget = new Target(Constants.CELL, 2, 2, Constants.NONE, false, false, 0, Constants.NONE);
        InfernoBuff hellFireBuff = new InfernoBuff(2, false, false, 1);
        Spell hellFire = new Spell("shop_hellFire_1", "hellFire", 600, 3, 0, 0, 0, hellFireTarget, hellFireBuff, SpellActivationType.ON_CAST, "", false);
        cardList.add(hellFire);

        //7
        Target lightingBoltTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell lightingBolt = new Spell("shop_lightingBolt_1", "lightingBolt", 1250, 2, 0, -8, 0, lightingBoltTarget, (Buff) null, SpellActivationType.ON_CAST, "", false);
        cardList.add(lightingBolt);

        //8
        Target poisonLakeTarget = new Target(Constants.CELL, 3, 3, Constants.NONE, false, false, 0, Constants.NONE);
        PoisonBuff poisonLakeBuff = new PoisonBuff(1, false, false, 1);
        Spell poisonLake = new Spell("shop_poisonLake_1", "poisonLake", 900, 5, 0, 0, 0, poisonLakeTarget, poisonLakeBuff, SpellActivationType.ON_CAST, "", false);
        cardList.add(poisonLake);

        //9
        Target madnessTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        DisarmBuff madnessBuff1 = new DisarmBuff(3, true, false);
        PowerBuff madnessBuff2 = new PowerBuff(3, true, false, 0, 4);
        List<Buff> madnessBuffs = new ArrayList<>();
        madnessBuffs.add(madnessBuff1);
        madnessBuffs.add(madnessBuff2);
        Spell madness = new Spell("shop_madness_1", "madness", 650, 0, 0, 0, 0, madnessTarget, madnessBuffs, SpellActivationType.ON_CAST, "", false);
        cardList.add(madness);

        //10
        Target allDisarmTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        DisarmBuff allDisarmBuff = new DisarmBuff(1, true, false);
        Spell allDisarm = new Spell("shop_allDisarm_1", "allDisarm", 2000, 9, 0, 0, 0, allDisarmTarget, allDisarmBuff, SpellActivationType.ON_CAST, "", false);
        cardList.add(allDisarm);


    }

    public void makeHeroes() {
        //1
        Target divSefidTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, true, 0, Constants.ALL);
        PowerBuff divSefidBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 4);
        Spell divSefidSpell = new Spell("", "", 0, 1, 0, 0, 2, divSefidTarget, divSefidBuff, SpellActivationType.ON_CAST, "", false);
        Unit divSefid = new Unit("shop_divSefid_1", "divSefid", 8000, 0, 50, 4, 1, 1, divSefidSpell, Constants.HERO, "", false);
        cardList.add(divSefid);
    }

    public void makeMinions() {

    }

    public void makeUsables() {

    }

    public void makeCollectables() {

    }

    public List<Card> getCardList() {
        return cardList;
    }

    public List<Usable> getUsableList() {
        return usableList;
    }

    public List<Collectable> getCollectableList() {
        return collectableList;
    }

    public Account getComputerPlayerLevel1() {
        return computerPlayerLevel1;
    }

    public Account getComputerPlayerLevel2() {
        return computerPlayerLevel2;
    }

    public Account getComputerPlayerLevel3() {
        return computerPlayerLevel3;
    }

    public Account getComputerPlayerCostume() {
        return computerPlayerCostume;
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public Battle getCurrentBattle() {
        return currentBattle;
    }

    public void setCurrentBattle(Battle currentBattle) {
        this.currentBattle = currentBattle;
    }

    public List<Account> getAccounts() {
        return accountList;
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }

    public void sortAccountsByWins() {
        Collections.sort(accountList);
    }

    public Card getCardWithName(String cardName) {
        for (Card card : cardList) {
            if (card.getId().equals(cardName))
                return card;
        }
        return null;
    }

    public boolean doesCardExist(String cardName) {
        return getCardWithName(cardName) != null;
    }

    public Usable getUsableWithName(String usableName) {
        for (Usable usable : usableList) {
            if (usable.getId().equals(usableName))
                return usable;
        }
        return null;
    }

    public boolean doesUsableExist(String itemName) {
        return getUsableWithName(itemName) != null;
    }

    public Collectable getCollectableWithName(String collectableName) {
        for (Collectable collectable : collectableList) {
            if (collectable.getId().equals(collectableName))
                return collectable;
        }
        return null;
    }

    public boolean doesCollectableExist(String collectableName) {
        return getCollectableWithName(collectableName) != null;
    }

    public Account getAccountWithUsername(String username) {
        for (Account account : accountList) {
            if (account.getUsername().equals(username))
                return account;
        }
        return null;
    }
}