import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class ServerRequestSender extends Thread {
    private Request request;

    public ServerRequestSender(Request request) {
        this.request = request;
    }

    @Override
    public void run() {
        try {
            OutputStreamWriter output = ClientDB.getInstance().getOutput();
            YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
            yaGson.toJson(request, output);
            output.flush();
//            logRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logRequest() {
        System.out.println("SentRequest-->>" + request.getRequestType());
    }
}