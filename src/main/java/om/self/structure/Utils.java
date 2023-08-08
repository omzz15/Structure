package om.self.structure;

/**
 * A collection of utilities used by the structures.
 */
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
        /**
         * The action of attaching a parent/child
         */
        ATTACH,
        /**
         * The action of detaching a parent/child
         */
        DETACH
    }

    /**
     * A method
     * that can be used to try and run some code
     * then run some success or fail code based on if the original code throws an exception.
     * @param action The code being tried
     * @param onSuccess The code to run if the original code succeeds
     * @param onFail The code to run if the original code fails
     */
    public static void tryFunction(Runnable action, Runnable onSuccess, Runnable onFail){
        try{
            action.run();
            onSuccess.run();
        } catch (Exception e){
            onFail.run();
        }
    }

    /**
     * Just a simple implementation of {@link Utils#tryFunction(Runnable, Runnable, Runnable)} that just runs code.
     * @param action The code being tried
     */
    public static void tryFunction(Runnable action){
        tryFunction(action, () -> {}, () -> {});
    }
}
