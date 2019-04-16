import java.util.Scanner;

public class Request {
    private static Scanner scanner = new Scanner(System.in);
    private String command;

    public String getNewCommand() {
        command = scanner.nextLine();
        return command;
    }

    public String getType() {

    }


}
