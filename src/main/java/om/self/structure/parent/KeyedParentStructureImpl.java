package om.self.structure.parent;

/**
 * A more advanced structure class that keeps all the functionality of {@link ParentStructureImpl} but requires a key for identification purposes like HashTable lookups
 * @param <K> The type of the key used to identify itself to its parent
 * @param <V> The type of the parent object(passed into ParentStructureImpl).
 */
public class KeyedParentStructureImpl<K, V> implements KeyedParentStructure<K, V> {
    private K parentKey;
    private V parent;

    public KeyedParentStructureImpl(){}

    public KeyedParentStructureImpl(K key, V parent){
        attachParent(key, parent);
    }

    @Override
    public void attachParent(K key, V parent) {
        if(key == null) throw new IllegalArgumentException("the key argument can not be null!");
        if(key == parentKey && this.parent == parent) return;

        detachParent();

        onParentAttach(key, parent);
        this.parentKey = key;
        this.parent = parent;
    }

    /**
     * @implNote this method can be called on a null parent so null checks may be required
     */
    @Override
    public void detachParent() {
        if(!isParentAttached()) return;

        onParentDetach(parentKey, parent);
        parentKey = null;
        parent = null;
    }

    @Override
    public V getParent() {
        return parent;
    }

    @Override
    public K getParentKey() {
        return parentKey;
    }
}
