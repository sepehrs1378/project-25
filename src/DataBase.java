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
    private Account computerPlayerLevel1;
    private Account computerPlayerLevel2;
    private Account computerPlayerLevel3;
    private Account computerPlayerCostum;

    public static DataBase getInstance() {
        return ourInstance;
    }

    private DataBase() {
    }

    public void makeEveryThing() {
        makeCardSpells();
        makeHeroes();
        makeMinions();
        makeItems();
        makeAccounts();
    }

    private void makeCardSpells() {
        //1
        String desc1 = "Target : Enemy Unit - disarms an enemy unit for the entirety of the current battle";
        Target totalDisarmTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        DisarmBuff totalDisarmBuff = new DisarmBuff(1000, true, false);
        Spell totalDisarm = new Spell("shop_totalDisarm_1", "totalDisarm", 1000, 0, 0, 0, 0, totalDisarmTarget, totalDisarmBuff, SpellActivationType.ON_CAST, desc1, false);
        cardList.add(totalDisarm);

        //2
        String desc2 = "Target : 2 * 2 square area - this spell removes negative buffs afflicted on friendly units and all positive buffs currently possessed by enemy units";
        Target areaDispelTarget = new Target(Constants.HERO_MINION, 2, 2, Constants.ALL, false, false, 0, Constants.ALL);
        Spell areaDispel = new Spell("shop_areaDispel_1", "areaDispel", 1500, 2, 0, 0, 0, areaDispelTarget, (Buff) null, SpellActivationType.ON_CAST, desc2, true);
        cardList.add(areaDispel);

        //3
        String desc3 = "Target : Friendly Unit - adds 2 values to ap of one friendly unit";
        Target empowerTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        Spell empower = new Spell("shop_empower_1", "empower", 250, 1, 2, 0, 0, empowerTarget, (Buff) null, SpellActivationType.ON_CAST, desc3, false);
        cardList.add(empower);

        //4
        String desc4 = "Target : Enemy Unit - causes 4 damage to an enemy unit";
        Target fireBallTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell fireBall = new Spell("shop_fireBall_1", "fireBall", 400, 1, 0, -4, 0, fireBallTarget, (Buff) null, SpellActivationType.ON_CAST, desc4, false);
        cardList.add(fireBall);

        //5
        String desc5 = "Target : Friendly Hero - adds 4 ap";
        Target godStrengthTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.ALL);
        Spell godStrength = new Spell("shop_godStrength_1", "godStrength", 450, 2, 4, 0, 0, godStrengthTarget, (Buff) null, SpellActivationType.ON_CAST, desc5, false);
        cardList.add(godStrength);

        //6
        String desc6 = "Target : 2 * 2 square area - for duration of two turns adds HellFire effect to target";
        Target hellFireTarget = new Target(Constants.CELL, 2, 2, Constants.NONE, false, false, 0, Constants.NONE);
        InfernoBuff hellFireBuff = new InfernoBuff(2, false, false, 1);
        Spell hellFire = new Spell("shop_hellFire_1", "hellFire", 600, 3, 0, 0, 0, hellFireTarget, hellFireBuff, SpellActivationType.ON_CAST, desc6, false);
        cardList.add(hellFire);

        //7
        String desc7 = "Target : Enemy Hero - applies 8 damage to target";
        Target lightingBoltTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell lightingBolt = new Spell("shop_lightingBolt_1", "lightingBolt", 1250, 2, 0, -8, 0, lightingBoltTarget, (Buff) null, SpellActivationType.ON_CAST, desc7, false);
        cardList.add(lightingBolt);

        //8
        String desc8 = "Target : 3 * 3 square area - for duration of one turn adds PoisonLake effect to target";
        Target poisonLakeTarget = new Target(Constants.CELL, 3, 3, Constants.NONE, false, false, 0, Constants.NONE);
        PoisonBuff poisonLakeBuff = new PoisonBuff(1, false, false, 1);
        Spell poisonLake = new Spell("shop_poisonLake_1", "poisonLake", 900, 5, 0, 0, 0, poisonLakeTarget, poisonLakeBuff, SpellActivationType.ON_CAST, desc8, false);
        cardList.add(poisonLake);

        //9
        String desc9 = "Target : Friendly Unit - for duration of three turns adds 4 damage to target(it could be disarmed)";
        Target madnessTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        DisarmBuff madnessBuff1 = new DisarmBuff(3, true, false);
        PowerBuff madnessBuff2 = new PowerBuff(3, true, false, 0, 4);
        List<Buff> madnessBuffs = new ArrayList<>();
        madnessBuffs.add(madnessBuff1);
        madnessBuffs.add(madnessBuff2);
        Spell madness = new Spell("shop_madness_1", "madness", 650, 0, 0, 0, 0, madnessTarget, madnessBuffs, SpellActivationType.ON_CAST, desc9, false);
        cardList.add(madness);

        //10
        String desc10 = "Target : All Enemy Units - disarms target for the duration of one turn";
        Target allDisarmTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        DisarmBuff allDisarmBuff = new DisarmBuff(1, true, false);
        Spell allDisarm = new Spell("shop_allDisarm_1", "allDisarm", 2000, 9, 0, 0, 0, allDisarmTarget, allDisarmBuff, SpellActivationType.ON_CAST, desc10, false);
        cardList.add(allDisarm);

        //11
        String desc11 = "Target : All Enemy Units - poisons target for the duration of four turns";
        Target allPoisonTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        PoisonBuff allPoisonBuff = new PoisonBuff(4, true, false, 1);
        Spell allPoison = new Spell("shop_allPoison_1", "allPoison", 1500, 8, 0, 0, 0, allPoisonTarget, allPoisonBuff, SpellActivationType.ON_CAST, desc11, false);
        cardList.add(allPoison);

        //12
        String desc12 = "Target : Friendly or Enemy Unit - removes positive buffs currently possessed by Enemy Units and removes all negative buffs currently afflicted on Friendly Units";
        Target dispelTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ALL, false, false, 0, Constants.ALL);
        Spell dispel = new Spell("shop_dispel_1", "dispel", 2100, 0, 0, 0, 0, dispelTarget, (Buff) null, SpellActivationType.ON_CAST, desc12, true);
        cardList.add(dispel);

        //13
        String desc13 = "Target : Friendly Unit - takes 6 hp from target(WeaknessBuff) but gives 2 HolyBuffs for duration of 3 turns";
        Target healthWithProfitTarget = new Target(Constants.ALL, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        List<Buff> healthWithProfitAddedBuffs = new ArrayList<>();
        WeaknessBuff healthWithProfitBuff1 = new WeaknessBuff(Integer.MAX_VALUE, true, false, 6, 0);
        HolyBuff healthWithProfitBuff2 = new HolyBuff(3, true, false, 2);
        healthWithProfitAddedBuffs.add(healthWithProfitBuff1);
        healthWithProfitAddedBuffs.add(healthWithProfitBuff2);
        Spell healthWithProfit = new Spell("shop_healthWithProfit_1", "healthWithProfit", 2250, 0, 0, 0, 0, healthWithProfitTarget, healthWithProfitAddedBuffs, SpellActivationType.ON_CAST, desc13, false);
        cardList.add(healthWithProfit);

        //14
        String desc14 = "Target : Friendly Unit - applies a PowerBuff and adds two aps to target(Continuous)";
        Target powerUpTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        PowerBuff powerUpBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 6);
        Spell powerUp = new Spell("shop_powerUp_1", "powerUp", 2500, 2, 0, 0, 0, powerUpTarget, powerUpBuff, SpellActivationType.ON_CAST, desc14, false);
        cardList.add(powerUp);

        //15
        String desc15 = "Target : All Friendly Units - applies a PowerBuff that adds 2 aps to target(Continuous)";
        Target allPowerTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.ALL);
        PowerBuff allPowerBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 2);
        Spell allPower = new Spell("shop_allPower_1", "allPower", 200, 4, 0, 0, 0, allPowerTarget, allPowerBuff, SpellActivationType.ON_CAST, desc15, false);
        cardList.add(allPower);

        //16
        String desc16 = "Target : Every Enemy Unit In A Column - appplies 6 damage to target";
        Target allAttackTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell allAttack = new Spell("shop_allAttack_1", "allAttack", 1500, 4, 0, -6, 0, allAttackTarget, (Buff) null, SpellActivationType.ON_CAST, desc16, false);
        cardList.add(allAttack);

        //17
        String desc17 = "Target : Enemy Minion - applies WeaknessBuff and thus reducing target's ap by 4";
        Target weakeningTarget = new Target(Constants.MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        WeaknessBuff weakeningBuff = new WeaknessBuff(Integer.MAX_VALUE, true, false, 0, 4);
        Spell weakening = new Spell("shop_weakening_1", "weakening", 1000, 1, 0, 0, 0, weakeningTarget, weakeningBuff, SpellActivationType.ON_CAST, desc17, false);
        cardList.add(weakening);

        //18
        String desc18 = "Target : Friendly Minion - applies a WeaknessBuff reducing target's hp by 6 while applying PowerBuff increasing target's ap by 8";
        Target sacrificeTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        List<Buff> sacrificeBuffs = new ArrayList<>();
        WeaknessBuff sacrificeBuff1 = new WeaknessBuff(Integer.MAX_VALUE, true, false, 6, 0);
        PowerBuff sacrificeBuff2 = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 8);
        sacrificeBuffs.add(sacrificeBuff1);
        sacrificeBuffs.add(sacrificeBuff2);
        Spell sacrifice = new Spell("shop_sacrifice_1", "sacrifice", 1600, 2, 0, 0, 0, sacrificeTarget, sacrificeBuffs, SpellActivationType.ON_CAST, desc18, false);
        cardList.add(sacrifice);

        //19
        //String desc19 = "Target : Random Enemy Minion with distance less than 8 squares from Friendly Hero - kills target";
        //Target kingsGuardTarget = new Target()
        //cardList.add(null);
        //todo added this so that computer decks work correctly make a new one later
        Spell kingsGuard = new Spell("shop_kingsGuard_1","kingsGuard",1750,9,0,0,0,null,(Buff)null,SpellActivationType.ON_CAST,"",false);
        cardList.add(kingsGuard);

        //20
        String desc20 = "Target : Enemy Unit - stuns target for duration of 2 turns";
        Target shockTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        StunBuff shockBuff = new StunBuff(2, true, false);
        Spell shock = new Spell("shop_shock_1", "shock", 1200, 1, 0, 0, 0, shockTarget, shockBuff, SpellActivationType.ON_CAST, desc20, false);
        cardList.add(shock);

    }

    private void makeHeroes() {
        //1
        String descSpell1 = "mana : 1 - cooldown : 2 - spell activation type : on cast - is not dispeller";
        String descUnit1 = "price : 8000 - mana : 0 - hp : 50 - ap : 4 - minRange : 1 - maxRange : 1 - Unit Type : Hero - can't use combo";
        Target divSefidTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, true, 0, Constants.ALL);
        PowerBuff divSefidBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 4);
        Spell divSefidSpell = new Spell("", "", 0, 1, 0, 0, 2, divSefidTarget, divSefidBuff, SpellActivationType.ON_CAST, descSpell1, false);
        Unit divSefid = new Unit("shop_divSefid_1", "divSefid", 8000, 0, 50, 4, 1, 1, divSefidSpell, Constants.HERO, descUnit1, false);
        cardList.add(divSefid);

        //2
        String descSpell2 = "mana : 5 - cooldown : 8 - spell activation type : on cast - is not dispeller";
        String descUnit2 = "price : 9000 - mana : 0 - hp : 50 - ap : 4 - minRange : 1 - maxRange : 1 - Unit Type : Hero - can't use combo";
        Target simorghTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        StunBuff simorghBuff = new StunBuff(1, true, false);
        Spell simorghSpell = new Spell("", "", 0, 5, 0, 0, 8, simorghTarget, simorghBuff, SpellActivationType.ON_CAST, descSpell2, false);
        Unit simorgh = new Unit("shop_simorgh_1", "simorgh", 9000, 0, 50, 4, 1, 1, simorghSpell, Constants.HERO, descUnit2, false);
        cardList.add(simorgh);

        //3
        String descSpell3 = "mana : 0 - cooldown : 1 - spell activation type : on cast - is not dispeller";
        String descUnit3 = "price : 8000 - mana : 0 - hp : 50 - ap : 4 - minRange : 1 - maxRange : 1 - Unit Type : Hero - can't use combo";
        Target ejdehaHaftSarTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        DisarmBuff ejdehaHaftSarBuff = new DisarmBuff(Integer.MAX_VALUE, true, false);
        Spell ejdehaHaftSarSpellSpell = new Spell("", "", 0, 0, 0, 0, 1, ejdehaHaftSarTarget, ejdehaHaftSarBuff, SpellActivationType.ON_CAST, descSpell3, false);
        Unit ejdehaHaftSar = new Unit("shop_ejdehaHaftSar_1", "ejdehaHaftSar", 8000, 0, 50, 4, 1, 1, ejdehaHaftSarSpellSpell, Constants.HERO, descUnit3, false);
        cardList.add(ejdehaHaftSar);

        //4
        String descSpell4 = "mana : 1 - cooldown : 2 - spell activation type : on cast - is not dispeller";
        String descUnit4 = "price : 8000 - mana : 0 - hp : 50 - ap : 4 - minRange : 1 - maxRange : 1 - Unit Type : Hero - can't use combo";
        Target rakhshTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        StunBuff rakhshBuff = new StunBuff(1, true, false);
        Spell rakhshSpell = new Spell("", "", 0, 1, 0, 0, 2, rakhshTarget, rakhshBuff, SpellActivationType.ON_CAST, descSpell4, false);
        Unit rakhsh = new Unit("shop_rakhsh_1", "rakhsh", 8000, 0, 50, 4, 1, 1, rakhshSpell, Constants.HERO, descUnit4, false);
        cardList.add(rakhsh);

        //5
        String descSpell5 = "mana : 0 - cooldown : 0 - spell activation type : on cast - is not dispeller";
        String descUnit5 = "price : 10000 - mana : 0 - hp : 50 - ap : 2 - minRange : 1 - maxRange : 1 - Unit Type : Hero - can't use combo";
        Target zahakTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        PoisonBuff zhakBuff = new PoisonBuff(3, true, false, 1);
        Spell zahakSpell = new Spell("", "", 0, 0, 0, 0, 0, zahakTarget, zhakBuff, SpellActivationType.ON_CAST, descSpell5, false);
        Unit zahak = new Unit("shop_zahak_1", "zahak", 10000, 0, 50, 2, 1, 1, zahakSpell, Constants.HERO, descUnit5, false);
        cardList.add(zahak);

        //6
        String descSpell6 = "mana : 1 - cooldown : 3 - spell activation type : on cast - is not dispeller";
        String descUnit6 = "price : 8000 - mana : 0 - hp : 50 - ap : 4 - minRange : 1 - maxRange : 1 - Unit Type : Hero - can't use combo";
        Target kavehTarget = new Target(Constants.CELL, 1, 1, Constants.NONE, false, false, 0, Constants.NONE);
        HolyBuff kavehBuff = new HolyBuff(3, true, false, 1);
        Spell kavehSpell = new Spell("", "", 0, 1, 0, 0, 3, kavehTarget, kavehBuff, SpellActivationType.ON_CAST, descSpell6, false);
        Unit kaveh = new Unit("shop_kaveh_1", "kaveh", 8000, 0, 50, 4, 1, 1, kavehSpell, Constants.HERO, descUnit6, false);
        cardList.add(kaveh);

        //7
        String descSpell7 = "mana : 2 - hpChange : -4 - cooldown : 2 - spell activation type : on cast - is not dispeller";
        String descUnit7 = "price : 10000 - mana : 0 - hp : 30 - ap : 2 - minRange : 2 - maxRange : 6 - Unit Type : Hero - can't use combo";
        Target arashTarget = new Target(Constants.HERO_MINION, 1, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell arashSpell = new Spell("", "", 0, 2, 0, -4, 2, arashTarget, (Buff) null, SpellActivationType.ON_CAST, descSpell7, false);
        Unit arash = new Unit("shop_arash_1", "arash", 10000, 0, 30, 2, 2, 6, arashSpell, Constants.HERO, descUnit7, false);
        cardList.add(arash);

        //8
        String descSpell8 = "mana : 1 - apChange : 2 - cooldown : 3 - spell activation type : on cast - is dispeller";
        String descUnit8 = "price : 11000 - mana : 0 - hp : 40 - ap : 3 - minRange : 2 - maxRange : 3 - Unit Type : Hero - can't use combo";
        Target afsanehTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, true, 0, Constants.ALL);
        Spell afsanehSpell = new Spell("", "", 0, 1, 2, 0, 0, afsanehTarget, (Buff) null, SpellActivationType.ON_CAST, descSpell8, true);
        Unit afsaneh = new Unit("shop_afsaneh_1", "afsaneh", 11000, 0, 40, 3, 2, 3, afsanehSpell, Constants.HERO, descUnit8, false);
        cardList.add(afsaneh);

        //9
        String descSpell9 = "mana : 0 - cooldown : 0 - spell activation type : on spawn - is dispeller";
        String descUnit9 = "price : 12000 - mana : 0 - hp : 35 - ap : 3 - minRange : 1 - maxRange : 3 - Unit Type : Hero - can't use combo";
        Target esfandiarTarget = new Target(Constants.HERO, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        HolyBuff esfandiarBuff = new HolyBuff(Integer.MAX_VALUE, false, true, 3);
        Spell esfandiarSpell = new Spell("", "", 0, 0, 0, 0, 0, esfandiarTarget, esfandiarBuff, SpellActivationType.ON_SPAWN, descSpell9, false);
        Unit esfandiar = new Unit("shop_esfandiar_1", "esfandiar", 12000, 0, 35, 3, 1, 3, esfandiarSpell, Constants.HERO, descUnit9, false);
        cardList.add(esfandiar);

        //10
        String descUnit10 = "price : 8000 - mana : 0 - hp : 55 - ap : 7 - minRange : 1 - maxRange : 4 - Unit Type : Hero - can't use combo";
        Unit rostam = new Unit("shop_rostam_1", "rostam", 8000, 0, 55, 7, 1, 4, null, Constants.HERO, descUnit10, false);
        cardList.add(rostam);
    }

    private void makeMinions() {
        //1
        String descMinion1 = "price : 300 - mana : 2 - hp : 5 - ap : 4 - minRange : 2 - maxRange : 7 - Type : Minion - can't use combo";
        Unit kamandarFars = new Unit("shop_kamandarFars_1", "kamandarFars", 300, 2, 5, 4, 2, 7, null, Constants.MINION, descMinion1, false);
        cardList.add(kamandarFars);

        //2
        String descSpell2 = "Contains : StunBuff - Spell Activation Type : on attack - isn't dispeller";
        String descMinion2 = "price : 400 - mana : 2 - hp : 6 - ap : 4 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target shamshirZanFarsTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        StunBuff shamshirZanFarsBuff = new StunBuff(1, true, false);
        Spell shamshirZanFarsSpell = new Spell("", "", 0, 0, 0, 0, 0, shamshirZanFarsTarget, shamshirZanFarsBuff, SpellActivationType.ON_ATTACK, descSpell2, false);
        Unit shamshirZanFars = new Unit("shop_shamshirZanFars_1", "shamshirZanFars", 400, 2, 6, 4, 1, 1, shamshirZanFarsSpell, Constants.MINION, descMinion2, false);
        cardList.add(shamshirZanFars);

        //3
        String descMinion3 = "price : 500 - mana : 1 - hp : 5 - ap : 3 - minRange : 1 - maxRange : 3 - Type : Minion - can't use combo";
        Unit neizeDarFars = new Unit("shop_neizeDarFars_1", "neizeDarFars", 500, 1, 5, 3, 1, 3, null, Constants.MINION, descMinion3, false);
        cardList.add(neizeDarFars);

        //4
        String descMinion4 = "price : 200 - mana : 4 - hp : 10 - ap : 6 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Unit asbSavarFars = new Unit("shop_asbSavarFars_1", "asbSavarFars", 200, 4, 10, 6, 1, 1, null, Constants.MINION, descMinion4, false);
        cardList.add(asbSavarFars);

        //5
        //String descSpell5 = "Contains : StunBuff - Spell Activation Type : on attack - isn't dispeller";
        //String descMinion5 = "price : 600 - mana : 9 - hp : 24 - ap : 4 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        //todo check if this is true? (i think it is good to add a powerBuff for each attack)
//        Target pahlevanFarsTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.ALL);
//        Spell pahlevanFarsSpell = new Spell("", "", 0, 0, 5, 0, 0, pahlevanFarsTarget, (Buff) null, SpellActivationType.ON_ATTACK, descSpell5, false);
//        Unit pahlevanFars = new Unit("shop_pahlevanFars_1", "pahlevanFars", 600, 9, 24, 6, 1, 1, pahlevanFarsSpell, Constants.MINION, descMinion5, false);
//        cardList.add(pahlevanFars);
        //todo added this so that computer decks work correctly make a new one later
        Unit pahlevanFars = new Unit("shop_pahlevanFars_1","pahlevanFars",600,9,24,6,1,1,null,Constants.MINION,"",false);
        cardList.add(pahlevanFars);

        //6
        String descMinion6 = "price : 800 - mana : 7 - hp : 12 - ap : 4 - minRange : 1 - maxRange : 1 - Type : Minion - can use combo";
        Unit sepahSalarFars = new Unit("shop_sepahSalarFars_1", "sepahSalarFars", 800, 7, 12, 4, 1, 1, null, Constants.MINION, descMinion6, true);
        cardList.add(sepahSalarFars);

        //7
        String descMinion7 = "price : 500 - mana : 1 - hp : 3 - ap : 4 - minRange : 2 - maxRange : 5 - Type : Minion - can't use combo";
        Unit kamandarToorani = new Unit("shop_kamandarToorani_1", "kamandarToorani", 500, 1, 3, 4, 2, 5, null, Constants.MINION, descMinion7, false);
        cardList.add(kamandarToorani);

        //8
        String descMinion8 = "price : 600 - mana : 1 - hp : 4 - ap : 2 - minRange : 2 - maxRange : 7 - Type : Minion - can't use combo";
        Unit gholabSangDarToorani = new Unit("shop_gholabSangDarToorani_1", "gholabSangDarToorani", 600, 1, 4, 2, 2, 7, null, Constants.MINION, descMinion8, false);
        cardList.add(gholabSangDarToorani);

        //9
        String descMinion9 = "price : 600 - mana : 1 - hp : 4 - ap : 4 - minRange : 1 - maxRange : 3 - Type : Minion - can't use combo";
        Unit neizeDarToorani = new Unit("shop_neizeDarToorani_1", "neizeDarToorani", 600, 1, 4, 4, 1, 3, null, Constants.MINION, descMinion9, false);
        cardList.add(neizeDarToorani);

        //10
        String descSpell10 = "Contains : PoisonBuff and StunBuff - Spell Activation Type : on attack - isn't dispeller";
        String descMinion10 = "price : 700 - mana : 4 - hp : 6 - ap : 6 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target jasoosTooraniTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        List<Buff> jasoosTooraniBuffs = new ArrayList<>();
        DisarmBuff jasoosTooraniBuff1 = new DisarmBuff(1, true, false);
        PoisonBuff jasoosTooraniBuff2 = new PoisonBuff(4, true, false, 1);
        jasoosTooraniBuffs.add(jasoosTooraniBuff1);
        jasoosTooraniBuffs.add(jasoosTooraniBuff2);
        Spell jasoosTooraniSpell = new Spell("", "", 0, 0, 0, 0, 0, jasoosTooraniTarget, jasoosTooraniBuffs, SpellActivationType.ON_ATTACK, descSpell10, false);
        Unit jasoosToorani = new Unit("shop_jasoosToorani_1", "jasoosToorani", 700, 4, 6, 6, 1, 1, jasoosTooraniSpell, Constants.MINION, descMinion10, false);
        cardList.add(jasoosToorani);

        //11
        String descMinion11 = "price : 450 - mana : 2 - hp : 3 - ap : 10 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Unit gorzdarToorani = new Unit("shop_gorzdarToorani_1", "gorzdarToorani", 450, 2, 3, 10, 1, 1, null, Constants.MINION, descMinion11, false);
        cardList.add(gorzdarToorani);

        //12
        String descMinion12 = "price : 800 - mana : 6 - hp : 6 - ap : 10 - minRange : 1 - maxRange : 1 - Type : Minion - can use combo";
        Unit shahzadehToorani = new Unit("shop_shahzadehToorani_1", "shahzadehToorani", 800, 6, 6, 10, 1, 1, null, Constants.MINION, descMinion12, true);
        cardList.add(shahzadehToorani);

        //13
        String descMinion13 = "price : 300 - mana : 9 - hp : 14 - ap : 10 - minRange : 1 - maxRange : 7 - Type : Minion - can't use combo";
        Unit divSiah = new Unit("shop_divSiah_1", "divSiah", 300, 9, 14, 10, 1, 7, null, Constants.MINION, descMinion13, false);
        cardList.add(divSiah);

        //14
        String descMinion14 = "price : 300 - mana : 9 - hp : 12 - ap : 12 - minRange : 2 - maxRange : 7 - Type : Minion - can't use combo";
        Unit ghoolSangAndaz = new Unit("shop_ghoolSangAndaz_1", "ghoolSangAndaz", 300, 9, 12, 12, 2, 7, null, Constants.MINION, descMinion14, false);
        cardList.add(ghoolSangAndaz);

        //15
        String descMinion15 = "price : 200 - mana : 2 - hp : 0 - ap : 2 - minRange : 1 - maxRange : 3 - Type : Minion - can't use combo";
//        Target oghabTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        //todo remove if not needed
        PowerBuff oghabBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 10, 0);
        Unit oghab = new Unit("shop_oghab_1", "oghab", 200, 2, 0, 2, 1, 3, (Spell) null, Constants.MINION, descMinion15, false, oghabBuff);
        cardList.add(oghab);

        //16
        String descMinion16 = "price : 300 - mana : 6 - hp : 16 - ap : 8 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Unit divGorazSavar = new Unit("shop_divGorazSavar_1", "divGorazSavar", 300, 6, 16, 8, 1, 1, null, Constants.MINION, descMinion16, false);
        cardList.add(divGorazSavar);

        //17
        String descSpell17 = "Contains : no Buff - Spell Activation Type : on death - isn't dispeller";
        String descMinion17 = "price : 500 - mana : 7 - hp : 12 - ap : 11 - minRange : 2 - maxRange : 4 - Type : Minion - can't use combo";
        Target ghoolTakCheshmTarget = new Target(Constants.MINION, 3, 3, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell ghoolTakCheshmSpell = new Spell("", "", 0, 0, 0, -2, 0, ghoolTakCheshmTarget, (Buff) null, SpellActivationType.ON_DEATH, descSpell17, false);
        Unit ghoolTakCheshm = new Unit("shop_ghoolTakCheshm_1", "ghoolTakCheshm", 500, 7, 12, 11, 2, 4, ghoolTakCheshmSpell, Constants.MINION, descMinion17, false);
        cardList.add(ghoolTakCheshm);

        //18
        String descSpell18 = "Contains : PoisonBuff - Spell Activation Type : on attack - isn't dispeller";
        String descMinion18 = "price : 300 - mana : 4 - hp : 5 - ap : 6 - minRange : 2 - maxRange : 4 - Type : Minion - can't use combo";
        Target marSamiTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        PoisonBuff marSamiBuff = new PoisonBuff(3, true, false, 1);
        Spell marSamiSpell = new Spell("", "", 0, 0, 0, 0, 0, marSamiTarget, marSamiBuff, SpellActivationType.ON_ATTACK, descSpell18, false);
        Unit marSami = new Unit("shop_marSami_1", "marSami", 300, 4, 5, 6, 2, 4, marSamiSpell, Constants.MINION, descMinion18, false);
        cardList.add(marSami);

        //19
        String descMinion19 = "price : 250 - mana : 5 - hp : 9 - ap : 5 - minRange : 2 - maxRange : 4 - Type : Minion - can't use combo";
        Unit ejdehaAtashAndaz = new Unit("shop_ejdehaAtashAndaz_1", "ejdehaAtashAndaz", 250, 5, 9, 5, 2, 4, null, Constants.MINION, descMinion19, false);
        cardList.add(ejdehaAtashAndaz);

        //20
        String descSpell20 = "Contains : ImmunityBuff - Spell Activation Type : on attack - isn't dispeller";
        String descMinion20 = "price : 600 - mana : 2 - hp : 1 - ap : 8 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target shirDarandeTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, false, 0, Constants.MELEE);
        ImmunityBuff shirDarandeBuff = new ImmunityBuff(Integer.MAX_VALUE, true, false, Constants.HOLY_BUFF);
        Spell shirDarandeSpell = new Spell("", "", 0, 0, 0, 0, 0, shirDarandeTarget, shirDarandeBuff, SpellActivationType.ON_ATTACK, descSpell20, false);
        Unit shirDarande = new Unit("shop_shirDarande_1", "shirDarande", 600, 2, 1, 8, 1, 1, shirDarandeSpell, Constants.MINION, descMinion20, false);
        cardList.add(shirDarande);

        //21
        String descSpell21 = "Contains : NegativeArmorBuff - Spell Activation Type : on attack - isn't dispeller";
        String descMinion21 = "price : 500 - mana : 8 - hp : 14 - ap : 7 - minRange : 2 - maxRange : 5 - Type : Minion - can't use combo";
        Target marGhoolPeikarTarget = new Target(Constants.MINION, 1, 1, Constants.ENEMY, false, false, 2, Constants.ALL);
        NegativeArmorBuff marGhoolPeikarBuff = new NegativeArmorBuff(Integer.MAX_VALUE, true, false, 1);
        Spell marGhoolPeikarSpell = new Spell("", "", 0, 0, 0, 0, 0, marGhoolPeikarTarget, marGhoolPeikarBuff, SpellActivationType.ON_SPAWN, descSpell21, false);
        Unit marGhoolPeikar = new Unit("shop_marGhoolPeikar_1", "marGhoolPeikar", 500, 8, 14, 7, 2, 5, marGhoolPeikarSpell, Constants.MINION, descMinion21, false);
        cardList.add(marGhoolPeikar);

        //22
        String descSpell22 = "Contains : SequentialDamgeBuff - Spell Activation Type : on attack - isn't dispeller";
        String descMinion22 = "price : 400 - mana : 5 - hp : 8 - ap : 2 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target gorgSefidTarget = new Target(Constants.MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        SequentialDamageBuff gorgSefidBuff = new SequentialDamageBuff(2, false, true, 6, 4);
        Spell gorgSefidSpell = new Spell("", "", 0, 0, 0, 0, 1, gorgSefidTarget, gorgSefidBuff, SpellActivationType.ON_ATTACK, descSpell22, false);
        Unit gorgSefid = new Unit("shop_gorgSefid_1", "gorgSefid", 400, 5, 8, 2, 1, 1, gorgSefidSpell, Constants.MINION, descMinion22, false);
        cardList.add(gorgSefid);

        //23
        String descSpell23 = "Contains : WeaknessBuff - Spell Activation Type : on attack - isn't dispeller";
        String descMinion23 = "price : 400 - mana : 4 - hp : 6 - ap : 2 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target palangTarget = new Target(Constants.MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        WeaknessBuff palangBuff = new WeaknessBuff(1, true, false, 8, 0);
        Spell palangSpell = new Spell("", "", 0, 0, 0, 0, 1, palangTarget, palangBuff, SpellActivationType.ON_ATTACK, descSpell23, false);
        Unit palang = new Unit("shop_palang_1", "palang", 400, 4, 6, 2, 1, 1, palangSpell, Constants.MINION, descMinion23, false);
        cardList.add(palang);

        //24
        String descSpell24 = "Contains : WeaknessBuff - Spell Activation Type : on attack - isn't dispeller";
        String descMinion24 = "price : 400 - mana : 3 - hp : 6 - ap : 1 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target gorgTarget = new Target(Constants.MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        WeaknessBuff gorgBuff = new WeaknessBuff(1, true, false, 6, 0);
        Spell gorgSpell = new Spell("", "", 0, 0, 0, 0, 1, gorgTarget, gorgBuff, SpellActivationType.ON_ATTACK, descSpell24, false);
        Unit gorg = new Unit("shop_gorg_1", "gorg", 400, 3, 6, 1, 1, 1, gorgSpell, Constants.MINION, descMinion24, false);
        cardList.add(gorg);

        //25
        String descSpell25 = "Contains : PowerBuff and WeaknessBuff - Spell Activation Type : passive - isn't dispeller";
        String descMinion25 = "price : 550 - mana : 4 - hp : 5 - ap : 4 - minRange : 2 - maxRange : 3 - Type : Minion - can't use combo";
        Target jadoogarTarget = new Target(Constants.MINION, 3, 3, Constants.FRIEND, false, false, 0, Constants.ALL);
        List<Buff> jadoogarBuffs = new ArrayList<>();
        PowerBuff jadoogarBuff1 = new PowerBuff(1, true, false, 0, 2);
        WeaknessBuff jadoogarBuff2 = new WeaknessBuff(1, true, false, 1, 0);
        jadoogarBuffs.add(jadoogarBuff1);
        jadoogarBuffs.add(jadoogarBuff2);
        Spell jadoogarSpell = new Spell("", "", 0, 0, 0, 0, 0, jadoogarTarget, jadoogarBuffs, SpellActivationType.PASSIVE, descSpell25, false);
        Unit jadoogar = new Unit("shop_jadoogar_1", "jadoogar", 550, 4, 5, 4, 2, 3, jadoogarSpell, Constants.MINION, descMinion25, false);
        cardList.add(jadoogar);

        //26
        String descSpell26 = "Contains : PowerBuf and HolyBuff - Spell Activation Type : passive - isn't dispeller";
        String descMinion26 = "price : 550 - mana : 6 - hp : 6 - ap : 6 - minRange : 2 - maxRange : 5 - Type : Minion - can't use combo";
        Target jadoogarAzamTarget = new Target(Constants.MINION, 3, 3, Constants.FRIEND, false, false, 0, Constants.ALL);
        List<Buff> jadoogarAzamBuffs = new ArrayList<>();
        PowerBuff jadoogarAzamBuff1 = new PowerBuff(1, true, false, 0, 2);
        HolyBuff jadoogarAzamBuff2 = new HolyBuff(1, true, true, 1);
        jadoogarAzamBuffs.add(jadoogarAzamBuff1);
        jadoogarAzamBuffs.add(jadoogarAzamBuff2);
        Spell jadoogarAzamSpell = new Spell("", "", 0, 0, 0, 0, 0, jadoogarAzamTarget, jadoogarAzamBuffs, SpellActivationType.PASSIVE, descSpell26, false);
        Unit jadoogarAzam = new Unit("shop_jadoogarAzam_1", "jadoogarAzam", 550, 6, 6, 6, 2, 5, jadoogarAzamSpell, Constants.MINION, descMinion26, false);
        cardList.add(jadoogarAzam);

        //27
        String descSpell27 = "Contains : PowerBuff - Spell Activation Type : passive - isn't dispeller";
        String descMinion27 = "price : 500 - mana : 5 - hp : 10 - ap : 4 - minRange : 2 - maxRange : 4 - Type : Minion - can't use combo";
        //todo spell activation type ambiguous
        Target genTarget = new Target(Constants.MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.ALL);
        PowerBuff genBuff = new PowerBuff(Integer.MAX_VALUE, true, true, 0, 1);
        Spell genSpell = new Spell("", "", 0, 0, 0, 0, 0, genTarget, genBuff, SpellActivationType.PASSIVE, descSpell27, false);
        Unit gen = new Unit("shop_gen_1", "gen", 500, 5, 10, 4, 2, 4, genSpell, Constants.MINION, descMinion27, false);
        cardList.add(gen);

        //28
        String descSpell28 = "Contains : ImmunityBuff - Spell Activation Type : on defend - isn't dispeller";
        String descMinion28 = "price : 500 - mana : 6 - hp : 10 - ap : 14 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target gorazVahshiTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.MELEE);
        ImmunityBuff gorazVahshiBuff = new ImmunityBuff(Integer.MAX_VALUE, true, false, Constants.DISARM);
        Spell gorazVahshiSpell = new Spell("", "", 0, 0, 0, 0, 0, gorazVahshiTarget, gorazVahshiBuff, SpellActivationType.ON_DEFEND, descSpell28, false);
        Unit gorazVahshi = new Unit("shop_gorazVahshi_1", "gorazVahshi", 500, 6, 10, 14, 1, 1, gorazVahshiSpell, Constants.MINION, descMinion28, false);
        cardList.add(gorazVahshi);

        //29
        String descSpell29 = "Contains : ImmunityBuff - Spell Activation Type : on defend - isn't dispeller";
        String descMinion29 = "price : 400 - mana : 8 - hp : 20 - ap : 12 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target piranTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.MELEE);
        ImmunityBuff piranBuff = new ImmunityBuff(Integer.MAX_VALUE, true, false, Constants.POISON);
        Spell piranSpell = new Spell("", "", 0, 0, 0, 0, 0, piranTarget, piranBuff, SpellActivationType.ON_DEFEND, descSpell29, false);
        Unit piran = new Unit("shop_piran_1", "piran", 400, 8, 20, 12, 1, 1, piranSpell, Constants.MINION, descMinion29, false);
        cardList.add(piran);

        //30
        String descSpell30 = "Contains : ImmunityBuff - Spell Activation Type : on defend - isn't dispeller";
        String descMinion30 = "price : 450 - mana : 4 - hp : 5 - ap : 7 - minRange : 2 - maxRange : 5 - Type : Minion - can't use combo";
        Target givTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.RANGED);
        ImmunityBuff givBuff = new ImmunityBuff(Integer.MAX_VALUE, true, false, Constants.ENEMY_CARD_SPELL);
        Spell givSpell = new Spell("", "", 0, 0, 0, 0, 0, givTarget, givBuff, SpellActivationType.ON_DEFEND, descSpell30, false);
        Unit giv = new Unit("shop_giv_1", "giv", 450, 4, 5, 7, 2, 5, givSpell, Constants.MINION, descMinion30, false);
        cardList.add(giv);

        //31
        String descSpell31 = "Contains : WeaknessBuff - Spell Activation Type : on spawn - isn't dispeller";
        String descMinion31 = "price : 450 - mana : 8 - hp : 16 - ap : 9 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target bahmanTarget = new Target(Constants.MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, true, false, 0, Constants.ALL);
        WeaknessBuff bahmanBuff = new WeaknessBuff(1, true, false, 16, 0);
        Spell bahmanSpell = new Spell("", "", 0, 0, 0, 0, 0, bahmanTarget, bahmanBuff, SpellActivationType.ON_SPAWN, descSpell31, false);
        Unit bahman = new Unit("shop_bahman_1", "bahman", 450, 8, 16, 9, 1, 1, bahmanSpell, Constants.MINION, descMinion31, false);
        cardList.add(bahman);

        //32
        String descSpell32 = "Contains : ImmunityBuff - Spell Activation Type : on defend - isn't dispeller";
        String descMinion32 = "price : 400 - mana : 7 - hp : 14 - ap : 8 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target ashkboosTarget = new Target(Constants.MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.MELEE);
        ImmunityBuff ashkboosBuff = new ImmunityBuff(Integer.MAX_VALUE, true, false, Constants.WEAKER_AP);
        Spell ashkboosSpell = new Spell("", "", 0, 0, 0, 0, 0, ashkboosTarget, ashkboosBuff, SpellActivationType.ON_DEFEND, descSpell32, false);
        Unit ashkboos = new Unit("shop_ashkboos_1", "ashkboos", 400, 7, 14, 8, 1, 1, ashkboosSpell, Constants.MINION, descMinion32, false);
        cardList.add(ashkboos);

        //33
        String descMinion33 = "price : 500 - mana : 4 - hp : 6 - ap : 20 - minRange : 2 - maxRange : 3 - Type : Minion - can't use combo";
        Unit iraj = new Unit("shop_iraj_1", "iraj", 500, 4, 6, 20, 2, 3, null, Constants.MINION, descMinion33, false);
        cardList.add(iraj);

        //34
        String descMinion34 = "price :600 - mana : 9 - hp : 30 - ap : 8 - minRange : 1 - maxRange : 2 - Type : Minion - can't use combo";
        Unit ghoolBozorg = new Unit("shop_ghoolBozorg_1", "ghoolBozorg", 600, 9, 30, 8, 1, 2, null, Constants.MINION, descMinion34, false);
        cardList.add(ghoolBozorg);

        //35
        String descSpell35 = "Contains : no Buffs - Spell Activation Type : on attack - is dispeller";
        String descMinion35 = "price : 550 - mana : 4 - hp : 10 - ap : 4 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target ghoolDoSarTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell ghoolDoSarSpell = new Spell("", "", 0, 0, 0, 0, 0, ghoolDoSarTarget, (Buff) null, SpellActivationType.ON_ATTACK, descSpell35, true);
        Unit ghoolDoSar = new Unit("shop_ghoolDoSar_1", "ghoolDoSar", 550, 4, 10, 4, 1, 1, ghoolDoSarSpell, Constants.MINION, descMinion35, false);
        cardList.add(ghoolDoSar);

        //36
        String descSpell36 = "Contains : StunBuff - Spell Activation Type : on spawn - isn't dispeller";
        String descMinion36 = "price : 500 - mana : 3 - hp : 3 - ap : 4 - minRange : 2 - maxRange : 5 - Type : Minion - can't use combo";
        Target naneSarmaTarget = new Target(Constants.MINION, 3, 3, Constants.ENEMY, false, false, 0, Constants.ALL);
        StunBuff naneSarmaBuff = new StunBuff(1, true, false);
        Spell naneSarmaSpell = new Spell("", "", 0, 0, 0, 0, 0, naneSarmaTarget, naneSarmaBuff, SpellActivationType.ON_SPAWN, descSpell36, false);
        Unit naneSarma = new Unit("shop_naneSarma_1", "naneSarma", 500, 3, 3, 4, 2, 5, naneSarmaSpell, Constants.MINION, descMinion36, false);
        cardList.add(naneSarma);

        //37
        String descSpell37 = "Contains : HolyBuff - Spell Activation Type : passive - isn't dispeller";
        String descMinion37 = "price : 650 - mana : 3 - hp : 1 - ap : 1 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target fooladZerehTarget = new Target(Constants.MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, true, 0, Constants.MELEE);
        HolyBuff fooladZerehBuff = new HolyBuff(Integer.MAX_VALUE, true, true, 12);
        Spell fooladZerehSpell = new Spell("", "", 0, 0, 0, 0, 0, fooladZerehTarget, fooladZerehBuff, SpellActivationType.PASSIVE, descSpell37, false);
        Unit fooladZereh = new Unit("shop_fooladZereh_1", "fooladZereh", 650, 3, 1, 1, 1, 1, fooladZerehSpell, Constants.MINION, descMinion37, false);
        cardList.add(fooladZereh);

        //38
        String descSpell38 = "Contains : WeaknessBuff - Spell Activation Type : on death - isn't dispeller";
        String descMinion38 = "price : 350 - mana : 4 - hp : 8 - ap : 5 - minRange : 1 - maxRange : 1 - Type : Minion - can't use combo";
        Target siavashTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, true, 0, Constants.ALL);
        WeaknessBuff siavashBuff = new WeaknessBuff(1, false, false, 6, 0);
        Spell siavashSpell = new Spell("", "", 0, 0, 0, 0, 0, siavashTarget, siavashBuff, SpellActivationType.ON_DEATH, descSpell38, false);
        Unit siavash = new Unit("shop_siavash_1", "siavash", 350, 4, 8, 5, 1, 1, siavashSpell, Constants.MINION, descMinion38, false);
        cardList.add(siavash);

        //39
        String descMinion39 = "price : 600 - mana : 5 - hp : 10 - ap : 4 - minRange : 1 - maxRange : 1 - Type : Minion - can use combo";
        Unit shahGhool = new Unit("shop_shahGhool_1", "shahGhool", 600, 5, 10, 4, 1, 1, null, Constants.MINION, descMinion39, true);
        cardList.add(shahGhool);

        //40
        String descMinion40 = "price : 600 - mana : 3 - hp : 6 - ap : 6 - minRange : 1 - maxRange : 1 - Type : Minion - can use combo";
        Unit arzhangDiv = new Unit("shop_arzhangDiv_1", "arzhangDiv", 600, 3, 6, 6, 1, 1, null, Constants.MINION, descMinion40, true);
        cardList.add(arzhangDiv);
    }

    //todo it is better to merge makeUsables() && makeCollectables in one method because it is important for the indexes of cardList to be accurate based on phase1.peyvast :)
    private void makeItems() {
        //for items we will also add the number in their separate List for knowing which item in peyvast is in the separated item lists(needed in costume game mode) look at examples below for more info
        //inPeyvast     inEachList
        //1             //1
        String descSpell1 = "Contains : ManaBuff - Spell Activation Type : passive - isn't dispeller";
        Target tajDanayeeTarget = new Target(Constants.PLAYER, 1, 1, Constants.FRIEND, false, true, 0, Constants.ALL);
        ManaBuff tajDanayeeBuff = new ManaBuff(3, false, false, 1);
        Spell tajDanayeeSpell = new Spell("", "", 0, 0, 0, 0, 0, tajDanayeeTarget, tajDanayeeBuff, SpellActivationType.PASSIVE, descSpell1, false);
        String descUsable1 = "price : 300";
        Usable tajDanayee = new Usable("shop_tajDanayee_1", descUsable1, 300, tajDanayeeSpell);
        usableList.add(tajDanayee);

        //2             //2
        String descSpell2 = "Contains : HolyBuff - Spell Activation Type : passive - isn't dispeller";
        Target namoosSeparTarget = new Target(Constants.HERO, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        HolyBuff namoosSeparBuff = new HolyBuff(Integer.MAX_VALUE, true, false, 12);
        Spell namoosSeparSpell = new Spell("", "", 0, 0, 0, 0, 0, namoosSeparTarget, namoosSeparBuff, SpellActivationType.PASSIVE, descSpell2, false);
        String descUsable2 = "price : 4000";
        Usable namoosSepar = new Usable("shop_namoosSepar_1", "", 4000, namoosSeparSpell);
        usableList.add(namoosSepar);

        //3             //3
        //String descSpell3 = "Contains : DisarmBuff - Spell Activation Type : passive - isn't dispeller";
        //todo in item all spells are passive but this doesn't seem passive, i created the required parts but not Usable itself
        Target kamanDamolTarget = new Target(Constants.HERO, 1, 1, Constants.ENEMY, false, false, 0, Constants.RANGED_HYBRID);
        DisarmBuff kamanDamoolBuff = new DisarmBuff(1, true, false);
        //String descUsable1 = "price : 300";
        //todo
        //usableList.add(null);

        //4             //1
        String descSpell4 = "Contains : no Buffs - Spell Activation Type : passive - isn't dispeller";
        Target nooshadrooTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.ALL);
        Spell nooshdarooSpell = new Spell("", "", 0, 0, 0, 6, 0, nooshadrooTarget, (Buff) null, SpellActivationType.ON_CAST, descSpell4, false);
        String descCollectable4 = "collectable";
        Collectable nooshdaroo = new Collectable("battle_nooshdaroo_1", descCollectable4, nooshdarooSpell);
        collectableList.add(nooshdaroo);

        //5             //2
        String descSpell5 = "Contains : no Buffs - Spell Activation Type : on cast - isn't dispeller";
        Target tirDoShakhTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.RANGED_HYBRID);
        Spell tirDoShakhSpell = new Spell("", "", 0, 0, 2, 0, 0, tirDoShakhTarget, (Buff) null, SpellActivationType.ON_CAST, descSpell5, false);
        String descCollectalbe5 = "collectable";
        Collectable tirDoShakh = new Collectable("battle_tirDoShakh_1", descCollectalbe5, tirDoShakhSpell);
        collectableList.add(tirDoShakh);

        //6             //4
        String descSpell6 = "Contains : no Buffs - Spell Activation Type : at start of the battle - isn't dispeller";
        Target parSimorghTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.RANGED_HYBRID);
        Spell parSimorghSpell = new Spell("", "", 0, 0, -2, 0, 0, parSimorghTarget, (Buff) null, SpellActivationType.ON_BATTLE_START, descSpell6, false);
        String descUsable6 = "price : 3500";
        Usable parSimorgh = new Usable("shop_parSimorgh_1", descUsable6, 3500, parSimorghSpell);
        usableList.add(parSimorgh);

        //7             //3
        String descSpell7 = "Contains : ManaBuff - Spell Activation Type : passive - isn't dispeller";
        Target exirTarget = new Target(Constants.MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.ALL);
        PowerBuff exirBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 3);
        Spell exirSpell = new Spell("", "", 0, 0, 0, 3, 0, exirTarget, exirBuff, SpellActivationType.ON_CAST, descSpell7, false);
        String descCollectable7 = "collectable";
        Collectable exir = new Collectable("battle_exir_1", descCollectable7, exirSpell);
        collectableList.add(exir);

        //8             //4
        String descSpell8 = "Contains : ManaBuff - Spell Activation Type : on cast - isn't dispeller";
        Target majoonManaTarget = new Target(Constants.PLAYER, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.NONE);
        ManaBuff majoonManaBuff = new ManaBuff(1, false, false, 3);
        Spell majoonManaSpell = new Spell("", "", 0, 0, 0, 0, 0, majoonManaTarget, majoonManaBuff, SpellActivationType.ON_CAST, descSpell8, false);
        String descCollectable8 = "collectable";
        Collectable majoonMana = new Collectable("battle_majoonMana_1", descCollectable8, majoonManaSpell);
        collectableList.add(majoonMana);

        //9             //5
        String descSpell9 = "Contains : HolyBuff - Spell Activation Type : on cast - isn't dispeller";
        Target majoonRooinTaniTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.ALL);
        HolyBuff majoonRooinTaniBuff = new HolyBuff(2, true, false, 10);
        Spell majoonRooinTaniSpell = new Spell("", "", 0, 0, 0, 0, 0, majoonRooinTaniTarget, majoonRooinTaniBuff, SpellActivationType.ON_CAST, descSpell9, false);
        Collectable majoonRooinTani = new Collectable("battle_majoonRooinTani_1", "majoonRooinTani", majoonRooinTaniSpell);
        collectableList.add(majoonRooinTani);

        //10            //6
        //String descSpell10 = "Contains : ManaBuff - Spell Activation Type : passive - isn't dispeller";
        //collectableList.add(null);
        //todo

        //11            //7
        String descSpell11 = "Contains : no Buffs - Spell Activation Type : on cast - isn't dispeller";
        Target randomDamageTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.ALL);
        Spell randomDamageSpell = new Spell("", "", 0, 0, 2, 0, 0, randomDamageTarget, (Buff) null, SpellActivationType.ON_CAST, descSpell11, false);
        Collectable randomDamage = new Collectable("battle_randomDamage_1", "randomDamage", randomDamageSpell);
        collectableList.add(randomDamage);

        //12            //5
        //usableList.add(null);
        //todo
        //todo what does it mean?

        //13            //8
        String descSpell12 = "Contains : no Buffs - Spell Activation Type : on cast - isn't dispeller";
        Target bladesOfAgilityTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.ALL);
        Spell bladesOfAgilitySpell = new Spell("", "", 0, 0, 6, 0, 0, bladesOfAgilityTarget, (Buff) null, SpellActivationType.ON_CAST, descSpell12, false);
        Collectable bladesOfAgility = new Collectable("battle_bladesOfAgility_1", "bladesOfAgility", bladesOfAgilitySpell);
        collectableList.add(bladesOfAgility);

        //14            //6
        String descSpell14 = "Contains : ManaBuff - Spell Activation Type : at the start of the battle - isn't dispeller";
        Target kingWisdomTarget = new Target(Constants.PLAYER, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.NONE);
        ManaBuff kingWisdomBuff = new ManaBuff(Integer.MAX_VALUE, false, false, 1);
        Spell kingWisdomSpell = new Spell("", "", 0, 0, 0, 0, 0, kingWisdomTarget, kingWisdomBuff, SpellActivationType.ON_BATTLE_START, descSpell14, false);
        Usable kingWisdom = new Usable("shop_kingWisdom_1", "", 9000, kingWisdomSpell);
        usableList.add(kingWisdom);

        //15            //7
        String descSpell15 = "Contains : no Buffs - Spell Activation Type : on spawn - isn't dispeller";
        Target assassinationDaggerSpellTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell assassinationDaggerSpellSpell = new Spell("", "", 0, 0, 0, -1, 0, assassinationDaggerSpellTarget, (Buff) null, SpellActivationType.ON_SPAWN, descSpell15, false);
        String descSpell151 = "Contains : ManaBuff - Spell Activation Type : on spawn - isn't dispeller";
        Target assassinationDaggerTarget = new Target(Constants.WHOLE_UNITS, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.ALL);
        Spell assassinationDaggerSpell = new Spell("", "", 0, 0, 0, 0, 0, assassinationDaggerTarget, (Buff) null, SpellActivationType.ON_BATTLE_START, "", false, assassinationDaggerSpellSpell);
        Usable assassinationDagger = new Usable("shop_assassinationDagger_1", descSpell151, 15000, assassinationDaggerSpell);
        usableList.add(assassinationDagger);

        //16            //8
        String descSpell16 = "Contains : PoisonBuff - Spell Activation Type : on spawn - isn't dispeller";
        Target poisonousDaggerSpellTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, true, false, 0, Constants.ALL);
        PoisonBuff poisonousDaggerSpellBuff = new PoisonBuff(1, true, false, 1);
        Spell poisonousDaggerSpellSpell = new Spell("", "", 0, 0, 0, 0, 0, poisonousDaggerSpellTarget, poisonousDaggerSpellBuff, SpellActivationType.ON_ATTACK, descSpell16, false);
        String descSpell161 = "Contains : no Buffs - Spell Activation Type : at the start of the battle- isn't dispeller";
        Target poisonousDaggerTarget = new Target(Constants.WHOLE_UNITS, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.ALL);
        Spell poisonousDaggerSpell = new Spell("", "", 0, 0, 0, 0, 0, poisonousDaggerTarget, (Buff) null, SpellActivationType.ON_BATTLE_START, descSpell161, false, poisonousDaggerSpellSpell);
        Usable poisonousDagger = new Usable("shop_poisonousDagger_1", "price : 7000", 7000, poisonousDaggerSpell);
        usableList.add(poisonousDagger);

        //17            //9
        String descSpell17 = "Contains : DisarmBuff - Spell Activation Type : on attack - isn't dispeller";
        Target shockHammerSpellTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        DisarmBuff shockHammerSpellBuff = new DisarmBuff(1, true, false);
        Spell shockHammerSpellSpell = new Spell("", "", 0, 0, 0, 0, 0, shockHammerSpellTarget, shockHammerSpellBuff, SpellActivationType.ON_ATTACK, descSpell17, false);
        String descSpell171 = "Contains : no Buffs - Spell Activation Type : at the start of the battle - isn't dispeller";
        Target shockHammerTarget = new Target(Constants.WHOLE_HEROES, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.ALL);
        Spell shockHammerSpell = new Spell("", "", 0, 0, 0, 0, 0, shockHammerTarget, (Buff) null, SpellActivationType.ON_BATTLE_START, descSpell171, false, shockHammerSpellSpell);
        Usable shockHammer = new Usable("shop_shockHammer_1", "price : 15000", 15000, shockHammerSpell);
        usableList.add(shockHammer);

        //18            //10
        String descSpell18 = "Contains : PowerBuff - Spell Activation Type : at the start of the battle - isn't dispeller";
        Target soulEaterSpellTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.ALL);
        PowerBuff soulEaterSpellBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 1);
        Spell soulEaterSpellSpell = new Spell("", "", 0, 0, 0, 0, 0, soulEaterSpellTarget, soulEaterSpellBuff, SpellActivationType.ON_BATTLE_START, descSpell18, false);
        String descSpell181 = "Contains : ManaBuff - Spell Activation Type : at the start of the battle - isn't dispeller";
        Target soulEaterTarget = new Target(Constants.WHOLE_UNITS, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.ALL);
        Spell soulEaterSpell = new Spell("", "", 0, 0, 0, 0, 0, soulEaterTarget, (Buff) null, SpellActivationType.ON_BATTLE_START, descSpell181, false, soulEaterSpellSpell);
        Usable soulEater = new Usable("shop_soulEater_1", "price : 25000", 25000, soulEaterSpell);
        usableList.add(soulEater);
        //19            //11
        String descSpell19 = "Contains : HolyBuff - Spell Activation Type : at the start of the battle - isn't dispeller";
        Target ghoslTamidTarget = new Target(Constants.WHOLE_MINIONS, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.ALL);
        HolyBuff ghoslTamidBuff = new HolyBuff(2, true, false, 1);
        Spell ghoslTamidSpell = new Spell("", "", 0, 0, 0, 0, 0, ghoslTamidTarget, ghoslTamidBuff, SpellActivationType.ON_BATTLE_START, descSpell19, false);
        Usable ghoslTamid = new Usable("shop_ghoslTamid_1", "price 20000", 20000, ghoslTamidSpell);
        usableList.add(ghoslTamid);

        //20            //9
        String descSpell20 = "Contains : no Buffs - Spell Activation Type : on spawn - isn't dispeller";
        Target shamshirChiniTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.MELEE);
        Spell shamshirChiniSpell = new Spell("", "", 0, 0, 5, 0, 0, shamshirChiniTarget, (Buff) null, SpellActivationType.ON_CAST, descSpell20, false);
        Collectable shamshirChini = new Collectable("battle_shamshirChini_1", "collectable", shamshirChiniSpell);
        collectableList.add(shamshirChini);
    }

    private void makeAccounts() {
        computerPlayerLevel1 = new Account("computer1", "1");
        computerPlayerLevel2 = new Account("computer2", "2");
        computerPlayerLevel3 = new Account("computer3", "3");
        computerPlayerCostum = new Account("computerCostum", "costum");

        Deck computerPlayer1Deck = new Deck("Deck");
        addToComputerDeck(computerPlayer1Deck,0,1);
        addToComputerDeck(computerPlayer1Deck,6,1);
        addToComputerDeck(computerPlayer1Deck,9,1);
        addToComputerDeck(computerPlayer1Deck,10,1);
        addToComputerDeck(computerPlayer1Deck,11,1);
        addToComputerDeck(computerPlayer1Deck,17,1);
        addToComputerDeck(computerPlayer1Deck,19,1);
        addToComputerDeck(computerPlayer1Deck,20,1);
        addToComputerDeck(computerPlayer1Deck,30,1);
        addToComputerDeck(computerPlayer1Deck,38,1);
        addToComputerDeck(computerPlayer1Deck,40,1);
        addToComputerDeck(computerPlayer1Deck,40,2);
        addToComputerDeck(computerPlayer1Deck,42,1);
        addToComputerDeck(computerPlayer1Deck,46,1);
        addToComputerDeck(computerPlayer1Deck,47,1);
        addToComputerDeck(computerPlayer1Deck,50,1);
        addToComputerDeck(computerPlayer1Deck,51,1);
        addToComputerDeck(computerPlayer1Deck,55,1);
        addToComputerDeck(computerPlayer1Deck,67,1);
        addToComputerDeck(computerPlayer1Deck,65,1);
        addToComputerDeck(computerPlayer1Deck,69,1);

        Deck computerPlayer2Deck = new Deck("deck");
        addToComputerDeck(computerPlayer2Deck,1,1);
        addToComputerDeck(computerPlayer2Deck,2,1);
        addToComputerDeck(computerPlayer2Deck,4,1);
        addToComputerDeck(computerPlayer2Deck,7,1);
        addToComputerDeck(computerPlayer2Deck,8,1);
        addToComputerDeck(computerPlayer2Deck,12,1);
//        addToComputerDeck(computerPlayer2Deck,18,1);
        addToComputerDeck(computerPlayer2Deck,24,1);
        addToComputerDeck(computerPlayer2Deck,31,1);
        addToComputerDeck(computerPlayer2Deck,32,1);
        addToComputerDeck(computerPlayer2Deck,34,1);
        addToComputerDeck(computerPlayer2Deck,37,1);
        addToComputerDeck(computerPlayer2Deck,41,1);
        addToComputerDeck(computerPlayer2Deck,44,1);
        addToComputerDeck(computerPlayer2Deck,44,2);
        addToComputerDeck(computerPlayer2Deck,48,1);
        addToComputerDeck(computerPlayer2Deck,52,1);
        addToComputerDeck(computerPlayer2Deck,56,1);
        addToComputerDeck(computerPlayer2Deck,59,1);
        addToComputerDeck(computerPlayer2Deck,62,1);
        addToComputerDeck(computerPlayer2Deck,68,1);

        Deck computerPlayer3Deck = new Deck("deck");
        addToComputerDeck(computerPlayer3Deck,0,1);
        addToComputerDeck(computerPlayer3Deck,5,1);
        addToComputerDeck(computerPlayer3Deck,6,1);
        addToComputerDeck(computerPlayer3Deck,9,1);
        addToComputerDeck(computerPlayer3Deck,11,1);
        addToComputerDeck(computerPlayer3Deck,13,1);
        addToComputerDeck(computerPlayer3Deck,14,1);
        addToComputerDeck(computerPlayer3Deck,15,1);
        addToComputerDeck(computerPlayer3Deck,26,1);
        addToComputerDeck(computerPlayer3Deck,35,1);
        addToComputerDeck(computerPlayer3Deck,36,1);
        addToComputerDeck(computerPlayer3Deck,39,1);
        addToComputerDeck(computerPlayer3Deck,43,1);
        addToComputerDeck(computerPlayer3Deck,45,1);
        addToComputerDeck(computerPlayer3Deck,45,2);
        addToComputerDeck(computerPlayer3Deck,49,1);
        addToComputerDeck(computerPlayer3Deck,53,1);
        addToComputerDeck(computerPlayer3Deck,54,1);
        addToComputerDeck(computerPlayer3Deck,57,1);
        addToComputerDeck(computerPlayer3Deck,58,1);
        addToComputerDeck(computerPlayer3Deck,60,1);
        addToComputerDeck(computerPlayer3Deck,63,1);

        Deck computerPlayerCostumDeck = new Deck("deck");


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

    public Account getComputerPlayerCostum() {
        return computerPlayerCostum;
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
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }

    public boolean doesCardExist(String cardName) {
        return getCardWithName(cardName) != null;
    }

    public Usable getUsableWithName(String usableName) {
        for (Usable usable : usableList) {
            if (usable.getName().equals(usableName))
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

    public Card findCardInShop(String cardName) {
        for (Card card : cardList) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    public Usable findUsableInShop(String usableName) {
        for (Usable usable : usableList) {
            if (usable.getName().equals(usableName)) {
                return usable;
            }
        }
        return null;
    }

    public void searchInShop(String command) {
        String[] strings = command.split("\\s+");
        Card card = findCardInShop(strings[1]);
        Usable usable = findUsableInShop(strings[1]);
        Collectable collectable = findCollectableInShop(strings[1]);
        ControllerShop.getOurInstance().showIdInShop(card, usable, collectable);
    }

    private static Collectable findCollectableInShop(String collectableName) {
        for (Collectable collectable : DataBase.getInstance().getCollectableList()) {
            if (collectable.getName().equals(collectableName)) {
                return collectable;
            }
        }
        return null;
    }

    private void addToComputerDeck(Deck computerDeck,int index, int number){
        Card card;
        card = cardList.get(index).clone();
        card.setId(computerPlayerLevel1.getUsername()+"_"+card.getName()+"_"+number);
        if(card instanceof Unit && ((Unit) card).getHeroOrMinion().equals(Constants.HERO)){
            computerDeck.setHero((Unit)card);
            return;
        }
        computerDeck.addToCards(card);
    }


    boolean doesAccountExist(String username) {
        return getAccountWithUsername(username) != null;
    }
}