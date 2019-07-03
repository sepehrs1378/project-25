import com.gilecode.yagson.YaGson;

import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private YaGson serializer = null;
    private YaGson diseralizer = null;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
