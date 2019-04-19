public class ControllerMatchInfo {
    private static final ControllerMatchInfo ourInstance = new ControllerMatchInfo();
    private static final View view = View.getInstance();

    private ControllerMatchInfo(){
    }

    public static ControllerMatchInfo getInstatnce(){
        return ourInstance;
    }

    public void showMatchHistory(Account account){
        view.showMatchHistory(account);
    }

}
