package om.self.structure;

public class Utils {
    /**
     * A check that can be applied to a value. This is used by Structures to check if a child or parent can be attached or detached.
     * @param <T> The type of value being checked
     */
    public interface Check<T>{
        /**
         * Applies the check to a value.
         * @param value The value being checked
         * @param action The action being performed
         * @return true if the check passes, false if the check fails
         */
        boolean apply(T value, Action action);
    }

    /**
     * A check that can be applied to a value. This is used by Structures to check if a child or parent can be attached or detached.
     * @param <K> The type of key being checked
     * @param <V> The type of value being checked
     */
    public interface KeyedCheck<K,V>{
        /**
         * Applies the check to a value.
         * @param key The key of the value being checked
         * @param value The value being checked
         * @param action The action being performed
         * @return true if the check passes, false if the check fails
         */
        boolean apply(K key, V value, Action action);
    }

    /**
     * An action that can be performed on a parent/child in a structure.
     */
    public enum Action{
        ATTACH,
        DETACH
    }

    public static void tryFunction(Runnable action, Runnable onSuccess, Runnable onFail){
        try{
            action.run();
            onSuccess.run();
        } catch (Exception e){
            onFail.run();
        }
    }

    public static void tryFunction(Runnable action){
        tryFunction(action, () -> {}, () -> {});
    }
}
