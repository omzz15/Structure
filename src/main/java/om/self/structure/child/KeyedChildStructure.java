package om.self.structure.child;

import java.util.Collection;
import java.util.Map;

public interface KeyedChildStructure<K, V> extends ChildContainer<V> {
    /**
     * method used to attach a child
     * @param key the key associated with the child
     * @param child the child being attached
     * @implNote make sure this method calls {@link KeyedChildStructure#onChildAttach(Object,Object)}
     */
    void attachChild(K key, V child);

    /**
     * method used to attach multiple children
     * @param children the children being attached
     */
    default void attachChildren(Map<K,V> children){
        for (Map.Entry<K,V> child: children.entrySet())
            attachChild(child.getKey(), child.getValue());
    }

    /**
     * method used to detach a child
     * @param key the key of the child being detached
     * @implNote make sure this method calls {@link KeyedChildStructure#onChildDetach(Object,Object)}
     */
    void detachChild(K key);

    @Override
    default Collection<V> getChildren(){
        return getChildrenAndKeys().values();
    }

    default Collection<K> getChildKeys(){
        return getChildrenAndKeys().keySet();
    }

    /**
     * gets the attached children
     * @return the children
     */
    Map<K, V> getChildrenAndKeys();

    /**
     * gets a child associated with a specific key
     * @param key the key of the child
     * @return will return the child or null if the key is not found
     */
    default V getChild(K key){
        return getChildrenAndKeys().get(key);
    }

    /**
     * checks whether a child is attached based on the key
     * @param key the key of the child you want to check
     * @return if the child is attached
     */
    default boolean isChildKeyAttached(K key){
        return getChildKeys().contains(key);
    }

    /**
     * method that is called when a child is attached.
     * @param key the key of the child
     * @param child the child that is being attached
     */
    default void onChildAttach(K key, V child){}

    /**
     * method that is called before a child is detached.
     * @param key the key of the child
     * @param child the child that is being detached
     */
    default void onChildDetach(K key, V child){}
}
