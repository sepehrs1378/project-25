import java.util.List;

public class View {
    private static View ourInstance = new View();

    public static View getInstance() {
        return ourInstance;
    }

    private View() {
    }

    public void printError(ErrorType errorType){
        System.out.println(errorType.getMessage());
    }

    public void printHelp(HelpType helpType){
        System.out.println(helpType.getMessage());
    }

    public void printContentsOfAList(List list){
        System.out.println(list);
    }
}
