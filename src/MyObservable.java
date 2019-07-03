import java.util.ArrayList;
import java.util.List;

public class MyObservable<T> {
    private T state;
    private List<MyObserver<? super T>> observerList = new ArrayList<>();

    public MyObservable(T state) {
        this.state = state;
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
        notifyAllObservers();
    }

    public void addObserver(MyObserver<? super T> observer) {
        observerList.add(observer);
    }

    public void removeObserver(MyObserver<? super T> observer) {
        observerList.remove(observer);
    }

    public void notifyObserver(MyObserver<? super T> observer) {
        observer.update(state);
    }

    public void notifyAllObservers() {
        observerList.forEach(observer -> observer.update(state));
    }
}
