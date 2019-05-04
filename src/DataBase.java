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
    private Account computerPlayerCostume;

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
        //cardList.add(null);
        //todo

        //20
        Target shockTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        StunBuff shockBuff = new StunBuff(2, true, false);
        Spell shock = new Spell("shop_shock_1", "shock", 1200, 1, 0, 0, 0, shockTarget, shockBuff, SpellActivationType.ON_CAST, "", false);
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
        Target kavehTarget = new Target(Constants.CELL, 1, 1, Constants.NONE, false, false, 0, Constants.NONE);
        HolyBuff kavehBuff = new HolyBuff(3, true, false, 1);
        Spell kavehSpell = new Spell("", "", 0, 1, 0, 0, 3, kavehTarget, kavehBuff, SpellActivationType.ON_CAST, "", false);
        Unit kaveh = new Unit("shop_kaveh_1", "kaveh", 8000, 0, 50, 4, 1, 1, kavehSpell, Constants.HERO, "", false);
        cardList.add(kaveh);

        //7
        Target arashTarget = new Target(Constants.HERO_MINION,1,Integer.MAX_VALUE,Constants.ENEMY,false,false,0,Constants.ALL);
        Spell arashSpell = new Spell("","",0,2,0,-4,2,arashTarget,(Buff)null,SpellActivationType.ON_CAST,"",false);
        Unit arash = new Unit("shop_arash_1","arash",10000,0,30,2,2,6,arashSpell,Constants.HERO,"",false);
        cardList.add(arash);

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
//        Target pahlevanFarsTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.ALL);
//        Spell pahlevanFarsSpell = new Spell("", "", 0, 0, 5, 0, 0, pahlevanFarsTarget, (Buff) null, SpellActivationType.ON_ATTACK, "", false);
//        Unit pahlevanFars = new Unit("shop_pahlevanFars_1", "pahlevanFars", 600, 9, 24, 6, 1, 1, pahlevanFarsSpell, Constants.MINION, "", false);
//        cardList.add(pahlevanFars);

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
        Target oghabTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.ALL);
        PowerBuff oghabBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 10, 0);
        Spell oghabSpell = new Spell("", "", 0, 0, 0, 0, 0, oghabTarget, oghabBuff, SpellActivationType.PASSIVE, "", false);
        Unit oghab = new Unit("shop_oghab_1", "oghab", 200, 2, 0, 2, 1, 3, oghabSpell, Constants.MINION, "", false);
        cardList.add(oghab);

        //16
        Unit divGorazSavar = new Unit("shop_divGorazSavar_1", "divGorazSavar", 300, 6, 16, 8, 1, 1, null, Constants.MINION, "", false);
        cardList.add(divGorazSavar);

        //17
        Target ghoolTakCheshmTarget = new Target(Constants.MINION, 3, 3, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell ghoolTakCheshmSpell = new Spell("", "", 0, 0, 0, -2, 0, ghoolTakCheshmTarget, (Buff) null, SpellActivationType.ON_DEATH, "", false);
        Unit ghoolTakCheshm = new Unit("shop_ghoolTakCheshm_1", "ghoolTakCheshm", 500, 7, 12, 11, 2, 4, ghoolTakCheshmSpell, Constants.MINION, "", false);
        cardList.add(ghoolTakCheshm);

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
        Target shirDarandeTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.MELEE);
        ImmunityBuff shirDarandeBuff = new ImmunityBuff(Integer.MAX_VALUE, true, false, Constants.HOLY_BUFF);
        Spell shirDarandeSpell = new Spell("", "", 0, 0, 0, 0, 0, shirDarandeTarget, shirDarandeBuff, SpellActivationType.ON_ATTACK, "", false);
        Unit shirDarande = new Unit("shop_shirDarande_1", "shirDarande", 600, 2, 1, 8, 1, 1, shirDarandeSpell, Constants.MINION, "", false);
        cardList.add(shirDarande);

        //21
        Target marGhoolPeikarTarget = new Target(Constants.MINION, 1, 1, Constants.ENEMY, false, false, 2, Constants.ALL);
        NegativeArmorBuff marGhoolPeikarBuff = new NegativeArmorBuff(Integer.MAX_VALUE, true, false, 1);
        Spell marGhoolPeikarSpell = new Spell("", "", 0, 0, 0, 0, 0, marGhoolPeikarTarget, marGhoolPeikarBuff, SpellActivationType.ON_SPAWN, "", false);
        Unit marGhoolPeikar = new Unit("shop_marGhoolPeikar_1", "marGhoolPeikar", 500, 8, 14, 7, 2, 5, marGhoolPeikarSpell, Constants.MINION, "", false);
        cardList.add(marGhoolPeikar);

        //22
        Target gorgSefidTarget = new Target(Constants.MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        SequentialDamageBuff gorgSefidBuff = new SequentialDamageBuff(2, false, true, 6, 4);
        Spell gorgSefidSpell = new Spell("", "", 0, 0, 0, 0, 1, gorgSefidTarget, gorgSefidBuff, SpellActivationType.ON_ATTACK, "", false);
        Unit gorgSefid = new Unit("shop_gorgSefid_1", "gorgSefid", 400, 5, 8, 2, 1, 1, gorgSefidSpell, Constants.MINION, "", false);
        cardList.add(gorgSefid);

        //23
        Target palangTarget = new Target(Constants.MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        WeaknessBuff palangBuff = new WeaknessBuff(1, true, false, 8, 0);
        Spell palangSpell = new Spell("", "", 0, 0, 0, 0, 1, palangTarget, palangBuff, SpellActivationType.ON_ATTACK, "", false);
        Unit palang = new Unit("shop_palang_1", "palang", 400, 4, 6, 2, 1, 1, palangSpell, Constants.MINION, "", false);
        cardList.add(palang);

        //24
        Target gorgTarget = new Target(Constants.MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        WeaknessBuff gorgBuff = new WeaknessBuff(1, true, false, 6, 0);
        Spell gorgSpell = new Spell("", "", 0, 0, 0, 0, 1, gorgTarget, gorgBuff, SpellActivationType.ON_ATTACK, "", false);
        Unit gorg = new Unit("shop_gorg_1", "gorg", 400, 3, 6, 1, 1, 1, gorgSpell, Constants.MINION, "", false);
        cardList.add(gorg);
        //25
        Target jadoogarTarget = new Target(Constants.MINION, 3, 3, Constants.FRIEND, false, false, 0, Constants.ALL);
        List<Buff> jadoogarBuffs = new ArrayList<>();
        PowerBuff jadoogarBuff1 = new PowerBuff(1, true, false, 0, 2);
        WeaknessBuff jadoogarBuff2 = new WeaknessBuff(1, true, false, 1, 0);
        jadoogarBuffs.add(jadoogarBuff1);
        jadoogarBuffs.add(jadoogarBuff2);
        Spell jadoogarSpell = new Spell("", "", 0, 0, 0, 0, 0, jadoogarTarget, jadoogarBuffs, SpellActivationType.PASSIVE, "", false);
        Unit jadoogar = new Unit("shop_jadoogar_1", "jadoogar", 550, 4, 5, 4, 2, 3, jadoogarSpell, Constants.MINION, "", false);
        cardList.add(jadoogar);

        //26
        Target jadoogarAzamTarget = new Target(Constants.MINION, 3, 3, Constants.FRIEND, false, false, 0, Constants.ALL);
        List<Buff> jadoogarAzamBuffs = new ArrayList<>();
        PowerBuff jadoogarAzamBuff1 = new PowerBuff(1, true, false, 0, 2);
        HolyBuff jadoogarAzamBuff2 = new HolyBuff(1, true, true, 1);
        jadoogarAzamBuffs.add(jadoogarAzamBuff1);
        jadoogarAzamBuffs.add(jadoogarAzamBuff2);
        Spell jadoogarAzamSpell = new Spell("", "", 0, 0, 0, 0, 0, jadoogarAzamTarget, jadoogarAzamBuffs, SpellActivationType.PASSIVE, "", false);
        Unit jadoogarAzam = new Unit("shop_jadoogarAzam_1", "jadoogarAzam", 550, 6, 6, 6, 2, 5, jadoogarAzamSpell, Constants.MINION, "", false);
        cardList.add(jadoogarAzam);

        //27
        //todo spell activation type ambiguous
        Target genTarget = new Target(Constants.MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.ALL);
        PowerBuff genBuff = new PowerBuff(Integer.MAX_VALUE, true, true, 0, 1);
        Spell genSpell = new Spell("", "", 0, 0, 0, 0, 0, genTarget, genBuff, SpellActivationType.PASSIVE, "", false);
        Unit gen = new Unit("shop_gen_1", "gen", 500, 5, 10, 4, 2, 4, genSpell, Constants.MINION, "", false);
        cardList.add(gen);

        //28
        Target gorazVahshiTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.MELEE);
        ImmunityBuff gorazVahshiBuff = new ImmunityBuff(Integer.MAX_VALUE, true, false, Constants.DISARM);
        Spell gorazVahshiSpell = new Spell("", "", 0, 0, 0, 0, 0, gorazVahshiTarget, gorazVahshiBuff, SpellActivationType.ON_DEFEND, "", false);
        Unit gorazVahshi = new Unit("shop_gorazVahshi_1", "gorazVahshi", 500, 6, 10, 14, 1, 1, gorazVahshiSpell, Constants.MINION, "", false);
        cardList.add(gorazVahshi);

        //29
        Target piranTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.MELEE);
        ImmunityBuff piranBuff = new ImmunityBuff(Integer.MAX_VALUE, true, false, Constants.POISON);
        Spell piranSpell = new Spell("", "", 0, 0, 0, 0, 0, piranTarget, piranBuff, SpellActivationType.ON_DEFEND, "", false);
        Unit piran = new Unit("shop_piran_1", "piran", 400, 8, 20, 12, 1, 1, piranSpell, Constants.MINION, "", false);
        cardList.add(piran);

        //30
        Target givTarget = new Target(Constants.MINION, 1, 1, Constants.FRIEND, false, true, 0, Constants.RANGED);
        ImmunityBuff givBuff = new ImmunityBuff(Integer.MAX_VALUE, true, false, Constants.ENEMY_CARD_SPELL);
        Spell givSpell = new Spell("", "", 0, 0, 0, 0, 0, givTarget, givBuff, SpellActivationType.ON_DEFEND, "", false);
        Unit giv = new Unit("shop_giv_1", "giv", 450, 4, 5, 7, 2, 5, givSpell, Constants.MINION, "", false);
        cardList.add(giv);

        //31
        Target bahmanTarget = new Target(Constants.MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, true, false, 0, Constants.ALL);
        WeaknessBuff bahmanBuff = new WeaknessBuff(1, true, false, 16, 0);
        Spell bahmanSpell = new Spell("", "", 0, 0, 0, 0, 0, bahmanTarget, bahmanBuff, SpellActivationType.ON_SPAWN, "", false);
        Unit bahman = new Unit("shop_bahman_1", "bahman", 450, 8, 16, 9, 1, 1, bahmanSpell, Constants.MINION, "", false);
        cardList.add(bahman);

        //32
        Target ashkboosTarget = new Target(Constants.MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, true, 0, Constants.MELEE);
        ImmunityBuff ashkboosBuff = new ImmunityBuff(Integer.MAX_VALUE, true, false, Constants.WEAKER_AP);
        Spell ashkboosSpell = new Spell("", "", 0, 0, 0, 0, 0, ashkboosTarget, ashkboosBuff, SpellActivationType.ON_DEFEND, "", false);
        Unit ashkboos = new Unit("shop_ashkboos_1", "ashkboos", 400, 7, 14, 8, 1, 1, ashkboosSpell, Constants.MINION, "", false);
        cardList.add(ashkboos);

        //33
        Unit iraj = new Unit("shop_iraj_1", "iraj", 500, 4, 6, 20, 2, 3, null, Constants.MINION, "", false);
        cardList.add(iraj);

        //34
        Unit ghoolBozorg = new Unit("shop_ghoolBozorg_1", "ghoolBozorg", 600, 9, 30, 8, 1, 2, null, Constants.MINION, "", false);
        cardList.add(ghoolBozorg);

        //35
        Target ghoolDoSarTarget = new Target(Constants.HERO_MINION, 1, 1, Constants.ENEMY, false, false, 0, Constants.ALL);
        Spell ghoolDoSarSpell = new Spell("", "", 0, 0, 0, 0, 0, ghoolDoSarTarget, (Buff) null, SpellActivationType.ON_ATTACK, "", true);
        Unit ghoolDoSar = new Unit("shop_ghoolDoSar_1", "ghoolDoSar", 550, 4, 10, 4, 1, 1, ghoolDoSarSpell, Constants.MINION, "", false);
        cardList.add(ghoolDoSar);

        //36
        Target naneSarmaTarget = new Target(Constants.MINION, 3, 3, Constants.ENEMY, false, false, 0, Constants.ALL);
        StunBuff naneSarmaBuff = new StunBuff(1, true, false);
        Spell naneSarmaSpell = new Spell("", "", 0, 0, 0, 0, 0, naneSarmaTarget, naneSarmaBuff, SpellActivationType.ON_SPAWN, "", false);
        Unit naneSarma = new Unit("shop_naneSarma_1", "naneSarma", 500, 3, 3, 4, 2, 5, naneSarmaSpell, Constants.MINION, "", false);
        cardList.add(naneSarma);

        //37
        Target fooladZerehTarget = new Target(Constants.MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, true, 0, Constants.MELEE);
        HolyBuff fooladZerehBuff = new HolyBuff(Integer.MAX_VALUE, true, true, 12);
        Spell fooladZerehSpell = new Spell("", "", 0, 0, 0, 0, 0, fooladZerehTarget, fooladZerehBuff, SpellActivationType.PASSIVE, "", false);
        Unit fooladZereh = new Unit("shop_fooladZereh_1", "fooladZereh", 650, 3, 1, 1, 1, 1, fooladZerehSpell, Constants.MINION, "", false);
        cardList.add(fooladZereh);

        //38
        Target siavashTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.ALL);
        WeaknessBuff siavashBuff = new WeaknessBuff(1, false, false, 6, 0);
        Spell siavashSpell = new Spell("", "", 0, 0, 0, 0, 0, siavashTarget, siavashBuff, SpellActivationType.ON_DEATH, "", false);
        Unit siavash = new Unit("shop_siavash_1", "siavash", 350, 4, 8, 5, 1, 1, siavashSpell, Constants.MINION, "", false);
        cardList.add(siavash);

        //39
        Unit shahGhool = new Unit("shop_shahGhool_1", "shahGhool", 600, 5, 10, 4, 1, 1, null, Constants.MINION, "", true);
        cardList.add(shahGhool);

        //40
        Unit arzhangDiv = new Unit("shop_arzhangDiv_1", "arzhangDiv", 600, 3, 6, 6, 1, 1, null, Constants.MINION, "", true);
        cardList.add(arzhangDiv);
    }

    //todo it is better to merge makeUsables() && makeCollectables in one method because it is important for the indexes of cardList to be accurate based on phase1.peyvast :)
    public void makeItems() {
        //for items we will also add the number in their separate List for knowing which item in peyvast is in the separated item lists(needed in costume game mode) look at examples below for more info
        //inPeyvast     inEachList
        //1             //1
        Target tajDanayeeTarget = new Target(Constants.PLAYER, 1, 1, Constants.FRIEND, false, true, 0, Constants.ALL);
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

        //3             //3
        //todo in item all spells are passive but this doesn't seem passive, i created the required parts but not Usable itself
        Target kamanDamolTarget = new Target(Constants.HERO, 1, 1, Constants.ENEMY, false, false, 0, Constants.RANGED_HYBRID);
        DisarmBuff kamanDamoolBuff = new DisarmBuff(1, true, false);
        //todo
        //usableList.add(null);

        //4             //1
        Target nooshadrooTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.ALL);
        Spell nooshdarooSpell = new Spell("", "", 0, 0, 0, 6, 0, nooshadrooTarget, (Buff) null, SpellActivationType.ON_CAST, "", false);
        Collectable nooshdaroo = new Collectable("battle_nooshdaroo_1", "", nooshdarooSpell);
        collectableList.add(nooshdaroo);

        //5             //2
        Target tirDoShakhTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.RANGED_HYBRID);
        Spell tirDoShakhSpell = new Spell("", "", 0, 0, 2, 0, 0, tirDoShakhTarget, (Buff) null, SpellActivationType.ON_CAST, "", false);
        Collectable tirDoShakh = new Collectable("battle_tirDoShakh_1", "", tirDoShakhSpell);
        collectableList.add(tirDoShakh);

        //6             //4
        Target parSimorghTarget = new Target(Constants.HERO, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.ENEMY, false, false, 0, Constants.RANGED_HYBRID);
        Spell parSimorghSpell = new Spell("", "", 0, 0, -2, 0, 0, parSimorghTarget, (Buff) null, SpellActivationType.ON_BATTLE_START, "", false);
        Usable parSimorgh = new Usable("shop_parSimorgh_1", "", 3500, parSimorghSpell);
        usableList.add(parSimorgh);

        //7             //3
        Target exirTarget = new Target(Constants.MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.ALL);
        PowerBuff exirBuff = new PowerBuff(Integer.MAX_VALUE, true, false, 0, 3);
        Spell exirSpell = new Spell("", "", 0, 0, 0, 3, 0, exirTarget, exirBuff, SpellActivationType.ON_CAST, "", false);
        Collectable exir = new Collectable("battle_exir_1", "exir", exirSpell);
        collectableList.add(exir);

        //8             //4
        Target majoonManaTarget = new Target(Constants.PLAYER, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.NONE);
        ManaBuff majoonManaBuff = new ManaBuff(1, false, false, 3);
        Spell majoonManaSpell = new Spell("", "", 0, 0, 0, 0, 0, majoonManaTarget, majoonManaBuff, SpellActivationType.ON_CAST, "", false);
        Collectable majoonMana = new Collectable("battle_majoonMana_1", "", majoonManaSpell);
        collectableList.add(majoonMana);

        //9             //5
        Target majoonRooinTaniTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.ALL);
        HolyBuff majoonRooinTaniBuff = new HolyBuff(2, true, false, 10);
        Spell majoonRooinTaniSpell = new Spell("", "", 0, 0, 0, 0, 0, majoonRooinTaniTarget, majoonRooinTaniBuff, SpellActivationType.ON_CAST, "", false);
        Collectable majoonRooinTani = new Collectable("battle_majoonRooinTani_1", "majoonRooinTani", majoonRooinTaniSpell);
        collectableList.add(majoonRooinTani);

        //10            //6
        //collectableList.add(null);
        //todo

        //11            //7
        Target randomDamageTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.ALL);
        Spell randomDamageSpell = new Spell("", "", 0, 0, 2, 0, 0, randomDamageTarget, (Buff) null, SpellActivationType.ON_CAST, "", false);
        Collectable randomDamage = new Collectable("battle_randomDamage_1", "randomDamage", randomDamageSpell);
        collectableList.add(randomDamage);

        //12            //5
        //usableList.add(null);
        //todo
        //todo what does it mean?

        //13            //8
        Target bladesOfAgilityTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, true, false, 0, Constants.ALL);
        Spell bladesOfAgilitySpell = new Spell("", "", 0, 0, 6, 0, 0, bladesOfAgilityTarget, (Buff) null, SpellActivationType.ON_CAST, "", false);
        Collectable bladesOfAgility = new Collectable("battle_bladesOfAgility_1", "bladesOfAgility", bladesOfAgilitySpell);
        collectableList.add(bladesOfAgility);

        //14            //6
        Target kingWisdomTarget = new Target(Constants.PLAYER, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.NONE);
        ManaBuff kingWisdomBuff = new ManaBuff(Integer.MAX_VALUE, false, false, 1);
        Spell kingWisdomSpell = new Spell("", "", 0, 0, 0, 0, 0, kingWisdomTarget, kingWisdomBuff, SpellActivationType.ON_BATTLE_START, "", false);
        Usable kingWisdom = new Usable("shop_kingWisdom_1", "", 9000, kingWisdomSpell);
        usableList.add(kingWisdom);

        //15            //7
        //usableList.add(null);
        //todo

        //16            //8
        //usableList.add(null);
        //todo

        //17            //9
        //usableList.add(null);
        //todo

        //18            //10
        //usableList.add(null);
        //todo

        //19            //11
        //usableList.add(null);
        //todo

        //20            //9
        Target shamshirChiniTarget = new Target(Constants.HERO_MINION, Integer.MAX_VALUE, Integer.MAX_VALUE, Constants.FRIEND, false, false, 0, Constants.MELEE);
        Spell shamshirChiniSpell = new Spell("", "", 0, 0, 5, 0, 0, shamshirChiniTarget, (Buff) null, SpellActivationType.ON_CAST, "", false);
        Collectable shamshirChini = new Collectable("battle_shamshirChini_1", "", shamshirChiniSpell);
        collectableList.add(shamshirChini);

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

    public static Collectable findCollectableInShop(String collectableName) {
        for (Collectable collectable : DataBase.getInstance().getCollectableList()) {
            if (collectable.getName().equals(collectableName)) {
                return collectable;
            }
        }
        return null;
    }

    public boolean doesAccountExist(String username) {
        return getAccountWithUsername(username) != null;
    }
}