class Target {
    public static final String FRIEND = "friend";
    public static final String ENEMY = "enemy";
    public static final String HERO = "hero";
    public static final String CELL = "cell";
    public static final String MINION = "minion";
    private String typeOfTarget;
    private int width;
    private int length;
    private String friendOrEnemy;
    private boolean selfTargeting;

    public String getTypeOfTarget(){
        return typeOfTarget;
    }

    public int getWidth(){
        return width;
    }

    public int getLength(){
        return length;
    }

    public String getFriendOrEnemy(){
        return friendOrEnemy;
    }

    public boolean isSelfTargeting(){
        return selfTargeting;
    }

    public void setTypeOfTarget(String typeOfTarget){
        this.typeOfTarget=typeOfTarget;
    }

    public void setWidth(int width){
        this.width=width;
    }

    public void setLength(int length){
        this.length=length;
    }

    public void setFriendOrEnemy(String friendOrEnemy){
        this.friendOrEnemy=friendOrEnemy;
    }

    public void setSelfTargeting(boolean selfTargeting){
        this.selfTargeting=selfTargeting;
    }

    public List<Cell> getCells(){

    }
}
