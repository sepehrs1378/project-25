import java.util.HashMap;
import java.util.List;

public class Request {
    private RequestType requestType;
    private List<String> messages;
    private List<Integer> integers;
    private List<Object> objects;
    private HashMap<Object, Class> objectClassHashMap = new HashMap<>();

    public Request(RequestType requestType, List<String> messages, List<Integer> integers
            , List<Object> objects) {
        this.requestType = requestType;
        this.messages = messages;
        this.integers = integers;
        this.objects = objects;
        for (Object object : objects) {
            objectClassHashMap.put(object, object.getClass());
        }
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<Integer> getIntegers() {
        return integers;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public HashMap<Object, Class> getObjectClassHashMap() {
        return objectClassHashMap;
    }
}
