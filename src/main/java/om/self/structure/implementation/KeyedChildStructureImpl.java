package om.self.structure.implementation;

import om.self.structure.base.child.KeyedChildStructure;

import java.util.Hashtable;
import java.util.Map;

public abstract class KeyedChildStructureImpl<K, V> implements KeyedChildStructure<K, V> {
    private final Hashtable<K, V> children = new Hashtable<>();

    @Override
    public void attachChild(K key, V child) {
        if(children.put(key, child) != child)
            onChildAttach(key, child);
    }

    @Override
    public void detachChild(K key) {
        if(!isChildKeyAttached(key)) return;
        onChildDetach(key, children.remove(key));
    }

    @Override
    public Map<K, V> getChildrenAndKeys() {
        return children;
    }
}
