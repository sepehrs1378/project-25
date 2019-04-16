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
}
