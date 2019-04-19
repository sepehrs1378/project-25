import java.util.ArrayList;
import java.util.List;

public class View {
    private static View ourInstance = new View();

    public static View getInstance() {
        return ourInstance;
    }

    private View() {
    }

    public void printError(ErrorType errorType) {
        System.out.println(errorType.getMessage());
    }

    public void printHelp(HelpType helpType) {
        System.out.println(helpType.getMessage());
    }

    public void printContentsOfAList(List list) {
        System.out.println(list);

    }
    public void showUsers(List<Account> users,String currentUserName){
        for(Account account:users){
            if(!account.getUsername().equals(currentUserName))
                System.out.println(account.getUsername());
        }
    }
    public void showLeaderboard(List<Account> accounts) {
        int counter = 1;
        for (Account account : accounts) {
            System.out.println(counter + "- Username: " + account.getUsername() +
                    " - Wins: " + account.getNumberOfWins());
            counter++;
        }
    }
}
