package om.self.structure.child;

import java.util.Collection;
import java.util.Map;

/**
 * An extension of {@link ChildContainer} that uses keys for identification and adds the ability to attach and detach children, plus methods that get called on attach and detach.
 * @param <K> the type of the key used to identify itself to its parent
 * @param <V> the type of the children
 * @see ChildContainer
 */
public interface KeyedChildStructure<K, V> extends ChildContainer<V> {
    /**
     * Attaches a child with a key.
     * @param key the key associated with the child
     * @param child the child being attached
     * @implNote This method should call {@link KeyedChildStructure#onChildAttach(Object,Object)}
     */
    default void attachChild(K key, V child){
        if(getChildrenAndKeys().put(key, child) == child) return;

        onChildAttach(key, child);
    }

    /**
     * Attaches multiple children with keys.
     * @param children the children being attached
     */
    default void attachChildren(Map<K,V> children){
        for (Map.Entry<K,V> child: children.entrySet())
            attachChild(child.getKey(), child.getValue());
    }

    /**
     * Detaches a child based on a key.
     * @param key the key of the child being detached
     * @implNote make sure this method calls {@link KeyedChildStructure#onChildDetach(Object,Object)}
     */
    default void detachChild(K key){
        if(!isChildKeyAttached(key)) return;

        onChildDetach(key, getChildrenAndKeys().remove(key));
    }

    /**
     * Detaches multiple children by calling {@link KeyedChildStructure#detachChild(Object)}.
     * @param keys The keys of the children being detached
     */
    default void detachChildren(Iterable<K> keys){
        for (K key: keys)
            detachChild(key);
    }

    /**
     * Detaches all attached children
     * @implNote To use the default implementation of this method, the hashmap used to store the children must be concurrent or {@link KeyedChildStructure#getChildKeys()} must return a copy of the keys and not a reference(pointer)
     */
    @Override
    default void detachChildren() {
        for (K key: getChildKeys())
            detachChild(key);
    }

    /**
     * Gets all attached children.
     * @return Attached children
     */
    @Override
    default Collection<V> getChildren(){return getChildrenAndKeys().values();}

    /**
     * Gets all the keys of attached children.
     * @return Keys of attached children
     */
    default Collection<K> getChildKeys(){
        return getChildrenAndKeys().keySet();
    }

    /**
     * Gets the attached children along with their keys.
     * @return The children and their keys
     * @implNote In order for default implementations of methods such as {@link KeyedChildStructure#detachChild(Object)} to work this must return a reference(pointer) not a copy
     */
    Map<K, V> getChildrenAndKeys();

    /**
     * Gets a child associated with a specific key
     * @param key the key of the child
     * @return will return the child or null if the key is not found
     */
    default V getChild(K key){
        return getChildrenAndKeys().get(key);
    }

    /**
     * Checks whether a child is attached based on the key
     * @param key the key of the child you want to check
     * @return if the child is attached
     */
    default boolean isChildKeyAttached(K key){
        return getChildKeys().contains(key);
    }

    /**
     * Called when a child is attached.
     * @param key the key of the child
     * @param child the child that is being attached
     */
    default void onChildAttach(K key, V child){}

    /**
     * Called when a child is detached.
     * @param key the key of the child
     * @param child the child that is being detached
     */
    default void onChildDetach(K key, V child){}
}
