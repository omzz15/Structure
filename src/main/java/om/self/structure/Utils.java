package om.self.structure;

public class Utils {
    public interface Check<T>{
        boolean apply(T value, Action action);
    }

    public interface KeyedCheck<K,V>{
        boolean apply(K key, V value, Action action);
    }

    public enum Action{
        ATTACH,
        DETACH
    }
}
