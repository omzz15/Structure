package om.self.structure.parent;

public interface KeyedParentStructure<K, V> extends ParentContainer<V> {
    void attachParent(K key, V parent);

    K getParentKey();

    /**
     * method that is called when a parent is attached or changed.
     * @param key the key of the parent
     * @param parent the parent that is being attached
     */
    default void onParentAttach(K key, V parent){}

    /**
     * method that is called when a parent is detached.
     * @param key the key of the parent being detached
     * @param parent the parent that is being detached
     */
    default void onParentDetach(K key, V parent){}
}
