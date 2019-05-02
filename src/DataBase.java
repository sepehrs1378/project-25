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
        Buff totalDisarmBuff = new DisarmBuff(1000, true, false);
        Spell totalDisarm = new Spell("shop_totalDisarm_1", "totalDisarm", 1000, 0, 0, 0, 0, totalDisarmTarget, totalDisarmBuff, SpellActivationType.ON_CAST, "", false);
        cardList.add(totalDisarm);

        //2
        Target areaDispelTarget = new Target(Constants.HERO_MINION, 2, 2, Constants.ALL, false, false, 0, Constants.ALL);
        Spell areaDispel = new Spell("shop_areaDispel_1", "areaDispel", 1500, 2, 0, 0, 0, areaDispelTarget, (Buff) null, SpellActivationType.ON_CAST, "", true);
        cardList.add(areaDispel);

        //11
        Target allPoisonTarget = new Target(Constants.HERO_MINION,Integer.MAX_VALUE,Integer.MAX_VALUE,Constants.ENEMY,false,false,0,Constants.ALL);
        PoisonBuff allPoisonBuff=new PoisonBuff(4,true,false,1);
        Spell allPoison = new Spell("shop_allPoison_1","allPoison",1500,8,0,0,0,allPoisonTarget,allPoisonBuff,SpellActivationType.ON_CAST,"",false);
        cardList.add(allPoison);

        //12
        Target dispelTarget=new Target(Constants.HERO_MINION,1,1,Constants.ALL,false,false,0,Constants.ALL);
        Spell dispel=new Spell("shop_dispel_1","dispel",2100,0,0,0,0,dispelTarget,(Buff)null,SpellActivationType.ON_CAST,"",true);
        cardList.add(dispel);

        //13
        Target healthWithProfitTarget= new Target(Constants.ALL,1,1,Constants.FRIEND,false,false,0,Constants.ALL);
        List<Buff> healthWithProfitAddedBuffs = new ArrayList<>();
        WeaknessBuff healthWithProfitBuff1= new WeaknessBuff(Integer.MAX_VALUE,true,false,6,0);
        HolyBuff healthWithProfitBuff2=new HolyBuff(3,true,false,2);
        healthWithProfitAddedBuffs.add(healthWithProfitBuff1);
        healthWithProfitAddedBuffs.add(healthWithProfitBuff2);
        Spell healthWithProfit = new Spell("shop_healthWithProfit_1","healthWithProfit",2250,0,0,0,0,healthWithProfitTarget,healthWithProfitAddedBuffs,SpellActivationType.ON_CAST,"",false);
        cardList.add(healthWithProfit);

        //14
        Target powerUpTarget= new Target(Constants.HERO_MINION,1,1,Constants.FRIEND,false,false,0,Constants.ALL);
        PowerBuff powerUpBuff = new PowerBuff(Integer.MAX_VALUE,true,false,0,6);
        Spell powerUp = new Spell("shop_powerUp_1","powerUp",2500,2,0,0,0,powerUpTarget,powerUpBuff,SpellActivationType.ON_CAST,"",false);
        cardList.add(powerUp);

        //15
        Target allPowerTarget = new Target(Constants.HERO_MINION,Integer.MAX_VALUE,Integer.MAX_VALUE,Constants.FRIEND,false,false,0,Constants.ALL);
        PowerBuff allPowerBuff = new PowerBuff(Integer.MAX_VALUE,true,false,0,2);
        Spell allPower=new Spell("shop_allPower_1","allPower",200,4,0,0,0,allPowerTarget,allPowerBuff,SpellActivationType.ON_CAST,"",false);
        cardList.add(allPower);

        //16
        Target allAttackTarget = new Target(Constants.HERO_MINION,Integer.MAX_VALUE,1,Constants.ENEMY,false,false,0,Constants.ALL);
        Spell allAttack = new Spell("shop_allAttack_1","allAttack",1500,4,0,-6,0,allAttackTarget,(Buff)null,SpellActivationType.ON_CAST,"",false);
        cardList.add(allAttack);

        //17
        Target weakeningTarget = new Target(Constants.MINION,1,1,Constants.ENEMY,false,false,0,Constants.ALL);
        WeaknessBuff weakeningBuff = new WeaknessBuff(Integer.MAX_VALUE,true,false,0,4);
        Spell weakening = new Spell("shop_weakening_1","weakening",1000,1,0,0,0,weakeningTarget,weakeningBuff,SpellActivationType.ON_CAST,"",false);
        cardList.add(weakening);

        //18
        Target sacrificeTarget=new Target (Constants.MINION,1,1,Constants.FRIEND,false,false,0,Constants.ALL);
        List<Buff> sacrificeBuffs = new ArrayList<>();
        WeaknessBuff sacrificeBuff1= new WeaknessBuff(Integer.MAX_VALUE,true,false,6,0);
        PowerBuff sacrificeBuff2=new PowerBuff(Integer.MAX_VALUE,true,false,0,8);
        sacrificeBuffs.add(sacrificeBuff1);
        sacrificeBuffs.add(sacrificeBuff2);
        Spell sacrifice = new Spell("shop_sacrifice_1","sacrifice",1600,2,0,0,0,sacrificeTarget,sacrificeBuffs,SpellActivationType.ON_CAST,"",false);
        cardList.add(sacrifice);

        //19
        //Target kingsGuardTarget = new Target()
        //todo

        //20
        Target shockTarget = new Target(Constants.HERO_MINION,1,1,Constants.ENEMY,false,false,0,Constants.ALL);
        StunBuff shockBuff = new StunBuff(2,true,false);
        Spell shock = new Spell("shop_shock_1","shok",1200,1,0,0,0,shockTarget,shockBuff,SpellActivationType.ON_CAST,"",false);
        cardList.add(shock);

    }

    public void makeHeroes() {

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