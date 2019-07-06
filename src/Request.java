import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Request implements Serializable {
    private RequestType requestType;
    private String message;
    private List<Integer> integers;
    private List<Object> objects;
    private HashMap<Object, Class> objectClassHashMap = new HashMap<>();

    public Request(RequestType requestType, String message, List<Integer> integers
            , Object... objectList) {
        this.requestType = requestType;
        this.message = message;
        this.integers = integers;
        this.objects.addAll(Arrays.asList(objectList));
        for (Object object : objects)
            objectClassHashMap.put(object, object.getClass());
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getMessage() {
        return message;
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
