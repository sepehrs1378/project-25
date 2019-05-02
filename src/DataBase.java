import com.sun.source.doctree.UnknownInlineTagTree;

import java.awt.event.MouseAdapter;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;

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
        makeCardSpells();
        makeHeroes();
        makeMinions();
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

        //11
        Target allPoisonTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        PoisonBuff allPoisonBuff = new PoisonBuff(4, true, false, 1);
        Spell allPoison = new Spell("shop_allPoison_1", "allPoison", 1500, 8, 0, 0, 0, allPoisonTarget, allPoisonBuff, SpellActivationType.ON_CAST, "", false);
        cardList.add(allPoison);

        //12
        Target dispelTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ALL, false, false, 0, Constants.ALL);
        Spell dispel = new Spell("shop_dispel_1", "dispel", 2100, 0, 0, 0, 0, dispelTarget, (Buff) null, SpellActivationType.ON_CAST, "", true);
        cardList.add(dispel);

        //13
        Target healthWithProfitTarget = new Target(Constants.ALL, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        List<Buff> healthWithProfitAddedBuffs = new ArrayList<>();
        WeaknessBuff healthWithProfitBuff1 = new WeaknessBuff(Integer.MAX_VALUE, true, false, 6, 0);
        HolyBuff healthWithProfitBuff2 = new HolyBuff(3, true, false, 2);
        healthWithProfitAddedBuffs.add(healthWithProfitBuff1);
        healthWithProfitAddedBuffs.add(healthWithProfitBuff2);
        Spell healthWithProfit = new Spell("shop_healthWithProfit_1", "healthWithProfit", 2250, 0, 0, 0, 0, healthWithProfitTarget, healthWithProfitAddedBuffs, SpellActivationType.ON_CAST, "", false);
        cardList.add(healthWithProfit);

        //14
        Target powerUpTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        PowerBuff powerUpBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 6);
        Spell powerUp = new Spell("shop_powerUp_1", "powerUp", 2500, 2, 0, 0, 0, powerUpTarget, powerUpBuff, SpellActivationType.ON_CAST, "", false);
        cardList.add(powerUp);

        //15
        Target allPowerTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.ALL);
        PowerBuff allPowerBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 2);
        Spell allPower = new Spell("shop_allPower_1", "allPower", 200, 4, 0, 0, 0, allPowerTarget, allPowerBuff, SpellActivationType.ON_CAST, "", false);
        cardList.add(allPower);

        //16
        Target allAttackTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell allAttack = new Spell("shop_allAttack_1", "allAttack", 1500, 4, 0, -6, 0, allAttackTarget, (Buff) null, SpellActivationType.ON_CAST, "", false);
        cardList.add(allAttack);

        //17
        Target weakeningTarget = new Target(Constants.MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        WeaknessBuff weakeningBuff = new WeaknessBuff(Integer.MAX_VALUE, true, false, 0, 4);
        Spell weakening = new Spell("shop_weakening_1", "weakening", 1000, 1, 0, 0, 0, weakeningTarget, weakeningBuff, SpellActivationType.ON_CAST, "", false);
        cardList.add(weakening);

        //18
        Target sacrificeTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        List<Buff> sacrificeBuffs = new ArrayList<>();
        WeaknessBuff sacrificeBuff1 = new WeaknessBuff(Integer.MAX_VALUE, true, false, 6, 0);
        PowerBuff sacrificeBuff2 = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 8);
        sacrificeBuffs.add(sacrificeBuff1);
        sacrificeBuffs.add(sacrificeBuff2);
        Spell sacrifice = new Spell("shop_sacrifice_1", "sacrifice", 1600, 2, 0, 0, 0, sacrificeTarget, sacrificeBuffs, SpellActivationType.ON_CAST, "", false);
        cardList.add(sacrifice);

        //19
        //Target kingsGuardTarget = new Target()
        cardList.add(null);
        //todo

        //20
        Target shockTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        StunBuff shockBuff = new StunBuff(2, true, false);
        Spell shock = new Spell("shop_shock_1", "shok", 1200, 1, 0, 0, 0, shockTarget, shockBuff, SpellActivationType.ON_CAST, "", false);
        cardList.add(shock);

    }

    public void makeHeroes() {
        //1
        Target divSefidTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, true, 0, Constants.ALL);
        PowerBuff divSefidBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 4);
        Spell divSefidSpell = new Spell("", "", 0, 1, 0, 0, 2, divSefidTarget, divSefidBuff, SpellActivationType.ON_CAST, "", false);
        Unit divSefid = new Unit("shop_divSefid_1", "divSefid", 8000, 0, 50, 4, 1, 1, divSefidSpell, Constants.HERO, "", false);
        cardList.add(divSefid);

        //2
        Target simorghTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        StunBuff simorghBuff = new StunBuff(1, true, false);
        Spell simorghSpell = new Spell("", "", 0, 5, 0, 0, 8, simorghTarget, simorghBuff, SpellActivationType.ON_CAST, "", false);
        Unit simorgh = new Unit("shop_simorgh_1", "simorgh", 9000, 0, 50, 4, 1, 1, simorghSpell, Constants.HERO, "", false);
        cardList.add(simorgh);

        //3
        Target ejdehaHaftSarTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        DisarmBuff ejdehaHaftSarBuff = new DisarmBuff(Integer.MAX_VALUE, true, false);
        Spell ejdehaHaftSarSpellSpell = new Spell("", "", 0, 0, 0, 0, 1, ejdehaHaftSarTarget, ejdehaHaftSarBuff, SpellActivationType.ON_CAST, "", false);
        Unit ejdehaHaftSar = new Unit("shop_ejdehaHaftSar_1", "ejdehaHaftSar", 8000, 0, 50, 4, 1, 1, ejdehaHaftSarSpellSpell, Constants.HERO, "", false);
        cardList.add(ejdehaHaftSar);

        //4
        Target rakhshTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, true, 0, Constants.ALL);
        StunBuff rakhshBuff = new StunBuff(1, true, false);
        Spell rakhshSpell = new Spell("", "", 0, 1, 0, 0, 2, rakhshTarget, rakhshBuff, SpellActivationType.ON_CAST, "", false);
        Unit rakhsh = new Unit("shop_rakhsh_1", "rakhsh", 8000, 0, 50, 4, 1, 1, rakhshSpell, Constants.HERO, "", false);
        cardList.add(rakhsh);

        //5
        Target zahakTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        PoisonBuff zhakBuff = new PoisonBuff(3, true, false, 1);
        Spell zahakSpell = new Spell("", "", 0, 0, 0, 0, 0, zahakTarget, zhakBuff, SpellActivationType.ON_CAST, "", false);
        Unit zahak = new Unit("shop_zahak_1", "zahak", 10000, 0, 50, 2, 1, 1, zahakSpell, Constants.HERO, "", false);
        cardList.add(zahak);

        //6
        //todo is this true? (what is the difference between holyBuff and immunityBuff?)
        Target kavehTarget = new Target(Constants.CELL, 1, 1, Constants.NONE, false, false, 0, Constants.NONE);
        HolyBuff kavehBuff = new HolyBuff(3, true, false, 1);
        Spell kavehSpell = new Spell("", "", 0, 1, 0, 0, 3, kavehTarget, kavehBuff, SpellActivationType.ON_CAST, "", false);
        Unit kaveh = new Unit("shop_kaveh_1", "kaveh", 8000, 0, 50, 4, 1, 1, kavehSpell, Constants.HERO, "", false);
        cardList.add(kaveh);

        //7
        //todo abstract like kingsGuardSpell
        cardList.add(null);

        //8
        Target afsanehTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, true, 0, Constants.ALL);
        Spell afsanehSpell = new Spell("", "", 0, 1, 2, 0, 0, afsanehTarget, (Buff) null, SpellActivationType.ON_CAST, "", true);
        Unit afsaneh = new Unit("shop_afsaneh_1", "afsaneh", 11000, 0, 40, 3, 2, 3, afsanehSpell, Constants.HERO, "", false);
        cardList.add(afsaneh);

        //9
        //todo check this
        Target esfandiarTarget = new Target(Constants.HERO, 1, 1, Constants.FRIEND, false, true, 0, Constants.ALL);
        HolyBuff esfandiarBuff = new HolyBuff(Integer.MAX_VALUE, false, true, 3);
        Spell esfandiarSpell = new Spell("", "", 0, 0, 0, 0, 0, esfandiarTarget, esfandiarBuff, SpellActivationType.ON_SPAWN, "", false);
        Unit esfandiar = new Unit("shop_esfandiar_1", "esfandiar", 12000, 0, 35, 3, 1, 3, esfandiarSpell, Constants.HERO, "", false);
        cardList.add(esfandiar);

        //10
        Unit rostam = new Unit("shop_rostam_1", "rostam", 8000, 0, 55, 7, 1, 4, null, Constants.HERO, "", false);
        cardList.add(rostam);
    }

    public void makeMinions() {
        //1
        Unit kamandarFars = new Unit("shop_kamandarFars_1", "kamandarFars", 300, 2, 5, 4, 2, 7, null, Constants.MINION, "", false);
        cardList.add(kamandarFars);

        //2
        Target shamshirZanFarsTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        StunBuff shamshirZanFarsBuff = new StunBuff(1, true, false);
        Spell shamshirZanFarsSpell = new Spell("", "", 0, 0, 0, 0, 0, shamshirZanFarsTarget, shamshirZanFarsBuff, SpellActivationType.ON_ATTACK, "", false);
        Unit shamshirZanFars = new Unit("shop_shamshirZanFars_1", "shamshirZanFars", 400, 2, 6, 4, 1, 1, shamshirZanFarsSpell, Constants.MINION, "", false);
        cardList.add(shamshirZanFars);

        //3
        Unit neizeDarFars = new Unit("shop_neizeDarFars_1", "neizeDarFars", 500, 1, 5, 3, 1, 3, null, Constants.MINION, "", false);
        cardList.add(neizeDarFars);

        //4
        Unit asbSavarFars = new Unit("shop_asbSavarFars_1", "asbSavarFars", 200, 4, 10, 6, 1, 1, null, Constants.MINION, "", false);
        cardList.add(asbSavarFars);

        //5
        //todo check if this is true? (i think it is good to add a powerBuff for each attack)
        Target pahlevanFarsTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.ALL);
        Spell pahlevanFarsSpell = new Spell("", "", 0, 0, 5, 0, 0, pahlevanFarsTarget, (Buff) null, SpellActivationType.ON_ATTACK, "", false);
        Unit pahlevanFars = new Unit("shop_pahlevanFars_1", "pahlevanFars", 600, 9, 24, 6, 1, 1, pahlevanFarsSpell, Constants.MINION, "", false);
        cardList.add(pahlevanFars);

        //6
        Unit sepahSalarFars = new Unit("shop_sepahSalarFars_1", "sepahSalarFars", 800, 7, 12, 4, 1, 1, null, Constants.MINION, "", true);
        cardList.add(sepahSalarFars);

        //7
        Unit kamandarToorani = new Unit("shop_kamandarToorani_1", "kamandarToorani", 500, 1, 3, 4, 2, 5, null, Constants.MINION, "", false);
        cardList.add(kamandarToorani);

        //8
        Unit gholabSangDarToorani = new Unit("shop_gholabSangDarToorani_1", "gholabSangDarToorani", 600, 1, 4, 2, 2, 7, null, Constants.MINION, "", false);
        cardList.add(gholabSangDarToorani);

        //9
        Unit neizeDarToorani = new Unit("shop_neizeDarToorani_1", "neizeDarToorani", 600, 1, 4, 4, 1, 3, null, Constants.MINION, "", false);
        cardList.add(neizeDarToorani);

        //10
        Target jasoosTooraniTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        List<Buff> jasoosTooraniBuffs = new ArrayList<>();
        DisarmBuff jasoosTooraniBuff1 = new DisarmBuff(1, true, false);
        PoisonBuff jasoosTooraniBuff2 = new PoisonBuff(4, true, false, 1);
        jasoosTooraniBuffs.add(jasoosTooraniBuff1);
        jasoosTooraniBuffs.add(jasoosTooraniBuff2);
        Spell jasoosTooraniSpell = new Spell("", "", 0, 0, 0, 0, 0, jasoosTooraniTarget, jasoosTooraniBuffs, SpellActivationType.ON_ATTACK, "", false);
        Unit jasoosToorani = new Unit("shop_jasoosToorani_1", "jasoosToorani", 700, 4, 6, 6, 1, 1, jasoosTooraniSpell, Constants.MINION, "", false);
        cardList.add(jasoosToorani);

        //11
        Unit gorzdarToorani = new Unit("shop_gorzdarToorani_1", "gorzdarToorani", 450, 2, 3, 10, 1, 1, null, Constants.MINION, "", false);
        cardList.add(gorzdarToorani);

        //12
        Unit shahzadehToorani = new Unit("shop_shahzadehToorani_1", "shahzadehToorani", 800, 6, 6, 10, 1, 1, null, Constants.MINION, "", true);
        cardList.add(shahzadehToorani);

        //13
        Unit divSiah = new Unit("shop_divSiah_1", "divSiah", 300, 9, 14, 10, 1, 7, null, Constants.MINION, "", false);
        cardList.add(divSiah);

        //14
        Unit ghoolSangAndaz = new Unit("shop_ghoolSangAndaz_1", "ghoolSangAndaz", 300, 9, 12, 12, 2, 7, null, Constants.MINION, "", false);
        cardList.add(ghoolSangAndaz);

        //15
        //todo what the hell is wrong with HP?
        Target oghabTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.ALL);
        PowerBuff oghabBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 10, 0);
        Spell oghabSpell = new Spell("", "", 0, 0, 0, 0, 0, oghabTarget, oghabBuff, SpellActivationType.PASSIVE, "", false);
        Unit oghab = new Unit("shop_oghab_1", "oghab", 200, 2, 0, 2, 1, 3, oghabSpell, Constants.MINION, "", false);
        cardList.add(oghab);

        //16
        Unit divGorazSavar = new Unit("shop_divGorazSavar_1", "divGorazSavar", 300, 6, 16, 8, 1, 1, null, Constants.MINION, "", false);
        cardList.add(divGorazSavar);

        //17
        //todo same problem with KingGuardSpell from now on i,ll call it kingsGuardProblem :)
        cardList.add(null);

        //18
        Target marSamiTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        PoisonBuff marSamiBuff = new PoisonBuff(3, true, false, 1);
        Spell marSamiSpell = new Spell("", "", 0, 0, 0, 0, 0, marSamiTarget, marSamiBuff, SpellActivationType.ON_ATTACK, "", false);
        Unit marSami = new Unit("shop_marSami_1", "marSami", 300, 4, 5, 6, 2, 4, marSamiSpell, Constants.MINION, "", false);
        cardList.add(marSami);

        //19
        Unit ejdehaAtashAndaz = new Unit("shop_ejdehaAtashAndaz_1", "ejdehaAtashAndaz", 250, 5, 9, 5, 2, 4, null, Constants.MINION, "", false);
        cardList.add(ejdehaAtashAndaz);

        //20
        //todo don't know how to make this on :/

        //todo ForAliiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
        //21

        //22

        //23

        //24

        //25

        //26

        //27

        //28

        //29

        //30

        //31

        //32

        //33

        //34

        //35

        //36

        //37

        //38

        //39

        //40

    }

    //todo it is better to merge makeUsables() && makeCollectables in one method because it is important for the indexes of cardList to be accurate based on phase1.peyvast :)
    public void makeItems() {
        //for items we will also add the number in their separate List for knowing which item in peyvast is in the separated item lists(needed in costume game mode) look at examples below for more info
        //inPeyvast     inEachList
        //1             //1
        Target tajDanayeeTarget = new Target(Constants.NONE, 1, 1, Constants.FRIEND, false, true, 0, Constants.ALL);
        ManaBuff tajDanayeeBuff = new ManaBuff(3, false, false, 1);
        Spell tajDanayeeSpell = new Spell("", "", 0, 0, 0, 0, 0, tajDanayeeTarget, tajDanayeeBuff, SpellActivationType.PASSIVE, "", false);
        Usable tajDanayee = new Usable("shop_tajDanayee_1", "", 300, tajDanayeeSpell);
        usableList.add(tajDanayee);

        //2             //2
        Target namoosSeparTarget = new Target(Constants.HERO, 1, 1, Constants.FRIEND, false, false, 0, Constants.ALL);
        HolyBuff namoosSeparBuff = new HolyBuff(Integer.MAX_VALUE, true, false, 12);
        Spell namoosSeparSpell = new Spell("", "", 0, 0, 0, 0, 0, namoosSeparTarget, namoosSeparBuff, SpellActivationType.PASSIVE, "", false);
        Usable namoosSepar = new Usable("shop_namoosSepar_1", "", 4000, namoosSeparSpell);
        usableList.add(namoosSepar);

        //3
        //todo in item all spells are passive but this doesn't seem passive, i created the required parts but not Usable itself
        Target kamanDamolTarget = new Target(Constants.HERO, 1, 1, Constants.ENEMY, false, false, 0, Constants.RANGED_HYBRID);
        DisarmBuff kamanDamoolBuff = new DisarmBuff(1, true, false);
        //todo
        usableList.add(null);

        //4

        //5

        //6

        //7

        //8

        //9

        //10

        //11

        //12

        //13

        //14

        //15

        //16

        //17

        //18

        //19

        //20

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