package om.self.structure.implementation;

import om.self.structure.base.parent.KeyedParentStructure;

/**
 * A more advanced structure class that keeps all the functionality of {@link ParentStructureImpl} but requires a key for identification purposes like HashTable lookups
 * @param <K> The type of the key used to identify itself to its parent
 * @param <V> The type of the parent object(passed into ParentStructureImpl).
 */
public abstract class KeyedParentStructureImpl<K, V> implements KeyedParentStructure<K, V> {
    private K parentKey;
    private V parent;

    @Override
    public void attachParent(K key, V parent) {
        if(isParentAttached()) detachParent();

        onParentAttach(key, parent);
        this.parentKey = key;
        this.parent = parent;
    }

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
