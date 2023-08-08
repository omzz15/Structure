package om.self.structure.parent;

/**
 * An extension of {@link ParentContainer} that adds the ability to attach a parent with a key(for identification), plus methods that get called on attach and detach.
 * @param <K> the type of the key
 * @param <V> the type of the parent
 * @see ParentContainer
 */
public interface KeyedParentStructure<K, V> extends ParentContainer<V> {
    /**
     * Attaches a parent. <br>
     * Implementation Note: This method should call {@link ParentStructure#onParentAttach(Object)}
     * @param key the key associated with the parent
     * @param parent the parent being attached
     */
    void attachParent(K key, V parent);

    /**
     * Gets the key of the attached parent.
     * @return the key of attached parent, null if no parent is attached
     */
    K getParentKey();

    /**
     * Called when a parent is attached or changed.
     * @param key the key of the parent being attached
     * @param parent the parent that is being attached
     */
    default void onParentAttach(K key, V parent){}

    /**
     * Called when a parent is detached.
     * @param key the key of the parent being detached
     * @param parent the parent that is being detached
     */
    default void onParentDetach(K key, V parent){}
}
