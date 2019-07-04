import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    private ResponseType responseType;
    private String message;
    private List<Integer> integers;
    private List<Object> objectList;
    private Map<Object, Class> objectClassMap = new HashMap<>();

    public Response(ResponseType responseType, String message, List<Integer> integers, List<Object> objectList) {
        this.responseType = responseType;
        this.message = message;
        this.integers = integers;
        this.objectList = objectList;
        if (objectList != null) {
            for (Object object : objectList)
                objectClassMap.put(object, object.getClass());
        }
    }

    public ResponseType getResponseType() {
        return responseType;
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

    public Map<Object, Class> getObjectClassMap() {
        return objectClassMap;
    }
}