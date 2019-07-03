import java.io.IOException;
import java.net.Socket;

public class ServerHandler extends Thread {
    private String address;
    private int port;

    public ServerHandler(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
