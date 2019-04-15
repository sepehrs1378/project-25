class Spell extends Card {
    public static final String PASSIVE="passive";
    public static final String CASTABLE="castable";
    private Target target;
    private int cooldown;
    private int apChange;
    private int hpChange;
    private List<Buff> addedBuffs = new ArrayList<Buff>();
    private List<Buff> deletedBuffs = new ArrayList<Buff>();
    private List<Unit> addedUnits = new ArrayList<Unit>();
    private String passiveOrCastable;

    public Target getTarget(){
        return target;
    }

    public int getCooldown(){
        return cooldown;
    }

    public int getApChange(){
        return apChange;
    }

    public int getHpChange(){
        return hpChange;
    }

    public void setTarget(Target target){
        this.target=target;
    }

    public void setCooldown(int cooldown){
        this.cooldown=cooldown;
    }

    public void setApChange(int apChange){
        this.apChange=apChange;
    }

    public void setHpChange(int hpChange){
        this.hpChange=hpChange;
    }

    public void doSpell(){

    }

    public void changeAp(){

    }

    public void changeHp(){

    }

    public void addBuffs(){

    }

    public void deleteBuffs(){

    }

    public void createUnits(){

    }
}
