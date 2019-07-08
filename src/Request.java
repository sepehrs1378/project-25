import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Request implements Serializable {
    private RequestType requestType;
    private String message;
    private List<Integer> integers;
    private List<Object> objectList;
    private HashMap<Object, Class> objectClassHashMap = new HashMap<>();

    public Request(RequestType requestType, String message, List<Integer> integers
            , List<Object> objectList) {
        this.requestType = requestType;
        this.message = message;
        this.integers = integers;
        this.objectList = objectList;
        if (objectList != null)
            for (Object object : this.objectList)
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

    public List<Object> getObjectList() {
        return objectList;
    }

    public HashMap<Object, Class> getObjectClassHashMap() {
        return objectClassHashMap;
    }
}
