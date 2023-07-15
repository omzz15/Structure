package om.self.structure.parent;

/**
 * An implementation of {@link ParentStructureImpl}
 * @param <K> The type of the key
 * @param <V> The type of the parent
 * @see KeyedParentStructure
 */
public class KeyedParentStructureImpl<K, V> implements KeyedParentStructure<K, V> {
    private K parentKey;
    private V parent;

    /**
     * Default constructor
     */
    public KeyedParentStructureImpl(){}

    /**
     * Constructor that automatically attaches a parent
     * @param key The key of the parent
     * @param parent The parent being attached
     */
    public KeyedParentStructureImpl(K key, V parent){
        attachParent(key, parent);
    }

    /**
     * Detaches the current parent if one is attached then attaches a parent.
     * @param key the key of the parent
     * @param parent the parent being attached
     */
    @Override
    public void attachParent(K key, V parent) {
        if(key == null) throw new IllegalArgumentException("the key argument can not be null!");
        if(key == parentKey && this.parent == parent) return;

        detachParent();
        if(isParentAttached()) return; //in case a check prevents parent from being detached

        onParentAttach(key, parent);
        this.parentKey = key;
        this.parent = parent;
    }

    /**
     * Detached the parent if one is attached.
     */
    @Override
    public void detachParent() {
        if(!isParentAttached()) return;

        onParentDetach(parentKey, parent);
        parentKey = null;
        parent = null;
    }

    /**
     * Gets the currently attached parent
     * @return the attached parent, null if no parent is attached
     */
    @Override
    public V getParent() {
        return parent;
    }

    /**
     * Gets the key of the currently attached parent
     * @return the key of attached parent, null if no parent is attached
     */
    @Override
    public K getParentKey() {
        return parentKey;
    }
}
